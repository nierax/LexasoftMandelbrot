/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * All attributes connected to the color definitions for a Mandelbrot
 * calculation.
 * 
 * @author nierax
 *
 */
public class ColorAttributesDTO {

	@JsonProperty
	private PaletteVariant paletteVariant;
	@JsonProperty
	private List<ColorDTO> customColorPalette;
	@JsonProperty
	private MandelbrotColorGrading colorGrading;
	@JsonProperty
	private ColorDTO mandelbrotColor;

	/**
	 * @return the paletteVariant
	 */
	public PaletteVariant getPaletteVariant() {
		return paletteVariant;
	}

	/**
	 * @param paletteVariant the paletteVariant to set
	 */
	public void setPaletteVariant(PaletteVariant paletteVariant) {
		this.paletteVariant = paletteVariant;
	}

	/**
	 * @return the customColorPalette
	 */
	public List<ColorDTO> getCustomColorPalette() {
		return customColorPalette;
	}

	/**
	 * @param customColorPalette the customColorPalette to set
	 */
	public void setCustomColorPalette(List<ColorDTO> customColorPalette) {
		this.customColorPalette = customColorPalette;
	}

	/**
	 * @return the colorGrading
	 */
	public MandelbrotColorGrading getColorGrading() {
		return colorGrading;
	}

	/**
	 * @param colorGrading the colorGrading to set
	 */
	public void setColorGrading(MandelbrotColorGrading colorGrading) {
		this.colorGrading = colorGrading;
	}

	/**
	 * @return the mandelbrotColor
	 */
	public ColorDTO getMandelbrotColor() {
		return mandelbrotColor;
	}

	/**
	 * @param mandelbrotColor the mandelbrotColor to set
	 */
	public void setMandelbrotColor(ColorDTO mandelbrotColor) {
		this.mandelbrotColor = mandelbrotColor;
	}

}
