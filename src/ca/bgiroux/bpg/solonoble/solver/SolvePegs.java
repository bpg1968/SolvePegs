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
package ca.bgiroux.bpg.solonoble.solver;

import ca.bgiroux.bpg.solonoble.triangular.BoardState;
import ca.bgiroux.bpg.solonoble.triangular.Tree;

/**
 *
 * A class containing the main method.
 *
 * @author Brian P. Giroux
 * @version 1.0
 *
 */
public class SolvePegs {
  /**
   *
   * The main method.
   *
   * @param args
   *          command line arguments.
   */
  public static void main(String[] args) {
    Tree t = new Tree(new BoardState());
    System.out.println(t.findWinners());
  }
}