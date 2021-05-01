/**
 * 
 */
package de.lexasoft.mandelbrot.api;

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
	 * Creates a runner for multiple calculations.
	 * <p>
	 * If the list contains just one entry, a single runner is used.
	 * 
	 * @param props The list of calculation properties for this runner
	 * @return
	 */
	public MandelbrotRunner createRunner(List<MandelbrotCalculationProperties> listOfProps) {
		if (listOfProps.size() == 1) {
			// Just a single runner needed.
			return createRunner(listOfProps.get(0));
		}
		MandelbrotRunnerChain chain = new MandelbrotRunnerChain();
		for (MandelbrotCalculationProperties props : listOfProps) {
			chain.addRunner(createRunner(props));
		}
		return chain;
	}

	public static MandelbrotRunnerFactory of() {
		return new MandelbrotRunnerFactory();
	}
}
