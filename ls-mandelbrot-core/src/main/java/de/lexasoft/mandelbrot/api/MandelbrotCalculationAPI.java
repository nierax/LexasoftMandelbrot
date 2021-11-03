/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.image.BufferedImage;

import de.lexasoft.common.model.Result;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotColorizeBuilder;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.cu.MandelbrotIteratorBuilder;
import de.lexasoft.util.TimeMeasureSupport;

/**
 * Facade to the calculation
 * <p>
 * Returns the result of the calculation in a {@link BufferedImage} object for
 * further use. Does not persist the image in any way, neither on disk or in
 * memory.
 * 
 * @author nierax
 *
 */
public class MandelbrotCalculationAPI {

	/**
	 * Calls the calculation of the Mandelbrot set.
	 * 
	 * @param model
	 * @return
	 */
	public final Result<MandelbrotImage> calculate(MandelbrotCalculationProperties model) {
		// Create colorize method
		MandelbrotColorize colorize = MandelbrotColorizeBuilder.of() //
		    .withPalette(model.getPaletteVariant()) //
		    .withColors(model.getCustomColorPalette()) //
		    .withGrading(model.getColorGrading()) //
		    .withMandelbrotColor(model.getMandelbrotColor()) //
		    .build();

		// Measure time the calculation run
		TimeMeasureSupport<Result<MandelbrotImage>> time = TimeMeasureSupport.of();

		// Start calculation
		Result<MandelbrotImage> image = time.runProcess(() -> MandelbrotIteratorBuilder.of() //
		    .withColorize(colorize) //
		    .withCalculationArea(model.getCalculation()) //
		    .withMaxIterations(model.getMaximumIterations()) //
		    .withImageArea(model.getImage()) //
		    .calculate());

		InfoCallbackAPI.of().outCalculationReady(time.getTimeElapsed());
		return image;
	}
}
