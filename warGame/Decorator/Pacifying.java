package warGame.Decorator;

import java.util.ArrayList;

import warGame.Model.WarGameItemModel;

public class Pacifying extends WeaponDecorator{

	public Pacifying(WarGameItemModel decoratedWeapon) {	
		super(decoratedWeapon);
	}

	@Override
	public WarGameItemModel addEnchantment() {
		if (decoratedWeapon.getEnchantList()==null) {
			ArrayList<String>enchantList = new ArrayList<String>();
			enchantList.add("Pacifying");
			decoratedWeapon.setEnchantList(enchantList);
		}else{
			ArrayList<String>enchantList = decoratedWeapon.getEnchantList();
			enchantList.add("Pacifying");
			decoratedWeapon.setEnchantList(enchantList);
		}
		return decoratedWeapon;
	}
	
}
