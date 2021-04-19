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

	private List<Color> palette;
	private int nrOfColors;

	private MandelbrotColorPalette(List<Color> palette) {
		this.palette = palette;
		this.nrOfColors = palette.size();
	}

	/**
	 * Create a new colorize method from the given palette.
	 * 
	 * @param palette The color palette to use
	 * @return the new object of {@link #MandelbrotColorPalette(List)}.
	 */
	public static MandelbrotColorPalette of(List<Color> palette) {
		return new MandelbrotColorPalette(palette);
	}

	@Override
	public Color getColorForIteration(int iteration, int maxIter) {
		if (iteration == maxIter) {
			return Color.BLACK;
		}
		int step = iteration % nrOfColors;
		return palette.get(step);
	}

	public List<Color> getPalette() {
		return palette;
	}

}
