package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.List;

public interface MandelbrotColorize {

	/**
	 * Find the color for this number of iterations.
	 * <p>
	 * If the point is in the Mandelbrot set, black should be returned, otherwise a
	 * color depending on the algorithm of the implementation.
	 * 
	 * @param iteration Number of iterations on a special point.
	 * @param maxIter   Maximum number of iterations on a special point.
	 * @return The color of the point with the given number of iterations.
	 */
	Color getColorForIteration(int iteration, int maxIter);

	static MandelbrotColorize of(ColorVariant variant, List<Color> colors, int colorInterval) {
		ColorPaletteFactory cFactory = new ColorPaletteFactory();
		MandelbrotColorize colorize = null;
		switch (variant) {
		case BLACK_WHITE:
			colorize = new MandelbrotBlackWhite();
			break;
		case GRADIENT2:
			if (colors.size() < 2) {
				throw new IllegalArgumentException("2 colors required for gradient 2.");
			}
			colorize = MandelbrotColorPalette.of(cFactory.createGradientList(colors.get(0), colors.get(1), colorInterval));
			break;
		case GRADIENT3:
			if (colors.size() < 3) {
				throw new IllegalArgumentException("3 colors required for gradient 3.");
			}
			colorize = MandelbrotColorPalette
			    .of(cFactory.createGradientList(colors.get(0), colors.get(1), colors.get(2), colorInterval));
			break;
		case RAINBOW29:
			colorize = MandelbrotColorPalette.of(cFactory.createRainbowPalette29());
			break;
		default:
			break;

		}
		return colorize;

	}

}