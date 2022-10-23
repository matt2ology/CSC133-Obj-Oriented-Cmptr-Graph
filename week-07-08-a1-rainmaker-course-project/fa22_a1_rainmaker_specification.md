# Assignment 1 :: A first Version of RainMaker

<iframe title="RainMaker A1 Demo" src="https://player.vimeo.com/video/762861924?h=b6497d0e08&amp;badge=0&amp;autopause=0&amp;player_id=0&amp;app_id=58479" width="854" height="480" allowfullscreen="allowfullscreen" allow="autoplay; fullscreen; picture-in-picture"></iframe>

## Introduction

This term we will be studying object-oriented graphics programming and design.
Simple video games provide a good platform for understanding these concepts and
you will be developing successively more complex variants of a simple
2D top-down video game over the course of the term.

This term our game is called **RainMaker.** RainMaker is set in the near future
where endless drought is wreaking havoc in the central valley. Ponds and lakes
are drying up and birdlife has virtually disappeared. With your specially
equipped helicopter you will roam the skies of the central valley seeking
clouds to seed using your custom designed _rainmaker_ helicopter attachment.
The rainmaker has several modes of operation and can either seed clouds from
above or shoot at them from the sides. The seed from above approach is less
effective, however, it does not harm birdlife. You will have to carefully
choose your cloud seeding strategies in order to prevent harm to birdlife.

As you seed clouds, their color changes as they become more ready to bring more
rain to the central valley. In this first version of the game, we will keep
things simple and have just a single level with just two clouds to seed,
a single pond, and only the top-down cloud seeding apparatus, and no birds or
fuel concerns. The level is completed when the pond level returns to an
acceptable level and the helicopter safely lands on the helipad. Future
versions of the game we will ramp up the complexity with the need for mid-air
refueling via fueling blimps, returning flocks of birds that increase the
challenge of seeding clouds, wind that keeps the clouds moving, and more
complex helicopter mechanics. As the game increases in complexity over the term
you will have to avoid other objects and manage your fuel and other resources
carefully in order to win the game.

The goal of this first assignment is to develop a simple version of the game
and to develop the basic OOP framework using the JavaFX API. You will be
building on this for the remainder of the semester and refactoring your code
continuously. To that end, we are not hyper concerned about getting everything
right the first time around. It’s far more important that you get it working
and then work on improving your working solution by continuously refactoring to
cleaner code as we progress through the term.

For this version we will use the keyboard to control a single helicopter in a
simplified graphical display that uses the entire screen. In later versions we
will add additional screen components and controls as we learn how to build
more complex GUI interfaces.

## High Level Program Structure

We’re going to start out with a basic structure that we will build on over the
course of the remainder of the term. Some of this structure will remain in
place throughout the project, other parts of it you will change and adapt.
Don’t be afraid of this, refactoring is an important part of good coding.

Pay close attention to the structure description below. If class names are
specified specifically in this document, then you are required to use those
exact class names in your project. We may refactor this structure in future
assignments, do not be afraid of change.

**Class GameApp**

At the highest level we have the class **GameApp**. This class extends the
JavaFX Application class. The purpose of this class is to manage the high-level
aspects of our application and setup and show the initial Scene for your
application. The GameApp class sets up all keyboard event handlers to invoke
public methods in Game.

### _**Class Game**_

For this first version all game logic and object construction belong in the
**Game** class. All of the rules in our game are implemented in the **Game**.
This class holds the state of the game and determines win/lose conditions and
instantiates and links the other _Game Objects._ The **Game** does not know
anything about where user input comes from or how it is generated.
The Game class extends the JavaFX class Pane. This allows the Game class to be
the container for all game objects. For this version of the game we will not
have a separate game object collection. This may change in a future revision.

_At this stage we are not overly concerned that we are purely and proper_
_implementing any particular application pattern, e.g., MVC. We do, however,_
_want to start thinking about separation of concerns._

_The interaction of these classes is discussed further later in the document._

