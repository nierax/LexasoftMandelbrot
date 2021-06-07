/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
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

	private BufferedImage image;

	/**
	 * 
	 */
	MandelbrotImageGraphics(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void write() throws IOException {
		// Nothing to do in this case

	}

	@Override
	protected Graphics getGraphics() {
		return this.image.getGraphics();
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

}
