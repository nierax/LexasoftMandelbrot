/**
 * 
 */
package de.lexasoft.mandelbrot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author admin
 *
 */
class MandelbrotRunnerFactoryTest {

	private MandelbrotRunnerFactory cut;
	private MandelbrotCalculationProperties props1;
	private MandelbrotCalculationProperties props2;
	private List<MandelbrotCalculationProperties> listOfProps;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		cut = new MandelbrotRunnerFactory();
		props1 = MandelbrotCalculationProperties.of("src/test/resources/mandelbrot-test.yaml");
		props1.setImageFilename("props1");
		props2 = MandelbrotCalculationProperties.of("src/test/resources/mandelbrot-test.yaml");
		props2.setImageFilename("props2");
		listOfProps = new ArrayList<>();
		listOfProps.add(props1);
		listOfProps.add(props2);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotRunnerFactory#createRunner(de.lexasoft.mandelbrot.MandelbrotCalculationProperties)}.
	 */
	@Test
	void testCreateRunnerMandelbrotCalculationProperties() {
		MandelbrotRunner result = cut.createRunner(props1);
		assertTrue(result instanceof MandelbrotSingleRunner);
		MandelbrotSingleRunner singleRunner = (MandelbrotSingleRunner) result;
		assertEquals("props1", singleRunner.getImageFilename());
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.MandelbrotRunnerFactory#createRunner(java.util.List)}.
	 */
	@Test
	void testCreateRunnerListOfMandelbrotCalculationProperties() {
		MandelbrotRunner result = cut.createRunner(listOfProps);
		assertTrue(result instanceof MandelbrotRunnerChain);
		MandelbrotRunnerChain chain = (MandelbrotRunnerChain) result;
		assertEquals(2, chain.runners().size());
		assertEquals("props1", ((MandelbrotSingleRunner) chain.runners().get(0)).getImageFilename());
		assertEquals("props2", ((MandelbrotSingleRunner) chain.runners().get(1)).getImageFilename());
	}

}
