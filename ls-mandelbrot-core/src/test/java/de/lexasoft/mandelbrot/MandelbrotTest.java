/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	private static final String IMAGE_DIRECTORY = "junit-tmp";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		topLeft = MandelbrotPointPosition.of(-2.02, 1.2);
		bottomRight = MandelbrotPointPosition.of(0.7, -1.2);
	}

	private static Stream<Arguments> testDrawMandelbrot() {
		ColorPaletteFactory cFactory = new ColorPaletteFactory();
		List<Color> ungraded = new ArrayList<>();
		ungraded.add(Color.BLUE);
		ungraded.add(Color.WHITE);
		ungraded.add(Color.ORANGE);
		ungraded.add(Color.WHITE);
		return Stream.of(
		    // Black and white
		    Arguments.of(new MandelbrotBlackWhite(), 500, IMAGE_DIRECTORY + "/mandelbrot-bw.tiff"),
		    // Rainbow 29 colors
		    Arguments.of(MandelbrotColorPalette.of(cFactory.createRainbowPalette29(), Color.BLACK), 580,
		        IMAGE_DIRECTORY + "/mandelbrot-rainbow.tiff"),
		    // 3 colors in list
		    Arguments.of(MandelbrotColorPalette.of(cFactory.createGradientList(ungraded, 21), Color.BLACK), 500,
		        IMAGE_DIRECTORY + "/mandelbrot-colorlist.tiff"));

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
