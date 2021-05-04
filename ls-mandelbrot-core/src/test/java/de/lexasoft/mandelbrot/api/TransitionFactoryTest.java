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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.PaletteVariant;

/**
 * @author admin
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
		cut = new TransitionFactory();
		// Transition starts at
		start = new MandelbrotCalculationProperties();
		start.setTopLeft(MandelbrotPointPosition.of(-2.02, 1.2));
		start.setBottomRight(MandelbrotPointPosition.of(0.7, -1.2));
		start.setMaximumIterations(10);
		start.setImageWidth(459);
		start.setImageHeight(405);
		start.setImageFilename("./junit-tmp/mandelbrot-test-transition.tiff");
		start.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> customPalette = new ArrayList<>();
		customPalette.add(new Color(25, 140, 255));
		customPalette.add(new Color(255, 255, 255));
		start.setCustomColorPalette(customPalette);
		start.setColorGrading(5);
		start.setMandelbrotColor(Color.BLACK);
		// Transition ends at
		end = start.cloneValues();
		end.setTopLeft(MandelbrotPointPosition.of(-1.2, 0.3));
		end.setBottomRight(MandelbrotPointPosition.of(-0.3, -0.2));
		end.setMaximumIterations(40);
		// Transition parameters
		transition = Transition.of(2, TransitionVariant.LINEAR);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.TransitionFactory#createTransitions(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties)}.
	 */
	@Test
	void testCreateTransitionsOk() {
		List<MandelbrotCalculationProperties> transitions = cut.createTransitions(start, end, transition);
		assertNotNull(transitions);
		assertEquals(2, transitions.size());

		// First step
		MandelbrotCalculationProperties props = transitions.get(0);
		assertNotNull(props);
		assertEquals(-1.72d, props.getTopLeft().cx());
		assertEquals(0.9d, props.getTopLeft().cy());
		assertEquals(0.4d, props.getBottomRight().cx());
		assertEquals(-0.9d, props.getBottomRight().cy());
		assertEquals(20, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-transition.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

		// Second step
		props = transitions.get(1);
		assertNotNull(props);
		assertEquals(-1.42d, props.getTopLeft().cx());
		assertEquals(0.6d, props.getTopLeft().cy());
		assertEquals(0.1d, props.getBottomRight().cx());
		assertEquals(-0.6d, props.getBottomRight().cy());
		assertEquals(20, props.getMaximumIterations());
		assertEquals(459, props.getImageWidth());
		assertEquals(405, props.getImageHeight());
		assertEquals("./junit-tmp/mandelbrot-test-transition.tiff", props.getImageFilename());
		assertSame(PaletteVariant.CUSTOM, props.getPaletteVariant());
		assertEquals(2, props.getCustomColorPalette().size());
		assertEquals(new Color(25, 140, 255), props.getCustomColorPalette().get(0));
		assertEquals(Color.WHITE, props.getCustomColorPalette().get(1));
		assertEquals(5, props.getColorGrading());
		assertEquals(Color.BLACK, props.getMandelbrotColor());

	}

	/**
	 * If the step is below 1, an {@link IllegalArgumentException} is thrown.
	 */
	@Test
	void testCreateTransitionsTooFewSteps() {
		assertThrows(IllegalArgumentException.class, () -> {
			cut.createTransitions(start, end, Transition.of(0, TransitionVariant.LINEAR));
		});
	}

}
