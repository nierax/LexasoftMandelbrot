# LexasoftMandelbrot
Calculating the Mandelbrot set

This is a framework to generate images of the Mandelbrot set.
For details of the Mandelbrot set, please refer to f.e. this [wikipedia page](https://en.wikipedia.org/wiki/Mandelbrot_set)

For now there is no gui implemented, just a cli application can be used to understand the functionality of this application.

The application is divided in 3 parts:
1. Calculation Unit 
  1. Does the mathematical calculation of the Mandelbrot set
  2. Gets the Parameters from the api
2. API
  1. Realizes the access to the calculation unit
3. Controller
  1. Combines the elements to lead from the input to the a calculation

For more input regarding the architecture refer documents on [GitHub](https://github.com/nierax/LexasoftMandelbrot/tree/master/documentation)