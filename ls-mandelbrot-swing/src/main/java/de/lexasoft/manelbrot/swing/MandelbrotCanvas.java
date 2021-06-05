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

	private MandelbrotCalculationProperties properties;

	/**
	 * 
	 */
	public MandelbrotCanvas(int imageWidth, int imageHeight) {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(imageWidth, imageHeight));
		this.properties = MandelbrotCalculationProperties.ofDefault();
		this.properties.setImageWidth(imageWidth);
		this.properties.setImageHeight(imageHeight);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			properties.setImageWidth(getWidth());
			properties.setImageHeight(getHeight());
			properties.setImage(MandelbrotImage.of(getWidth(), getHeight()));
			properties.setBottomRight(MandelbrotPointPosition.of(properties.getBottomRight().cx(), Double.NaN));
			properties.normalize();
			MandelbrotRunner.of(properties).run();
			g.drawImage(properties.getImage().getImage(), 0, 0, this);
		} catch (MandelbrotRunnerException e) {
			throw new IllegalArgumentException("Something went wrong", e);
		}
	}

}
