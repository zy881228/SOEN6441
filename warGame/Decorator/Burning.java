package warGame.Decorator;

import java.util.ArrayList;

import warGame.Model.WarGameItemModel;

public class Burning extends WeaponDecorator{

	public Burning(WarGameItemModel decoratedWeapon) {	
		super(decoratedWeapon);
	}

	@Override
	public WarGameItemModel addEnchantment() {
		if (decoratedWeapon.getEnchantList()==null) {
			ArrayList<String>enchantList = new ArrayList<String>();
			enchantList.add("Burning");
			decoratedWeapon.setEnchantList(enchantList);
		}else{
			ArrayList<String>enchantList = decoratedWeapon.getEnchantList();
			enchantList.add("Burning");
			decoratedWeapon.setEnchantList(enchantList);
		}
		return decoratedWeapon;
	}
	
}
