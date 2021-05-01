/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mandelbrot.MandelbrotRunnerException;

/**
 * Implementation of {@link MandelbrotRunner}, which runs a number of runner
 * objects.
 * 
 * @author nierax
 *
 */
public class MandelbrotRunnerChain implements MandelbrotRunner {

	private List<MandelbrotRunner> runners;

	/**
	 * Creates a new runner chain.
	 */
	public MandelbrotRunnerChain() {
		super();
		runners = new ArrayList<>();
	}

	/**
	 * Runs all runners in the chain.
	 */
	@Override
	public void run() throws MandelbrotRunnerException {
		for (MandelbrotRunner runner : runners) {
			runner.run();
		}
	}

	/**
	 * Adds a runner to the chain.
	 * 
	 * @param runner Runner to add
	 */
	public void addRunner(MandelbrotRunner runner) {
		runners.add(runner);
	}

	/**
	 * 
	 * @return The chain of runners in this object.
	 */
	List<MandelbrotRunner> runners() {
		return runners;
	}

}
