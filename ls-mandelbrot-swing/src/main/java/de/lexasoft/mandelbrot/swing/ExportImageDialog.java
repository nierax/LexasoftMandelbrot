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

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class ExportImageDialog extends JDialog {
	private ExportImagePanel panel;

	/**
	 * 
	 */
	public ExportImageDialog(JFrame parent) {
		super(parent);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("Export image file");

		panel = new ExportImagePanel();
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	public void popupDialog() {
		setModal(true);
		pack();
		setLocationRelativeTo(getParent());
		setVisible(true);
	}

	public void closeDialog() {
		setVisible(false);
		dispose();
	}

	public ExportImagePanel getPanel() {
		return panel;
	}
}
