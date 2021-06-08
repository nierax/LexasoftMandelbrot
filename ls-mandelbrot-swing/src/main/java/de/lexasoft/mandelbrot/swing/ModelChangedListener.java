/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

/**
 * Listens to a change of the model.
 * <p>
 * Transports an event with some basic information about the source of the event
 * and the model, affected.
 * <p>
 * See also {@link ModelChangedEvent}
 * 
 * @author nierax
 *
 */
public interface ModelChangedListener<T> {

	/**
	 * Implement, how to react the model change here.
	 * 
	 * @param event
	 */
	void modelChanged(ModelChangedEvent<T> event);
}
