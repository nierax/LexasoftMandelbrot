/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.math.BigDecimal;

/**
 * Represents a position of a point in the Mandelbrot calculation.
 * 
 * @author nierax
 *
 */
public class MandelbrotPointPosition {
	/**
	 * x position
	 */
	private BigDecimal cx;
	/**
	 * y position
	 */
	private BigDecimal cy;

	public MandelbrotPointPosition() {
		this(BigDecimal.ZERO, BigDecimal.ZERO);
	}

	/**
	 * Constructor to create new position.
	 * 
	 * @param cx
	 * @param cy
	 */
	MandelbrotPointPosition(BigDecimal cx, BigDecimal cy) {
		this.cx = cx;
		this.cy = cy;
	}

	/**
	 * Fluent api for Mandelbrot point position.
	 * 
	 * @param cx
	 * @param cy
	 * @return New object of {@link MandelbrotPointPosition}
	 */
	public static final MandelbrotPointPosition of(BigDecimal cx, BigDecimal cy) {
		return new MandelbrotPointPosition(cx, cy);
	}

	/**
	 * Create from another Mandelbrot point position
	 * 
	 * @param other The other position
	 * @return New object of {@link MandelbrotPointPosition}
	 */
	public static final MandelbrotPointPosition of(MandelbrotPointPosition other) {
		return of(other.cx, other.cy);
	}

	/**
	 * Create from double values.
	 * 
	 * @param cx
	 * @param cy
	 * @return New object of {@link MandelbrotPointPosition}
	 */
	public static final MandelbrotPointPosition of(double cx, double cy) {
		BigDecimal bdcx = Double.isNaN(cx) ? BigDecimal.ZERO : BigDecimal.valueOf(cx);
		BigDecimal bdcy = Double.isNaN(cy) ? BigDecimal.ZERO : BigDecimal.valueOf(cy);
		return of(bdcx, bdcy);
	}

	public BigDecimal cx() {
		return cx;
	}

	public BigDecimal setCx(BigDecimal cx) {
		this.cx = cx;
		return cx;
	}

	public BigDecimal movex(BigDecimal delta) {
		this.cx = cx.add(delta);
		return this.cx;
	}

	public BigDecimal cy() {
		return cy;
	}

	/**
	 * This method is needed to fulfill java bean convention.
	 * <p>
	 * Prefer to use {@link MandelbrotPointPosition#cx()}
	 * 
	 * @return the cx
	 */
	@Deprecated
	public BigDecimal getCx() {
		return cx();
	}

	/**
	 * This method is needed to fulfill java bean convention.
	 * <p>
	 * Prefer to use {@link MandelbrotPointPosition#cy()}
	 * 
	 * @return the cy
	 */
	@Deprecated
	public BigDecimal getCy() {
		return cy();
	}

	public BigDecimal setCy(BigDecimal cy) {
		this.cy = cy;
		return cy;
	}

	public BigDecimal movey(BigDecimal delta) {
		this.cy = cy.add(delta);
		return this.cy;
	}

	/**
	 * Moves the point to the given new position.
	 * 
	 * @param cx X coordinate of the new position
	 * @param cy Y coordinate of the new position
	 * @return Instance of this object.
	 */
	public MandelbrotPointPosition moveTo(BigDecimal cx, BigDecimal cy) {
		setCx(cx);
		setCy(cy);
		return this;
	}

	/**
	 * Moves the point by the values in delta.
	 * 
	 * @param delta The difference, the point has to be moved.
	 * @return Reference to this object.
	 */
	public MandelbrotPointPosition move(MandelbrotPointPosition delta) {
		movex(delta.cx());
		movey(delta.cy());
		return this;
	}

	/**
	 * Subtract the given value (subtrahend) from this object (minuend).
	 * 
	 * @param subtrahend The value, that will be subtracted from this object.
	 * @return The difference between this object and @subtrahend in a new object
	 */
	public MandelbrotPointPosition subtract(MandelbrotPointPosition subtrahend) {
		BigDecimal deltax = cx.subtract(subtrahend.cx());
		BigDecimal deltay = cy.subtract(subtrahend.cy());
		return MandelbrotPointPosition.of(deltax, deltay);
	}

	/**
	 * 
	 * @return True, if cx is set, false otherwise.
	 */
	public boolean isCxSet() {
		return !cx.equals(BigDecimal.ZERO);
	}

	/**
	 * 
	 * @return True, if cy is set, false otherwise.
	 */
	public boolean isCySet() {
		return !cy.equals(BigDecimal.ZERO);
	}

	public BigDecimal unsetCx() {
		return setCx(BigDecimal.ZERO);
	}

	public BigDecimal unsetCy() {
		return setCy(BigDecimal.ZERO);
	}

	/**
	 * Equality means, that the point positions are numerically identical.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MandelbrotPointPosition)) {
			return false;
		}
		MandelbrotPointPosition other = (MandelbrotPointPosition) obj;
		return ((other.cx().compareTo(cx) == 0) && (other.cy().compareTo(cy) == 0));
	}

	@Override
	public String toString() {
		return "[cx=" + cx + ", cy=" + cy + "]";
	}

}
