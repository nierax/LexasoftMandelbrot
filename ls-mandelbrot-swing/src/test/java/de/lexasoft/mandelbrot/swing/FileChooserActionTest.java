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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.swing.FileChooserAction.FileAction;

/**
 * 
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class FileChooserActionTest {

	private FileChooserAction cut;
	@Mock
	private JFileChooser chooser;
	@Mock
	private static MockedStatic<JOptionPane> staticJOptionPane;
	@Mock
	private Component parent;
	@Mock
	private FileAction handler;
	@Mock
	private File file;

	private static final String ERROR_MSG = "any/error/msg";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = FileChooserAction.of();
	}

	/**
	 * Cancel option chosen
	 */
	@Test
	final void testFileSaveActionCancel() {
		when(chooser.showSaveDialog(parent)).thenReturn(JFileChooser.CANCEL_OPTION);
		cut.fileSaveAction(chooser, parent, handler, ERROR_MSG);
		verify(chooser).showSaveDialog(parent);
		// Nothing else should happen.
	}

	/**
	 * Approve option chosen
	 * 
	 * @throws IOException
	 */
	@Test
	final void testFileSaveActionOk() throws IOException {
		when(chooser.showSaveDialog(parent)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(chooser.getSelectedFile()).thenReturn(file);
		cut.fileSaveAction(chooser, parent, handler, ERROR_MSG);
		verify(chooser).showSaveDialog(parent);
		verify(chooser).getSelectedFile();
		verify(handler).handleFile(file);
	}

	/**
	 * Error option occurred
	 */
	@Test
	final void testFileSaveActionError() {
		when(chooser.showSaveDialog(parent)).thenReturn(JFileChooser.ERROR_OPTION);

		cut.fileSaveAction(chooser, parent, handler, ERROR_MSG);
		verify(chooser).showSaveDialog(parent);

		staticJOptionPane
		    .verify(() -> JOptionPane.showMessageDialog(parent, ERROR_MSG, "Error", JOptionPane.ERROR_MESSAGE));
	}

	/**
	 * Cancel option chosen
	 */
	@Test
	final void testFileOpenActionCancel() {
		when(chooser.showOpenDialog(parent)).thenReturn(JFileChooser.CANCEL_OPTION);
		cut.fileOpenAction(chooser, parent, handler, ERROR_MSG);
		verify(chooser).showOpenDialog(parent);
		// Nothing else should happen.
	}

	/**
	 * Approve option chosen
	 * 
	 * @throws IOException
	 */
	@Test
	final void testFileOpenActionOk() throws IOException {
		when(chooser.showOpenDialog(parent)).thenReturn(JFileChooser.APPROVE_OPTION);
		when(chooser.getSelectedFile()).thenReturn(file);
		cut.fileOpenAction(chooser, parent, handler, ERROR_MSG);
		verify(chooser).showOpenDialog(parent);
		verify(chooser).getSelectedFile();
		verify(handler).handleFile(file);
	}

	/**
	 * Error option occurred
	 */
	@Test
	final void testFileOpenActionError() {
		when(chooser.showOpenDialog(parent)).thenReturn(JFileChooser.ERROR_OPTION);

		cut.fileOpenAction(chooser, parent, handler, ERROR_MSG);
		verify(chooser).showOpenDialog(parent);

		staticJOptionPane
		    .verify(() -> JOptionPane.showMessageDialog(parent, ERROR_MSG, "Error", JOptionPane.ERROR_MESSAGE));
	}

}
