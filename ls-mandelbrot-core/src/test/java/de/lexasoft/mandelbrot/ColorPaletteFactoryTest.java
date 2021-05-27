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

	/**
	 * Test method for #ColorPaletteFactory::createRainbowPalette7()
	 */
	@Test
	void testCreateRainbowPalette7() {
		List<Color> result = cut.createRainbowPalette7();
		assertEquals(7, result.size(), "Size of list is not correct");
		assertEquals(Color.RED, result.get(0));
		assertEquals(Color.ORANGE, result.get(1));
		assertEquals(Color.YELLOW, result.get(2));
		assertEquals(Color.GREEN, result.get(3));
		assertEquals(Color.BLUE, result.get(4));
		assertEquals(new Color(75, 0, 130), result.get(5));
		assertEquals(new Color(136, 0, 255), result.get(6));
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
