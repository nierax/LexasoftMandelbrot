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

/**
 * The status bar controller, handling the duration of the calculation
 * 
 * @author nierax
 */
public class StatusbarController implements DurationUpdater {

	private StatusbarView view;

	/**
	 * Create the controller for the status bar
	 */
	public StatusbarController(StatusbarView view) {
		this.view = view;
	}

	void initController() {
		this.view.getDuration().setText("-- ms");
	}

	/**
	 * Update the duration in the status bar.
	 */
	@Override
	public void updateDuration(long timeInMillis) {
		String duration = String.format("%s ms", timeInMillis);
		view.getDuration().setText(duration);
	}

}
