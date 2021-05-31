package de.lexasoft.mandelbrot.api;

import java.util.List;

import de.lexasoft.mandelbrot.MandelbrotRunnerChain;
import de.lexasoft.mandelbrot.MandelbrotSingleRunner;

public interface MandelbrotRunner {

	/**
	 * Runs a MandelbrotIterator calculation with the values, given during
	 * instantiation.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	void run() throws MandelbrotRunnerException;

	/**
	 * Creates a runner for single calculation
	 * 
	 * @param props The calculation properties for this runner
	 * @return
	 */
	public static MandelbrotRunner of(MandelbrotCalculationProperties props) {
		return MandelbrotSingleRunner.of(props);
	}

	/**
	 * Creates a runner for multiple calculations.
	 * <p>
	 * If the list contains just one entry, a single runner is used.
	 * 
	 * @param listOfProps The list of calculation properties for this runner
	 * @return
	 */
	public static MandelbrotRunner of(List<MandelbrotCalculationProperties> listOfProps) {
		if (listOfProps.size() == 1) {
			return of(listOfProps.get(0));
		}
		MandelbrotRunnerChain chain = new MandelbrotRunnerChain();
		for (MandelbrotCalculationProperties props : listOfProps) {
			chain.addRunner(of(props));
		}
		return chain;
	}

}