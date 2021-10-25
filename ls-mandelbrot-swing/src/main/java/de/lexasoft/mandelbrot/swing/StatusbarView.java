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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The status bar, shown in the bottom of the application
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class StatusbarView extends JPanel {

	private JLabel advert;
	private JTextField duration;

	/**
	 * Create a new status bar instance.
	 */
	public StatusbarView() {
		initView();
	}

	/**
	 * Initialize the view.
	 */
	private void initView() {
		this.setLayout(new BorderLayout(0, 0));
		advert = new JLabel("https://github.com/nierax/LexasoftMandelbrot");
		this.add(advert, BorderLayout.WEST);

		duration = new JTextField();
		duration.setEditable(false);
		add(duration, BorderLayout.EAST);
		duration.setColumns(10);
	}

	/**
	 * The text field, showing the duration of a calculation
	 * 
	 * @return Text field, showing the duration
	 */
	JTextField getDuration() {
		return duration;
	}
}
