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

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * @author nierax
 *
 */
public class CalculationDialog extends AnchorPane {

	private CalculationDialogController controller;

	/**
	 *  
	 */
	public CalculationDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("calculation-control.fxml"));
			// Creating a new controller
			controller = new CalculationDialogController();
			// Using the controller (can not be done via fxml in custom controls)
			loader.setController(controller);
			// Load from fxml
			Node n = loader.load();
			// Add the controller to the pane
			this.getChildren().add(n);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param children
	 */
	public CalculationDialog(Node... children) {
		super(children);
	}

}
