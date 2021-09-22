/**
 * Copyright (C) 2021 nierax
 * This program is free software; you can redistribute it and/or modify it under the terms of 
 * the GNU General Public License as published by the Free Software Foundation; either version 3 
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; 
 * if not, see <http://www.gnu.org/licenses/>. 
 */
package de.lexasoft.mandelbrot.swing;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import de.lexasoft.mandelbrot.swing.model.APIModelFactory;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;
import de.lexasoft.mandelbrot.swing.model.ImageControllerModel;

/**
 * This adapter converts the controller model to the api model and starts the
 * calculation.
 * <p>
 * The class works state less, which means, that all values must be given via
 * the api call.
 * 
 * @author nierax
 *
 */
public class RunCalculationAdapter {

	/**
	 * Don't create from outside this class
	 */
	private RunCalculationAdapter() {
	}

	/**
	 * Calculate the Mandelbrot set from the given controller model.
	 * 
	 * @param calcModel  The calculation controller model
	 * @param colorModel The color controller model
	 * @param imgModel   the image controller model
	 * @return {@link MandelbrotImage} with the calculation result.
	 */
	public MandelbrotImage calculate(CalculationControllerModel calcModel, ColorControllerModel colorModel,
	    ImageControllerModel imgModel) {
		MandelbrotAttributesDTO model = APIModelFactory.of().createFromCM(calcModel, colorModel, imgModel);
		return MandelbrotController.of().executeSingleCalculation(model);
	}

	/**
	 * Create the run calculation adapter.
	 * 
	 * @return New calculation adapter.
	 */
	public static RunCalculationAdapter of() {
		return new RunCalculationAdapter();
	}

}
