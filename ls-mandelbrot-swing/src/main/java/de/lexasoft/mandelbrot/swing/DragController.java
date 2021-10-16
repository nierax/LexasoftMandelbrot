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

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;

/**
 * Controls the drag of the image with the mouse.
 * 
 * @author nierax
 *
 */
public class DragController extends ModelChangingController<CalculationArea> {

	private JPanel view;
	private DragListener dragListener;
	private CalculationAreaControllerModel model;

	/**
	 * @param view
	 */
	public DragController(JPanel view) {
		this.view = view;
		this.dragListener = new DragListener();
	}

	class DragListener extends MouseInputAdapter {
		private int x;
		private int y;

		@Override
		public void mousePressed(MouseEvent e) {
			this.x = e.getX();
			this.y = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
//			super.mouseReleased(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int x1 = e.getX();
			int y1 = e.getY();
			dragCalculation(new Point(x, y), new Point(x1, y1));
			this.x = x1;
			this.y = y1;
		}

		/**
		 * @return the x
		 */
		int getX() {
			return x;
		}

		/**
		 * @return the y
		 */
		int getY() {
			return y;
		}

	}

	public void initController() {
		view.addMouseMotionListener(dragListener);
		view.addMouseListener(dragListener);
	}

	/**
	 * Does the dragging of the calculation area.
	 * 
	 * @param from
	 * @param to
	 */
	private void dragCalculation(Point from, Point to) {
		if (model != null) {
			MandelbrotPointPosition cFrom = model.calculation().calculatePointFromImagePosition(model.image(), from);
			MandelbrotPointPosition cTo = model.calculation().calculatePointFromImagePosition(model.image(), to);
			MandelbrotPointPosition diff = cFrom.subtract(cTo);
			model.calculation().move(diff);
			fireModelChangedEvent(new ModelChangedEvent<CalculationArea>(this, model.calculation()));
		}
	}

	/**
	 * @return the dragListener
	 */
	DragListener getDragListener() {
		return dragListener;
	}

	/**
	 * Must be informed about changes to the @CalculationAreaControllerModel. This
	 * is implemented in {@link MandelbrotUIController}
	 * 
	 * @param model
	 */
	void modelChanged(CalculationAreaControllerModel model) {
		this.model = model;
	}

	void resetModel() {
		this.model = null;
	}

}
