/**
 * Copyright (C) 2021 nierax
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Represents the area of a calculation.
 * 
 * @author nierax
 *
 */
public class CalculationArea {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private MathContext mc = new MathContext(200, RoundingMode.HALF_UP);

	/**
	 * @param topLeft
	 * @param bottomRight
	 */
	private CalculationArea(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	/**
	 * Create a new instance of calculation area.
	 * 
	 * @param topLeft
	 * @param bottomRight
	 * @return
	 */
	public final static CalculationArea of(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight) {
		return new CalculationArea(topLeft, bottomRight);
	}

	private int countNotSet() {
		int count = 0;
		if (!topLeft.isCxSet()) {
			count++;
		}
		if (!topLeft.isCySet()) {
			count++;
		}
		if (!bottomRight.isCxSet()) {
			count++;
		}
		if (!bottomRight.isCySet()) {
			count++;
		}
		return count;
	}

	private BigDecimal difference(BigDecimal v0, BigDecimal v1) {
		return v0.subtract(v1).abs();
	}

	private BigDecimal aspectRatioFromImage(ImageArea image) {
		BigDecimal height = BigDecimal.valueOf(image.height());
		BigDecimal width = BigDecimal.valueOf(image.width());
		return width.divide(height, mc);
	}

	private boolean widthIsGiven() {
		return topLeft.isCxSet() && bottomRight.isCxSet();
	}

	/**
	 * Adapt aspect ratio of calculation area to the aspect ratio of the image given
	 * by recalculating one of the point coordinates in to left or bottom right
	 * coordinate.
	 * <p>
	 * Set one of the coordinates to <code>Double.NaN</code> to define the
	 * coordinate to be adapted.
	 * <p>
	 * Default parameter to be adapted is bottom right y.
	 * 
	 * @param image The image to adapt the aspect ratio to.
	 * @throws IllegalArgumentException if more than one parameter is set to
	 *                                  <code>Double.NaN</code>
	 */
	public void followAspectRatio(ImageArea image) {
		// Check the number of omitted parameters?
		int count = countNotSet();
		// All parameters given, recalculate bottomRight.cy
		if (count == 0) {
			bottomRight.unsetCy();
		}
		// More than one parameter omitted.
		else if (count > 1) {
			throw new IllegalArgumentException(
			    String.format("Just one calculation parameter can be omitted, but it were %s", count));
		}
		BigDecimal ratioXtoY = aspectRatioFromImage(image);
		// width is given
		if (widthIsGiven()) {
			BigDecimal height = difference(topLeft.cx(), bottomRight.cx()).divide(ratioXtoY, mc);
			if (bottomRight.isCySet()) {
				topLeft.setCy(bottomRight.cy().add(height));
			} else {
				bottomRight.setCy(topLeft.cy().subtract(height));
			}
			return;
		}
		// height is given
		BigDecimal width = difference(topLeft.cy(), bottomRight.cy()).multiply(ratioXtoY);
		if (bottomRight.isCxSet()) {
			topLeft.setCx(bottomRight.cx().subtract(width));
		} else {
			bottomRight.setCx(topLeft.cx().add(width));
		}
	}

	private BigDecimal half(BigDecimal value) {
		return value.divide(BigDecimal.valueOf(2), mc);
	}

	/**
	 * Fit the calculation area into the given image. Thus the calculation area is
	 * always completely visible.
	 * 
	 * @param image The image area to fit in.
	 */
	public void fitIn(ImageArea image) {
		BigDecimal widthCalc0 = bottomRight.cx().subtract(topLeft.cx()).abs();
		BigDecimal heightCalc0 = topLeft.cy().subtract(bottomRight.cy()).abs();
		AspectRatio aspectRatioImage = AspectRatio.of(BigDecimal.valueOf(image.width()),
		    BigDecimal.valueOf(image.height()));
		AspectRatio aspectRatioCalc = AspectRatio.of(widthCalc0, heightCalc0);
		int relation = aspectRatioImage.value().compareTo(aspectRatioCalc.value());
		// aspect ratio of image and calculation are identical
		if (relation == 0) {
			// Nothing to do here
			return;
		}
		// aspect ratio of image is wider than aspect ratio of calculation
		if (relation > 0) {
			BigDecimal widthCalc1 = heightCalc0.multiply(aspectRatioImage.value());
			bottomRight.setCx(bottomRight.cx().subtract(half(widthCalc0)).add(half(widthCalc1)));
//			bottomRight.setCx(bottomRight.cx() - (widthCalc0 / 2) + (widthCalc1 / 2));
			topLeft.setCx(topLeft.cx().add(half(widthCalc0)).subtract(half(widthCalc1)));
//			topLeft.setCx(topLeft.cx() + (widthCalc0 / 2) - (widthCalc1 / 2));
		} else {
			// aspect ratio of image is higher than aspect ratio of calculation
			BigDecimal heightCalc1 = widthCalc0.divide(aspectRatioImage.value(), mc);
			topLeft.setCy(topLeft.cy().subtract(half(heightCalc0)).add(half(heightCalc1)));
//			topLeft.setCy(topLeft.cy() - (heightCalc0 / 2) + (heightCalc1 / 2));
			bottomRight.setCy(bottomRight.cy().add(half(heightCalc0)).subtract(half(heightCalc1)));
//			bottomRight.setCy(bottomRight.cy() + (heightCalc0 / 2) - (heightCalc1 / 2));
		}
	}

	/**
	 * Calculates the point on the calculation area in respect to the position of
	 * the point on the image.
	 * <p>
	 * This method does NOT respect the aspect ratio. The corners of the image are
	 * considered to be the corners of the calculation area.
	 * 
	 * @param topLeft     The top left point of the calculation area
	 * @param bottomRight The bottom right point of the calculation area
	 * @param image       The width and height of the image.
	 * @param imgPoint    The requested position on the image
	 * @return The calculation point in respect to the point on the image
	 */
	public MandelbrotPointPosition calculatePointFromImagePosition(ImageArea image, Point imgPoint) {
		BigDecimal xrel = BigDecimal.valueOf(imgPoint.getX() / image.width());
		BigDecimal yrel = BigDecimal.valueOf(imgPoint.getY() / image.height());
		BigDecimal xdiff = bottomRight.cx().subtract(topLeft.cx());
		BigDecimal ydiff = topLeft.cy().subtract(bottomRight.cy());

		BigDecimal cx = topLeft.cx().add(xdiff.multiply(xrel));
		BigDecimal cy = topLeft.cy().subtract(ydiff.multiply(yrel));
//		double cx = topLeft.cx() + ((bottomRight.cx() - topLeft.cx()) * (imgPoint.getX() / image.width()));
//		double cy = topLeft.cy() - ((topLeft.cy() - bottomRight.cy()) * (imgPoint.getY() / image.height()));
		return MandelbrotPointPosition.of(cx, cy);
	}

	/**
	 * Gets the coordinates of the center point of this calculation.
	 * 
	 * @return The center point
	 */
	public MandelbrotPointPosition getCenterPoint() {
		BigDecimal halfWidth = topLeft.cx().subtract(bottomRight.cx()).divide(BigDecimal.valueOf(2));
		BigDecimal halfHeight = topLeft.cy().subtract(bottomRight.cy()).divide(BigDecimal.valueOf(2));
		BigDecimal cx = topLeft.cx().subtract(halfWidth);
//		double cx = topLeft.cx() - ((topLeft.cx() - bottomRight.cx()) / 2);
		BigDecimal cy = topLeft.cy().subtract(halfHeight);
//		double cy = topLeft.cy() - ((topLeft.cy() - bottomRight.cy()) / 2);
		return MandelbrotPointPosition.of(cx, cy);
	}

	/**
	 * 
	 * @return The width of the calculation area.
	 */
	public BigDecimal width() {
		return bottomRight.cx().subtract(topLeft.cx());
	}

	/**
	 * 
	 * @return The height of the calculation area.
	 */
	public BigDecimal height() {
		return topLeft.cy().subtract(bottomRight.cy());
	}

	/**
	 * Zooms in or out the calculation where the center parameter defines the
	 * midpoint of the zoom area.
	 * 
	 * @param factor The zoom factor, where factor < 1 means to zoom in, and > 1 to
	 *               zoom out.
	 * @param mouse  The center point of the zoom.
	 */
	public CalculationArea zoom(double factor, MandelbrotPointPosition mouse) {
		BigDecimal width = width();
		BigDecimal height = height();
		// First get relative position of the mouse point
		// Relation to topLeft
		BigDecimal rX1 = (mouse.cx().subtract(topLeft.cx())).divide(width);
		BigDecimal rY1 = (topLeft.cy().subtract(mouse.cy())).divide(height);
		// Relation to bottomRight
		BigDecimal rX2 = (bottomRight.cx().subtract(mouse.cx())).divide(width);
		BigDecimal rY2 = (mouse.cy().subtract(bottomRight.cy())).divide(height);

		// Now move the result in relation as calculated above.
		BigDecimal width1 = width.multiply(BigDecimal.valueOf(factor));
		BigDecimal height1 = height.multiply(BigDecimal.valueOf(factor));
		topLeft.moveTo(mouse.cx().subtract((rX1.multiply(width1))), mouse.cy().add((rY1.multiply(height1))));
		bottomRight.moveTo(mouse.cx().add((rX2.multiply(width1))), mouse.cy().subtract((rY2.multiply(height1))));
		return this;
	}

	public CalculationArea move(MandelbrotPointPosition delta) {
		topLeft.move(delta);
		bottomRight.move(delta);
		return this;
	}

	/**
	 * @return the topLeft
	 */
	public MandelbrotPointPosition topLeft() {
		return topLeft;
	}

	/**
	 * @return the bottomRight
	 */
	public MandelbrotPointPosition bottomRight() {
		return bottomRight;
	}

	public CalculationArea cloneValues() {
		return CalculationArea.of(MandelbrotPointPosition.of(topLeft), MandelbrotPointPosition.of(bottomRight));
	}

	@Override
	public String toString() {
		return "CalculationArea [topLeft=" + topLeft + ", bottomRight=" + bottomRight + "]";
	}

}
