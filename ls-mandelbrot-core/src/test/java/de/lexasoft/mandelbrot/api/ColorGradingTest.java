/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.ColorGradingCircle;
import de.lexasoft.mandelbrot.ColorGradingLine;

/**
 * Test the factory method of the interface {@link ColorGrading}
 * 
 * @author nierax
 *
 */
class ColorGradingTest {

	private static Stream<Arguments> testOfOk() {
		return Stream.of(
		    // Line implementation
		    Arguments.of(ColorGradingStyle.LINE, ColorGradingLine.class),
		    // Circle implementation
		    Arguments.of(ColorGradingStyle.CIRCLE, ColorGradingCircle.class));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.ColorGrading#of(de.lexasoft.mandelbrot.api.ColorGradingStyle)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testOfOk(ColorGradingStyle style, Class<?> expected) {
		ColorGrading result = ColorGrading.of(style);
		assertEquals(result.getClass(), expected);
	}

}