### ### _**Game Object Classes**_

_In addition to the classes described above you will have some additional_
_classes that represent game objects._ In this version of this project, you
will build a simple hierarchy of game objects. Because we want to inherit the
properties of JavaFX Node objects, our game object class will extend the JavaFX
**Group** class. This alleviates us from having to setup a number of different
properties that each object needs, for example, the object’s location in the
world.

Later in this document I will discuss the basics of object behaviors and
private data, but for now, let’s jump into the various classes that will
represent the game objects.

### _**Class GameObject**_

The abstract **GameObject** class is the base of our object hierarchy.
It contains methods and fields that manage the common aspects of all game
objects in our program. Any state or behavior in this class should apply to all
game object this. For example, the helicopter can move, while a pond cannot.
Consequently, you would not include anything regarding movement in this class.

### _**Class Pond**_

This class represents a pond or lake in the Central Valley.
For this first version of the project, we will abstract the pond as a simple
blue circle placed at random such that it does not intersect any other ground
based object.

### _**Class Cloud**_

This class represents a cloud in the skies of the Central Valley.
For this first version of the project, we will abstract the cloud as a simple,
initially white, circle placed at random anywhere other than fully directly
over the helipad.

### _**Class Helipad**_

This class represents the starting and ending location of this first game.
The helicopter will take off from the helipad and after seeding all of the
clouds will have to land back on the helipad in order to end the game.
For this game there is no actual notion of altitude. A helicopter is landed on
the helipad whenever it is contained within the bounds of the helipad and not
moving.

The helipad is represented on screen by a gray square with a gray circle
centered within the square. There should be a gap between the circle’s edge and
the square edge. The exact relationship is not strict and you may adjust for
taste. However, the circle must be centered and there must be a clear and
visible gap between the circle and the square. The helipad should be centered
along the width of the screen and should be roughly one half of its width above
the bottom edge of the screen. Feel free to adjust slightly to make sure that
your Helicopter fuel readout is clearly visible on startup.

### _**Class Helicopter**_

This class is the most complex game object and represents the main
_player character_ of this version of the game. The helicopter is represented
as a small filled yellow circle with a line emanating from the center of the
circle pointing in the direction of the helicopter’s heading.

In this project the heading of an object is the compass heading specified in
degrees. Note: When the helicopter is initially place on the map it is placed
facing a heading of zero degrees, or, due north, and a speed of zero.
There are some complications relating to compass heading that are discussed
later in this document. As the heading changes the line must rotate to point in
the direction of the new heading. As with the helipad, you should derive the
size of the helicopter object from the dimensions of the screen. More on this
later. As long as it looks reasonably similar to the drawing and behaves as
described herein, you will be fine.

Below the helicopter you must display the current fuel, as shown above.
These move with the helicopter but remain in the same position relative to the
helicopter body. The Helicopter is initially centered on the Helipad so it is a
good idea to pass the necessary coordinates into the Helicopter’s constructor.
You must pass in the center of the helipad. You may want to think about the
order in which you create the objects to avoid issues here. Note, you cannot
derive the coordinates of the helipad based on its placement rules for this
game. In other words, if the location of the helipad changes, no changes to the
helicopter code should have to be made. You are allowed to compute any
adjustments necessary to center the helicopter on the helipad based on the
center of the helipad that is passed in. If this doesn’t make sense at the
moment, then just go ahead and use whatever you think will work and you should
be able to immediately see what needs to be done. This leads us to some
additional advice that you would do well to heed throughout this course:

Don’t be afraid to experiment and try things out. More importantly,
give yourself enough time for this experimentation as it is an important part
of the discovery and learning process.

The fuel properties of the helicopter are integer values. The initial fuel
value is set for playability at 25000 and must be specified in the Game class.
You should adjust the rest of your constants to make your game playable in a
similar timeframe as the demo. The helicopter also has a speed that is
initially set to zero. The helicopter speed increases and decreases with brake
and acceleration commands, but, for now, there is a maximum speed of 10 and a
minimum speed of -2. The helicopter will ignore requests to go faster than the
maximum speed or slower than the minimum. Note that negative speeds fly the
helicopter backwards and that the speed transitions smoothly from forward to
reverse direction.

