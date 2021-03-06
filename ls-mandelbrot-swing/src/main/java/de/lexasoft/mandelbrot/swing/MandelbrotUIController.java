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
	private DrawCalculationAreaController calcAreaController;
	private ExportImageController exportController;
	private ZoomController zoomController;
	private DragController dragController;
	private StatusbarController statusbarController;

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
		this.calcAreaController = new DrawCalculationAreaController(this.view.getDrawCalculationAreaPanel());
		this.zoomController = new ZoomController(view.getDrawCalculationAreaPanel());
		this.dragController = new DragController(view.getDrawCalculationAreaPanel());
		this.statusbarController = new StatusbarController(view.getStatusBar());
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
		zoomController.initController();
		zoomController.addModelChangedListener(e -> calculationController.calculationAreaChanged(e));
		dragController.initController();
		dragController.addModelChangedListener(e -> calculationController.calculationAreaChanged(e));
		imageController.initController(calculationController);
		imageController.addModelChangedListener(e -> calcAreaController.calculationAreaModelChanged(e.getModel()));
		imageController.addModelChangedListener(e -> zoomController.modelChanged(e.getModel()));
		imageController.addModelChangedListener(e -> dragController.modelChanged(e.getModel()));
		fileMenuController.initController();
		fileMenuController.addModelChangedListener(e -> handleLoadEvent(e));
		exportController.initController();
		statusbarController.initController();
		imageController.setDurationUpdater(statusbarController);

		// Now we can start calculating the image
		imageController.startRunning();
	}

	/**
	 * 
	 */
	private void handleLoadEvent(ModelChangedEvent<MandelbrotAttributesDTO> event) {
		// Stop calculating the image
		imageController.stopRunning();

		// Initialize model newly
		initModel(event.getModel());
		// Reset Models in controller without keeping data
		zoomController.resetModel();
		dragController.resetModel();
		// Set new values in controllers (the right values in UI).
		colorController.replaceModel(event.getModel().getColor());
		calculationController.replaceModel(event.getModel().getCalculation());
		imageController.setCalcModel(calculationController);

		// Now start calculating again
		imageController.startRunning();
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
