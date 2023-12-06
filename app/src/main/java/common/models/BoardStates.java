package common.models;

import java.util.Stack;

public class BoardStates {
    private final Stack<Board> boardStack;

    private BoardStates(Stack<Board> newBoardStack) {
        this.boardStack = newBoardStack;
    }
    public BoardStates() {
        this.boardStack = new Stack<>();
    }
    public BoardStates push(Board board) {
        Stack<Board> newBoardStack = new Stack<>();
        newBoardStack.addAll(boardStack);
        newBoardStack.push(board);
        return new BoardStates(newBoardStack);
    }

    public Board peek() {
        return boardStack.peek();
    }

    public static BoardStates empty() {
        return new BoardStates(new Stack<>());
    }
}