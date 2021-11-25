module de.lexasoft.mandelbrot {
	requires transitive java.desktop;
	requires org.slf4j;
	requires de.lexasoft.common;
	requires com.fasterxml.jackson.annotation;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires java.logging;
	requires com.fasterxml.jackson.dataformat.yaml;

	exports de.lexasoft.mandelbrot.ctrl;
	exports de.lexasoft.mandelbrot.api;
	exports de.lexasoft.mandelbrot;

	opens de.lexasoft.mandelbrot.ctrl;
	opens de.lexasoft.mandelbrot.api;
	opens de.lexasoft.mandelbrot;
	opens de.lexasoft.mandelbrot.cu;
}
