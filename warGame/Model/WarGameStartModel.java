package warGame.Model;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;
import java.util.Random;

import warGame.Strategy.CharacterStrategy;
import warGame.View.WarGameStartView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WarGameStartModel extends Observable{
	
	/**
	 * default construct
	 */
	public WarGameStartModel(){}
	
	/**
	 * custom construct
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public WarGameStartModel(WarGameCharacterModel characterToPlay, WarGameCampaignModel campaignToPlay) throws UnsupportedEncodingException, FileNotFoundException{
		this.characterToPlay = characterToPlay;
		this.campaignToPlay = campaignToPlay;
		ArrayList<String> mapLists = campaignToPlay.getMapLists();
		mapsModel = new ArrayList<WarGameMapModel>();
		Map<String, WarGameMapModel> mapsByMap = WarGameMapModel.listAllMaps();		
		for (String mapNameByString : mapLists) {
			for (String mapID : mapsByMap.keySet()) {
				WarGameMapModel temp = new WarGameMapModel();
				temp = mapsByMap.get(mapID);
				if (mapNameByString.equals(temp.getMapName())) {
				mapsModel.add(temp);
				}
			}
		}
		for (WarGameMapModel map : mapsModel) {
			String mapArr[][] = map.getMap();
			for (int i = 0; i < mapArr.length; i++) {
				for (int j = 0; j < mapArr[0].length; j++) {
					if (mapArr[i][j].startsWith("I")) {
						if (i==0) {
							mapArr[i+1][j] = "h"+" "+this.characterToPlay.getCharacterID();
						}else
						if(i==mapArr.length-1){
							mapArr[i-1][j] = "h"+" "+this.characterToPlay.getCharacterID();
						}else
						if (j==0) {
							mapArr[i][j+1] = "h"+" "+this.characterToPlay.getCharacterID();
						}else
						if(j==mapArr[0].length-1){
							mapArr[i][j-1] = "h"+" "+this.characterToPlay.getCharacterID();
						}else{
							mapArr[5][5] = "h"+" "+this.characterToPlay.getCharacterID();
						}
					}
				}
			}
		}
	}
	
	/**
	 * choose campaign and character to start the game
	 */
	public void chooseView(){
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * show map view
	 */
	public void DisplayMapView(){
		setChanged();
		notifyObservers(this);
	}
	
	public void setCharacterModel(WarGameCharacterModel newCharacterModel)
	{
		characterToPlay = newCharacterModel;
	}
	public WarGameCharacterModel getCharacterModel()
	{
		return characterToPlay;
	}
	public void setMapID(String newID){
		mapID = newID;
	}
	public String getMapID()
	{
		return mapID;
	}
	public WarGameCharacterModel characterAdaption(WarGameCharacterModel newCharacterModel){
		return newCharacterModel;
	}
	
	/**
	 * set the adaption of the map
	 *  @param mapModel
	 *  @param level
	 *  @return WarGameMapModel
	 */
	public WarGameMapModel setAdaption(WarGameMapModel mapModel, int level){
		int count=0;
		WarGameItemModel itemModel = new WarGameItemModel();
		for(WarGameCharacterModel characterModel_buffer : mapModel.getContainFriends())
		{
			characterModel_buffer.setLevel(level);
			characterModel_buffer.scoresChange();
			String equip[] = characterModel_buffer.getEquip();
			String backpack[] = characterModel_buffer.getBackpack();
			for(int i =0;i<7;i++)
			{
				if(!equip[i].equals("null"))
				{
					if(i == 6)
					{
						String str[] = equip[i].trim().split(" ");
						String changeBefore = equip[i];
						equip[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level)+" "+str[3]+" "+str[4];
						characterModel_buffer.setEquipChanged(changeBefore, equip[i]);
					}
					else
					{
						String str[] = equip[i].trim().split(" ");
						String changeBefore = equip[i];
						equip[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
						characterModel_buffer.setEquipChanged(changeBefore, equip[i]);
					}
				}
						
			}
			for(int i =0;i<10;i++)
			{
				if(!backpack[i].equals("null"))
				{
					String str[] = backpack[i].trim().split(" ");
					if(str[0].equals("Weapon"))
					{
						backpack[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level)+" "+str[3]+" "+str[4];
					}
					else
					{
						backpack[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
					}
				}
						
			}
			mapModel.getContainFriends().set(count, characterModel_buffer);
			count++;
		}
		count=0;
		for(WarGameCharacterModel characterModel_buffer : mapModel.getContainEnemies())
		{
			characterModel_buffer.setLevel(level);
			characterModel_buffer.scoresChange();
			String equip[] = characterModel_buffer.getEquip();
			String backpack[] = characterModel_buffer.getBackpack();
			for(int i =0;i<7;i++)
			{
				if(!equip[i].equals("null"))
				{
					if(i == 6)
					{
						String str[] = equip[i].trim().split(" ");
						String changeBefore = equip[i];
						equip[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level)+" "+str[3]+" "+str[4];
						characterModel_buffer.setEquipChanged(changeBefore, equip[i]);
					}
					else
					{
						String str[] = equip[i].trim().split(" ");
						String changeBefore = equip[i];
						equip[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
						characterModel_buffer.setEquipChanged(changeBefore, equip[i]);
					}
				}
						
			}
			for(int i =0;i<10;i++)
			{
				if(!backpack[i].equals("null"))
				{
					String str[] = backpack[i].trim().split(" ");
					if(str[0].equals("Weapon"))
					{
						backpack[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level)+" "+str[3]+" "+str[4];
					}
					else
					{
						backpack[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
					}
				}
						
			}
			characterModel_buffer.setLevel(level);
			characterModel_buffer.scoresChange();
			mapModel.getContainEnemies().set(count, characterModel_buffer);
			count++;
		}
		count =0;
		for(WarGameItemModel itemModel_buffer : mapModel.getContainItems())
		{
			String enchanNum = itemModel_buffer.itemAdaption(level);
			itemModel_buffer.setEnchanNum(enchanNum);
			mapModel.getContainItems().set(count, itemModel_buffer);
			count++;
		}
		return mapModel;
		
	}
	
	/**
	 * check the backpack if it is full
	 *  @param newBackpack
	 *  @return boolean
	 */
	public Boolean checkBackpack(String newBackpack[])
	{
		int count =0;
		for(int i=0;i<10;i++)
		{
			if(newBackpack[i].equals("null"))
			{
				break;
			}
			count++;
		}
		if(count == 10)
		{
			return false;
		}
		return true;
	}
	

	/**
	 * character move 4 directions
	 * @return
	 */
	public static WarGameMapModel characterMovement(ArrayList<WarGameMapModel> mapModelLists, String direction){
		WarGameMapModel mapModel = mapModelLists.get(0);
		String map[][] = mapModel.getMap();
		int posX = 0;
		int posY = 0;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j].equals("h")) {
					posY = i;
					posX = j;
				}
			}
		}
		switch (direction) {
		case "left":
			if (map[posY][posX-1].equals("f")) {
				map[posY][posX] = "f";
				map[posY][posX-1] = "h";
				mapModel.setMap(map);
			}
			if (map[posY][posX-1].equals("O")) {
				mapModelLists.remove(0);
				mapModel = mapModelLists.get(0);
			}
			break;
		case "right":
			if (map[posY][posX+1].equals("f")) {
				map[posY][posX] = "f";
				map[posY][posX+1] = "h";
				mapModel.setMap(map);
			}
			if (map[posY][posX+1].equals("O")) {
				mapModelLists.remove(0);
				mapModel = mapModelLists.get(0);
			}
			break;
		case "up":
			if (map[posY-1][posX].equals("f")) {
				map[posY][posX] = "f";
				map[posY-1][posX] = "h";
				mapModel.setMap(map);
			}
			if (map[posY-1][posX].equals("O")) {
				mapModelLists.remove(0);
				mapModel = mapModelLists.get(0);
			}
			break;
		case "down":
			if (map[posY+1][posX].equals("f")) {
				map[posY][posX] = "f";
				map[posY+1][posX] = "h";
				mapModel.setMap(map);
			}
			if (map[posY+1][posX].equals("O")) {
				mapModelLists.remove(0);
				mapModel = mapModelLists.get(0);
			}
			break;

		}
		return mapModel;
	}
	
	/**
	 * calculte the value of two character
	 */
	
	public WarGameCharacterModel fightChange(WarGameCharacterModel character1,WarGameCharacterModel character2)
	{
		WarGameCharacterModel output = new WarGameCharacterModel();
		String hitPoints[] = character2.getScore(7);
		String armorClass[] = character2.getScore(8);
		String attack[] = character1.getScore(9);
		String damage[] = character1.getScore(10);
		ArrayList<String> special = character1.getSpeEnchantment();
		String affect = null;
		for (String string : special) {
			affect += string+" ";
		}
		Random rand = new Random();
		if(Integer.parseInt(hitPoints[1]) > 0)
		{
			int attackRand = rand.nextInt(20)+1;
			int attackTotal = attackRand + Integer.parseInt(attack[1]);
			WarGameStartView.logging("Attack random number is "+attackRand);
			WarGameStartView.logging("Attack ability number is "+attack[1]);
			WarGameStartView.logging("Armor class number is "+armorClass[1]);
			if(attackTotal > Integer.parseInt(armorClass[1]))
			{
				if (special.contains("Slaying")) {
					character2.setHitPoints(0);
					WarGameStartView.logging("Character"+character2.getCharacterID()+" is dead in this attack!");
				}
				else
				{
					int damageRand = rand.nextInt(8)+1;
					WarGameStartView.logging("Damage random number is "+damageRand);
					WarGameStartView.logging("Damage ability number is "+damage[1]);

					int damageTotal = damageRand + Integer.parseInt(damage[1]);
					if (character2.getStatus().contains("Burning")) {
						damageTotal = damageRand +5*(Integer.parseInt(damage[1]));
						WarGameStartView.logging("Burning!");
					}
					int result = Integer.parseInt(hitPoints[1]) - damageTotal;
					WarGameStartView.logging("Character"+character2.getCharacterID()+" lose Hitpoints from "+hitPoints[1]+" to "+result);
					WarGameStartView.logging("Character"+character2.getCharacterID()+" get affected "+affect);
					character2.setStatus(special);
					if(result<1)
					{
						WarGameStartView.logging("Character"+character2.getCharacterID()+" is dead in this attack!");
					}
					character2.setHitPoints(result);
					output = character2;
				}
			}
			else
			{
				WarGameStartView.logging("Character"+character2.getCharacterID()+" can't be attacked, because Armor class too high!");
			}
		}
		
		return output;
	}
	
	//build3
		/**
		 * strategy
		 */
		private CharacterStrategy strategy;
		/**
		 * character for strategy
		 */
		private WarGameCharacterModel characterForStrategy;
		/**
		 * Plugs in a specific strategr to be used
		 *@param strategy the turn method to be aplied 
		*/
		public void setStrategy(CharacterStrategy strategy, WarGameCharacterModel character){
			this.strategy = strategy;
			this.characterForStrategy = character;
		}
		/**
		 * turn method to decide the action for all players on the map
		 * this method return depending on what strategy is
		 */
		public ArrayList<String> turn(){
			ArrayList<String> actionList = this.strategy.turn();
			return actionList;
		}
		
	public CharacterStrategy getStrategy() {
			return strategy;
		}

		public void setStrategy(CharacterStrategy strategy) {
			this.strategy = strategy;
		}

		public WarGameCharacterModel getCharacterForStrategy() {
			return characterForStrategy;
		}

		public void setCharacterForStrategy(WarGameCharacterModel characterForStrategy) {
			this.characterForStrategy = characterForStrategy;
		}
		//build3
	
	
/************************************added************************************/
	private
	
	/**
	 * the mapID
	 */
	String mapID = "null";
	
	/**
	 * which campaign to playwith
	 */
	private WarGameCampaignModel campaignToPlay;
	/**
	 * which character to playwith
	 */
	private WarGameCharacterModel characterToPlay;
	/**
	 * map models to playwith
	 */
	private ArrayList<WarGameMapModel> mapsModel;
	
	/**
	 * order list
	 */
	private ArrayList<String> orderList;

	/**
	 * get the campaign model to play with
	 * @return
	 */
	public WarGameCampaignModel getCampaignToPlay() {
		return campaignToPlay;
	}

	/**
	 * set the campaign model to play with
	 * @param campaignToPlay
	 */
	public void setCampaignToPlay(WarGameCampaignModel campaignToPlay) {
		this.campaignToPlay = campaignToPlay;
	}

	/**
	 * get the character model to play with
	 * @return
	 */
	public WarGameCharacterModel getCharacterToPlay() {
		return characterToPlay;
	}

	/**
	 * set the character model to play with
	 * @param characterToPlay
	 */
	public void setCharacterToPlay(WarGameCharacterModel characterToPlay) {
		this.characterToPlay = characterToPlay;
	}

	/**
	 * get the map model arraylist of the campaign
	 * @return
	 */
	public ArrayList<WarGameMapModel> getMapsModel() {
		return mapsModel;
	}

	/**
	 * set the map model arraylist of the campaign
	 * @param mapsModel
	 */
	public void setMapsModel(ArrayList<WarGameMapModel> mapsModel) {
		this.mapsModel = mapsModel;
	}
	
	/**
	 * get order list
	 * @return
	 */
	public ArrayList<String> getOrderList() {
		return orderList;
	}

	/**
	 * set order list
	 * @param orderList
	 */
	public void setOrderList(ArrayList<String> orderList) {
		this.orderList = orderList;
	}
}
