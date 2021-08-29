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
package de.lexasoft.mandelbrot.swing.model;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * Indicated the attributes, needed to draw the rectangle, showing the real
 * calculation area.
 * 
 * @author nierax
 *
 */
public interface CalculationAreaControllerModel extends ControllerModel {

	/**
	 * The top left corner, given by the user to calculate.
	 * 
	 * @return Position of top left corner
	 */
	MandelbrotPointPosition calcTopLeft();

	/**
	 * The bottom right corner, given by the user to calculate.
	 * 
	 * @return Position of bottom right corner
	 */
	MandelbrotPointPosition calcBottomRight();

	/**
	 * The adopted top left corner, the image was calculated in.
	 * 
	 * @return Position of adopted top left corner
	 */
	MandelbrotPointPosition adoptTopLeft();

	/**
	 * The adopted bottom right corner, the image was calculated in.
	 * 
	 * @return Position of adopted bottom right corner
	 */
	MandelbrotPointPosition adoptBottomRight();

	/**
	 * The width of the image in pixel.
	 * 
	 * @return The width of the image.
	 */
	int imageWidth();

	/**
	 * The height of the image in pixel.
	 * 
	 * @return The height of the image.
	 */
	int imageHeight();

}
