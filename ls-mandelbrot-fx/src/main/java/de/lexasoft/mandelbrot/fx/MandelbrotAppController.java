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

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author nierax
 *
 */
public class MandelbrotAppController implements Initializable {

	@FXML
	private AnchorPane imagePane;

	@FXML
	private ImageView mbImage;

	private CalculationController calculation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.calculation = new CalculationController(mbImage::setImage);
		imagePane.widthProperty().addListener((v, o, c) -> calculation.newWidth(c.intValue()));
		imagePane.heightProperty().addListener((v, o, c) -> calculation.newHeight(c.intValue()));
	}
}
