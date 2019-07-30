package nz.ac.ara.rmw.ass1Practice.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import nz.ac.ara.rmw.ass1Practice.Direction;
import nz.ac.ara.rmw.ass1Practice.Filer;
import nz.ac.ara.rmw.ass1Practice.Game;

class GameTests {
	
	//change this for when handing in
	public String filePath = "./src/nz/ac/ara/rmw/ass1Practice/";
	
	public String[][] basicLevel = { {"#", "#", "#", "#", "#", "#", "#", "/", "/"},
						            {"#", "-", "/", "M", "/", "-", "#", "/", "/"},
						            {"#", "/", "/", "#", "#", "/", "#", "#", "/"},
						            {"#", "-", "/", "-", "#", "-", "/", "E", "/"},
						            {"#", "/", "/", "#", "#", "/", "#", "#", "/"},
						            {"#", "-", "/", "T", "/", "-", "#", "/", "/"},
						            {"#", "#", "#", "#", "#", "#", "#", "/", "/"} };
	
	public String[][] loseLevel = { {"#", "#", "#", "#", "#", "#", "#", "#", "-"},
						            {"#", "-", "/", "M", "/", "-", "/", "#", "-"},
						            {"#", "/", "/", "#", "#", "/", "/", "#", "#"},
						            {"#", "-", "/", "-", "#", "T", "/", "-", "E"},
						            {"#", "/", "/", "#", "#", "/", "/", "#", "#"},
						            {"#", "-", "/", "-", "/", "-", "/", "#", "-"},
						            {"#", "#", "#", "#", "#", "#", "#", "#", "-"} };
	
	public String[][] loseLevel2 = { {"#", "#", "#", "#", "#", "#", "#", "#", "-"},
						            {"#", "-", "/", "-", "/", "-", "/", "#", "-"},
						            {"#", "/", "/", "#", "#", "/", "/", "#", "#"},
						            {"#", "-", "/", "-", "#", "M", "/", "-", "E"},
						            {"#", "/", "/", "#", "#", "/", "/", "#", "#"},
						            {"#", "-", "/", "T", "/", "-", "/", "#", "-"},
						            {"#", "#", "#", "#", "#", "#", "#", "#", "-"} };
	
	public String[][] winLevel = { {"#", "#", "#", "#", "#", "#", "#", "/", "/"},
								    {"#", "-", "/", "-", "/", "-", "#", "/", "/"},
								    {"#", "/", "/", "#", "#", "/", "#", "#", "/"},
								    {"#", "-", "/", "M", "#", "T", "/", "E", "/"},
								    {"#", "/", "/", "#", "#", "/", "#", "#", "/"},
								    {"#", "-", "/", "-", "/", "-", "#", "/", "/"},
								    {"#", "#", "#", "#", "#", "#", "#", "/", "/"} };
	
