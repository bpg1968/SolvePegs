/*
 * Copyright 2017 Brian P. Giroux
 *
 * This file is part of the SolvePegs Project.
 *
 * The SolvePegs Project is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * The SolvePegs Project is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the SolvePegs Project.  If not, see
 * <http://www.gnu.org/licenses/>.
 *
 */
/**
 * <p>
 * This package uses a brute force approach to find solutions to the 15 hole
 * triangular variant of Peg Solitaire.
 * </p>
 *
 * <p>
 * To find a solution, a tree is constructed with the initial board state at the
 * root node. We then find all possible legal moves from that state, creating
 * child nodes for each board state that results from applying a legal move to
 * the state. We continue applying moves recursively until we reach a board
 * state with no possible moves.
 * </p>
 *
 * <p>
 * After the tree of all possible moves is created, we walk the tree searching
 * for nodes that satisfy the winning board state. From every such node, we
 * trace a path back to the root node (the initial board state).
 * <p>
 *
 * <h3>Licence</h3>
 *
 * <p>
 * This package is part of the SolvePegs Project.
 * </p>
 *
 * <p>
 * The SolvePegs Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * </p>
 *
 * <p>
 * The SolvePegs Project is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * </p>
 *
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * the SolvePegs Project. If not, see &lt;<a href=
 * "http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>&gt;.
 * </p>
 *
 * <p>
 * Even though the GNU General Public License does NOT require you to send your
 * modifications back to the author, it is considered "good form" to do so, as
 * this allows your modifications to be incorporated into future versions of the
 * program, allowing others to benefit from them.
 * </p>
 *
 * <p>
 * Copyright &copy; 2017 Brian P. Giroux
 * </p>
 *
 * @author Brian P. Giroux
 * @version 1.0
 *
 */
package ca.bgiroux.bpg.solonoble.triangular;