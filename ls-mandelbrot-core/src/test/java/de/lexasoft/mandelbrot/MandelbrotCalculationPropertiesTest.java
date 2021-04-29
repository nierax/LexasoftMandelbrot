/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author nierax
 *
 */
class MandelbrotCalculationPropertiesTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotCalculationProperties#of(java.lang.String)}.
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	void testOf() throws JsonParseException, JsonMappingException, IOException {
		MandelbrotCalculationProperties cut = MandelbrotCalculationProperties.of("src/test/resources/mandelbrot-test.yaml");
		assertEquals(-2.02d, cut.getTopLeft().cx());
		assertEquals(-1.2d, cut.getTopLeft().cy());
		assertEquals(0.7d, cut.getBottomRight().cx());
		assertEquals(1.2d, cut.getBottomRight().cy());
		assertEquals(500, cut.getMaximumIterations());
		assertEquals(4590, cut.getImageWidth());
		assertEquals(4050, cut.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test.tiff", cut.getImageFilename());

		assertSame(PaletteVariant.CUSTOM, cut.getPaletteVariant());
		assertEquals(2, cut.getCustomColorPalette().size());

		assertEquals(25, cut.getCustomColorPalette().get(0).getRed());
		assertEquals(140, cut.getCustomColorPalette().get(0).getGreen());
		assertEquals(255, cut.getCustomColorPalette().get(0).getBlue());

		assertEquals(255, cut.getCustomColorPalette().get(1).getRed());
		assertEquals(255, cut.getCustomColorPalette().get(1).getGreen());
		assertEquals(255, cut.getCustomColorPalette().get(1).getBlue());

		assertEquals(5, cut.getColorGrading());

		assertEquals(Color.BLACK, cut.getMandelbrotColor());
	}

	/**
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	void testOfAspectRatioError() throws JsonParseException, JsonMappingException, IOException {
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotCalculationProperties.of("src/test/resources/mandelbrot-test-ar-widthandheight-error.yaml");
		});
	}

	private static Stream<Arguments> testOfAspectRatioImageOk() {
		return Stream.of(
		    // Image width given, image height calculated
		    Arguments.of("src/test/resources/mandelbrot-test-ar-imageheight.yaml", 4590, 4050),
		    // Image height given, image width calculated
		    Arguments.of("src/test/resources/mandelbrot-test-ar-imagewidth.yaml", 4590, 4050),
		    // Image width given, image height calculated with cx both points in minus
		    Arguments.of("src/test/resources/mandelbrot-test-ar-bothinminus.yaml", 3000, 2000),
		    // Image width given, image height calculated with cx both points in plus
		    Arguments.of("src/test/resources/mandelbrot-test-ar-bothinplus.yaml", 3000, 2000));
	}

	/**
	 * Tests the determination of the image width / image height from the aspect
	 * ration of the Mandelbrot calculation.
	 * 
	 * @param yamlFilename   YAML file with test case
	 * @param expectedWidth  Which image width is expected (either given in the file
	 *                       or calculated)
	 * @param expectedHeight Which image height is expected (either given in the
	 *                       file or calculated)
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ParameterizedTest
	@MethodSource
	void testOfAspectRatioImageOk(String yamlFilename, int expectedWidth, int expectedHeight)
	    throws JsonParseException, JsonMappingException, IOException {
		MandelbrotCalculationProperties cut = MandelbrotCalculationProperties.of(yamlFilename);
		assertEquals(expectedWidth, cut.getImageWidth());
		assertEquals(expectedHeight, cut.getImageHeight());
	}

	/**
	 * Make the creation of the point a little bit shorter.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private static MandelbrotPointPosition point(double x, double y) {
		return MandelbrotPointPosition.of(x, y);
	}

	private static Stream<Arguments> testOfAspectRatioCalculationOk() {
		return Stream.of(
		    // Bottom right x is sought-after
		    Arguments.of("src/test/resources/mandelbrot-test-ar-brcx.yaml", point(-2.02, 1.2), point(0.7, -1.2)),
		    // Bottom right x is sought-after
		    Arguments.of("src/test/resources/mandelbrot-test-ar-brcy.yaml", point(-2.02, 1.2), point(0.7, -1.2)),
		    // Bottom right x is sought-after
		    Arguments.of("src/test/resources/mandelbrot-test-ar-tlcx.yaml", point(-2.02, 1.2), point(0.7, -1.2)),
		    // Bottom right x is sought-after
		    Arguments.of("src/test/resources/mandelbrot-test-ar-tlcy.yaml", point(-2.02, 1.2), point(0.7, -1.2)));
	}

	/**
	 * 
	 * @param yamlFilename
	 * @param expectedTopLeft
	 * @param expectedBottomRight
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ParameterizedTest
	@MethodSource
	void testOfAspectRatioCalculationOk(String yamlFilename, MandelbrotPointPosition expectedTopLeft,
	    MandelbrotPointPosition expectedBottomRight) throws JsonParseException, JsonMappingException, IOException {
		MandelbrotCalculationProperties cut = MandelbrotCalculationProperties.of(yamlFilename);
		assertEquals(expectedTopLeft.cx(), cut.getTopLeft().cx(), 0.001);
		assertEquals(expectedTopLeft.cy(), cut.getTopLeft().cy(), 0.001);
		assertEquals(expectedBottomRight.cx(), cut.getBottomRight().cx(), 0.001);
		assertEquals(expectedBottomRight.cy(), cut.getBottomRight().cy(), 0.001);
	}

	@Test
	void testOfAspcetRatioCalculationError() throws JsonParseException, JsonMappingException, IOException {
		assertThrows(IllegalArgumentException.class, () -> {
			MandelbrotCalculationProperties.of("src/test/resources/mandelbrot-test-ar-2values-error.yaml");
		});
	}
}
