/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;

/**
 * This class represents the color gradients for an image of the Mandelbrot set.
 * <p>
 * Every point can be calculated to a special color on a gradient between two
 * colors, which must be given with constructor.
 * 
 * @author nierax
 */
public class MandelbrotColorize2ColorGradient implements MandelbrotColorize {

	private int nrOfColors;
	private Color gradientStart;
	private Color gradientEnd;

	private float stepRed;
	private float stepGreen;
	private float stepBlue;

	public MandelbrotColorize2ColorGradient(Color gradientStart, Color gradientEnd, int nrOfColors) {
		this.gradientStart = gradientStart;
		this.gradientEnd = gradientEnd;
		this.nrOfColors = nrOfColors;
		this.stepRed = calculateGradient(this.gradientStart.getRed(), this.gradientEnd.getRed(), this.nrOfColors);
		this.stepGreen = calculateGradient(this.gradientStart.getGreen(), this.gradientEnd.getGreen(), this.nrOfColors);
		this.stepBlue = calculateGradient(this.gradientStart.getBlue(), this.gradientEnd.getBlue(), this.nrOfColors);
	}
	
	/**
	 * Calculates the difference per step for one RGB color.
	 * 
	 * @param rgbCStart
	 * @param rgbCEnd
	 * @param nrOfColors
	 * @return Factor per step
	 */
	private float calculateGradient(int rgbCStart, int rgbCEnd, int nrOfColors) {
		return (float) (rgbCEnd - rgbCStart) / nrOfColors;
	}

	/**
	 * Find the color for this number of iterations.
	 * <p>
	 * If the point is in the Mandelbrot set, black is returned, otherwise a color
	 * between the two gradients.
	 * 
	 * @param iteration Number of iterations on a special point.
	 * @param maxIter   Maximum number of iterations on a special point.
	 * @return The color of the point with the given number of iterations.
	 */
	@Override
	public Color getColorForIteration(int iteration, int maxIter) {
		if (iteration == maxIter) {
			return Color.BLACK;
		}
		int red = (int) (gradientStart.getRed() + iteration * stepRed);
		int green = (int) (gradientStart.getGreen() + iteration * stepGreen);
		int blue = (int) (gradientStart.getBlue() + iteration * stepBlue);
		return new Color(red, green, blue);
	}

	float stepGreen() {
		return stepGreen;
	}

	float stepRed() {
		return stepRed;
	}

	float stepBlue() {
		return stepBlue;
	}

}
