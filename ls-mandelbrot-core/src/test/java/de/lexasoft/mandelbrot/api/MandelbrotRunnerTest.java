/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.MandelbrotRunnerChain;
import de.lexasoft.mandelbrot.MandelbrotSingleRunner;

/**
 * @author admin
 *
 */
class MandelbrotRunnerTest {

	private MandelbrotCalculationProperties props1;
	private MandelbrotCalculationProperties props2;
	private List<MandelbrotCalculationProperties> listOfProps;

	private MandelbrotCalculationProperties createProps() {
		MandelbrotCalculationProperties props = MandelbrotCalculationProperties.of();
		props.setTopLeft(MandelbrotPointPosition.of(-2.02, 1.2));
		props.setBottomRight(MandelbrotPointPosition.of(0.7, -1.2));
		props.setMaximumIterations(500);
		props.setImageWidth(4590);
		props.setImageHeight(4050);
		props.setImageFilename("./junit-tmp/mandelbrot-test.tiff");
		props.setPaletteVariant(PaletteVariant.CUSTOM);
		List<Color> customPalette = new ArrayList<>();
		customPalette.add(new Color(25, 140, 255));
		customPalette.add(new Color(255, 255, 255));
		props.setCustomColorPalette(customPalette);
		props.setColorGrading(5);
		props.setMandelbrotColor(Color.BLACK);
		return props;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		props1 = createProps();
		props1.setImageFilename("props1");
		props2 = createProps();
		props2.setImageFilename("props2");
		listOfProps = new ArrayList<>();
		listOfProps.add(props1);
		listOfProps.add(props2);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotRunner#of(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties)}.
	 */
	@Test
	final void testOfMandelbrotCalculationProperties() {
		MandelbrotRunner result = MandelbrotRunner.of(props1);
		assertTrue(result instanceof MandelbrotSingleRunner);
	}

	/**
	 * Test method for {@link MandelbrotRunner#of(List)}, when there are tow entries
	 * in the list.
	 */
	@Test
	final void testOfListOfMandelbrotCalculationProperties() {
		MandelbrotRunner result = MandelbrotRunner.of(listOfProps);
		assertTrue(result instanceof MandelbrotRunnerChain);
		MandelbrotRunnerChain chain = (MandelbrotRunnerChain) result;
		assertEquals(2, chain.nrOfRunners());
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.api.MandelbrotRunner#of(java.util.List)}, when
	 * the list contains one entry, only.
	 */
	@Test
	void testCreateRunnerListOfMandelbrotCalculationProperties1Entry() {
		List<MandelbrotCalculationProperties> listOneEntry = new ArrayList<>();
		listOneEntry.add(props1);
		MandelbrotRunner result = MandelbrotRunner.of(listOneEntry);
		assertTrue(result instanceof MandelbrotSingleRunner);
	}

}
