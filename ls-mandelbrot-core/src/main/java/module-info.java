module de.lexasoft.mandelbrot {
	requires java.desktop;
	requires org.slf4j;
	requires de.lexasoft.common;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires java.logging;
	requires com.fasterxml.jackson.dataformat.yaml;

	exports de.lexasoft.mandelbrot.ctrl;
	exports de.lexasoft.mandelbrot;

	opens de.lexasoft.mandelbrot.ctrl to com.fasterxml.jackson.databind;
	opens de.lexasoft.mandelbrot.api to com.fasterxml.jackson.databind;
}
