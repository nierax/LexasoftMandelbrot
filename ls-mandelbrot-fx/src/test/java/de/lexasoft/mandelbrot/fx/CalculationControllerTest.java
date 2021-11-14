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
package de.lexasoft.mandelbrot.fx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

	private CalculationController cut;

	@Mock
	private ImageOut outImage;
	@Mock
	private static MockedStatic<MandelbrotController> staticCtrl;
	@Mock
	private static MockedStatic<SwingFXUtils> staticSwingFXUtils;
	@Mock
	private MandelbrotController ctrl;
	@Mock
	private MandelbrotImage mbImage;
	@Mock
	private BufferedImage digImage;
	@Mock
	private WritableImage fxImage;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new CalculationController(outImage);
	}

	@Test
	final void __construct__() {
		// Already constructed
		// Are initial values set correctly?
		assertEquals(AspectRatioHandle.FITIN, cut.model().getImage().getAspectRatioHandle());
		assertEquals(0, cut.model().getImage().getImageHeight());
		assertEquals(0, cut.model().getImage().getImageWidth());
	}

	@Test
	final void __new_width_all_given__() {
		// Prepare
		staticCtrl.when(MandelbrotController::of).thenReturn(ctrl);
		when(ctrl.executeSingleCalculation(cut.model())).thenReturn(mbImage);
		when(mbImage.getImage()).thenReturn(digImage);
		staticSwingFXUtils.when(() -> SwingFXUtils.toFXImage(digImage, null)).thenReturn(fxImage);
		// set height so it isn't 0 anymore
		cut.model().getImage().setImageHeight(400);

		// Run
		cut.newWidth(450);

		// Check
		assertEquals(450, cut.model().getImage().getImageWidth());
		verify(ctrl).executeSingleCalculation(cut.model());
		verify(outImage).outImage(any(Image.class));
	}

	@Test
	final void __new_width_no_height_given__() {
		// Height is 0 from constructor. No mock is expected

		// Run
		cut.newWidth(450);

		// Check: Controller must not be invoked.
		assertEquals(450, cut.model().getImage().getImageWidth());
		verify(ctrl, times(0)).executeSingleCalculation(cut.model());
	}

	@Test
	final void __new_height_all_given__() {
		// Prepare
		staticCtrl.when(MandelbrotController::of).thenReturn(ctrl);
		when(ctrl.executeSingleCalculation(cut.model())).thenReturn(mbImage);
		when(mbImage.getImage()).thenReturn(digImage);
		staticSwingFXUtils.when(() -> SwingFXUtils.toFXImage(digImage, null)).thenReturn(fxImage);
		// set height so it isn't 0 anymore
		cut.model().getImage().setImageWidth(450);

		// Run
		cut.newHeight(400);

		// Check
		assertEquals(400, cut.model().getImage().getImageHeight());
		verify(ctrl).executeSingleCalculation(cut.model());
		verify(outImage).outImage(any(Image.class));
	}

	@Test
	final void __new_height_no_width_given__() {
		// Width is 0 from constructor. No mock is expected

		// Run
		cut.newHeight(400);

		// Check: Controller must not be invoked.
		assertEquals(400, cut.model().getImage().getImageHeight());
		verify(ctrl, times(0)).executeSingleCalculation(cut.model());
	}

}
