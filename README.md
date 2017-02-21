# code-cadet-game
Fun multi-player command-line game using multi-threading

##SUMMARY
With this game, we want to look in a funny way at different aspects of a \<Code Cadet\>’s life. 
The game will present quizzes that will advance the career of whoever answers correctly. There will be events all type of events that affect the life of our players, such as “Portugal winning the WorldCup”, which increases all players’ happiness or “AI taking everybody’s job”, which represents a step back in everyone's’ career. From time to time there will be a JAVA wisdom quote provided by a sexy cow. Enjoy!

##GAME LOGIC (step by step)
- The game has one server and several players (max 4), connected by TCP protocol.
- The server executes all game operations.
- The game works with two applications: one for the server and one for the players.
- The game master executes server.jar which awaits for players to connect.
- When players connect to the server to start a new game, a welcome menu is presented with the list of commands available during the game.
- When a player starts the game, he/she is asked 3 questions:
  1. Username (two players cannot have the same username).
  2. How many players will be in this session (2-4 players).
  3. Fast or normal version (How many steps to the end of the game).
- The server waits those players to connect and starts the game automatically.
- Our cadets have to leave \<Academia de Código_\> and continue with their lives. The first thing to do is to get a job: the players that answer correctly our first question, get a job, the others continue the game as unemployed.
- The game will only continue until all players can answer. The players that failed the answer don’t get a job.
- The first player with the correct answer rolls the dice. 
- The game continues in a loop of: roll-dice - answer - roll-dice:
  - The winner is whoever reaches the end faster. To reach the end, the player needs to advance a finite number of steps.     Life events and decisions can move the player forward or backwards some steps. Three areas of life can be affected or       promoted: career, happiness and money.
  - When a player rolls the dice a choosable or non-choosable event comes up, both can be personal or collective:
       1. **Personal**: They can affect the life of the player that rolled the dice.
        1. _Choosable_: The player makes a choice that has an impact on his/her life.
        2. _Non-choosable_: The player doesn’t have any choice.
       2. **Collective**: They affect all players.
        1. _Choosable_: All players must choose. There are 3 kinds:
          1. Career: The first player who answers correctly earns points in this area.
          2. Money:The first player who picks the event earns or loses on the area depending on the               consequence of this selection.
          3. Happiness: Same as 2.
        2. _Non-choosable_: The event affects all players in the same manner.
- After each turn, the consequence of the choice or event is shown in the screen.
- After this, the position of all players is shown in the screen (how close or far they are from the finish line).
- From time to time a ascii cow shows up with a wisdom quote about java.
- The winner is whoever makes it to the finish line in first place.
