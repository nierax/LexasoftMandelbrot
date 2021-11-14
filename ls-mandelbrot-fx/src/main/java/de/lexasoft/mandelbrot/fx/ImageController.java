package de.lexasoft.mandelbrot.fx;

import java.net.URL;
import java.util.ResourceBundle;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.AspectRatioHandle;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * @author nierax
 *
 */
public class ImageController implements Initializable {

	@FXML
	private AnchorPane imagePane;

	@FXML
	private ImageView mbImage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imagePane.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				resizeImage(AspectRatioHandle.FITIN, newValue, imagePane.getHeight());
			}
		});

		imagePane.heightProperty().addListener((v, o, c) -> resizeImage(AspectRatioHandle.FITIN, imagePane.getWidth(), c));
	}

	private MandelbrotImage calculate(AspectRatioHandle arHandle, int width, int height) {
		MandelbrotAttributesDTO attribs = MandelbrotAttributesDTO.ofDefaults();
		attribs.getImage().setAspectRatioHandle(arHandle);
		attribs.getImage().setImageHeight(height);
		attribs.getImage().setImageWidth(width);
		return MandelbrotController.of().executeSingleCalculation(attribs);
	}

	private void resizeImage(AspectRatioHandle arHandle, Number width, Number height) {
		if ((width.intValue() > 0) && (height.intValue() > 0)) {
			Image image = SwingFXUtils
			    .toFXImage(calculate(AspectRatioHandle.FITIN, width.intValue(), height.intValue()).getImage(), null);
			mbImage.setImage(image);
		}
	}

}
