/**
 * 
 */
package de.lexasoft.mandelbrot.swing.model;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * Describes the attributes, used to control the colors in the calculation.
 * 
 * @author nierax
 *
 */
public interface ColorControllerModel extends ControllerModel {

	/**
	 * 
	 * @return The palette variant used.
	 */
	PaletteVariant paletteVariant();

	/**
	 * 
	 * @return The color grading style used.
	 */
	ColorGradingStyle gradingStyle();

	/**
	 * 
	 * @return The total number of colors used.
	 */
	int totalNrOfColors();

}
