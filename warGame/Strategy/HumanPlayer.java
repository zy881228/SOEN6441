package warGame.Strategy;

import java.util.ArrayList;

public class HumanPlayer implements CharacterStrategy{
	
	@Override
	public ArrayList<String> turn() {
		ArrayList<String> actionList = new ArrayList<String>();
		actionList.add("Human");
		return actionList;
	}
}
