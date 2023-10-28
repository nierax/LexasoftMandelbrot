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
	 * @param mandelbrotColor The color of the Mandelbrot set.
	 * @return An object of {@link MandelbrotColorize}, ready to use.
	 */
	private MandelbrotColorize createAndGradePalette(List<Color> ungraded, MandelbrotColorGrading colorGrading,
	    Color mandelbrotColor) {
		List<Color> colors = !colorGrading.shouldBeGraded() ? ungraded
		    : ColorGrading.of(colorGrading.getStyle()).gradePalette(ungraded, colorGrading.getColorsTotal());
		return MandelbrotColorPalette.of(colors, mandelbrotColor);
	}

	/**
	 * Private method to create {@link MandelbrotColorize} object
	 * 
	 * @param variant         The variant used
	 * @param colors          The colors, describing the palette to create.
	 * @param colorGrading    The number of steps for color gradients.
	 * @param mandelbrotColor The color used for the Mandelbrot set.
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
	 * Factory method for the builder.
	 * 
	 * @return
	 */
	public static final MandelbrotColorizeBuilder of() {
		return new MandelbrotColorizeBuilder();
	}

	/**
	 * The palette type to use.
	 * 
	 * @Default {@link PaletteVariant#BLACK_WHITE}
	 * @see PaletteVariant
	 * @param palette Use this type of palette.
	 * @return Reference to the builder for fluent api.
	 */
	public MandelbrotColorizeBuilder withPalette(PaletteVariant palette) {
		this.palette = Optional.ofNullable(palette);
		return this;
	}

	/**
	 * Describes the grading style to use.
	 * 
	 * @Default {@link ColorGradingStyle#NONE}
	 * @param grading
	 * @return
	 */
	public MandelbrotColorizeBuilder withGrading(MandelbrotColorGrading grading) {
		this.grading = Optional.ofNullable(grading);
		return this;
	}

	/**
	 * The background color in the Mandelbrot set.
	 * 
	 * @Default {@link java.awt.Color#BLACK}
	 * @param mandelbrotColor
	 * @return
	 */
	public MandelbrotColorizeBuilder withMandelbrotColor(Color mandelbrotColor) {
		this.mandelbrotColor = Optional.ofNullable(mandelbrotColor);
		return this;
	}

	/**
	 * The list of colors, if not given by the palette variant.
	 * 
	 * @Default {@link PaletteVariant#BLUEWHITE#colorPalette()}
	 * @param colors
	 * @return
	 */
	public MandelbrotColorizeBuilder withColors(List<Color> colors) {
		this.colors = Optional.ofNullable(colors);
		return this;
	}

	/**
	 * Builds the colorize object from the values, given before or the defaults, as
	 * mentioned with the with methods.
	 * 
	 * @return
	 */
	public MandelbrotColorize build() {
		return createColorize( //
		    this.palette.orElse(PaletteVariant.BLACK_WHITE), //
		    this.colors.orElseGet(PaletteVariant.BLUEWHITE::colorPalette), //
		    this.grading.orElseGet(MandelbrotColorGrading::none), //
		    this.mandelbrotColor.orElse(Color.BLACK));
	}
}
