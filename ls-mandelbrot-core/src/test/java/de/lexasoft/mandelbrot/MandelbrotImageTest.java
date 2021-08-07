/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author nierax
 *
 */
class MandelbrotImageTest {

	private MandelbrotImage cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = MandelbrotImage.of(459, 405);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotImage#MandelbrotImage2()}.
	 */
	@Test
	final void testof() {
		MandelbrotImage cut = MandelbrotImage.of(459, 405);
		BufferedImage image = cut.getImage();
		assertNotNull(image);
		assertEquals(459, image.getWidth());
		assertEquals(405, image.getHeight());
	}

	@ParameterizedTest
	@ValueSource(strings = { "./junit-tmp/mbimage-test.tiff", "./junit-tmp/mbimage-test.jpg",
	    "./junit-tmp/mbimage-test.png" })
	final void testWriteToFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		cut.writeToFile(fileName);
		assertTrue(file.exists());
	}

}
