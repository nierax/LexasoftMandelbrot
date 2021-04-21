/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class represents an image of a Mandelbrot set calculation.
 * 
 * @author nierax
 *
 */
public class MandelbrotImage {

	private BufferedImage image;
	private Graphics2D g2d;

	public MandelbrotImage(int width, int height) {
		super();
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2d = image.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);
	}

	/**
	 * Draw a point with the given color.
	 * 
	 * @param point The point to draw.
	 * @param color The color, the point should have.
	 * @return The color used.
	 */
	public Color colorizePoint(Point point, Color color) {
		g2d.setColor(color);
		g2d.drawLine(point.x, point.y, point.x, point.y);
		return color;
	}

	/**
	 * Writes the image into a file with the given filename.
	 * <p>
	 * The file type (*.tiff, *.png, *.jpg,...) is used to define the type of the
	 * image.
	 * 
	 * @param qualifiedFilename
	 * @throws IOException
	 */
	public void writeAsFile(String qualifiedFilename) throws IOException {
		File file = new File(qualifiedFilename);
		String filetype = qualifiedFilename.substring(qualifiedFilename.lastIndexOf(".") + 1);
		boolean written = ImageIO.write(image, filetype, file);
		if (!written) {
			throw new IOException("Image could not be written to file \"" + qualifiedFilename + "\"");
		}
	}

}
