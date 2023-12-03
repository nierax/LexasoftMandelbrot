/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 
 */
class MandelbrotFormulaExactTest {

	private static Stream<Arguments> testIterate() {
		return Stream.of(//
		    Arguments.of(0.0, 0.0, 500, 500, CalcPrecision.EXACT), //
		    Arguments.of(0.75, -0.75, 500, 2, CalcPrecision.EXACT), //
		    Arguments.of(0.0, 1.2, 500, 3, CalcPrecision.EXACT), //
		    Arguments.of(0.0, 1.1, 500, 4, CalcPrecision.EXACT), //
		    Arguments.of(0.5, -0.5, 500, 5, CalcPrecision.EXACT),
		    Arguments.of(-1.99624454148471615720, 8.2E-19, 500, 58, CalcPrecision.EXACT),
		    Arguments.of(-1.384541484716157205240174672489082997, 8.2E-35, 500, 500, CalcPrecision.EXACT),
		    Arguments.of(0.0, 0.0, 500, 500, CalcPrecision.LOW), //
		    Arguments.of(0.75, -0.75, 500, 2, CalcPrecision.LOW), //
		    Arguments.of(0.0, 1.2, 500, 3, CalcPrecision.LOW), //
		    Arguments.of(0.0, 1.1, 500, 4, CalcPrecision.LOW), //
		    Arguments.of(0.5, -0.5, 500, 5, CalcPrecision.LOW),
		    Arguments.of(-1.99624454148471615720, 8.2E-19, 500, 62, CalcPrecision.LOW),
		    Arguments.of(-1.384541484716157205240174672489082997, 8.2E-35, 500, 500, CalcPrecision.LOW),
		    Arguments.of(0.0, 0.0, 500, 500, CalcPrecision.MIDDLE), //
		    Arguments.of(0.75, -0.75, 500, 2, CalcPrecision.MIDDLE), //
		    Arguments.of(0.0, 1.2, 500, 3, CalcPrecision.MIDDLE), //
		    Arguments.of(0.0, 1.1, 500, 4, CalcPrecision.MIDDLE), //
		    Arguments.of(0.5, -0.5, 500, 5, CalcPrecision.MIDDLE),
		    Arguments.of(-1.99624454148471615720, 8.2E-19, 500, 58, CalcPrecision.MIDDLE),
		    Arguments.of(-1.384541484716157205240174672489082997, 8.2E-35, 500, 500, CalcPrecision.MIDDLE));
	}

	/**
	 * Test method for
	 * {@link de.lexasoft.mandelbrot.cu.MandelbrotFormulaExact#iterate(java.math.BigDecimal, java.math.BigDecimal, int)}.
	 */
	@ParameterizedTest
	@MethodSource
	void testIterate(double cx, double cy, int maxIt, int expected, CalcPrecision prec) {
		MandelbrotFormulaExact cut = new MandelbrotFormulaExact(prec);
		int counter = cut.iterate(BigDecimal.valueOf(cx), BigDecimal.valueOf(cy), maxIt);
		assertEquals(expected, counter);
	}

}
