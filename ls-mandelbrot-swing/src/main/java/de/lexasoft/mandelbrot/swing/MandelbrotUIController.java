/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.Dimension;

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
	private CalculationAreaController calcAreaController;
	private ExportImageController exportController;

	/**
	 * 
	 */
	public MandelbrotUIController(MandelbrotAttributesDTO model, MandelbrotSwingView view) {
		initModel(model);
		this.view = view;
		this.colorController = new ColorController(this.model.getColor(), this.view.getColorControlPanel());
		this.calculationController = new CalculationController(model.getCalculation(), this.view.getCalculationPanel());
		Dimension initialImageSize = new Dimension(this.model.getImage().getImageWidth(),
		    this.model.getImage().getImageHeight());
		this.imageController = new MandelbrotImageController(this.calculationController, this.colorController,
		    initialImageSize, this.view.getImagePanel());
		this.exportController = new ExportImageController(this.view.getFrmLexasoftMandelbrotApplication());
		this.fileMenuController = new FileMenuController(this.view.getMnFile(),
		    this.view.getFrmLexasoftMandelbrotApplication(), this.exportController, this.calculationController,
		    this.colorController, this.imageController);
		this.calcAreaController = new CalculationAreaController(this.view.getCalculationAreaPanel());
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
		calculationController.setShowCalculationArea(calcAreaController);
		imageController.initController(calculationController);
		imageController.addModelChangedListener(e -> calcAreaController.calculationAreaModelChanged(e.getModel()));
		fileMenuController.initController();
		fileMenuController.addModelChangedListener(e -> handleLoadEvent(e));
		exportController.initController();
	}

	/**
	 * 
	 */
	private void handleLoadEvent(ModelChangedEvent<MandelbrotAttributesDTO> event) {
		// Initialize model newly
		initModel(event.getModel());
		// Set new values in controllers (the right values in UI).
		colorController.replaceModel(event.getModel().getColor());
		calculationController.replaceModel(event.getModel().getCalculation());
		imageController.setCalcModel(calculationController);
		imageController.reCalculate();
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
