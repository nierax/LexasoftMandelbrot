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

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * This class controls the file menu.
 * 
 * @author nierax
 *
 */
public class FileMenuController {

	private FileMenuView menuView;
	private JFrame parentFrame;
	private MandelbrotAttributesDTO model;

	/**
	 * 
	 */
	public FileMenuController(FileMenuView view, JFrame parent, MandelbrotAttributesDTO model) {
		this.menuView = view;
		this.parentFrame = parent;
		this.model = model;
	}

	public void initController() {
		menuView.getMntmSave().addActionListener(l -> saveFile());
		menuView.getMntmLoad().addActionListener(l -> loadFile());
	}

	private void doSaveFile(File file2Save) throws JsonGenerationException, JsonMappingException, IOException {
		model.writeToYamlFile(file2Save);
	}

	public void saveFile() {
		JFileChooser fileChooser = createFileChooser("Specify a file to save");

		int userSelection = fileChooser.showSaveDialog(parentFrame);
		switch (userSelection) {
		case JFileChooser.CANCEL_OPTION: {
			return;
		}
		case JFileChooser.APPROVE_OPTION: {
			try {
				doSaveFile(fileChooser.getSelectedFile());
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		case JFileChooser.ERROR_OPTION: {
			JOptionPane.showMessageDialog(parentFrame, "An error occured saving the calculation. Please try again.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		}
	}

	protected JFileChooser createFileChooser(String dialogTitle) {
		JFileChooser fileChooserDialog = new JFileChooser();
		fileChooserDialog.setDialogTitle(dialogTitle);
		fileChooserDialog.setFileFilter(new FileNameExtensionFilter("Mandelbrot calculation files", "yaml"));
		return fileChooserDialog;
	}

	public void loadFile() {
		System.out.println("tbd: load file...");
	}

}
