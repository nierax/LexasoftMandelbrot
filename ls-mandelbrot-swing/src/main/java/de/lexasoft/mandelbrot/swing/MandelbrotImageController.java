/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.ctrl.CalculationAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import de.lexasoft.mandelbrot.swing.model.AspectRatio;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;

/**
 * This controller does the calculation of the Mandelbrot set and sets it as
 * image into the corresponding JPanel.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageController {

	private ImagePanel view;
	private MandelbrotAttributesDTO model;
	private CalculationControllerModel calcModel;

	public MandelbrotImageController(MandelbrotAttributesDTO model, ImagePanel view) {
		this.view = view;
		this.model = model;
		initView();
	}

	public void initView() {
		this.view.setPreferredSize(new Dimension(model.getImage().getImageWidth(), model.getImage().getImageHeight()));
	}

	public void initController(CalculationControllerModel calcModel) {
		setCalcModel(calcModel);
		this.view.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				view.drawImage(calculate());
			}

			@Override
			public void componentResized(ComponentEvent e) {
				view.drawImage(calculate());
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
	BufferedImage calculate() {
		handleAspectRatio(getCalcModel().aspectRatio());
		MandelbrotImage image = MandelbrotController.of().executeSingleCalculation(model);
		return image.getImage();
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
		view.drawImage(calculate());
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
		CalculationAttributesDTO calc = model.getCalculation();
		calc.setBottomRight(MandelbrotPointPosition.of(calc.getBottomRight().cx(), Double.NaN));
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
		view.drawImage(calculate());
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

}
