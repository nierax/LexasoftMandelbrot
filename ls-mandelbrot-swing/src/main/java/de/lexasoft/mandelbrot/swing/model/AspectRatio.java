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
	IGNORE(Double.NaN),

	/**
	 * Use the full area to display the image.
	 */
	FILL(0d),

	/**
	 * Quadratic
	 */
	AR1x1(1d),

	/**
	 * 3 to 2
	 */
	AR3x2(3d / 2d),

	/**
	 * 4 to 3
	 */
	AR4x3(4d / 3d),

	/**
	 * 16 to 10
	 */
	AR16x10(16d / 10d),

	/**
	 * 16 to 9
	 */
	AR16x9(16d / 9d);

	/**
	 * @return the ratioX2Y
	 */
	public Double getRatioX2Y() {
		return ratioX2Y;
	}

	private Double ratioX2Y;

	private AspectRatio(Double ratioX2Y) {
		this.ratioX2Y = ratioX2Y;
	}

}
