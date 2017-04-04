package warGame.Model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

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
						if(i==mapArr[0].length-1){
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
					String str[] = equip[i].trim().split(" ");
					String changeBefore = equip[i];
					equip[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
					characterModel_buffer.setEquipChanged(changeBefore, equip[i]);
				}
						
			}
			for(int i =0;i<10;i++)
			{
				if(!backpack[i].equals("null"))
				{
					String str[] = backpack[i].trim().split(" ");
					backpack[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
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
					String str[] = equip[i].trim().split(" ");
					String changeBefore = equip[i];
					equip[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
					characterModel_buffer.setEquipChanged(changeBefore, equip[i]);
				}
						
			}
			for(int i =0;i<10;i++)
			{
				if(!backpack[i].equals("null"))
				{
					String str[] = backpack[i].trim().split(" ");
					backpack[i] = str[0]+" "+str[1]+" "+itemModel.itemAdaption(level);
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
	
}
