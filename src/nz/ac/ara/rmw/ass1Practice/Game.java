package nz.ac.ara.rmw.ass1Practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game implements IGame {
	
	private int moveCount = 0;
	private List<Direction> theseusMoves = new ArrayList<Direction>(); // for undo / history
	public List<Direction> minotaurMoves = new ArrayList<Direction>(); // for undo / history
	public String[][] gameLevel = {{}};
	// change to private when don't need to test
	
	public void load(String[][] newLevel) {
		String[][] defaultGame = { {"#", "#", "#", "#", "#", "#", "#", "/", "/"},
						            {"#", "-", "/", "M", "/", "-", "#", "/", "/"},
						            {"#", "/", "/", "#", "#", "/", "#", "#", "/"},
						            {"#", "-", "/", "-", "#", "-", "/", "E", "/"},
						            {"#", "/", "/", "#", "#", "/", "#", "#", "/"},
						            {"#", "-", "/", "T", "/", "-", "#", "/", "/"},
						            {"#", "#", "#", "#", "#", "#", "#", "/", "/"} };
		String[][] empty = {{}};
		if(Arrays.deepEquals(newLevel, empty)) {
			gameLevel = defaultGame;
		}
		else {
			gameLevel = newLevel;
		}
	}

	@Override
	public void moveTheseus(Direction direction) {
		String value = this.getTheseusPos(); //get T position
		String[] splitT = value.split(",");
		int yPos = Integer.parseInt(splitT[(0)]);
		int xPos = Integer.parseInt(splitT[(splitT.length - 1)]);
		int toX = 0; // destination
		int toY = 0;
		int betweenX_PosAndDest = 0; // before destination, e.g. wall or empty wall space
		int betweenY_PosAndDest = 0;
		
		switch(direction) {
		case UP:
			betweenX_PosAndDest = xPos;			
			betweenY_PosAndDest = yPos - 1;
			toX = xPos;
			toY = yPos - 2;	
			break;
		case DOWN:
			betweenX_PosAndDest = xPos;			
			betweenY_PosAndDest = yPos + 1;
			toX = xPos;	
			toY = yPos + 2;	
			break;
		case LEFT:
			betweenX_PosAndDest = xPos - 1;			
			betweenY_PosAndDest = yPos;
			toX = xPos - 2;
			toY = yPos;	
			break;
		case RIGHT:
			betweenX_PosAndDest = xPos + 1;	
			betweenY_PosAndDest = yPos;
			toX = xPos + 2;
			toY = yPos;	
			break;
		case NONE:
			this.moveMinotaur();
			moveCount++;
			theseusMoves.add(direction);
			break;
		default:
			break;
		}
		
		if(this.checkTheseusMove(betweenX_PosAndDest, betweenY_PosAndDest, toX, toY)) {
			moveCount++;
			theseusMoves.add(direction);
			
			if(!gameLevel[toY][toX].equals("E")) {
				this.move("T", yPos, xPos, toY, toX);
				this.moveMinotaur();
			}
			else {
				this.move("T", yPos, xPos, toY, toX);
				System.out.println("YAY YOU WON!");
			}
			
		}
		else if(gameLevel[toY][toX].equals("M")) {
				System.out.println("OH NO YOU LOST! Why did you move onto Minotaur!?");
				this.move("M", yPos, xPos, toY, toX);
		}
	}
	
	private boolean checkTheseusMove(int betweenX, int betweenY, int toX, int toY) {
		boolean result = false;
			if(gameLevel[betweenY][betweenX].equals("/")) {
				if (toX <= gameLevel[0].length) { // so doesn't try fall off map
					if(gameLevel[toY][toX].equals("-") || gameLevel[toY][toX].equals("E")) {
						result = true;
					}
				}
			}
		
		return result;
	}
	
	public String getTheseusPos() {
		int y = 0;
		int x = 0;
		for (int i = 0; i < gameLevel.length; i++) {
		    for (int j = 0; j < gameLevel[i].length; j++) {
		        if (gameLevel[i][j].equals("T")) {
		            y = i;
		            x = j;
		        }
		    }
		} 
		return (y + "," + x);
	}
	
	public String getMinotaurPos() {
		int y = 0;
		int x = 0;
		for (int i = 0; i < gameLevel.length; i++) {
		    for (int j = 0; j < gameLevel[i].length; j++) {
		        if (gameLevel[i][j].equals("M")) {
		            y = i;
		            x = j;
		        }
		    }
		} 
		return (y + "," + x);
	}

	@Override
	public void moveMinotaur() {
		int count = 0; 
		
		while(count < 2) { //minotaur can move twice
			String theseus = this.getTheseusPos(); //get T position
			String minotaur = this.getMinotaurPos();
			String[] splitT = theseus.split(",");
			int yPosT = Integer.parseInt(splitT[(0)]);
			int xPosT = Integer.parseInt(splitT[(splitT.length - 1)]);
			String[] splitM = minotaur.split(",");
			int yPosM = Integer.parseInt(splitM[(0)]);
			int xPosM = Integer.parseInt(splitM[(splitM.length - 1)]);
			int toX = 0; // destination
			int toY = 0;
			int betweenX_PosAndDest = 0; // before destination, e.g. wall or empty wall space
			int betweenY_PosAndDest = 0;
			Direction direction = null; // to know what way to go
			
			if(xPosT < xPosM) {
				direction = Direction.LEFT;
			}
			else if(xPosT > xPosM) {
				direction = Direction.RIGHT;
			}
			else if(yPosT < yPosM) {
				direction = Direction.UP;
			}
			else if(yPosT > yPosM) {
				direction = Direction.DOWN;
			}
			else {
				direction = Direction.NONE;
			}

			switch(direction) {
			case UP:
				betweenX_PosAndDest = xPosM;			
				betweenY_PosAndDest = yPosM - 1;
				toX = xPosM;
				toY = yPosM  - 2;
				break;
			case DOWN:
				betweenX_PosAndDest = xPosM;			
				betweenY_PosAndDest = yPosM + 1;
				toX = xPosM;	
				toY = yPosM + 2;
				break;
			case LEFT:
				betweenX_PosAndDest = xPosM - 1;			
				betweenY_PosAndDest = yPosM;
				toX = xPosM - 2;	
				toY = yPosM;	
				break;
			case RIGHT:
				betweenX_PosAndDest = xPosM + 1;	
				betweenY_PosAndDest = yPosM;
				toX = xPosM + 2;
				toY = yPosM;	
				break;
			case NONE:
				break;
			default:
				break;
			}
			
			if(this.checkMinotaurMove(betweenX_PosAndDest, betweenY_PosAndDest, toX, toY)) {
				this.move("M", yPosM, xPosM, toY, toX);
				minotaurMoves.add(direction);
			}
			else {
				if(yPosT < yPosM) {
					direction = Direction.UP;
				}
				else if(yPosT > yPosM) {
					direction = Direction.DOWN;
				}
				else if(xPosT < xPosM) {
					direction = Direction.LEFT;
				}
				else if(xPosT > xPosM) {
					direction = Direction.RIGHT;
				}

				switch(direction) {
				case UP:
					betweenX_PosAndDest = xPosM;			
					betweenY_PosAndDest = yPosM - 1;
					toX = xPosM;
					toY = yPosM  - 2;
					break;
				case DOWN:
					betweenX_PosAndDest = xPosM;			
					betweenY_PosAndDest = yPosM + 1;
					toX = xPosM;	
					toY = yPosM + 2;
					break;
				case LEFT:
					betweenX_PosAndDest = xPosM - 1;			
					betweenY_PosAndDest = yPosM;
					toX = xPosM - 2;	
					toY = yPosM;	
					break;
				case RIGHT:
					betweenX_PosAndDest = xPosM + 1;	
					betweenY_PosAndDest = yPosM;
					toX = xPosM + 2;
					toY = yPosM;	
					break;
				default:
					break;
				}
				
				if(this.checkMinotaurMove(betweenX_PosAndDest, betweenY_PosAndDest, toX, toY)) {
					this.move("M", yPosM, xPosM, toY, toX);
					minotaurMoves.add(direction);
				}
			}
			count++;
		}
	}
	
	private void move(String player, int yPos, int xPos, int toY, int toX) {
		if(player.equals("T")) {
			gameLevel[yPos][xPos] = "-";
			gameLevel[toY][toX] = "T";	
		}
		else if(player.equals("M")) {
			gameLevel[yPos][xPos] = "-";
			gameLevel[toY][toX] = "M";	
		}
	}

	private boolean checkMinotaurMove(int betweenX, int betweenY, int toX, int toY) {	
		boolean result = false;
		if(gameLevel[betweenY][betweenX].equals("/")) {
			if(gameLevel[toY][toX].equals("-")) {
				result = true;
			}
			else if(gameLevel[toY][toX].equals("T")) {
				System.out.println("OH NO YOU LOST! Minotaur caught you!");
				result = true;
			}
		}
		
		return result;
	}
	
	public void undo() {
		String value = this.getTheseusPos(); //get T position
		int yPos = Integer.parseInt(value.substring(0, value.length() / 2)); // get y pos
		int xPos = Integer.parseInt(value.substring(value.length() - 1)); // get x pos
		int toX = 0; // destination
		int toY = 0;
		Direction direction = theseusMoves.get(theseusMoves.size() - 1);

		switch(direction) {
		case UP: // need to move down
			toX = xPos;
			toY = yPos + 2;	
			break;
		case DOWN: // need to move up
			toX = xPos;	
			toY = yPos - 2;	
			break;
		case LEFT: // need to move right
			toX = xPos + 2;
			toY = yPos;	
			break;
		case RIGHT: // need to move left
			toX = xPos - 2;
			toY = yPos;	
			break;
		default:
			break;
		}
		
		this.move("T", yPos, xPos, toY, toX);
		if(theseusMoves.size() == minotaurMoves.size()) {
			this.undoMinotaur();
		}	
		moveCount--;
		theseusMoves.remove(direction);
	}
	
	private void undoMinotaur() {
		String minotaur = this.getMinotaurPos();
		int count = 0; 
		int xPosM = Integer.parseInt(minotaur.substring(minotaur.length() - 1));
		int yPosM = Integer.parseInt(minotaur.substring(0, minotaur.length() / 2));
		int toX = 0; // destination
		int toY = 0;
		
		while(count < 2) {
			
			Direction direction = minotaurMoves.get(minotaurMoves.size() - 1);
		
			switch(direction) {
			case UP: // need to move down
				toX = xPosM;
				toY = yPosM + 2;	
				break;
			case DOWN: // need to move up
				toX = xPosM;	
				toY = yPosM - 2;	
				break;
			case LEFT: // need to move right
				toX = xPosM + 2;
				toY = yPosM;	
				break;
			case RIGHT: // need to move left
				toX = xPosM - 2;
				toY = yPosM;	
				break;
			default:
				break;
			}
			
			this.move("M", yPosM, xPosM, toY, toX);
			minotaurMoves.remove(direction);
			count++;
		}
	}
	
	public void reset() {
		String[][] empty = {{}};
		this.load(empty);
	}

	public int getMoveCount() {
		return moveCount;
	}
	
	public String getMoveHistory() {
        String moveHistory = "";
        int length = theseusMoves.size() - 1;
        
        for(int i = length; i >= 0; i--) {
        	moveHistory += theseusMoves.get(i).toString() + " ";
        }
        
        return moveHistory;
    }
}
