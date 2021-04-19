/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author admin
 *
 */
class ColorGradientCalculatorTest {

	private ColorGradientCalculator cut;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new ColorGradientCalculator();
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
	 * {@link de.lexasoft.mandelbrot.ColorGradientCalculator#createGradientList(java.awt.Color, java.awt.Color, int)}.
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

}
