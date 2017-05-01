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

import java.util.Arrays;

/**
 * <p>
 * At any given time in the game, a board may be in any one of 32768
 * (2<sup>15</sup>) states (although some of those states may be unreachable).
 * </p>
 *
 * <p>
 * The puzzle board is a triangular board with rows of peg holes. The bottom row
 * has five (5) peg holes, the second row from the bottom has four (4) peg
 * holes, the next row has three (3) peg holes, then two (2) peg holes and
 * finally a single peg hole a the top.
 * </p>
 *
 * <p>
 * The peg holes are numbered as follows:
 *
 * <pre>
 *         (14)
 *
 *       (13)(12)
 *
 *     (11)(10)( 9)
 *
 *   ( 8)( 7)( 6)( 5)
 *
 * ( 4)( 3)( 2)( 1)( 0)
 * </pre>
 *
 * although, for convenience, the peg holes also have single character names as
 * follows:
 *
 * <pre>
 *     E
 *    D C
 *   B A 9
 *  8 7 6 5
 * 4 3 2 1 0
 * </pre>
 * </p>
 *
 * @author Brian P. Giroux
 * @version 1.0
 *
 */
public class BoardState {
  /**
   * The total number of peg holes in a board.
   */
  public static final int TOTAL_PEG_HOLES = 15;
  /**
   * It is common to start the puzzle with the top hole (hole E according to the
   * above diagram) empty; this constant represents that state.
   */
  public static final int COMMON_START_STATE = 0b0_11_111_1111_11111;

  /*
   * This array contains the status of each peg hole on the board: true means
   * the peg hole is occupied by a peg, false means it is unoccupied.
   */
  private boolean pegs[];

  /**
   * Same as {@code new BoardState(0)}.
   */
  public BoardState() {
    this(0);
  }

  /**
   * <p>
   * Create a {@code BoardState} from an integer where each bit of the integer
   * represents a peg position. High order bits (above 14) are ignored.
   * </p>
   *
   * <p>
   * Since a {@code BoardState} with an integer value of 0 would represent an
   * empty board, and since we would never want to initialise nor transition to
   * an empty board, the integer value of 0 is used to initialise the board to
   * its default start state (ie, all pegs holes filled except the top corner
   * peg).
   * </p>
   *
   * @param state
   *          the integer number representing this board's state.
   */
  public BoardState(int state) {
    this.pegs = new boolean[TOTAL_PEG_HOLES];
    int n = (state == 0 ? COMMON_START_STATE : state);
    for (int i = 0; i < TOTAL_PEG_HOLES; i++)
      if ((n & (1L << i)) == 0) // check if bit at position i is set
        this.pegs[i] = false;
      else
        this.pegs[i] = true;
  }

  /**
   * Make a (defensive) copy of a {@code BoardState}.
   *
   * @param state
   *          the state to copy.
   */
  public BoardState(BoardState state) {
    this.pegs = new boolean[TOTAL_PEG_HOLES];
    for (int i = 0; i < TOTAL_PEG_HOLES; i++)
      this.pegs[i] = state.pegs[i];
  }

  /**
   * Create a new {@code BoardState} by applying a move to some start state.
   *
   * @param startState
   *          the state of the board before the move is applied.
   * @param move
   *          the move to apply to the start state.
   */
  public BoardState(BoardState startState, Move move) {
    this(startState);
    applyMove(move);
  }

  /**
   * Determine if the peg hole at the given position is occupied.
   *
   * @param position
   *          the position.
   * @return {@code true} if the peg hole at this {@code position} is occupied
   *         by a peg, {@code false} if the hole is empty.
   */
  public boolean getPeg(int position) {
    return this.pegs[position];
  }

  /**
   * <p>
   * Count the number of pegs remaining on the board.
   * </p>
   *
   * <p>
   * Since, during normal game play, there is always a minimum of 1 peg
   * remaining on the board, we can use a peg count of 0 to indicate some
   * special condition. We return 0 if the last peg on the board is in the
   * centre hole on the bottom row.
   * </p>
   *
   * @return the number of pegs remaining on the board or 0 if there is only 1
   *         peg and it is in the centre hole of the bottom row.
   */
  public int countPegs() {
    int pegCount = 0;
    for (boolean peg : this.pegs)
      if (peg)
        pegCount++;
    if (pegCount == 1 && this.pegs[2])
      pegCount = 0;
    return pegCount;
  }

  /**
   * <p>
   * Determine if a given move is legal. For a move to be legal, we must have a
   * peg in the start position (this is the peg we will be moving), the end
   * position must be an empty hole (this is the destination of the peg found in
   * the start position) and the peg hole between the start and end positions
   * (the jump position) must contain a peg.
   * </p>
   *
   * <p>
   * For example, if <tt>S</tt> is the start position <tt>E</tt> is the end
   * position and <tt>J</tt> is the jump position, and <tt>O</tt> represents an
   * empty peg hole and <tt>0</tt> represents an occupied peg hole, then the
   * following table illustrates a legal move and some moves that are not legal:
   * </p>
   *
   * <pre>
   *           S J E
   *     legal 0 0 O
   * not legal 0 O O
   * not legal 0 0 0
   * </pre>
   *
   * @param move
   *          the move.
   * @return {@code true} if the given move is legal; {@code false} otherwise.
   */
  public boolean isLegalMove(Move move) {
    return this.pegs[move.getStartPosition()] && this.pegs[move.getJumpPosition()] && !this.pegs[move.getEndPosition()];
  }

  /*
   * Apply the given move. The given move has 3 values: the start position, the
   * end position and the jump position (the position between the start and the
   * end positions. Applying a move is just a matter of toggling all three
   * position: we move the peg from the start position to the empty end position
   * and the peg that must be in the jump position for the move to be legal gets
   * removed.
   *
   * <pre> S J E Before applying move: 0 0 O After applying move: O O 0 </pre>
   */
  private void applyMove(Move move) {
    toggle(move.getStartPosition());
    toggle(move.getJumpPosition());
    toggle(move.getEndPosition());
  }

  /*
   * Toggle the value of a peg hole: if the peg hole is occupied, toggle it to
   * be unoccupied; if the peg hole is unoccupied, toggle it to be occupied.
   */
  private void toggle(int position) {
    this.pegs[position] = (this.pegs[position] ? false : true);
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "BoardState [pegs=" + Arrays.toString(this.pegs) + ", pegCount()=" + countPegs() + "]";
  }
}