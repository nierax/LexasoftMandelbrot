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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * This class controls the file menu.
 * 
 * @author nierax
 *
 */
public class FileMenuController extends ModelChangingController<MandelbrotAttributesDTO> {

	private FileMenuView menuView;
	private JFrame parentFrame;
	private MandelbrotAttributesDTO model;

	/**
	 * 
	 */
	public FileMenuController(FileMenuView view, JFrame parent, MandelbrotAttributesDTO model) {
		this.menuView = view;
		this.parentFrame = parent;
		initModel(model);
	}

	void initModel(MandelbrotAttributesDTO model) {
		this.model = model;
	}

	void initController() {
		menuView.getMntmSave().addActionListener(l -> saveFile());
		menuView.getMntmLoad().addActionListener(l -> loadFile());
		menuView.getMntmExportImage().addActionListener(l -> exportImage());
	}

	private void doSaveFile(File file2Save) throws JsonGenerationException, JsonMappingException, IOException {
		String absolutePath = file2Save.getAbsolutePath();
		if (!absolutePath.endsWith(".yaml")) {
			file2Save = new File(absolutePath + ".yaml");
		}
		model.writeToYamlFile(file2Save);
	}

	/**
	 * API method to save the calculation, called by the listener.
	 * <p>
	 * The method opens the dialog for the user to choose a file to save the
	 * calculation to.
	 * <p>
	 * If the file name chosen does not end with ".yaml", this suffix will be added
	 * automatically.
	 */
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
		default: {
			JOptionPane.showMessageDialog(parentFrame, "An error occured saving the calculation. Please try again.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		}
	}

	JFileChooser createFileChooser(String dialogTitle) {
		JFileChooser fileChooserDialog = new JFileChooser();
		fileChooserDialog.setDialogTitle(dialogTitle);
		fileChooserDialog.setFileFilter(new FileNameExtensionFilter("Mandelbrot calculation files", "yaml"));
		return fileChooserDialog;
	}

	private void doLoadFile(File file2Load) throws JsonParseException, JsonMappingException, IOException {
		MandelbrotAttributesDTO newModel = MandelbrotAttributesDTO.of(file2Load);
		initModel(newModel);
		fireModelChangedEvent(new ModelChangedEvent<MandelbrotAttributesDTO>(this, newModel));
	}

	/**
	 * @return the model
	 */
	MandelbrotAttributesDTO getModel() {
		return model;
	}

	public void loadFile() {
		JFileChooser fileChooser = createFileChooser("Choose file to load from...");
		int userSelection = fileChooser.showOpenDialog(parentFrame);
		switch (userSelection) {
		case JFileChooser.APPROVE_OPTION: {
			try {
				doLoadFile(fileChooser.getSelectedFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		case JFileChooser.CANCEL_OPTION: {
			return;
		}
		default: {
			JOptionPane.showMessageDialog(parentFrame, "An error occured loading the calculation. Please try again.", "Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		}
	}

	/**
	 * Starts the export dialog.
	 */
	public void exportImage() {
		ExportImageDialog exportImageDialog = new ExportImageDialog(this.parentFrame);
		exportImageDialog.popupDialog();
	}

}
