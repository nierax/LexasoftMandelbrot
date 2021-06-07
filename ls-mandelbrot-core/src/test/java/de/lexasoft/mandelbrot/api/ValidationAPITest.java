/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class ValidationAPITest {

	ValidationAPI cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = ValidationAPI.of();
	}

	private static Stream<Arguments> testMinimumNrOfColorsForGrading() {
		return Stream.of(Arguments.of(2, ColorGradingStyle.LINE, 3), Arguments.of(2, ColorGradingStyle.CIRCLE, 4),
		    Arguments.of(7, ColorGradingStyle.LINE, 13), Arguments.of(7, ColorGradingStyle.CIRCLE, 14),
		    Arguments.of(29, ColorGradingStyle.LINE, 57), Arguments.of(29, ColorGradingStyle.CIRCLE, 58),
		    Arguments.of(29, ColorGradingStyle.NONE, 0));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.ValidationAPI#minimumNrOfColorsForGrading(int, de.lexasoft.mandelbrot.api.ColorGradingStyle)}.
	 */
	@ParameterizedTest
	@MethodSource
	final void testMinimumNrOfColorsForGrading(int nrOfColorsUngraded, ColorGradingStyle gradingStyle, int expected) {
		int result = cut.minimumNrOfColorsForGrading(nrOfColorsUngraded, gradingStyle);
		assertEquals(expected, result);
	}

}
