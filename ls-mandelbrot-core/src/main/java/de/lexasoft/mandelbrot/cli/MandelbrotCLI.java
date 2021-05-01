/**
 * 
 */
package de.lexasoft.mandelbrot.cli;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.MandelbrotRunnerException;
import de.lexasoft.mandelbrot.ctrl.CalculationPropertiesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;

/**
 * CLI application for MandelbrotIterator calculation.
 * <p>
 * Call with a yaml file, containing the attributes for a MandelbrotIterator
 * calculation
 * 
 * @author nierax
 *
 */
public class MandelbrotCLI {

	private void doRun(String yamlFilename)
	    throws JsonParseException, JsonMappingException, IOException, MandelbrotRunnerException {
		System.out.println("Starting to calculate...");
		CalculationPropertiesDTO propDTO = CalculationPropertiesDTO.of(yamlFilename);
		MandelbrotController.of(propDTO).flowCalculation();
		System.out.println("Done.");
	}

	/**
	 * Main method for the CLI application.
	 * 
	 * @param args Array of arguments. The first argument (args[0]) is used to read
	 *             the file name to a yaml file, containing the calculation
	 *             properties.
	 */
	public static void main(String[] args) {
		MandelbrotCLI cli = new MandelbrotCLI();
		if ((args == null) || args.length < 1) {
			System.err.println("File name to yaml file must be given.");
			System.exit(99);
		}
		try {
			cli.doRun(args[0]);
			System.exit(0);
		} catch (IOException e) {
			System.err.println("Unknown IO error occured. Check console for details. (" + e.getLocalizedMessage() + ")");
			e.printStackTrace();
		} catch (MandelbrotRunnerException e) {
			System.err.println("Could not run the calculation. Check console for details. (" + e.getLocalizedMessage() + ")");
			e.printStackTrace();
		}
		System.exit(99);
	}

}
