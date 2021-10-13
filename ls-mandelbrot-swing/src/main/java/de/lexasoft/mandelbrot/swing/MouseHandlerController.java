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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;

/**
 * @author nierax
 *
 */
public class MouseHandlerController implements MouseWheelListener {

	private JPanel view;
	private CalculationAreaControllerModel model;

	/**
	 * 
	 */
	public MouseHandlerController(JPanel view) {
		this.view = view;
		this.model = null;
	}

	public void initController() {
		this.view.addMouseWheelListener(this);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("MouseWheel " + e);
	}

}
