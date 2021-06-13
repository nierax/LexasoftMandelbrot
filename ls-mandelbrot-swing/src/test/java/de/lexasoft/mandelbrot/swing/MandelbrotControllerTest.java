/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
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
		List<ModelChangedListener<ColorControllerModel>> queue = cut.getColorController().getQueue();
		// Exactly one entry
		assertEquals(1, queue.size());
		// This is the image controller, which draws the Mandelbrot image
		assertSame(cut.getImageController(), queue.get(0));
	}

}
