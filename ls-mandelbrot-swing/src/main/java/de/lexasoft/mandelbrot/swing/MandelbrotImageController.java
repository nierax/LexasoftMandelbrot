/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;
import de.lexasoft.mandelbrot.swing.model.ImageControllerModel;

/**
 * This controller does the calculation of the Mandelbrot set and sets it as
 * image into the corresponding JPanel.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageController extends ModelChangingController<CalculationAreaControllerModel>
    implements ImageControllerModel {

	private ImagePanel view;
	private CalculationControllerModel calcModel;
	private ColorControllerModel colorCM;
	private RunCalculationAdapter calculationAdapter;

	public MandelbrotImageController(CalculationControllerModel calcCM, ColorControllerModel colorCM,
	    Dimension initialSize, ImagePanel view) {
		this.view = view;
		this.calcModel = calcCM;
		this.colorCM = colorCM;
		this.calculationAdapter = RunCalculationAdapter.of();
		initView(initialSize);
	}

	void initView(Dimension initialSize) {
		this.view.setPreferredSize(initialSize);
	}

	public void initController(CalculationControllerModel calcModel) {
		setCalcModel(calcModel);
		this.view.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				calculateAndDraw();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				calculateAndDraw();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// Do nothing
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// Do nothing
			}
		});
	}

	/**
	 * 
	 */
	private void calculateAndDraw() {
		MandelbrotImage image = calculationAdapter.calculate(calcModel, colorCM, this);
		view.drawImage(image.getImage());
		CalculationAreaControllerModel calcAreaModel = new CalculationAreaControllerModel() {

			@Override
			public ImageArea image() {
				return ImageArea.of(image.getImage().getWidth(), image.getImage().getHeight());
			}

			@Override
			public CalculationArea calculation() {
				return CalculationArea.of(calcModel.topLeft(), calcModel.bottomRight());
			}

			@Override
			public CalculationArea total() {
				return CalculationArea.of(image.topLeft(), image.bottomRight());
			}

		};
		fireModelChangedEvent(new ModelChangedEvent<CalculationAreaControllerModel>(this, calcAreaModel));
	}

	/**
	 * Calculates and redraws the component.
	 */
	public void reCalculate() {
		calculateAndDraw();
	}

	/**
	 * Has to be called, when the underlying color controller model has changed.
	 * <p>
	 * To ensure a lightweight message communication, the link is set within the
	 * {@link MandelbrotUIController} with a lambda expression.
	 * 
	 * @param event The event connected with the change.
	 */
	public void colorModelChanged(ModelChangedEvent<ColorControllerModel> event) {
		this.colorCM = (ColorControllerModel) event.getModel();
		calculateAndDraw();
	}

	/**
	 * Has to be called, when the underlying calculation controller model has
	 * changed.
	 * <p>
	 * To ensure a lightweight message communication, the link is set within the
	 * {@link MandelbrotUIController} with a lambda expression.
	 * 
	 * @param event The event connected with the change.
	 */
	public void calculationModelChanged(ModelChangedEvent<CalculationControllerModel> event) {
		setCalcModel(event.getModel());
		calculateAndDraw();
	}

	/**
	 * @return the calcModel
	 */
	CalculationControllerModel getCalcModel() {
		return calcModel;
	}

	ColorControllerModel getColorModel() {
		return colorCM;
	}

	/**
	 * @param calcModel the calcModel to set
	 */
	void setCalcModel(CalculationControllerModel calcModel) {
		this.calcModel = calcModel;
	}

	public void replaceModel(CalculationControllerModel calcCtrlModel) {
		setCalcModel(calcCtrlModel);
	}

	@Override
	public int imageWidth() {
		return view.getWidth();
	}

	@Override
	public int imageHeight() {
		return view.getHeight();
	}

	@Override
	public String imageFilename() {
		// Image file name not supported here.
		return null;
	}

}
