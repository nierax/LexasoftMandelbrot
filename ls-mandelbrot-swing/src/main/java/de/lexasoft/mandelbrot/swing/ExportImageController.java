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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.swing.model.APIModelFactory;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;
import de.lexasoft.mandelbrot.swing.model.ImageControllerModel;

/**
 * Controller for exporting a calculation into an image file.
 * <p>
 * Uses the @ExportImageDialog to ask for image width and height and file to
 * save to.
 * 
 * @author nierax
 *
 */
public class ExportImageController implements ImageControllerModel {

	private ExportImageDialog view;

	private int imageWidth;
	private int imageHeight;
	private String imageFilename;
	private MandelbrotAttributesDTO model;
	private double aspectRatio;

	/**
	 * 
	 */
	public ExportImageController(JFrame parent) {
		this.view = createDialog(parent);
		this.aspectRatio = Double.NaN;
	}

	ExportImageDialog createDialog(JFrame parent) {
		return new ExportImageDialog(parent);
	}

	public void initController() {
		view.getPanel().getImageWidth().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				imageWidthChanged(Integer.parseInt(view.getPanel().getImageWidth().getText()));
			}
		});
		view.getPanel().getImageHeight().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				imageHeightChanged(Integer.parseInt(view.getPanel().getImageHeight().getText()));
			}
		});
		view.getPanel().getImageFilename().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				imageFilename = view.getPanel().getImageFilename().getText();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}

	private void ensureAspectRatioSet() {
		if (Double.isNaN(aspectRatio)) {
			aspectRatio = (double) imageWidth() / imageHeight();
		}
	}

	private void resetTextFields() {
		this.view.getPanel().getImageWidth().setText(Integer.toString(this.imageWidth));
		this.view.getPanel().getImageHeight().setText(Integer.toString(this.imageHeight));
//		this.view.getPanel().getImageWidth().repaint();
//		this.view.getPanel().getImageHeight().repaint();
	}

	void imageWidthChanged(int imageWidth) {
		ensureAspectRatioSet();
		this.imageWidth = imageWidth;
		this.imageHeight = (int) Math.round(this.imageWidth / aspectRatio);
		resetTextFields();
	}

	void imageHeightChanged(int imageHeight) {
		ensureAspectRatioSet();
		this.imageHeight = imageHeight;
		this.imageWidth = (int) Math.round(this.imageHeight * aspectRatio);
		resetTextFields();
	}

	@Override
	public int imageWidth() {
		return imageWidth;
	}

	@Override
	public int imageHeight() {
		return imageHeight;
	}

	@Override
	public String imageFilename() {
		return imageFilename;
	}

	/**
	 * Prepares the calculation by translating the model into the api model and
	 * popping up the export dialog.
	 * <p>
	 * Corrects the image sizes, that they fit to the aspect ratio.
	 * 
	 * @param calcModel
	 * @param colModel
	 * @param imgModel
	 */
	public void exportImageFor(CalculationControllerModel calcModel, ColorControllerModel colModel,
	    ImageControllerModel imgModel) {
		model = APIModelFactory.of().createFromCM(calcModel, colModel, imgModel);
		imageWidth = model.getImage().getImageWidth();
		imageHeight = model.getImage().getImageHeight();
		imageFilename = model.getImage().getImageFilename();
		aspectRatio = (double) imageWidth() / imageHeight();

		view.getPanel().getImageWidth().setText(Integer.toString(imageWidth));
		view.getPanel().getImageHeight().setText(Integer.toString(imageHeight));
		view.popupDialog();
	}
}
