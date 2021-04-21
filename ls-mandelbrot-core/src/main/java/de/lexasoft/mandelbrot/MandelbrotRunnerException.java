/**
 * 
 */
package de.lexasoft.mandelbrot;

/**
 * @author admin
 *
 */
@SuppressWarnings("serial")
public class MandelbrotRunnerException extends Exception {

	/**
	 * @param message
	 * @param cause
	 */
	public MandelbrotRunnerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public MandelbrotRunnerException(Throwable cause) {
		super(cause);
	}

}