	@Test // 1
	void test_T_Pos() {
		Game g = new Game();
		g.load(basicLevel);
		String expected = "5,3";
        String actual = g.getTheseusPos();
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 2
	void test_M_Pos() {
		Game g = new Game();
		g.load(basicLevel);
		String expected = "1,3";
        String actual = g.getMinotaurPos();
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 3
	void test_T_left() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		String expected = "T";
        String actual = basicLevel[5][1];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 4
	void test_T_right() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.RIGHT);
		String expected = "T";
        String actual = basicLevel[5][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 5
	void test_T_up() {
		Game g = new Game();
		g.load(winLevel);
		g.moveTheseus(Direction.UP);
		String expected = "T";
        String actual = winLevel[1][5]; 
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 6
	void test_T_down() {
		Game g = new Game();
		g.load(winLevel);
		g.moveTheseus(Direction.DOWN);
		String expected = "T";
        String actual = winLevel[5][5]; 
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 7
	void test_T_delay() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.NONE); //direction when not moving
		String expected = "T";
        String actual = basicLevel[5][3]; 
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 8
	void test_T_on_wall() { // should not move
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.UP);
		String expected = "-";
        String actual = basicLevel[3][3]; 
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 9
	void test_T_on_E() {
		Game g = new Game();
		g.load(winLevel);
		g.moveTheseus(Direction.RIGHT);
		String expected = "T";
        String actual = winLevel[3][7];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 10
	void test_T_past_E() {
		try {
			Filer f = new Filer();
		    Game g = new Game();
		    g.load(f.load(filePath + "level1.txt"));
		    g.moveTheseus(Direction.LEFT);
		    g.moveTheseus(Direction.RIGHT);
		    g.moveTheseus(Direction.RIGHT);
		    g.moveTheseus(Direction.UP);
		    g.moveTheseus(Direction.RIGHT);
		    g.moveTheseus(Direction.RIGHT); // here error should through as can't go off map
		    fail( "My method didn't throw when I expected it to" );
		}	 
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.print(e + "\n");
		}
	}
	
	@Test // 11
	void test_T_on_M() { // T will lose
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.LEFT); // M goes left then down
		g.moveTheseus(Direction.NONE); // delay T so M goes down one to get 'stuck'
		g.moveTheseus(Direction.RIGHT); // M doesn't move
		g.moveTheseus(Direction.RIGHT); // M doesn't move
		g.moveTheseus(Direction.UP); // M doesn't move
		g.moveTheseus(Direction.UP); // M moves up then right
		g.moveTheseus(Direction.LEFT); // T moves left onto M and dies
		String expected = "M";
        String actual = g.gameLevel[3][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 12
	void test_M_left_twice() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level3.txt"));
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP); // moves left twice here
		String expected = "M";
        String actual = g.gameLevel[3][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 13
	void test_M_right_twice() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level3.txt"));
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.RIGHT);
		String expected = "M";
        String actual = g.gameLevel[1][7];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 14
	void test_M_down_twice() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.RIGHT); // M goes right then down
		g.moveTheseus(Direction.DOWN); // M goes down twice to win
		String expected = "M";
        String actual = g.gameLevel[7][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 15
	void test_M_up_twice() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level4.txt"));
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.UP); // M moves up twice here
		String expected = "M";
        String actual = g.gameLevel[5][7];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 16
	void test_M_left_down() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.LEFT); // M goes left then down
		String expected = "M";
        String actual = g.gameLevel[3][1];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 17
	void test_M_right_down() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.RIGHT); // M goes right then down
		String expected = "M";
        String actual = g.gameLevel[3][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 18
	void test_M_left_up() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level1.txt"));
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT); // happens here
		String expected = "M"; // T loses
        String actual = g.gameLevel[1][1];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 19
	void test_M_right_up() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.NONE);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP); // right then up here
		String expected = "M"; // T loses
        String actual = g.gameLevel[1][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 20
	void test_M_down_left() {
		Game g = new Game();
		g.load(loseLevel2);
		g.moveMinotaur();
		String expected = "M";
        String actual = g.gameLevel[5][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 21
	void test_M_down_right() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level3.txt"));
		g.moveTheseus(Direction.DOWN);
		String expected = "M";
        String actual = g.gameLevel[5][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 22
	void test_M_up_left() { // can't go horizontal first so goes vertical
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level3.txt"));
		g.moveTheseus(Direction.RIGHT); 
		g.moveTheseus(Direction.RIGHT); 
		g.moveTheseus(Direction.RIGHT); 
		g.moveTheseus(Direction.RIGHT); 
		g.moveTheseus(Direction.UP); 
		g.moveTheseus(Direction.DOWN); 
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.UP); 
		g.moveTheseus(Direction.UP); 
		String expected = "M";
        String actual = g.gameLevel[3][9];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 23
	void test_M_up_right() { // can't go horizontal first so goes vertical
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.DOWN); // M doesn't move
		g.moveTheseus(Direction.LEFT); // M goes left then down
		g.moveTheseus(Direction.NONE); // delay T so M goes down one to get 'stuck'
		g.moveTheseus(Direction.RIGHT); // M doesn't move
		g.moveTheseus(Direction.RIGHT); // M doesn't move
		g.moveTheseus(Direction.UP); // M doesn't move
		g.moveTheseus(Direction.UP); // M moves up then right
		String expected = "M";
        String actual = g.gameLevel[3][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 24
	void test_M_delay() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveMinotaur();
		String expected = "M";
        String actual = basicLevel[1][3]; 
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 25
	void test_M_on_wall() { // should fail
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT); // M should get stuck
		g.moveTheseus(Direction.RIGHT); // M tries to go right like T but can't because of wall
		String expected = "M";
        String actual = basicLevel[3][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 26
	void test_M_on_T() {
		Game g = new Game();
		g.load(loseLevel);
		g.moveMinotaur();
		String expected = "M";
        String actual = loseLevel[3][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 27
	void test_M_on_E() { // shouldn't work
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.DOWN);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.NONE);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.RIGHT);
		String expected = "T"; // M can't move onto E, but T can
        String actual = g.gameLevel[3][7];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 28
	void test_M_stuck() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		String expected = "M";
        String actual = basicLevel[3][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 29
	void test_T_move_history() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveMinotaur();
		String expected = "UP RIGHT RIGHT LEFT ";
        String actual = g.getMoveHistory();
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 30
	void test_M_move_history() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		List<Direction> moves = new ArrayList<Direction>();
		moves.add(Direction.LEFT);
		moves.add(Direction.DOWN);
		moves.add(Direction.RIGHT);
		List<Direction> expected = moves;
        List<Direction> actual = g.minotaurMoves;
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 31
	void test_move_count() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		int expected = 3;
        int actual = g.getMoveCount();
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 32
	void test_delay_adds_to_move_count() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.NONE);
		int expected = 4;
        int actual = g.getMoveCount();
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 33
	void test_reset() {
		Game g = new Game();
		g.load(winLevel);
		g.reset();
		String expected = Arrays.deepToString(basicLevel);
        String actual = Arrays.deepToString(g.gameLevel);
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 34
	void test_load_level1() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level1.txt"));
		String expected = "M";
        String actual = g.gameLevel[1][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 35
	void test_load_level2() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		String expected = "T";
        String actual = g.gameLevel[3][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 36
	void test_save_level1() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level1.txt"));
		f.save(filePath + "test1.txt", g.gameLevel);
		String expected = Arrays.deepToString(g.gameLevel);
        String actual = Arrays.deepToString(f.load(filePath + "test1.txt"));
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 37
	void test_save_level2() {
		Filer f = new Filer();
		Game g = new Game();
		g.load(f.load(filePath + "level2.txt"));
		f.save(filePath + "test2.txt", g.gameLevel);
		String expected = Arrays.deepToString(g.gameLevel);
        String actual = Arrays.deepToString(f.load(filePath + "test2.txt"));
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 38
	void test_undo_once() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.DOWN);
		g.undo();
		String expected = "T";
        String actual = basicLevel[1][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 39 
	void test_undo_twice() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.DOWN);
		g.undo();
		g.undo();
		String expected = "T";
        String actual = basicLevel[3][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 40
	void test_undo_three_times() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.DOWN);
		g.undo();
		g.undo();
		g.undo();
		String expected = "T";
        String actual = basicLevel[5][5];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 41
	void test_undo_four_times() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.DOWN);
		g.undo();
		g.undo();
		g.undo();
		g.undo();
		String expected = "T";
        String actual = basicLevel[5][3];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
	
	@Test // 42
	void test_undo_five_times() {
		Game g = new Game();
		g.load(basicLevel);
		g.moveTheseus(Direction.LEFT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.RIGHT);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.UP);
		g.moveTheseus(Direction.DOWN);
		g.undo();
		g.undo();
		g.undo();
		g.undo();
		g.undo();
		String expected = "M";
        String actual = basicLevel[3][1];
        String errorMessage = "Expected " + expected + " but got " + actual;
        assertEquals( errorMessage, expected, actual );
	}
}
