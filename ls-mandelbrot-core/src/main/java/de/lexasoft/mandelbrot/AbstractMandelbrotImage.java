package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class AbstractMandelbrotImage implements MandelbrotImage {

	AbstractMandelbrotImage() {
		super();
	}

	/**
	 * Draw a point with the given color.
	 * 
	 * @param point The point to draw.
	 * @param color The color, the point should have.
	 * @return The color used.
	 */
	@Override
	public Color colorizePoint(Point point, Color color) {
		Graphics g2d = getGraphics();
		g2d.setColor(color);
		g2d.drawLine(point.x, point.y, point.x, point.y);
		return color;
	}

	protected abstract Graphics getGraphics();

}