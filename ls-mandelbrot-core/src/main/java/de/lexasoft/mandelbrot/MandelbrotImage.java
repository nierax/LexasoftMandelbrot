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
import java.nio.ByteOrder;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

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
	 * Should compress the output, but doesn't work yet properly.
	 * 
	 * @param image
	 * @param file
	 * @throws IOException
	 */
	private void handleTiff(BufferedImage image, File file) throws IOException {
		final ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(file);
		if (imageOutputStream == null) {
			throw new IOException("Could not obtain output stream for file " + file.getAbsolutePath());
		}
		imageOutputStream.setByteOrder(ByteOrder.LITTLE_ENDIAN);
		final Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("tif");
		if (imageWriters == null) {
			throw new IOException("Could not obtain an image writer for tif format.");
		}
		final ImageWriter imageWriter = imageWriters.next();
		imageWriter.setOutput(imageOutputStream);
		// define compression
		final ImageWriteParam parameters = imageWriter.getDefaultWriteParam();
		parameters.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		parameters.setCompressionType("LZW");
		parameters.setCompressionQuality(0.5f);
		parameters.setTilingMode(ImageWriteParam.MODE_EXPLICIT);
		parameters.setTiling(image.getWidth(), image.getHeight(), 0, 0);

		ImageTypeSpecifier spec = new ImageTypeSpecifier(image);
		IIOMetadata metaData = imageWriter.getDefaultImageMetadata(spec, parameters);
		// write the image to a file
		try {
			imageWriter.write(null, new IIOImage(image, null, metaData), parameters);
		} catch (IOException e) {
			throw new IOException("Unable to write image to file: " + file.getAbsolutePath(), e);
		}

		// close stream, release resources
		imageOutputStream.close();
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

//		if (filetype.startsWith("tif")) {
//		handleTiff(image, file);
//		} else {
		if (!ImageIO.write(image, filetype, file)) {
			throw new IOException("Image could not be written to file \"" + qualifiedFilename + "\"");
		}
//		}
	}

}
