/**
 * 
 */
package de.lexasoft.mandelbrot.swing.model;

import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * This interface represents the model of the CalculationController.
 * 
 * @author nierax
 *
 */
public interface CalculationControllerModel extends ControllerModel {

	/**
	 * 
	 * @return The complex number, defining the top left corner.
	 */
	MandelbrotPointPosition topLeft();

	/**
	 * 
	 * @return The complex number, defining the bottom right corner.
	 */
	MandelbrotPointPosition bottomRight();

	/**
	 * 
	 * @return The maximum number of iterations calculated, to assign a number to
	 *         the Mandelbrot set.
	 */
	int maximumIterations();
}
