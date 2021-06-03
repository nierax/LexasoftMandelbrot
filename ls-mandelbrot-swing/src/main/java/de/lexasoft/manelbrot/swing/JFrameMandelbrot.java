/**
 * 
 */
package de.lexasoft.manelbrot.swing;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import de.lexasoft.mandelbrot.api.ColorGradingStyle;
import de.lexasoft.mandelbrot.api.MandelbrotCalculationProperties;
import de.lexasoft.mandelbrot.api.MandelbrotColorGrading;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;
import de.lexasoft.mandelbrot.api.PaletteVariant;

/**
 * @author nierax
 *
 */
@SuppressWarnings("serial")
public class JFrameMandelbrot extends JFrame {

	private int imageWidth;
	private int imageHeight;
	private int frameWidth;
	private int frameHeight;
	private MandelbrotCalculationProperties props;

	/**
	 * @throws HeadlessException
	 */
	public JFrameMandelbrot(int imageWidth, int imageHeight) throws HeadlessException {
		super("Lexasoft Mandelbrot");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.frameWidth = this.imageWidth + 20;
		this.frameHeight = this.imageHeight + 45;
		setBounds(300, 350, frameWidth, frameHeight);
		setVisible(true);
	}

	private MandelbrotCalculationProperties createDefaultProps() {
		MandelbrotCalculationProperties props = MandelbrotCalculationProperties.of();
		props.setTopLeft(MandelbrotPointPosition.of(-2.02d, 1.2d));
		props.setBottomRight(MandelbrotPointPosition.of(0.7d, -1.2d));
		props.setPaletteVariant(PaletteVariant.BLUEWHITE);
		props.setColorGrading(MandelbrotColorGrading.of(ColorGradingStyle.LINE, 6));
		props.setImageHeight(imageHeight);
		props.setImageWidth(imageWidth);
		return props;
	}

	public static void main(String[] args) {
		new JFrameMandelbrot(459, 405);
	}

}
