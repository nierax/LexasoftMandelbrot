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
		initModel(model);
		this.view = view;
		this.colorController = new ColorController(this.model.getColor(), this.view.getColorControlPanel());
		this.calculationController = new CalculationController(model.getCalculation(), this.view.getCalculationPanel());
		this.imageController = new MandelbrotImageController(this.model, this.view.getImagePanel());
		this.fileMenuController = new FileMenuController(this.view.getMnFile(),
		    this.view.getFrmLexasoftMandelbrotApplication(), this.model);
		initView();
	}

	/**
	 * @param model
	 */
	private void initModel(MandelbrotAttributesDTO model) {
		this.model = model;
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
		fileMenuController.addModelChangedListener(e -> loadEventhandling(e));
	}

	/**
	 * 
	 */
	private void loadEventhandling(ModelChangedEvent<MandelbrotAttributesDTO> event) {
//		view.getFrmLexasoftMandelbrotApplication().setVisible(false);
		initModel(event.getModel());
		calculationController.replaceModel(event.getModel().getCalculation());
		colorController.replaceModel(event.getModel().getColor());
		imageController.replaceModel(event.getModel(), calculationController);
//		view.getFrmLexasoftMandelbrotApplication().setVisible(true);
		view.repaint();
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

	/**
	 * @return the fileMenuController
	 */
	FileMenuController getFileMenuController() {
		return fileMenuController;
	}

}
