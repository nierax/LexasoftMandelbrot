package de.lexasoft.mandelbrot.cu;

import java.math.MathContext;

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

  public MathContext precision() {
    return precision;
  }

}
