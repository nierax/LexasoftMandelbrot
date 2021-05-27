/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the grading steps between two colors.
 * 
 * @author nierax
 *
 */
public class ColorGrader {

	/**
	 * Grading factor for color red.
	 */
	private double gfRed;

	/**
	 * Grading factor for color green.
	 */
	private double gfGreen;

	/**
	 * Grading factor for color blue.
	 */
	private double gfBlue;

	private Color start;
	private int nrOfSteps;

	/**
	 * 
	 */
	private ColorGrader(Color colorStart, Color colorEnd, int nrOfSteps) {
		this.start = colorStart;
		this.nrOfSteps = nrOfSteps;
		calculateFactors(colorStart, colorEnd, nrOfSteps);
	}

	private void calculateFactors(Color colorStart, Color colorEnd, int nrOfSteps) {
		gfRed = (double) (colorEnd.getRed() - colorStart.getRed()) / (nrOfSteps + 1);
		gfGreen = (double) (colorEnd.getGreen() - colorStart.getGreen()) / (nrOfSteps + 1);
		gfBlue = (double) (colorEnd.getBlue() - colorStart.getBlue()) / (nrOfSteps + 1);
	}

	/**
	 * Grades the colors from start to end and gets a list with all intermediate
	 * steps, but without the colors start and end themselves.
	 * 
	 * @return
	 */
	public List<Color> gradeColors() {
		List<Color> colorList = new ArrayList<>();
		for (int i = 1; i <= nrOfSteps; i++) {
			colorList.add(gradeColor(start, i));
		}
		return colorList;
	}

	private Color gradeColor(Color start, int step) {
		int cr = (int) Math.round((start.getRed() + gfRed * step));
		int cg = (int) Math.round((start.getGreen() + gfGreen * step));
		int cb = (int) Math.round((start.getBlue() + gfBlue * step));
		return new Color(cr, cg, cb);
	}

	public static ColorGrader of(Color colorStart, Color colorEnd, int nrOfSteps) {
		return new ColorGrader(colorStart, colorEnd, nrOfSteps);
	}

}
