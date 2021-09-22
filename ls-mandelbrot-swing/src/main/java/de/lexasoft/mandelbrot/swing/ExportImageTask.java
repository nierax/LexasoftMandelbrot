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

/**
 * This task can be used to export an image to a file in a separate thread.
 * <p>
 * It uses the worker pattern from swing to ensure, the long running export task
 * does not freeze the ui.
 * <p>
 * Simply create an object of this task with an execution and a done action.
 * These actions usually are lambda expressions.
 * <p>
 * Then call the execute() method of the worker.
 * 
 * @author nierax
 *
 */
public class ExportImageTask extends SwingWorker<Void, Void> {

	/**
	 * Interface, that represents the execution action
	 *
	 */
	interface WorkerDone {
		void done();
	}

	/**
	 * Interface, that represents the done action
	 *
	 */
	interface WorkerExecute {
		void execute();
	}

	private WorkerDone done;
	private WorkerExecute execute;

	/**
	 * @param model
	 */
	private ExportImageTask(WorkerExecute execute, WorkerDone done) {
		this.done = done;
		this.execute = execute;
	}

	@Override
	protected Void doInBackground() throws Exception {
		execute.execute();
		return null;
	}

	/**
	 * Create a task object with an execution and a done action.
	 * 
	 * @param execute The action with the long running task.
	 * @param done    The action, that should be taken, when the long running task
	 *                ends.
	 * @return New object of export image task.
	 */
	public static ExportImageTask of(WorkerExecute execute, WorkerDone done) {
		return new ExportImageTask(execute, done);
	}

	@Override
	protected void done() {
		done.done();
	}

}
