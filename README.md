# Bitmap Transformer

This repo contains code for a CLI that is capable of performing multiple transformations to a bitmap image (.bmp). In order to use the CLI, clone the repo and within the root of the repo, run the command in this format:

``` 
./gradlew run --args 'input-file-path output-file-path transform-command transform-parameter'
```

* _input-file-path_ : path to the bmp file that will be tranformed
* _output-file-path_ : path to a bmp file that will hold the transformation
* _transform-command_ : single word command to indicate which transformation. List of transformation listed below
* _transform-parameter_ : parameter for some transformations. Enter 0 if command does not need a parameter.

The repo contains sample files under resources folder that contains the original bmp file and files that are the result of each transformation. 

### List of Transformations

#### 1. Randomize

Randomized each pixel rgb values

**Command** : 'randomize'
**Parameter**: none

#### 2. Invert Color

Invert each pixel rgb values 

**Command** : 'invert'
**Parameter**: none

#### 3. Grayscale

Convert image to grayscale or black or white 

**Command** : 'grayscale'
**Parameter**: none

#### 4. Lighten or Darken

Lighten or darken the image by a certain percentage that you specify in the parameter  

**Command** : 'changeBrightness'
**Parameter**: '0-100' (percentage)

#### 5. Rotate 90 Degrees Clockwise or Counterclockwise

Rotate the image either clockwise or counterclockwise. Put in parameter either 1 for clockwise or -1 for counterclockwise

**Command** : 'rotate'
**Parameter**: 1 or -1

#### 6. Flip Horizontally

Flip the image across the vertical axis

**Command** : 'flipHorizontally'
**Parameter**: none

#### 7. Flip Vertically

Flip the image across the horizontal axis

**Command** : 'flipVertically'
**Parameter**: none