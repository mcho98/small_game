# My Personal Project (Title Pending)

## A text-based RPG

For my project, I would like to make a **text-based RPG** in which you'll be able to create a character with user-specified attributes in different categories. The character will then try to pass through randomly selected scenarios from a list of possible scenarios. The pass or fail will be dictated by the character's stats. Passing will reward items and/or stats that can help a character whereas failing will make the character take damage. How much damage will depend on the scenario. the game ends when the damage taken exceeds the health fo the character. 

The game will be open to anyone but I'm hoping that it will be enjoyable to fellow students who like RPG videos games or D&D. I've liked RPGs since my childhood so I would like to try and make one. I want to encorporate modularity so that my program can work as a framework for many different settings and scenarios. 

## User Stories

As a user, I am able to:
- create a character with certain attributes.
- list possible scenarios that can happen in-game
- add multiple scenarios to a list of possible scenarios that can happen in-game.
- remove multiple scenarios to a list of possible scenarios that can happen in-game.
- list items from an inventory
- add items to an inventory
- remove items from an inventory
- add stats to my character
- list my character's stats
- save my character data - such as stats, level, and inventory -  (if I choose to do so)
- load my character data - such as stats, level, and inventory -  (if I choose to do so)

As a user I still want to be able to:
- have multiple characters independent to each other and list them.

## Instructions for Grader
# Adding X to Y
To generate the first required action related to adding Xs to a Y
- Run Main
- Hit the "Scenarios" button
- In the white panel is a listing of Scenarios
- There are 7 text fields in the light gray panel
- Type characters in the first text field and numbers in the next 6
- Click the "Add Scenarios" button when ready. The white panel should be updated to now include the new scenario

To generate the second required action related to adding Xs to a Y
- Run Main
- Hit the "Scenarios" button
- In the white panel is a listing of Scenarios
- There is 1 text field in the dark panel next to a button labelled "Remove Scenario"
- Type a number from the listing into the sole text field
- Click the "Remove Scenario" button when ready. The white panel should be updated to not have the removed scenario

# Visual Component
To find the visual component, simply run Main.
The visual component is a picture background only present in the start menu

# Saving
To save the state of my application, 
- Run Main
- Press "Start"
- enter any string of characters in the middle text field and click "Create New Character"
- Press "Save" to save the state of the game
- You can also press "Continue" to progress the game and save anytime before the game ending using "Save"

# Loading
To load the state of my application,
- Run Main
- Press "Load" to load the state of the application

