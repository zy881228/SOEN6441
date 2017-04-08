package warGame.Decorator;

import java.util.ArrayList;

import warGame.Model.WarGameItemModel;

public class WeaponDecorator extends WarGameItemModel{
	
	protected WarGameItemModel decoratedWeapon;
	
	public WeaponDecorator(WarGameItemModel decoratedWeapon, ArrayList<String> enchantList, int attackRange){
		this.decoratedWeapon = decoratedWeapon;
		decoratedWeapon.setEnchantList(enchantList);
		decoratedWeapon.setAttackRange(attackRange);
	}

}
