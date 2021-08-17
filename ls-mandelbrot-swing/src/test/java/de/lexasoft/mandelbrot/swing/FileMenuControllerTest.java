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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * @author nierax
 *
 */
class FileMenuControllerTest {

	class CUT extends FileMenuController {

		public CUT(FileMenuView view, JFrame parent, MandelbrotAttributesDTO model) {
			super(view, parent, model);
		}

		@Override
		protected JFileChooser createFileChooser(String dialogTitle) {
			return fileChooser;
		}
	}

	private CUT cut;

	@Mock
	private JFileChooser fileChooser;
	@Mock
	private static MockedStatic<JOptionPane> staticJOptionPane;
	@Mock
	private JFrame parentFrame;
	@Mock
	private FileMenuView view;
	@Mock
	private MandelbrotAttributesDTO model;
	@Mock
	private File file;

	@BeforeAll
	static void init() {
		staticJOptionPane = mockStatic(JOptionPane.class);
	}

	@AfterAll
	static void close() {
		staticJOptionPane.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		fileChooser = mock(JFileChooser.class);
		parentFrame = mock(JFrame.class);
		model = mock(MandelbrotAttributesDTO.class);
		view = mock(FileMenuView.class);
		file = mock(File.class);
		cut = new CUT(view, parentFrame, model);
	}

	/**
	 * Error occurred choosing a file to save.
	 * {@link de.lexasoft.mandelbrot.swing.FileMenuController#saveFile()}.
	 */
	@Test
	final void testSaveFileExitError() {
		// Get error, when the dialog should open
		when(fileChooser.showSaveDialog(parentFrame)).thenReturn(JFileChooser.ERROR_OPTION);
		// call the method.
		cut.saveFile();
		// Verify, whether the error dialog is called.
		assertErrorDialogShown();
	}

	/**
	 * 
	 */
	private void assertErrorDialogShown() {
		staticJOptionPane.verify(() -> JOptionPane.showMessageDialog(same(parentFrame), anyString(), eq("Error"),
		    eq(JOptionPane.ERROR_MESSAGE)));
	}

	/**
	 * Choosing a file to save has been cancelled.
	 * {@link de.lexasoft.mandelbrot.swing.FileMenuController#saveFile()}.
	 */
	@Test
	final void testSaveFileExitCancel() {
		// Cancel, as the dialog should disappear
		when(fileChooser.showSaveDialog(parentFrame)).thenReturn(JFileChooser.CANCEL_OPTION);
		// call the method.
		cut.saveFile();
		// Nothing happens, when the cancel button was pressed.
	}

	/**
	 * A file to save has been successfully chosen.
	 * {@link de.lexasoft.mandelbrot.swing.FileMenuController#saveFile()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	final void testSaveFileExitOk() throws JsonGenerationException, JsonMappingException, IOException {
		// Go on with the save process
		when(fileChooser.showSaveDialog(parentFrame)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(fileChooser.getSelectedFile()).thenReturn(file);
		// call the method.
		cut.saveFile();
		// Check, whether the save method on the model is called with the given file.
		verify(model, times(1)).writeToYamlFile(file);
	}

	/**
	 * A file to save has been chosen, but an I/O error occurred.
	 * {@link de.lexasoft.mandelbrot.swing.FileMenuController#saveFile()}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@Test
	final void testSaveFileWriteError() throws JsonGenerationException, JsonMappingException, IOException {
		// Go on with the save process
		when(fileChooser.showSaveDialog(parentFrame)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(fileChooser.getSelectedFile()).thenReturn(file);
		doThrow(new IOException("Something went wrong")).when(model).writeToYamlFile(file);
		// call the method.
		cut.saveFile();
		// Check, whether the save method on the model is called with the given file.
		verify(model, times(1)).writeToYamlFile(file);
		assertErrorDialogShown();
	}

}
