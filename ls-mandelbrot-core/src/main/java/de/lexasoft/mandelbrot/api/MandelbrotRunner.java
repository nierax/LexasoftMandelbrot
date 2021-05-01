package de.lexasoft.mandelbrot.api;

import de.lexasoft.mandelbrot.MandelbrotRunnerException;

public interface MandelbrotRunner {

	/**
	 * Runs a MandelbrotIterator calculation with the values, given during instantiation.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	void run() throws MandelbrotRunnerException;

}