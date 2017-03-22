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

public class WarGameMapModel extends Observable{
//--------------------------------------------------Attributes--------------------------------------------------	
	/**
	 * unique map ID
	 */
	private String mapID;
	/**
	 * map name
	 */
	private String mapName;
	/**
	 * 2-dimension map
	 */
	private String map[][];
	/**
	 * enemies on the map
	 */
	private ArrayList<WarGameCharacterModel> containEnemies;
	/**
	 * friends on the map
	 */
	private ArrayList<WarGameCharacterModel> containFriends;
	/**
	 * items on the map
	 */
	private ArrayList<WarGameItemModel> containItems;

//--------------------------------------------------Methods--------------------------------------------------	
	/**
	 * default construct
	 */
	public WarGameMapModel(){}
	
	/**
	 * custom construct
	 * @param mapName
	 * @param map
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	public WarGameMapModel(String mapName, int mapHeight, int mapWidth) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		this.mapID = Integer.parseInt(this.lastMapID())+1+"";
		this.mapName = mapName;
		this.map = new String[mapHeight][mapWidth];
		this.containEnemies = null;
		this.containFriends = null;
		this.containItems = null;
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				if (i==0||j==0||i==mapHeight-1||j==mapWidth-1){
					this.map[i][j] = "x";
				}else{
					this.map[i][j] = "f";
				}
			}
		}
	}
	
	/**
	 * used in controller to call the viewer
	 */
	public void setMapCreationView(){
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * used in controller to call the viewer
	 */
	public void setMapLoadView(){
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * @return the last key of the map in the json file
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public String lastMapID() throws UnsupportedEncodingException, FileNotFoundException{
		ArrayList<String> allKeysInMap = new ArrayList<String>();
		Map<String, WarGameMapModel> mapsByMap = WarGameMapModel.listAllMaps();
		Iterator<Entry<String, WarGameMapModel>> it = mapsByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WarGameMapModel> entry = (Map.Entry<String, WarGameMapModel>)it.next();
			allKeysInMap.add(entry.getKey());
		}
		String lastMapID = allKeysInMap.get(allKeysInMap.size()-1);
		return lastMapID;
	}
	
	/**
	 * list all the maps from the map file
	 * @return a map
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static Map<String, WarGameMapModel> listAllMaps() throws UnsupportedEncodingException, FileNotFoundException{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("src/file/maps.json"), "UTF-8");
		Map<String, WarGameMapModel> mapsByMap = gson.fromJson(isreader, new TypeToken<Map<String, WarGameMapModel>>(){}.getType());
		return mapsByMap;
	}
	
	/**
	 * save the map into the map file
	 * @param characterModel
	 * @throws IOException 
	 */
	public void saveMap(WarGameMapModel mapModel) throws IOException{
		Map<String, WarGameMapModel> mapsByMap = WarGameMapModel.listAllMaps();
		mapsByMap.put(mapModel.getMapID(), mapModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/maps.json");
		fw.write(gson.toJson(mapsByMap));
		fw.close();
	}
	
	/**
	 * load the specific map by mapID
	 * @param mapID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public WarGameMapModel loadMap(String mapID) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, WarGameMapModel> mapsByMap = WarGameMapModel.listAllMaps();
		WarGameMapModel mapModel = mapsByMap.get(mapID);
		return mapModel;
	}
	

	/**
	 * has entry
	 * @return true if map contains an entry
	 */
	public Boolean hasEntry(){
		for (int i = 0; i < this.map.length; i++) {
			for (String s: this.map[i]) {
				if (s.equals("I")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * has exit
	 * @return true if map contains an exit
	 */
	public Boolean hasExit(){
		for (int i = 0; i < this.map.length; i++) {
			for (String s: this.map[i]) {
				if (s.equals("O")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * elements cannot be set on the border of the map
	 * @return true if no elements on edge
	 */
	public Boolean elementOnEdge(){
		String map[][] = this.map;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (i==0||j==0||i==map.length-1|j==map[0].length-1) {
					if (map[i][j].equals("m")||map[i][j].equals("n")||map[i][j].equals("i")) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * doors cannot be on set in the corner of the map
	 * @return
	 */
	public Boolean doorPostion(){
		if (this.map[0][0].equals("I")||
				this.map[0][this.map[0].length-1].equals("I")||
				this.map[this.map.length-1][0].equals("I")||
				this.map[this.map.length-1][this.map[0].length-1].equals("I")||
				this.map[0][0].equals("O")||
				this.map[0][this.map[0].length-1].equals("O")||
				this.map[this.map.length-1][0].equals("O")||
				this.map[this.map.length-1][this.map[0].length-1].equals("O")) {
			return false;
		}
		return true;
	}
	
	/**
	 * the item/friend/monster is reachable
	 */
	public Boolean canReach(){
		String map[][] = this.map;
		int posX=0;
		int posY=0;
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				if (this.map[i][j].equals("O")||this.map[i][j].equals("I")||this.map[i][j].equals("i")||this.map[i][j].equals("m")||this.map[i][j].equals("n")) {
					map = this.getMap();
					posX = i;
					posY = j;
					if(posX==0){
						if ((map[i][j-1].equals("x")||map[i][j-1].equals("I")||map[i][j-1].equals("O"))
								&&(map[i][j+1].equals("x")||map[i][j+1].equals("I")||map[i][j+1].equals("O"))
								&&(map[i+1][j].equals("x")||map[i+1][j].equals("I")||map[i+1][j].equals("O"))) {
							return false;
						}
					}
					if(posX==map.length-1){
						if ((map[i][j-1].equals("x")||map[i][j-1].equals("I")||map[i][j-1].equals("O"))
								&&(map[i][j+1].equals("x")||map[i][j+1].equals("O")||map[i][j+1].equals("I"))
								&&(map[i-1][j].equals("x")||map[i-1][j].equals("I")||map[i-1][j].equals("O"))) {
							return false;
						}
					}			
					if (posY==0) {
						if ((map[i-1][j].equals("x")||map[i-1][j].equals("I")||map[i-1][j].equals("O"))
								&&(map[i][j+1].equals("x")||map[i][j+1].equals("I")||map[i][j+1].equals("O"))
								&&(map[i+1][j].equals("x")||map[i+1][j].equals("I")||map[i+1][j].equals("O"))) {
							return false;
						}
					}			
					if(posY==map[0].length-1){
						if ((map[i-1][j].equals("x")||map[i-1][j].equals("I")||map[i-1][j].equals("O"))
								&&(map[i+1][j].equals("x")||map[i+1][j].equals("I")||map[i+1][j].equals("O"))
								&&(map[i][j-1].equals("x")||map[i][j-1].equals("I")||map[i][j-1].equals("O"))) {
							return false;
						}
					}
					if (posX!=0&&posY!=0&&posX!=map.length-1&&posY!=map[0].length-1) {
						if ((map[i-1][j].equals("x")||map[i-1][j].equals("I")||map[i-1][j].equals("O"))
								&&(map[i+1][j].equals("x")||map[i+1][j].equals("I")||map[i+1][j].equals("O"))
								&&(map[i][j-1].equals("x")||map[i][j-1].equals("I")||map[i][j-1].equals("O"))
								&&(map[i][j+1].equals("x")||map[i][j+1].equals("I")||map[i][j+1].equals("O"))) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
//--------------------------------------------------Getters/Setters--------------------------------------------------	
	/**
	 * get current map ID
	 */
	public String getMapID() {
		return mapID;
	}
	
	/**
	 * set map ID
	 * @param mapID
	 */
	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	/**
	 * get name of the map
	 * @return
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * set name of the map
	 * @param mapName
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	/**
	 * get the 2-dimension string array stands for map
	 * @return
	 */
	public String[][] getMap() {
		return map;
	}

	/**
	 * set the 2-dimension string array stands for map
	 * @param map
	 */
	public void setMap(String[][] map) {
		this.map = map;
	}

	/**
	 * get the item model arraylist in the map
	 * @return
	 */
	public ArrayList<WarGameItemModel> getContainItems() {
		return containItems;
	}

	/**
	 * set the item model arraylist in the map
	 * @return
	 */
	public void setContainItems(ArrayList<WarGameItemModel> containItems) {
		this.containItems = containItems;
	}

	/**
	 * get the enemy arraylist in the map
	 * @return
	 */
	public ArrayList<WarGameCharacterModel> getContainEnemies() {
		return containEnemies;
	}

	/**
	 * set the enemy arraylist in the map
	 * @param containEnemies
	 */
	public void setContainEnemies(ArrayList<WarGameCharacterModel> containEnemies) {
		this.containEnemies = containEnemies;
	}

	/**
	 * get the friend arraylist in the map
	 * @return
	 */
	public ArrayList<WarGameCharacterModel> getContainFriends() {
		return containFriends;
	}

	/**
	 * set the friend arraylist in the map
	 * @param containFriends
	 */
	public void setContainFriends(ArrayList<WarGameCharacterModel> containFriends) {
		this.containFriends = containFriends;
	}
	
	/**
	 * override to string methods
	 */
	@Override
	public String toString() {
		return this.mapName;
	}
	
}
