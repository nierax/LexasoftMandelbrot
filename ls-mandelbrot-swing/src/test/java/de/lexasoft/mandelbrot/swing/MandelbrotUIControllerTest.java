/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;

/**
 * @author nierax
 *
 */
class MandelbrotUIControllerTest {

	private MandelbrotUIController cut;
	private MandelbrotAttributesDTO model;
	private MandelbrotSwingView view;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotAttributesDTO.ofDefaults();
		view = new MandelbrotSwingView();
		cut = new MandelbrotUIController(model, view);
	}

	/**
	 * Check, whether the correct connections are made, as this is the main task for
	 * this controller and is very important for the application to work correctly.
	 * <p>
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.MandelbrotUIController#initController()}.
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
