package de.lexasoft.mandelbrot.fx;

import java.net.URL;
import java.util.ResourceBundle;

import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.ctrl.MandelbrotAttributesDTO;
import de.lexasoft.mandelbrot.ctrl.MandelbrotController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author nierax
 *
 */
public class ImageController implements Initializable {

	@FXML
	private ImageView mbImage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = SwingFXUtils.toFXImage(calculate().getImage(), null);
		mbImage.setImage(image);
	}

	private MandelbrotImage calculate() {
		return MandelbrotController.of().executeSingleCalculation(MandelbrotAttributesDTO.ofDefaults());
	}

}
