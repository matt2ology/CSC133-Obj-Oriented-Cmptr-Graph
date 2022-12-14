# Graphics Theory Quiz

## **Q1)** \[5 points\] In about 250 words describe homogenous coordinates. What are they, why do we use them, and how are they different from non-homogeneous coordinates

In game development we mostly care about 3D, and also 2D, matrices are used
for transforming positions, directions, or other matrices. There are a lot of
other uses of matrices - as well as mathematical formalities. In this context
we care about Vec4 and Matrix4 (x, y, z, w).

Homogenous coordinates boils down to matrix maths, a system of coordinates, that
are used on 2D/3D graphical objects. The numbers in the matrices represents
positional coordinates on a standard Cartesian coordinate system.

By using homogenous coordinates we are able to derive the following:

- position - position = displacement
- position + displacement = position
- displacement + displacement = displacement

Positions does not mean directions. Rotation of an object changes everything in
a matrix. Translation changes positions, but not the normals.

Homogenous coordinates are different from non-homogenous coordinates, for
non-homogenous are in of ordered pairs, a tuple, of 'x' and 'y' and are used in
classic Euclidean geometry; in contrast, homogenous coordinates are not in a
one-to-one representation of points, it allows the representation of lines, and
allow the representation of points at infinity.

## **Q2)** \[5 Points\] In about 250  words compare and contrast retained mode vs immediate mode GUI APIs. Give examples of each and discuss the pros and cons of each using technical language

Retained mode GUI APIs like immediate mode GUI APIs are used to create GUIs.
The difference is that in retained mode, the GUI is created once and then displayed.

In immediate mode, the GUI is created and displayed every frame.
The pros of retained mode is that it is more efficient because it only needs to be created once.
The cons of retained mode is that it is more difficult to implement.

The pros of immediate mode is that it is easier to implement.
The cons of immediate mode is that it is less efficient because it needs to be
created and displayed every frame. An example of a retained mode GUI API is JavaFX.
An example of an immediate mode GUI API is OpenGL.


## **Q3)** \[5 Points\] In about 250 words explain the concept of a graphics transform. Give as much technical detail as you can within the length constraint

A graphics transform is a way to change the way an object is drawn on the
screen without changing the object itself or the coordinates of the object.

We care about graphics transformation because we want to be able to move,
rotate, scale, etc. our objects in the game.

Graphics transformation are applied to a node in the order in which the
transformations are applied is the order in which they are added to the list
of transforms.

## **Q4)** \[5 Points\] In about 250 words explain the concept of a Bezier Curve. Give as much technical detail as you can within the length constraint

Bezier curve is a parametric curve used in computer graphics and related fields.
It is named after Pierre Bezier, who used it in the 1960s for designing car bodies,
and Pierre Bézier himself, who used it in the 1970s for designing the bodywork of Renault cars.

The curve is commonly used in computer graphics to model smooth curves.
It is a generalization of quadratic Bézier curves and cubic Bézier curves.
The general form of a Bezier curve is: `B(t) = Σi=0nPi * (1-t)n-i * t^i` where
`n` is the degree of the curve and Pi is the `i`th control point.
The curve starts at `P_0` and ends at `P_n`. The parameter `t` varies between 0 and 1.

The curve is a straight line when n = 1, and a quadratic or cubic Bézier curve
when n = 2 or n = 3, respectively. The curve is a smooth curve when the control
points are chosen so that the first and second derivatives are continuous at the endpoints.

The curve is a convex combination of the control points when n = 1, and a convex
combination of the control points and their midpoints when n = 2 or n = 3, respectively.

The curve is a rational Bézier curve when the control points are weighted.
The curve is a Bézier curve of degree n when the control points are n+1 points
in n-dimensional space. The curve is a Bézier curve of degree n when the
control points are n+1 points in n-dimensional homogeneous space.
