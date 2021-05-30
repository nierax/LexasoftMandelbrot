package de.lexasoft.mandelbrot.api;

import java.awt.Color;
import java.util.List;

public interface ColorGrading {

	/**
	 * Grades every color palette by inserting steps between the colors in the
	 * palette.
	 * 
	 * @param ungradedPalette The original palette
	 * @param grading         The number of entries, the graded palette should have
	 * @return The graded color palette
	 */
	List<Color> gradePalette(List<Color> ungradedPalette, int grading);

}