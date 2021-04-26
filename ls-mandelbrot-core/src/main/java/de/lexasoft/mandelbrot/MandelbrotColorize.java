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

	/**
	 * Factory method for a colorize object.
	 * 
	 * @param variant         The variant used
	 * @param colors          The colors, describing the palette to create.
	 * @param colorGrading    The number of steps for color gradients.
	 * @param mandelbrotColor The color used for the Mandelbrot set.
	 * @return A colorize object, prepared to use.
	 */
	static MandelbrotColorize of(PaletteVariant variant, List<Color> colors, int colorGrading, Color mandelbrotColor) {
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
			colorize = MandelbrotColorPalette.of(cFactory.createGradientList(colors.get(0), colors.get(1), colorGrading),
			    mandelbrotColor);
			break;
		case GRADIENT3:
			if (colors.size() < 3) {
				throw new IllegalArgumentException("3 colors required for gradient 3.");
			}
			colorize = MandelbrotColorPalette
			    .of(cFactory.createGradientList(colors.get(0), colors.get(1), colors.get(2), colorGrading), mandelbrotColor);
			break;
		case RAINBOW29:
			List<Color> rainbow = cFactory.createRainbowPalette29();
			if (colorGrading > 0) {
				rainbow = cFactory.createGradientList(rainbow, colorGrading);
			}
			colorize = MandelbrotColorPalette.of(rainbow, mandelbrotColor);
			break;
		case CUSTOM:
			colorize = MandelbrotColorPalette.of(cFactory.createGradientList(colors, colorGrading), mandelbrotColor);
			break;
		default:
			break;

		}
		return colorize;
	}

	/**
	 * Factory method for a colorize object. Black is used as default color of the
	 * Mandelbrot set.
	 * 
	 * @param variant   The variant used
	 * @param colors    The colors, describing the palette to create.
	 * @param nrOfSteps The number of steps for color gradients.
	 * @return A colorize object, prepared to use.
	 */
	static MandelbrotColorize of(PaletteVariant variant, List<Color> colors, int nrOfSteps) {
		return of(variant, colors, nrOfSteps, Color.BLACK);
	}
}