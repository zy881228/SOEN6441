package warGame.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
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
	public void rollDice()
	{
		
	}
	public static void changeStratergy(int userID[], int upperID[],int message[],String interest[]){
		int i = 0;
		int j = 0;
		int e = 0;
		int m = 0;
		int v = 0;
		int upper = 0;
		int SumZero = 0;
		double temp = 1;
		int MessageE[] = new int[5];
		int SumMessage[] = new int[5];
		//for simi
		String strtemp = new String();
		String str[] = new String[10];
		int size = 0;
		int s = 0;
		double result_sim;
		while((upperID[i] == 0)&&(i<1000))
		{
			SumZero = SumZero + message[i];
			i++;
			
		}
		for(i=0;i<1000;i++)
		{
			//System.out.println("iyeye:"+i);
			//System.out.println("upperID:"+upperID[i]);
			//System.out.println("message:"+message[i]);
			e = 0;
			j = 0;
			m = 0;
			v = 0;
			upper = 0;
			//System.out.println("nima1:"+i);
			if(upperID[i] != 0)
			{
				upper = i;
				//System.out.println("nima2:"+i);
				while(upperID[upper] != 0)
				{
					j = 0;
					//System.out.println("nima3:"+i);
					//System.out.println("e:"+e);
					SumMessage[e] =0;
					//System.out.println("SumMessage[e]:"+SumMessage[e]);
					MessageE[e] = message[upper];
					while((userID[j] != upperID[upper])&&(j<999))
					{
						//System.out.println("nima4:"+i);
						//System.out.println("j:"+j);
						//System.out.println("upper:"+upper);
						if(j<999)
						{j++;
						}
						
					}
					for(m=0;m<1000;m++)
					{
						if((upperID[m] == upperID[upper]))
						{
							//System.out.println("###:"+message[m]);
							SumMessage[e] = SumMessage[e]+message[m];
							
						}
					}
					
					upper = j;
					//System.out.println("&&&:"+message[upper]);
					SumMessage[e] = SumMessage[e]+message[upper];
					e++;
					//System.out.println("nima5:"+i);
				}//while
				MessageE[e] = message[upper];
				SumMessage[e] = SumZero;
			}//if
			else
			{
				MessageE[0] = message[i];
				SumMessage[0] = SumZero;
				
			}
			temp = 1;
			for(v=0;v<e+1;v++)
			{
				//System.out.println("SumM:"+SumMessage[v]);
				//System.out.println("Message:"+MessageE[v]);
				temp = temp*MessageE[v]/SumMessage[v];
			}
			//System.out.println("temp:"+temp);
			//similarity
			strtemp = interest[i];
			str = strtemp.split(",");
			size = str.length;
			String interest_each[] = new String[size];
		    for(s=0;s<size;s++)
		    {
		    	interest_each[s] = str[s];
		    }
			//result_sim = similarity_in(interest_each);
			
			String F = String.format("%.7f",temp);
			//String S = String.format("%.7f",result_sim);
			//String T = String.format("%.7f",temp+result_sim);
  	        //System.out.println(F+" "+S+" "+T);
		}//for1
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
