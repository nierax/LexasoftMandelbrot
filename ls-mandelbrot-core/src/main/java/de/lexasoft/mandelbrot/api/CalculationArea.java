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

/**
 * Represents the area of a calculation.
 * 
 * @author nierax
 *
 */
public class CalculationArea {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;

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

	private int countNaN() {
		int count = 0;
		if (Double.isNaN(topLeft.cx())) {
			count++;
		}
		if (Double.isNaN(topLeft.cy())) {
			count++;
		}
		if (Double.isNaN(bottomRight.cx())) {
			count++;
		}
		if (Double.isNaN(bottomRight.cy())) {
			count++;
		}
		return count;
	}

	private double difference(double v0, double v1) {
		return Math.abs(v0 - v1);
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
		int count = countNaN();
		// All parameters given, recalculate bottomRight.cy
		if (count == 0) {
			bottomRight.setCy(Double.NaN);
		}
		// More than one parameter omitted.
		else if (count > 1) {
			throw new IllegalArgumentException(
			    String.format("Just one calculation parameter can be omitted, but it were %s", count));
		}
		double ratioXtoY = (double) image.width() / (double) image.height();
		// width is given
		if (!Double.isNaN(topLeft.cx()) && !Double.isNaN(bottomRight.cx())) {
			double height = difference(topLeft.cx(), bottomRight.cx()) / ratioXtoY;
			if (Double.isNaN(bottomRight.cy())) {
				bottomRight.setCy(topLeft.cy() - height);
			} else {
				topLeft.setCy(bottomRight.cy() + height);
			}
			return;
		}
		// height is given
		double width = difference(topLeft.cy(), bottomRight.cy()) * ratioXtoY;
		if (Double.isNaN(bottomRight.cx())) {
			bottomRight.setCx(topLeft.cx() + width);
		} else {
			topLeft.setCx(bottomRight.cx() - width);
		}
	}

	/**
	 * Fit the calculation area into the given image. Thus the calculation area is
	 * always completely visible.
	 * 
	 * @param image The image area to fit in.
	 */
	public void fitIn(ImageArea image) {
		double widthCalc0 = Math.abs(bottomRight.cx() - topLeft.cx());
		double heightCalc0 = Math.abs(topLeft.cy() - bottomRight.cy());
		double aspectRatioImage = (double) image.width() / (double) image.height();
		double aspectRatioCalc = widthCalc0 / heightCalc0;
		int relation = Double.compare(aspectRatioImage, aspectRatioCalc);
		// aspect ratio of image and calculation are identical
		if (relation == 0) {
			// Nothing to do here
			return;
		}
		// aspect ratio of image is wider than aspect ratio of calculation
		if (relation > 0) {
			double widthCalc1 = heightCalc0 * aspectRatioImage;
			bottomRight.setCx(bottomRight.cx() - (widthCalc0 / 2) + (widthCalc1 / 2));
			topLeft.setCx(topLeft.cx() + (widthCalc0 / 2) - (widthCalc1 / 2));
		} else {
			// aspect ratio of image is higher than aspect ratio of calculation
			double heightCalc1 = widthCalc0 / aspectRatioImage;
			topLeft.setCy(topLeft.cy() - (heightCalc0 / 2) + (heightCalc1 / 2));
			bottomRight.setCy(bottomRight.cy() + (heightCalc0 / 2) - (heightCalc1 / 2));
		}
	}

	public MandelbrotPointPosition calculatePointFromImagePosition(ImageArea image, Point imgPoint) {
		double cx = topLeft.cx() + ((bottomRight.cx() - topLeft.cx()) * (imgPoint.getX() / image.width()));
		double cy = topLeft.cy() - ((topLeft.cy() - bottomRight.cy()) * (imgPoint.getY() / image.height()));
		return MandelbrotPointPosition.of(cx, cy);
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

}
