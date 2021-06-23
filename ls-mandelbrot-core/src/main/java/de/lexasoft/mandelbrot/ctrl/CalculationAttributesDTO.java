/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * All attributes, used to define the calculation attributes, such as the area
 * of complex numbers and the maximum numbers of iterations.
 * 
 * @author nierax
 *
 */
public class CalculationAttributesDTO {

	@JsonProperty
	private PointDTO topLeft;
	@JsonProperty
	private PointDTO bottomRight;
	@JsonProperty
	private int maximumIterations;

	/**
	 * @return the topLeft
	 */
	public PointDTO getTopLeft() {
		return topLeft;
	}

	/**
	 * @param topLeft the topLeft to set
	 */
	public void setTopLeft(PointDTO cx) {
		this.topLeft = cx;
	}

	/**
	 * @return the bottomRight
	 */
	public PointDTO getBottomRight() {
		return bottomRight;
	}

	/**
	 * @param bottomRight the bottomRight to set
	 */
	public void setBottomRight(PointDTO cy) {
		this.bottomRight = cy;
	}

	/**
	 * @return the maximumIterations
	 */
	public int getMaximumIterations() {
		return maximumIterations;
	}

	/**
	 * @param maximumIterations the maximumIterations to set
	 */
	public void setMaximumIterations(int maximumIterations) {
		this.maximumIterations = maximumIterations;
	}

	/**
	 * Create {@link CalculationAttributesDTO} with default values.
	 * 
	 * @return
	 */
	public static final CalculationAttributesDTO ofDefault() {
		CalculationAttributesDTO calc = new CalculationAttributesDTO();
		calc.topLeft = PointDTO.of("-2.02d", "1.2d");
		calc.bottomRight = PointDTO.of("0.8d", "-1.2d");
		calc.maximumIterations = 25;
		return calc;
	}
}
