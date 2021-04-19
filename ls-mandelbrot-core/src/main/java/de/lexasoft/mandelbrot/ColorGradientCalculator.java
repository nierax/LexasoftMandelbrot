/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the gradient between two colors in a given number of steps.
 * <p>
 * Returns the list of colors on the way from one color to the next.
 * 
 * @author nierax
 *
 */
public class ColorGradientCalculator {

	class GradientFactors {
		float red;
		float green;
		float blue;
	}

	/**
	 * Creates a list of colors in steps between two colors: Color start and color
	 * end.
	 * 
	 * @param colorStart The first color used (at step 0)
	 * @param colorEnd   The last color used (at step nrOfSteps-1)
	 * @param nrOfSteps  The number of steps to gradient between.
	 * @return A list with all colors at their positions.
	 */
	public List<Color> createGradientList(Color colorStart, Color colorEnd, int nrOfSteps) {
		List<Color> colors = new ArrayList<>(nrOfSteps);
		GradientFactors gf = calculateFactors(colorStart, colorEnd, nrOfSteps);
		// Start color is set directly
		colors.add(colorStart);
		// The colors between start and end will be interpolated
		for (int i = 1; i < nrOfSteps - 1; i++) {
			colors.add(createColor(colorStart, gf, i));
		}
		// To avoid mistakes from rounding -> set the last color as defined
		colors.add(colorEnd);
		return colors;
	}

	/**
	 * Creates a list of colors in steps between three colors: Color start, Color
	 * between and color end.
	 * 
	 * @param colorStart   The first color used (at step 0)
	 * @param colorBetween The color used in the middle of the interval.
	 * @param colorEnd     The last color used (at step nrOfSteps-1)
	 * @param nrOfSteps    The number of steps to gradient between.
	 * @return A list with all colors at their positions.
	 */
	public List<Color> createGradientList(Color colorStart, Color colorBetween, Color colorEnd, int nrOfSteps) {
		List<Color> colors = new ArrayList<>(nrOfSteps);
		int stepBetween = nrOfSteps / 2;
		int stepEnd = nrOfSteps - stepBetween;
		GradientFactors gf1 = calculateFactors(colorStart, colorBetween, stepBetween);
		GradientFactors gf2 = calculateFactors(colorBetween, colorEnd, nrOfSteps - stepBetween);
		// First color is set directly
		colors.add(colorStart);
		// Then add colors between start and between
		for (int i = 1; i < stepBetween - 1; i++) {
			colors.add(createColor(colorStart, gf1, i));
		}
		// Then add between
		colors.add(colorBetween);
		// Then add colors between middle and end.
		for (int i = 1; i < stepEnd; i++) {
			colors.add(createColor(colorBetween, gf2, i));
		}
		// Now add the end color directly.
		colors.add(colorEnd);
		return colors;
	}

	private GradientFactors calculateFactors(Color colorStart, Color colorEnd, int nrOfSteps) {
		GradientFactors gf = new GradientFactors();
		gf.red = (float) (colorEnd.getRed() - colorStart.getRed()) / nrOfSteps;
		gf.green = (float) (colorEnd.getGreen() - colorStart.getGreen()) / nrOfSteps;
		gf.blue = (float) (colorEnd.getBlue() - colorStart.getBlue()) / nrOfSteps;
		return gf;
	}

	private Color createColor(Color colorStart, GradientFactors gf, int step) {
		int cr = (int) (colorStart.getRed() + gf.red * step);
		int cg = (int) (colorStart.getGreen() + gf.green * step);
		int cb = (int) (colorStart.getBlue() + gf.blue * step);
		return new Color(cr, cg, cb);
	}
}
