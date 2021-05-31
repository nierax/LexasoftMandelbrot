/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
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
		cut.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 5));
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

	/**
	 * Copy should include new objects, but same values.
	 */
	@Test
	void testCopyValues() {
		MandelbrotCalculationProperties clonedProps = cut.cloneValues();
		assertNotNull(clonedProps);
		assertNotSame(cut, clonedProps);

		assertNotSame(cut.getTopLeft(), clonedProps.getTopLeft());
		assertEquals(cut.getTopLeft().cx(), clonedProps.getTopLeft().cx());
		assertEquals(cut.getTopLeft().cy(), clonedProps.getTopLeft().cy());

		assertNotSame(cut.getBottomRight(), clonedProps.getBottomRight());
		assertEquals(cut.getBottomRight().cx(), clonedProps.getBottomRight().cx());
		assertEquals(cut.getBottomRight().cy(), clonedProps.getBottomRight().cy());

		assertEquals(cut.getMaximumIterations(), clonedProps.getMaximumIterations());
		assertEquals(cut.getImageWidth(), clonedProps.getImageWidth());
		assertEquals(cut.getImageHeight(), clonedProps.getImageHeight());
		assertEquals(cut.getImageFilename(), clonedProps.getImageFilename());

		assertEquals(cut.getPaletteVariant(), clonedProps.getPaletteVariant());
		assertNotSame(cut.getCustomColorPalette(), clonedProps.getCustomColorPalette());
		assertEquals(cut.getCustomColorPalette().size(), clonedProps.getCustomColorPalette().size());
		for (int i = 0; i < cut.getCustomColorPalette().size(); i++) {
			assertEquals(cut.getCustomColorPalette().get(i), clonedProps.getCustomColorPalette().get(i));
		}

		assertEquals(cut.getColorGrading(), clonedProps.getColorGrading());
		assertEquals(cut.getMandelbrotColor(), clonedProps.getMandelbrotColor());
	}

	/**
	 * If the properties to be copied are empty, the result should be a new object,
	 * which is empty, too.
	 */
	@Test
	void testCopyValuesEmptyProperties() {
		// New empty object
		MandelbrotCalculationProperties cut = MandelbrotCalculationProperties.of();
		// Now clone this
		MandelbrotCalculationProperties clonedProps = cut.cloneValues();

		assertNotNull(clonedProps);
		assertNotSame(cut, clonedProps);
		assertNull(clonedProps.getTopLeft());
		assertNull(clonedProps.getBottomRight());
		assertEquals(0, clonedProps.getMaximumIterations());
		assertEquals(0, clonedProps.getImageWidth());
		assertEquals(0, clonedProps.getImageHeight());
		assertNull(clonedProps.getImageFilename());
		assertNull(clonedProps.getPaletteVariant());
		assertNull(clonedProps.getCustomColorPalette());
		assertNull(clonedProps.getColorGrading());
		assertNull(clonedProps.getMandelbrotColor());
	}

	private static Stream<Arguments> testAdd2ImageFilename() {
		return Stream.of(
		    // Tiff with 0
		    Arguments.of("./junit-tmp/mandelbrot-test.tiff", 1, 0, "./junit-tmp/mandelbrot-test_0.tiff"),
		    // Tiff with 5
		    Arguments.of("./junit-tmp/mandelbrot-test.tiff", 1, 5, "./junit-tmp/mandelbrot-test_5.tiff"),
		    // Jpg with 1
		    Arguments.of("./junit-tmp/mandelbrot-test.jpg", 2, 1, "./junit-tmp/mandelbrot-test_01.jpg"),
		    // Png with 99
		    Arguments.of("./junit-tmp/mandelbrot-test.png", 4, 99, "./junit-tmp/mandelbrot-test_0099.png"),
		    // Tiff with 1234
		    Arguments.of("./junit-tmp/mandelbrot-test.tif", 4, 1234, "./junit-tmp/mandelbrot-test_1234.tif"),
		    // Tiff with 12345
		    Arguments.of("./junit-tmp/mandelbrot-test.gif", 4, 12345, "./junit-tmp/mandelbrot-test_12345.gif"),
		    // Tiff with 12345
		    Arguments.of("./junit-tmp/mandelbrot-test.gif", 5, 12345, "./junit-tmp/mandelbrot-test_12345.gif"),
		    // Tiff with 12345
		    Arguments.of("./junit-tmp/mandelbrot-test.gif", 6, 12345, "./junit-tmp/mandelbrot-test_012345.gif"),
		    // No leading 0
		    Arguments.of("./junit-tmp/mandelbrot-test.tiff", 0, 1, "./junit-tmp/mandelbrot-test_1.tiff"),
		    // nrOfDigits < 0 -> ignore
		    Arguments.of("./junit-tmp/mandelbrot-test.tiff", -1, 15, "./junit-tmp/mandelbrot-test_15.tiff"));
	}

	/**
	 * Checks, whether the
	 * {@link MandelbrotCalculationProperties#addIndex2ImageFilename(int)} method
	 * adds the index correctly to the file name
	 * <p>
	 * The index will be added, divided by "_" before the file type in the filename.
	 * It is filled up with "0" to the length of nrOfDigits digits. If the index has
	 * more than nrOfDigits digits, than the index will be added in its original
	 * length.
	 * 
	 * @param imageFilename
	 * @param nrOfDigits
	 * @param idx
	 * @param expected
	 */
	@ParameterizedTest
	@MethodSource
	void testAdd2ImageFilename(String imageFilename, int nrOfDigits, int idx, String expected) {
		cut.setImageFilename(imageFilename);
		String changedFilename = cut.addIndex2ImageFilename(nrOfDigits, idx);
		// new file name must also have been set.
		assertEquals(changedFilename, cut.getImageFilename());
		// Is the index added to the file name?
		assertEquals(expected, changedFilename);
	}
}
