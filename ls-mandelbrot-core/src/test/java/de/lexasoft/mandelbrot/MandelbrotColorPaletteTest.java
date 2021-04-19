/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 * @author nierax
 */
class MandelbrotColorPaletteTest {

	private MandelbrotColorPalette cut;
	private final static int MAXIMUM_ITERATIONS = 50;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		List<Color> palette = new ArrayList<>();
		palette.add(Color.WHITE);
		palette.add(Color.GREEN);
		palette.add(Color.BLUE);
		cut = MandelbrotColorPalette.of(palette);
	}

	private static Stream<Arguments> testGetColorForIteration() {
		return Stream.of(
		    // First position
		    Arguments.of(0, Color.WHITE),
		    // Second position
		    Arguments.of(1, Color.GREEN),
		    // Third position
		    Arguments.of(2, Color.BLUE),
		    // Fourth position must be the same as first, because, there are only three
		    // colors in the palette.
		    Arguments.of(3, Color.WHITE),
		    // Fifth same as second
		    Arguments.of(4, Color.GREEN),
		    // Sixth same as third
		    Arguments.of(5, Color.BLUE),
		    // With maximum iteration BLACK should be return.
		    Arguments.of(MAXIMUM_ITERATIONS, Color.BLACK));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotColorPalette#getColorForIteration(int, int)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testGetColorForIteration(int iteration, Color expected) {
		Color result = cut.getColorForIteration(iteration, MAXIMUM_ITERATIONS);
		assertEquals(expected.getRed(), result.getRed(), "Red was not calculated correctly.");
		assertEquals(expected.getGreen(), result.getGreen(), "Green was not calculated correctly.");
		assertEquals(expected.getBlue(), result.getBlue(), "Blue was not calculated correctly.");
	}

}
