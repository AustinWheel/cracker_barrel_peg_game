# Solution Simulation for Cracker Barrel Peg Game
## Description
This is just a fun game simulation for the peg game shown below. The point of the game is that there is one open peg, and you jump pegs, eliminating the jumped peg. Your goal is to leave only a single peg behind. This project simulate the game board and then finds all the possible solutions from any starting point.

![image](https://github.com/user-attachments/assets/230c84e7-a58a-487f-8831-d363a3659c9e)

## Approach
Created a board to simulate the game, where each peg or hole is denoted by a `Node(name, x, y)` with coordinates as shown below. A node can be empty if there is no peg placed. We start by removing a peg from any of the starting holes, and then we play from there by expanding the search graph. We can identify the valid moves by looking to the upward and downward diagonals and then the left and right ones as well.
![image](https://github.com/user-attachments/assets/52480f42-120f-4b34-b94b-282096859337)

### Interesting Results

Below are some results I recieved from running, only from the starting position of A1:

Best score from ( A1 ): `14`
Possible perfect games: `29760 / 568630`

C1-->A1, 
C3-->C1, 
A1-->C3, 
D1-->B1, 
D4-->B2, 
E2-->C2, 
E3-->C3, 
B1-->D3, 
B2-->D4, 
E5-->C3, 
C3-->E3, 
E4-->E2, 
E1-->E3

Including any starting position there are over `7` million unique runs, and `400,000` 'perfect' games that are possible.
