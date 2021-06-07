# Changelog

## Version 0.1.2
* MandelbrotCalculationProperties allows to specify an abstract MandelbrotImage.
* First version of a swing gui added
* Color control panel to the gui added.

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