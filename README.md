# The SolvePegs Project

Peg Solitaire (sometimes called the Peg Board Puzzle or Peg Board Game) is a
one player board game involving movement of pegs on a board with holes. This
project attempts to find solutions to this puzzle.

## The `triangular` package
The `triangular` package uses a brute force approach to find solutions to the
15 hole triangular variant of Peg Solitaire.

To find a solution, a tree is constructed with the initial board state at the
root node. We then find all possible legal moves from that state, creating
child nodes for each board state that results from applying a legal move to
the state. We continue applying moves recursively until we reach a board
state with no possible moves.

After the tree of all possible moves is created, we walk the tree searching
for nodes that satisfy the winning board state. From every such node, we
trace a path back to the root node (the initial board state).


## Running the solver
For your convenience, a Java jar file is included in this project.
To execute the jar file, change to the directory containing the jar file and
type:

`java -jar SolvePegs.jar`

After a few seconds, 16,128 solutions will be displayed on the screen (one
solution per line). It may be useful to redirect the output to a text file:

`java -jar SolvePegs.jar > solutions.txt`

and view the text file with a standard text viewer or editor.

Depending on your operating system and its configuration, it may be possible
to run the jar file directly (for example, by "double clicking" on it).


## Producing your own jar file
To produce your own jar file:

```
$ cd SolvePegs/bin
$ jar cvfe ../jar/SolvePegs.jar \
  ca.bgiroux.bpg.solonoble.solver.SolvePegs \
  ca/bgiroux/bpg/solonoble/package-info.class \
  ca/bgiroux/bpg/solonoble/triangular/*.class \
  ca/bgiroux/bpg/solonoble/solver/*.class
```


## Licence

The SolvePegs Project is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by the Free
Software Foundation, either version 3 of the License, or (at your option) any
later version.

The SolvePegs Project is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
details.

You should have received a copy of the GNU General Public License along with
the SolvePegs Project. If not, see <http://www.gnu.org/licenses/>.

Even though the GNU General Public License does NOT require you to send your
modifications back to the author, it is considered "good form" to do so, as
this allows your modifications to be incorporated into future versions of the
program, allowing others to benefit from them.

Copyright &copy; 2017 Brian P. Giroux
