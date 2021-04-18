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
 * @author admin
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
	
	public Color colorizePoint(Point point, Color color) {
		g2d.setColor(color);
		g2d.drawLine(point.x, point.y, point.x, point.y);
		return color;
	}
	
	public void writeAsFile(String qualifiedFilename) throws IOException {
		File file = new File(qualifiedFilename);
		ImageIO.write(image, "png", file);
	}

}
