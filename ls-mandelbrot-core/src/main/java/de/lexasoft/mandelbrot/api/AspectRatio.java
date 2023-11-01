/**
 * Copyright (C) 2023 admin
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.lexasoft.common.model.SimpleType;

/**
 * This value represents an aspect ratio from a width and a height.
 */
public class AspectRatio extends SimpleType<BigDecimal> {

	private AspectRatio(BigDecimal value) {
		super(value);
	}

	/**
	 * Create from a given value for the aspect ratio
	 * 
	 * @param value The aspect ratio to use
	 * @return New AspectRatio object
	 */
	public final static AspectRatio of(BigDecimal value) {
		return new AspectRatio(value);
	}

	/**
	 * Create from a given width and height.
	 * 
	 * @param width  The width of the area.
	 * @param height The height of the area.
	 * @return New AspectRatio object
	 */
	public final static AspectRatio of(BigDecimal width, BigDecimal height) {
		BigDecimal value = width.divide(height, 200, RoundingMode.HALF_UP);
		return of(value);
	}

	/**
	 * Create from given positions in a Mandelbrot calculation
	 * 
	 * @param topLeft
	 * @param bottomRight
	 * @return
	 */
	public final static AspectRatio of(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight) {
		BigDecimal width = topLeft.cx().subtract(bottomRight.cx()).abs();
		BigDecimal height = topLeft.cy().subtract(bottomRight.cy()).abs();
		return of(width, height);
	}
}
