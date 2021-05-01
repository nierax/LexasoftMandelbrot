/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nierax
 *
 */
public class ColorDTO {

	@JsonProperty
	private int red;
	@JsonProperty
	private int green;
	@JsonProperty
	private int blue;

	public Color getColor() {
		return new Color(red, green, blue);
	}
}