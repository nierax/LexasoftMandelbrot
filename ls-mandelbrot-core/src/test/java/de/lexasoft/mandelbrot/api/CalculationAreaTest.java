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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class CalculationAreaTest {

	private CalculationArea cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Make the creation of the point a little bit shorter.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static MandelbrotPointPosition point(double x, double y) {
		return MandelbrotPointPosition.of(x, y);
	}

	private static Stream<Arguments> testFollowAspectRatioOk() {
		return Stream.of(
		    // Bottom right x is sought-after
		    Arguments.of(point(-2.02, 1.2), point(Double.NaN, -1.2), ImageArea.of(459, 405), point(-2.02, 1.2),
		        point(0.7, -1.2)),
		    // Bottom right y is sought-after
		    Arguments.of(point(-2.02, 1.2), point(0.7, Double.NaN), ImageArea.of(459, 405), point(-2.02, 1.2),
		        point(0.7, -1.2)),
		    // Top left x is sought-after
		    Arguments.of(point(Double.NaN, 1.2), point(0.7, -1.2), ImageArea.of(459, 405), point(-2.02, 1.2),
		        point(0.7, -1.2)),
		    // Top left y is sought-after
		    Arguments.of(point(-2.02, Double.NaN), point(0.7, -1.2), ImageArea.of(459, 405), point(-2.02, 1.2),
		        point(0.7, -1.2)),
		    // All parameters given -> bottomRight.cy will be recalculated
		    Arguments.of(point(-2.02, 1.2), point(0.7, -0.8), ImageArea.of(459, 405), point(-2.02, 1.2), point(0.7, -1.2)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.CalculationArea#followAspectRatio(de.lexasoft.mandelbrot.api.ImageArea)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testFollowAspectRatioOk(MandelbrotPointPosition tl, MandelbrotPointPosition br, ImageArea image,
	    MandelbrotPointPosition expTl, MandelbrotPointPosition expBr) {
		cut = new CalculationArea(tl, br);
		// Run
		cut.followAspectRatio(image);
		// Check
		assertEquals(expTl.cx(), cut.topLeft().cx(), 0.000001);
		assertEquals(expTl.cy(), cut.topLeft().cy(), 0.000001);
		assertEquals(expBr.cx(), cut.bottomRight().cx(), 0.00001);
		assertEquals(expBr.cy(), cut.bottomRight().cy(), 0.00001);
	}

	private static final Stream<Arguments> testFollowAspectRatioTooManyNaN() {
		return Stream.of(Arguments.of(point(-2.02, 1.2), point(Double.NaN, Double.NaN)),
		    Arguments.of(point(-2.02, Double.NaN), point(0.8, Double.NaN)),
		    Arguments.of(point(Double.NaN, Double.NaN), point(0.8, Double.NaN)));
	}

	/**
	 * Make sure, not more than one parameter is NaN
	 */
	@ParameterizedTest
	@MethodSource
	final void testFollowAspectRatioTooManyNaN(MandelbrotPointPosition tl, MandelbrotPointPosition br) {
		cut = new CalculationArea(tl, br);
		assertThrows(IllegalArgumentException.class, () -> cut.followAspectRatio(ImageArea.of(459, 405)));
	}

	private static final Stream<Arguments> testFitIn() {
		return Stream.of(
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), ImageArea.of(459, 405), point(-2.02, 1.2), point(0.7, -1.2)),
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), ImageArea.of(720, 405), point(-2.7933, 1.2),
		        point(1.4733, -1.2)),
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), ImageArea.of(459, 459), point(-2.02, 1.36),
		        point(0.7, -1.36)),
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), ImageArea.of(459, 800), point(-2.02, 2.3704),
		        point(0.7, -2.3704)));
	}

	/**
	 * Test method for {@link CalculationArea#fitIn(ImageArea)}
	 * 
	 * @param tl    Top left position for the test candidate.
	 * @param br    Bottom right position for the test candidate.
	 * @param image Image to fit the calculation in.
	 * @param expTl Expected top left point of the calculation area.
	 * @param expBr Expected bottom right point of the calculation area.
	 */
	@ParameterizedTest
	@MethodSource
	final void testFitIn(MandelbrotPointPosition tl, MandelbrotPointPosition br, ImageArea image,
	    MandelbrotPointPosition expTl, MandelbrotPointPosition expBr) {
		cut = new CalculationArea(tl, br);
		// Run
		cut.fitIn(image);
		// Check
		assertEquals(expTl.cx(), cut.topLeft().cx(), 0.0001);
		assertEquals(expTl.cy(), cut.topLeft().cy(), 0.0001);
		assertEquals(expBr.cx(), cut.bottomRight().cx(), 0.0001);
		assertEquals(expBr.cy(), cut.bottomRight().cy(), 0.0001);
	}

}
