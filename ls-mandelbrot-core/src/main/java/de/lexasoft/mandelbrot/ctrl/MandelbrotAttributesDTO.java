/**
 * 
 */
package de.lexasoft.mandelbrot.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature;

/**
 * Keeps the structure of objects used to calculate a Mandelbrot image.
 * 
 * @author nierax
 *
 */
public class MandelbrotAttributesDTO {

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
		return of(new File(yamlFilename));
	}

	/**
	 * Reads the given yaml file and fills the attribute DTOs.
	 * 
	 * @param yamlFile The yaml file (should exist in the file system)
	 * @return The attribute DTOs with the read in values.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static MandelbrotAttributesDTO of(File yamlFile) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		MandelbrotAttributesDTO props = mapper.readValue(yamlFile, MandelbrotAttributesDTO.class);
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

	public void writeToYamlFile(String yamlFileName) throws JsonGenerationException, JsonMappingException, IOException {
		writeToYamlFile(new File(yamlFileName));
	}

	public void writeToYamlFile(File yamlFile) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(Feature.WRITE_DOC_START_MARKER));
		mapper.findAndRegisterModules();
		mapper.writeValue(yamlFile, this);
	}

	/**
	 * Instantiates Mandelbrot attributes with the default values.
	 * <p>
	 * Especially useful for testing purposes, but can also be used in production.
	 * 
	 * @return Mandelbrot default attributes.
	 */
	public final static MandelbrotAttributesDTO ofDefaults() {
		MandelbrotAttributesDTO attribs = new MandelbrotAttributesDTO();
		attribs.setCalculation(CalculationAttributesDTO.ofDefault());
		attribs.setImage(ImageAttributesDTO.ofDefault());
		attribs.setColor(ColorAttributesDTO.ofDefault());
		return attribs;
	}

}
