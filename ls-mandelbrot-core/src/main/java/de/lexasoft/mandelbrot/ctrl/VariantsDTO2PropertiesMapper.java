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
public class VariantsDTO2PropertiesMapper extends AbstractDTO2PropertiesMapper {

	private VariantsDTO2PropertiesMapper(MandelbrotAttributesDTO propsDTO) {
		super(propsDTO);
	}

	@Override
	protected void mapFollowingCalculations(List<TransitionAttributesDTO> followingCalcs,
	    List<MandelbrotCalculationProperties> listOfProps) {
		MandelbrotCalculationProperties baseProps = listOfProps.get(0);
		// If there are more calculations given
		if (followingCalcs != null && !followingCalcs.isEmpty()) {
			for (MandelbrotAttributesDTO calc : followingCalcs) {
				listOfProps.add(mapSingleCalculation(calc, baseProps.cloneValues()));
			}
		}
	}

	public static VariantsDTO2PropertiesMapper of(MandelbrotAttributesDTO propsDTO) {
		return new VariantsDTO2PropertiesMapper(propsDTO);
	}
}
