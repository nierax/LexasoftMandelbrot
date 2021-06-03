/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Graphics;
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
 * This class represents an image of a MandelbrotIterator set calculation.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageFile extends AbstractMandelbrotImage implements MandelbrotImage {

	private BufferedImage image;
	private File file;
	private String fileType;

	MandelbrotImageFile(int width, int height, String qualifiedFilename) {
		super();
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		file = new File(qualifiedFilename);
		fileType = qualifiedFilename.substring(qualifiedFilename.lastIndexOf(".") + 1);
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
	@Override
	public void write() throws IOException {
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
	 * Get graphics object to write to image file.
	 * 
	 * @return Graphics object created from image.
	 */
	@Override
	protected Graphics getGraphics() {
		return image.createGraphics();
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

}
