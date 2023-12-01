package de.lexasoft.mandelbrot.cu;

import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;

/**
 * This interface represents an iterator for calculation of the Mandelbrot set.
 * 
 * @author nierax
 */
public interface MandelbrotIterator {

  /**
   * This method is designed to calculate the Mandelbrot set into an Image.
   *
   * @param calculation The calculation area.
   * @param maxIt       Number of maximum iterations.
   * @param imageDim    The dimensions of the image to draw.
   * @return The image with the graphics written in.
   */
  MandelbrotImage drawMandelbrot(CalculationArea calculation, int maxIt, ImageArea imageDim);

  /**
   * Create MandelbrotIterator with the given calculation version and the colorize
   * strategy.
   * 
   * @param version  Calculation version to be used. FAST vs. EXACT
   * @param colorize The colorize strategy to use.
   * @return Newly created MandelbrotIterator object.
   */
  public static MandelbrotIterator of(CalculationVersion version, MandelbrotColorize colorize) {
    switch (version) {
    case EXACT: {
      return new MandelbrotIteratorExact(colorize);
    }
    default:
      return new MandelbrotIteratorFast(colorize);
    }

  }

}
