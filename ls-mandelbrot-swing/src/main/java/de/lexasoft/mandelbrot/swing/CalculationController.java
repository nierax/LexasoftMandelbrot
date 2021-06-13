/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;

/**
 * @author nierax
 *
 */
public class CalculationController extends ModelChangingController<CalculationControllerModel>
    implements CalculationControllerModel {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private int maximumIterations;
	private CalculationPanel view;

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
	}

	/**
	 * Register the listeners to react on changes
	 */
	public void initController() {

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

}
