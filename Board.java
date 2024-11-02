import java.awt.Color;
import java.util.ArrayList;

import acm.program.ConsoleProgram;

public class Board extends ConsoleProgram{
	private int width;
	private int height;
	private ArrayList<Pieces> piecesList;
	private ArrayList<Squares> checkersquaresList;
	private ArrayList<Moves> possibleMovesList;
	private Player p1;
	private Player p2;
	private Player playerTurn;
	
	public Board() {
		this.width = 8;
		this.height = 8;
		this.checkersquaresList = new ArrayList<Squares>();
		this.piecesList = new ArrayList<Pieces>();
		//checkerboard setup
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				if(x % 2 == 1) {
					if(y % 2 == 0) {
						Squares black = new Squares(Color.black, new Location(x,y));
						checkersquaresList.add(black);
					}else if (y % 2 == 1) {
						Squares white = new Squares(Color.white, new Location(x,y));
						checkersquaresList.add(white);
					}
				}
				
				if(x % 2 == 0) {
					if(y % 2 ==0) {
						Squares white = new Squares(Color.white, new Location(x,y));
						checkersquaresList.add(white);
					}else if(y % 2 == 1) {
						Squares black = new Squares(Color.black, new Location(x,y));
						checkersquaresList.add(black);
					}
				}
			}
		}
		
		//full pieces list for p1
		ArrayList<Pieces> p1PiecesList = new ArrayList<Pieces>();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < 3; y++) {
				if(x % 2 ==1) {
					if(y % 2 == 0) {
						Pieces p1 = new Pieces(this, new Location(x,y), Color.red);
						p1PiecesList.add(p1);
						piecesList.add(p1);
					}
				} else if(x % 2 ==0) {
					if(y % 2 ==1) {
						Pieces p1 = new Pieces(this, new Location(x,y), Color.red);
						p1PiecesList.add(p1);
						piecesList.add(p1);
					}
				}
			}
		}
		//make p1
		p1 = new Player(p1PiecesList, Color.red);
		
		//full pieces list for p2
		ArrayList<Pieces> p2PiecesList = new ArrayList<Pieces>();
		for(int x = 0; x < width; x++) {
			for(int y = height - 3; y < height; y++) {
				if(x % 2 == 0) {
					if(y % 2 == 1) {
						Pieces p2 = new Pieces(this, new Location(x,y), Color.green);
						p2PiecesList.add(p2);
						piecesList.add(p2);
					}
				}else if(x % 2 ==1) {
					if(y % 2 ==0) {
						Pieces p2 = new Pieces(this, new Location(x,y), Color.green);
						p2PiecesList.add(p2);
						piecesList.add(p2);
					}
				}
			}
		}
		
		//make p2
		p2 = new Player(p2PiecesList, Color.green);
		
		//originally set first player turn to p1
		playerTurn = p1;
	}
	
	//arraylist of all possible moves
	public ArrayList<Moves> getPossibleMoves(Player p){
		ArrayList<Moves> possibleMoves = new ArrayList<Moves>();
		for(int i = 0; i < p.getMyPiecesList().size(); i++) {
			Pieces currentPiece = p.getMyPiecesList().get(i);
			//coordinates of piece to the left diagonal of current piece
			int leftX = currentPiece.getLocation().getX() - 1;
			int leftY = currentPiece.getLocation().getY();
			
			//numbers flip depending on player (whether on top or bottom)
			if(p == p1) {
				leftY++;
			} else {
				leftY--;
			}
			
			//coordinates of piece to the right diagonal of current piece
			int rightX = currentPiece.getLocation().getX() + 1;
			int rightY = currentPiece.getLocation().getY();
			
			//numbers flip depending on player (whether on top or bottom)
			if(p == p1) {
				rightY++;
			} else {
				rightY--;
			}
			
			Pieces leftPiece = getPieceAt(leftX, leftY);
			Pieces rightPiece = getPieceAt(rightX, rightY);
			
			//if right diagonal is empty
			if(rightPiece == null) {
				Squares targetSquare = getSquareAt(rightX, rightY);
				//add to possible moves if right diagonal is empty and target square exists
				if(targetSquare != null) {
					Moves rightMove = new Moves(targetSquare, currentPiece);
					possibleMoves.add(rightMove);
				}
			} else //check for other players piece
				{ if(rightPiece.getColor() != p.getColor()) {
					//set coordinates for each player
					rightX++;
					if(p == p1) {
						rightY++;
					} else {
						rightY--;
					}
					Pieces targetPiece = getPieceAt(rightX, rightY);
					if(targetPiece == null) {
						Squares targetSquare = getSquareAt(rightX, rightY);
						if(targetSquare != null) {
							Moves rightMove = new Moves(targetSquare, currentPiece);
							possibleMoves.add(rightMove);
						}
					}
				}
			}
			
			//if left diagonal is empty
			if(leftPiece == null) {
				Squares targetSquare = getSquareAt(leftX, leftY);
				//add to possible moves if left diagonal is empty and target square exists
				if(targetSquare != null) {
					Moves leftMove = new Moves(targetSquare, currentPiece);
					possibleMoves.add(leftMove);
				}
			} else { //check for other player's piece
				if(leftPiece.getColor() != p.getColor()) {
					//set x and y coordinates for each player
					leftX--;
					if(p == p1) {
						leftY++;
					} else {
						leftY--;
					}
					//check if diagonal squares are empty
					Pieces targetPiece = getPieceAt(leftX, leftY);
					if(targetPiece == null) {
						Squares targetSquare = getSquareAt(leftX, leftY);
						if(targetSquare != null) {
							Moves leftMove = new Moves(targetSquare, currentPiece);
							possibleMoves.add(leftMove);
						}
					}
				}
			}
			
			//possible moves for QUEENS
			if(currentPiece.isQueen()) {
				leftX = currentPiece.getLocation().getX() - 1;
				leftY = currentPiece.getLocation().getY();
				
				//numbers flip depending on player (whether on top or bottom)
				if(p == p1) {
					leftY--;
				} else {
					leftY++;
				}
				
				//coordinates of piece to the right diagonal of current piece
				rightX = currentPiece.getLocation().getX() + 1;
				rightY = currentPiece.getLocation().getY();
				
				if(p == p1) {
					rightY--;
				} else {
					rightY++;
				}
				
				leftPiece = getPieceAt(leftX, leftY);
				rightPiece = getPieceAt(rightX, rightY);
				
				if(rightPiece == null) {
					Squares targetSquare = getSquareAt(rightX, rightY);
					if(targetSquare != null) {
						Moves rightMove = new Moves(targetSquare, currentPiece);
						possibleMoves.add(rightMove);
					}
				} else //other players piece
					{ if(rightPiece.getColor() != p.getColor()) {
						rightX++;
						if(p == p1) {
							rightY--;
						} else {
							rightY++;
						}
						Pieces targetPiece = getPieceAt(rightX, rightY);
						if(targetPiece == null) {
							Squares targetSquare = getSquareAt(rightX, rightY);
							if(targetSquare != null) {
								Moves rightMove = new Moves(targetSquare, currentPiece);
								possibleMoves.add(rightMove);
							}
						}
					}
				}
				
				//if left is empty
				if(leftPiece == null) {
					Squares targetSquare = getSquareAt(leftX, leftY);
					if(targetSquare != null) {
						Moves leftMove = new Moves(targetSquare, currentPiece);
						possibleMoves.add(leftMove);
					}
				} else { //check for other player
					if(leftPiece.getColor() != p.getColor()) {
						//set x and y coordinates for each player
						leftX--;
						if(p == p1) {
							leftY--;
						} else {
							leftY++;
						}
						//check if diagonal squares are empty
						Pieces targetPiece = getPieceAt(leftX, leftY);
						if(targetPiece == null) {
							Squares targetSquare = getSquareAt(leftX, leftY);
							if(targetSquare != null) {
								Moves leftMove = new Moves(targetSquare, currentPiece);
								possibleMoves.add(leftMove);
							}
						}
					}
				}
			}
		}
		return possibleMoves;
	}
	
	//arraylist of possible moves for the clicked piece
	public ArrayList<Moves> clickedPiecePossibleMoves(Player p, Pieces clickedPiece){
		ArrayList<Moves> clickedPiecePossibleMoves = new ArrayList<Moves>();
		Pieces currentPiece = clickedPiece;
		//coordinates of piece to the left diagonal of current piece
		int leftX = currentPiece.getLocation().getX() - 1;
		int leftY = currentPiece.getLocation().getY();
		
		//numbers flip depending on player (whether on top or bottom)
		if(p == p1) {
			leftY++;
		} else {
			leftY--;
		}
		
		//coordinates of piece to the right diagonal of current piece
		int rightX = currentPiece.getLocation().getX() + 1;
		int rightY = currentPiece.getLocation().getY();
		
		//numbers flip depending on player (whether on top or bottom)
		if(p == p1) {
			rightY++;
		} else {
			rightY--;
		}
		
		Pieces leftPiece = getPieceAt(leftX, leftY);
		Pieces rightPiece = getPieceAt(rightX, rightY);
		
		//right diagonal square is empty
		if(rightPiece == null) {
			Squares targetSquare = getSquareAt(rightX, rightY);
			//add right diagonal to possible moves if the target square is not null
			if(targetSquare != null) {
				Moves rightMove = new Moves(targetSquare, currentPiece);
				clickedPiecePossibleMoves.add(rightMove);
			}
		} else //other players piece
			{ if(rightPiece.getColor() != p.getColor()) {
				rightX++;
				if(p == p1) {
					rightY++;
				} else {
					rightY--;
				}
				Pieces targetPiece = getPieceAt(rightX, rightY);
				if(targetPiece == null) {
					Squares targetSquare = getSquareAt(rightX, rightY);
					if(targetSquare != null) {
						Moves rightMove = new Moves(targetSquare, currentPiece);
						clickedPiecePossibleMoves.add(rightMove);
					}
				}
			}
		}
		
		//if left is empty
		if(leftPiece == null) {
			Squares targetSquare = getSquareAt(leftX, leftY);
			if(targetSquare != null) {
				Moves leftMove = new Moves(targetSquare, currentPiece);
				clickedPiecePossibleMoves.add(leftMove);
			}
		} else { //check for other player
			if(leftPiece.getColor() != p.getColor()) {
				//set x and y coordinates for each player
				leftX--;
				if(p == p1) {
					leftY++;
				} else {
					leftY--;
				}
				//check if diagonal squares are empty
				Pieces targetPiece = getPieceAt(leftX, leftY);
				if(targetPiece == null) {
					Squares targetSquare = getSquareAt(leftX, leftY);
					if(targetSquare != null) {
						Moves leftMove = new Moves(targetSquare, currentPiece);
						clickedPiecePossibleMoves.add(leftMove);
					}
				}
			}
		}
		
		//possible moves for QUEENS
		if(currentPiece.isQueen()) {
			leftX = currentPiece.getLocation().getX() - 1;
			leftY = currentPiece.getLocation().getY();
			
			//numbers flip depending on player (whether on top or bottom)
			if(p == p1) {
				leftY--;
			} else {
				leftY++;
			}
			
			//coordinates of piece to the right diagonal of current piece
			rightX = currentPiece.getLocation().getX() + 1;
			rightY = currentPiece.getLocation().getY();
			
			if(p == p1) {
				rightY--;
			} else {
				rightY++;
			}
			
			leftPiece = getPieceAt(leftX, leftY);
			rightPiece = getPieceAt(rightX, rightY);
			
			if(rightPiece == null) {
				Squares targetSquare = getSquareAt(rightX, rightY);
				if(targetSquare != null) {
					Moves rightMove = new Moves(targetSquare, currentPiece);
					clickedPiecePossibleMoves.add(rightMove);
				}
			} else //other players piece
				{ if(rightPiece.getColor() != p.getColor()) {
					rightX++;
					if(p == p1) {
						rightY--;
					} else {
						rightY++;
					}
					Pieces targetPiece = getPieceAt(rightX, rightY);
					if(targetPiece == null) {
						Squares targetSquare = getSquareAt(rightX, rightY);
						if(targetSquare != null) {
							Moves rightMove = new Moves(targetSquare, currentPiece);
							clickedPiecePossibleMoves.add(rightMove);
						}
					}
				}
			}
			
			//if left is empty
			if(leftPiece == null) {
				Squares targetSquare = getSquareAt(leftX, leftY);
				if(targetSquare != null) {
					Moves leftMove = new Moves(targetSquare, currentPiece);
					clickedPiecePossibleMoves.add(leftMove);
				}
			} else { //check for other player
				if(leftPiece.getColor() != p.getColor()) {
					//set x and y coordinates for each player
					leftX--;
					if(p == p1) {
						leftY--;
					} else {
						leftY++;
					}
					//check if diagonal squares are empty
					Pieces targetPiece = getPieceAt(leftX, leftY);
					if(targetPiece == null) {
						Squares targetSquare = getSquareAt(leftX, leftY);
						if(targetSquare != null) {
							Moves leftMove = new Moves(targetSquare, currentPiece);
							clickedPiecePossibleMoves.add(leftMove);
						}
					}
				}
			}
		}
		
		return clickedPiecePossibleMoves;
	}
	
	public Pieces getPieceAt(int x, int y) {
		for(int i = 0; i < piecesList.size(); i++) {
			Pieces currentPiece = piecesList.get(i);
			
			if(currentPiece.getLocation().getX() == x && currentPiece.getLocation().getY() ==y) {
				return currentPiece;
			}
		}
		return null;
	}
	
	//override method for different 
	public Pieces getPieceAt(Location loc) {
		return getPieceAt(loc.getX(), loc.getY());
	}
	
	public Squares getSquareAt(int x, int y) {
		for(int i = 0; i < checkersquaresList.size(); i++) {
			Squares currentSquare = checkersquaresList.get(i);
			
			if(currentSquare.getLocation().getX() == x 
				&& currentSquare.getLocation().getY() ==y) {
				return currentSquare;
			}
		}
		return null;
	}
	
	//move pieces diagonally and/or "eat"/take opponents pieces
	public void movePiece(Pieces clickedPiece, Location targetLocation) {
		int distanceX = clickedPiece.getMyLocation().getX() - targetLocation.getX();
		
		if (Math.abs(distanceX) > 1){
			int capturedPieceX = 0;
			if(clickedPiece.getMyLocation().getX() > targetLocation.getX()) {
				capturedPieceX = clickedPiece.getMyLocation().getX() - 1;
			} else {
				capturedPieceX = clickedPiece.getMyLocation().getX() + 1;
			}
			
			int capturedPieceY = 0;
			if(clickedPiece.getMyLocation().getY() > targetLocation.getY()) {
				capturedPieceY = clickedPiece.getMyLocation().getY() - 1;
			} else {
				capturedPieceY = clickedPiece.getMyLocation().getY() + 1;
			}
			Location capturedPieceLocation = new Location(capturedPieceX, capturedPieceY);
			Pieces capturedPiece = getPieceAt(capturedPieceLocation);
			piecesList.remove(capturedPiece);
			p1.getMyPiecesList().remove(capturedPiece);
			p2.getMyPiecesList().remove(capturedPiece);
		}
		clickedPiece.setMyLocation(targetLocation);
		
		//upgrade pieces into queens
		if(playerTurn == p2 && clickedPiece.getMyLocation().getY() == 0) {
			clickedPiece.setQueen(true);
		}
		
		if(playerTurn == p1 && clickedPiece.getMyLocation().getY() == 7) {
			clickedPiece.setQueen(true);
		}
	}
	
	public void switchPlayerTurn() {
		if(playerTurn == p1) {
			System.out.println("Player 2's turn (GREEN) ");
			playerTurn = p2;
		} else {
			System.out.println("Player 1's turn (RED) ");
			playerTurn = p1;
		}
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public Player getPlayerTurn() {
		return playerTurn;
	}
	
	public ArrayList<Squares> getcheckersquaresList() {
		return checkersquaresList;
	}
	
	public void setCheckersquaresList(ArrayList<Squares> checkersquaresList) {
		this.checkersquaresList = checkersquaresList;
	}
	
	public ArrayList<Pieces> getPiecesList(){
		return piecesList;
	}
	
	public ArrayList<Moves> getPossibleMovesList(){
		return getPossibleMoves(playerTurn);
	}
	
	public ArrayList<Moves> getClickedPiecePossibleMovesList(Pieces piece){
		return clickedPiecePossibleMoves(playerTurn, piece);
	}
}

