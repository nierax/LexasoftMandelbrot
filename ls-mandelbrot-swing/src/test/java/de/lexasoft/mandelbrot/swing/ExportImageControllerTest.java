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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.PaletteVariant;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;
import de.lexasoft.mandelbrot.swing.model.ImageControllerModel;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class ExportImageControllerTest {

	class CUT extends ExportImageController {

		public CUT(JFrame parent) {
			super(parent);
		}

		@Override
		ExportImageDialog createDialog(JFrame parent) {
			return exportDlg;
		}

	}

	private CUT cut;

	@Mock
	private JFrame parent;
	@Mock
	private ExportImageDialog exportDlg;
	@Mock
	private ExportImagePanel exportPanel;
	@Mock
	private CalculationControllerModel calcModel;
	@Mock
	private JTextField imageWidth;
	@Mock
	private JTextField imageHeight;
	@Mock
	private ColorControllerModel colorModel;
	@Mock
	private ImageControllerModel imgModel;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new CUT(parent);
		when(exportDlg.getPanel()).thenReturn(exportPanel);
		when(exportPanel.getImageWidth()).thenReturn(imageWidth);
		when(exportPanel.getImageHeight()).thenReturn(imageHeight);
	}

	private void mockCalculationModel() {
		when(calcModel.topLeft()).thenReturn(MandelbrotPointPosition.of(-2.02, 1.2));
		when(calcModel.bottomRight()).thenReturn(MandelbrotPointPosition.of(0.8, -1.2));
		when(calcModel.aspectRatio()).thenReturn(AspectRatio.FILL);
		when(calcModel.maximumIterations()).thenReturn(50);
	}

	private void mockColorModel() {
		when(colorModel.paletteVariant()).thenReturn(PaletteVariant.BLUEWHITE);
		when(colorModel.totalNrOfColors()).thenReturn(6);
		when(colorModel.gradingStyle()).thenReturn(ColorGradingStyle.LINE);
	}

	private static Stream<Arguments> testExportImageFor() {
		return Stream.of(
		    // With FILL the image size is unchanged
		    Arguments.of(459, 405, AspectRatio.FILL, 459, 405),
		    // With IGNORE the image size is unchanged
		    Arguments.of(459, 405, AspectRatio.IGNORE, 459, 405),
		    // With 1x1 both sides should have the same value (smaller value)
		    Arguments.of(459, 405, AspectRatio.AR1x1, 405, 405), Arguments.of(405, 459, AspectRatio.AR1x1, 405, 405),
		    // With 3x2 the with is wider than the height.
		    Arguments.of(600, 459, AspectRatio.AR3x2, 600, 400));
	}

	/**
	 * Should initialize the the export dialog with the image size from the image
	 * model and open it.
	 */
	@ParameterizedTest
	@MethodSource
	final void testExportImageFor(int imgWidth, int imgHeight, AspectRatio ar, int expWidth, int expHeight) {
		mockCalculationModel();
		when(calcModel.aspectRatio()).thenReturn(ar);
		mockColorModel();
		when(imgModel.imageWidth()).thenReturn(imgWidth);
		when(imgModel.imageHeight()).thenReturn(imgHeight);

		// Run
		cut.exportImageFor(calcModel, colorModel, imgModel);

		// Check
		verify(imgModel).imageHeight();
		verify(imgModel).imageWidth();
		verify(imageWidth).setText(Integer.toString(expWidth));
		verify(imageHeight).setText(Integer.toString(expHeight));
		verify(exportDlg).popupDialog();
		assertEquals(expWidth, cut.imageWidth());
		assertEquals(expHeight, cut.imageHeight());
		assertNull(cut.imageFilename());
	}

	private static final Stream<Arguments> testImageWidthChanged() {
		return Stream.of(
		    // Export 10 times as high
		    Arguments.of(459, 405, AspectRatio.FILL, 4590, 4050),
		    // Export with 2048 px, quadratic
		    Arguments.of(459, 405, AspectRatio.AR1x1, 2048, 2048),
		    // Export with 2048 px, same aspect ratio
		    Arguments.of(459, 405, AspectRatio.IGNORE, 2048, 1807));
	}

	/**
	 * 
	 * @param imgWidth
	 * @param imgHeight
	 * @param ar
	 * @param expWidth
	 * @param expHeight
	 */
	@ParameterizedTest
	@MethodSource
	final void testImageWidthChanged(int imgWidth, int imgHeight, AspectRatio ar, int newWidth, int expHeight) {
		mockCalculationModel();
		when(calcModel.aspectRatio()).thenReturn(ar);
		mockColorModel();
		when(imgModel.imageWidth()).thenReturn(imgWidth);
		when(imgModel.imageHeight()).thenReturn(imgHeight);

		// Initialize controller
		cut.exportImageFor(calcModel, colorModel, imgModel);
		// Change width
		cut.imageWidthChanged(newWidth);

		// Verify
		// Model corrected?
		assertEquals(newWidth, cut.imageWidth());
		assertEquals(expHeight, cut.imageHeight());
		// Entered in text fields?
		verify(imageWidth).setText(Integer.toString(newWidth));
		verify(imageHeight).setText(Integer.toString(expHeight));
	}

	private static final Stream<Arguments> testImageHeightChanged() {
		return Stream.of(
		    // Export 10 times as high
		    Arguments.of(459, 405, AspectRatio.FILL, 4590, 4050),
		    // Export with 2048 px, quadratic
		    Arguments.of(459, 405, AspectRatio.AR1x1, 2048, 2048),
		    // Export with 2048 px, same aspect ratio
		    Arguments.of(459, 405, AspectRatio.IGNORE, 2048, 1807));
	}

	/**
	 * 
	 * @param imgWidth
	 * @param imgHeight
	 * @param ar
	 * @param expWidth
	 * @param newHeight
	 */
	@ParameterizedTest
	@MethodSource
	final void testImageHeightChanged(int imgWidth, int imgHeight, AspectRatio ar, int expWidth, int newHeight) {
		mockCalculationModel();
		when(calcModel.aspectRatio()).thenReturn(ar);
		mockColorModel();
		when(imgModel.imageWidth()).thenReturn(imgWidth);
		when(imgModel.imageHeight()).thenReturn(imgHeight);

		// Initialize controller
		cut.exportImageFor(calcModel, colorModel, imgModel);
		// Change width
		cut.imageHeightChanged(newHeight);

		// Verify
		// Model corrected?
		assertEquals(expWidth, cut.imageWidth());
		assertEquals(newHeight, cut.imageHeight());
		// Entered in text fields?
		verify(imageWidth).setText(Integer.toString(expWidth));
		verify(imageHeight).setText(Integer.toString(newHeight));
	}

}
