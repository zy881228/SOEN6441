package warGame.Decorator;

import warGame.Model.WarGameItemModel;

public class WeaponDecorator extends WarGameItemModel{
	
	protected final WarGameItemModel decoratedWeapon;
	
	public WeaponDecorator(WarGameItemModel decoratedWeapon){
		this.decoratedWeapon = decoratedWeapon;
	}

	@Override
	public WarGameItemModel addEnchantment() {
		decoratedWeapon.addEnchantment();
		return decoratedWeapon;
	}

}
