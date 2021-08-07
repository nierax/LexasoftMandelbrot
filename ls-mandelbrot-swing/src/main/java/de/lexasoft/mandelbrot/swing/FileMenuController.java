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

/**
 * This class controls the file menu.
 * 
 * @author nierax
 *
 */
public class FileMenuController {

	private FileMenuView view;

	/**
	 * 
	 */
	public FileMenuController(FileMenuView view) {
		this.view = view;
	}

	public void initController() {
		view.getMntmSave().addActionListener(l -> saveFile());
		view.getMntmLoad().addActionListener(l -> loadFile());
	}

	public void saveFile() {
		System.out.println("tbd: save file...");
	}

	public void loadFile() {
		System.out.println("tbd: load file...");
	}

}
