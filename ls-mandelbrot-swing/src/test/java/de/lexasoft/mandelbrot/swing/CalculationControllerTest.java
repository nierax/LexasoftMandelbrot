/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.ctrl.CalculationAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

	private MandelbrotAttributesDTO model;
	@Mock
	private CalculationPanel view;
	@Mock
	private JTextField tlCx;
	@Mock
	private JTextField tlCy;
	@Mock
	private JTextField brCx;
	@Mock
	private JTextField brCy;
	@Mock
	private JTextField maxIter;
	@Mock
	private JComboBox<AspectRatio> aspetRatio;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotAttributesDTO.ofDefaults();
		view = mock(CalculationPanel.class);
		tlCx = mock(JTextField.class);
		tlCy = mock(JTextField.class);
		brCx = mock(JTextField.class);
		brCy = mock(JTextField.class);
		aspetRatio = mock(JComboBox.class);
		when(view.getTlcx()).thenReturn(tlCx);
		when(view.getTlcy()).thenReturn(tlCy);
		when(view.getBrcx()).thenReturn(brCx);
		when(view.getBrcy()).thenReturn(brCy);
		when(view.getMaxIter()).thenReturn(maxIter);
		when(view.getAspectRatio()).thenReturn(aspetRatio);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.CalculationController#CalculationController(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.swing.CalculationPanel)}.
	 */
	@Test
	final void testCalculationController() {
		// Call constructor
		CalculationController cut = new CalculationController(model, view);

		// Check the result
		assertNotNull(cut); // Should never fail
		// Controller model
		CalculationAttributesDTO calc = model.getCalculation();
		assertEquals(calc.getTopLeft(), cut.topLeft());
		assertEquals(calc.getBottomRight(), cut.bottomRight());
		assertEquals(calc.getMaximumIterations(), cut.maximumIterations());
		// View initialized?
		verify(tlCx).setText(Double.toString(calc.getTopLeft().cx()));
		verify(tlCy).setText(Double.toString(calc.getTopLeft().cy()));
		verify(brCx).setText(Double.toString(calc.getBottomRight().cx()));
		verify(brCy).setText(Double.toString(calc.getBottomRight().cy()));
		verify(maxIter).setText(Integer.toString(calc.getMaximumIterations()));
		verify(aspetRatio).setSelectedItem(AspectRatio.FILL);
	}

}
