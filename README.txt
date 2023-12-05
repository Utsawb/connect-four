Demonstration Video: https://youtube.com/live/qqxqIGIhoJ0
(I added Launcher.java after the fact to get a jar file built so the video doesn't show it)

To play the game you can run the connect_four.jar file directly
or you can build the project in your IDE, make sure it has maven
support.

Interactables:
    Board :
        OnHover : Highlights the column of interest
        OnClick : Places a piece in the column of interest
    Reset Button :
        OnClick : Resets the board
    Save Button :
        OnClick : Saves the current game state
    Load Button :
        OnClick : Loads the last saved game state
    AI Button :
        OnClick : Toggles between a random AI and a smart AI