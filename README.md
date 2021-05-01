# LexasoftMandelbrot
Calculating the Mandelbrot set

This is a framework to generate images of the Mandelbrot set.
For details of the Mandelbrot set, please refer to f.e. this [wikipedia page](https://en.wikipedia.org/wiki/Mandelbrot_set)

For now there is no gui implemented, just a cli application can be used to understand the functionality of this application.

## Architecture
The application is divided in 3 parts:
1. Calculation Unit 
    * Does the mathematical calculation of the Mandelbrot set
    * Gets the Parameters from the api
2. API
    * Realizes the access to the calculation unit
    * Can be used by any application or gui, wanting to create a calculation
3. Controller
    * Combines the elements to lead from the input to the a calculation
    * Can be used by applications and gui, but concentrates on batch processing
4. CLI (Sample) Application
     * Makes it possible to access the calculation over a CLI call
     * Uses YAML files to read the configuration

For more input regarding the architecture refer documents on [GitHub](https://github.com/nierax/LexasoftMandelbrot/tree/master/documentation)

## Usage
At the moment a runnable version is not provided, yet. So please clone the [repository from GitHub](https://github.com/nierax/LexasoftMandelbrot) and build the application by running maven target install.

Run the runnable jar (ls-mandelbrot-core-*{version}*-jar-with-dependencies.jar) with java -jar and the yaml file as parameter.

f.ex: 	

`java -jar ls-mandelbrot-core-{version}-jar-with-dependencies.jar my-yaml-file.yaml`

A sample YAML file is [here](https://github.com/nierax/LexasoftMandelbrot/blob/master/ls-mandelbrot-core/src/main/resources/mandelbrot-general-01.yaml)