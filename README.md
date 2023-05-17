The Domino Placement Solver is a Prolog project that aims to solve the problem of placing domino pieces on a board of given dimensions. The project offers both informed and uninformed search algorithms to find valid placements for dominos on the board.

The project consists of the following components:

1-Board Creation: The project takes the dimensions of the board as a parameter and generates the board accordingly. The board is represented as a grid of squares.

2-Valid Placement: The project ensures that dominos are placed in valid positions on the board. A valid placement means that the domino occupies two adjacent squares either horizontally or vertically and does not overlap with any "bomb" squares.

3-Uninformed Search: The project utilizes an uninformed search algorithm, specifically depth-first search, to explore all possible placements of dominos on the board. It generates and displays all valid placement configurations, considering different numbers of dominos.

4-Informed Search: The project also provides an informed search algorithm to optimize the placement of dominos. It focuses on finding configurations that maximize the number of dominos placed on the board. Only the cases with the maximum possible number of dominos are displayed.

5-Graphical User Interface (GUI): The project includes a Java-based GUI that interacts with the Prolog code. The GUI prompts the user to enter a query, including the board dimensions and specific domino placement constraints. It also allows the user to select either the informed or uninformed search approach. The results of the search are displayed within the GUI.

Example Query:
solve(3,3,(1,3),(2,1),Dominos)

Overall, the Domino Placement Solver project provides a comprehensive solution for placing dominos on a board, showcasing different search algorithms and enabling users to explore various valid placement configurations based on their specific requirements.
