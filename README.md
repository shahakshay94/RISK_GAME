# RISK_GAME
This is a Risk Board Game app made in Java. 

SOEN 6441 APP (Concordia University)

The app is purely based on java with no database being used. 
The following are the features of the game :
 1. Single Game Mode
 2. Tournament Mode
 3. Create your own map
 4. Edit a map
 5. Load a saved game
 
 The following is the information of the Player Settings :
  There are 5 Players:
     i. Human
     ii. Aggressive Bot
     iii. Benevolent Bot
     iv. Random Bot
     v. Cheater Bot
  
 Important things to know before playing the game :
 1. You must have atleast one player as Human type in Single Game mode
 2. The MAP which is being loaded must follow the correct syntax upto the mark, otherwise system will exit if the
    map is invalid.
 3. You can download different maps from  this link. http://www.windowsgames.co.uk/conquest_maps.html
 4. For creating your own map file, either create it from the system all follow the syntax of it which can be found over here
    http://www.windowsgames.co.uk/conquest_create.html
 5. Tournament Mode has some bugs so for some combinations of the player it may take huge amount of time to complete the game.
 
 How to launch the game ?
 
 Run the Launcher class and you are good to go.
 
 Scope of improvements :
 1. Proper implementation of MVC for some view classes.(Launcher, etc).
 2. Player class has all the methods and API layer combined, need to differentiate those. Player class should only contain       the data memebers which are necessary for the player.
 3. API layer should have all the methods and rules of the game and all the validations.
 4. Need to add playerManager class which handles all the functions regarding any player modifications in the game.
 5. Correct way to implement Strategy Pattern. Strategy for each player should be set when players are intialized instead of     assigning strategy to the player whose turn is it play.
 6. Real time update of the GameView class when playing the tournament instead of logging all of it in a txt file.
 7. More elegant unit test cases for the tournament mode.
 8. Much more dynamic structure and modular.
 9. Observer pattern can be implemented in much nicer way.
 
 
 Referred from the following repo https://github.com/tlmader/risk
 
 Please feel free to create a PR for any improvements other then mentioned above. This is my first attempt at Java programming, learned a great lot of things. But there is still chance of improvement. 
 
