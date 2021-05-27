/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import de.lexasoft.mandelbrot.api.MandelbrotRunner;
import de.lexasoft.mandelbrot.api.MandelbrotRunnerException;
import de.lexasoft.mandelbrot.api.MandelbrotRunnerFactory;

/**
 * Implements the control flow from the input to the runner.
 * 
 * @author nierax
 *
 */
public class MandelbrotController {

	private CalculationPropertiesDTO propDTO;
	private MandelbrotRunnerFactory runnerFactory;

	public MandelbrotController(CalculationPropertiesDTO propDTO) {
		this.propDTO = propDTO;
		this.runnerFactory = MandelbrotRunnerFactory.of();
	}

	public void flowCalculation() throws MandelbrotRunnerException {
		MandelbrotRunner runner = runnerFactory.createRunner(AbstractDTO2PropertiesMapper.of(propDTO).mapDTO2Properties());
		runner.run();
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
