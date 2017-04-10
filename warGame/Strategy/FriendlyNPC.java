package warGame.Strategy;

import java.util.ArrayList;

public class FriendlyNPC implements CharacterStrategy{

	@Override
	public ArrayList<String> turn() {
		ArrayList<String> actionList = new ArrayList<String>();
		actionList.add("Loot_Chest");
		actionList.add("Move_Randomly");		
		return actionList;
	}
}
