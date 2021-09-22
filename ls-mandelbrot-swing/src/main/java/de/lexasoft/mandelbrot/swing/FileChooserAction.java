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

import java.awt.Component;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This factory creates an @JFileChooser dialog with some predefined filters.
 * 
 * @author nierax
 *
 */
public class FileChooserAction {

	interface FileAction {
		void handleFile(File fileToHandle) throws IOException;
	}

	/**
	 * 
	 */
	private FileChooserAction() {
	}

	public static final FileChooserAction of() {
		return new FileChooserAction();
	}

	/**
	 * File chooser to load or save yaml files.
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public JFileChooser createYamlFileChooser(String dialogTitle) {
		return createFileChooser(dialogTitle, new FileNameExtensionFilter("Mandelbrot calculation files", "yaml"));
	}

	public JFileChooser createImageFileChooser(String dialogTitel) {
		return createFileChooser(dialogTitel, new FileNameExtensionFilter("Images", "tiff", "jpg", "gif", "png"));
	}

	/**
	 * @param dialogTitle
	 * @return
	 */
	private JFileChooser createFileChooser(String dialogTitle, FileFilter filter) {
		JFileChooser fileChooserDialog = new JFileChooser();
		fileChooserDialog.setDialogTitle(dialogTitle);
		fileChooserDialog.setFileFilter(filter);
		return fileChooserDialog;
	}

	/**
	 * Ask for a file to save to and save it via a @FileAction call.
	 * 
	 * @param chooser  The @JFileChooser object
	 * @param parent   The parent to relate to
	 * @param handler  The @FileAction object to save the file. Mostly Lambda
	 *                 expression
	 * @param errorMsg The error message, being displayed, if an error occurred.
	 */
	public void fileSaveAction(JFileChooser chooser, Component parent, FileAction handler, String errorMsg) {
		int result = chooser.showSaveDialog(parent);
		doHandleFile(chooser, parent, handler, errorMsg, result);
	}

	/**
	 * Ask for a file to open and open it via a @FileAction call.
	 * 
	 * @param chooser  The @JFileChooser object
	 * @param parent   The parent to relate to
	 * @param handler  The @FileAction object to open the file. Mostly Lambda
	 *                 expression
	 * @param errorMsg The error message, being displayed, if an error occurred.
	 */
	public void fileOpenAction(JFileChooser chooser, Component parent, FileAction handler, String errorMsg) {
		int result = chooser.showOpenDialog(parent);
		doHandleFile(chooser, parent, handler, errorMsg, result);
	}

	/**
	 * @param chooser
	 * @param parent
	 * @param handler
	 * @param errorMsg
	 * @param result
	 */
	private void doHandleFile(JFileChooser chooser, Component parent, FileAction handler, String errorMsg, int result) {
		switch (result) {
		case JFileChooser.CANCEL_OPTION:
			return;
		case JFileChooser.APPROVE_OPTION:
			try {
				handler.handleFile(chooser.getSelectedFile());
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		default:
			JOptionPane.showMessageDialog(parent, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);

		}
	}

}
