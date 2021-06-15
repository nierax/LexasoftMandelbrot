/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * Keeps the structure of objects used to calculate a Mandelbrot image.
 * 
 * @author nierax
 *
 */
public class MandelbrotAttributesDTO implements NormalizeDTO {

	@JsonProperty
	private CalculationAttributesDTO calculation;
	@JsonProperty
	private ColorAttributesDTO color;
	@JsonProperty
	private List<TransitionAttributesDTO> following;
	@JsonProperty
	private ImageAttributesDTO image;

	/**
	 * Reads the given yaml file and fills the attribute DTOs.
	 * 
	 * @param yamlFilename The name of the yaml file (should exist in the file
	 *                     system)
	 * @return The attribute DTOs with the read in values.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static MandelbrotAttributesDTO of(String yamlFilename)
	    throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		MandelbrotAttributesDTO props = mapper.readValue(new File(yamlFilename), MandelbrotAttributesDTO.class);
		return props;
	}

	/**
	 * @return the calculation
	 */
	public CalculationAttributesDTO getCalculation() {
		return calculation;
	}

	/**
	 * @param calculation the calculation to set
	 */
	public void setCalculation(CalculationAttributesDTO calculation) {
		this.calculation = calculation;
	}

	/**
	 * @return the color
	 */
	public ColorAttributesDTO getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(ColorAttributesDTO color) {
		this.color = color;
	}

	/**
	 * @return the following
	 */
	public List<TransitionAttributesDTO> getFollowing() {
		return following;
	}

	/**
	 * @return the image
	 */
	public ImageAttributesDTO getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(ImageAttributesDTO image) {
		this.image = image;
	}

	@Override
	public void normalize() {
		if (image != null) {
			image.normalize();
		}
	}

}
