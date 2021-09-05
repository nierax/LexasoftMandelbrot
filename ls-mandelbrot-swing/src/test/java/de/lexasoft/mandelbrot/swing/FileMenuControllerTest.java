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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class FileMenuControllerTest {

	class CUT extends FileMenuController {

		public CUT(FileMenuView view, JFrame parent, MandelbrotAttributesDTO model) {
			super(view, parent, model);
		}

		@Override
		JFileChooser createFileChooser(String dialogTitle) {
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
	private static MockedStatic<MandelbrotAttributesDTO> staticModel;
	@Mock
	private File file;
	@Mock
	private ModelChangedListener<MandelbrotAttributesDTO> listener;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
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
		// Make sure, the dialog was opened.
		verify(fileChooser).showSaveDialog(parentFrame);
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
		when(file.getAbsolutePath()).thenReturn("/somewhere/my-filename.yaml");
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
		when(file.getAbsolutePath()).thenReturn("/somewhere/my-filename.yaml");
		doThrow(new IOException("Something went wrong")).when(model).writeToYamlFile(file);
		// call the method.
		cut.saveFile();
		// Check, whether the save method on the model is called with the given file.
		verify(model, times(1)).writeToYamlFile(file);
		assertErrorDialogShown();
	}

	/**
	 * Load file when everything is ok.
	 */
	@SuppressWarnings("unchecked")
	@Test
	final void testLoadFileOk() {
		// Mock the choice of a file
		when(fileChooser.showOpenDialog(parentFrame)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(fileChooser.getSelectedFile()).thenReturn(file);
		// Create a new model for the test.
		model = mock(MandelbrotAttributesDTO.class);
		staticModel.when(() -> MandelbrotAttributesDTO.of(file)).thenReturn(model);
		// Register a listener for the event
		cut.addModelChangedListener(listener);

		// Call the method
		cut.loadFile();

		// Verify, whether the model was loaded.
		verify(fileChooser).showOpenDialog(parentFrame);
		verify(fileChooser).getSelectedFile();
		staticModel.verify(() -> MandelbrotAttributesDTO.of(file));
		assertSame(model, cut.getModel());
		// Check, whether the event was fired
		verify(listener, times(1)).modelChanged(any(ModelChangedEvent.class));
	}

	/**
	 * Choosing a file to load has been cancelled.
	 * {@link de.lexasoft.mandelbrot.swing.FileMenuController#saveFile()}.
	 */
	@Test
	final void testLoadFileExitCancel() {
		// Cancel, as the dialog should disappear
		when(fileChooser.showOpenDialog(parentFrame)).thenReturn(JFileChooser.CANCEL_OPTION);
		// call the method.
		cut.loadFile();
		// Make sure, the dialog was opened.
		verify(fileChooser).showOpenDialog(parentFrame);
		// Nothing happens, when the cancel button was pressed.
	}

	/**
	 * Error occurred choosing a file to load.
	 * {@link de.lexasoft.mandelbrot.swing.FileMenuController#saveFile()}.
	 */
	@Test
	final void testLoadFileExitError() {
		// Get error, when the dialog should open
		when(fileChooser.showOpenDialog(parentFrame)).thenReturn(JFileChooser.ERROR_OPTION);
		// call the method.
		cut.loadFile();
		// Make sure, the dialog was opened.
		verify(fileChooser).showOpenDialog(parentFrame);
		// Verify, whether the error dialog is called.
		assertErrorDialogShown();
	}

	/**
	 * Necessary to test the changed file name, because lambda expressions don't
	 * work due to the ambiguous method
	 * {@link MandelbrotAttributesDTO#writeToYamlFile(String)}
	 * 
	 * @author nierax
	 *
	 */
	class FileNameTester implements ArgumentMatcher<File> {

		/**
		 * @param expectedFilename
		 */
		public FileNameTester(String expectedFilename) {
			this.expectedFilename = expectedFilename;
		}

		private String expectedFilename;

		@Override
		public boolean matches(File argument) {
			return argument.getName().equals(expectedFilename);
		}

	}

	private static Stream<Arguments> testSaveFilenameExtension() {
		return Stream.of(
		    // Given without any extension
		    Arguments.of("/somewhere/my-filename", "my-filename.yaml", true),
		    // Given with other extension
		    Arguments.of("/somewhere/my-filename.doc", "my-filename.doc.yaml", true),
		    // Given with the right extension - don't change anything
		    Arguments.of("/somewhere/my-filename.yaml", "my-filename.yaml", false));
	}

	/**
	 * Check, whether the automatic change of file name works.
	 * 
	 * @param filename         The file name, chosen by the user
	 * @param expectedFilename The file name, expected after being corrected
	 * @param renameExpected   Is it necessary to call rename action?
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ParameterizedTest
	@MethodSource
	final void testSaveFilenameExtension(String filename, String expectedFilename, boolean renameExpected)
	    throws JsonGenerationException, JsonMappingException, IOException {
		// Simulate users interaction
		when(fileChooser.showSaveDialog(parentFrame)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(fileChooser.getSelectedFile()).thenReturn(file);
		// Which file name is chosen?
		when(file.getAbsolutePath()).thenReturn(filename);

		cut.saveFile();

		if (renameExpected) {
			// Was the expected file name used?
			verify(model).writeToYamlFile(argThat(new FileNameTester(expectedFilename)));
		} else {
			verify(model).writeToYamlFile(file);
		}
	}

}
