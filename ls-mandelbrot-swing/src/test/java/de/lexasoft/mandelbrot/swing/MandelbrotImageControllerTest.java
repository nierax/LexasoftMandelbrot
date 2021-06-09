/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class MandelbrotImageControllerTest {

	private MandelbrotCalculationProperties model;
	private MandelbrotImageController cut;
	@Mock
	private ImagePanel view;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotCalculationProperties.ofDefault();
		view = mock(ImagePanel.class);
		cut = new MandelbrotImageController(model, view);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.MandelbrotImageController#initView()}.
	 */
	@Test
	final void testInitView() {
		// initView() is called within the constructor, so we can check immediately.
		// Is the size set correctly?
		verify(view).setPreferredSize(new Dimension(459, 405));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.MandelbrotImageController#calculate()}.
	 */
	@Test
	final void testCalculate() {
		// Prepare
		when(view.getWidth()).thenReturn(459);
		when(view.getHeight()).thenReturn(405);
		BufferedImage result = cut.calculate();
		assertNotNull(result);
		assertEquals(459, result.getWidth());
		assertEquals(405, result.getHeight());
		verify(view).setPreferredSize(new Dimension(459, 405));
	}

}
