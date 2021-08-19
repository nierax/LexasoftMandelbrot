/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * Controller for Mandelbrot Swing application according the MVC pattern.
 * <p>
 * Holds both the view and the model and combines it by defining suitable
 * listeners.
 * 
 * @author nierax
 *
 */
public class MandelbrotUIController {

	private MandelbrotAttributesDTO model;
	private MandelbrotSwingView view;
	private ColorController colorController;
	private MandelbrotImageController imageController;
	private CalculationController calculationController;
	private FileMenuController fileMenuController;

	/**
	 * 
	 */
	public MandelbrotUIController(MandelbrotAttributesDTO model, MandelbrotSwingView view) {
		this.model = model;
		this.view = view;
		this.colorController = new ColorController(this.model.getColor(), this.view.getColorControlPanel());
		this.calculationController = new CalculationController(model.getCalculation(), this.view.getCalculationPanel());
		this.imageController = new MandelbrotImageController(this.model, this.view.getImagePanel());
		this.fileMenuController = new FileMenuController(this.view.getMnFile(),
		    this.view.getFrmLexasoftMandelbrotApplication(), this.model);
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
		colorController.addModelChangedListener(e -> imageController.colorModelChanged(e));
		colorController.initController();
		calculationController.addModelChangedListener(e -> imageController.calculationModelChanged(e));
		calculationController.initController();
		imageController.initController(calculationController);
		fileMenuController.initController();
		fileMenuController.addModelChangedListener(e -> colorController.replaceModel(model.getColor()));
		fileMenuController.addModelChangedListener(e -> calculationController.replaceModel(model.getCalculation()));
		fileMenuController.addModelChangedListener(e -> imageController.replaceModel(model, calculationController));
		fileMenuController.addModelChangedListener(e -> view.repaint());
	}

	/**
	 * @return the colorController
	 */
	ColorController getColorController() {
		return colorController;
	}

	/**
	 * @return the imageController
	 */
	MandelbrotImageController getImageController() {
		return imageController;
	}

	CalculationController getCalculationController() {
		return calculationController;
	}

}
