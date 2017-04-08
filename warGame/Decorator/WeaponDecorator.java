package warGame.Decorator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public abstract class WeaponDecorator extends Weapon{
	
	protected  Weapon decoratedWeapon;
	protected  ArrayList<String> enchantList;
	protected String weaponName;
	protected int attackRange; 
	protected String affectedType;
	protected int affectedValue;
	protected String weaponID;

	
	public WeaponDecorator(Weapon decoratedWeapon, String weaponName, int attackRange, String affectedType, int affectedValue, ArrayList<String> enchantList) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		this.weaponID = Integer.parseInt(this.lastWeaponID())+1+"";
		this.decoratedWeapon = decoratedWeapon;
		this.weaponName = weaponName;
		this.attackRange = attackRange;
		this.affectedType = affectedType;
		this.affectedValue = affectedValue;
		this.enchantList = enchantList;
	}
	
	@Override
	public Weapon adaptToLevel(int level) {
		if (level>=1&&level<=4) {
			this.affectedValue = 1;
		}
		if (level>=5&&level<=8) {
			this.affectedValue = 2;
		}
		if (level>=9&&level<=12) {
			this.affectedValue = 3;
		}
		if (level>=13&&level<=16) {
			this.affectedValue = 4;
		}
		if (level>=17&&level<=20) {
			this.affectedValue = 5;
		}
		return this;
	}
	
	public String lastWeaponID() throws UnsupportedEncodingException, FileNotFoundException {
		ArrayList<String> allKeysInMap = new	 ArrayList<String>();
		Map<String, WeaponDecorator> weaponsByMap = WeaponDecorator.listAllWeapons();
		Iterator<Entry<String, WeaponDecorator>> it = weaponsByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WeaponDecorator> entry = (Map.Entry<String, WeaponDecorator>) it.next();
			allKeysInMap.add(entry.getKey());
		}
		String lastWeaponID = allKeysInMap.get(allKeysInMap.size()-1);
		return lastWeaponID;
	}
	
	public static Map<String, WeaponDecorator> listAllWeapons() throws UnsupportedEncodingException, FileNotFoundException {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("src/file/weapons.json"), "UTF-8");
		Map<String, WeaponDecorator> weaponsByMap = gson.fromJson(isreader, new TypeToken<Map<String, Weapon>>(){}.getType());
		return weaponsByMap;
	}
	
	@Override
	public void saveWeapon(WeaponDecorator weapon) throws IOException{
		Map<String, WeaponDecorator> weaponsByMap = WeaponDecorator.listAllWeapons();
		weaponsByMap.put(weapon.getWeaponID(), weapon);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/weapons.json");
		fw.write(gson.toJson(weaponsByMap));
		fw.close();
	}
	
	@Override
	public ArrayList<String> addToEnchantList(String enchantment) {
		this.enchantList.add(enchantment);
		return this.enchantList;
	}

	@Override
	public void setWeaponView() {
		setChanged();
		notifyObservers(this);
	}
	
	@Override
	public String toString() {
		return this.weaponName;
	}
	
	public Weapon getDecoratedWeapon() {
		return decoratedWeapon;
	}

	public void setDecoratedWeapon(Weapon decoratedWeapon) {
		this.decoratedWeapon = decoratedWeapon;
	}

	public ArrayList<String> getEnchantList() {
		return enchantList;
	}

	public void setEnchantList(ArrayList<String> enchantList) {
		this.enchantList = enchantList;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public String getAffectedType() {
		return affectedType;
	}

	public void setAffectedType(String affectedType) {
		this.affectedType = affectedType;
	}

	public int getAffectedValue() {
		return affectedValue;
	}

	public void setAffectedValue(int affectedValue) {
		this.affectedValue = affectedValue;
	}

	public String getWeaponID() {
		return weaponID;
	}

	public void setWeaponID(String weaponID) {
		this.weaponID = weaponID;
	}
	
	
}
