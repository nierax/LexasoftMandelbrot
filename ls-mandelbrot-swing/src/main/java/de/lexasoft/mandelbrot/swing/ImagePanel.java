package de.lexasoft.mandelbrot.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private BufferedImage image;

	/**
	 * Create the panel with the given backgroundColor.
	 * 
	 * @param backgroundColor The background color to use
	 */
	public ImagePanel(Color backgroundColor) {
		setBackground(backgroundColor);
	}

	/**
	 * Create the panel with the background color white.
	 */
	public ImagePanel() {
		this(Color.LIGHT_GRAY);
	}

	/**
	 * Paint the image to the canvas
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
			g.drawImage(image, 0, 0, this);
		} else {
			g.drawString("No image", getWidth() / 2, getHeight() / 2);
		}
	}

	public void drawImage(BufferedImage image) {
		this.image = image;
		validate();
		repaint();
	}

}
