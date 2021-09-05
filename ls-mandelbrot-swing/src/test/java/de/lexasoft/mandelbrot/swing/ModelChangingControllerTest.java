/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author nierax
 *
 */
class ModelChangingControllerTest {

	private ModelChangingController<String> cut;
	private int count;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		count = 0;
		cut = new ModelChangingController<>();
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.ModelChangingController#fireModelChangedEvent(de.lexasoft.mandelbrot.swing.ModelChangedEvent)}.
	 */
	@Test
	final void testFireModelChangedEvent() {
		ModelChangedEvent<String> event = new ModelChangedEvent<String>(cut, "event");
		// Add 3 listener
		for (int i = 0; i < 3; i++) {
			cut.addModelChangedListener(e -> {
				count++;
				// Is the right event fired?
				assertSame(event, e);
			});
		}
		// Let it run;
		cut.fireModelChangedEvent(event);
		assertEquals(3, count);
	}

}
