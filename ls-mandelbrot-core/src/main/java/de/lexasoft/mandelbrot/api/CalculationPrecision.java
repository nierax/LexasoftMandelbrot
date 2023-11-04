/**
 * Copyright (C) 2023 nierax
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

import java.math.MathContext;
import java.math.RoundingMode;

import de.lexasoft.common.model.SimpleType;

/**
 * 
 * @author nierax
 */
public class CalculationPrecision extends SimpleType<MathContext> {

	/**
	 * 
	 */
	private CalculationPrecision(int precision, RoundingMode rounding) {
		super(new MathContext(precision, rounding));
	}

	/**
	 * Create from given parameters
	 * 
	 * @param precision
	 * @param rounding
	 * @return New Instance with Calculation precision.
	 */
	public static final CalculationPrecision of(int precision, RoundingMode rounding) {
		return new CalculationPrecision(precision, rounding);
	}

	/**
	 * Create from standard values.
	 * 
	 * @return New Instance with Calculation precision.
	 */
	public static final CalculationPrecision of() {
		return of(20, RoundingMode.HALF_UP);
	}

}
