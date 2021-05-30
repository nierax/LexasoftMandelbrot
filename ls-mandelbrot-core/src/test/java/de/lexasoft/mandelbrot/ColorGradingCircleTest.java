/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class ColorGradingCircleTest {

	private ColorGradingCircle cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ColorGradingCircle();
	}

	private static List<Color> palette(Color... colors) {
		List<Color> list = new ArrayList<>();
		for (Color color : colors) {
			list.add(color);
		}
		return list;
	}

	private static Stream<Arguments> testGradePalette() {
		return Stream.of(
		    // Rainbow7 with one step between
		    Arguments.of(ColorPaletteFactory.of().createRainbowPalette7(), 14, new int[] { 0, 2, 4, 6, 8, 10, 12 }),
		    // Blue white with 3 steps between
		    Arguments.of(palette(new Color(25, 140, 255), Color.WHITE), 8, new int[] { 0, 4 }),
		    // Blue white with 1 step between
		    Arguments.of(palette(new Color(25, 140, 255), Color.WHITE), 4, new int[] { 0, 2 }),
		    // Rainbow7 with one step between and a remainder of 2
		    Arguments.of(ColorPaletteFactory.of().createRainbowPalette7(), 16, new int[] { 0, 2, 4, 6, 8, 10, 13 }));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ColorGradingLine#gradePalette(java.util.List, int)}.
	 * 
	 * @param ungraded          The original, ungraded palette
	 * @param grading           The grading, that should be used
	 * @param originalPositions The positions, on which the original colors are
	 *                          expected.
	 */
	@ParameterizedTest
	@MethodSource
	final void testGradePalette(List<Color> ungraded, int grading, int[] originalPositions) {
		List<Color> result = cut.gradePalette(ungraded, grading);
		assertNotNull(result);
		assertEquals(grading, result.size());
		for (int i = 0; i < ungraded.size(); i++) {
			assertEquals(ungraded.get(i), result.get(originalPositions[i]));
		}
	}

	private static Stream<Arguments> testGradePaletteTooLow() {
		return Stream.of(
		    // 13 Is not enough for a 7 entries palette.
		    Arguments.of(ColorPaletteFactory.of().createRainbowPalette7(), 13),
		    // 3 Is not enough for a 2 entries palette.
		    Arguments.of(palette(Color.BLUE, Color.WHITE), 3));
	}

	@ParameterizedTest
	@MethodSource
	final void testGradePaletteTooLow(List<Color> ungraded, int grading) {
		assertThrows(IllegalArgumentException.class, () -> {
			cut.gradePalette(ungraded, grading);
		});

	}
}
