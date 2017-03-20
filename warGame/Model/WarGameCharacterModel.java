package warGame.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.JButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import warGame.Builder.*;
import warGame.Builder.Character;


/**
 * This class is a character model contains methods that allows user to :
 * <p>Create a new fighter character following the d20 game rules<br/>
 * <p>Set level, ability scores, ability modifiers, hit points, armor class, attack bonus, damage bonus,multiple attacks, owned items<br/>
 * <p>Edit elements of the character<br/>
 * <p>Show the character including all the elements set/edited by the user<br/>
 * <p>Save the character information in the file<br/>
 * <p>Validate the character before saving<bt/>
 * <p>Load the character from the file<br/>
 * @version build 1
 */

public class WarGameCharacterModel extends Observable {

	public WarGameCharacterModel(){
		
	}
	public  WarGameCharacterModel(int picID,WarGameCharacterModel o){
		this.characterID = o.getCharacterID();
    	this.level = o.getLevel();
    	String result[] = o.getScore(0);
    	this.strength = Integer.parseInt(result[1]);
    	result = o.getScore(1);
    	this.dexterity = Integer.parseInt(result[1]);
    	result = o.getScore(2);
    	this.constitution = Integer.parseInt(result[1]);
    	result = o.getScore(3);
    	this.intelligence = Integer.parseInt(result[1]);
    	result = o.getScore(4);
    	this.wisdom = Integer.parseInt(result[1]);
    	result = o.getScore(5);
    	this.charisma = Integer.parseInt(result[1]);
    	result = o.getScore(6);
        this.hit_points = Integer.parseInt(result[1]);
        result = o.getScore(7);
        this.armor_class = Integer.parseInt(result[1]);
        result = o.getScore(8);
        this.attack_bonus = Integer.parseInt(result[1]);
        result = o.getScore(10);
        this.damage_bonus = Integer.parseInt(result[1]);
        result = o.getScore(11);
        this.multiple_attacks = Integer.parseInt(result[1]);
        result = o.getScore(12);
    	this.strength_modifier = Integer.parseInt(result[1]);
    	result = o.getScore(13);
    	this.dexterity_modifier = Integer.parseInt(result[1]);
    	result = o.getScore(14);
    	this.constitution_modifier = Integer.parseInt(result[1]);
    	result = o.getScore(15);
    	this.intelligence_modifier = Integer.parseInt(result[1]);
    	result = o.getScore(16);
    	this.wisdom_modifier = Integer.parseInt(result[1]);
    	result = o.getScore(17);
    	this.charisma_modifier = Integer.parseInt(result[1]);
    	this.equip = o.getEquip();
    	this.backpack = o.getBackpack();
    	this.picNumber = o.getPicNumber();
	}

   
    
