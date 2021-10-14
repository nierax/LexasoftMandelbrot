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
import static org.mockito.Mockito.verify;

import java.awt.Rectangle;
import java.util.stream.Stream;

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
class DrawCalculationAreaControllerTest {

	class CUT extends DrawCalculationAreaController {

		public CUT(DrawCalculationAreaPanel view) {
			super(view);
		}

		@Override
		Rectangle createRectangle() {
			return rectangle;
		}

	}

	private CUT cut;
	@Mock
	private DrawCalculationAreaPanel view;
	private Rectangle rectangle;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new CUT(view);
		rectangle = new Rectangle();
	}

	private static CalculationAreaControllerModel createModel(double ctlX, double ctlY, double cbrX, double cbrY,
	    double atlX, double atlY, double abrX, double abrY, int imageWidth, int imageHeight) {
		return new CalculationAreaControllerModel() {

			@Override
			public ImageArea image() {
				return ImageArea.of(imageWidth, imageHeight);
			}

			@Override
			public CalculationArea calculation() {
				return CalculationArea.of(MandelbrotPointPosition.of(ctlX, ctlY), MandelbrotPointPosition.of(cbrX, cbrY));
			}

			@Override
			public CalculationArea total() {
				return CalculationArea.of(MandelbrotPointPosition.of(atlX, atlY), MandelbrotPointPosition.of(abrX, abrY));
			}

		};
	}

	private static Rectangle createResult(int x, int y, int width, int height) {
		Rectangle rect = new Rectangle();
		rect.setBounds(x, y, width, height);
		return rect;
	}

	private static Stream<Arguments> testCalculationAreaModelChanged() {
		return Stream.of(
		    // width dimension half size
		    Arguments.of(createModel(-0.5, 0.5, 0.5, -0.5, -1, 0.5, 1, -0.5, 200, 100), createResult(50, 0, 100, 100)),
		    // both dimensions same values
		    Arguments.of(createModel(-1, 0.5, 1, -0.5, -1, 0.5, 1, -0.5, 200, 100), createResult(0, 0, 200, 100)),
		    // 2/3 in width, 1/2 in height
		    Arguments.of(createModel(-0.8, 0.5, 0.8, -0.5, -1.2, 1, 1.2, -1, 300, 200), createResult(50, 50, 200, 100)));
	}

	/**
	 * Is the right rectangle given to the view and are the border values of the
	 * rectangle correctly?
	 * <p>
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.DrawCalculationAreaController#calculationAreaModelChanged(de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCalculationAreaModelChanged(CalculationAreaControllerModel input, Rectangle expectedBounds) {
		// run
		cut.calculationAreaModelChanged(input);

		// check
		verify(view).drawRect(rectangle);
		assertEquals(expectedBounds, rectangle);
	}

}
