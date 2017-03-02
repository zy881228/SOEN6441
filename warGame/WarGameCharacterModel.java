package warGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

class WarGameCharacterModel extends Observable {

    /*public void WarGameCharacterModel(){
    	level = this.level;
		ability_scores = this.ability_scores;
		hit_points = this.hit_points;
		armor_class = this.armor_class;
		attack_bonus = this.attack_bonus;
		damage_bonus = this.damage_bonus;
    }*/

	public void createCharacter() {
		// TODO Auto-generated method character = new Character();
		Random rand = new Random();
		int a = rand.nextInt(20)+1;
		level = a;
		a = rand.nextInt(20)+1;
		ability_scores = a;
		a = rand.nextInt(20)+1;
		ability_modifiers = a;
		a = rand.nextInt(7)+1;
		picNumber = a;
		
		strength = get4d6Number();
		dexterity = get4d6Number();
		constitution = get4d6Number();
		intelligence = get4d6Number();
		wisdom = get4d6Number();
		charisma = get4d6Number();
		strength_modifier = getModifierNum(strength);
		dexterity_modifier = getModifierNum(dexterity);
		constitution_modifier = getModifierNum(constitution);
		intelligence_modifier = getModifierNum(intelligence);
		wisdom_modifier = getModifierNum(wisdom);
		charisma_modifier = getModifierNum(charisma);
		
		hit_points = calHit_points(constitution_modifier,level);
		armor_class = calArmor_class(dexterity_modifier,0);
		attack_bonus = calAttack(level);
		damage_bonus = calDamage(strength_modifier);
		multiple_attacks = getMultiple(attack_bonus);
		for(int i=0;i<7;i++)
		{
			equipID[i] = null;
		}
		for(int i=0;i<10;i++)
		{
			backpackID[i] = null;
		}
		
		viewType = 1;
		setChanged();
		notifyObservers(this);
	}
	public int calHit_points(int cons,int level){
		Random rand = new Random();
		int a = rand.nextInt(10)+1;
		int result = (a+cons)*level;
		return result;
	}
	public int calArmor_class(int dex_modifier,int armor_item){
		int result = 10+dex_modifier+armor_item;
		return result;
	}
	public int calAttack(int level){
		int result = level;
		return result;
	}
	public int calDamage(int stren_modifier){
		int result = stren_modifier;
		return result;
	}
	public int getMultiple(int attack_bonus){
		int result = 0;
		if(attack_bonus<6)
		{
			result = 0;
		}
		if(attack_bonus>=6&&attack_bonus<11)
		{
			result = 1;
		}
		if(attack_bonus>=11&&attack_bonus<16)
		{
			result = 2;
		}
		if(attack_bonus>=16)
		{
			result = 3;
		}
		return result;
	}
	public int get4d6Number(){
		Random rand = new Random();
		int randNum[] = new int[4];
		randNum[0] = rand.nextInt(6)+1;
		randNum[1] = rand.nextInt(6)+1;
		randNum[2] = rand.nextInt(6)+1;
		randNum[3] = rand.nextInt(6)+1;
		for(int i=0;i<4;i++)
		{
			for(int j=1;j<3;j++)
			{
				if(randNum[i]<randNum[j])
				{
					int buffer = randNum[i];
					randNum[i] = randNum[j];
					randNum[j] = buffer;
				}
			}
		}
		int result = randNum[0]+randNum[1]+randNum[2];
		
		return result;
	}
	public int getModifierNum(int score){
		int result = 0;
		if(score == 1)
		{
			result = -5;
		}
		if((score>=2)&&(score<=3))
		{
			result = -4;
		}
		if((score>=4)&&(score<=5))
		{
			result = -3;
		}
		if((score>=6)&&(score<=7))
		{
			result = -2;
		}
		if((score>=8)&&(score<=9))
		{
			result = -1;
		}
		if((score>=10)&&(score<=11))
		{
			result = 0;
		}
		if((score>=12)&&(score<=13))
		{
			result = 1;
		}
		if((score>=14)&&(score<=15))
		{
			result = 2;
		}
		if((score>=16)&&(score<=17))
		{
			result = 3;
		}
		if((score>=18)&&(score<=19))
		{
			result = 4;
		}
		if((score>=20)&&(score<=21))
		{
			result = 5;
		}
		
		return result;
	}
	
