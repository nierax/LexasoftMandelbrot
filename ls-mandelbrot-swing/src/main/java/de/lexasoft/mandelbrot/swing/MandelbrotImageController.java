/**
 * 
 */
package de.lexasoft.mandelbrot.swing;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.swing.model.CalculationAreaControllerModel;
import de.lexasoft.mandelbrot.swing.model.CalculationControllerModel;
import de.lexasoft.mandelbrot.swing.model.ColorControllerModel;
import de.lexasoft.mandelbrot.swing.model.ImageControllerModel;
import de.lexasoft.util.TimeMeasureSupport;

/**
 * This controller does the calculation of the Mandelbrot set and sets it as
 * image into the corresponding JPanel.
 * 
 * @author nierax
 *
 */
public class MandelbrotImageController extends ModelChangingController<CalculationAreaControllerModel>
    implements ImageControllerModel {

	/**
	 * Runs the calculation in a separate thread to keep it detached from the UI
	 * thread.
	 *
	 */
	class RunCalculationTask extends SwingWorker<MandelbrotImage, Void> {

		private TimeMeasureSupport<MandelbrotImage> time;

		/**
		 * Run the calculation.
		 */
		@Override
		protected MandelbrotImage doInBackground() throws Exception {
			time = TimeMeasureSupport.of();
			return time.runProcess(() -> calculationAdapter.calculate(calcModel, colorCM, MandelbrotImageController.this));
		}

		/**
		 * Actions after the calculation is completed.
		 */
		@Override
		protected void done() {
			try {
				MandelbrotImage image = get();
				view.drawImage(image.getImage());
				durationChanged(time.getTimeElapsed());
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
				fireModelChangedEvent(
				    new ModelChangedEvent<CalculationAreaControllerModel>(MandelbrotImageController.this, calcAreaModel));

			} catch (InterruptedException e) {
				// Just let the task end.
			} catch (ExecutionException e) {
				// Just let the task end.
			}
		}

	}

	private ImagePanel view;
	private CalculationControllerModel calcModel;
	private ColorControllerModel colorCM;
	private RunCalculationAdapter calculationAdapter;
	private boolean running;
	private DurationUpdater durationUpdater;

	public MandelbrotImageController(CalculationControllerModel calcCM, ColorControllerModel colorCM,
	    Dimension initialSize, ImagePanel view) {
		this.running = false;
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
		if (isRunning()) {
			createWorker().execute();
		}
	}

	SwingWorker<MandelbrotImage, Void> createWorker() {
		return new RunCalculationTask();
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

	public boolean isRunning() {
		return running;
	}

	public void stopRunning() {
		this.running = false;
	}

	public void startRunning() {
		this.running = true;
		reCalculate();
	}

	private void durationChanged(long duration) {
		if (getDurationUpdater() != null) {
			getDurationUpdater().updateDuration(duration);
		}
	}

	/**
	 * @return the durationUpdater
	 */
	DurationUpdater getDurationUpdater() {
		return durationUpdater;
	}

	/**
	 * @param durationUpdater the durationUpdater to set
	 */
	void setDurationUpdater(DurationUpdater durationUpdater) {
		this.durationUpdater = durationUpdater;
	}

}
