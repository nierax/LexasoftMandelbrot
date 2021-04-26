/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author admin
 *
 */
class ColorPaletteFactoryTest {

	private ColorPaletteFactory cut;
	private static List<Color> palette3Entries;
	private static List<Color> palette4Entries;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ColorPaletteFactory();
		palette3Entries = new ArrayList<>();
		palette3Entries.add(Color.RED);
		palette3Entries.add(Color.GREEN);
		palette3Entries.add(Color.BLUE);
		palette4Entries = new ArrayList<>();
		palette4Entries.add(Color.RED);
		palette4Entries.add(Color.GREEN);
		palette4Entries.add(Color.BLUE);
		palette4Entries.add(Color.ORANGE);
	}

	private static Stream<Arguments> testCreateGradientList2() {
		return Stream.of(
		    // First color
		    Arguments.of(Color.BLUE, Color.RED, 32, 0, Color.BLUE),
		    // Last color
		    Arguments.of(Color.BLUE, Color.RED, 32, 31, Color.RED),
		    // Half way
		    Arguments.of(Color.BLUE, Color.RED, 32, 16, new Color(127, 0, 127)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ColorPaletteFactory#createGradientList(java.awt.Color, java.awt.Color, int)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testCreateGradientList2(Color color1, Color color2, int nrOfSteps, int step, Color expected) {
		List<Color> result = cut.createGradientList(color1, color2, nrOfSteps);
		assertEquals(nrOfSteps, result.size(), "Size of the list was not right.");
		Color cStep = result.get(step);
		assertEquals(expected.getRed(), cStep.getRed(), "Red color was not correctly calculated.");
		assertEquals(expected.getGreen(), cStep.getGreen(), "Green color was not correctly calculated.");
		assertEquals(expected.getBlue(), cStep.getBlue(), "Blue color was not correctly calculated.");
	}

	private static Stream<Arguments> testCreateGradientList3() {
		return Stream.of(
		    // First color
		    Arguments.of(Color.BLUE, Color.WHITE, Color.RED, 32, 0, Color.BLUE),
		    // Last color
		    Arguments.of(Color.BLUE, Color.WHITE, Color.RED, 32, 31, Color.RED),
		    // Central color
		    Arguments.of(Color.BLUE, Color.WHITE, Color.RED, 32, 15, Color.WHITE),
		    // Half way blue to white
		    Arguments.of(Color.BLUE, Color.WHITE, Color.RED, 32, 8, new Color(127, 127, 255)),
		    // Half way white to red
		    Arguments.of(Color.BLUE, Color.WHITE, Color.RED, 32, 23, new Color(255, 127, 127)));
	}

	/**
	 * 
	 * @param color1
	 * @param color2
	 * @param color3
	 * @param nrOfSteps
	 * @param step
	 * @param expected
	 */
	@ParameterizedTest
	@MethodSource
	void testCreateGradientList3(Color color1, Color color2, Color color3, int nrOfSteps, int step, Color expected) {
		List<Color> result = cut.createGradientList(color1, color2, color3, nrOfSteps);
		assertEquals(nrOfSteps, result.size(), "Size of the list was not right.");
		Color cStep = result.get(step);
		assertEquals(expected.getRed(), cStep.getRed(), "Red color was not correctly calculated.");
		assertEquals(expected.getGreen(), cStep.getGreen(), "Green color was not correctly calculated.");
		assertEquals(expected.getBlue(), cStep.getBlue(), "Blue color was not correctly calculated.");
	}

	/**
	 * Test method for #ColorPaletteFactory::createRainbowPalette29()
	 */
	@Test
	void testCreateRainbowPalette29() {
		List<Color> result = cut.createRainbowPalette29();
		assertEquals(29, result.size(), "Size of list is not correct");
		// First color
		assertEquals(128, result.get(0).getRed(), "First color not correct in red.");
		assertEquals(0, result.get(0).getGreen(), "First color not correct in green.");
		assertEquals(0, result.get(0).getBlue(), "First color not correct in blue.");
		// Last color
		assertEquals(168, result.get(28).getRed(), "Last color not correct in red.");
		assertEquals(0, result.get(28).getGreen(), "Last color not correct in green.");
		assertEquals(185, result.get(28).getBlue(), "Last color not correct in blue.");
	}

	private static Stream<Arguments> testCreateGradientList() {
		return Stream.of(
		    // Steps should be equal all over.
		    Arguments.of(palette4Entries, 19, new int[] { 0, 6, 12, 18 }),
		    // One step more for the last color (because of a modulo 1)
		    Arguments.of(palette4Entries, 20, new int[] { 0, 6, 12, 19 }),
		    // One step more for the last 2 colors (because of a modulo 2)
		    Arguments.of(palette4Entries, 21, new int[] { 0, 6, 13, 20 }),
		    // At least 7 steps for a 4 entry palette
		    Arguments.of(palette4Entries, 7, new int[] { 0, 2, 4, 6 }),
		    // At least 5 steps for a 3 entry palette
		    Arguments.of(palette3Entries, 5, new int[] { 0, 2, 4 }));
	}

	@ParameterizedTest
	@MethodSource
	void testCreateGradientList(List<Color> ungraded, int gradient, int[] expectedIdx) {
		List<Color> result = cut.createGradientList(ungraded, gradient);
		assertNotNull(result);
		assertEquals(gradient, result.size());
		for (int i = 0; i < expectedIdx.length; i++) {
			int j = expectedIdx[i];
			assertEquals(ungraded.get(i), result.get(j));
			if (j < result.size() - 1) {
				// The next color must not be the same some original
				assertNotEquals(ungraded.get(i), result.get(j + 1));
			}
			if (j > 0) {
				// The previous color must not be the same some original
				assertNotEquals(ungraded.get(i), result.get(j - 1));
			}
		}
	}

	private static Stream<Arguments> testCreateGradientListGradientTooLow() {
		return Stream.of(
		    // 6 Is not enough for a 4 entries palette.
		    Arguments.of(palette4Entries, 6),
		    // 4 Is not enough for a 3 entries palette.
		    Arguments.of(palette3Entries, 4));
	}

	@ParameterizedTest
	@MethodSource
	void testCreateGradientListGradientTooLow(List<Color> ungraded, int gradient) {
		assertThrows(IllegalArgumentException.class, () -> {
			cut.createGradientList(ungraded, gradient);
		});
	}

}
