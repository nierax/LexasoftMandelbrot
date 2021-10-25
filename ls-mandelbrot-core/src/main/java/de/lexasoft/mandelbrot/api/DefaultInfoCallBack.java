/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This default implementation of {@link InfoCallbackAPI} writes information to
 * the standard output.
 * 
 * @author nierax
 */
public class DefaultInfoCallBack implements InfoCallbackAPI {

	private static Logger LOGGER = LoggerFactory.getLogger(DefaultInfoCallBack.class);

	@Override
	public void outFileWritten(String filenameWritten2) {
		System.out.println("Image file written to: " + filenameWritten2);
	}

	@Override
	public void outCalculationReady(long timeUsed) {
		LOGGER.info((String.format("Calculation ready. Time used: %s ms.", timeUsed)));
	}

}
