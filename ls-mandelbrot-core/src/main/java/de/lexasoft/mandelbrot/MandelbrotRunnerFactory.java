/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.util.List;

/**
 * @author nierax
 *
 */
public class MandelbrotRunnerFactory {

	/**
	 * Creates a runner for single calculation
	 * 
	 * @param props The calculation properties for this runner
	 * @return
	 */
	public MandelbrotRunner createRunner(MandelbrotCalculationProperties props) {
		return MandelbrotSingleRunner.of(props);
	}

	/**
	 * Creates a runner for multiple calculations
	 * 
	 * @param props The list of calculation properties for this runner
	 * @return
	 */
	public MandelbrotRunner createRunner(List<MandelbrotCalculationProperties> listOfProps) {
		MandelbrotRunnerChain chain = new MandelbrotRunnerChain();
		for (MandelbrotCalculationProperties props : listOfProps) {
			chain.addRunner(MandelbrotSingleRunner.of(props));
		}
		return chain;
	}
}
