/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author nierax
 *
 */
class MandelbrotFormulaFastTest {

  private MandelbrotFormulaFast cut;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    cut = new MandelbrotFormulaFast();
  }

  private static Stream<Arguments> testIterate() {
    return Stream.of(//
        Arguments.of(0.0, 0.0, 500, 500), //
        Arguments.of(0.75, -0.75, 500, 2), //
        Arguments.of(0.0, 1.2, 500, 3), //
        Arguments.of(0.0, 1.1, 500, 4), //
        Arguments.of(0.5, -0.5, 500, 5), //
        Arguments.of(-1.99624454148471615720, 8.2E-19, 5, 5));
  }

  /**
   * Test method for
   * {@link de.lexasoft.mandelbrot.cu.MandelbrotFormulaFast#iterate(double, double, int)}.
   * <p>
   * Checks some known points of the Mandelbrot set to evaluate that the method
   * works correctly.
   */
  @ParameterizedTest
  @MethodSource
  void testIterate(double cx, double cy, int maxIt, int expected) {
    int counter = cut.iterate(cx, cy, maxIt);
    assertEquals(expected, counter);
  }

}
