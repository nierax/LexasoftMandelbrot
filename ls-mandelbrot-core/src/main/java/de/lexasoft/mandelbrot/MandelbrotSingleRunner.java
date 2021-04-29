/**
 * 
 */
package de.lexasoft.mandelbrot;

import java.awt.Color;
import java.io.IOException;

/**
 * This class runs a Mandelbrot calculation and colorization.
 * <p>
 * Attributes are read from #MandelbrotCalculationProperties.
 * 
 * @author nierax
 */
public class MandelbrotSingleRunner implements MandelbrotRunner {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private int maximumIterations;
	private int imageWidth;
	private int imageHeight;
	private String imageFilename;
	private MandelbrotColorize colorize;

	/**
	 * Use of Method to instantiate class.
	 */
	private MandelbrotSingleRunner() {

	}

	private MandelbrotSingleRunner initializeFromProperties(MandelbrotCalculationProperties props) {
		this.topLeft = MandelbrotPointPosition.of(props.getTopLeft().cx(), props.getTopLeft().cy());
		this.bottomRight = MandelbrotPointPosition.of(props.getBottomRight().cx(), props.getBottomRight().cy());
		this.maximumIterations = props.getMaximumIterations();
		this.imageWidth = props.getImageWidth();
		this.imageHeight = props.getImageHeight();
		this.imageFilename = props.getImageFilename();
		Color mandelbrotColor = (props.getMandelbrotColor() == null) ? Color.BLACK : props.getMandelbrotColor();
		this.colorize = MandelbrotColorizeFactory.of(props.getPaletteVariant(), props.getCustomColorPalette(), props.getColorGrading(),
		    mandelbrotColor);
		return this;
	}

	/**
	 * Initializes a new runner from the given properties
	 * 
	 * @param props Properties, from which the runner is initialized
	 * @return The new runner object.
	 */
	public static MandelbrotSingleRunner of(MandelbrotCalculationProperties props) {
		MandelbrotSingleRunner runner = new MandelbrotSingleRunner();
		return runner.initializeFromProperties(props);
	}

	/**
	 * Runs a Mandelbrot calculation with the values, given during instantiation.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	@Override
	public void run() throws MandelbrotRunnerException {
		Mandelbrot calculator = Mandelbrot.of(colorize);
		MandelbrotImage image = calculator.drawMandelbrot(topLeft, bottomRight, maximumIterations, imageWidth, imageHeight);
		try {
			image.writeAsFile(imageFilename);
		} catch (IOException e) {
			throw new MandelbrotRunnerException(e);
		}
	}

	MandelbrotPointPosition getTopLeft() {
		return topLeft;
	}

	MandelbrotPointPosition getBottomRight() {
		return bottomRight;
	}

	int getMaximumIterations() {
		return maximumIterations;
	}

	int getImageWidth() {
		return imageWidth;
	}

	int getImageHeight() {
		return imageHeight;
	}

	String getImageFilename() {
		return imageFilename;
	}

	MandelbrotColorize getColorize() {
		return colorize;
	}
}
