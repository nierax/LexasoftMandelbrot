/**
 * 
 */
package de.lexasoft.mandelbrot;

/**
 * Represents a position of a point in the MandelbrotIterator calculation.
 * 
 * @author nierax
 *
 */
public class MandelbrotPointPosition {
	/**
	 * x position
	 */
	private double cx;
	/**
	 * y position
	 */
	private double cy;

	public MandelbrotPointPosition() {
		cx = Double.NaN;
		cy = Double.NaN;
	}

	/**
	 * Constructor to create new position.
	 * 
	 * @param cx
	 * @param cy
	 */
	MandelbrotPointPosition(double cx, double cy) {
		super();
		this.cx = cx;
		this.cy = cy;
	}

	/**
	 * Fluent api for position.
	 * 
	 * @param cx
	 * @param cy
	 * @return New object of {@link MandelbrotPointPosition}
	 */
	public static final MandelbrotPointPosition of(double cx, double cy) {
		return new MandelbrotPointPosition(cx, cy);
	}

	public double cx() {
		return cx;
	}

	public double setCx(double cx) {
		this.cx = cx;
		return cx;
	}

	public double movex(double delta) {
		this.cx += delta;
		return this.cx;
	}

	public double cy() {
		return cy;
	}

	public double setCy(double cy) {
		this.cy = cy;
		return cy;
	}

	public double movey(double delta) {
		this.cy += delta;
		return this.cy;
	}

}
