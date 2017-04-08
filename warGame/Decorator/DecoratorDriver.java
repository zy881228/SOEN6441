package warGame.Decorator;

import java.util.ArrayList;

public class DecoratorDriver {
	
	public static void main(String[] args) {
		ArrayList<String> enchantList = new ArrayList<String>();
		Weapon weapon = new SimpleWeapon();
		
		weapon = new Freezing(weapon, enchantList);
		weapon.addToEnchantList("Freezing");
		
		weapon = new Burning(weapon, enchantList);
		weapon.addToEnchantList("Burning");
		
		weapon = new Slaying(weapon, enchantList);
		weapon.addToEnchantList("Slaying");
		
		weapon = new Frightening(weapon, enchantList);
		weapon.addToEnchantList("Frightening");
		
		weapon = new Pacifying(weapon, enchantList);
		weapon.addToEnchantList("Pacifying");

	}

}
