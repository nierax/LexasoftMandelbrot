/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.cu.CalcPrecision;

/**
 * All attributes, used to define the calculation attributes, such as the area
 * of complex numbers and the maximum numbers of iterations.
 * 
 * @author nierax
 *
 */
public class CalculationAttributesDTO {

	@JsonSerialize
	private MandelbrotPointPosition topLeft;
	@JsonSerialize
	private MandelbrotPointPosition bottomRight;
	@JsonProperty
	private int maximumIterations;
	@JsonProperty
	private CalcPrecision calcPrecision;

	/**
	 * @return the topLeft
	 */
	public MandelbrotPointPosition getTopLeft() {
		return topLeft;
	}

	/**
	 * @param topLeft the topLeft to set
	 */
	public void setTopLeft(MandelbrotPointPosition cx) {
		this.topLeft = cx;
	}

	/**
	 * @return the bottomRight
	 */
	public MandelbrotPointPosition getBottomRight() {
		return bottomRight;
	}

	/**
	 * @param bottomRight the bottomRight to set
	 */
	public void setBottomRight(MandelbrotPointPosition cy) {
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
	 * @return the calcPrecision
	 */
	public CalcPrecision getCalcPrecision() {
		return calcPrecision;
	}

	/**
	 * @param calcPrecision the calcPrecision to set
	 */
	public void setCalcPrecision(CalcPrecision calcPrecision) {
		this.calcPrecision = calcPrecision;
	}

	/**
	 * Create {@link CalculationAttributesDTO} with default values.
	 * 
	 * @return
	 */
	public static final CalculationAttributesDTO ofDefault() {
		CalculationAttributesDTO calc = new CalculationAttributesDTO();
		calc.topLeft = MandelbrotPointPosition.of(-2.02d, 1.2d);
		calc.bottomRight = MandelbrotPointPosition.of(0.8d, -1.2d);
		calc.maximumIterations = 25;
		calc.calcPrecision = CalcPrecision.FAST;
		return calc;
	}

}
