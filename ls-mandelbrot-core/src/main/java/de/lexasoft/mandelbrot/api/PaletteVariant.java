/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * Which color palette to use?
 * 
 * @author nierax
 *
 */
public enum PaletteVariant {

	/**
	 * Black and White only, grading not possible
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
	 * 2 colors sky blue and white
	 */
	BLUEWHITE,

	/**
	 * Custom palette, defined by user (f.ex. in YAML file)
	 */
	CUSTOM

}