We will discuss all of these objects further in a later section on game
behavior. For now, let’s take a look at all of the commands that we need to
play this first version of the game.

### _**Class Pond and Cloud**_

This class represents a single pond or cloud on the abstract map of the central
valley. The Pond is represented on the screen by a blue circle and the cloud is
represented by a white circle. Both display a percentage text in the center.
For the pond the percentage is of the normal radius of the pond, for the cloud
it is the percentage of saturation. For this version of the game, you will only
one of each place at random in the upper two thirds of the screen. Each of your
objects must have some slight random variation in both size and position when
placed so that each time you play the game both the position and size are
slightly different. It is your job to manage this variation so that the game is
fun and playable. This will be a recurring theme throughout this course and it
should be among your first lessons in the following idea:

Just because the code works does not mean that the project is complete.

As long as the pond’s percentage is less than 100% you must make it rain by
seeding the cloud. You seed the cloud by flying over the cloud and pressing the
space bar rapidly, or, holding the space bar down. Every press of the space bar
increases the saturation by 1%. As the cloud becomes more saturated, the color
turns to gray. Simply decrease all RGB values by the amount of saturation.
A fully saturated cloud would thus have the color rgb(155,155,155). When the
saturation reaches 30% the rainfall will start to fill the pond at a rate
proportional to the cloud’s saturation. The rainfall must increase the area,
not the radius. In future versions of the game, we will adapt this further.
The cloud will automatically lose saturation when it’s not being seeded at a
rate that allows the percentage to drop about 1%/second. For this version of
the game, you will just need to adjust this experimentally. In future versions
we will make this depend on frame rate so that we can be consistent.

We will use interfaces to define small slices of behavior and as a type for
iteration purposes as needed. You will need the following interfaces. While
there may be other interfaces going forward, you will want to define each class
above with the following interfaces as necessary.

### _**Interface Updatable**_

For this version of the game, we will only have this single interface.
Updatable classes are dynamic and implement a callback, update() method that is
invoked from the main game timer. Note that for this version of the game, not
all game objects are updatable. For example, the helipad has no dynamic
behavior at this time.

## Game Commands

For this version of the game all of the game input is via keyboard commands.
Later on in the course we will talk in detail about things like command objects
and event driven operation. For now, we are just going to use these features as
given below. I will give you a basic explanation of the behavior and some
boiler plate code to implement the commands.

**The Commands**

Left Arrow Changes the heading of the helicopter by 15 degrees to the left.  
Right Arrow Changes the heading of the helicopter by 15 degrees to the right.  
Up Arrow Increases the speed of the helicopter by 0.1.  
Down Arrow Decreases the speed of the helicopter by 0.1.  
‘I’ Turns on the helicopter ignition.  
‘b’ \[optional\] shows bounding boxes around objects.  
‘r’ Reinitializes the game

For each command in our game, we want to add a key listener in our GameApp class.
Note in JavaFX these are constants and you do not need to define them in terms
of any integer or character constants. See the following URL:

<https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html>

## Game Mechanics

You should have watched the game play demo video to get a basic idea of the
gameplay for this version of the game. Note, we will be changing both the game
play as well as the win/lose conditions throughout the term. At each stage the
gameplay will reflect the learning goals for the module.

**Game Play Overview**

The game begins with the helicopter stopped and resting on the pad. For this
version of the game the engine is not running and will need to be started by
pressing the ‘i’ key. Up until the helicopter is running, it does not consume
fuel. As soon as you start the helicopter it consumes fuel on every cycle.
The player will use the navigation keys to move the helicopter towards the
cloud. At the cloud the helicopter will slow down and seed the cloud by
pressing the space bar rapidly. The helicopter may leave the cloud at any time,
however, the saturation level is always decreasing and the single pond will
need to reach 100% capacity in order to avoid a loss.

