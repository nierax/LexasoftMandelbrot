/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
class MandelbrotCalculationPropertiesTest {

	private MandelbrotCalculationProperties cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotCalculationProperties();
		cut.setTopLeft(MandelbrotPointPosition.of(-2.02, 1.2));
		cut.setBottomRight(MandelbrotPointPosition.of(0.7, -1.2));
		cut.setMaximumIterations(500);
		cut.setImageWidth(4590);
		cut.setImageHeight(4050);
		cut.setImageFilename("./junit-tmp/mandelbrot-test.tiff");
		cut.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> customPalette = new ArrayList<>();
		customPalette.add(new Color(25, 140, 255));
		customPalette.add(new Color(255, 255, 255));
		cut.setCustomColorPalette(customPalette);
		cut.setColorGrading(5);
		cut.setMandelbrotColor(Color.BLACK);
	}

	/**
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@Test
	void testNormalizeAspectRatioError() {
		cut.setImageHeight(0);
		cut.setImageWidth(0);
		assertThrows(IllegalArgumentException.class, () -> {
			cut.normalize();
		});
	}

	private static Stream<Arguments> testNormalizeAspectRatioImageOk() {
		return Stream.of(
		    // Image width given, image height calculated
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), 4590, 0, 4590, 4050),
		    // Image height given, image width calculated
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), 0, 4050, 4590, 4050),
		    // Image width given, image height calculated with cx both points in minus
		    Arguments.of(point(-4, 1), point(-1, -1), 3000, 0, 3000, 2000),
		    // Image width given, image height calculated with cx both points in plus
		    Arguments.of(point(1, 1), point(4, -1), 3000, 0, 3000, 2000));
	}

	/**
	 * Tests the determination of the image width / image height from the aspect
	 * ration of the MandelbrotIterator calculation.
	 * 
	 * @param topLeft        The position top left to relate to.
	 * @param bottomRight    The position bottom right to relate to.
	 * @param imageWidth     The given image width (or 0, if not given)
	 * @param imageHeight    The given image height (or 0, if not given)
	 * @param expectedWidth  Which image width is expected (either given or
	 *                       calculated)
	 * @param expectedHeight Which image height is expected (either given or
	 *                       calculated)
	 */
	@ParameterizedTest
	@MethodSource
	void testNormalizeAspectRatioImageOk(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    int imageWidth, int imageHeight, int expectedWidth, int expectedHeight) {
		cut.setTopLeft(topLeft);
		cut.setBottomRight(bottomRight);
		cut.setImageWidth(imageWidth);
		cut.setImageHeight(imageHeight);

		cut.normalize();

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

	private static Stream<Arguments> testNormalizeAspectRatioCalculationOk() {
		return Stream.of(
		    // Bottom right x is sought-after
		    Arguments.of(point(-2.02, 1.2), point(Double.NaN, -1.2), point(-2.02, 1.2), point(0.7, -1.2)),
		    // Bottom right y is sought-after
		    Arguments.of(point(-2.02, 1.2), point(0.7, Double.NaN), point(-2.02, 1.2), point(0.7, -1.2)),
		    // Top left x is sought-after
		    Arguments.of(point(Double.NaN, 1.2), point(0.7, -1.2), point(-2.02, 1.2), point(0.7, -1.2)),
		    // Top left y is sought-after
		    Arguments.of(point(-2.02, Double.NaN), point(0.7, -1.2), point(-2.02, 1.2), point(0.7, -1.2)));
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
	void testNormalizeAspectRatioCalculationOk(MandelbrotPointPosition topLeft, MandelbrotPointPosition bottomRight,
	    MandelbrotPointPosition expectedTopLeft, MandelbrotPointPosition expectedBottomRight) {
		cut.setTopLeft(topLeft);
		cut.setBottomRight(bottomRight);

		cut.normalize();

		assertEquals(expectedTopLeft.cx(), cut.getTopLeft().cx(), 0.001);
		assertEquals(expectedTopLeft.cy(), cut.getTopLeft().cy(), 0.001);
		assertEquals(expectedBottomRight.cx(), cut.getBottomRight().cx(), 0.001);
		assertEquals(expectedBottomRight.cy(), cut.getBottomRight().cy(), 0.001);
	}

	/**
	 * If more than one parameter is missing, an {@link IllegalArgumentException} is
	 * expected.
	 */
	@Test
	void testNormalizeAspcetRatioCalculationError() {
		cut.getTopLeft().setCx(Double.NaN);
		cut.getBottomRight().setCx(Double.NaN);
		assertThrows(IllegalArgumentException.class, () -> {
			cut.normalize();
		});
	}
}
