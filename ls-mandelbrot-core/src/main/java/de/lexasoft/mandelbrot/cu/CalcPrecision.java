package de.lexasoft.mandelbrot.cu;

import java.math.MathContext;

/**
 * Signals the precision, the calculation is done with.
 * <p>
 * From {@link FAST} (fastest but the most impprecise) to {@link #EXACT} (very
 * slow, but very precise).
 */
public enum CalcPrecision {

	/**
	 * Fast but less exact calculation.
	 * <p>
	 * Done with double type and the known limitations of precision.
	 */
	FAST(null),

	/**
	 * Low precision, but much more the {@link #FAST}
	 * <p>
	 * Uses BigDecimal with @MathContext#DECIMAL32
	 */
	LOW(MathContext.DECIMAL32),

	/**
	 * Compromise between speed and precision.
	 * <p>
	 * Uses BigDecimal with @MathContext#DECIMAL64
	 */
	MIDDLE(MathContext.DECIMAL64),

	/**
	 * Exact but slow calculation
	 * <p>
	 * Uses BigDecimal with @MathContext#DECIMAL128
	 */
	EXACT(MathContext.DECIMAL128);

	private MathContext precision;

	private CalcPrecision(MathContext precision) {
		this.precision = precision;
	}

	/**
	 * 
	 * @return The mathematical context for the calculation with BigDecimal. Not
	 *         used for {@link #FAST}.
	 */
	public MathContext precision() {
		return precision;
	}

}
