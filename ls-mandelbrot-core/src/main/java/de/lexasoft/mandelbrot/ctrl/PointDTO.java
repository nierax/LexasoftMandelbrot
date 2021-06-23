/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.util.Objects;

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

	public final static PointDTO of(String cx, String cy) {
		PointDTO point = new PointDTO();
		point.cx = cx;
		point.cy = cy;
		return point;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cx, cy);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointDTO other = (PointDTO) obj;
		return Objects.equals(cx, other.cx) && Objects.equals(cy, other.cy);
	}

	@Override
	public String toString() {
		return "PointDTO [cx=" + cx + ", cy=" + cy + "]";
	}

}
