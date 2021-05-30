package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.lexasoft.mandelbrot.api.ColorGrading;

public abstract class AbstractColorGrading implements ColorGrading {

	class GradingAttr {
		int grading;
		int[] stepsPerInterval;
		int nrOfIntervals;
	}

	public AbstractColorGrading() {
		super();
	}

	/**
	 * Must return the minimum number of grading, depending on the number of colors
	 * in the original (ungraded) palette.
	 * 
	 * @param noCUngraded The number of colors in the original color palette.
	 * @return The minimum grading
	 */
	protected abstract int minimumGrading(int noCUngraded);

	/**
	 * Must return the number of intervals, needed to fulfill the grading.
	 * 
	 * @param noCUngraded The number of colors in the original color palette.
	 * @return The number of intervals for this number of colors.
	 */
	protected abstract int nrOfIntervals(int noCUngraded);

	private void assertGradingPossible(int nrOfColors, int grading) {
		// Make sure, there is at least one step between all colors
		int minimumGrading = minimumGrading(nrOfColors);
		if (grading < minimumGrading(nrOfColors)) {
			throw new IllegalArgumentException(
			    String.format("For %s number of colors grading must be at least %s.", nrOfColors, minimumGrading));
		}
	}

	private GradingAttr calculateGradingAttributes(int noCUngraded, int grading) {
		assertGradingPossible(noCUngraded, grading);
		GradingAttr ga = new GradingAttr();
		ga.grading = grading;
		ga.nrOfIntervals = nrOfIntervals(noCUngraded);
		// Number of steps per interval
		int stepsPI = (grading - noCUngraded) / ga.nrOfIntervals;
		// Fill array steps per interval with the common steps per interval
		ga.stepsPerInterval = new int[ga.nrOfIntervals];
		Arrays.fill(ga.stepsPerInterval, stepsPI);
		// If there is a remainder, the number of steps is increased by one for some
		// intervals, beginning with the last one.
		int remainderPI = (grading - noCUngraded) % ga.nrOfIntervals;
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
	@Override
	public List<Color> gradePalette(List<Color> ungradedPalette, int grading) {
		// Calculate the parameters of grading
		int noCUngraded = ungradedPalette.size();
		GradingAttr ga = calculateGradingAttributes(ungradedPalette.size(), grading);
		List<Color> gradedList = new ArrayList<>();
		int lastAddedColorIdx = 0;
		for (int i = 0; i < ga.nrOfIntervals; i++) {
			// if i or i+1 is greater than the number of colors => start from the beginning.
			// For this there is a modulo division.
			Color firstColor = ungradedPalette.get(i % noCUngraded);
			Color secondColor = ungradedPalette.get((i + 1) % noCUngraded);
			gradedList.add(firstColor);
			gradedList.addAll(ColorGrader.of(firstColor, secondColor, ga.stepsPerInterval[i]).gradeColors());
			lastAddedColorIdx = i;
		}
		// Check, whether the last color in the original palette has been added already.
		// If so, don't add it again.
		if (lastAddedColorIdx < ungradedPalette.size() - 1) {
			gradedList.add(ungradedPalette.get(ungradedPalette.size() - 1));
		}
		return gradedList;
	}

}