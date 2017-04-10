package warGame.Strategy;

import java.util.ArrayList;

public class AggressiveNPC implements CharacterStrategy{

	@Override
	public ArrayList<String> turn() {
		ArrayList<String> actionList = new ArrayList<String>();
		actionList.add("Loot_Chest");
		actionList.add("Attack_Character");
		actionList.add("Move_Aggressively");		
		return actionList;
	}
}
