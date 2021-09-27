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
	public CalculationArea(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight) {
		super();
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
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

}
