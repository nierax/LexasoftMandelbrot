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
	BLACK_WHITE(1),

	/**
	 * Rainbow in 29 colors
	 */
	RAINBOW29(29),

	/**
	 * Rainbow in 7 colors
	 */
	RAINBOW7(7),

	/**
	 * 2 colors sky blue and white
	 */
	BLUEWHITE(2),

	/**
	 * Custom palette, defined by user (f.ex. in YAML file)
	 */
	CUSTOM(-1);

	private int nrOfColorsUngraded;

	private PaletteVariant(int nrOfColorsUngraded) {
		this.nrOfColorsUngraded = nrOfColorsUngraded;
	}

	/**
	 * @return the nrOfColorsUngraded
	 */
	public int getNrOfColorsUngraded() {
		return nrOfColorsUngraded;
	}

}
