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
package ca.bgiroux.bpg.solonoble.triangular;

/**
 * <p>
 * A move consists of moving a peg from its position (the start position) to an
 * empty hole (the end position) by jumping over exactly one (1)
 * <strong>occupied</strong> hole (the jump position) in a straight line.
 * </p>
 *
 * <p>
 * {@link BoardState} details the board hole position numbers.
 * </p>
 *
 * @author Brian P. Giroux
 * @version 1.0
 * @see BoardState
 */

public class Move {
  private int startPosition;
  private int endPosition;
  /*
   * The position of the hole between the start position and the end position
   * (the hole that gets jumped).
   */
  private int jumpPosition;

  /**
   * There are only 36 possible moves; these are the 36 moves.
   */
  public static final Move[] moves = {

      new Move(0, 1, 2), new Move(0, 5, 9), new Move(1, 2, 3), new Move(1, 6, 10), new Move(2, 1, 0), new Move(2, 6, 9),
      new Move(2, 7, 11), new Move(2, 3, 4), new Move(3, 2, 1), new Move(3, 7, 10), new Move(4, 3, 2),
      new Move(4, 8, 11), new Move(5, 6, 7), new Move(5, 9, 12), new Move(6, 7, 8), new Move(6, 10, 13),
      new Move(7, 6, 5), new Move(7, 10, 12), new Move(8, 7, 6), new Move(8, 11, 13), new Move(9, 5, 0),
      new Move(9, 6, 2), new Move(9, 10, 11), new Move(9, 12, 14), new Move(10, 6, 1), new Move(10, 7, 3),
      new Move(11, 7, 2), new Move(11, 8, 4), new Move(11, 10, 9), new Move(11, 13, 14), new Move(12, 9, 5),
      new Move(12, 10, 7), new Move(13, 10, 6), new Move(13, 11, 8), new Move(14, 12, 9), new Move(14, 13, 11) };

  /*
   * Create a move with a start position, an end position and a position between
   * the start and end positions that we will call the jump position.
   *
   * Since there are only 36 possible moves, there is no need to create a new
   * move every time we need one; instead, we create all 36 moves and store them
   * in an array of moves.
   *
   * @param startPosition the current position of the peg that will be moved.
   *
   * @param jumpPosition the position between the start and end positions.
   *
   * @param endPosition the moved peg's destination position.
   */
  private Move(int startPosition, int jumpPosition, int endPosition) {
    this.startPosition = startPosition;
    this.jumpPosition = jumpPosition;
    this.endPosition = endPosition;
  }

  /**
   * Returns the current position of the peg that will be moved.
   *
   * @return the current position of the peg that will be moved.
   */
  public int getStartPosition() {
    return this.startPosition;
  }

  /**
   * Returns the moved peg's destination position for this move.
   *
   * @return the moved peg's destination position.
   */
  public int getEndPosition() {
    return this.endPosition;
  }

  /**
   * Returns the position between the start position and the end position for
   * this move.
   *
   * @return the position between the start and end positions.
   */
  public int getJumpPosition() {
    return this.jumpPosition;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "(" + positionName(this.startPosition) + "->" + positionName(this.endPosition) + ")";
  }

  /*
   * Translate a hole position number to a single character hole position name.
   *
   * The names of position numbers 0 to 9 are simply their position numbers;
   * position numbers 10, 11, 12, 13 and 14 have names 'A', 'B', 'C', 'D' and
   * 'E' respectively.
   *
   * For example, move "(9->E)" means to take the peg that is currently in
   * position 9 and move it to position 14.
   *
   * Using a single character name instead of the position number ensures that
   * the toString() method produces a string of predictable length, which can in
   * turn keep strings containing sequences of moves well aligned.
   *
   * @param position the hole position number.
   *
   * @return the hole position's single character name.
   */
  private char positionName(int position) {
    return (position < 10 ? (char) (position + '0') : (char) (position - 10 + 'A'));
  }
}
