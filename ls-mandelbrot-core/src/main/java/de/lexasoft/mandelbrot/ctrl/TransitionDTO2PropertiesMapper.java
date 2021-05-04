/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.List;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
public class TransitionDTO2PropertiesMapper extends AbstractDTO2PropertiesMapper {

	private TransitionDTO2PropertiesMapper(CalculationPropertiesDTO propsDTO) {
		super(propsDTO);
	}

	private void calculateTransition(MandelbrotCalculationProperties start, MandelbrotCalculationProperties end,
	    List<MandelbrotCalculationProperties> listOfProps) {

	}

	@Override
	protected void mapFollowingCalculations(List<TransitionPropertiesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps) {
		MandelbrotCalculationProperties baseProps = listOfProps.get(0);
		for (TransitionPropertiesDTO calc : followingDTO) {

			listOfProps.add(mapSingleCalculation(calc, baseProps.cloneValues()));
		}
	}

	public static TransitionDTO2PropertiesMapper of(CalculationPropertiesDTO propsDTO) {
		return new TransitionDTO2PropertiesMapper(propsDTO);
	}
}
