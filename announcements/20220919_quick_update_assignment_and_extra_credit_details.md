[Daryl Posnett](https://csus.instructure.com/courses/93920/users/43499)

## Quick Update :: Assignment and Extra Credit Details

All Sections

Several of you have sent me messages regarding some common questions:

**Q1: Do I need to resubmit the file later if I submit the extra credit today?**

A: Yes. All final submissions must be complete. So you will need to submit all files for your final. However, the question betrays an incorrect thought process. You will almost certainly need to do some refactoring of your early code submission. This is the reason for the extra credit in the first place. I have yet to see a single early submission that doesn't need to be refactored, assuming that is, that the submitter wants all of the points. 

Also, please note, the final video is up to 2 minutes in length and _**MUST**_ be narrated. The extra credit video is shorter and does not need to be narrated. 

**Q2: Do I need to rename files submitted for extra credit?**

A: No, just use the specified name and don't worry about Canvas appending digits to the end of the filename. We know that it does this, we wish that it didn't, but it's not a big deal as long as the file that you submit has the correct name. Related, this is fully specified in the assignment. If I wanted you to use a different name I would have stated that.

**Q3: I made my current tool reference static and public, is this ok?**

A: No! Not for the final submission at any rate. It's fine for the extra credit, we aren't going to pick your extra credit apart, I just want you to get things working earlier rather than later. All data must be private, period! This is an unrealistic aim for real programming, but it is strict in this course. I will talk about this, specifically, in class on Tuesday and you may turn in your extra credit this way, but you must refactor this for full points on your final submission. _**Even one public member variable will prevent you from earning an A on the final submission.**_ 

**Q4: Can I make the color constants public?**

A: No! There is no reason for this. Your main file must instantiate the ColorTools and so will be able to pass in the Color constants. There is no other _**user defined**_ container class that holds all of the ColorTools, _e.g., a class extending VBox_, that is an appropriate home for the color array/collection. The constants in Color are already public, so there is no reason to redefine them. If you put them into an array, as was done in the first assignment, then that array is a variable and so cannot be public as per Q3.

This announcement is closed for comments