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
		this.containCharacters = null;
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
	
	
//--------------------------------------------------Getters/Setters--------------------------------------------------	
	public String getMapID() {
		return mapID;
	}

	public void setMapID(String mapID) {
		this.mapID = mapID;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String[][] getMap() {
		return map;
	}

	public void setMap(String[][] map) {
		this.map = map;
	}

	public ArrayList<WarGameItemModel> getContainItems() {
		return containItems;
	}

	public void setContainItems(ArrayList<WarGameItemModel> containItems) {
		this.containItems = containItems;
	}

	public ArrayList<WarGameCharacterModel> getContainEnemies() {
		return containEnemies;
	}

	public void setContainEnemies(ArrayList<WarGameCharacterModel> containEnemies) {
		this.containEnemies = containEnemies;
	}

	public ArrayList<WarGameCharacterModel> getContainFriends() {
		return containFriends;
	}

	public void setContainFriends(ArrayList<WarGameCharacterModel> containFriends) {
		this.containFriends = containFriends;
	}
	
	@Override
	public String toString() {
		return this.mapName;
	}
	
}
