/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		assertEquals(PointDTO.of("-2.02d", "1.2d"), cut.getTopLeft());
		assertEquals(PointDTO.of("0.8d", "-1.2d"), cut.getBottomRight());
		assertEquals(25, cut.getMaximumIterations());
	}

}
