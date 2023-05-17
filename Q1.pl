% How to run this code:

% Call the solve/5 predicate and pass to it the inputs
% solve(M, N, Bomb1, Bomb2, Dominos)
% M = Rows, N = Columns
% Bomb1/Bomb2 = set of (x, y) for its placement 
% and Dominos is the ganerated output.

% Possible testcases: 
% solve(3,3,(1,3),(2,1),Dominos).
% solve(2,4,(2,2),(2,3),Dominos).

% tell('output.txt'),
% solve(3, 3, (1,3), (2,1), Dominos),
% maplist(writeln, Dominos),
% told.
% ==========================================================================
% The code:
% Check if the cell is valid and empty
valid_cell(Board, X, Y) :-
   member((X, Y), Board).

% Check if the domino can be placed horizontally
h_placement(Board, X, Y) :-
  valid_cell(Board, X, Y),
    Y2 is Y+1,
  valid_cell(Board, X, Y2).
% Check if the domino can be placed vertically

v_placement(Board, X, Y) :-
  valid_cell(Board, X, Y),
        X2 is X+1,
  valid_cell(Board, X2, Y).

generate_positions(_, M, Y, M, Y, []) :- !.
generate_positions(Board, X, Y, M, N, Positions) :-
  (X > M ; Y > N) ->
    Positions = []
  ;
  (Y < N, h_placement(Board, X, Y) ->
    Y2 is Y+1,
    Positions1 = [(X, Y, X, Y2, h)]
  ;
    Positions1 = []
  ),
  (X < M, v_placement(Board, X, Y) ->
    X2 is X+1,
    Positions2 = [(X, Y, X2, Y, v)]
  ;
    Positions2 = []
  ),
  (Y < N -> Y1 is Y + 1, X1 = X; Y1 = 1, X1 is X + 1),
  generate_positions(Board, X1, Y1, M, N, RestPositions),
  append(Positions1, Positions2, Positions3),
  append(Positions3, RestPositions, Positions).

% Place dominos horizontally and return resulting board
place_dominos_h(Board, [(X, Y, X, Y1, h)|RestPositions], [(X, Y, X, Y1, h)|RestDominos], ResultBoard) :-
  select((X, Y), Board, Board1),
  select((X, Y1), Board1, Board2),
  place_dominos_h(Board2, RestPositions, RestDominos, ResultBoard).
place_dominos_h(Board, [_|RestPositions], Dominos, ResultBoard) :-
  place_dominos_h(Board, RestPositions, Dominos, ResultBoard).
place_dominos_h(ResultBoard, [], [],ResultBoard).

% Place dominos vertically
place_dominos_v(Board, [(X, Y, X1, Y, v)|RestPositions], [(X, Y, X1, Y, v)|RestDominos], ResultBoard) :-
  select((X, Y), Board, Board1),
  select((X1, Y), Board1, Board2),
  place_dominos_v(Board2, RestPositions, RestDominos, ResultBoard).
place_dominos_v(Board, [_|RestPositions], Dominos, ResultBoard) :-
  place_dominos_v(Board, RestPositions, Dominos, ResultBoard).
place_dominos_v(ResultBoard, [], [], ResultBoard).

% Place dominos using DFS
place_dominos(Board, Positions, DominosList) :-
  findall(Dominos, place_dominos_helper(Board, Positions, Dominos), DominosList).

place_dominos_helper(Board, Positions, Dominos) :-
  place_dominos_h(Board, Positions, Dominos1, ResultBoard),
  place_dominos_v(ResultBoard, Positions, Dominos2, ResultBoard2),
  append(Dominos1, Dominos2, Dominos),
  \+ h_placement(ResultBoard2, _, _),
  \+ v_placement(ResultBoard2, _, _).
  
% Solving the problem 

solve(M, N, Bomb1, Bomb2, Dominos) :- 
  numlist(1, M, Rows),
  numlist(1, N, Columns),
  findall((X, Y), (member(X, Rows), member(Y, Columns)), Board),
  delete(Board, Bomb1, Board1),
  delete(Board1, Bomb2, Board2),
  generate_positions(Board2, 1, 1, M, N, Positions),
  place_dominos(Board2, Positions, Dominos).