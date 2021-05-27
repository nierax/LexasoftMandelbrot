package de.lexasoft.mandelbrot.api;

public interface MandelbrotRunner {

	/**
	 * Runs a MandelbrotIterator calculation with the values, given during instantiation.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	void run() throws MandelbrotRunnerException;

}