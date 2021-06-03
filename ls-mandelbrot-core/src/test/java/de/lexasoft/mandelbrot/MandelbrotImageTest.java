/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author nierax
 *
 */
class MandelbrotImageTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotImage#of(int, int, String)}.
	 */
	@Test
	final void testOfQualifiedFilename() {
		MandelbrotImage result = MandelbrotImage.of(450, 300, "/qualified/file/name");
		assertNotNull(result);
		assertTrue(result instanceof MandelbrotImageFile);
	}

	/**
	 * Test method for {@link MandelbrotImage#of(java.awt.Graphics)}
	 */
	@Test
	final void testOfGraphics() {
		MandelbrotImage result = MandelbrotImage.of(new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB).getGraphics());
		assertNotNull(result);
		assertTrue(result instanceof MandelbrotImageGraphics);
	}

}
