/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

/**
 * Signalizes an exception in a controller execution.
 * 
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class MandelbrotControllerException extends RuntimeException {

	/**
	 * @param message
	 */
	public MandelbrotControllerException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MandelbrotControllerException(String message, Throwable cause) {
		super(message, cause);
	}

}
