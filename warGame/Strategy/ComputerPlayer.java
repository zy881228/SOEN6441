package warGame.Strategy;

import java.util.ArrayList;

public class ComputerPlayer implements CharacterStrategy{

	@Override
	public ArrayList<String> turn() {
		ArrayList<String> actionList = new ArrayList<String>();
		actionList.add("Kill_Enemy");
		actionList.add("Loot_Chest");
		actionList.add("Reach_Exit");
		return actionList;
	}
}
