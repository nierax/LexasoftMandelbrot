/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * Controller for Mandelbrot Swing application according the MVC pattern.
 * <p>
 * Holds both the view and the model and combines it by defining suitable
 * listeners.
 * 
 * @author nierax
 *
 */
public class MandelbrotController {

	private MandelbrotCalculationProperties model;
	private MandelbrotSwingView view;
	private ColorController colorController;

	/**
	 * 
	 */
	public MandelbrotController(MandelbrotCalculationProperties model, MandelbrotSwingView view) {
		this.model = model;
		this.view = view;
		this.colorController = new ColorController(this.model, this.view.getColorControlPanel(),
		    this.view.getMandelbrotCanvas());
		initView();
	}

	/**
	 * Connects the input with the model.
	 */
	public void initView() {
	}

	/**
	 * Registers the listeners to promote changes of the attributes.
	 */
	public void initController() {
		colorController.initController();
	}

}
