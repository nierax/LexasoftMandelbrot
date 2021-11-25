module de.lexasoft.mandelbrot.fx {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.base;
	requires javafx.graphics;
	requires de.lexasoft.mandelbrot;

	// opens de.lexasoft.mandelbrot.fx to javafx.graphics, javafx.fxml;
	opens de.lexasoft.mandelbrot.fx;
}
