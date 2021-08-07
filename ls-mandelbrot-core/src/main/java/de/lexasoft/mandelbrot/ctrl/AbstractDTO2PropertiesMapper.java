package de.lexasoft.mandelbrot.ctrl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

public abstract class AbstractDTO2PropertiesMapper {

	enum Type {
		SINGLE, VARIANT, TRANSITION
	}

	private MandelbrotAttributesDTO attribsDTO;

	public AbstractDTO2PropertiesMapper(MandelbrotAttributesDTO propsDTO) {
		this.attribsDTO = propsDTO;
	}

	/**
	 * In this method the list of calculations, given in the following parameter
	 * must be mapped and added to the listOfProps.
	 * 
	 * @param dto
	 * @param listOfProps
	 */
	protected abstract void mapFollowingCalculations(List<TransitionAttributesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps);

	private List<Color> mapListOfColorDTO2(List<ColorDTO> listOfColorDTO) {
		List<Color> colors = new ArrayList<>();
		for (ColorDTO colorDTO : listOfColorDTO) {
			colors.add(colorDTO.getColor());
		}
		return colors;
	}

	/**
	 * The method to map from dto to properties.
	 * 
	 * @param dto
	 * @return
	 */
	public List<MandelbrotCalculationProperties> mapDTO2Properties() {
		List<MandelbrotCalculationProperties> listOfProps = new ArrayList<>();
		// Set first calculation directly
		MandelbrotCalculationProperties baseProps = mapSingleCalculation(attribsDTO, MandelbrotCalculationProperties.of());
		listOfProps.add(baseProps);
		mapFollowingCalculations(attribsDTO.getFollowing(), listOfProps);
		// At last: Normalize all properties in list
		listOfProps.stream().forEach((p) -> {
			p.normalize();
		});

		return listOfProps;
	}

	/**
	 * 
	 * Maps a single calculation from DTO to {@link MandelbrotCalculationProperties}
	 * 
	 * @param dto   Calculation input
	 * @param props Calculation properties, where the values are set to.
	 * @return Calculation properties, same object as given
	 */
	protected MandelbrotCalculationProperties mapSingleCalculation(MandelbrotAttributesDTO dto,
	    MandelbrotCalculationProperties props) {
		CalculationAttributesDTO calcDTO = dto.getCalculation();
		if (calcDTO != null) {
			mapCalculation(props, calcDTO);
		}
		ImageAttributesDTO imageDTO = dto.getImage();
		if (imageDTO != null) {
			mapImage(props, imageDTO);
		}
		ColorAttributesDTO colorDTO = dto.getColor();
		if (colorDTO != null) {
			mapColors(props, colorDTO);
		}
		return props;
	}

	/**
	 * @param props
	 * @param colorDTO
	 */
	private void mapColors(MandelbrotCalculationProperties props, ColorAttributesDTO colorDTO) {
		if (colorDTO.getPaletteVariant() != null) {
			props.setPaletteVariant(colorDTO.getPaletteVariant());
		}
		if (colorDTO.getCustomColorPalette() != null) {
			props.setCustomColorPalette(mapListOfColorDTO2(colorDTO.getCustomColorPalette()));
		}
		if (colorDTO.getColorGrading() != null && colorDTO.getColorGrading().getColorsTotal() > 0) {
			props.setColorGrading(colorDTO.getColorGrading());
		}
		if (colorDTO.getMandelbrotColor() != null) {
			props.setMandelbrotColor(colorDTO.getMandelbrotColor().getColor());
		}
	}

	/**
	 * @param props
	 * @param imageDTO
	 */
	private void mapImage(MandelbrotCalculationProperties props, ImageAttributesDTO imageDTO) {
		if (imageDTO.getImageWidth() > 0) {
			props.setImageWidth(imageDTO.getImageWidth());
		}
		if (imageDTO.getImageHeight() > 0) {
			props.setImageHeight(imageDTO.getImageHeight());
		}
		if (imageDTO.getImageFilename() != null && !"".equals(imageDTO.getImageFilename())) {
			props.setImageFilename(imageDTO.getImageFilename());
		}
		if (imageDTO.getAspectRatioHandle() != null) {
			props.setAspectRatio(imageDTO.getAspectRatioHandle());
		}

	}

	/**
	 * @param props
	 * @param calcDTO
	 */
	private void mapCalculation(MandelbrotCalculationProperties props, CalculationAttributesDTO calcDTO) {
		if (calcDTO.getTopLeft() != null) {
			props.setTopLeft(MandelbrotPointPosition.of(calcDTO.getTopLeft()));
		}
		if (calcDTO.getBottomRight() != null) {
			props.setBottomRight(MandelbrotPointPosition.of(calcDTO.getBottomRight()));
		}
		if (calcDTO.getMaximumIterations() > 0) {
			props.setMaximumIterations(calcDTO.getMaximumIterations());
		}
	}

	private static Type determineType(MandelbrotAttributesDTO propsDTO) {
		if ((propsDTO.getFollowing() == null)) {
			return Type.SINGLE;
		}
		if (propsDTO.getFollowing().isEmpty()) {
			return Type.SINGLE;
		}
		for (TransitionAttributesDTO props : propsDTO.getFollowing()) {
			if (props.getTransition() != null && props.getTransition().steps() > 0) {
				return Type.TRANSITION;
			}
		}
		return Type.VARIANT;
	}

	public static AbstractDTO2PropertiesMapper of(MandelbrotAttributesDTO propsDTO) {
		Type type = determineType(propsDTO);
		switch (type) {
		case SINGLE:
			return SingleDTO2PropertiesMapper.of(propsDTO);
		case VARIANT:
			return VariantsDTO2PropertiesMapper.of(propsDTO);
		case TRANSITION:
			return TransitionDTO2PropertiesMapper.of(propsDTO);
		default:
			break;
		}
		return null;
	}

	protected MandelbrotAttributesDTO getAttribsDTO() {
		return attribsDTO;
	}

}