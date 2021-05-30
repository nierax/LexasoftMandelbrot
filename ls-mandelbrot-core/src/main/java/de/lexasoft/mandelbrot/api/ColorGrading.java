package de.lexasoft.mandelbrot.api;

import java.awt.Color;
import java.util.List;

import de.lexasoft.mandelbrot.ColorGradingCircle;
import de.lexasoft.mandelbrot.ColorGradingLine;

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

	/**
	 * Factory method for color grading implementations
	 * 
	 * @param style The style, for which the implementation should be used.
	 * @return A new object for the given color grading style.
	 */
	static ColorGrading of(ColorGradingStyle style) {
		switch (style) {
		case LINE:
			return new ColorGradingLine();
		case CIRCLE:
			return new ColorGradingCircle();
		default:
			throw new IllegalArgumentException("Can't find an implementation for color grading " + style);
		}
	}

}