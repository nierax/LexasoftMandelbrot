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
	private static final int IMAGE_WIDTH = 4590;
	private static final int IMAGE_HEIGHT = 4050;

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
		    Arguments.of(new MandelbrotBlackWhite(), 500, "C:\\Users\\axeln\\Pictures\\mandelbrot-bw.tiff"),
		    // 2 colors
		    Arguments.of(new Mandelbrot2ColorGradient(Color.BLUE, Color.RED, 5), 50,
		        "C:\\Users\\axeln\\Pictures\\mandelbrot-color.tiff"),
		    // 2 colors lighter blue
		    Arguments.of(new Mandelbrot2ColorGradient(new Color(25, 140, 255), Color.WHITE, 5), 500,
		        "C:\\Users\\axeln\\Pictures\\mandelbrot-color2.tiff"));
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
