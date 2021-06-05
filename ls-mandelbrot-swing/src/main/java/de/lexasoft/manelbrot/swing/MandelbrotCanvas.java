/**
 * 
 */
package de.lexasoft.manelbrot.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.MandelbrotRunner;
import de.lexasoft.mandelbrot.api.MandelbrotRunnerException;

/**
 * This is the canvas, the Mandelbrot image is painted in.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class MandelbrotCanvas extends JPanel {

	private MandelbrotCalculationProperties model;

	/**
	 * 
	 */
	public MandelbrotCanvas(MandelbrotCalculationProperties model) {
		setBackground(Color.WHITE);
		this.model = model;
		setPreferredSize(new Dimension(model.getImageWidth(), model.getImageHeight()));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			model.setImageWidth(getWidth());
			model.setImageHeight(getHeight());
			model.setImage(MandelbrotImage.of(getWidth(), getHeight()));
			model.setBottomRight(MandelbrotPointPosition.of(model.getBottomRight().cx(), Double.NaN));
			model.normalize();
			MandelbrotRunner.of(model).run();
			g.drawImage(model.getImage().getImage(), 0, 0, this);
		} catch (MandelbrotRunnerException e) {
			throw new IllegalArgumentException("Something went wrong", e);
		}
	}

	/**
	 * Inform the component, that the underlying model has changed.
	 * <p>
	 * The component will be redrawn by this call.
	 */
	public void modelChanged() {
		validate();
		repaint();
	}

}
