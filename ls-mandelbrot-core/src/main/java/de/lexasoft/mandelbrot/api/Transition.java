/**
 * 
 */
package de.lexasoft.mandelbrot.api;

/**
 * @author admin
 *
 */
public class Transition {

	private int steps;
	private TransitionVariant variant;

	private Transition(int steps, TransitionVariant variant) {
		this.steps = steps;
		this.variant = variant;
	}

	public int steps() {
		return this.steps;
	}

	public TransitionVariant variant() {
		return this.variant;
	}

	public static Transition of(int steps, TransitionVariant variant) {
		return new Transition(steps, variant);
	}

}
