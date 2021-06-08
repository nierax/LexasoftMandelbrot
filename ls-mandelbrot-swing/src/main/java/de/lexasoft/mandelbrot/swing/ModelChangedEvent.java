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

	private ModelChanger<T> source;
	private T model;

	/**
	 * Create a new model changed event.
	 * 
	 * @param source
	 * @param model
	 */
	public ModelChangedEvent(ModelChanger<T> source, T model) {
		this.source = source;
		this.model = model;
	}

	/**
	 * @return the source
	 */
	public ModelChanger<T> getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(ModelChanger<T> source) {
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
