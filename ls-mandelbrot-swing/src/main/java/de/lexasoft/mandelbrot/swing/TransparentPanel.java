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

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class TransparentPanel extends JPanel {

	/**
	 * 
	 */
	public TransparentPanel() {
		setOpaque(false);
	}

	/**
	 * @param layout
	 */
	public TransparentPanel(LayoutManager layout) {
		super(layout);
		setOpaque(false);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public TransparentPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		setOpaque(false);
	}

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public TransparentPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		Rectangle r = g.getClipBounds();
		g.fillRect(r.x, r.y, r.width, r.height);
		super.paintComponent(g);
	}

}
