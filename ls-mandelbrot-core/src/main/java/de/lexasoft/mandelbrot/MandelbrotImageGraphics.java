/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Graphics;
import java.io.IOException;

/**
 * Writes the image into an abstract graphics context.
 * <p>
 * How to deal with this furthermore depends on the graphics context instance.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageGraphics extends AbstractMandelbrotImage {

	private Graphics graphics;

	/**
	 * 
	 */
	MandelbrotImageGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	@Override
	public void write() throws IOException {
		// Nothing to do in this case

	}

	@Override
	protected Graphics getGraphics() {
		return this.graphics;
	}

}
