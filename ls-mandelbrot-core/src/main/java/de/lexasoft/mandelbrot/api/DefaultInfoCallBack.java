/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * This default implementation of {@link InfoCallbackAPI} writes information to
 * the standard output.
 * 
 * @author nierax
 */
public class DefaultInfoCallBack implements InfoCallbackAPI {

	@Override
	public void outFileWritten(String filenameWritten2) {
		System.out.println("Image file written to: " + filenameWritten2);
	}

	@Override
	public void outCalculationReady(long timeUsed) {
		System.out.println(String.format("Calculation ready. Time used: %s ms.", timeUsed));
	}

}
