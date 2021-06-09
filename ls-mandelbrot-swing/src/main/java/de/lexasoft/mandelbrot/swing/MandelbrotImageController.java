/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

/**
 * @author nierax
 *
 */
public class MandelbrotImageController implements ModelChangedListener<MandelbrotCalculationProperties> {

	private MandelbrotCanvas view;

	public MandelbrotImageController(MandelbrotCanvas view) {
		this.view = view;
	}

	public void initView() {

	}

	@Override
	public void modelChanged(ModelChangedEvent<MandelbrotCalculationProperties> event) {
		view.modelChanged();
	}

}
