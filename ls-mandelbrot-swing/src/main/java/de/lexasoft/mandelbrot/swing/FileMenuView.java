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

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Represents the file menu
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class FileMenuView extends JMenu {
	private JMenuItem mntmLoad;
	private JMenuItem mntmSave;

	/**
	 * 
	 */
	public FileMenuView() {
		super("File");

		mntmLoad = new JMenuItem("Load...");
		add(mntmLoad);

		mntmSave = new JMenuItem("Save...");
		add(mntmSave);
	}

	public JMenuItem getMntmLoad() {
		return mntmLoad;
	}

	public JMenuItem getMntmSave() {
		return mntmSave;
	}
}
