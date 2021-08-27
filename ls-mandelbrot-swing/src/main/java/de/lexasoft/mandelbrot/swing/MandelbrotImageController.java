/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;

/**
 * This controller does the calculation of the Mandelbrot set and sets it as
 * image into the corresponding JPanel.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageController extends ModelChangingController<CalculationAreaControllerModel> {

	private ImagePanel view;
	private MandelbrotAttributesDTO model;
	private CalculationControllerModel calcModel;

	public MandelbrotImageController(MandelbrotAttributesDTO model, ImagePanel view) {
		this.view = view;
		initModel(model);
		initView();
	}

	void initModel(MandelbrotAttributesDTO model) {
		this.model = model;
	}

	void initView() {
		this.view.setPreferredSize(new Dimension(model.getImage().getImageWidth(), model.getImage().getImageHeight()));
	}

	public void initController(CalculationControllerModel calcModel) {
		setCalcModel(calcModel);
		this.view.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				calculateAndDraw();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				calculateAndDraw();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// Do nothing
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// Do nothing
			}
		});
	}

	/**
	 * Calculate with current model.
	 * 
	 * @return
	 */
	MandelbrotImage calculate() {
		handleAspectRatio(getCalcModel().aspectRatio());
		MandelbrotImage image = MandelbrotController.of().executeSingleCalculation(model);
		return image;
	}

	/**
	 * 
	 */
	private void calculateAndDraw() {
		MandelbrotImage image = calculate();
		view.drawImage(image.getImage());
		CalculationAreaControllerModel calcAreaModel = new CalculationAreaControllerModel() {

			@Override
			public int imageWidth() {
				return image.getImage().getWidth();
			}

			@Override
			public int imageHeight() {
				return image.getImage().getHeight();
			}

			@Override
			public MandelbrotPointPosition calcTopLeft() {
				return calcModel.topLeft();
			}

			@Override
			public MandelbrotPointPosition calcBottomRight() {
				return calcModel.bottomRight();
			}

			@Override
			public MandelbrotPointPosition adoptTopLeft() {
				return image.topLeft();
			}

			@Override
			public MandelbrotPointPosition adoptBottomRight() {
				return image.bottomRight();
			}
		};
		fireModelChangedEvent(new ModelChangedEvent<CalculationAreaControllerModel>(this, calcAreaModel));
	}

	private void assignColorCM(ColorControllerModel colorCM) {
		model.getColor().setPaletteVariant(colorCM.paletteVariant());
		model.getColor().getColorGrading().setStyle(colorCM.gradingStyle());
		model.getColor().getColorGrading().setColorsTotal(colorCM.totalNrOfColors());
	}

	/**
	 * Has to be called, when the underlying color controller model has changed.
	 * <p>
	 * To ensure a lightweight message communication, the link is set within the
	 * {@link MandelbrotUIController} with a lambda expression.
	 * 
	 * @param event The event connected with the change.
	 */
	public void colorModelChanged(ModelChangedEvent<ColorControllerModel> event) {
		assignColorCM((ColorControllerModel) event.getModel());
		calculateAndDraw();
	}

	private void assignDimensions(CalculationControllerModel calcCM) {
		model.getCalculation().setTopLeft(calcCM.topLeft());
		model.getCalculation().setBottomRight(calcCM.bottomRight());
	}

	private void handleAspectRatio(AspectRatio ar) {
		int width = view.getWidth();
		int height = view.getHeight();
		switch (ar) {
		case IGNORE:
			model.getImage().setImageWidth(width);
			model.getImage().setImageHeight(height);
			model.getImage().setAspectRatioHandle(AspectRatioHandle.IGNORE);
			return;
		case FILL:
			// Use height and width as calculated above
			break;
		default:
			height = (int) Math.round(width / ar.getRatioX2Y());
			break;
		}
		model.getImage().setImageWidth(width);
		model.getImage().setImageHeight(height);
		model.getImage().setAspectRatioHandle(AspectRatioHandle.FITIN);
	}

	private void assignCalculationCM(CalculationControllerModel calcCM) {
		assignDimensions(calcCM);
		model.getCalculation().setMaximumIterations(calcCM.maximumIterations());
	}

	/**
	 * Has to be called, when the underlying calculation controller model has
	 * changed.
	 * <p>
	 * To ensure a lightweight message communication, the link is set within the
	 * {@link MandelbrotUIController} with a lambda expression.
	 * 
	 * @param event The event connected with the change.
	 */
	public void calculationModelChanged(ModelChangedEvent<CalculationControllerModel> event) {
		assignCalculationCM(event.getModel());
		calculateAndDraw();
	}

	/**
	 * @return the calcModel
	 */
	CalculationControllerModel getCalcModel() {
		return calcModel;
	}

	/**
	 * @param calcModel the calcModel to set
	 */
	void setCalcModel(CalculationControllerModel calcModel) {
		this.calcModel = calcModel;
	}

	public void replaceModel(MandelbrotAttributesDTO model, CalculationControllerModel calcCtrlModel) {
		initModel(model);
		setCalcModel(calcCtrlModel);
	}

	/**
	 * @return the model
	 */
	MandelbrotAttributesDTO getModel() {
		return model;
	}

}
