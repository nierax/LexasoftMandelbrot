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
	 * Deprecated. Use custom with grading instead
	 */
	@Deprecated
	GRADIENT2,

	/**
	 * Deprecated. Use custom with grading instead
	 */
	@Deprecated
	GRADIENT3,

	/**
	 * Rainbow in 29 colors
	 */
	RAINBOW29,

	/**
	 * Custom palette, defined by user (f.ex. in YAML file)
	 */
	CUSTOM

}
