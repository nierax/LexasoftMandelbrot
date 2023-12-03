/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Represents one point to be investigated in an exact calculated manner.
 * <p>
 * Based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
 * 
 * @author nierax
 *
 */
public class MandelbrotFormulaExact {

  private static final BigDecimal TWO = BigDecimal.valueOf((int) 2);

  private static final MathContext PREC = CalculationVersion.EXACT.precision();

  private BigDecimal quadrat(BigDecimal base) {
    return base.multiply(base, PREC);
  }

  private boolean hasToContinue(BigDecimal zx, BigDecimal zy, int counter, int maxIt) {
    int compare = quadrat(zx).add(quadrat(zy), PREC).compareTo(BigDecimal.valueOf(4d));
    return compare <= 0 && counter < maxIt;
  }

  /**
   * Calculate a single point in the Mandelbrot set.
   * 
   * @param cx
   * @param cy
   * @param maxIt
   * @return
   */
  public int iterate(BigDecimal cx, BigDecimal cy, int maxIt) {
    int counter = 0;
    BigDecimal zx = BigDecimal.valueOf(0.0), zy = BigDecimal.valueOf(0.0), tmp;
    do {
      // tmp = zx * zx - zy * zy + cx;
      tmp = quadrat(zx).subtract(quadrat(zy), PREC).add(cx, PREC);
      // zy = 2 * zx * zy + cy;
      zy = TWO.multiply((zx.multiply(zy, PREC)), PREC).add(cy, PREC);
      zx = tmp;
      counter = counter + 1;
    } while (hasToContinue(zx, zy, counter, maxIt));
    return counter;
  }

}
