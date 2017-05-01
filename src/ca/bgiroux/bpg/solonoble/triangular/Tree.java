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

import java.util.LinkedList;

/**
 * Container for the root node and methods for accessing the nodes of the Tree.
 *
 * @author Brian P. Giroux
 * @version 1.0
 *
 */
public class Tree {
  private Node root;

  /**
   * Construct a new Tree who's root node contains the given BoardState.
   *
   * @param initialState
   *          the BoardState for the Tree's root node.
   */
  public Tree(BoardState initialState) {
    this.root = new Node(initialState);
  }

  /**
   * Walk the tree finding sequences of moves leading to a winning
   * {@code BoardState}.
   *
   * @return all sequences of moves (one sequence per line) leading from the
   *         initial {@code BoardState} to a winning {@code BoardState}.
   */
  public String findWinners() {
    return this.root.findWinners();
  }

  /**
   * <p>
   * Compute the height of the tree.
   * </p>
   *
   * <p>
   * The height of a tree is the height of its root node; the height of the root
   * node is the number of edges on the path from the root node to the node
   * farthest from the root (the deepest node).
   * </p>
   *
   * <p>
   * See <a href=
   * "https://en.wikipedia.org/wiki/Tree_(data_structure)#Terminology">https://en.wikipedia.org/wiki/Tree_(data_structure)#Terminology</a>
   * for more details.
   * </p>
   *
   * @return the height of the tree.
   */
  public int getHeight() {
    return this.root.getHeight();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return this.root.toString();
  }

  /**
   * The nodes of the tree.
   *
   * @author Brian P. Giroux
   * @version 1.0
   *
   */
  public static class Node {
    /*
     * This node's payload.
     */
    private BoardState data;
    /*
     * Points back to this node's parent node. If this node is the root node,
     * the parent node is NULL.
     */
    private Node parent;
    /*
     * The move that caused us to transition from the parent node to this node.
     * If this node is the root node, the move is NULL.
     *
     * The value of this node's data is new BoardState(parent, move);
     */
    private Move move;
    private LinkedList<Node> children;

    /**
     * Construct a {@code Node} with a given {@code BoardState} and construct
     * all this node's children.
     *
     * @param state
     *          the state.
     */
    private Node(BoardState state) {
      this.children = new LinkedList<Node>();
      this.data = state;
      makeChildren();
    }

    /*
     * Construct a Node with a given BoardState, parent and move that caused us
     * to transition from the parent node to the node being constructed. We
     * construct all this node's children by calling another constructor.
     *
     * @param state the state.
     *
     * @param parent this node's parent node.
     *
     * @param move the move that caused us to transition from the parent node
     * the node being constructed.
     */
    private Node(BoardState state, Node parent, Move move) {
      this(state);
      this.parent = parent;
      this.move = move;
    }

    /*
     * Creates all of the current node's children by applying all legal moves to
     * the current node's BoardState.
     */
    private void makeChildren() {
      for (Move move : Move.moves)
        if (this.data.isLegalMove(move))
          this.children.add(new Node(new BoardState(this.data, move), this, move));
    }

    /*
     * Walk down each node finding sequences of moves leading to a winning
     * BoardState.
     *
     * @return all sequences of moves (one sequence per line) leading from the
     * initial BoardState (contained in the root node) to a winning BoardState.
     */
    private String findWinners() {
      String result = "";
      if (this.data.countPegs() == 0)
        result += traceBack() + "\n";

      if (hasChildren())
        for (Node child : this.children)
          result += child.findWinners();
      return result;
    }

    /*
     * Trace a route back to the root node from this node by following each
     * node's parent field along the way. Records each move that gets us from
     * the parent node to the current node; when we get back to the root node,
     * the String returned contains a sequence of moves that gets us from the
     * root node to this node.
     *
     * @return a sequence of moves that gets us from the root node to this node.
     */
    private String traceBack() {
      String result = "";

      if (this.parent != null)
        result += this.parent.traceBack() + this.move.toString();

      return result;
    }

    /*
     * Recursively compute the height of this node.
     *
     * The height of a node is the length of the longest downward path to a leaf
     * (a node without children) from that node. The height of the root is the
     * height of the tree.
     *
     * The height of this node is the height of this node's highest child node
     * plus 1 (itself). The height of a leaf node is 1 (the height of its
     * highest child is 0 since there are no children plus 1).
     *
     * @return the height of this node.
     *
     * @see https://en.wikipedia.org/wiki/Tree_(data_structure)#Terminology
     */
    private int getHeight() {
      int height = 0;
      if (hasChildren()) {
        for (Node child : this.children) {
          int childHeight = child.getHeight();
          if (childHeight > height)
            height = childHeight;
        }
        height++; // count self
      }
      return height;
    }

    /*
     * Check to see if this node has at least 1 child.
     *
     * @return {@code true} if this node has 1 or more children, {@code false}
     * otherwise.
     */
    private boolean hasChildren() {
      return this.children.size() > 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
      String result = "Node: ";
      result += this.data + "\n";
      for (Node child : this.children)
        result += child;
      return result;
    }
  }
}