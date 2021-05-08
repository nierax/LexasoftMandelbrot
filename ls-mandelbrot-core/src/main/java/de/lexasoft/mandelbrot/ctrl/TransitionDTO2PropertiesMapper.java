/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.List;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.Transition;
import de.lexasoft.mandelbrot.api.TransitionFactory;

/**
 * @author nierax
 *
 */
public class TransitionDTO2PropertiesMapper extends AbstractDTO2PropertiesMapper {

	private TransitionFactory transitionFactory;

	private TransitionDTO2PropertiesMapper(CalculationPropertiesDTO propsDTO) {
		super(propsDTO);
		transitionFactory = new TransitionFactory();
	}

	private int determineNrOfDigits(int total) {
		int nrOfDigits = 2;
		int i = 10;
		while (total > i) {
			i = i * 10;
			nrOfDigits++;
		}
		return nrOfDigits;
	}

	@Override
	protected void mapFollowingCalculations(List<TransitionPropertiesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps) {
		MandelbrotCalculationProperties baseProps = listOfProps.get(0);
		MandelbrotCalculationProperties start = baseProps;
		List<MandelbrotCalculationProperties> listOfTransitions;
		for (TransitionPropertiesDTO calc : followingDTO) {
			Transition transition = Transition.of(calc.getTransition().steps(), calc.getTransition().variant());
			MandelbrotCalculationProperties next = mapSingleCalculation(calc, baseProps.cloneValues());
			listOfTransitions = transitionFactory.createTransitions(start, next, transition);
			listOfProps.addAll(listOfTransitions);
			listOfProps.add(next);
			start = next;
		}
		int size = listOfProps.size();
		for (int i = 0; i < size; i++) {
			listOfProps.get(i).addIndex2ImageFilename(determineNrOfDigits(size), i + 1);
		}
	}

	public static TransitionDTO2PropertiesMapper of(CalculationPropertiesDTO propsDTO) {
		return new TransitionDTO2PropertiesMapper(propsDTO);
	}
}
