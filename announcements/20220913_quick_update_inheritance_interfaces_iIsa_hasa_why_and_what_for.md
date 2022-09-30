[Daryl Posnett](https://csus.instructure.com/courses/93920/users/43499)

I wanted to briefly cover some of this today with respect the project, however, we will spend more time on this on Thursday. With respect to the tool hierarchy, I probably shouldn't have been quite so flippant about the choices that I made as I don't want you to get the wrong idea. The chosen hierarchy has some very good reasons for its selection. My reference to "the perfect being the enemy of the good" can apply to almost any _decomposition_ of code. This is important enough of an idea that it even has a name: "The Tyranny of the Dominant Decomposition." The idea is that any choices that favor one dimension will have consequence for others. In some sense, it can be thought of as an opportunity cost of whatever choices we make in software structure. 

_**Some things to think about:**_

What do all three tools, ShapeTool, ColorTool, and ActionTool have in common? What behavior do they all have that would suggest that it's a good idea to derive them from the same base class? Second, think carefully about what it means to _**be**_ a StackPane vs what it means to be placed in one? Finally, even though ColorPane doesn't need to be a StackPane, per se, think about what advantage you would gain making it some other kind of LayoutPane when it has to share behavior with the other tools?

This announcement is closed for comments