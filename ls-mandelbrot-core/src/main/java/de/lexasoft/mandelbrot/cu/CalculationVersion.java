package de.lexasoft.mandelbrot.cu;

import java.math.MathContext;

/**
 * Signals the precision, the calculation is done with.
 * <p>
 * FAST: Fast, but less exact
 * <p>
 * EXACT: Highest precision, but slower.
 */
public enum CalculationVersion {

  /**
   * Fast but less exact calculation
   */
  FAST(null),

  /**
   * Exact but slower calculation
   */
  EXACT(MathContext.DECIMAL128);

  private MathContext precision;

  private CalculationVersion(MathContext precision) {
    this.precision = precision;
  }

  /**
   * 
   * @return The mathematic context for the calculation with BigDecimal. Used
   *         for @EXACT only.
   */
  public MathContext precision() {
    return precision;
  }

}
