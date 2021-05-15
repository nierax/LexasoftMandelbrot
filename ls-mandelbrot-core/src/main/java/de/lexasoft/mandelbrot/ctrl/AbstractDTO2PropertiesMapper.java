package de.lexasoft.mandelbrot.ctrl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.lexasoft.mandelbrot.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;

public abstract class AbstractDTO2PropertiesMapper {

	enum Type {
		SINGLE, VARIANT, TRANSITION
	}

	private CalculationPropertiesDTO propsDTO;

	public AbstractDTO2PropertiesMapper(CalculationPropertiesDTO propsDTO) {
		this.propsDTO = propsDTO;
	}

	/**
	 * In this method the list of calculations, given in the following parameter
	 * must be mapped and added to the listOfProps.
	 * 
	 * @param dto
	 * @param listOfProps
	 */
	protected abstract void mapFollowingCalculations(List<TransitionPropertiesDTO> followingDTO,
	    List<MandelbrotCalculationProperties> listOfProps);

	private Double mapDoubleFromString(String val) {
		return ((val == null) || ("".equals(val) || ("auto".equals(val))) ? Double.NaN : Double.parseDouble(val));
	}

	private MandelbrotPointPosition mapPoint(PointDTO point) {
		double cx = mapDoubleFromString(point.getCx());
		double cy = mapDoubleFromString(point.getCy());
		return MandelbrotPointPosition.of(cx, cy);
	}

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
		MandelbrotCalculationProperties baseProps = mapSingleCalculation(propsDTO, MandelbrotCalculationProperties.of());
		listOfProps.add(baseProps);
		mapFollowingCalculations(propsDTO.getFollowing(), listOfProps);
		// At last: Normalize all properties in list
		listOfProps.stream().forEach((p) -> p.normalize());
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
	protected MandelbrotCalculationProperties mapSingleCalculation(CalculationPropertiesDTO dto,
	    MandelbrotCalculationProperties props) {
		if (dto.getTopLeft() != null) {
			props.setTopLeft(mapPoint(dto.getTopLeft()));
		}
		if (dto.getBottomRight() != null) {
			props.setBottomRight(mapPoint(dto.getBottomRight()));
		}
		if (dto.getMaximumIterations() > 0) {
			props.setMaximumIterations(dto.getMaximumIterations());
		}
		if (dto.getImageWidth() > 0) {
			props.setImageWidth(dto.getImageWidth());
		}
		if (dto.getImageHeight() > 0) {
			props.setImageHeight(dto.getImageHeight());
		}
		if (dto.getImageFilename() != null && !"".equals(dto.getImageFilename())) {
			props.setImageFilename(dto.getImageFilename());
		}
		if (dto.getPaletteVariant() != null) {
			props.setPaletteVariant(dto.getPaletteVariant());
		}
		if (dto.getCustomColorPalette() != null) {
			props.setCustomColorPalette(mapListOfColorDTO2(dto.getCustomColorPalette()));
		}
		if (dto.getColorGrading() > 0) {
			props.setColorGrading(dto.getColorGrading());
		}
		if (dto.getMandelbrotColor() != null) {
			props.setMandelbrotColor(dto.getMandelbrotColor().getColor());
		}
		return props;
	}

	private static Type determineType(CalculationPropertiesDTO propsDTO) {
		if ((propsDTO.getFollowing() == null)) {
			return Type.SINGLE;
		}
		if (propsDTO.getFollowing().isEmpty()) {
			return Type.SINGLE;
		}
		for (TransitionPropertiesDTO props : propsDTO.getFollowing()) {
			if (props.getTransition() != null && props.getTransition().steps() > 0) {
				return Type.TRANSITION;
			}
		}
		return Type.VARIANT;
	}

	public static AbstractDTO2PropertiesMapper of(CalculationPropertiesDTO propsDTO) {
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

	protected CalculationPropertiesDTO getPropsDTO() {
		return propsDTO;
	}

}