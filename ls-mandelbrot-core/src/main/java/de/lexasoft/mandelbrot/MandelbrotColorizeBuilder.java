/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import de.lexasoft.mandelbrot.api.ColorGrading;
import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * This Factory creates objects of {@link MandelbrotColorize}.
 * 
 * @author nierax
 *
 */
public class MandelbrotColorizeBuilder {

	private Optional<PaletteVariant> palette = Optional.empty();
	private Optional<List<Color>> colors = Optional.empty();
	private Optional<MandelbrotColorGrading> grading = Optional.empty();
	private Optional<Color> mandelbrotColor = Optional.empty();

	/**
	 * 
	 */
	private MandelbrotColorizeBuilder() {
		super();
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
		boolean isGradingUsed = (colorGrading != null) && (!colorGrading.getStyle().equals(ColorGradingStyle.NONE))
		    && (colorGrading.getColorsTotal() > 0);
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
		case CUSTOM:
			colorize = createAndGradePalette(colors, colorGrading, mandelbrotColor);
			break;
		default:
			colorize = createAndGradePalette(variant.colorPalette(), colorGrading, mandelbrotColor);
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
		MandelbrotColorizeBuilder factory = new MandelbrotColorizeBuilder();
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

	public static final MandelbrotColorizeBuilder of() {
		return new MandelbrotColorizeBuilder();
	}

	public MandelbrotColorizeBuilder withPalette(PaletteVariant palette) {
		this.palette = Optional.ofNullable(palette);
		return this;
	}

	public MandelbrotColorizeBuilder withGrading(MandelbrotColorGrading grading) {
		this.grading = Optional.ofNullable(grading);
		return this;
	}

	public MandelbrotColorizeBuilder withMandelbrotColor(Color mandelbrotColor) {
		this.mandelbrotColor = Optional.ofNullable(mandelbrotColor);
		return this;
	}

	public MandelbrotColorizeBuilder withColors(List<Color> colors) {
		this.colors = Optional.ofNullable(colors);
		return this;
	}

	public MandelbrotColorize build() {
		return createColorize( //
		    this.palette.orElse(PaletteVariant.BLACK_WHITE), //
		    this.colors.orElseGet(PaletteVariant.BLUEWHITE::colorPalette), //
		    this.grading.orElseGet(MandelbrotColorGrading::none), //
		    this.mandelbrotColor.orElse(Color.BLACK));
	}
}
