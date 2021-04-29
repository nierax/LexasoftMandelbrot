package de.lexasoft.mandelbrot;

public interface MandelbrotRunner {

	/**
	 * Runs a Mandelbrot calculation with the values, given during instantiation.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	void run() throws MandelbrotRunnerException;

}