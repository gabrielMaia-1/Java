package aplication;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		
		
		while(!chessMatch.getCheckMate()) {
			UI.clearScreen();
			UI.printMatch(chessMatch, chessMatch.getCapturedList());
			try {
				System.out.println();
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(),chessMatch.possibleMoves(source));
				
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				chessMatch.performChessMove(source, target);
				
				if(chessMatch.getPromoted() != null) {
					System.out.println("Pawn Promoted! Chose a piece to promote (Q/H/B/R):\n");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
					sc.nextLine();
				}
				
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		
		UI.clearScreen();
		UI.printMatch(chessMatch, chessMatch.getCapturedList());
		
	}

}