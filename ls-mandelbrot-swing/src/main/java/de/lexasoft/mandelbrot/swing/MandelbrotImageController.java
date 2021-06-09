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

/**
 * This controller does the calculation of the Mandelbrot set and sets it as
 * image into the corresponding JPanel.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageController implements ModelChangedListener<MandelbrotCalculationProperties> {

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
			model.setImageWidth(view.getWidth());
			model.setImageHeight(view.getHeight());
			model.setImage(MandelbrotImage.of(view.getWidth(), view.getHeight()));
			model.setBottomRight(MandelbrotPointPosition.of(model.getBottomRight().cx(), Double.NaN));
			model.normalize();
			MandelbrotRunner.of(model).run();
			return model.getImage().getImage();
		} catch (MandelbrotRunnerException e) {
			throw new IllegalArgumentException("Something went wrong", e);
		}

	}

	@Override
	public void modelChanged(ModelChangedEvent<MandelbrotCalculationProperties> event) {
		view.drawImage(calculate());
	}

}
