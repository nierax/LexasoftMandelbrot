/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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

	class GradingAttr {
		int grading;
		int[] stepsPerInterval;
		int nrOfIntervals;
	}

	private void assertGradingPossible(int nrOfColors, int grading) {
		// Make sure, there is at least one step between all colors
		int minimumGrading = 2 * nrOfColors - 1;
		if (grading < minimumGrading) {
			throw new IllegalArgumentException(
			    String.format("For %s number of colors grading must be at least %s.", nrOfColors, minimumGrading));
		}
	}

	private GradingAttr calculateGradingAttributes(int nrOfColors, int grading) {
		assertGradingPossible(nrOfColors, grading);
		GradingAttr ga = new GradingAttr();
		ga.grading = grading;
		ga.nrOfIntervals = nrOfColors - 1;
		// Number of steps per interval
		int stepsPI = (grading - nrOfColors) / ga.nrOfIntervals;
		// Fill array steps per interval with the common steps per interval
		ga.stepsPerInterval = new int[ga.nrOfIntervals];
		Arrays.fill(ga.stepsPerInterval, stepsPI);
		// If there is a remainder, the number of steps is increased by one for some
		// intervals, beginning with the last one.
		int remainderPI = (grading - nrOfColors) % ga.nrOfIntervals;
		if (remainderPI > 0) {
			int ii = ga.nrOfIntervals - 1;
			for (int i = 0; i < remainderPI; i++) {
				ga.stepsPerInterval[ii]++;
				ii--;
			}
		}
		return ga;
	}

	/**
	 * Grades every color palette by inserting steps between the colors in the
	 * palette.
	 * 
	 * @param ungradedPalette The original palette
	 * @param grading         The number of entries, the graded palette should have
	 * @return The graded color palette
	 */
	public List<Color> createGradientList(List<Color> ungradedPalette, int grading) {
		// Calculate the parameters of grading
		GradingAttr ga = calculateGradingAttributes(ungradedPalette.size(), grading);
		List<Color> gradedList = new ArrayList<>();
		// Handling for each interval
		for (int i = 0; i < ga.nrOfIntervals; i++) {
			Color firstColor = ungradedPalette.get(i);
			Color secondColor = ungradedPalette.get(i + 1);
			// Put the first color of the interval in the list
			gradedList.add(firstColor);
			// Then grade the colors in the given number of steps to the next color in the
			// interval
			for (int j = 0; j < ga.stepsPerInterval[i]; j++) {
				GradientFactors gf = calculateFactors(firstColor, secondColor, ga.stepsPerInterval[i] + 1);
				gradedList.add(gradeColor(firstColor, gf, j + 1));
			}
		}
		// Now add the last color directly
		gradedList.add(ungradedPalette.get(ungradedPalette.size() - 1));
		return gradedList;
	}

	private GradientFactors calculateFactors(Color colorStart, Color colorEnd, int nrOfSteps) {
		GradientFactors gf = new GradientFactors();
		gf.red = (float) (colorEnd.getRed() - colorStart.getRed()) / nrOfSteps;
		gf.green = (float) (colorEnd.getGreen() - colorStart.getGreen()) / nrOfSteps;
		gf.blue = (float) (colorEnd.getBlue() - colorStart.getBlue()) / nrOfSteps;
		return gf;
	}

	private Color gradeColor(Color colorStart, GradientFactors gf, int step) {
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

	/**
	 * Creates a color palette with rainbow colors in 7 steps.
	 * 
	 * @return
	 */
	public List<Color> createRainbowPalette7() {
		List<Color> colors = new ArrayList<>(7);
		colors.add(Color.RED);
		colors.add(Color.ORANGE);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(new Color(75, 0, 130)); // Indigo
		colors.add(new Color(136, 0, 255)); // Purple
		return colors;
	}
}
