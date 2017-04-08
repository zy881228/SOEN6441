package warGame.Decorator;

import java.util.ArrayList;

public class Frightening extends WeaponDecorator{
	public Frightening(Weapon decoratedWeapon, ArrayList<String> enchantList){
		super(decoratedWeapon, enchantList);
	}
	
	@Override
	public ArrayList<String> addToEnchantList(String enchantment) {
		return super.addToEnchantList(enchantment);
	}
}
