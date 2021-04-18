/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author admin
 *
 */
class MandelbrotTest {
	
	private Mandelbrot cut;
	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private static final int MAXIMUM_ITERATIONS = 500;
	private static final int IMAGE_WIDTH = 459;
	private static final int IMAGE_HEIGHT = 405;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new Mandelbrot();
		topLeft = MandelbrotPointPosition.of(-2.02, -1.2);
		bottomRight = MandelbrotPointPosition.of(0.7, 1.2);
	}

	/**
	 * Test method for {@link de.lexasoft.mandelbrot.Mandelbrot#drawMandelbrot(de.lexasoft.mandelbrot.MandelbrotPointPosition, de.lexasoft.mandelbrot.MandelbrotPointPosition, int, int, int)}.
	 * @throws IOException 
	 */
	@Test
	void testDrawMandelbrot() throws IOException {
		MandelbrotImage image = cut.drawMandelbrot(topLeft, bottomRight, MAXIMUM_ITERATIONS, IMAGE_WIDTH, IMAGE_HEIGHT);
		assertNotNull(image, "Image could not be created");
		image.writeAsFile("C:\\Users\\axeln\\Pictures\\mandelbrot.tiff");
	}

}
