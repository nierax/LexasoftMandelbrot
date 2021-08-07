/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
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

import de.lexasoft.mandelbrot.api.InfoCallbackAPI;

/**
 * Encapsulates the handling of an image of a Mandelbrot set.
 * 
 * @author nierax
 *
 */
public class MandelbrotImage {

	private BufferedImage image;

	/**
	 * 
	 */
	MandelbrotImage(int width, int height, int imageType) {
		image = new BufferedImage(width, height, imageType);
	}

	/**
	 * Draw a point with the given color.
	 * 
	 * @param point The point to draw.
	 * @param color The color, the point should have.
	 * @return The color used.
	 */
	public Color colorizePoint(Point point, Color color) {
		Graphics g2d = image.getGraphics();
		g2d.setColor(color);
		g2d.drawLine(point.x, point.y, point.x, point.y);
		return color;
	}

	private String fileType(String qualifiedFilename) {
		return qualifiedFilename.substring(qualifiedFilename.lastIndexOf(".") + 1);
	}

	/**
	 * Experimental: Compress the output.
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

	public void writeToFile(String fileName) throws IOException {
		String fileType = fileType(fileName);
		File file = new File(fileName);
		if (fileType.startsWith("tif")) {
			handleTiff(image, file);
		} else {
			if (!ImageIO.write(image, fileType, file)) {
				throw new IOException("Image could not be written to file \"" + file.getAbsolutePath() + "\"");
			}
		}
		InfoCallbackAPI.of().outFileWritten(file.getCanonicalPath());
	}

	/**
	 * Creates a Mandelbrot image with the given height and width and imageType.
	 * 
	 * @param width     The width of the image.
	 * @param height    The height of the image.
	 * @param imageType The type of the image (according {@link BufferedImage})
	 * @return The MandelbrotImage object.
	 */
	public final static MandelbrotImage of(int width, int height, int imageType) {
		return new MandelbrotImage(width, height, imageType);
	}

	/**
	 * Creates a Mandelbrot image with the given height and width and the image type
	 * RGB.
	 * 
	 * @param width  The width of the image.
	 * @param height The height of the image.
	 * @return The MandelbrotImage object.
	 */
	public final static MandelbrotImage of(int width, int height) {
		return of(width, height, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

}
