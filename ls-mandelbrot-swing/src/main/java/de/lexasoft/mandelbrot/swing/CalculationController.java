/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.text.ParseException;

import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.ctrl.CalculationAttributesDTO;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;

/**
 * Controls the calculation properties, such as the complex numbers, defining
 * the area to investigate and the maximum number of iterations to calculate.
 * 
 * @author nierax
 *
 */
public class CalculationController extends ModelChangingController<CalculationControllerModel>
    implements CalculationControllerModel {

	// Attributes of the controller model.
	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private AspectRatio aspectRatio;
	private int maximumIterations;

	private ShowCalculationArea showCalculationArea;

	// The view, being connected to.
	private CalculationPanel view;

	class CalculationFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// Nothing to do here.
		}

		@Override
		public void focusLost(FocusEvent e) {
			calculate();
		}

	}

	/**
	 * Create the controller with the given objects.
	 * <p>
	 * The model is needed for initial assignment of the controller model. It is not
	 * kept as reference.
	 * 
	 * @param initialModel
	 * @param view
	 */
	public CalculationController(CalculationAttributesDTO initialModel, CalculationPanel view) {
		initModel(initialModel);
		this.view = view;
		initView();
	}

	/**
	 * Write the initial values in the controller model
	 * 
	 * @param calculationModel
	 */
	private void initModel(CalculationAttributesDTO calculationModel) {
		topLeft = MandelbrotPointPosition.of(calculationModel.getTopLeft());
		bottomRight = MandelbrotPointPosition.of(calculationModel.getBottomRight());
		maximumIterations = calculationModel.getMaximumIterations();
		aspectRatio = AspectRatio.FILL;
	}

	/**
	 * Write the initial values from the
	 */
	private void initView() {
		try {
			setCalculationAreaValues(topLeft, bottomRight);
			this.view.getMaxIter().setBasicValue(maximumIterations());
			this.view.getAspectRatio().setSelectedItem(aspectRatio());
			this.view.getChckbxShowCalculationArea().setSelected(false);
		} catch (ParseException e) {
			throw new MandelbrotException("Error initializing calculation area values.", e);
		}
	}

	/**
	 * Register the listeners to react on changes
	 */
	public void initController() {
		this.view.getMaxIter().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				handleMaximumIterations();
			}

			@Override
			public void focusGained(FocusEvent e) {
				// Nothing to be done here.
			}
		});
		this.view.getAspectRatio().addItemListener(e -> handleAspectRatio(e));
		this.view.getTlcx().addFocusListener(new CalculationFocusListener());
		this.view.getTlcy().addFocusListener(new CalculationFocusListener());
		this.view.getBrcx().addFocusListener(new CalculationFocusListener());
		this.view.getBrcy().addFocusListener(new CalculationFocusListener());
		this.view.getChckbxShowCalculationArea()
		    .addChangeListener(l -> showCalculationArea.show(this.view.getChckbxShowCalculationArea().isSelected()));
	}

	private void doHandleMaximumIterations(int maximumIterations) {
		maximumIterations(maximumIterations);
	}

	void handleMaximumIterations() {
		try {
			doHandleMaximumIterations(view.getMaxIter().getBasicValue());
		} catch (ParseException e) {
			throw new MandelbrotException("Error parsing input value for maximum iterations.", e);
		}
		fireModelChanged();
	}

	void handleAspectRatio(ItemEvent e) {
		aspectRatio((AspectRatio) view.getAspectRatio().getSelectedItem());
		fireModelChanged();
	}

	/**
	 * Fire a model changed event to the listeners.
	 */
	void fireModelChanged() {
		fireModelChangedEvent(new ModelChangedEvent<CalculationControllerModel>(this, this));
	}

	public void calculate() {
		try {
			topLeft.setCx(view.getTlcx().getBasicValue());
			topLeft.setCy(view.getTlcy().getBasicValue());
			bottomRight.setCx(view.getBrcx().getBasicValue());
			bottomRight.setCy(view.getBrcy().getBasicValue());
		} catch (ParseException e) {
			throw new MandelbrotException("Error parsing calculation area values", e);
		}
		fireModelChanged();
	}

	@Override
	public MandelbrotPointPosition topLeft() {
		return topLeft;
	}

	@Override
	public MandelbrotPointPosition bottomRight() {
		return bottomRight;
	}

	@Override
	public int maximumIterations() {
		return maximumIterations;
	}

	int maximumIterations(int maximumIterations) {
		return this.maximumIterations = maximumIterations;
	}

	@Override
	public AspectRatio aspectRatio() {
		return aspectRatio;
	}

	public AspectRatio aspectRatio(AspectRatio aspectRatio) {
		return this.aspectRatio = aspectRatio;
	}

	public void replaceModel(CalculationAttributesDTO newModel) {
		initModel(newModel);
		initView();
	}

	/**
	 * @return the showCalculationArea
	 */
	public ShowCalculationArea getShowCalculationArea() {
		return showCalculationArea;
	}

	/**
	 * @param showCalculationArea the showCalculationArea to set
	 */
	public void setShowCalculationArea(ShowCalculationArea showCalculationArea) {
		this.showCalculationArea = showCalculationArea;
	}

	/**
	 * Informs the @CalculationController about changes of the calculation area,
	 * such as via a mouse wheel event.
	 * 
	 * @param calculation
	 */
	public void calculationAreaChanged(ModelChangedEvent<CalculationArea> event) {
		CalculationArea calculation = event.getModel();
		setCalculationAreaValues(calculation.topLeft(), calculation.bottomRight());
		calculate();
	}

	/**
	 * @param calculation
	 */
	private void setCalculationAreaValues(MandelbrotPointPosition tl, MandelbrotPointPosition br) {
		try {
			this.view.getTlcx().setBasicValue(tl.cx());
			this.view.getTlcy().setBasicValue(tl.cy());
			this.view.getBrcx().setBasicValue(br.cx());
			this.view.getBrcy().setBasicValue(br.cy());
		} catch (ParseException e) {
			throw new MandelbrotException("Error setting calculation area values");
		}
	}
}
