package warGame.Decorator;

import java.util.ArrayList;

import warGame.Model.WarGameItemModel;

public class Freezing extends WeaponDecorator{

	public Freezing(WarGameItemModel decoratedWeapon) {
		super(decoratedWeapon);
	}

	@Override
	public WarGameItemModel addEnchantment() {
		if (decoratedWeapon.getEnchantList()==null) {
			ArrayList<String>enchantList = new ArrayList<String>();
			enchantList.add("Freezing");
			decoratedWeapon.setEnchantList(enchantList);
		}else{
			ArrayList<String>enchantList = decoratedWeapon.getEnchantList();
			enchantList.add("Freezing");
			decoratedWeapon.setEnchantList(enchantList);
		}
		return decoratedWeapon;
	}

}
