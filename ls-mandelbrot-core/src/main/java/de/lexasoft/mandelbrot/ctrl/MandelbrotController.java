/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import de.lexasoft.mandelbrot.api.MandelbrotRunner;
import de.lexasoft.mandelbrot.api.MandelbrotRunnerException;

/**
 * Implements the control flow from the input to the runner.
 * 
 * @author nierax
 *
 */
public class MandelbrotController {

	private CalculationPropertiesDTO propDTO;

	public MandelbrotController(CalculationPropertiesDTO propDTO) {
		this.propDTO = propDTO;
	}

	public void flowCalculation() throws MandelbrotRunnerException {
		MandelbrotRunner.of(AbstractDTO2PropertiesMapper.of(propDTO).mapDTO2Properties()).run();
	}

	/**
	 * Factory method for controller.
	 * 
	 * @param propsDTO The supplied properties of the calculation(s) to run.
	 * @return New instance of a{@link MandelbrotController}
	 */
	public static MandelbrotController of(CalculationPropertiesDTO propsDTO) {
		return new MandelbrotController(propsDTO);
	}
}
