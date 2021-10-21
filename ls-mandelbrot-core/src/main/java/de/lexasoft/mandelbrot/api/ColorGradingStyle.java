/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * The style of color grading in Mandelbrot colorization.
 * 
 * @author nierax
 *
 */
public enum ColorGradingStyle {

	NONE("None"), LINE("Line"), CIRCLE("Circle");

	private String name;

	private ColorGradingStyle(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
