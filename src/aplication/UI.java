package aplication;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

import boardgame.Piece;

import java.util.List;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void clearScreen() {
		System.out.print("\033[\033[2J");
		System.out.flush();
	}
	
	public static void printBoard(ChessPiece[][] chessPiece) {
		for (int i = 0; i < chessPiece.length; i++) {
			System.out.print((8-i) + " ");
			for (int j = 0; j < chessPiece.length; j++) {
				printPiece(chessPiece[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] chessPiece, boolean[][] possibleMoves) {
		for (int i = 0; i < chessPiece.length; i++) {
			System.out.print((8-i) + " ");
			for (int j = 0; j < chessPiece.length; j++) {
				printPiece(chessPiece[i][j],possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece, boolean backgroundHighlight) {
		
		//Background Print
		if(backgroundHighlight)
			System.out.print(ANSI_YELLOW_BACKGROUND);
		else
			System.out.print(ANSI_BLACK_BACKGROUND);
		
		if(piece == null) {
			System.out.print("-");
		}
		else {
			if(piece.getColor() == Color.RED)
				System.out.print(ANSI_RED + piece + ANSI_RESET);
			else
				System.out.print(ANSI_BLUE + piece + ANSI_RESET);
		}
		System.out.print(ANSI_BLACK_BACKGROUND + " ");
	}
	
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column,row);
		} catch (RuntimeException e ) {
			throw new InputMismatchException("Error reading chess position");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedList) {
		printBoard(chessMatch.getPieces());
		System.out.println("\nTurn: ");
		System.out.print(chessMatch.getTurn() + "\n");
		
		if(!capturedList.isEmpty())
			printCapturedPieces(capturedList);
		
		if(!chessMatch.getCheckMate()) {
			if(chessMatch.getCheck())
				System.out.println("CHECK!");
			
			System.out.println("\nPlayer: ");
			System.out.print(chessMatch.getCurrentPlayer() + "\n");
		} else {
			System.out.println("CHECK MATE!");
		}
	}

	private static void printCapturedPieces(List<ChessPiece> capturedlist) {
		List<Piece> reds = (capturedlist.stream().filter(x -> x.getColor() == Color.RED).collect(Collectors.toList()));
		List<Piece> blues = (capturedlist.stream().filter(x -> x.getColor() == Color.BLUE).collect(Collectors.toList()));
		
		if(!reds.isEmpty()) {
			System.out.println("Captured Pieces");
			System.out.print("RED [");
			System.out.print(Arrays.toString(reds.toArray()));
			System.out.println("]");
		}
		
		if(!blues.isEmpty()) {
			System.out.print("BLUE [");
			System.out.print(Arrays.toString(blues.toArray()));
			System.out.println("]");
		}
	}
	
}







