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
package de.lexasoft.mandelbrot.fx;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import javafx.embed.swing.SwingFXUtils;

/**
 * This controller does the calculation of the Mandelbrot image.
 * 
 * @author nierax
 *
 */
public class CalculationController {

	private MandelbrotAttributesDTO model;

	private ImageOut imageOut;

	/**
	 * 
	 */
	public CalculationController(ImageOut imageOut) {
		this.model = initModel();
		this.imageOut = imageOut;
	}

	private MandelbrotAttributesDTO initModel() {
		MandelbrotAttributesDTO model = MandelbrotAttributesDTO.ofDefaults();
		model.getImage().setAspectRatioHandle(AspectRatioHandle.FITIN);
		model.getImage().setImageHeight(0);
		model.getImage().setImageWidth(0);
		return model;
	}

	public void calculate() {
		if (model.getImage().isImageDimensionSet()) {
			imageOut.outImage(SwingFXUtils.toFXImage(//
			    MandelbrotController.of().executeSingleCalculation(model).getImage(), //
			    null));
		}
	}

	MandelbrotAttributesDTO model() {
		return this.model;
	}

	public CalculationController newWidth(int newWidth) {
		model.getImage().setImageWidth(newWidth);
		calculate();
		return this;
	}

	public CalculationController newHeight(int newHeight) {
		model.getImage().setImageHeight(newHeight);
		calculate();
		return this;
	}

}
