# Change log

## Unpublished

## Version 0.1.8
**Enhancements**

* Internal code optimized:
* Clearer API to calculation package
* Use of message handling with de.lexasoft.common.model.Message (needs de.lexasoft::ls-commons-parent v0.1.4 or above)
* Showing an hint, that custom palettes are not supported yet.
* Using the result type de.lexasoft.common.model.Result<T>

**Bug fixes**

* Use of custom palette in Swing GUI does not cause an exception anymore. Default Frozen is used.

## Version 0.1.7 
October 26, 2021

**Enhancements**

* Status bar added with duration of calculation
* Logging to log file with java.util.Logging
* Custom log configuration (logger.properties) can be added as first application parameter
* Divider between image and control area can be slipped

**Bug fixes**

* Resize of the image did not work properly in horizontal direction

## Version 0.1.6
**Enhancements**
* Assembly of Mandelbrot swing application added.
** Bug Fixes**
* Corrected: Use of older JUnit packages.

## Version 0.1.5
**Enhancements**
* Improved UI performance by running calculation in a separate thread
* Image can be dragged with the mouse
* Text input fields for calculation and colors made error resistant and type safe. Lexasoft commons 0.1.1 and up is needed.
* Keep the selected directory in image export dialog
**Bug fixes**
* After loading a calculation, the old one was shown sometimes

## Version 0.1.4
* Zooming in and out in calculation with mouse wheel

## Version 0.1.3
* UI allows to export calculation to image files

## Version 0.1.2
* MandelbrotCalculationProperties allows to specify an abstract MandelbrotImage.
* First version of a swing gui added
* Color control panel to the gui added
* Showing the calculation area in the image
* File menu to save and load the calculation added
* Handling of aspect ratio improved

## Version 0.1.1
May 31, 2021
* Color grading now supports two modes:
** LINE: Colors are stretched between each color of the palette, but not between the last and the first color.
** CIRCLE: The color turns back to start by grading from end to start again
* Palette Variant BLUEWHITE added

## Version 0.1.0 
May 18, 2021
* Transitions from one snapshot to another added
* Transition covers the Mandelbrot coordinates and the maximum number of iterations.
* Transition methods are linear, slow in, slow out, slow in out
* For details please check the sample yaml files

## May 1, 2021
First version provided, still 0.0.1-SNAPSHOT.

**Included features:**
* Calculation of Mandelbrot set images
* One single image or a couple of them in a batch
* All can be controlled via an cli8 application with an yaml file as input
* Several predefined color palettes:
    * Black / White
    * Rainbow in 29 colors
    * Rainbow in 7 colors
* In addition: custom palettes possible with arbitrary number of colors
* Colors can be "strechted" between the colors in the palette by specifying a grading
    * The grading is the number of colors, the palette does have by calculating shading between the entries in the original palette.