/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

/**
 * Marks AttributeDTOs, which support a normalize function to adjust parameters
 * after reading from yaml or being set via the set methods.
 * 
 * @author nierax
 *
 */
public interface NormalizeDTO {

	/**
	 * This method is called to normalize the attributes in the DTO by any method,
	 * known to the DTO.
	 */
	void normalize();
}
