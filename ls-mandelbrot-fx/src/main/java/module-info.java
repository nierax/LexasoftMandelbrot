module de.lexasoft.mandelbrot.fx {
	requires javafx.controls;
	requires javafx.fxml;

	opens de.lexasoft.mandelbrot.fx to javafx.graphics, javafx.fxml;
}
