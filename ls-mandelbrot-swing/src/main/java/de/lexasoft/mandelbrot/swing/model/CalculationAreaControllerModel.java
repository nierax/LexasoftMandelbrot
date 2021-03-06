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

import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * Indicated the attributes, needed to draw the rectangle, showing the real
 * calculation area.
 * 
 * @author nierax
 *
 */
public interface CalculationAreaControllerModel extends ControllerModel {

	/**
	 * The area, defining the original calculation.
	 * 
	 * @return
	 */
	CalculationArea calculation();

	/**
	 * The complete area, shown.
	 * 
	 * @return
	 */
	CalculationArea total();

	/**
	 * Image in which the calculation is shown
	 * 
	 * @return
	 */
	ImageArea image();

}
