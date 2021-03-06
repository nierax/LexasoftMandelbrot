/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.List;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.Transition;
import de.lexasoft.mandelbrot.api.TransitionFactory;

/**
 * Extends the {@link AbstractDTO2PropertiesMapper} to handle the list of
 * following calculations in case of transitions.
 * <p>
 * By use of the factory method
 * {@link AbstractDTO2PropertiesMapper#of(CalculationPropertiesDTO)} it is
 * guaranteed, that this implementation is used, when at least one transition is
 * in the list, only
 * 
 * @author nierax
 *
 */
public class TransitionDTO2PropertiesMapper extends AbstractDTO2PropertiesMapper {

	private TransitionDTO2PropertiesMapper(MandelbrotAttributesDTO propsDTO) {
		super(propsDTO);
	}

	/**
	 * Calculate the number of leading 0 for the filename.
	 * 
	 * @param nrOfCalculations Number of calculations in total.
	 * @return Number of leading 0, required.
	 */
	private int determineNrOfDigits(int nrOfCalculations) {
		int nrOfDigits = 2;
		int i = 100;
		while (nrOfCalculations >= i) {
			i = i * 10;
			nrOfDigits++;
		}
		return nrOfDigits;
	}

	/**
	 * Handles the list of following calculations in case of transitions.
	 * <p>
	 * Calculates the transitions in the given number of steps and adds them to the
	 * list of calculation properties.
	 * <p>
	 * Changes the file names in the list by adding an index number.
	 * 
	 * @param followingDTO List of following calculations
	 * @param listOfProps  List of properties, containing the base entry.
	 */
	@Override
	protected void mapFollowingCalculations(List<TransitionAttributesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps) {
		MandelbrotCalculationProperties start = listOfProps.get(0);
		for (TransitionAttributesDTO calc : followingDTO) {
			// Figure transition parameters
			Transition transition = Transition.of(calc.getTransition().steps(), calc.getTransition().variant());
			TransitionFactory transitionFactory = TransitionFactory.of(transition);
			// Map the next entry (end point of the transition)
			MandelbrotCalculationProperties next = mapSingleCalculation(calc, start.cloneValues());
			// Calculate transitions and add them to the list
			listOfProps.addAll(transitionFactory.createTransitions(start, next));
			// Add the end point of the transition
			listOfProps.add(next);
			// End point is the start point of the next transition
			start = next;
		}
		// Add indices to the file names
		int size = listOfProps.size();
		for (int i = 0; i < size; i++) {
			listOfProps.get(i).addIndex2ImageFilename(determineNrOfDigits(size), i + 1);
		}
	}

	public static TransitionDTO2PropertiesMapper of(MandelbrotAttributesDTO propsDTO) {
		return new TransitionDTO2PropertiesMapper(propsDTO);
	}
}
