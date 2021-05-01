/**
 * 
 */
package de.lexasoft.mandelbrot.api;

import java.awt.Color;
import java.io.IOException;

import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotColorizeFactory;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.MandelbrotRunnerException;
import de.lexasoft.mandelbrot.cu.MandelbrotIterator;

/**
 * This class runs a MandelbrotIterator calculation and colorization.
 * <p>
 * Attributes are read from #MandelbrotCalculationProperties.
 * 
 * @author nierax
 */
public class MandelbrotSingleRunner implements MandelbrotRunner {

	private MandelbrotPointPosition topLeft;
	private MandelbrotPointPosition bottomRight;
	private int maxIterations;
	private int imageWidth;
	private int imageHeight;
	private String imageFilename;
	private MandelbrotColorize colorize;
	private InfoCallbackAPI info;

	/**
	 * Use of Method to instantiate class.
	 */
	private MandelbrotSingleRunner() {

	}

	private MandelbrotSingleRunner initializeFromProperties(MandelbrotCalculationProperties props) {
		this.topLeft = MandelbrotPointPosition.of(props.getTopLeft().cx(), props.getTopLeft().cy());
		this.bottomRight = MandelbrotPointPosition.of(props.getBottomRight().cx(), props.getBottomRight().cy());
		this.maxIterations = props.getMaximumIterations();
		this.imageWidth = props.getImageWidth();
		this.imageHeight = props.getImageHeight();
		this.imageFilename = props.getImageFilename();
		Color mandelbrotColor = (props.getMandelbrotColor() == null) ? Color.BLACK : props.getMandelbrotColor();
		this.colorize = MandelbrotColorizeFactory.of(props.getPaletteVariant(), props.getCustomColorPalette(),
		    props.getColorGrading(), mandelbrotColor);
		return this;
	}

	/**
	 * Initializes a new runner from the given properties
	 * 
	 * @param props Properties, from which the runner is initialized
	 * @return The new runner object.
	 */
	public static MandelbrotSingleRunner of(MandelbrotCalculationProperties props, InfoCallbackAPI info) {
		MandelbrotSingleRunner runner = new MandelbrotSingleRunner();
		runner.info = info;
		return runner.initializeFromProperties(props);
	}

	public static MandelbrotSingleRunner of(MandelbrotCalculationProperties props) {
		return of(props, new DefaultInfoCallBack());
	}

	/**
	 * Runs a MandelbrotIterator calculation with the values, given during
	 * instantiation.
	 * 
	 * @throws MandelbrotRunnerException
	 */
	@Override
	public void run() throws MandelbrotRunnerException {
		try {
			MandelbrotIterator calculator = MandelbrotIterator.of(colorize);
			long start = System.currentTimeMillis();
			MandelbrotImage image = calculator.drawMandelbrot(topLeft, bottomRight, maxIterations, imageWidth,
			    imageHeight);
			long stop = System.currentTimeMillis();
			info.outCalculationReady(stop - start);
			image.writeAsFile(imageFilename);
			info.outFileWritten(imageFilename);
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

	int getMaxIterations() {
		return maxIterations;
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
