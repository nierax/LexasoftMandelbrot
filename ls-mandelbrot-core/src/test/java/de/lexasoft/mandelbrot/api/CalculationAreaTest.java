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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Point;
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
		cut = CalculationArea.of(tl, br);
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
		cut = CalculationArea.of(tl, br);
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
		cut = CalculationArea.of(tl, br);
		// Run
		cut.fitIn(image);
		// Check
		assertEquals(expTl.cx(), cut.topLeft().cx(), 0.0001);
		assertEquals(expTl.cy(), cut.topLeft().cy(), 0.0001);
		assertEquals(expBr.cx(), cut.bottomRight().cx(), 0.0001);
		assertEquals(expBr.cy(), cut.bottomRight().cy(), 0.0001);
	}

	private static final Stream<Arguments> testCalculatePointFromImagePosition() {
		return Stream.of(
		    // Top left
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), ImageArea.of(459, 405), new Point(0, 0), point(-2.02, 1.2)),
		    // Bottom right
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), ImageArea.of(459, 405), new Point(459, 405),
		        point(0.8, -1.2)),
		    // Center
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), ImageArea.of(458, 404), new Point(229, 202), point(-0.61, 0)),
		    // Top right
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), ImageArea.of(459, 405), new Point(459, 0), point(0.8, 1.2)),
		    // Bottom Left
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), ImageArea.of(459, 405), new Point(0, 405),
		        point(-2.02, -1.2)),
		    // 1/3 x, 2/3 y
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), ImageArea.of(459, 405), new Point(153, 270),
		        point(-1.08, -0.4)));

	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.MandelbrotUtilities#calculatePointFromImagePosition(de.lexasoft.mandelbrot.api.MandelbrotPointPosition, de.lexasoft.mandelbrot.api.MandelbrotPointPosition, java.awt.Dimension, de.lexasoft.common.math.Point)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCalculatePointFromImagePosition(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    ImageArea imgDim, Point imgPoint, MandelbrotPointPosition expected) {
		// Prepare
		cut = CalculationArea.of(topLeft, bottomRight);
		// Run
		MandelbrotPointPosition result = cut.calculatePointFromImagePosition(imgDim, imgPoint);
		// Check
		assertNotNull(result);
		assertEquals(expected.cx(), result.cx(), 0.00001);
		assertEquals(expected.cy(), result.cy(), 0.00001);
	}

	private static final Stream<Arguments> testZoom() {
		return Stream.of(
		    // Zooming in with center in the middle.
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), 0.9, point(-0.61, 0), point(-1.879, 1.08),
		        point(0.659, -1.08)),
		    // Zooming out with center in the middle.
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), 1.1, point(-0.61, 0), point(-2.161, 1.32),
		        point(0.941, -1.32)),
		    // Zooming in with center in the top left corner.
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), 0.9, point(-2.02, 1.2), point(-2.02, 1.2),
		        point(0.518, -0.96)),
		    // Zooming in with center in the top right corner.
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), 0.9, point(0.8, 1.2), point(-1.738, 1.2), point(0.8, -0.96)),
		    // Zooming in with center in the bottom right corner.
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), 0.9, point(0.8, -1.2), point(-1.738, 0.96), point(0.8, -1.2)),
		    // Zooming in with center in the bottom left corner
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), 0.9, point(-2.02, -1.2), point(-2.02, 0.96),
		        point(0.518, -1.2)));
	}

	/**
	 * Tests the zoom functionality.
	 * 
	 * @param tl
	 * @param br
	 * @param factor
	 * @param center
	 * @param expTl
	 * @param expBr
	 */
	@ParameterizedTest
	@MethodSource
	final void testZoom(MandelbrotPointPosition tl, MandelbrotPointPosition br, double factor,
	    MandelbrotPointPosition center, MandelbrotPointPosition expTl, MandelbrotPointPosition expBr) {
		// Prepare
		cut = CalculationArea.of(tl, br);
		// Run
		cut.zoom(factor, center);
		// Check
		assertEquals(expTl.cx(), cut.topLeft().cx(), 0.00001);
		assertEquals(expTl.cy(), cut.topLeft().cy(), 0.00001);
		assertEquals(expBr.cx(), cut.bottomRight().cx(), 0.00001);
		assertEquals(expBr.cy(), cut.bottomRight().cy(), 0.00001);
	}

	private static final Stream<Arguments> testGetCenterPoint() {
		return Stream.of(//
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), point(-0.61, 0)),
		    Arguments.of(point(-1, 1), point(1, -1), point(0, 0)),
		    Arguments.of(point(-1.81, 1.08), point(0.72, -1.08), point(-0.545, 0)),
		    Arguments.of(point(-1.01, 0.6), point(0.4, -0.6), point(-0.305, 0)));
	}

	@ParameterizedTest
	@MethodSource
	final void testGetCenterPoint(MandelbrotPointPosition tl, MandelbrotPointPosition br,
	    MandelbrotPointPosition expCenter) {
		// Prepare
		cut = CalculationArea.of(tl, br);
		// Run
		MandelbrotPointPosition center = cut.getCenterPoint();
		// Check
		assertEquals(expCenter.cx(), center.cx(), 0.0001);
		assertEquals(expCenter.cy(), center.cy(), 0.0001);
	}

	private final static Stream<Arguments> testMove() {
		return Stream.of( //
		    // Move to right and up
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), point(0.5, 1), point(-1.52, 2.2), point(1.3, -0.2)),
		    // Move to left and up
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), point(-0.1, 0.2), point(-2.12, 1.4), point(0.7, -1)),
		    // Move to left and down
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), point(-0.1, -0.2), point(-2.12, 1), point(0.7, -1.4)),
		    // Move to right and down
		    Arguments.of(point(-2.02, 1.2), point(0.8, -1.2), point(0.1, -0.2), point(-1.92, 1), point(0.9, -1.4)));
	}

	/**
	 * 
	 * @param tl    Initial top left position
	 * @param br    Initial bottom right position
	 * @param delta Delta by which the calculation area should be moved
	 * @param expTl Expected top left position
	 * @param expBr Expected bottom right position
	 */
	@ParameterizedTest
	@MethodSource
	final void testMove(MandelbrotPointPosition tl, MandelbrotPointPosition br, MandelbrotPointPosition delta,
	    MandelbrotPointPosition expTl, MandelbrotPointPosition expBr) {
		// Prepare
		cut = CalculationArea.of(tl, br);
		// Run
		CalculationArea result = cut.move(delta);
		// Check
		assertSame(cut, result);
		assertEquals(expTl.cx(), result.topLeft().cx(), 0.0001);
		assertEquals(expTl.cy(), result.topLeft().cy(), 0.0001);
		assertEquals(expBr.cx(), result.bottomRight().cx(), 0.0001);
		assertEquals(expBr.cy(), result.bottomRight().cy(), 0.0001);
	}

}
