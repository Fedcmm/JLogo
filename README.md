# Java Logo language interpreter

This is a simple interpreter of the [Logo](https://en.wikipedia.org/wiki/Logo_(programming_language))
programming language, developed for the Advanced Programming exam at [UNICAM](http://www.unicam.it/).

It is able to load a file with one instruction per line and can also save the 
generated drawing as text.


## Building and running

To build the program, first open a terminal window in the app's main folder, 
then run the `build` command using the correct version of the Gradle wrapper 
based on your OS:

    gradlew build       (on MacOS/Linux)
    gradlew.bat build   (on Windows)

After building, use the wrapper again to execute the `run` command that will 
start the program.


## Using the program

Instructions have to be placed in a _.txt_ file, one per line.

The program has the option to execute the instructions one by one or 
automatically, in which case the interval between two instructions can be 
adjusted with the slider on the right.


### Instructions

The program supports the following set of instructions:
* FORWARD _dist_
* BACKWARD _dist_
* LEFT _angle_
* RIGHT _angle_
* CLEARSCREEN
* HOME
* PENUP
* PENDOWN
* SETPENCOLOR _r_ _g_ _b_
* SETSCREENCOLOR _r_ _g_ _b_
* SETPENSIZE _r_ _g_ _b_
* REPEAT _num_ \[ _cmds_ \] \(repeats for _num_ times the commands in the list, commands must be separated by semicolons\)