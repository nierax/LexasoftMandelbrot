/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements the color line grading.
 * 
 * @see <a
 *      href=https://github.com/nierax/LexasoftMandelbrot/blob/feature/ColorGradingCircle/documentation/ColorGrading-Line.pdf>Color
 *      Grading Line documentation</a>
 * 
 * @author nierax
 *
 */
public class ColorGradingLine {

	class GradingAttr {
		int grading;
		int[] stepsPerInterval;
		int nrOfIntervals;
	}

	/**
	 * 
	 */
	ColorGradingLine() {
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
	public List<Color> gradePalette(List<Color> ungradedPalette, int grading) {
		// Calculate the parameters of grading
		GradingAttr ga = calculateGradingAttributes(ungradedPalette.size(), grading);
		List<Color> gradedList = new ArrayList<>();
		for (int i = 0; i < ga.nrOfIntervals; i++) {
			Color firstColor = ungradedPalette.get(i);
			Color secondColor = ungradedPalette.get(i + 1);
			gradedList.add(firstColor);
			gradedList.addAll(ColorGrader.of(firstColor, secondColor, ga.stepsPerInterval[i]).gradeColors());
		}
		gradedList.add(ungradedPalette.get(ungradedPalette.size() - 1));
		return gradedList;
	}

	public static ColorGradingLine of() {
		return new ColorGradingLine();
	}

}
