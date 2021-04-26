/**
 * 
 */
package de.lexasoft.mandelbrot;

/**
 * Which color palette to use?
 * 
 * @author nierax
 *
 */
public enum PaletteVariant {

	/**
	 * Black an White only, grading not possible
	 */
	BLACK_WHITE,

	/**
	 * Rainbow in 29 colors
	 */
	RAINBOW29,

	/**
	 * Rainbow in 7 colors
	 */
	RAINBOW7,

	/**
	 * Custom palette, defined by user (f.ex. in YAML file)
	 */
	CUSTOM

}
