package de.lexasoft.mandelbrot.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private BufferedImage image;

	/**
	 * Create the panel.
	 */
	public ImagePanel() {

	}

	/**
	 * Paint the image to the canvas
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
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
