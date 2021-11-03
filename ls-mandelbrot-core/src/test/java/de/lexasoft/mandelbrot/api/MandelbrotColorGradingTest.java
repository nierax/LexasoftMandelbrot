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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class MandelbrotColorGradingTest {

	private MandelbrotColorGrading cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotColorGrading();
	}

	private final static Stream<Arguments> _should_be_graded() {
		return Stream.of( //
		    Arguments.of(ColorGradingStyle.NONE, 0, false), //
		    Arguments.of(ColorGradingStyle.NONE, 10, false), //
		    Arguments.of(ColorGradingStyle.LINE, 0, false), //
		    Arguments.of(ColorGradingStyle.CIRCLE, 0, false), //
		    Arguments.of(ColorGradingStyle.LINE, 5, true), //
		    Arguments.of(ColorGradingStyle.CIRCLE, 5, true));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotColorGrading#shouldBeGraded()}.
	 */
	@ParameterizedTest
	@MethodSource
	final void _should_be_graded(ColorGradingStyle gradingStyle, int colorsTotal, boolean expected) {
		cut.setStyle(gradingStyle);
		cut.setColorsTotal(colorsTotal);
		assertEquals(expected, cut.shouldBeGraded());
	}

}
