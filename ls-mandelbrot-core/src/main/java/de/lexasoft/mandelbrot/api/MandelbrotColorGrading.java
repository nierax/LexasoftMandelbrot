/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.util.Objects;

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
	 * Creates an object, signalizing, that no color grading is wanted.
	 * 
	 * @return MandelbrotColorGrading with no color grading
	 */
	public static MandelbrotColorGrading none() {
		return new MandelbrotColorGrading(ColorGradingStyle.NONE, 0);
	}

	public boolean shouldBeGraded() {
		return style != ColorGradingStyle.NONE && colorsTotal > 0;
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

	@Override
	public int hashCode() {
		return Objects.hash(colorsTotal, style);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MandelbrotColorGrading other = (MandelbrotColorGrading) obj;
		return colorsTotal == other.colorsTotal && style == other.style;
	}

	@Override
	public String toString() {
		return "MandelbrotColorGrading [style=" + style + ", colorsTotal=" + colorsTotal + "]";
	}

}
