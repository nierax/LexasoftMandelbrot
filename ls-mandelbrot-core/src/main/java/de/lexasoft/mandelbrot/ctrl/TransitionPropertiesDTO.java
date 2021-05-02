/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nierax
 *
 */
public class TransitionPropertiesDTO extends CalculationPropertiesDTO {

	enum TransitionVariant {
		LINEAR;
	}

	class Transition {
		@JsonProperty
		private int steps;
		@JsonProperty
		private TransitionVariant variant;
	}

	@JsonProperty
	private Transition transition;

	public Transition getTransition() {
		return transition;
	}
}
