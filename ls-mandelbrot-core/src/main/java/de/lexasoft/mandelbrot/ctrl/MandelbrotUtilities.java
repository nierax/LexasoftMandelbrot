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
package de.lexasoft.mandelbrot.ctrl;

import java.awt.Point;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * This class offers some utility calculations to ease the handling of
 * calculation parameters
 * 
 * @author nierax
 *
 */
public class MandelbrotUtilities {

	/**
	 * Use the of() method only
	 */
	private MandelbrotUtilities() {
	}

	/**
	 * @return A new instance of MandelbrotUtilities
	 */
	public static final MandelbrotUtilities of() {
		return new MandelbrotUtilities();
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
	 * @param imgDim      The width and height of the image.
	 * @param imgPoint    The requested position on the image
	 * @return The calculation point in respect to the point on the image
	 */
	public MandelbrotPointPosition calculatePointFromImagePosition(MandelbrotPointPosition topLeft,
	    MandelbrotPointPosition bottomRight, ImageArea imgDim, Point imgPoint) {
		return CalculationArea.of(topLeft, bottomRight).calculatePointFromImagePosition(imgDim, imgPoint);
	}

	/**
	 * Calculates the point on the calculation area in respect to the position of
	 * the point on the image.
	 * <p>
	 * This method does respect the aspect ratio between image and calculation. It
	 * will be corrected in respect to the @AspectRatioHandle
	 * 
	 * @param calc     The calculation area
	 * @param image    The image
	 * @param imgPoint The point in respect to the image
	 * @param arHandle The aspect ratio method used to ensure, calculation area and
	 *                 image area has the same aspect ratio.
	 * @return The Mandelbrot coordinates of the given point in the image
	 */
	public MandelbrotPointPosition calculatePointFromImagePosition(CalculationArea calc, ImageArea image, Point imgPoint,
	    AspectRatioHandle arHandle) {
		switch (arHandle) {
		case FITIN:
			calc.fitIn(image);
			break;
		case FOLLOW_IMAGE:
			calc.followAspectRatio(image);
		case FOLLOW_CALCULATION: {
			image.followAspectRatio(calc);
			break;
		}
		default:
		}
		return calc.calculatePointFromImagePosition(image, imgPoint);
	}

}
