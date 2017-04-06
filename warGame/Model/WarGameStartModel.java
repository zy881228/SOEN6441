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
	
	//add
	public static void solution(List firstlist, List secondlist)
	{
		
		for(int i=0;i<secondlist.size();i++)
		{
			List<String> listtemp = new ArrayList<String>();
			List<String> list_out = new ArrayList<String>();
			String temp1 = (String) secondlist.get(i);
			for(int j=0;j<firstlist.size();j++)
			{
				String temp2 = (String) firstlist.get(j);
				int length = temp2.length();
				if(length == (temp1.length()))
				{
					listtemp.add(temp2);
				}
			}
			//listtemp是删选过的长度和目标字符串相同的listl列表
			
			
			//把 temp1 分割成单个字母的数组
			int temp_length = temp1.length();
			char temp[] = new char[temp_length];
			//temp1分割成temp，temp1是字符串，temp[]是单个字母的数组
			
			//System.out.println(temp[]);
			
			/*for (int k =0; k < temp1.length() ; k++)
			{
				String  temps[] = temp1.split("");
				
				temp[k]= temps[k+1].toChar();
				
			}*/
			temp = temp1.toCharArray();
			
			
			
			
			
			
			for(int m = 0;m<listtemp.size();m++)
			{
				String temp3 = listtemp.get(m);
				char temp3_char[] = new char[temp3.length()];
				temp3_char = temp3.toCharArray();
				int counter = 0;
				for(int n =0;n<temp.length;n++)
				{
					//System.out.println("temp3:"+temp3);
					//System.out.println("temp[]:"+temp[n]);
					for(int j=0;j<temp3_char.length;j++)
					{
						//System.out.println("temp3_char[]:"+temp3_char[j]);
						//System.out.println("temp[]:"+temp[n]);
						if(temp3_char[j] == temp[n])
						{
							//System.out.println("***");
							counter++;
							break;
						}
					}
					/*if(temp3.indexOf("i") != (-1));
					{
						System.out.println("***");
						counter++;
					}*/
				}
				//System.out.println(counter);
				if(counter == temp_length)
				{
					list_out.add(temp3);
				}
			}//此循环完成后，输出list已获得
			
			
			
			if(list_out.size() != 0)
			{
			   System.out.println(list_out);
			   System.out.println("-------------------");
			}
			else
			{
				System.out.println("No Answer Found");
				System.out.println("-------------------");
			}
			
		}//最大for循环
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
