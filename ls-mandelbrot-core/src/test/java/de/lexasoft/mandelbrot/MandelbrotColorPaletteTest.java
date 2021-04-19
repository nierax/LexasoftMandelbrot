/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
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
class MandelbrotColorPaletteTest {

	private MandelbrotColorPalette cut;
	private Color gradientStart;
	private Color gradientEnd;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		gradientStart = Color.BLUE;
		gradientEnd = Color.RED;
		cut = new MandelbrotColorPalette(gradientStart, gradientEnd, 500);
	}

	/**
	 * Constructor calculates deltas. We should check these ;-).
	 */
	@Test
	void testConstructor() {
		assertEquals(0.51f, cut.stepRed(), 0.001f, "Step red was not correctly calculated.");
		assertEquals(0f, cut.stepGreen(), 0.001f, "Step green was not correctly calculated.");
		assertEquals(-0.51f, cut.stepBlue(), 0.001f, "Step blue was not correctly calculated.");
	}

	private static Stream<Arguments> testGetColorForIteration() {
		return Stream.of(
		    // Start of the color gradient
		    Arguments.of(0, Color.BLUE),
		    // Member of Mandelbrot Set
		    Arguments.of(500, Color.BLACK),
		    // End of the color gradient
		    Arguments.of(499, new Color(254, 0, 0)),
		    // Half way through the color gradient
		    Arguments.of(250, new Color(127, 0, 127)));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotColorPalette#getColorForIteration(int)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testGetColorForIteration(int iteration, Color expected) {
		Color result = cut.getColorForIteration(iteration);
		assertEquals(expected.getRed(), result.getRed(), "Color red was not correctly calculated");
		assertEquals(expected.getGreen(), result.getGreen(), "Color green was not correctly calculated");
		assertEquals(expected.getBlue(), result.getBlue(), "Color blue was not correctly calculated");
	}

}
