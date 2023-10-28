/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.List;

/**
 * This colorize method uses a given color palette to colorize the Mandelbrot
 * set.
 * <p>
 * The index in the palette is called with iteration modulo number of colors.
 * 
 * @author nierax
 */
public class MandelbrotColorPalette implements MandelbrotColorize {

	private final List<Color> palette;
	private final Color mandelbrotColor;
	private final int nrOfColors;

	private MandelbrotColorPalette(List<Color> palette, Color mandelbrotColor) {
		this.palette = palette;
		this.nrOfColors = palette.size();
		this.mandelbrotColor = mandelbrotColor;
	}

	/**
	 * Create a new colorize method from the given palette. In addition the color
	 * for the Mandelbrot set can be given.
	 * 
	 * @param palette         The color palette to use
	 * @param mandelbrotColor The color, used for the Mandelbrot set.
	 * @return The new object of {@link #MandelbrotColorPalette(List)}.
	 */
	public static MandelbrotColorPalette of(List<Color> palette, Color mandelbrotColor) {
		return new MandelbrotColorPalette(palette, mandelbrotColor);
	}

	@Override
	public Color getColorForIteration(int iteration, int maxIter) {
		if (iteration == maxIter) {
			return mandelbrotColor;
		}
		int paletteIdx = iteration % nrOfColors;
		return palette.get(paletteIdx);
	}

	public List<Color> getPalette() {
		return palette;
	}

}
