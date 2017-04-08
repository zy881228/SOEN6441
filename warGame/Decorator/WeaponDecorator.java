package warGame.Decorator;

import java.util.ArrayList;

public abstract class WeaponDecorator extends Weapon{
	protected final Weapon decoratedWeapon;
	protected final ArrayList<String> enchantList;
	
	public WeaponDecorator(Weapon decoratedWeapon, ArrayList<String> enchantList){
		this.decoratedWeapon = decoratedWeapon;
		this.enchantList = enchantList;
	}
	
	@Override
	public ArrayList<String> addToEnchantList(String enchantment) {
		this.enchantList.add(enchantment);
		return this.enchantList;
	}
}