The complete the game, the player must return to the landing pad and bring the
speed of the helicopter back to zero and turn off the ignition. The game ends
when the win conditions are met or the helicopter runs out of fuel, whichever
comes first. Once this happens the game will end and a dialog box will appear
to report the player’s score which is defined as the remaining fuel, and to
give the player an opportunity to play again. If at any time during the mission
the player runs out of fuel then the game is over and a dialog box will appear
letting the player know that they have lost the game and, again, giving them an
opportunity to play the game again.

**Helicopter Mechanics**

The helicopter is somewhat more complex and will evolve significantly over the
course of the term. For this assignment we want to focus on simple movement and
firefighting mechanics. The **Helicopter** object has a number of properties in
addition to the location property. The helicopter’s state includes fields for
heading, speed, fuel, and water. Make sure that you review the section on
object-oriented programming in this assignment before you start coding setters
and getters arbitrarily.

### _**Movement**_

The helicopter moves forward with increasing speed as the up arrow is pressed.
You should define a maximum speed that the helicopter does not exceed. For this
version of the game, increase the speed by 0.1 for each press of the up arrow
and implement a max speed of 10 and a minimum speed of -2. Adjust all other
timing factors to work with these choices. Pressing the down arrow reduces the
speed and will begin to move the helicopter backwards as the speed becomes
negative. In later versions of the game, we will adjust this behavior somewhat.
In addition to more realistic flight mechanics, we will link the speed change
to the device’s refresh rate so that the game plays well on both fast and slow
devices.

The right and left arrow keys will adjust the _heading_ of the helicopter.
In this first version we will keep this very simple and simply change the
heading by fifteen degrees to the left or to the right based on which key was
pressed. A heading of zero degrees represents due north and you will want to
make sure that your code respects this convention. It is very important to
realize, however, that most of calculations that you will need to execute will
expect that zero degrees lies along the X axis. Moreover, the various
trigonometric functions such as sine and cosine expect the argument to be in
radians, and not degrees. Finally, because we are not using the standard device
coordinates that causes the Y axis values to increase in a downward direction,
a _turnLeft()_ and _turnRight()_ will work as expected in terms of direction.
In order to achieve this easily, you will want to implement a Y scaling of (-1)
in your main Game class (Pane).

Turning or changing speed has no immediate effect on the helicopter’s movement.
These actions merely change the state of the helicopter by updating the
helicopter’s speed and heading fields. The helicopter’s position is updated
when its _move()_ method is invoked from the _update()_ method in the Game
class.

## Detailed Program Structure

Before we start talking about the internal structure of this project, let’s
clarify some things about programming in Java related to common misconceptions
as well as some unusual requirements for this assignment.

**Just One File Please!**

While this will likely change with the very next assignment,
for this assignment, I want you to put all of your code in a single Java file
called **GameApp.java**. It is a common misconception that each Java source
file can contain only one class. This has never been true. It used to be the
case that there could be only one **public** class in each file, but even this
requirement doesn’t hold anymore under certain conditions. Nonetheless, we will
adhere to that for this project.

There are several reasons for doing it this way this time that will not apply
later. First, I want you to have a constant sense of how much code that you’re
writing over time for this first assignment. My example version of this project
was about 550 lines of java. I want you to keep that in mind. If you’ve written
2000 lines of java, you’re overdoing it. If you haven’t written 100 lines
before we get to the end of the week, you are very far behind. Second, adding
more files adds cognitive load that is more easily managed once you have the
structure of the program in your mind. I often start small projects this way
and it can save you time at first. Part of the reason for this is that deleting
classes that you don’t want doesn’t involve removing files. Finally, you may be
doing peer review of the work of others and this will help to speed that up for
you and your class mates.

_Constants and Static Methods_

Create final static constants in your Game class for anything that needs to be
easily changed, for example, the GAME\_HEIGHT and GAME\_WIDTH which should
initially be 800 and 400 respectively.

