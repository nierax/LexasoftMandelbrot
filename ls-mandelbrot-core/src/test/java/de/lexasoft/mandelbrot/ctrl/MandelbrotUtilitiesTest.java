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
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
 *
 */
class MandelbrotUtilitiesTest {

	private MandelbrotUtilities cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = MandelbrotUtilities.of();
	}

	private static MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	private final static Stream<Arguments> testCalculatePointFromImagePosition() {
		return Stream.of(
		    // Ignore the aspect ratio
		    Arguments.of(CalculationArea.of(point(-2.02, 1.2), point(0.8, -1.2)), ImageArea.of(459, 405), new Point(0, 0),
		        AspectRatioHandle.IGNORE, point(-2.02, 1.2)),
		    // Fit the calculation into the image
		    Arguments.of(CalculationArea.of(point(-2.02, 1.2), point(0.8, -1.2)), ImageArea.of(300, 200), new Point(0, 0),
		        AspectRatioHandle.FITIN, point(-2.41, 1.2)),
		    Arguments.of(CalculationArea.of(point(-2.02, 1.2), point(0.8, -1.2)), ImageArea.of(300, 200),
		        new Point(150, 100), AspectRatioHandle.FITIN, point(-0.61, 0)),
		    // Follow image
		    Arguments.of(CalculationArea.of(point(-2.02, 1.2), point(0.8, -1.2)), ImageArea.of(459, 405), new Point(0, 0),
		        AspectRatioHandle.FOLLOW_IMAGE, point(-2.02, 1.2)),
		    // Follow calculation
		    Arguments.of(CalculationArea.of(point(-2.02, 1.2), point(0.8, -1.2)), ImageArea.of(459, 405), new Point(0, 0),
		        AspectRatioHandle.FOLLOW_CALCULATION, point(-2.02, 1.2)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotUtilities#calculatePointFromImagePosition(de.lexasoft.mandelbrot.api.CalculationArea, de.lexasoft.mandelbrot.api.ImageArea, java.awt.Point, de.lexasoft.mandelbrot.api.AspectRatioHandle)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCalculatePointFromImagePosition(CalculationArea calc, ImageArea img, Point point, AspectRatioHandle ar,
	    MandelbrotPointPosition expected) {
		// Run
		MandelbrotPointPosition result = cut.calculatePointFromImagePosition(calc, img, point, ar);
		// Check
		assertEquals(expected.cx(), result.cx(), 0.00001);
		assertEquals(expected.cy(), result.cy(), 0.00001);
	}

}
