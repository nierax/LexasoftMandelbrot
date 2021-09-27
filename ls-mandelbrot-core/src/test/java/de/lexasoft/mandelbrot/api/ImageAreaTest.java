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
class ImageAreaTest {

	private ImageArea cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	private static CalculationArea area(double tlCx, double tlCy, double brCx, double brCy) {
		return new CalculationArea(MandelbrotPointPosition.of(tlCx, tlCy), MandelbrotPointPosition.of(brCx, brCy));
	}

	private static final Stream<Arguments> testFollowAspectRatio() {
		return Stream.of(
		    // Image width given, image height calculated
		    Arguments.of(area(-2.02, 1.2, 0.7, -1.2), 4590, 0, 4590, 4050),
		    // Image height given, image width calculated
		    Arguments.of(area(-2.02, 1.2, 0.7, -1.2), 0, 4050, 4590, 4050),
		    // Image width given, image height calculated with cx both points in minus
		    Arguments.of(area(-4, 1, -1, -1), 3000, 0, 3000, 2000),
		    // Image width given, image height calculated with cx both points in plus
		    Arguments.of(area(1, 1, 4, -1), 3000, 0, 3000, 2000),
		    // Both dimensions given => height should be recalculated
		    Arguments.of(area(1, 1, 4, -1), 3000, 1000, 3000, 2000));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.ImageArea#followAspectRatio(de.lexasoft.mandelbrot.api.CalculationArea)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testFollowAspectRatio(CalculationArea calc, int width, int height, int expWidth, int expHeight) {
		cut = new ImageArea(width, height);
		// Run
		cut.followAspectRatio(calc);

		// Check
		assertEquals(expWidth, cut.width());
		assertEquals(expHeight, cut.height());
	}

}
