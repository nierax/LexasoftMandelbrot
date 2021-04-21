/**
 * 
 */
package de.lexasoft.mandelbrot.cli;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.lexasoft.mandelbrot.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.MandelbrotRunner;
import de.lexasoft.mandelbrot.MandelbrotRunnerException;

/**
 * CLI application for Mandelbrot calculation.
 * <p>
 * Call with a yaml file, containing the attributes for a Mandelbrot calculation
 * 
 * @author nierax
 *
 */
public class MandelbrotCLI {

	private MandelbrotRunner runner;

	private void doRun(String yamlFilename)
	    throws JsonParseException, JsonMappingException, IOException, MandelbrotRunnerException {
		MandelbrotCalculationProperties props = MandelbrotCalculationProperties.of(yamlFilename);
		runner = MandelbrotRunner.of(props);
		System.out.println("Starting to claculate...");
		runner.run();
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
			System.err.println("File name to yaml file mus be given.");
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
