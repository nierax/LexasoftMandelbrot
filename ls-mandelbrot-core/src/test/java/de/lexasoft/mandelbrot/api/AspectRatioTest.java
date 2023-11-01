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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test for aspect ratio value
 */
class AspectRatioTest {

	private BigDecimal value1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		value1 = BigDecimal.valueOf(1);
	}

	private static BigDecimal value(double value) {
		return BigDecimal.valueOf(value);
	}

	private static MandelbrotPointPosition point(double x, double y) {
		return MandelbrotPointPosition.of(value(x), value(y));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.AspectRatio#of(java.math.BigDecimal)}.
	 */
	@Test
	final void test_of_simple_value() {
		AspectRatio cut = AspectRatio.of(value1);
		assertEquals(value1, cut.value());
	}

	private static final Stream<Arguments> test_of_width_height() {
		return Stream.of(//
		    Arguments.of(value(1), value(2), value(0.5)), //
		    Arguments.of(value(2), value(2), value(1)), //
		    Arguments.of(value(4), value(3), value(1.333333)), //
		    Arguments.of(value(3), value(4), value(0.75)));
	}

	@ParameterizedTest
	@MethodSource
	final void test_of_width_height(BigDecimal width, BigDecimal height, BigDecimal expected) {
		AspectRatio cut = AspectRatio.of(width, height);
		assertEquals(expected.doubleValue(), cut.value().doubleValue(), 2);
	}

	private static final Stream<Arguments> test_of_2_points() {
		return Stream.of(//
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), value(1.13333)), //
		    Arguments.of(point(-2.7933, 1.2), point(1.4733, -1.2), value(1.77775)));
	}

	@ParameterizedTest
	@MethodSource
	final void test_of_2_points(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    BigDecimal expected) {
		AspectRatio cut = AspectRatio.of(topLeft, bottomRight);
		assertEquals(expected.doubleValue(), cut.value().doubleValue(), 2);
	}

}
