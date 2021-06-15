/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.lexasoft.mandelbrot.api.TransitionVariant;

/**
 * Contains the attributes to control transitions.
 * 
 * @author nierax
 *
 */
public class TransitionAttributesDTO extends MandelbrotAttributesDTO {

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
