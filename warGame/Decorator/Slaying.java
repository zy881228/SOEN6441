package warGame.Decorator;

import java.util.ArrayList;

import warGame.Model.WarGameItemModel;

public class Slaying extends WeaponDecorator{

	public Slaying(WarGameItemModel decoratedWeapon) {
		super(decoratedWeapon);
	}

	@Override
	public WarGameItemModel addEnchantment() {
		if (decoratedWeapon.getEnchantList()==null) {
			ArrayList<String>enchantList = new ArrayList<String>();
			enchantList.add("Slaying");
			decoratedWeapon.setEnchantList(enchantList);
		}else{
			ArrayList<String>enchantList = decoratedWeapon.getEnchantList();
			enchantList.add("Slaying");
			decoratedWeapon.setEnchantList(enchantList);
		}
		return decoratedWeapon;
	}

}
