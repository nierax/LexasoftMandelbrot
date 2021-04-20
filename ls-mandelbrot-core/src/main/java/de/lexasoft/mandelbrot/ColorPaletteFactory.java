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
public class ColorPaletteFactory {

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

	/**
	 * Creates a color palette with rainbow colors in 29 steps.
	 * 
	 * @return
	 */
	public List<Color> createRainbowPalette29() {
		List<Color> colors = new ArrayList<>(29);
		colors.add(new Color(128, 0, 0));
		colors.add(new Color(130, 40, 40));
		colors.add(new Color(141, 83, 59));
		colors.add(new Color(153, 102, 117));
		colors.add(new Color(153, 102, 169));
		colors.add(new Color(128, 0, 128));
		colors.add(new Color(101, 0, 155));
		colors.add(new Color(72, 0, 225));
		colors.add(new Color(4, 0, 208));
		colors.add(new Color(0, 68, 220));
		colors.add(new Color(1, 114, 226));
		colors.add(new Color(1, 159, 232));
		colors.add(new Color(11, 175, 162));
		colors.add(new Color(23, 179, 77));
		colors.add(new Color(0, 212, 28));
		colors.add(new Color(0, 255, 0));
		colors.add(new Color(128, 255, 0));
		colors.add(new Color(200, 255, 0));
		colors.add(new Color(255, 255, 0));
		colors.add(new Color(255, 219, 0));
		colors.add(new Color(255, 182, 0));
		colors.add(new Color(255, 146, 0));
		colors.add(new Color(255, 109, 0));
		colors.add(new Color(255, 73, 0));
		colors.add(new Color(255, 0, 0));
		colors.add(new Color(255, 0, 128));
		colors.add(new Color(255, 105, 180));
		colors.add(new Color(255, 0, 255));
		colors.add(new Color(168, 0, 185));
		return colors;
	}
}
