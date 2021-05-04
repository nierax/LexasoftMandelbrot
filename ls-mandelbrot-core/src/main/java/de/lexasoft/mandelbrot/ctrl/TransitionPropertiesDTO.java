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
		public Transition() {
			super();
		}

		@JsonProperty
		private int steps;
		@JsonProperty
		private TransitionVariant variant;

		public int steps() {
			return this.steps;
		}

		public TransitionVariant variant() {
			return this.variant;
		}
	}

	@JsonProperty
	private Transition transition;

	public Transition getTransition() {
		return transition;
	}
}
