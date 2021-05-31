/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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
 * @author nierax
 *
 */
class TransitionFactoryTest {

	private TransitionFactory cut;
	private MandelbrotCalculationProperties start;
	private MandelbrotCalculationProperties end;
	private Transition transition;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		// Transition starts at
		start = new MandelbrotCalculationProperties();
		start.setImageWidth(459);
		start.setImageHeight(405);
		start.setImageFilename("./junit-tmp/mandelbrot-test-transition.tiff");
		start.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> customPalette = new ArrayList<>();
		customPalette.add(new Color(25, 140, 255));
		customPalette.add(new Color(255, 255, 255));
		start.setCustomColorPalette(customPalette);
		start.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 5));
		start.setMandelbrotColor(Color.BLACK);
		// Transition ends at
		end = start.cloneValues();
		// Transition parameters
		transition = Transition.of(2, TransitionVariant.LINEAR);
		cut = new TransitionFactory(transition);
	}

	private static MandelbrotPointPosition point(double cx, double cy) {
		return MandelbrotPointPosition.of(cx, cy);
	}

	private static Stream<Arguments> testCreateTransitionsOk() {
		return Stream.of(
		    // All arguments given
		    Arguments.of(point(-2.02, 1.2), point(0.7, -1.2), point(-1.12, 0.3), point(-0.2, -0.3), 10, 40),
		    // Top left cx is not given
		    Arguments.of(point(Double.NaN, 1.2), point(0.7, -1.2), point(Double.NaN, 0.3), point(-0.2, -0.3), 10, 40));
	}

	private double expected(double given, double expected) {
		return (Double.isNaN(given) ? Double.NaN : expected);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.TransitionFactory#createTransitions(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testCreateTransitionsOk(MandelbrotPointPosition tlStart, MandelbrotPointPosition brStart,
	    MandelbrotPointPosition tlEnd, MandelbrotPointPosition brEnd, int mTStart, int mTEnd) {
		start.setTopLeft(tlStart);
		start.setBottomRight(brStart);
		start.setMaximumIterations(mTStart);
		end.setTopLeft(tlEnd);
		end.setBottomRight(brEnd);
		end.setMaximumIterations(mTEnd);

		List<MandelbrotCalculationProperties> transitions = cut.createTransitions(start, end);
		assertNotNull(transitions);
		assertEquals(2, transitions.size());

		// First step
		MandelbrotCalculationProperties props = transitions.get(0);
		assertNotNull(props);
		assertEquals(expected(tlStart.cx(), -1.72d), props.getTopLeft().cx(), 0.001);
		assertEquals(expected(tlStart.cy(), 0.9d), props.getTopLeft().cy(), 0.001);
		assertEquals(expected(brStart.cx(), 0.4d), props.getBottomRight().cx(), 0.001);
		assertEquals(expected(brStart.cy(), -0.9d), props.getBottomRight().cy(), 0.001);
		assertEquals(20, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-transition.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading().getColorsTotal());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Second step
		props = transitions.get(1);
		assertNotNull(props);
		assertEquals(expected(tlStart.cx(), -1.42d), props.getTopLeft().cx(), 0.001);
		assertEquals(expected(tlStart.cy(), 0.6d), props.getTopLeft().cy(), 0.001);
		assertEquals(expected(brStart.cx(), 0.1d), props.getBottomRight().cx(), 0.001);
		assertEquals(expected(brStart.cy(), -0.6d), props.getBottomRight().cy(), 0.001);
		assertEquals(30, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-transition.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading().getColorsTotal());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

	}

	/**
	 * If the step is below 1, an {@link IllegalArgumentException} is thrown.
	 */
	@Test
	void testOfTooFewSteps() {
		assertThrows(IllegalArgumentException.class, () -> {
			TransitionFactory.of(Transition.of(0, TransitionVariant.LINEAR));
		});
	}

	private static Stream<Arguments> testOf() {
		return Stream.of(Arguments.of(Transition.of(100, TransitionVariant.LINEAR), TransitionFactory.class),
		    Arguments.of(Transition.of(100, TransitionVariant.SOFT_INOUT), SoftTransitionFactory.class),
		    Arguments.of(Transition.of(100, TransitionVariant.SOFT_IN), SoftTransitionFactory.class),
		    Arguments.of(Transition.of(100, TransitionVariant.SOFT_OUT), SoftTransitionFactory.class));
	}

	/**
	 * Does the of() method produce the right TransitionFactory object?
	 * 
	 * @param transition
	 */
	@ParameterizedTest
	@MethodSource
	void testOf(Transition transition, Class<?> expected) {
		TransitionFactory cut = TransitionFactory.of(transition);
		assertNotNull(cut);
		assertEquals(expected, cut.getClass());
	}

}
