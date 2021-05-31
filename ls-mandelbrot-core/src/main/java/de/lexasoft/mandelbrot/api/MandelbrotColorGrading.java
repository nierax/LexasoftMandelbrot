/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nierax
 *
 */
public class MandelbrotColorGrading {

	@JsonProperty
	private ColorGradingStyle style;
	@JsonProperty
	private int colorsTotal;

	/**
	 * Default constructor needed to be read in via YAML
	 */
	public MandelbrotColorGrading() {
	}

	/**
	 * 
	 */
	public MandelbrotColorGrading(ColorGradingStyle style, int colorsTotal) {
		this.style = style;
		this.colorsTotal = colorsTotal;
	}

	/**
	 * @return the style
	 */
	public ColorGradingStyle getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(ColorGradingStyle style) {
		this.style = style;
	}

	/**
	 * @return the colorsTotal
	 */
	public int getColorsTotal() {
		return colorsTotal;
	}

	/**
	 * @param colorsTotal the colorsTotal to set
	 */
	public void setColorsTotal(int colorsTotal) {
		this.colorsTotal = colorsTotal;
	}

	public static MandelbrotColorGrading of(ColorGradingStyle style, int colorsTotal) {
		return new MandelbrotColorGrading(style, colorsTotal);
	}

}
