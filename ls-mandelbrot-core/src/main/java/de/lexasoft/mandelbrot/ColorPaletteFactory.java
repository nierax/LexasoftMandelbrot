/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the gradient between two colors in a given number of steps.
 * <p>
 * Returns the list of colors on the way from one color to the next.
 * 
 * @author nierax
 *
 */
public class ColorPaletteFactory {

	/**
	 * Creates a color palette with rainbow colors in 29 steps.
	 * 
	 * @return
	 */
	public List<Color> createRainbowPalette29() {
		List<Color> colors = new ArrayList<>(29);
		colors.add(new Color(128, 0, 0));
		colors.add(new Color(130, 40, 40));
		colors.add(new Color(141, 83, 59));
		colors.add(new Color(153, 102, 117));
		colors.add(new Color(153, 102, 169));
		colors.add(new Color(128, 0, 128));
		colors.add(new Color(101, 0, 155));
		colors.add(new Color(72, 0, 225));
		colors.add(new Color(4, 0, 208));
		colors.add(new Color(0, 68, 220));
		colors.add(new Color(1, 114, 226));
		colors.add(new Color(1, 159, 232));
		colors.add(new Color(11, 175, 162));
		colors.add(new Color(23, 179, 77));
		colors.add(new Color(0, 212, 28));
		colors.add(new Color(0, 255, 0));
		colors.add(new Color(128, 255, 0));
		colors.add(new Color(200, 255, 0));
		colors.add(new Color(255, 255, 0));
		colors.add(new Color(255, 219, 0));
		colors.add(new Color(255, 182, 0));
		colors.add(new Color(255, 146, 0));
		colors.add(new Color(255, 109, 0));
		colors.add(new Color(255, 73, 0));
		colors.add(new Color(255, 0, 0));
		colors.add(new Color(255, 0, 128));
		colors.add(new Color(255, 105, 180));
		colors.add(new Color(255, 0, 255));
		colors.add(new Color(168, 0, 185));
		return colors;
	}

	/**
	 * Creates a color palette with rainbow colors in 7 steps.
	 * 
	 * @return
	 */
	public List<Color> createRainbowPalette7() {
		List<Color> colors = new ArrayList<>(7);
		colors.add(Color.RED);
		colors.add(Color.ORANGE);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(new Color(75, 0, 130)); // Indigo
		colors.add(new Color(136, 0, 255)); // Purple
		return colors;
	}

	/**
	 * Creates a color palette with two colors: blue and white
	 * 
	 * @return
	 */
	public List<Color> createBlueWhitePalette() {
		List<Color> colors = new ArrayList<>(2);
		colors.add(new Color(25, 140, 255));
		colors.add(Color.WHITE);
		return colors;
	}

	public static ColorPaletteFactory of() {
		return new ColorPaletteFactory();
	}
}