    /**
     * Create a new character by setting the character's level, ability scores(4d6), ability modifiers, hit points, armor class, attack bonus, damage bonus, multiple attacks, owned items.
     * <p> The ability score of the character consists of Strength, Dexterity, Constitution, Intelligence, Wisdom or Charisma that are generated randomly using the 4d6 generation method.<br/>
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    
    
    
	public void createCharacter(int charType) throws UnsupportedEncodingException, FileNotFoundException {
		// TODO Auto-generated method character = new Character();
		Random rand = new Random();
		int a = rand.nextInt(21)+1;
		level = a;
		a = rand.nextInt(8)+1;
		picNumber = a;
		
		Explorer explorer;
		Character bully, nimble, tank;
		
		CharacterBuilder bullyBuilder = new BullyCharacterBuilder();
		CharacterBuilder nimbleBuilder = new NimbleCharacterBuilder();
		CharacterBuilder tankBuilder = new TankCharacterBuilder();
		
		explorer = new Explorer();
		if(charType == 0)
		{
			explorer.setBuilder(bullyBuilder);
			explorer.constructCharacter();
			bully = explorer.getCharacter();
			strength = bully.getStrength();
			dexterity = bully.getDexterity();
			constitution = bully.getConstitution();
			intelligence = bully.getIntelligence();
			wisdom = bully.getWisdom();
			charisma = bully.getCharisma();
		}
		if(charType == 1)
		{
			explorer.setBuilder(nimbleBuilder);
			explorer.constructCharacter();
			nimble = explorer.getCharacter();
			strength = nimble.getStrength();
			dexterity = nimble.getDexterity();
			constitution = nimble.getConstitution();
			intelligence = nimble.getIntelligence();
			wisdom = nimble.getWisdom();
			charisma = nimble.getCharisma();
		}
		if(charType == 2)
		{
			explorer.setBuilder(tankBuilder);
			explorer.constructCharacter();
			tank = explorer.getCharacter();
			strength = tank.getStrength();
			dexterity = tank.getDexterity();
			constitution = tank.getConstitution();
			intelligence = tank.getIntelligence();
			wisdom = tank.getWisdom();
			charisma = tank.getCharisma();
		}
		
		/*strength = get4d6Number();
		dexterity = get4d6Number();
		constitution = get4d6Number();
		intelligence = get4d6Number();
		wisdom = get4d6Number();
		charisma = get4d6Number();*/
		strength_modifier = getModifierNum(strength);
		dexterity_modifier = getModifierNum(dexterity);
		constitution_modifier = getModifierNum(constitution);
		intelligence_modifier = getModifierNum(intelligence);
		wisdom_modifier = getModifierNum(wisdom);
		charisma_modifier = getModifierNum(charisma);
		
		for(int i=0;i<7;i++)
		{
			equipID[i] = null;
			equip[i] = "null";
		}
		for(int i=0;i<10;i++)
		{
			backpackID[i] = null;
			backpack[i] = "null";
		}
		
