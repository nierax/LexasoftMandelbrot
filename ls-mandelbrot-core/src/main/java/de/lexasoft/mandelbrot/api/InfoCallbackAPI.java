/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * @author nierax
 *
 */
public interface InfoCallbackAPI {

	/**
	 * Called from runner when an image has been calculated and written to file
	 * system.
	 * 
	 * @param filenameWritten2
	 */
	void outFileWritten(String filenameWritten2);

	/**
	 * Called from runner, when the calculation is done.
	 * 
	 * @param timeUsed
	 */
	void outCalculationReady(long timeUsed);

	static InfoCallbackAPI of() {
		return new DefaultInfoCallBack();
	}
}
