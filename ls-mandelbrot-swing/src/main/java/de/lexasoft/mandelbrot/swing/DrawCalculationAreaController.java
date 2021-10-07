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

import java.awt.Rectangle;

import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;

/**
 * The {@link DrawCalculationAreaController} draws the rectangle, which marks
 * the calculation area over the image.
 * 
 * @see DrawCalculationAreaPanel
 * @author nierax
 *
 */
public class DrawCalculationAreaController implements ShowCalculationArea {

	private DrawCalculationAreaPanel view;

	/**
	 * 
	 */
	public DrawCalculationAreaController(DrawCalculationAreaPanel view) {
		this.view = view;
	}

	/**
	 * Private method to do the calculation.
	 * <p>
	 * Could be refactored to the controller api in the future, if necessary.
	 * 
	 * @param model The model to calculate from
	 * @return The borders of the calculation area in a rectangle object.
	 */
	private Rectangle calculateArea(CalculationAreaControllerModel model) {
		Rectangle rect = createRectangle();
		double adoptWidth = model.adoptBottomRight().cx() - model.adoptTopLeft().cx();
		double adoptHeight = model.adoptTopLeft().cy() - model.adoptBottomRight().cy();
		double calcWidth = model.calcBottomRight().cx() - model.calcTopLeft().cx();
		double calcHeight = model.calcTopLeft().cy() - model.calcBottomRight().cy();

		int rectWidth = (int) Math.round(model.imageWidth() - ((adoptWidth - calcWidth) * model.imageWidth() / adoptWidth));
		int calcImageX0 = (model.imageWidth() - rectWidth) / 2;

		int rectHeight = (int) Math
		    .round(model.imageHeight() - ((adoptHeight - calcHeight) * model.imageHeight() / adoptHeight));
		int calcImageY0 = (model.imageHeight() - rectHeight) / 2;

		rect.setBounds(calcImageX0, calcImageY0, rectWidth, rectHeight);
		return rect;
	}

	/**
	 * To make this class easier testable.
	 * 
	 * @return
	 */
	Rectangle createRectangle() {
		return new Rectangle();
	}

	/**
	 * Use this method to draw the calculation area into the image.
	 * <p>
	 * Should be called after every calculation, use of a model changed event
	 * recommended.
	 * 
	 * @param model The model to calculate the borders of the calculation area.
	 */
	public void calculationAreaModelChanged(CalculationAreaControllerModel model) {
		view.drawRect(calculateArea(model));
	}

	@Override
	public void show(boolean flag) {
		this.view.setVisible(flag);
	}

}
