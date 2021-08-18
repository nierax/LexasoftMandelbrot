/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.ctrl.CalculationAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;

/**
 * @author nierax
 *
 */
@ExtendWith(MockitoExtension.class)
class CalculationControllerTest {

	private CalculationController cut;

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
	private JComboBox<AspectRatio> aspectRatio;
	@Mock
	private JButton btnCalculate;
	@Mock
	private ModelChangedListener<CalculationControllerModel> listener;

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
		btnCalculate = mock(JButton.class);
		aspectRatio = mock(JComboBox.class);
		when(view.getTlcx()).thenReturn(tlCx);
		when(view.getTlcy()).thenReturn(tlCy);
		when(view.getBrcx()).thenReturn(brCx);
		when(view.getBrcy()).thenReturn(brCy);
		when(view.getMaxIter()).thenReturn(maxIter);
		when(view.getAspectRatio()).thenReturn(aspectRatio);
		cut = new CalculationController(model.getCalculation(), view);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.CalculationController#CalculationController(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.swing.CalculationPanel)}.
	 */
	@Test
	final void testCalculationController() {
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
		verify(aspectRatio).setSelectedItem(AspectRatio.FILL);
	}

	/**
	 * What happens, when the calculate button is pressed?
	 */
	@SuppressWarnings("unchecked")
	@Test
	final void testBtnClaculationPressed() {
		// Simulate input values
		when(tlCx.getText()).thenReturn("-0.15");
		when(tlCy.getText()).thenReturn("-0.1");
		when(brCx.getText()).thenReturn("-0.05");
		when(brCy.getText()).thenReturn("0");

		// Initialize
		cut.initController();
		cut.addModelChangedListener(listener);

		// Simulate calculate button
		cut.calculate();

		// Check
		// Are values set correctly from view to controller model?
		verify(tlCx).getText();
		verify(tlCy).getText();
		verify(brCx).getText();
		verify(brCy).getText();
		// Have listeners been informed?
		verify(listener).modelChanged(any(ModelChangedEvent.class));
		// Check, whether the values in controller model are set correctly
		assertEquals(-0.15, cut.topLeft().cx());
		assertEquals(-0.1, cut.topLeft().cy());
		assertEquals(-0.05, cut.bottomRight().cx());
		assertEquals(0, cut.bottomRight().cy());
	}

	@Test
	final void testReplaceModel() {
		// Set new values in calculation model
		CalculationAttributesDTO calc = model.getCalculation();
		calc.setTopLeft(MandelbrotPointPosition.of(0.6, 0.4));
		calc.setBottomRight(MandelbrotPointPosition.of(-0.5, 0.2));
		calc.setMaximumIterations(53);

		// fire event
		cut.replaceModel(calc);

		// Then check, whether the values are set correctly.
		verify(tlCx).setText("0.6");
		verify(tlCy).setText("0.4");
		verify(brCx).setText("-0.5");
		verify(brCy).setText("0.2");
		verify(maxIter).setText("53");
	}

}
