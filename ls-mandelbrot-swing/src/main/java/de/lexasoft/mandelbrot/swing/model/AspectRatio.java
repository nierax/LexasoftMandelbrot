/**
 * 
 */
package de.lexasoft.mandelbrot.swing.model;

/**
 * Define several aspect ratios for the image to be shown
 * 
 * @author nierax
 *
 */
public enum AspectRatio {

	/**
	 * Don't care about an aspect ratio
	 */
	IGNORE(Double.NaN, "Ignore"),

	/**
	 * Use the full area to display the image.
	 */
	FILL(0d, "Fill"),

	/**
	 * Quadratic
	 */
	AR1x1(1d, "1:1"),

	/**
	 * 3 to 2
	 */
	AR3x2(3d / 2d, "3:2"),

	/**
	 * 4 to 3
	 */
	AR4x3(4d / 3d, "4:3"),

	/**
	 * 16 to 10
	 */
	AR16x10(16d / 10d, "16:10"),

	/**
	 * 16 to 9
	 */
	AR16x9(16d / 9d, "16:9");

	private String outText;

	/**
	 * @return the ratioX2Y
	 */
	public Double getRatioX2Y() {
		return ratioX2Y;
	}

	@Override
	public String toString() {
		return outText;
	}

	private Double ratioX2Y;

	private AspectRatio(Double ratioX2Y, String outText) {
		this.ratioX2Y = ratioX2Y;
		this.outText = outText;
	}

}
