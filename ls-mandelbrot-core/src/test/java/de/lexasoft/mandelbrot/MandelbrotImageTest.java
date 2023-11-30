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
import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
 *
 */
class MandelbrotImageTest {

	private MandelbrotImage cut;
	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		topLeft = MandelbrotPointPosition.of(BigDecimal.valueOf(-2.02), BigDecimal.valueOf(1.2));
		bottomRight = MandelbrotPointPosition.of(BigDecimal.valueOf(0.8), BigDecimal.valueOf(-1.2));
		cut = MandelbrotImage.of(459, 405, topLeft, bottomRight);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotImage#MandelbrotImage2()}.
	 */
	@Test
	final void testof() {
		BufferedImage image = cut.getImage();
		assertNotNull(image);
		assertEquals(459, image.getWidth());
		assertEquals(405, image.getHeight());
		assertEquals(topLeft, cut.topLeft());
		assertEquals(bottomRight, cut.bottomRight());
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
