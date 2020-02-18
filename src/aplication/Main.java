package aplication;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> capturedPieces = new ArrayList<ChessPiece>();
		
		
		while(true) {
			UI.clearScreen();
			UI.printMatch(chessMatch, capturedPieces);
			try {
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(),chessMatch.possibleMoves(source));
				
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece captured = chessMatch.performChessMove(source, target);
				
				if(captured != null)
					capturedPieces.add(captured);
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}