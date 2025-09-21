/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.MathContext;

import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * This representation is working more exactly by using BigDeccimal instead of
 * double for calculation.
 * <p>
 * Thus it is much slower but with very large zoom results are much more
 * precise.
 */
public class MandelbrotIteratorExact implements MandelbrotIterator {

	private final MandelbrotColorize colorize;

	private final CalcPrecision precision;

	/**
	 * Create with {@link CalcPrecision#EXACT} and {@link MandelbrotBlackWhite}.
	 */
	MandelbrotIteratorExact() {
		this(CalcPrecision.EXACT, new MandelbrotBlackWhite());
	}

	/**
	 * Create with the given precision and colorize method.
	 * 
	 * @param precision The required precision
	 * @param colorize  The colorize method to be used.
	 */
	MandelbrotIteratorExact(CalcPrecision precision, MandelbrotColorize colorize) {
		this.precision = precision;
		this.colorize = colorize;
	}

	private MathContext prec() {
		return precision.precision();
	}

	/**
	 * Based on https://www.k-achilles.de/algorithmen/apfelmaennchen.pdf
	 * 
	 * Implementation of the Mandelbrot calculation with the type bIgDecimal. More
	 * exact but slower.
	 * 
	 * @param calculation The calculation area.
	 * @param maxIt       Number of maximum iterations.
	 * @param imageDim    The dimensions of the image to draw.
	 * @return The image with the graphics written in.
	 */
	@Override
	public MandelbrotImage drawMandelbrot(CalculationArea calculation, int maxIt, ImageArea imageDim) {
		BigDecimal xstart = calculation.topLeft().cx();
		BigDecimal xend = calculation.bottomRight().cx();
		BigDecimal ystart = calculation.topLeft().cy();
		BigDecimal yend = calculation.bottomRight().cy();
		int imageWidth = imageDim.width();
		int imageHeight = imageDim.height();
		MandelbrotImage image = MandelbrotImage.of(imageDim, calculation);

		// Steps for calculations are orientated on the width / height of the image
		// double dx = (xend - xstart) / (imageWidth - 1);
		BigDecimal dx = (xend.subtract(xstart, prec())).divide(BigDecimal.valueOf(imageWidth - 1), prec());
		// double dy = (yend - ystart) / (imageHeight - 1);
		BigDecimal dy = (yend.subtract(ystart, prec())).divide(BigDecimal.valueOf(imageHeight - 1), prec());

		MandelbrotFormulaExact point = new MandelbrotFormulaExact(this.precision);

		// Start position
		BigDecimal cpx = xstart;
		BigDecimal cpy = ystart;
		for (int column = 0; column < imageWidth; column++) {
			cpy = ystart;
			for (int line = 0; line < imageHeight; line++) {
				int iterate = point.iterate(cpx, cpy, maxIt);
				Point iPoint = new Point();
				iPoint.x = column;
				iPoint.y = line;
				image.colorizePoint(iPoint, colorize.getColorForIteration(iterate, maxIt));
				cpy = cpy.add(dy, prec());
			}
			cpx = cpx.add(dx, prec());
		}
		return image;
	}

}
