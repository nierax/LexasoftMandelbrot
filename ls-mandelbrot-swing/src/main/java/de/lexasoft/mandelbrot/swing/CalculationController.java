/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;

/**
 * Controls the calculation properties, such as the complex numbers, defining
 * the are to investigate and the maximum number of iterations to calculate.
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

	// The view, being connected to.
	private CalculationPanel view;

	/**
	 * Create the controller with the given objects.
	 * <p>
	 * The model is needed for initial assignment of the controller model. It is not
	 * kept as reference.
	 * 
	 * @param initalModel
	 * @param view
	 */
	public CalculationController(MandelbrotCalculationProperties initalModel, CalculationPanel view) {
		initModel(initalModel);
		this.view = view;
		initView();
	}

	/**
	 * Write the initial values in the controller model
	 * 
	 * @param initialModel
	 */
	private void initModel(MandelbrotCalculationProperties initialModel) {
		topLeft = MandelbrotPointPosition.of(initialModel.getTopLeft().cx(), initialModel.getTopLeft().cy());
		bottomRight = MandelbrotPointPosition.of(initialModel.getBottomRight().cx(), initialModel.getBottomRight().cy());
		maximumIterations = initialModel.getMaximumIterations();
		aspectRatio = AspectRatio.FILL;
	}

	/**
	 * Write the initial values from the
	 */
	private void initView() {
		this.view.getTlcx().setText(Double.toString(topLeft().cx()));
		this.view.getTlcy().setText(Double.toString(topLeft().cy()));
		this.view.getBrcx().setText(Double.toString(bottomRight().cx()));
		this.view.getBrcy().setText(Double.toString(bottomRight().cy()));
		this.view.getMaxIter().setText(Integer.toString(maximumIterations()));
		this.view.getAspectRatio().setSelectedItem(aspectRatio());
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
	}

	private void doHandleMaximumIterations(int maximumIterations) {
		maximumIterations(maximumIterations);
	}

	void handleMaximumIterations() {
		doHandleMaximumIterations(Integer.parseInt(view.getMaxIter().getText()));
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
}