package warGame.Decorator;

import java.util.ArrayList;

import warGame.Model.WarGameItemModel;

public class Frightening extends WeaponDecorator{

	public Frightening(WarGameItemModel decoratedWeapon) {	
		super(decoratedWeapon);
	}

	@Override
	public WarGameItemModel addEnchantment() {
		if (decoratedWeapon.getEnchantList()==null) {
			ArrayList<String>enchantList = new ArrayList<String>();
			enchantList.add("Frightening");
			decoratedWeapon.setEnchantList(enchantList);
		}else{
			ArrayList<String>enchantList = decoratedWeapon.getEnchantList();
			enchantList.add("Frightening");
			decoratedWeapon.setEnchantList(enchantList);
		}
		return decoratedWeapon;
	}
	
}
