/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
 *
 */
class CalculationAttributesDTOTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.ctrl.CalculationAttributesDTO#ofDefault()}.
	 */
	@Test
	final void testOfDefault() {
		CalculationAttributesDTO cut = CalculationAttributesDTO.ofDefault();
		assertNotNull(cut);
		assertEquals(MandelbrotPointPosition.of(-2.02d, 1.2d), cut.getTopLeft());
		assertEquals(MandelbrotPointPosition.of(0.8d, -1.2d), cut.getBottomRight());
		assertEquals(25, cut.getMaximumIterations());
	}

}
