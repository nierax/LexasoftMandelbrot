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
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
 *
 */
class MandelbrotCoreUtilitiesTest {

	MandelbrotCoreUtilities cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotCoreUtilities();
	}

	private static MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	private static final Stream<Arguments> testCalculateAspectRatioForImage() {
		return Stream.of(
		    // Image width given, image height calculated
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), new Dimension(4590, 0), new Dimension(4590, 4050)),
		    // Image height given, image width calculated
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), new Dimension(0, 4050), new Dimension(4590, 4050)),
		    // Image width given, image height calculated with cx both points in minus
		    Arguments.of(point(-4, 1), point(-1, -1), new Dimension(3000, 0), new Dimension(3000, 2000)),
		    // Image width given, image height calculated with cx both points in plus
		    Arguments.of(point(1, 1), point(4, -1), new Dimension(3000, 0), new Dimension(3000, 2000)),
		    // Both dimensions given => height should be recalculated
		    Arguments.of(point(1, 1), point(4, -1), new Dimension(3000, 1000), new Dimension(3000, 2000)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotCoreUtilities#calculateAspectRatioForImage(de.lexasoft.mandelbrot.api.MandelbrotPointPosition, de.lexasoft.mandelbrot.api.MandelbrotPointPosition, java.awt.Dimension)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCalculateAspectRatioForImage(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    Dimension imgBoundaries, Dimension expectedBoundaries) {
		// Run
		Dimension result = cut.calculateAspectRatioForImage(topLeft, bottomRight, imgBoundaries);
		// Check
		assertEquals(expectedBoundaries, result);
	}

}
