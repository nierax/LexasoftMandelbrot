/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.io.IOException;
import java.util.List;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationAPI;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.cli.MandelbrotCLI;

/**
 * Implements the control flow from the input to the core api.
 * 
 * @author nierax
 *
 */
public class MandelbrotController {

	private MandelbrotCalculationAPI api;

	/**
	 * Constructor is package protected to avoid instantiation from public classes
	 * from other packages.
	 */
	MandelbrotController() {
		super();
		api = new MandelbrotCalculationAPI();
	}

	/**
	 * Executes a calculation with potentially more than one calculation in the
	 * mutliCalc parameter.
	 * <p>
	 * The calculation needs a file name with every calculation, to save the image
	 * directly after calculation. If at least one filename is missing in the list,
	 * a {@link MandelbrotControllerException} is thrown.
	 * <p>
	 * This method is potentially useful in batch scenarios, where several
	 * calculations are made and the results are written in image files. This is
	 * used in {@link MandelbrotCLI} application, for instance.
	 * 
	 * @param multiCalc The calculation to do, potentially with more than one
	 *                  calculation via the following attribute.
	 */
	public void executeMultiCalculation(MandelbrotAttributesDTO multiCalc) {
		List<MandelbrotCalculationProperties> props = AbstractDTO2PropertiesMapper.of(multiCalc).mapDTO2Properties();
		// Check, whether all calculations have a file name given.
		// We do this before the calculation to save time.
		props.stream().forEach(p -> {
			if (p.getImageFilename() == null || "".equals(p.getImageFilename())) {
				throw new MandelbrotControllerException("In multi mode a file name must be given for each calculation.");
			}
		});
		// Now step through all calculations
		props.stream().forEach(p -> {
			try {
				api.calculate(p).writeToFile(p.getImageFilename());
			} catch (IOException e) {
				throw new MandelbrotControllerException("Error occured writing the image file", e);
			}
		});
	}

	/**
	 * Executes a calculation with exactly one calculation in the base parameters.
	 * <p>
	 * The "following" attribute is expected to be empty, as there is no method of
	 * handling the image known at this point. In case this attribute is not null, a
	 * {@link MandelbrotControllerException} is thrown.
	 * 
	 * @param singleCalc
	 * @return
	 */
	public MandelbrotImage executeSingleCalculation(MandelbrotAttributesDTO singleCalc) {
		if (singleCalc.getFollowing() != null) {
			throw new MandelbrotControllerException(
			    "Method MandelbrotController::executeSingleCalculation can handle single calculation, only. Try executeMultiCalculation instead.");
		}
		MandelbrotCalculationProperties props = AbstractDTO2PropertiesMapper.of(singleCalc).mapDTO2Properties().get(0);
		return api.calculate(props);
	}

	/**
	 * Factory method for controller.
	 * 
	 * @param propsDTO The supplied properties of the calculation(s) to run.
	 * @return New instance of a{@link MandelbrotController}
	 */
	public static MandelbrotController of() {
		return new MandelbrotController();
	}
}
