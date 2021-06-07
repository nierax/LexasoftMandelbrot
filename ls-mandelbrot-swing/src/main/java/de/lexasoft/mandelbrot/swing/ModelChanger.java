/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

/**
 * @author nierax
 *
 */
public interface ModelChanger<T> {

	void sendModelChanged(ModelChangedEvent<T> event);
}
