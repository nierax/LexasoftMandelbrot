package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;

public interface MandelbrotImage {

	/**
	 * Draw a point with the given color.
	 * 
	 * @param point The point to draw.
	 * @param color The color, the point should have.
	 * @return The color used.
	 */
	Color colorizePoint(Point point, Color color);

	/**
	 * Writes the image to its target stream / queue / file, depending on the
	 * implementation.
	 * 
	 * @throws IOException
	 */
	void write() throws IOException;

	/**
	 * Create a suitable instance of MandelbrotImage for writing in a file.
	 * 
	 * @param width             Width of the image
	 * @param height            Height of the image
	 * @param qualifiedFilename The filename to write to.
	 * @return An new instance to handle a Mandelbrot image
	 */
	static MandelbrotImage of(int width, int height, String qualifiedFilename) {
		return new MandelbrotImageFile(width, height, qualifiedFilename);
	}

	/**
	 * Create an instance of MandelbrotImage with an abstract graphics object
	 * 
	 * @param graphics
	 * @return
	 */
	static MandelbrotImage of(Graphics graphics) {
		return new MandelbrotImageGraphics(graphics);
	}
}