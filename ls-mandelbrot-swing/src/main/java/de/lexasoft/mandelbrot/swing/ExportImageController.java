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

import de.lexasoft.mandelbrot.swing.model.ImageControllerModel;

/**
 * @author nierax
 *
 */
public class ExportImageController implements ImageControllerModel {

	private ExportImagePanel view;

	private int imageWidth;
	private int imageHeight;
	private String imageFilename;

	/**
	 * 
	 */
	public ExportImageController(ExportImagePanel view) {
	}

	public void initController() {
		view.getImageWidth().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				imageWidth = Integer.parseInt(view.getImageWidth().getText());
			}
		});
		view.getImageHeight().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				imageHeight = Integer.parseInt(view.getImageHeight().getText());
			}
		});
		view.getImageFilename().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				imageFilename = view.getImageFilename().getText();
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
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

}
