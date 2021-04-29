/**
 * 
 */
package de.lexasoft.mandelbrot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author admin
 *
 */
public class PointDTO {

	public PointDTO() {
		super();
	}

	@JsonProperty
	private String cx;
	@JsonProperty
	private String cy;

	public String getCx() {
		return cx;
	}

	public String getCy() {
		return cy;
	}

}
