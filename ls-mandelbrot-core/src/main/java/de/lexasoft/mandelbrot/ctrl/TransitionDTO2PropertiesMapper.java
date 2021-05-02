/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.List;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author admin
 *
 */
public class TransitionDTO2PropertiesMapper extends AbstractDTO2PropertiesMapper {

	private TransitionDTO2PropertiesMapper(CalculationPropertiesDTO propsDTO) {
		super(propsDTO);
	}

	@Override
	protected void mapFollowingCalculations(List<TransitionPropertiesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps) {
		// TODO Auto-generated method stub

	}

	public static TransitionDTO2PropertiesMapper of(CalculationPropertiesDTO propsDTO) {
		return new TransitionDTO2PropertiesMapper(propsDTO);
	}
}
