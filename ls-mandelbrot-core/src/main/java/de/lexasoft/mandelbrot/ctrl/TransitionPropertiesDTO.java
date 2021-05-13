/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.lexasoft.mandelbrot.api.TransitionVariant;

/**
 * @author nierax
 *
 */
public class TransitionPropertiesDTO extends CalculationPropertiesDTO {

	class TransitionDTO {
		public TransitionDTO() {
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
	private TransitionDTO transition;

	public TransitionDTO getTransition() {
		return transition;
	}
}
