/**
 * 
 */
package de.lexasoft.mandelbrot.cu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.lexasoft.mandelbrot.ColorGradingLine;
import de.lexasoft.mandelbrot.ColorPaletteFactory;
import de.lexasoft.mandelbrot.MandelbrotBlackWhite;
import de.lexasoft.mandelbrot.MandelbrotColorPalette;
import de.lexasoft.mandelbrot.MandelbrotColorize;
import de.lexasoft.mandelbrot.MandelbrotImage;
import de.lexasoft.mandelbrot.api.CalculationArea;
import de.lexasoft.mandelbrot.api.ImageArea;
import de.lexasoft.mandelbrot.api.MandelbrotPointPosition;

/**
 * @author nierax
 */
class MandelbrotIteratorTest {

  private CalculationArea calculation;
  private ImageArea imageDim;
  private static final int IMAGE_WIDTH = 459;
  private static final int IMAGE_HEIGHT = 405;
  private static final String IMAGE_DIRECTORY = "junit-tmp";

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    calculation = CalculationArea.of(MandelbrotPointPosition.of(-2.02, 1.2), MandelbrotPointPosition.of(0.7, -1.2));
    imageDim = ImageArea.of(IMAGE_WIDTH, IMAGE_HEIGHT);
  }

  private static Stream<Arguments> testDrawMandelbrot() {
    ColorPaletteFactory cFactory = new ColorPaletteFactory();
    List<Color> ungraded = new ArrayList<>();
    ungraded.add(Color.BLUE);
    ungraded.add(Color.WHITE);
    ungraded.add(Color.ORANGE);
    ungraded.add(Color.WHITE);
    return Stream.of(
        // FAST, Black and white
        Arguments.of(CalculationVersion.FAST, //
            new MandelbrotBlackWhite(), 500, //
            IMAGE_DIRECTORY + "/mandelbrot-bw-fast.tiff"),
        // FAST, Rainbow 29 colors
        Arguments.of(CalculationVersion.FAST, //
            MandelbrotColorPalette.of(cFactory.createRainbowPalette29(), Color.BLACK), //
            580, IMAGE_DIRECTORY + "/mandelbrot-rainbow-fast.tiff"),
        // FAST, 3 colors in list
        Arguments.of(CalculationVersion.FAST, //
            MandelbrotColorPalette.of(ColorGradingLine.of().gradePalette(ungraded, 21), Color.BLACK), 500,
            IMAGE_DIRECTORY + "/mandelbrot-colorlist-fast.tiff"),
        // EXACT, Black and white
        Arguments.of(CalculationVersion.EXACT, //
            new MandelbrotBlackWhite(), 50, //
            IMAGE_DIRECTORY + "/mandelbrot-bw-exact.tiff"),
        // FAST, Rainbow 29 colors
        Arguments.of(CalculationVersion.EXACT, //
            MandelbrotColorPalette.of(cFactory.createRainbowPalette29(), Color.BLACK), //
            50, IMAGE_DIRECTORY + "/mandelbrot-rainbow-exact.tiff"),
        // FAST, 3 colors in list
        Arguments.of(CalculationVersion.EXACT, //
            MandelbrotColorPalette.of(ColorGradingLine.of().gradePalette(ungraded, 21), Color.BLACK), //
            50, IMAGE_DIRECTORY + "/mandelbrot-colorlist-exact.tiff"));

  }

  /**
   * Test method for
   * {@link de.lexasoft.mandelbrot.cu.MandelbrotIteratorFast#drawMandelbrot(de.lexasoft.mandelbrot.api.MandelbrotPointPosition, de.lexasoft.mandelbrot.api.MandelbrotPointPosition, int, int, int)}.
   * 
   * @throws IOException
   */
  @ParameterizedTest
  @MethodSource
  void testDrawMandelbrot(CalculationVersion version, MandelbrotColorize col, int maxIter, String filename)
      throws IOException {
    MandelbrotIterator cut = MandelbrotIterator.of(version, col);
    MandelbrotImage image = cut.drawMandelbrot(calculation, maxIter, imageDim);
    assertNotNull(image, "Image could not be created");
    image.writeToFile(filename);
  }

}