_Screen Origin and Coordinate System_

The origin, (0,0), of the screen is located, by default, in the upper left-hand
corner with positive values increasing to the right and down. We will invert
this by scaling the main pane by (-1). This will also require us to scale all
text by (-1) to avoid mirroring. I suggest that you use a GameText class to
achieve this easily.

**Game** Structure

The Game class provides the model for our game. It manages the changing state
of our game as we interact with it. Your Game must declare and initialize all
other game objects, manage the initialization of the game, determine when the
game is won or lost, and create all of the objects in the game world by placing
them in the scene graph. Your Game must have an init() method that is distinct
from the constructor. The init() method is invoked whenever a new game must be
played. It is perfectly reasonable to simply recreate all of the game objects
in this init() method. The init() method creates all of the new state of the
world including the positioning of each of the game objects. Don’t forget to
clear all children out of the Pane before initializing new objects.

As we have seen from the Game class structure, GameWorld has an update() method
that is called to update the state of the game. In this method you need to move
your helicopter and check the win/lose status of the game.

If the game as reached either a win or lose condition, the game should invoke
a _modal dialog box_ as shown in the demo video to offer to quit or replay.

You may create helper methods as necessary but they should not be public unless
required. Later we may move these methods to a separate class,

## OOP concerns for this Project

### _**Private Data and Setters and Getters**_

In this course, all mutable, i.e., settable, fields must be private. This means
that you may need setters and getters to obtain the value of this data
externally or to set the value of this data. You do not always need setters or
getters, however, and it’s important to try to think in terms of behaviors.
As discussed in class, you should not assume that it’s best to just add setters
and getters for each variable. This is unnecessary and is reasonably equivalent
to just making the data public. Unnecessary setters and getters indicate lower
quality code and may negatively impact your grade.

## Coding STandards

Many of the required coding standards for this project have been defined
throughout this document. In addition, you must adhere to the following.
Note, these are only starter guidelines and you should have learned these in
your previous courses. You will be learning the basics of Clean Coding in this
course and your grade will, in part, reflect the degree to which you adopt
those standards.

1. Class names always start with an upper-case letter

2. Variable names always start with a lower-case letter

3. Non-Constant identifiers use camel case

4. Constant identifiers use upper snake case

5. All code is neat and properly indented

6. You are restricted to an **80-character width**

    1. I want you to break habits that you may have developed of writing very
    long lines of Java code. You must learn to limit your width to aid
    readability.

    2. Java allows you to break lines in places you might not have thought
    about. Learn to structure your code more vertically with carefully placed
    line breaks that do not change the semantics of the Java language.

    3. The reason that it’s so important for this first project is that it
    makes peer review and grading easier. Lines longer than 80 characters will
    break in speed-grader making your code less readable.

    4. _**You WILL lose points for not adhering to this requirement.**_

In the clean code discussion, the authors will warn you that commenting is a
code smell. I’m quite sure that your other instructors have told you to comment
excessively. We will be focusing in this course on writing clean and
self-documenting code. However, this is not always possible and, where
necessary, you must communicate your intent with comments.

### _**Meta-Comments**_

For this submission I definitely want you to leave meta-comments.
These are communications to your instructor about what your intent and choices
were. Tell me about your thought process while writing your code. This should
be a natural part of your coding. If you practice this while you’re coding,
then you will learn to develop text that can go in documentation at a later
time. For future versions of the project, you will most likely have to submit
a writeup that includes much of this discussion. You definitely want to
practice communicating about your design process now!

## **Submission**

Submission standards are changing for this semester. For now, just get started
with your coding, I will discuss submission mechanisms in class. In short, you
will be submitting your work, in progress, every week on Monday. You will be
expected to show constant improvement in your code over the remainder of the
term and you will be expected to meet specific milestones. This approach is
intended to keep you on track while allowing you some flexibility. If you miss
too many milestones then it is possible to fail the project.
