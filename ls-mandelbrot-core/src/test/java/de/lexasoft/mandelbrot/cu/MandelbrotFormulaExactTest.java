/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
class MandelbrotFormulaExactTest {
  
  private MandelbrotFormulaExact cut;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    cut = new MandelbrotFormulaExact();
  }
  
  private static Stream<Arguments> testIterate() {
    return Stream.of(//
        Arguments.of(0.0, 0.0, 500, 500), //
        Arguments.of(0.75, -0.75, 500, 2), //
        Arguments.of(0.0, 1.2, 500, 3), //
        Arguments.of(0.0, 1.1, 500, 4), 
        Arguments.of(0.5, -0.5, 500, 5));
  }

  /**
   * Test method for {@link de.lexasoft.mandelbrot.cu.MandelbrotFormulaExact#iterate(java.math.BigDecimal, java.math.BigDecimal, int)}.
   */
  @ParameterizedTest
  @MethodSource
  void testIterate(double cx, double cy, int maxIt, int expected) {
    int counter = cut.iterate(BigDecimal.valueOf(cx), BigDecimal.valueOf(cy), maxIt);
    assertEquals(expected, counter);
  }

}
