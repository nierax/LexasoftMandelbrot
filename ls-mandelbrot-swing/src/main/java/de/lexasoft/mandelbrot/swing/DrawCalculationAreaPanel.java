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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * This panel is used to draw the rectangle with the calculation area.
 * 
 * @see DrawCalculationAreaController
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class DrawCalculationAreaPanel extends TransparentPanel {

	private Rectangle calculationArea;
	private Color color;
	private boolean toDraw;

	public void drawRect(Rectangle calculationArea, Color color) {
		this.calculationArea = calculationArea;
		this.color = color;
		validate();
		repaint();
	}

	public void drawRect(Rectangle calculationArea) {
		drawRect(calculationArea, Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (toDraw && (color != null) && (calculationArea != null)) {
			g.setXORMode(color);
			g.drawRect(calculationArea.x, calculationArea.y, calculationArea.width, calculationArea.height);
		}
	}

	public void undrawRect(boolean toDraw) {
		this.toDraw = toDraw;
		validate();
		repaint();
	}
}