	public String modifierChange(String newMessage[]){
		String result = new String();
		strength_modifier = getModifierNum(strength);
		dexterity_modifier = getModifierNum(dexterity);
		constitution_modifier = getModifierNum(constitution);
		intelligence_modifier = getModifierNum(intelligence);
		wisdom_modifier = getModifierNum(wisdom);
		charisma_modifier = getModifierNum(charisma);
		
		hit_points = calHit_points(constitution_modifier, Integer.parseInt(newMessage[1]));
		armor_class = calArmor_class(dexterity_modifier, armor_class);
		attack_bonus = calAttack(Integer.parseInt(newMessage[1]));
		damage_bonus = calDamage(strength_modifier);
		multiple_attacks = getMultiple(attack_bonus);
		result = hit_points+" "+armor_class+" "+attack_bonus+" "+damage_bonus+" "+multiple_attacks+
				 " "+strength_modifier+" "+dexterity_modifier+" "+constitution_modifier+" "+intelligence_modifier
				 +" "+wisdom_modifier+" "+charisma_modifier+" ";
		return result;
	}
	
	public Boolean replaceCharacter(String newMessage[]) throws IOException{
		String buffer = new String();
		String modifier_message = new String();
		String message = new String();
		ArrayList <String> characterList = new ArrayList();
		File file = new File("src/file/character.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		modifier_message = modifierChange(newMessage);
		while((buffer = BF.readLine()) != null)
		{
			String str[] = buffer.trim().split(" ");
			if(str[0].equals(newMessage[0]))
			{
				System.out.println("run1");
				for(int i=0;i<newMessage.length;i++)
				{
					message = message + newMessage[i]+" ";	
				}
				message = message + modifier_message;
				for(int i=0;i<7;i++)
				{
					message = message + equipID[i] +" ";
				}
				for(int i=0;i<10;i++)
				{
					message = message + backpackID[i] + " ";
				}
				message = message+picNumber+"\r\n";
				characterList.add(message);
			}
			else
			{
				message = new String();
				System.out.println("run2");
				for(int i=0;i<37;i++)
				{
					message = message + str[i] +" ";
				}
				message = message + "\r\n";
				characterList.add(message);
			}
		}
		file.delete();
		file = new File("src/file/character.txt");
		FileOutputStream os;
		os = new FileOutputStream(file,true);
		for(int i=0;i<characterList.size();i++)
		{
			System.out.println("run3");
			os.write(characterList.get(i).getBytes());
		}
		os.close();
		characterID = newMessage[0];
		level = Integer.parseInt(newMessage[1]);
		ability_scores = Integer.parseInt(newMessage[2]);
		hit_points = Integer.parseInt(newMessage[3]);
		armor_class = Integer.parseInt(newMessage[4]);
		attack_bonus = Integer.parseInt(newMessage[5]);
		damage_bonus = Integer.parseInt(newMessage[6]);
		level_ori = level;
	    ability_scores_ori = ability_scores;
	    hit_points_ori = hit_points;
	    armor_class_ori = armor_class;
	    attack_bonus_ori = attack_bonus;
	    damage_bonus_ori = damage_bonus;
	    return true;
	}
	
	public Boolean saveChar(String message) throws IOException{
		File logFile = new File("src/file/character.txt");
		FileOutputStream os;
	    os = new FileOutputStream(logFile,true);
        os.write(message.getBytes());
	    os.close();
	    return true;
	}
	public int getTotalChar() throws IOException{
		String buffer = new String();
		int count = 0;
		File file = new File("src/file/character.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			count++;
		}
		return count;
	}
	
	public void loadChar(String newCharacterID) throws IOException {
		// TODO Auto-generated method stub
		//total 37 data
		String charID[] = newCharacterID.trim().split("r");
		String buffer = new String();
		File file = new File("src/file/character.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			String str[] = buffer.trim().split(" ");
			if(str[0].equals(charID[2]))
			{
				characterID = str[0];
				level = Integer.parseInt(str[1]);
				strength = Integer.parseInt(str[2]);
				dexterity = Integer.parseInt(str[3]);
				constitution = Integer.parseInt(str[4]);
				intelligence = Integer.parseInt(str[5]);
				wisdom = Integer.parseInt(str[6]);
				charisma = Integer.parseInt(str[7]);
				strength_modifier = Integer.parseInt(str[13]);
				dexterity_modifier = Integer.parseInt(str[14]);
				constitution_modifier = Integer.parseInt(str[15]);
				intelligence_modifier = Integer.parseInt(str[16]);
				wisdom_modifier = Integer.parseInt(str[17]);
				charisma_modifier = Integer.parseInt(str[18]);
				hit_points = Integer.parseInt(str[8]);
				armor_class = Integer.parseInt(str[9]);
				attack_bonus = Integer.parseInt(str[10]);
				damage_bonus = Integer.parseInt(str[11]);
				multiple_attacks = Integer.parseInt(str[12]);
				picNumber = Integer.parseInt(str[36]);
				for(int i=0;i<7;i++)
				{
					equipID[i] = str[19+i];
					equip[i] = getItemInfo(equipID[i]);
				}
				for(int i=0;i<10;i++)
				{
					backpackID[i] = str[26+i];
					backpack[i] = getItemInfo(backpackID[i]);
				}
				
			}
			
		}
		
	    level_ori = level;
	    ability_scores_ori = ability_scores;
	    hit_points_ori = hit_points;
	    armor_class_ori = armor_class;
	    attack_bonus_ori = attack_bonus;
	    damage_bonus_ori = damage_bonus;
		viewType = 2;
		setChanged();
		notifyObservers(this);
	}
	public String getItemInfo(String itemID) throws IOException{
		String buffer_item = new String();
		String result ="null";
		File file_item = new File("src/file/item.txt");
		BufferedReader BF_item = new BufferedReader(new InputStreamReader(new FileInputStream(file_item),"UTF-8"));
		while((buffer_item = BF_item.readLine()) != null)
		{
			String str_item[] = buffer_item.trim().split(" ");
			if(itemID.equals(str_item[0]))
			{
				result = str_item[1]+" "+str_item[2]+" "+str_item[3];
			}
		}
		return result;
	}
	
	public void editCharacter(){
		
		viewType = 4;
		setChanged();
		notifyObservers(this);
	}
	
	public void setEquipChanged(String changeBefore,String changeAfter){
		
		if(changeBefore == null)//equip item and equip position is null
		{
			String strAfter[] = changeAfter.trim().split(" ");
			strength_ori = strength;
			dexterity_ori = dexterity;
			constitution_ori = constitution;
			intelligence_ori = intelligence;
			wisdom_ori = wisdom;
			charisma_ori = charisma;
			hit_points_ori = hit_points;
			armor_class_ori = armor_class;
			attack_bonus_ori =attack_bonus;
			damage_bonus_ori = damage_bonus;
			
			if(strAfter[1].equals("Intelligence"))
			{
				intelligence = intelligence + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Wisdom"))
			{
				wisdom = wisdom + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Armor_class"))
			{
				armor_class = calArmor_class(dexterity_modifier,armor_class + Integer.parseInt(strAfter[2]));
			}
			if(strAfter[1].equals("Strength"))
			{
				strength = strength + Integer.parseInt(strAfter[2]);
				strength_modifier = strength_modifier + Integer.parseInt(strAfter[2]);
				damage_bonus = calDamage(strength_modifier);
			}
			if(strAfter[1].equals("Constitution"))
			{
				constitution = constitution + Integer.parseInt(strAfter[2]);
				constitution_modifier = constitution_modifier + Integer.parseInt(strAfter[2]);
				hit_points = calHit_points(constitution_modifier,level);
			}
			if(strAfter[1].equals("Charisma"))
			{
				charisma = charisma + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Dexterity"))
			{
				dexterity = dexterity + Integer.parseInt(strAfter[2]);
				dexterity_modifier = dexterity_modifier + Integer.parseInt(strAfter[2]);
				armor_class = calArmor_class(dexterity_modifier,0);
			}
			if(strAfter[1].equals("Attack_bonus"))
			{
				attack_bonus = attack_bonus + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Damage_bonus"))
			{
				damage_bonus = damage_bonus + Integer.parseInt(strAfter[2]);
			}
		}
		else if(changeAfter == null)//unequip item
		{
			String strBefore[] = changeBefore.trim().split(" ");
			strength_ori = strength;
			dexterity_ori = dexterity;
			constitution_ori = constitution;
			intelligence_ori = intelligence;
			wisdom_ori = wisdom;
			charisma_ori = charisma;
			hit_points_ori = hit_points;
			armor_class_ori = armor_class;
			attack_bonus_ori =attack_bonus;
			damage_bonus_ori = damage_bonus;
			if(strBefore[1].equals("Intelligence"))
			{
				intelligence = intelligence - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Wisdom"))
			{
				wisdom = wisdom - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Armor_class"))
			{
				armor_class = calArmor_class(dexterity_modifier,armor_class-Integer.parseInt(strBefore[2]));
			}
			if(strBefore[1].equals("Strength"))
			{
				strength = strength - Integer.parseInt(strBefore[2]);
				strength_modifier = strength_modifier - Integer.parseInt(strBefore[2]);
				damage_bonus = calDamage(strength_modifier);
			}
			if(strBefore[1].equals("Constitution"))
			{
				constitution = constitution - Integer.parseInt(strBefore[2]);
				constitution_modifier = constitution_modifier - Integer.parseInt(strBefore[2]);
				hit_points = calHit_points(constitution_modifier,level);
			}
			if(strBefore[1].equals("Charisma"))
			{
				charisma = charisma - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Dexterity"))
			{
				dexterity = dexterity - Integer.parseInt(strBefore[2]);
				dexterity_modifier = dexterity_modifier - Integer.parseInt(strBefore[2]);
				armor_class = calArmor_class(dexterity_modifier,0);
			}
			if(strBefore[1].equals("Attack_bonus"))
			{
				attack_bonus = attack_bonus - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Damage_bonus"))
			{
				damage_bonus = damage_bonus - Integer.parseInt(strBefore[2]);
			}
			//ability_scores = maxNumber(ability_scores,0);
			//armor_class = maxNumber(armor_class,0);
			//hit_points = maxNumber(hit_points,0);
			//attack_bonus = maxNumber(attack_bonus,0);
			//damage_bonus = maxNumber(damage_bonus,0);
		}
		else if((changeBefore != null)&&(changeAfter != null))//equip item and equip position is not null
		{
			String strBefore[] = changeBefore.trim().split(" ");
			String strAfter[] = changeAfter.trim().split(" ");
			strength_ori = strength;
			dexterity_ori = dexterity;
			constitution_ori = constitution;
			intelligence_ori = intelligence;
			wisdom_ori = wisdom;
			charisma_ori = charisma;
			hit_points_ori = hit_points;
			armor_class_ori = armor_class;
			attack_bonus_ori =attack_bonus;
			damage_bonus_ori = damage_bonus;
			if(strBefore[1].equals("Intelligence"))
			{
				intelligence = intelligence - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Wisdom"))
			{
				wisdom = wisdom - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Armor_class"))
			{
				armor_class = calArmor_class(dexterity_modifier,armor_class-Integer.parseInt(strBefore[2]));
			}
			if(strBefore[1].equals("Strength"))
			{
				strength = strength - Integer.parseInt(strBefore[2]);
				strength_modifier = strength_modifier - Integer.parseInt(strBefore[2]);
				damage_bonus = calDamage(strength_modifier);
			}
			if(strBefore[1].equals("Constitution"))
			{
				constitution = constitution - Integer.parseInt(strBefore[2]);
				constitution_modifier = constitution_modifier - Integer.parseInt(strBefore[2]);
				hit_points = calHit_points(constitution_modifier,level);
			}
			if(strBefore[1].equals("Charisma"))
			{
				charisma = charisma - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Dexterity"))
			{
				dexterity = dexterity - Integer.parseInt(strBefore[2]);
				dexterity_modifier = dexterity_modifier - Integer.parseInt(strBefore[2]);
				armor_class = calArmor_class(dexterity_modifier,0);
			}
			if(strBefore[1].equals("Attack_bonus"))
			{
				attack_bonus = attack_bonus - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Damage_bonus"))
			{
				damage_bonus = damage_bonus - Integer.parseInt(strBefore[2]);
			}
			//equip
			if(strAfter[1].equals("Intelligence"))
			{
				intelligence = intelligence + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Wisdom"))
			{
				wisdom = wisdom + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Armor_class"))
			{
				armor_class = calArmor_class(dexterity_modifier,armor_class + Integer.parseInt(strAfter[2]));
			}
			if(strAfter[1].equals("Strength"))
			{
				strength = strength + Integer.parseInt(strAfter[2]);
				strength_modifier = strength_modifier + Integer.parseInt(strAfter[2]);
				damage_bonus = calDamage(strength_modifier);
			}
			if(strAfter[1].equals("Constitution"))
			{
				constitution = constitution + Integer.parseInt(strAfter[2]);
				constitution_modifier = constitution_modifier + Integer.parseInt(strAfter[2]);
				hit_points = calHit_points(constitution_modifier,level);
			}
			if(strAfter[1].equals("Charisma"))
			{
				charisma = charisma + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Dexterity"))
			{
				dexterity = dexterity + Integer.parseInt(strAfter[2]);
				dexterity_modifier = dexterity_modifier + Integer.parseInt(strAfter[2]);
				armor_class = calArmor_class(dexterity_modifier,0);
			}
			if(strAfter[1].equals("Attack_bonus"))
			{
				attack_bonus = attack_bonus + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Damage_bonus"))
			{
				damage_bonus = damage_bonus + Integer.parseInt(strAfter[2]);
			}
			
		}
		viewType = 3;
		setChanged();
		notifyObservers(this);
		
	}
	
	public int maxNumber(int a, int b){
		if(a>b)
		{
			return a;
		}
		else
			return b;
	}
	public void setItem(String itemInfo){
		String str[] = itemInfo.trim().split(" ");
		int count = 0;
		for(int i =0;i<10;i++)
		{
			if((backpackID[i] == null)||(backpackID[i].equals("null")))
			{
				String strID[] = str[0].trim().split("m");
				System.out.println(strID[1]);
				backpackID[i] = strID[1];
				break;
			}
			count++;
		}
	}

/**************************added**************/
	
	private
	int level;
    int ability_scores;
    int ability_modifiers;
    int hit_points;
    int armor_class ;
    int attack_bonus ;
    int damage_bonus ;
    int level_ori;
    
    int strength;
    int dexterity;
    int constitution;
    int intelligence;
    int wisdom;
    int charisma;
    int strength_ori;
    int dexterity_ori;
    int constitution_ori;
    int intelligence_ori;
    int wisdom_ori;
    int charisma_ori;
    
    int strength_modifier;
    int dexterity_modifier;
    int constitution_modifier;
    int intelligence_modifier;
    int wisdom_modifier;
    int charisma_modifier;
    
    int ability_scores_ori;
    int ability_modifiers_ori;
    int hit_points_ori;
    int armor_class_ori ;
    int attack_bonus_ori ;
    int damage_bonus_ori ;
    int multiple_attacks; //
    int viewType = 0;//view type
    int picNumber;
    String characterID;
    String backpack[] = new String[10];
    String equip[] = new String[7];//0:helmet 1:armor 2:shield 3:ring 4:belt 5:boots 6:weapon
    String backpackID[] = new String[10];
    String equipID[] = new String[7];
    
    
    public int getLevel(){
    	return level;
    }
    public int getAbility_scores(){
    	return ability_scores;
    }
    public int getAbility_modifiers(){
    	return ability_modifiers;
    }
    public int getHit_points(){
    	return hit_points;
    }
    public int getArmor_class(){
    	return armor_class;
    }
    public int getAttack_bonus(){
    	return attack_bonus;
    }
    public int getDamage_bonus(){
    	return damage_bonus;
    }
    public int getMultiple_attacks(){
    	return multiple_attacks;
    }
    public int getLevelOri(){
    	return level_ori;
    }
    public int getAbility_scoresOri(){
    	return ability_scores_ori;
    }
    public int getAbility_modifiersOri(){
    	return ability_modifiers_ori;
    }
    public int getHit_pointsOri(){
    	return hit_points_ori;
    }
    public int getArmor_classOri(){
    	return armor_class_ori;
    }
    public int getAttack_bonusOri(){
    	return attack_bonus_ori;
    }
    public int getDamage_bonusOri(){
    	return damage_bonus_ori;
    }
    
    public int getViewType(){
    	return viewType;
    }
    public String[] getBackpack(){
    	return backpack;
    }
    public String[] getEquip(){
    	return equip;
    }
    public String getCharacterID(){
    	return characterID;
    }
    public int getPicNumber(){
    	return picNumber;
    }
    public String[] getScore(int index){
    	String result[] = new String[2];//total 18
    	if(index == 0)
    	{
    		result[0] = "Level";
    		result[1] = level+"";
    	}
    	if(index == 1)
    	{
    		result[0] = "Strength";
    		result[1] = strength+"";
    	}
    	if(index == 2)
    	{
    		result[0] = "Dexterity";
    		result[1] = dexterity+"";
    	}
    	if(index == 3)
    	{
    		result[0] = "Constitution";
    		result[1] = constitution+"";
    	}
    	if(index == 4)
    	{
    		result[0] = "Intelligence";
    		result[1] = intelligence+"";
    	}
    	if(index == 5)
    	{
    		result[0] = "Wisdom";
    		result[1] = wisdom+"";
    	}
    	if(index == 6)
    	{
    		result[0] = "Charisma";
    		result[1] = charisma+"";
    	}
    	if(index == 7)
    	{
    		result[0] = "Hit points";
    		result[1] = hit_points+"";
    	}
    	if(index == 8)
    	{
    		result[0] = "Armor class";
    		result[1] = armor_class+"";
    	}
    	if(index == 9)
    	{
    		result[0] = "Attack bonus";
    		result[1] = attack_bonus+"";
    	}
    	if(index == 10)
    	{
    		result[0] = "Damage bonus";
    		result[1] = damage_bonus+"";
    	}
    	if(index == 12)
    	{
    		result[0] = "Strength modifier";
    		result[1] = strength_modifier+"";
    	}
    	if(index == 13)
    	{
    		result[0] = "Dexterity modifier";
    		result[1] = dexterity_modifier+"";
    	}
    	if(index == 14)
    	{
    		result[0] = "Constitution modifier";
    		result[1] = constitution_modifier+"";
    	}
    	if(index == 15)
    	{
    		result[0] = "Intelligence modifier";
    		result[1] = intelligence_modifier+"";
    	}
    	if(index == 16)
    	{
    		result[0] = "Wisdom modifier";
    		result[1] = wisdom_modifier+"";
    	}
    	if(index == 17)
    	{
    		result[0] = "Charisma modifier";
    		result[1] = charisma_modifier+"";
    	}
    	if(index == 11)
    	{
    		result[0] = "Multiple attacks";
    		result[1] = multiple_attacks+"";
    	}
    	return result;
    }
    public String[] getScore_ori(int index){
    	String result[] = new String[2];//total 11
    	if(index == 0)
    	{
    		result[0] = "Level";
    		result[1] = level_ori+"";
    	}
    	if(index == 1)
    	{
    		result[0] = "Strength";
    		result[1] = strength_ori+"";
    	}
    	if(index == 2)
    	{
    		result[0] = "Dexterity";
    		result[1] = dexterity_ori+"";
    	}
    	if(index == 3)
    	{
    		result[0] = "Constitution";
    		result[1] = constitution_ori+"";
    	}
    	if(index == 4)
    	{
    		result[0] = "Intelligence";
    		result[1] = intelligence_ori+"";
    	}
    	if(index == 5)
    	{
    		result[0] = "Wisdom";
    		result[1] = wisdom_ori+"";
    	}
    	if(index == 6)
    	{
    		result[0] = "Charisma";
    		result[1] = charisma_ori+"";
    	}
    	if(index == 7)
    	{
    		result[0] = "Hit points";
    		result[1] = hit_points_ori+"";
    	}
    	if(index == 8)
    	{
    		result[0] = "Armor class";
    		result[1] = armor_class_ori+"";
    	}
    	if(index == 9)
    	{
    		result[0] = "Attack bonus";
    		result[1] = attack_bonus_ori+"";
    	}
    	if(index == 10)
    	{
    		result[0] = "Damage bonus";
    		result[1] = damage_bonus_ori+"";
    	}
    	return result;
    }
    public String[] getEquipID(){
    	return equipID;
    }
    public String[] getBackpackID(){
    	return backpackID;
    }
   
}
