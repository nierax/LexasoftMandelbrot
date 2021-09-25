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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Dimension;
import java.awt.Point;
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
class MandelbrotUtilitiesTest {

	private MandelbrotUtilities cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = MandelbrotUtilities.of();
	}

	private static MandelbrotPointPosition point(double x, double y) {
		return MandelbrotPointPosition.of(x, y);
	}

	private static final Stream<Arguments> testCalculatePointFromImagePosition() {
		return Stream.of(
		    // Top left
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), new Dimension(459, 405), new Point(0, 0), point(-2.02, 1.2)),
		    // Bottom right
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), new Dimension(459, 405), new Point(459, 405),
		        point(0.8, -1.2)),
		    // Center
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), new Dimension(458, 404), new Point(229, 202),
		        point(-0.61, 0)),
		    // Top right
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), new Dimension(459, 405), new Point(459, 0), point(0.8, 1.2)),
		    // Bottom Left
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), new Dimension(459, 405), new Point(0, 405),
		        point(-2.02, -1.2)),
		    // 1/3 x, 2/3 y
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), new Dimension(459, 405), new Point(153, 270),
		        point(-1.08, -0.4)));

	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotUtilities#calculatePointFromImagePosition(de.lexasoft.mandelbrot.api.MandelbrotPointPosition, de.lexasoft.mandelbrot.api.MandelbrotPointPosition, java.awt.Dimension, de.lexasoft.common.math.Point)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCalculatePointFromImagePosition(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    Dimension imgDim, Point imgPoint, MandelbrotPointPosition expected) {
		// Run
		MandelbrotPointPosition result = cut.calculatePointFromImagePosition(topLeft, bottomRight, imgDim, imgPoint);
		// Check
		assertNotNull(result);
		assertEquals(expected.cx(), result.cx(), 0.00001);
		assertEquals(expected.cy(), result.cy(), 0.00001);
	}

}
