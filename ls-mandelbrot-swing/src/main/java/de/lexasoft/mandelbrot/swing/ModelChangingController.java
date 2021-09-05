/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for controller, which have the ability to change the underlying
 * model.
 * 
 * @author nierax
 *
 */
public class ModelChangingController<T> implements ModelChanger<T> {

	private List<ModelChangedListener<T>> queue = new ArrayList<>();

	/**
	 * Add a listener to the queue of listeners.
	 * <p>
	 * The listeners will be called FIFO.
	 * 
	 * @param listener The listener to add.
	 */
	public void addModelChangedListener(ModelChangedListener<T> listener) {
		queue.add(listener);
	}

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
	@Override
	public void fireModelChangedEvent(ModelChangedEvent<T> event) {
		for (ModelChangedListener<T> modelChangedListener : queue) {
			modelChangedListener.modelChanged(event);
		}
	}

	/**
	 * @return the queue
	 */
	List<ModelChangedListener<T>> getQueue() {
		return queue;
	}
}
