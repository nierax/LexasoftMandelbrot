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
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.util.stream.Stream;

import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class ZoomControllerTest {

	class TestListener implements ModelChangedListener<CalculationArea> {

		private CalculationArea calculation;

		@Override
		public void modelChanged(ModelChangedEvent<CalculationArea> event) {
			this.calculation = event.getModel();
		}

	}

	private ZoomController cut;
	@Mock
	private JPanel view;
	@Mock
	private MouseWheelEvent event;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ZoomController(view);
	}

	private static MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	private static CalculationAreaControllerModel model(double calcTlcx, double calcTlcy, double calcBrcx,
	    double calcBrcy, double totalTlcx, double totalTlcy, double totalBrcx, double totalBrcy, int imgWidth,
	    int imgHeight) {
		return new CalculationAreaControllerModel() {

			@Override
			public CalculationArea total() {
				return CalculationArea.of(point(totalTlcx, totalTlcy), point(totalBrcx, totalBrcy));
			}

			@Override
			public CalculationArea calculation() {
				return CalculationArea.of(point(calcTlcx, calcTlcy), point(calcBrcx, calcBrcy));
			}

			@Override
			public ImageArea image() {
				return ImageArea.of(imgWidth, imgHeight);
			}

		};
	}

	private static Stream<Arguments> testMouseWheelMoved() {
		return Stream.of( //
		    // Zoom in in center for one step
		    Arguments.of(model(-2.02, 1.2, 0.8, -1.2, -2.2, 1.5, 1, -1.5, 460, 404), new Point(230, 202), -3,
		        MouseWheelEvent.WHEEL_UNIT_SCROLL, CalculationArea.of(point(-1.879, 1.08), point(0.659, -1.08))),
		    // Zoom out from center for one step
		    Arguments.of(model(-2.02, 1.2, 0.8, -1.2, -2.2, 1.5, 1, -1.5, 460, 404), new Point(230, 202), 3,
		        MouseWheelEvent.WHEEL_UNIT_SCROLL, CalculationArea.of(point(-2.161, 1.32), point(0.941, -1.32))));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.ZoomController#mouseWheelMoved(java.awt.event.MouseWheelEvent)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testMouseWheelMoved(CalculationAreaControllerModel model, Point mousePoint, int rotation, int type,
	    CalculationArea expResult) {
		// Prepare
		when(event.getScrollType()).thenReturn(type);
		when(event.getWheelRotation()).thenReturn(rotation);
		when(event.getPoint()).thenReturn(mousePoint);
		TestListener listener = new TestListener();
		cut.addModelChangedListener(listener);
		cut.modelChanged(model);
		// Run
		cut.mouseWheelMoved(event);

		// Check
		assertEquals(expResult.topLeft().cx(), listener.calculation.topLeft().cx(), 0.0001);
		assertEquals(expResult.topLeft().cy(), listener.calculation.topLeft().cy(), 0.0001);
		assertEquals(expResult.bottomRight().cx(), listener.calculation.bottomRight().cx(), 0.0001);
		assertEquals(expResult.bottomRight().cy(), listener.calculation.bottomRight().cy(), 0.0001);
	}

}
