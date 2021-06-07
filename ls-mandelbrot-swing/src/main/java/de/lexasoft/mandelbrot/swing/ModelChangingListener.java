/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

/**
 * @author nierax
 *
 */
public interface ModelChangingListener<T> {

	void modelChanged(ModelChangedEvent<T> event);
}
