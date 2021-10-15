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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;

/**
 * Controller to handle the mouse wheel action.
 * <p>
 * Mouse wheel is used to zoom in or out the calculation.
 * 
 * @author nierax
 *
 */
public class ZoomController extends ModelChangingController<CalculationArea> implements MouseWheelListener {

	private JPanel view;
	private CalculationAreaControllerModel model;

	/**
	 * The view is given with the constructor. It is used to register this
	 * controller as a mouse wheel listener to it.
	 * <p>
	 * The internal model is null, as it is not known in the phase of initializing
	 * this component. To get the model, the @ZoomController must be
	 * registered with the @CalculationAreaControllerModel change event, which is
	 * done in @MandelbrotUIController
	 * 
	 * @param view
	 */
	public ZoomController(JPanel view) {
		this.view = view;
		this.model = null;
	}

	/**
	 * Implementation of the init controller phase.
	 */
	public void initController() {
		this.view.addMouseWheelListener(this);
	}

	private double zoomFactor(MouseWheelEvent e) {
		double delta = (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) ? 0.1d : 0.5d;
		return 1 + (e.getWheelRotation() * delta / 3d);
	}

	/**
	 * Connected to the mouse wheel event.
	 * <p>
	 * Calculates the boundaries by zooming in or out and sends
	 * a @ModelChangedEvent.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (model != null) {
			CalculationArea calculation = model.calculation();
			MandelbrotPointPosition point = calculation.calculatePointFromImagePosition(model.image(), e.getPoint());
			fireModelChangedEvent(new ModelChangedEvent<CalculationArea>(this, calculation.zoom(zoomFactor(e), point)));
		}
	}

	void modelChanged(CalculationAreaControllerModel model) {
		this.model = model;
	}

}
