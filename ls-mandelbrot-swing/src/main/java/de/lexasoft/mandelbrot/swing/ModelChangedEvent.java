/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

/**
 * This event is fired, when a change in the model appears.
 * 
 * @author nierax
 *
 */
public class ModelChangedEvent<T> {

	private ModelChangingController source;
	private T model;

	/**
	 * Create a new model changed event.
	 * 
	 * @param source
	 * @param model
	 */
	public ModelChangedEvent(ModelChangingController source, T model) {
		this.source = source;
		this.model = model;
	}

	/**
	 * @return the source
	 */
	public ModelChangingController getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(ModelChangingController source) {
		this.source = source;
	}

	/**
	 * @return the model
	 */
	public T getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(T model) {
		this.model = model;
	}

}
