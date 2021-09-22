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
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
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
	private JFrame parent;
	private File file2Exort;

	/**
	 * 
	 */
	public ExportImageController(JFrame parent) {
		this.view = createDialog(parent);
		this.parent = parent;
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
		view.getPanel().getChooseButton().addActionListener((e) -> chooseImageFileToSave());
		view.getPanel().getBtnExportImage().addActionListener((e) -> exportImageFile());
	}

	private void ensureAspectRatioSet() {
		if (Double.isNaN(aspectRatio)) {
			aspectRatio = (double) imageWidth() / imageHeight();
		}
	}

	private void resetTextFields() {
		this.view.getPanel().getImageWidth().setText(Integer.toString(this.imageWidth));
		this.view.getPanel().getImageHeight().setText(Integer.toString(this.imageHeight));
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

	private void enableDisableApproveButton() {
		ExportImagePanel panel = view.getPanel();
		String fileName = panel.getImageFilename().getText();
		panel.getBtnExportImage().setEnabled((fileName != null) && !"".equals(fileName));
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
		return (file2Exort == null) ? "" : file2Exort.getAbsolutePath();
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

	private void setFileToExportTo(File file) {
		this.file2Exort = file;
		this.view.getPanel().getImageFilename().setText(imageFilename());
		enableDisableApproveButton();
	}

	public void chooseImageFileToSave() {
		JFileChooser chooser = FileChooserAction.of().createImageFileChooser("Choose a file to export to");
		FileChooserAction.of().fileSaveAction(chooser, parent, (f) -> setFileToExportTo(f), imageFilename);
	}

	private JDialog createWaitDialog(String fileName) {
		JOptionPane jOptionPane = new JOptionPane("Exporting image to " + fileName + ". \n\rPlease wait...",
		    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
		JDialog dialog = new JDialog(parent);
		dialog.setTitle("Exporting...");
		dialog.setModal(true);
		dialog.setContentPane(jOptionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		return dialog;
	}

	public void exportImageFile() {
		model.getImage().setImageFilename(imageFilename());
		model.getImage().setImageWidth(imageWidth());
		model.getImage().setImageHeight(imageHeight());
		JDialog waitDialog = createWaitDialog(imageFilename());
		ExportImageTask
		    // Execute export and save image file
		    .of(() -> MandelbrotController.of().executeMultiCalculation(model),
		        // Close message dialog when ready
		        () -> waitDialog.setVisible(false))
		    .execute();
		view.closeDialog();
		waitDialog.setVisible(true);
	}
}
