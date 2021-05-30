/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.List;

import de.lexasoft.mandelbrot.api.ColorGrading;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * This Factory creates objects of {@link MandelbrotColorize}.
 * 
 * @author nierax
 *
 */
public class MandelbrotColorizeFactory {

	private ColorPaletteFactory cFactory;

	/**
	 * 
	 */
	private MandelbrotColorizeFactory() {
		super();
		cFactory = new ColorPaletteFactory();
	}

	/**
	 * Creates the {@link MandelbrotColorize} object from the given palette. If
	 * colorGrading is 0, no grading will be made.
	 * 
	 * @param ungraded        The original color palette.
	 * @param colorGrading    The color grading (number of colors, the palette
	 *                        should have in the end).
	 * @param mandelbrotColor The color of the MandelbrotIterator set.
	 * @return An object of {@link MandelbrotColorize}, ready to use.
	 */
	private MandelbrotColorize createAndGradePalette(List<Color> ungraded, MandelbrotColorGrading colorGrading,
	    Color mandelbrotColor) {
		boolean isGradingUsed = (colorGrading != null) && colorGrading.getColorsTotal() > 0;
		List<Color> custom = !isGradingUsed ? ungraded
		    : ColorGrading.of(colorGrading.getStyle()).gradePalette(ungraded, colorGrading.getColorsTotal());
		return MandelbrotColorPalette.of(custom, mandelbrotColor);
	}

	/**
	 * Private method to create {@link MandelbrotColorize} object
	 * 
	 * @param variant         The variant used
	 * @param colors          The colors, describing the palette to create.
	 * @param colorGrading    The number of steps for color gradients.
	 * @param mandelbrotColor The color used for the MandelbrotIterator set.
	 * @return An object of {@link MandelbrotColorize}, ready to use.
	 */
	private MandelbrotColorize createColorize(PaletteVariant variant, List<Color> colors,
	    MandelbrotColorGrading colorGrading, Color mandelbrotColor) {
		MandelbrotColorize colorize = null;
		switch (variant) {
		case BLACK_WHITE:
			colorize = new MandelbrotBlackWhite();
			break;
		case RAINBOW29:
			colorize = createAndGradePalette(cFactory.createRainbowPalette29(), colorGrading, mandelbrotColor);
			break;
		case RAINBOW7:
			colorize = createAndGradePalette(cFactory.createRainbowPalette7(), colorGrading, mandelbrotColor);
			break;
		case CUSTOM:
			colorize = createAndGradePalette(colors, colorGrading, mandelbrotColor);
			break;
		default:
			break;

		}
		return colorize;

	}

	/**
	 * Factory method for a colorize object.
	 * 
	 * @param variant         The variant used
	 * @param colors          The colors, describing the palette to create.
	 * @param colorGrading    The number of steps for color gradients. 0, if no
	 *                        grading is wanted.
	 * @param mandelbrotColor The color used for the MandelbrotIterator set.
	 * @return An object of {@link MandelbrotColorize}, ready to use.
	 */
	public static MandelbrotColorize of(PaletteVariant variant, List<Color> colors, MandelbrotColorGrading colorGrading,
	    Color mandelbrotColor) {
		MandelbrotColorizeFactory factory = new MandelbrotColorizeFactory();
		return factory.createColorize(variant, colors, colorGrading, mandelbrotColor);
	}

	/**
	 * Factory method for a colorize object. Black is used as default color of the
	 * MandelbrotIterator set.
	 * 
	 * @param variant   The variant used
	 * @param colors    The colors, describing the palette to create.
	 * @param nrOfSteps The number of steps for color gradients.
	 * @return An object of {@link MandelbrotColorize}, ready to use.
	 */
	public static MandelbrotColorize of(PaletteVariant variant, List<Color> colors, MandelbrotColorGrading colorGrading) {
		return of(variant, colors, colorGrading, Color.BLACK);
	}
}
