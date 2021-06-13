/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;

/**
 * @author nierax
 *
 */
class MandelbrotControllerTest {

	private MandelbrotController cut;
	private MandelbrotCalculationProperties model;
	private MandelbrotSwingView view;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotCalculationProperties.ofDefault();
		view = new MandelbrotSwingView();
		cut = new MandelbrotController(model, view);
	}

	/**
	 * Check, whether the correct connections are made, as this is the main task for
	 * this controller and is very important for the application to work correctly.
	 * <p>
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.MandelbrotController#initController()}.
	 */
	@Test
	final void testInitController() {
		// Make connections.
		cut.initController();

		// Check, whether they are correct
		List<ModelChangedListener<ColorControllerModel>> queueColor = cut.getColorController().getQueue();
		List<ModelChangedListener<CalculationControllerModel>> queueCalc = cut.getCalculationController().getQueue();

		// Exactly one entry
		assertEquals(1, queueColor.size());
		assertEquals(1, queueCalc.size());
		// Since we are using lambda expressions here, we don't know anything about the
		// instances.
	}

}
