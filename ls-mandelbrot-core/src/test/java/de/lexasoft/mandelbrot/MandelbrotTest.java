/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 */
class MandelbrotTest {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private static final int IMAGE_WIDTH = 459;
	private static final int IMAGE_HEIGHT = 405;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		topLeft = MandelbrotPointPosition.of(-2.02, -1.2);
		bottomRight = MandelbrotPointPosition.of(0.7, 1.2);
	}

	private static Stream<Arguments> testDrawMandelbrot() {
		return Stream.of(
		    // Black and white
		    Arguments.of(new MandelbrotColorizeBlackWhite(), 500, "C:\\Users\\axeln\\Pictures\\mandelbrot-bw.tiff"),
		    // 2 colors
		    Arguments.of(new MandelbrotColorize2ColorGradient(Color.BLUE, Color.WHITE, 50), 50,
		        "C:\\Users\\axeln\\Pictures\\mandelbrot-color.tiff"));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.Mandelbrot#drawMandelbrot(de.lexasoft.mandelbrot.MandelbrotPointPosition, de.lexasoft.mandelbrot.MandelbrotPointPosition, int, int, int)}.
	 * 
	 * @throws IOException
	 */
	@ParameterizedTest
	@MethodSource
	void testDrawMandelbrot(MandelbrotColorize col, int maxIter, String filename) throws IOException {
		Mandelbrot cut = Mandelbrot.of(col);
		MandelbrotImage image = cut.drawMandelbrot(topLeft, bottomRight, maxIter, IMAGE_WIDTH, IMAGE_HEIGHT);
		assertNotNull(image, "Image could not be created");
		image.writeAsFile(filename);
	}

}
