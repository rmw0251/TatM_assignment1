package nz.ac.ara.rmw.ass1Practice;

public interface IGame {

	public void moveTheseus(Direction direction);
	public void moveMinotaur();
	public void load(String[][] newLevel);
	public String getTheseusPos();
	public String getMinotaurPos();
	public void undo();
	public void reset();
	
}
