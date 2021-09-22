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
package de.lexasoft.mandelbrot.swing.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.ctrl.ColorDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class APIModelFactoryTest {

	private APIModelFactory cut;

	@Mock
	private CalculationControllerModel calcModel;
	@Mock
	private ColorControllerModel colModel;
	@Mock
	private ImageControllerModel imgModel;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = APIModelFactory.of();
	}

	private void mockCalculationCM(MandelbrotPointPosition tl, MandelbrotPointPosition br, AspectRatio ar, int maxIter) {
		when(calcModel.topLeft()).thenReturn(tl);
		when(calcModel.bottomRight()).thenReturn(br);
		when(calcModel.aspectRatio()).thenReturn(ar);
		when(calcModel.maximumIterations()).thenReturn(maxIter);
	}

	private void mockColorCM(PaletteVariant pv, int nrOfCol, ColorGradingStyle gs) {
		when(colModel.paletteVariant()).thenReturn(pv);
		when(colModel.totalNrOfColors()).thenReturn(nrOfCol);
		when(colModel.gradingStyle()).thenReturn(gs);
	}

	private void mockImgCM(int imgWidth, int imgHeight, String imgFilename) {
		when(imgModel.imageWidth()).thenReturn(imgWidth);
		when(imgModel.imageHeight()).thenReturn(imgHeight);
		when(imgModel.imageFilename()).thenReturn(imgFilename);
	}

	private static MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	private static Stream<Arguments> testCreateFromCM() {
		return Stream.of(
		    Arguments.of(point(1.0, 0.8), point(-1.0, -0.8), AspectRatio.FILL, 27, PaletteVariant.BLUEWHITE, 22,
		        ColorGradingStyle.LINE, 500, 400, "any/folder/image.img"),
		    Arguments.of(point(2.05, 0.5), point(-0.8, -0.5), AspectRatio.FILL, 23, PaletteVariant.RAINBOW7, 26,
		        ColorGradingStyle.LINE, 650, 380, "any/folder/image.img"));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.model.APIModelFactory#createFromCM(de.lexasoft.mandelbrot.swing.model.CalculationControllerModel, de.lexasoft.mandelbrot.swing.model.ColorControllerModel, de.lexasoft.mandelbrot.swing.model.ImageControllerModel)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCreateFromCM(MandelbrotPointPosition tl, MandelbrotPointPosition br, AspectRatio ar, int maxIter,
	    PaletteVariant pv, int nrOfCol, ColorGradingStyle gs, int imageWidth, int imageHeight, String imgFilename) {
		mockCalculationCM(tl, br, ar, maxIter);
		mockColorCM(pv, nrOfCol, gs);
		mockImgCM(imageWidth, imageHeight, imgFilename);

		// run
		MandelbrotAttributesDTO model = cut.createFromCM(calcModel, colModel, imgModel);

		// Check Calculation
		assertNotNull(model);
		assertEquals(tl, model.getCalculation().getTopLeft());
		assertNotSame(tl, model.getCalculation().getTopLeft());
		assertEquals(br, model.getCalculation().getBottomRight());
		assertNotSame(br, model.getCalculation().getBottomRight());
		assertEquals(maxIter, model.getCalculation().getMaximumIterations());
		// Check color
		assertEquals(pv, model.getColor().getPaletteVariant());
		assertEquals(nrOfCol, model.getColor().getColorGrading().getColorsTotal());
		assertEquals(gs, model.getColor().getColorGrading().getStyle());
		assertEquals(ColorDTO.of(0, 0, 0), model.getColor().getMandelbrotColor());
		// Check image
		assertEquals(imageWidth, model.getImage().getImageWidth());
		assertEquals(imageHeight, model.getImage().getImageHeight());
		assertEquals(imgFilename, model.getImage().getImageFilename());
		assertEquals(AspectRatioHandle.FITIN, model.getImage().getAspectRatioHandle());
	}

	private static Stream<Arguments> testCreateFromCM_AspectRatio() {
		return Stream.of(Arguments.of(450, 405, AspectRatio.FILL, AspectRatioHandle.FITIN, 450, 405),
		    Arguments.of(450, 405, AspectRatio.IGNORE, AspectRatioHandle.IGNORE, 450, 405),
		    Arguments.of(450, 405, AspectRatio.AR1x1, AspectRatioHandle.FITIN, 405, 405),
		    Arguments.of(450, 505, AspectRatio.AR1x1, AspectRatioHandle.FITIN, 450, 450),
		    Arguments.of(450, 405, AspectRatio.AR16x9, AspectRatioHandle.FITIN, 450, 253),
		    Arguments.of(450, 405, AspectRatio.AR16x10, AspectRatioHandle.FITIN, 450, 281),
		    Arguments.of(450, 405, AspectRatio.AR3x2, AspectRatioHandle.FITIN, 450, 300),
		    Arguments.of(400, 405, AspectRatio.AR4x3, AspectRatioHandle.FITIN, 400, 300),
		    Arguments.of(420, 800, AspectRatio.AR3x2, AspectRatioHandle.FITIN, 420, 280));
	}

	/**
	 * Check, whether aspect ratio is handled correctly under creation of the model.
	 */
	@ParameterizedTest
	@MethodSource
	final void testCreateFromCM_AspectRatio(int imgWidth, int imgHeight, AspectRatio ar, AspectRatioHandle expAR,
	    int expWidth, int expHeight) {
		mockCalculationCM(point(-2.02, 1.2), point(0.8, -1.2), ar, 26);
		mockColorCM(PaletteVariant.BLUEWHITE, 24, ColorGradingStyle.LINE);
		mockImgCM(imgWidth, imgHeight, "/any/folder/image.img");

		// Run
		MandelbrotAttributesDTO model = cut.createFromCM(calcModel, colModel, imgModel);

		assertNotNull(model);
		assertEquals(expAR, model.getImage().getAspectRatioHandle());
		assertEquals(expWidth, model.getImage().getImageWidth());
		assertEquals(expHeight, model.getImage().getImageHeight());
	}

}
