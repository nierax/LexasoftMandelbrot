/**
 * Copyright (C) 2021 admin
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

import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CalculationDialogController implements Initializable {

	@FXML
	private TextField brcx;

	@FXML
	private TextField brcy;

	@FXML
	private CheckBox cbShowCA;

	@FXML
	private ChoiceBox<AspectRatioHandle> choiceAspectRatio;

	@FXML
	private TextField maxIter;

	@FXML
	private TextField tlcx;

	@FXML
	private TextField tlcy;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceAspectRatio.setItems(FXCollections.observableArrayList(AspectRatioHandle.values()));
	}

}
