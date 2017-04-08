package warGame.Decorator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Weapon extends Observable{
	public abstract ArrayList<String> addToEnchantList(String enchantment);
	public abstract void saveWeapon(WeaponDecorator weapon) throws IOException;
	public abstract Weapon adaptToLevel(int level);
	public abstract void setWeaponView();
	public abstract String lastWeaponID() throws UnsupportedEncodingException, FileNotFoundException;
}
