/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore
	public Color getColor() {
		return new Color(red, green, blue);
	}

	public static final ColorDTO of(int red, int green, int blue) {
		ColorDTO color = new ColorDTO();
		color.red = red;
		color.green = green;
		color.blue = blue;
		return color;
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return red;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(int red) {
		this.red = red;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(int green) {
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}
}
