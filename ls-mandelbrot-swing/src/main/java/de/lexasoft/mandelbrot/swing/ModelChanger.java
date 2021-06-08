/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

/**
 * The interface of the object, performing the change of the model.
 * 
 * @author nierax
 *
 */
public interface ModelChanger<T> {

	/**
	 * This method fires the event to all listeners in its queue.
	 * <p>
	 * It is not recommended to call this method elsewhere than from the extending
	 * class.
	 * <p>
	 * It should be called once, when all changes are made, not with every change.
	 * 
	 * @param event The event to transport.
	 */
	void fireModelChangedEvent(ModelChangedEvent<T> event);
}
