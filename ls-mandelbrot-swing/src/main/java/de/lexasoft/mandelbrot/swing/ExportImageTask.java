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

import javax.swing.SwingWorker;

import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;

/**
 * @author nierax
 * @param <V>
 * @param <T>
 *
 */
public class ExportImageTask extends SwingWorker<Void, Void> {

	interface WorkerDone {
		void done();
	}

	private MandelbrotAttributesDTO model;
	private WorkerDone done;

	/**
	 * @param model
	 */
	private ExportImageTask(MandelbrotAttributesDTO model, WorkerDone done) {
		this.model = model;
		this.done = done;
	}

	@Override
	protected Void doInBackground() throws Exception {
		MandelbrotController.of().executeMultiCalculation(model);
		return null;
	}

	public static ExportImageTask of(MandelbrotAttributesDTO model, WorkerDone done) {
		return new ExportImageTask(model, done);
	}

	@Override
	protected void done() {
		done.done();
	}

}
