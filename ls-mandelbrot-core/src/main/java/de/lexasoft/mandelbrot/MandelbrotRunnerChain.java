/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 *
 */
public class MandelbrotRunnerChain implements MandelbrotRunner {

	private List<MandelbrotRunner> runners;

	/**
	 * 
	 */
	public MandelbrotRunnerChain() {
		super();
		runners = new ArrayList<>();
	}

	@Override
	public void run() throws MandelbrotRunnerException {
		for (MandelbrotRunner runner : runners) {
			runner.run();
		}
	}

	public void addRunner(MandelbrotRunner runner) {
		runners.add(runner);
	}

	List<MandelbrotRunner> runners() {
		return runners;
	}

}
