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

import javax.swing.JFileChooser;
import javax.swing.JFrame;

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
	public FileMenuController(MandelbrotSwingView view, MandelbrotAttributesDTO model) {
		this.menuView = view.getMnFile();
		this.parentFrame = view.getFrmLexasoftMandelbrotApplication();
		this.model = model;
	}

	public void initController() {
		menuView.getMntmSave().addActionListener(l -> saveFile());
		menuView.getMntmLoad().addActionListener(l -> loadFile());
	}

	private void doSaveFile(File file2Save) {
		System.out.println(String.format("tbd: implement save action for file %s ...", file2Save.getAbsolutePath()));
	}

	public void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		int userSelection = fileChooser.showSaveDialog(parentFrame);
		switch (userSelection) {
		case JFileChooser.CANCEL_OPTION: {
			return;
		}
		case JFileChooser.APPROVE_OPTION: {
			doSaveFile(fileChooser.getSelectedFile());
			break;
		}
		case JFileChooser.ERROR_OPTION: {
			System.out.println("Error occured...");
		}
		}
	}

	public void loadFile() {
		System.out.println("tbd: load file...");
	}

}
