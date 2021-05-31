/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The color grader should be tested to the right calculation on behalf of the
 * correct rounding.
 * 
 * @author nierax
 *
 */
class ColorGraderTest {

	private static List<Color> expected(Color... colors) {
		List<Color> colorList = new ArrayList<>();
		for (Color color : colors) {
			colorList.add(color);
		}
		return colorList;
	}

	private static Stream<Arguments> testGradeColors() {
		return Stream.of(
		    // Just one step between red and blue
		    Arguments.of(Color.RED, Color.BLUE, 1, expected(new Color(128, 0, 128))),
		    // Three steps between blue and white
		    Arguments.of(Color.BLUE, Color.WHITE, 3,
		        expected(new Color(64, 64, 255), new Color(128, 128, 255), new Color(191, 191, 255))));
	}

	/**
	 * Check the grading calculation in detail.
	 * 
	 * @param start     The start color
	 * @param end       The end color
	 * @param nrOfSteps The number of entries in the list
	 * @param expected  The expected list
	 */
	@ParameterizedTest
	@MethodSource
	final void testGradeColors(Color start, Color end, int nrOfSteps, List<Color> expected) {
		ColorGrader cut = ColorGrader.of(start, end, nrOfSteps);
		List<Color> received = cut.gradeColors();
		assertNotNull(received);
		assertEquals(expected.size(), received.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), received.get(i));
		}
	}

}
