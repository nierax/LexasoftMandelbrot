/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.MandelbrotRunner;
import de.lexasoft.mandelbrot.api.MandelbrotRunnerException;
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
	private MandelbrotCalculationProperties model;

	public MandelbrotImageController(MandelbrotCalculationProperties model, ImagePanel view) {
		this.view = view;
		this.model = model;
		initView();
	}

	public void initView() {
		this.view.setPreferredSize(new Dimension(model.getImageWidth(), model.getImageHeight()));
	}

	public void initController() {
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

	BufferedImage calculate() {
		try {
			int width = view.getWidth();
			int height = view.getHeight();
			model.setImageWidth(width);
			model.setImageHeight(height);
			model.setImage(MandelbrotImage.of(width, height));
			model.setBottomRight(MandelbrotPointPosition.of(model.getBottomRight().cx(), Double.NaN));
			model.normalize();
			MandelbrotRunner.of(model).run();
			return model.getImage().getImage();
		} catch (MandelbrotRunnerException e) {
			throw new IllegalArgumentException("Something went wrong", e);
		}

	}

	private void assignColorCM(ColorControllerModel colorCM) {
		model.setPaletteVariant(colorCM.paletteVariant());
		model.getColorGrading().setStyle(colorCM.gradingStyle());
		model.getColorGrading().setColorsTotal(colorCM.totalNrOfColors());
	}

	/**
	 * Has to be called, when the underlying color controller model has changed.
	 * <p>
	 * To ensure a lightweight message communication, the link is set within the
	 * {@link MandelbrotController} with a lambda expression.
	 * 
	 * @param event The event connected with the change.
	 */
	public void colorModelChanged(ModelChangedEvent<ColorControllerModel> event) {
		assignColorCM((ColorControllerModel) event.getModel());
		view.drawImage(calculate());
	}

	private void assignCalculationCM(CalculationControllerModel calcCM) {
		model.setMaximumIterations(calcCM.maximumIterations());
	}

	/**
	 * Has to be called, when the underlying calculation controller model has
	 * changed.
	 * <p>
	 * To ensure a lightweight message communication, the link is set within the
	 * {@link MandelbrotController} with a lambda expression.
	 * 
	 * @param event The event connected with the change.
	 */
	public void calculationModelChanged(ModelChangedEvent<CalculationControllerModel> event) {
		assignCalculationCM(event.getModel());
		view.drawImage(calculate());
	}

}