		hit_points = calHit_points(constitution_modifier,level);
		armor_class = calArmor_class(dexterity_modifier,0);
		attack_bonus = calAttack(strength_modifier,level);
		damage_bonus = calDamage(strength_modifier,"null");
		multiple_attacks = getMultiple(attack_bonus);
		characterID = Integer.parseInt(lastMapID())+1+"";
		
	}
	
	/**
	 * Show the create character frame
	 */
	
	public void createCharacterFrame(){
		viewType = 1;
		setChanged();
		notifyObservers(this);
	}
    
    
    /**
     * Calculate the hit points.
     * @param cons the constitution of the character
     * @param level the level of the character
     * @return
     */
    
	public int calHit_points(int cons,int level){
		Random rand = new Random();
		int a = rand.nextInt(10)+1;
		int result = (a+cons)*level;
		return result;
	}
    
    
    /**
     * Calculate the armor class.
     * @param dex_modifier the midifier value of the dexterity
     * @param armor_item the sum of the items with armor class
     * @return
     */
    
	public int calArmor_class(int dex_modifier,int armor_item){
		int result = 10+dex_modifier+armor_item;
		return result;
	}
    
    /**
     * Calculate the attack bonus.
     * @param level the level of the character
     * @return
     */
    
	public int calAttack(int strength_modifier,int level){
		int result = strength_modifier+level;
		return result;
	}
    
    /**
     * Calculate the damage bonus.
     * @param stren_modifier the midifier value of the strength
     * @return
     */
    
	public int calDamage(int stren_modifier,String weapon){
		int result = 0;
		if(weapon.equals("null"))
		{
			result = 0;
		}
		else
		{
			String str[] = weapon.trim().split(" ");
			if(str[1].equals("Damage_bonus"))
			{
				result = stren_modifier+Integer.parseInt(str[2]);
			}
			else
			{
				result = stren_modifier;
			}
		}
		return result;
	}
    
    /**
     * Calculate the multiple attacks.
     * @param attack_bonus the value of attack bonus
     * @return
     */
    
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
    
    /**
     * Roll foue six-sided dice and sum the biggest three number.
     * @return
     */
    
	public int get4d6Number(){
		Random rand = new Random();
		int randNum[] = new int[4];
		randNum[0] = rand.nextInt(6)+1;
		randNum[1] = rand.nextInt(6)+1;
		randNum[2] = rand.nextInt(6)+1;
		randNum[3] = rand.nextInt(6)+1;
		/*for(int i=0;i<4;i++)
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
		}*/
		int result = randNum[0]+randNum[1]+randNum[2]+randNum[3];
		
		return result;
	}
    
    /**
     * Calculate the ability modifier.
     * <p>get the ability modifier according to the table<br/>
     * @param score the value of rolled dice
     * @return
     */
    
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
		if((score>=22)&&(score<=23))
		{
			result = 6;
		}
		if((score>=24)&&(score<=25))
		{
			result = 7;
		}
		
		return result;
	}
	
    /**
     * Calculate the armor class.
     * @param newMessage[] change to the value of modifiers
     * @return
     */
    
	public String modifierChange(String newMessage[]){
		String result = new String();
		strength_modifier = getModifierNum(Integer.parseInt(newMessage[2]));
		dexterity_modifier = getModifierNum(Integer.parseInt(newMessage[3]));
		constitution_modifier = getModifierNum(Integer.parseInt(newMessage[4]));
		intelligence_modifier = getModifierNum(Integer.parseInt(newMessage[5]));
		wisdom_modifier = getModifierNum(Integer.parseInt(newMessage[6]));
		charisma_modifier = getModifierNum(Integer.parseInt(newMessage[7]));
		
		hit_points = calHit_points(constitution_modifier, Integer.parseInt(newMessage[1]));
		armor_class = 0;
		for(int i=0;i<7;i++)
		{
			if(!equip[i].equals("null"))
			{
				String str[] = equip[i].trim().split(" ");
				if(str[1].equals("Armor_class"))
				{
					armor_class = armor_class+ Integer.parseInt(str[2]);
				}
			}
		}
		armor_class = calArmor_class(dexterity_modifier, armor_class);
		attack_bonus = calAttack(strength_modifier,Integer.parseInt(newMessage[1]));
		damage_bonus = calDamage(strength_modifier,equip[6]);
		multiple_attacks = getMultiple(attack_bonus);
		result = hit_points+" "+armor_class+" "+attack_bonus+" "+damage_bonus+" "+multiple_attacks+
				 " "+strength_modifier+" "+dexterity_modifier+" "+constitution_modifier+" "+intelligence_modifier
				 +" "+wisdom_modifier+" "+charisma_modifier+" ";
		return result;
	}
	
    /**
     * Validate the new modifier value.
     * @param newMessage[]
     * @throws IOException
     * @return
     */
    
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
				//System.out.println("run1");
				message = new String();
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
				//System.out.println("run2");
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
			//System.out.println("run3");
			//System.out.println(characterList.get(i));
			os.write(characterList.get(i).getBytes());
		}
		os.close();
		characterID = newMessage[0];
		level = Integer.parseInt(newMessage[1]);
		strength = Integer.parseInt(newMessage[2]);
		dexterity = Integer.parseInt(newMessage[3]);
		constitution = Integer.parseInt(newMessage[4]);
		intelligence = Integer.parseInt(newMessage[5]);
		wisdom = Integer.parseInt(newMessage[6]);
		charisma = Integer.parseInt(newMessage[7]);
		level_ori = level;
		strength_ori = strength;
		dexterity_ori = dexterity;
		constitution_ori = constitution;
		intelligence_ori = intelligence;
		wisdom_ori = wisdom;
		charisma_ori = charisma;
	    return true;
	}
	
    /**
     * Validate the output.
     * @param message
     * @throws IOException
     * @return
     */
    
	public Boolean saveChar(String message) throws IOException{
		File logFile = new File("src/file/character.txt");
		FileOutputStream os;
	    os = new FileOutputStream(logFile,true);
        os.write(message.getBytes());
	    os.close();
	    return true;
	}
    
    /**
     * Get the character number.
     * @throws IOException
     * @return
     */
    
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
	
    /**
     * Load the character information.
     * @param newCharacterID
     * @throws IOException
     */
    
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
	    hit_points_ori = hit_points;
	    armor_class_ori = armor_class;
	    attack_bonus_ori = attack_bonus;
	    damage_bonus_ori = damage_bonus;
		viewType = 2;
		setChanged();
		notifyObservers(this);
	}
    
    /**
     * Get the item information.
     * @param itemID
     * @throws IOException
     * @return
     */
    
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
	
    /**
     * Edit the character.
     */
    
	public void editCharacter(){
		
		viewType = 4;
		setChanged();
		notifyObservers(this);
	}
	
    /**
     * Edit the character.
     * @param changeBefore
     * @param changeAfter
     */
    
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
				intelligence_modifier = intelligence_modifier + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Wisdom"))
			{
				wisdom = wisdom + Integer.parseInt(strAfter[2]);
				wisdom_modifier = wisdom_modifier + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Armor_class"))
			{
				armor_class = 0;
				for(int i=0;i<7;i++)
				{
					if(!equip[i].equals("null"))
					{
						String str[] = equip[i].trim().split(" ");
						if(str[1].equals("Armor_class"))
						{
							//System.out.println(armor_class);
							armor_class = armor_class+ Integer.parseInt(str[2]);
						}
					}
				}
				armor_class = calArmor_class(dexterity_modifier,armor_class);
			}
			if(strAfter[1].equals("Strength"))
			{
				strength = strength + Integer.parseInt(strAfter[2]);
				strength_modifier = strength_modifier + Integer.parseInt(strAfter[2]);
				damage_bonus = calDamage(strength_modifier,equip[6]);
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
				charisma_modifier = charisma_modifier + Integer.parseInt(strAfter[2]);
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
				damage_bonus = strength_modifier;
			}
			if(strAfter[1].equals("Damage_bonus"))
			{
				damage_bonus = strength_modifier + Integer.parseInt(strAfter[2]);
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
				intelligence_modifier = intelligence_modifier - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Wisdom"))
			{
				wisdom = wisdom - Integer.parseInt(strBefore[2]);
				wisdom_modifier = wisdom_modifier - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Armor_class"))
			{
				armor_class = 0;
				for(int i=0;i<7;i++)
				{
					if(!equip[i].equals("null"))
					{
						String str[] = equip[i].trim().split(" ");
						if(str[1].equals("Armor_class"))
						{
							armor_class = armor_class+ Integer.parseInt(str[2]);
						}
					}
				}
				armor_class = calArmor_class(dexterity_modifier,armor_class);
			}
			if(strBefore[1].equals("Strength"))
			{
				strength = strength - Integer.parseInt(strBefore[2]);
				strength_modifier = strength_modifier - Integer.parseInt(strBefore[2]);
				damage_bonus = calDamage(strength_modifier,equip[6]);
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
				charisma_modifier = charisma_modifier - Integer.parseInt(strBefore[2]);
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
				damage_bonus = 0;
			}
			if(strBefore[1].equals("Damage_bonus"))
			{
				damage_bonus = 0;
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
				intelligence_modifier = intelligence_modifier - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Wisdom"))
			{
				wisdom = wisdom - Integer.parseInt(strBefore[2]);
				wisdom_modifier = wisdom_modifier - Integer.parseInt(strBefore[2]);
			}
			if(strBefore[1].equals("Armor_class"))
			{
				armor_class = 0;
				for(int i=0;i<7;i++)
				{
					if(!equip[i].equals("null"))
					{
						String str[] = equip[i].trim().split(" ");
						if(str[1].equals("Armor_class"))
						{
							armor_class = armor_class+ Integer.parseInt(str[2]);
						}
					}
				}
				armor_class = calArmor_class(dexterity_modifier,armor_class);
			}
			if(strBefore[1].equals("Strength"))
			{
				strength = strength - Integer.parseInt(strBefore[2]);
				strength_modifier = strength_modifier - Integer.parseInt(strBefore[2]);
				damage_bonus = calDamage(strength_modifier,equip[6]);
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
				charisma_modifier = charisma_modifier - Integer.parseInt(strBefore[2]);
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
				damage_bonus = 0;
			}
			if(strBefore[1].equals("Damage_bonus"))
			{
				damage_bonus = 0;
			}
			//equip
			if(strAfter[1].equals("Intelligence"))
			{
				intelligence = intelligence + Integer.parseInt(strAfter[2]);
				intelligence_modifier = intelligence_modifier + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Wisdom"))
			{
				wisdom = wisdom + Integer.parseInt(strAfter[2]);
				wisdom_modifier = wisdom_modifier + Integer.parseInt(strAfter[2]);
			}
			if(strAfter[1].equals("Armor_class"))
			{
				armor_class = 0;
				for(int i=0;i<7;i++)
				{
					if(!equip[i].equals("null"))
					{
						String str[] = equip[i].trim().split(" ");
						if(str[1].equals("Armor_class"))
						{
							armor_class = armor_class+ Integer.parseInt(str[2]);
						}
					}
				}
				armor_class = calArmor_class(dexterity_modifier,armor_class);
			}
			if(strAfter[1].equals("Strength"))
			{
				strength = strength + Integer.parseInt(strAfter[2]);
				strength_modifier = strength_modifier + Integer.parseInt(strAfter[2]);
				damage_bonus = calDamage(strength_modifier,equip[6]);
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
				charisma_modifier = charisma_modifier + Integer.parseInt(strAfter[2]);
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
				damage_bonus = strength_modifier;
			}
			if(strAfter[1].equals("Damage_bonus"))
			{
				damage_bonus = strength_modifier + Integer.parseInt(strAfter[2]);
			}
			
		}
		viewType = 3;
		setChanged();
		notifyObservers(this);
		
	}
	
    /**
     * Compare the value of two value and return the larger one.
     * @param a
     * @param b
     * @return
     */
    
	public int maxNumber(int a, int b){
		if(a>b)
		{
			return a;
		}
		else
			return b;
	}
    
    /**
     * Set the item.
     * @param itemInfo
     */
    
	public void setItem(String itemInfo){
		String str[] = itemInfo.trim().split(" ");
		int count = 0;
		for(int i =0;i<10;i++)
		{
			if((backpack[i] == null)||(backpack[i].equals("null")))
			{
				//String strID[] = str[0].trim().split("m");
				//System.out.println(strID[1]);
				backpack[i] = str[1]+" "+str[2]+" "+str[3];
				break;
			}
			count++;
		}
	}
	
	/**
	 * @return the last key of the map in the json file
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public String lastMapID() throws UnsupportedEncodingException, FileNotFoundException{
		ArrayList<String> allKeysInMap = new ArrayList<String>();
		Map<String, WarGameCharacterModel> mapsByMap = WarGameCharacterModel.listAllCharacters();
		Iterator<Entry<String, WarGameCharacterModel>> it = mapsByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WarGameCharacterModel> entry = (Map.Entry<String, WarGameCharacterModel>)it.next();
			allKeysInMap.add(entry.getKey());
		}
		String lastMapID = allKeysInMap.get(allKeysInMap.size()-1);
		return lastMapID;
	}
	
	public static Map<String, WarGameCharacterModel> listAllCharacters() throws UnsupportedEncodingException, FileNotFoundException{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("src/file/characters.json"), "UTF-8");
		Map<String, WarGameCharacterModel> mapsByMap = gson.fromJson(isreader, new TypeToken<Map<String, WarGameCharacterModel>>(){}.getType());
		return mapsByMap;
	}
	
	public Boolean saveCharJson(WarGameCharacterModel characterModel) throws IOException{
		Map<String, WarGameCharacterModel> mapsByMap = WarGameCharacterModel.listAllCharacters();
		//Map<String, WarGameCharacterModel> mapsByMap = null;
		mapsByMap.put(characterModel.getCharacterID(), characterModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/characters.json");
		fw.write(gson.toJson(mapsByMap));
		fw.close();
		return true;
	}
	
	/**
	 * load the specific map by mapID
	 * @param mapID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public void loadCharacterJson(String characterID) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, WarGameCharacterModel> mapsByMap = WarGameCharacterModel.listAllCharacters();
		WarGameCharacterModel characterModel = mapsByMap.get(characterID);
		this.characterID = characterModel.getCharacterID();
    	this.level = characterModel.getLevel();
    	String result[] = characterModel.getScore(0);
    	this.strength = Integer.parseInt(result[1]);
    	result = characterModel.getScore(1);
    	this.dexterity = Integer.parseInt(result[1]);
    	result = characterModel.getScore(2);
    	this.constitution = Integer.parseInt(result[1]);
    	result = characterModel.getScore(3);
    	this.intelligence = Integer.parseInt(result[1]);
    	result = characterModel.getScore(4);
    	this.wisdom = Integer.parseInt(result[1]);
    	result = characterModel.getScore(5);
    	this.charisma = Integer.parseInt(result[1]);
    	result = characterModel.getScore(6);
        this.hit_points = Integer.parseInt(result[1]);
        result = characterModel.getScore(7);
        this.armor_class = Integer.parseInt(result[1]);
        result = characterModel.getScore(8);
        this.attack_bonus = Integer.parseInt(result[1]);
        result = characterModel.getScore(10);
        this.damage_bonus = Integer.parseInt(result[1]);
        result = characterModel.getScore(11);
        this.multiple_attacks = Integer.parseInt(result[1]);
        result = characterModel.getScore(12);
    	this.strength_modifier = Integer.parseInt(result[1]);
    	result = characterModel.getScore(13);
    	this.dexterity_modifier = Integer.parseInt(result[1]);
    	result = characterModel.getScore(14);
    	this.constitution_modifier = Integer.parseInt(result[1]);
    	result = characterModel.getScore(15);
    	this.intelligence_modifier = Integer.parseInt(result[1]);
    	result = characterModel.getScore(16);
    	this.wisdom_modifier = Integer.parseInt(result[1]);
    	result = characterModel.getScore(17);
    	this.charisma_modifier = Integer.parseInt(result[1]);
    	this.equip = characterModel.getEquip();
    	this.backpack = characterModel.getBackpack();
    	this.picNumber = characterModel.getPicNumber();
    	viewType = 2;
    	setChanged();
    	notifyObservers(this);
	}
	
	public Boolean editCharacterJson(String newMessage[]) throws IOException{
		Map<String, WarGameCharacterModel> mapsByMap = WarGameCharacterModel.listAllCharacters();
		WarGameCharacterModel characterModel = mapsByMap.get(newMessage[0]);
		characterModel.level =  Integer.parseInt(newMessage[1]);
		characterModel.strength = Integer.parseInt(newMessage[2]);
		characterModel.dexterity = Integer.parseInt(newMessage[3]);
		characterModel.constitution = Integer.parseInt(newMessage[4]);
		characterModel.intelligence = Integer.parseInt(newMessage[5]);
		characterModel.wisdom = Integer.parseInt(newMessage[6]);
		characterModel.charisma = Integer.parseInt(newMessage[7]);
		String message = modifierChange(newMessage);
		String str[] = message.trim().split(" ");
		characterModel.hit_points = Integer.parseInt(str[0]);
		characterModel.armor_class = Integer.parseInt(str[1]);
		characterModel.attack_bonus = Integer.parseInt(str[2]);
		characterModel.damage_bonus = Integer.parseInt(str[3]);
		characterModel.strength_modifier = Integer.parseInt(str[4]);
		characterModel.dexterity_modifier = Integer.parseInt(str[5]);
		characterModel.constitution_modifier = Integer.parseInt(str[6]);
		characterModel.intelligence_modifier = Integer.parseInt(str[7]);
		characterModel.wisdom_modifier = Integer.parseInt(str[8]);
		characterModel.charisma_modifier = Integer.parseInt(str[9]);
		
		characterID = newMessage[0];
		level = Integer.parseInt(newMessage[1]);
		strength = Integer.parseInt(newMessage[2]);
		dexterity = Integer.parseInt(newMessage[3]);
		constitution = Integer.parseInt(newMessage[4]);
		intelligence = Integer.parseInt(newMessage[5]);
		wisdom = Integer.parseInt(newMessage[6]);
		charisma = Integer.parseInt(newMessage[7]);
		level_ori = level;
		strength_ori = strength;
		dexterity_ori = dexterity;
		constitution_ori = constitution;
		intelligence_ori = intelligence;
		wisdom_ori = wisdom;
		charisma_ori = charisma;
		
		mapsByMap.put(newMessage[0], characterModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/characters.json");
		fw.write(gson.toJson(mapsByMap));
		fw.close();
		return true;
	}
	

/**************************************added*******************************************************/
	
	private
    
    /**
     *<p> level is the level of the character.<br/>
     */
	int level;
	
    /**
     *<p> hit_points is the hit points of the character.<br/>
     */
    int hit_points;
    
    /**
     *<p> armor_class is the armor class of the character.<br/>
     */
    int armor_class ;
    
    /**
     *<p> attack_bonus is the attack bonus value of the character.<br/>
     */
    int attack_bonus ;
    
    /**
     *<p> damage_bonus is the damage bonus value of the character.<br/>
     */
    int damage_bonus ;
    
    /**
     *<p> level_ori is the original level of the character.<br/>
     */
    int level_ori;
    
    /**
     *<p> strength is the strength value of the character.<br/>
     */
    int strength;
    
    /**
     *<p> dexterity is the dexterity value of the character.<br/>
     */
    int dexterity;
    
    /**
     *<p> constitution is the constitution value of the character.<br/>
     */
    int constitution;
    
    /**
     *<p> intelligence is the intelligence value of the character.<br/>
     */
    int intelligence;
    
    /**
     *<p> wisdom is the wisdom value of the character.<br/>
     */
    int wisdom;
    
    /**
     *<p> charisma is the charisma value of the character.<br/>
     */
    int charisma;
    
    /**
     *<p> strength_ori is the original strength value of the character.<br/>
     */
    int strength_ori;
    
    /**
     *<p> dexterity_ori is the original dexterity value of the character.<br/>
     */
    int dexterity_ori;
    
    /**
     *<p> constitution_ori is the original constitution value of the character.<br/>
     */
    int constitution_ori;
    
    /**
     *<p> intelligence_ori is the original intelligence value of the character.<br/>
     */
    int intelligence_ori;
    
    /**
     *<p> wisdom_ori is the original wisdom value of the character.<br/>
     */
    int wisdom_ori;
    
    /**
     *<p> charisma_ori is the original charisma value of the character.<br/>
     */
    int charisma_ori;
    
    /**
     *<p> strength_modifier is the strength modifier value of the character.<br/>
     */
    int strength_modifier;
    
    /**
     *<p> dexterity_modifier is the dexterity modifier value of the character.<br/>
     */
    int dexterity_modifier;
    
    /**
     *<p> constitution_modifier is the constitution modifier value of the character.<br/>
     */
    int constitution_modifier;
    
    /**
     *<p> intelligence_modifier is the intelligence modifier value of the character.<br/>
     */
    int intelligence_modifier;
    
    /**
     *<p> wisdom_modifier is the wisdom modifier value of the character.<br/>
     */
    int wisdom_modifier;
    
    /**
     *<p> charisma_modifier is the charisma modifier value of the character.<br/>
     */
    int charisma_modifier;
    
    /**
     *<p> hit_points_ori is the original hit points value of the character.<br/>
     */
    int hit_points_ori;
    
    /**
     *<p> armor_class_ori is the original armor class value of the character.<br/>
     */
    int armor_class_ori;
    
    /**
     *<p> attack_bonus_ori is the original attack bonus value of the character.<br/>
     */
    int attack_bonus_ori;
    
    /**
     *<p> damage_bonus_ori is the original damage bonus of the character.<br/>
     */
    int damage_bonus_ori;
    
    /**
     *<p> multiple_attacks is the value of multiple attacks of the character.<br/>
     */
    int multiple_attacks; //
    
    /**
     *<p> viewType is the type of the view of diifferent operations.<br/>
     */
    int viewType = 0;//view type
    
    /**
     *<p> picNumber is the ID numeber of the picture.<br/>
     */
    int picNumber;
    
    /**
     *<p> characterID is the ID number of the character.<br/>
     */
    String characterID;
    
    /**
     *<p> backpack[] is the item in the backpack of the character.<br/>
     */
    String backpack[] = new String[10];
    
    /**
     *<p> equip[] is the equipped item of the character.<br/>
     */
    String equip[] = new String[7];//0:helmet 1:armor 2:shield 3:ring 4:belt 5:boots 6:weapon
    
    /**
     *<p> backpackID[] is the ID number of the item in the backpack of the character.<br/>
     */
    String backpackID[] = new String[10];
    
    /**
     *<p> equipID[] is the the ID number of the item equipped of the character.<br/>
     */
    String equipID[] = new String[7];
    
    
    /**
     * <p>get the level of the character<br/>
     */
    public int getLevel(){
    	return level;
    }
    
    
    /**
     * <p>get the original level of the character<br/>
     */
    public int getLevelOri(){
    	return level_ori;
    }
    
    
    /**
     * <p>get the original hit points of the character<br/>
     */
    public int getHit_pointsOri(){
    	return hit_points_ori;
    }
    
    /**
     * <p>get the original armor class of the character<br/>
     */
    public int getArmor_classOri(){
    	return armor_class_ori;
    }
    
    /**
     * <p>get the original attack bonus of the character<br/>
     */
    public int getAttack_bonusOri(){
    	return attack_bonus_ori;
    }
    
    /**
     * <p>get the original damage bonus of the character<br/>
     */
    public int getDamage_bonusOri(){
    	return damage_bonus_ori;
    }
    
    /**
     * <p>get the corresponded frame<br/>
     */
    public int getViewType(){
    	return viewType;
    }
    
    /**
     * <p>get the items in the backpack of the character<br/>
     */
    public String[] getBackpack(){
    	return backpack;
    }
    
    /**
     * <p>get the equipped items of the character<br/>
     */
    public String[] getEquip(){
    	return equip;
    }
    
    /**
     * <p>get the ID number of the character<br/>
     */
    public String getCharacterID(){
    	return characterID;
    }
    
    /**
     * <p>get the picture ID number<br/>
     */
    public int getPicNumber(){
    	return picNumber;
    }
    
    /**
     * <p>get the information of the character<br/>
     */
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
    
    /**
     * <p>get the original information of the character<br/>
     */
    
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
    
    /**
     * <p>get the ID number of the equipped item<br/>
     */
    
    public String[] getEquipID(){
    	return equipID;
    }
    
    /**
     * <p>get the ID number of the item in the backpack<br/>
     */
    
    public String[] getBackpackID(){
    	return backpackID;
    }
    
    /**
     * <p>set the value of the item in the equip<br/>
     */
    
    public void setEquip(String newEquip,int index){
    	equip[index] = newEquip;  	
    }
    
    /**
     * <p>set the value of the item in the backpack<br/>
     */
    
    public void setBackpack(String newBack,int index){
    	backpack[index] = newBack;  	
    }
    
   
}

