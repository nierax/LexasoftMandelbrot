/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.lexasoft.common.swing.LSJFormattedTextField;
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
	private LSJFormattedTextField<Double> tlCx;
	@Mock
	private LSJFormattedTextField<Double> tlCy;
	@Mock
	private LSJFormattedTextField<Double> brCx;
	@Mock
	private LSJFormattedTextField<Double> brCy;
	@Mock
	private LSJFormattedTextField<Integer> maxIter;
	@Mock
	private JComboBox<AspectRatio> aspectRatio;
	@Mock
	private JButton btnCalculate;
	@Mock
	private ModelChangedListener<CalculationControllerModel> listener;
	@Mock
	private JCheckBox chckbxShowCalculationArea;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		model = MandelbrotAttributesDTO.ofDefaults();
		when(view.getTlcx()).thenReturn(tlCx);
		when(view.getTlcy()).thenReturn(tlCy);
		when(view.getBrcx()).thenReturn(brCx);
		when(view.getBrcy()).thenReturn(brCy);
		when(view.getMaxIter()).thenReturn(maxIter);
		when(view.getAspectRatio()).thenReturn(aspectRatio);
		when(view.getChckbxShowCalculationArea()).thenReturn(chckbxShowCalculationArea);
		cut = new CalculationController(model.getCalculation(), view);
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.swing.CalculationController#CalculationController(de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties, de.lexasoft.mandelbrot.swing.CalculationPanel)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	final void testCalculationController() throws ParseException {
		// Check the result
		assertNotNull(cut); // Should never fail
		// Controller model
		CalculationAttributesDTO calc = model.getCalculation();
		assertEquals(calc.getTopLeft(), cut.topLeft());
		assertEquals(calc.getBottomRight(), cut.bottomRight());
		assertEquals(calc.getMaximumIterations(), cut.maximumIterations());
		// View initialized?
		verify(tlCx).setBasicValue(calc.getTopLeft().cx());
		verify(tlCy).setBasicValue(calc.getTopLeft().cy());
		verify(brCx).setBasicValue(calc.getBottomRight().cx());
		verify(brCy).setBasicValue(calc.getBottomRight().cy());
		verify(maxIter).setBasicValue(calc.getMaximumIterations());
		verify(aspectRatio).setSelectedItem(AspectRatio.FILL);
	}

	/**
	 * What happens, when the calculate button is pressed?
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@Test
	final void testBtnClaculationPressed() throws ParseException {
		// Simulate input values
		when(tlCx.getBasicValue()).thenReturn(-0.15);
		when(tlCy.getBasicValue()).thenReturn(-0.1);
		when(brCx.getBasicValue()).thenReturn(-0.05);
		when(brCy.getBasicValue()).thenReturn(0d);

		// Initialize
		cut.initController();
		cut.addModelChangedListener(listener);

		// Simulate calculate button
		cut.calculate();

		// Check
		// Are values set correctly from view to controller model?
		verify(tlCx).getBasicValue();
		verify(tlCy).getBasicValue();
		verify(brCx).getBasicValue();
		verify(brCy).getBasicValue();
		// Have listeners been informed?
		verify(listener).modelChanged(any(ModelChangedEvent.class));
		// Check, whether the values in controller model are set correctly
		assertEquals(-0.15, cut.topLeft().cx());
		assertEquals(-0.1, cut.topLeft().cy());
		assertEquals(-0.05, cut.bottomRight().cx());
		assertEquals(0, cut.bottomRight().cy());
	}

	@Test
	final void testReplaceModel() throws ParseException {
		// Set new values in calculation model
		CalculationAttributesDTO calc = model.getCalculation();
		calc.setTopLeft(MandelbrotPointPosition.of(0.6, 0.4));
		calc.setBottomRight(MandelbrotPointPosition.of(-0.5, 0.2));
		calc.setMaximumIterations(53);

		// fire event
		cut.replaceModel(calc);

		// Then check, whether the values are set correctly.
		verify(tlCx).setBasicValue(0.6);
		verify(tlCy).setBasicValue(0.4);
		verify(brCx).setBasicValue(-0.5);
		verify(brCy).setBasicValue(0.2);
		verify(maxIter).setBasicValue(53);
	}

}
