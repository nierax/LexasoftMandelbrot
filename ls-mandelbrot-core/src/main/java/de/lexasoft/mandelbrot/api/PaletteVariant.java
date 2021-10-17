/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	BLACK_WHITE("Black and White"),

	/**
	 * Rainbow in 29 colors
	 */
	RAINBOW29("Rainbow 29 colors", new Color(128, 0, 0), new Color(130, 40, 40), new Color(141, 83, 59),
	    new Color(153, 102, 117), new Color(153, 102, 169), new Color(128, 0, 128), new Color(101, 0, 155),
	    new Color(72, 0, 225), new Color(4, 0, 208), new Color(0, 68, 220), new Color(1, 114, 226),
	    new Color(1, 159, 232), new Color(11, 175, 162), new Color(23, 179, 77), new Color(0, 212, 28),
	    new Color(0, 255, 0), new Color(128, 255, 0), new Color(200, 255, 0), new Color(255, 255, 0),
	    new Color(255, 219, 0), new Color(255, 182, 0), new Color(255, 146, 0), new Color(255, 109, 0),
	    new Color(255, 73, 0), new Color(255, 0, 0), new Color(255, 0, 128), new Color(255, 105, 180),
	    new Color(255, 0, 255), new Color(168, 0, 185)),

	/**
	 * Rainbow in 7 colors
	 */
	RAINBOW7("Rainbow 7 colors", Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
	    // Indigo
	    new Color(75, 0, 130),
	    // Purple
	    new Color(136, 0, 255)),

	/**
	 * 2 colors sky blue and white
	 */
	BLUEWHITE("Blue and White", new Color(25, 140, 255), Color.WHITE),

	/**
	 * Custom palette, defined by user (f.ex. in YAML file)
	 */
	CUSTOM("Custom palette");

	private List<Color> colorPalette;
	private String name;

	private PaletteVariant(String name, Color... colors) {
		this.name = name;
		colorPalette = new ArrayList<>();
		for (Color color : colors) {
			colorPalette.add(color);
		}
	}

	/**
	 * @return the nrOfColorsUngraded
	 */
	public int nrOfColorsUngraded() {
		return colorPalette.size();
	}

	public List<Color> colorPalette() {
		return colorPalette;
	}

	@Override
	public String toString() {
		return name;
	}

}
