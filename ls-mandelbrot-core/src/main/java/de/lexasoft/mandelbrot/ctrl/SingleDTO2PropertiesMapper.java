/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.List;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * Maps from the properties DTO to the properties.
 * 
 * @author nierax
 *
 */
public class SingleDTO2PropertiesMapper extends AbstractDTO2PropertiesMapper {

	private SingleDTO2PropertiesMapper(CalculationPropertiesDTO propsDTO) {
		super(propsDTO);
	}

	@Override
	protected void mapFollowingCalculations(List<TransitionPropertiesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps) {
		// Nothing to do here
	}

	public static SingleDTO2PropertiesMapper of(CalculationPropertiesDTO propsDTO) {
		return new SingleDTO2PropertiesMapper(propsDTO);
	}
}
