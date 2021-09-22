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
package de.lexasoft.mandelbrot.swing.model;

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.ctrl.CalculationAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.ColorAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.ColorDTO;
import de.lexasoft.mandelbrot.ctrl.ImageAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;

/**
 * This factory creates an api model from the controller model.
 * 
 * @author nierax
 *
 */
public class APIModelFactory {

	public final static APIModelFactory of() {
		return new APIModelFactory();
	}

	private CalculationAttributesDTO createCalculationModel(CalculationControllerModel calcCM) {
		CalculationAttributesDTO calcModel = new CalculationAttributesDTO();
		calcModel.setTopLeft(MandelbrotPointPosition.of(calcCM.topLeft()));
		calcModel.setBottomRight(MandelbrotPointPosition.of(calcCM.bottomRight()));
		calcModel.setMaximumIterations(calcCM.maximumIterations());
		return calcModel;
	}

	private ColorAttributesDTO createColorModel(ColorControllerModel colCM) {
		ColorAttributesDTO colModel = new ColorAttributesDTO();
		colModel.setPaletteVariant(colCM.paletteVariant());
		colModel.setColorGrading(MandelbrotColorGrading.of(colCM.gradingStyle(), colCM.totalNrOfColors()));
		colModel.setMandelbrotColor(ColorDTO.of(0, 0, 0));
		return colModel;
	}

	private ImageAttributesDTO handleAspectRatio(AspectRatio ar, ImageControllerModel imageCM) {
		ImageAttributesDTO model = new ImageAttributesDTO();
		int width = imageCM.imageWidth();
		int height = imageCM.imageHeight();
		switch (ar) {
		case IGNORE:
			model.setImageWidth(width);
			model.setImageHeight(height);
			model.setAspectRatioHandle(AspectRatioHandle.IGNORE);
			return model;
		case FILL:
			// Use height and width as calculated above
			break;
		default:
			double ard = ar.getRatioX2Y();
			if (ard > (width / height)) {
				height = (int) Math.round(width / ar.getRatioX2Y());
			} else {
				width = (int) Math.round(height * ar.getRatioX2Y());
			}
			break;
		}
		model.setImageWidth(width);
		model.setImageHeight(height);
		model.setAspectRatioHandle(AspectRatioHandle.FITIN);
		return model;
	}

	private ImageAttributesDTO createImageModel(AspectRatio ar, ImageControllerModel imgCM) {
		ImageAttributesDTO imgModel = handleAspectRatio(ar, imgCM);
		imgModel.setImageFilename(imgCM.imageFilename());
		return imgModel;
	}

	/**
	 * Map to the API model from the controller model.
	 * <p>
	 * All objects are cloned in order to decouple the source from the destination.
	 * <p>
	 * This method translates the aspect ratio controller model to the one of the
	 * api model. It also corrects the image width and height, if necessary.
	 * 
	 * @param calcModel The calculation controller model.
	 * @param colModel  The color controller model.
	 * @param imModel   the image controller model.
	 * @return A new instance of the api model.
	 */
	public MandelbrotAttributesDTO createFromCM(CalculationControllerModel calcModel, ColorControllerModel colModel,
	    ImageControllerModel imModel) {
		MandelbrotAttributesDTO model = new MandelbrotAttributesDTO();
		model.setCalculation(createCalculationModel(calcModel));
		model.setColor(createColorModel(colModel));
		model.setImage(createImageModel(calcModel.aspectRatio(), imModel));
		return model;
	}
}
