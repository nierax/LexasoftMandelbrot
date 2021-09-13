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

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

	/**
	 * Should initialize the the export dialog with the image size from the image
	 * model and open it.
	 */
	@Test
	final void testExportImageFor() {
		when(imgModel.imageWidth()).thenReturn(459);
		when(imgModel.imageHeight()).thenReturn(405);

		// Run
		cut.exportImageFor(calcModel, colorModel, imgModel);

		// Check
		verify(imgModel).imageHeight();
		verify(imgModel).imageWidth();
		verify(imageWidth).setText("459");
		verify(imageHeight).setText("405");
		verify(exportDlg).popupDialog();
		assertEquals(459, cut.imageWidth());
		assertEquals(405, cut.imageHeight());
		assertNull(cut.imageFilename());
	}

}
