package warGame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is a map model contains methods that allows user to :
 * <p>Create a new map by user-defined size<br/>
 * <p>Set elements on the map<br/>
 * <p>Edit elements on the map<br/>
 * <p>Show the map including all the elements set/edited by the user<br/>
 * <p>Save the map information in the file<br/>
 * <p>Validate the map before saving<bt/>
 * <p>Load the map from the file<br/>
 * @version build 1
 * @author Student ID: 40001993
 */
public class WarGameMapModel extends Observable{
	/**
	 *<p> mapName is the name of the map used for saving in the file.<br/>
	 */
	private String mapName;
	/**
	 *<p> map is designed as a 2-dimension array
	 */
	private String map[][] = new String[0][0];
	/**
	 * <p>map's height<br/>
	 */
	private int mapHeight = 10;
	/**
	 * <p>map's width<br/>
	 */
	private int mapWidth = 10;
	/**
	 * <p>this one will be used in the view module<br/>
	 */
	private int viewModel =0; 
	/**
	 *<p> errorMsg is the error message that will be shown in validating the map if contains errors
	 */
	private String errorMsg;
	/**
	 *<p> mapFileName is a list contains all the map files in the map folder
	 */
	private ArrayList<String> mapFileName;
	/**
	 * <p>characterName is a list contains all the characters information<br/>
	 */
	private ArrayList<String> characterName;
	/**
	 * <p>itemName is a list contains all the items information<br/>
	 */
	private ArrayList<String> itemName;
	
	
	/**
	 * Create a new map(2-dimension array) by setting the map name, map height and map width.
	 * <p> The border of the map is wall and the floors inside.<br/>
	 * @param name
 	* @param height
 	* @param width
 	*/
	public void createMap(String name, int height, int width){
		this.mapName = name;
		this.map = new String[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i==0||j==0||i==height-1||j==width-1){
					this.map[i][j] = "x";
				}else{
					this.map[i][j] = "f";
				}
			}
		}
	}
	
	/**
	 * <p>used in the controller to open a window for creating new map<br/>
	 */
	public void setMap(){
		this.createMap(this.getMapName(), this.getMapHeight(), this.getMapWidth());
		viewModel = 5;
		setChanged();
		notifyObservers(this);
	}
	
/**
 * Set the elements in the particular position (i,j) and the specific element.
 * <p>User cannot put the element outside the map.<br>
 * @param i
 * @param j
 * @param s
 */
	public void setElements(int i, int j, String s){
		//Elements outside the map
		if(i>this.map.length || j>this.map[0].length||i<1||j<1){
			System.out.println("Cannot put the elements outside the map!");
		}else{
			this.map[i-1][j-1] = s;
		}
	}

	/**
	 * <p>get the 2-dimension array<br/>
	 * @return
	 */
	public String[][] getMap(){
		return map;
	}
	
	/**
	 * <p>get the name of the map<br/>
	 * @return
	 */
	public String getMapName(){
		return mapName;
	}
	
	/**
	 * <p>display the map in a new window<br/>
	 */
	public void showMap(){
//		System.out.println(this.mapName);
//		for(int i=0;i<this.map.length;i++){
//			for(int j=0;j<this.map[i].length;j++){
//				System.out.print(this.map[i][j]);
//				}
//			System.out.print("\n");
//			}
		viewModel = 1;
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Display the map saved in the map folder by name.
	 * @param mapFile
	 * @throws IOException
	 */
	public void showMapByName(String mapFile) throws IOException{
		BufferedReader read = new BufferedReader(new FileReader("src/file/map/"+mapFile));
		this.mapName = mapFile;
		String line;
		int row = 0;
		int tempL=0;
		while((line = read.readLine()) != null){
			String[] temp = line.split(","); 
			for(int j=0;j<temp.length;j++){
				tempL = j;
				}
			row++;
			}
		read.close();
		this.map = new String[row][tempL+1];
		outputMap();
		this.showMap();
	}
	
	/**
	 * <p>read the detail information of the map from the map file<br/>
	 * @throws
	 */
	public void  outputMap() throws IOException{
		BufferedReader read = new BufferedReader(new FileReader("src/file/map/"+this.mapName));
		String line;
		int row = 0;
		while((line = read.readLine()) != null){
			String[] temp = line.split(","); 
			for(int j=0;j<temp.length;j++){
				this.map[row][j] = temp[j];
				}
			row++;
			}
		read.close();
	}
	
	/**
	 * Validate the map before saving
	 * <p>Neither an item nor a monster in the map<br/>
	 * <p>The map contains an entry door and an exit door<br/>
	 * <p>Entry/Exit door cannot be put in the corner<br/>
	 * <p>The door cannot be surrounded by the walls thus the character cannot reach the door<br/>
	 * <p>The hero/monster/item chest cannot bu put on the border<br/>
	 * @return
	 * <p>Map file save if return true<br/>
	 */
	public Boolean validateMap(){
//		At least one monster or one chest on the map
//		if(this.containsThings()==false){
//			System.out.println("Contains at least one monster or one chest!");
//			this.errorMsg = "Contains at least one monster or one chest!";
//			return false;
//		}
//		Has entry
		if(this.hasEntry()==false){
//			System.out.println("Need an entry!");
			this.errorMsg = "Need an entry!";
			return false;
		}
//		Has exit
		if(this.hasExit()==false){
//			System.out.println("Need an exit!");
			this.errorMsg = "Need an exit!";
			return false;
		}
//		Entry/exit door cannot be in corner
		if(this.doorPos()==false){
//			System.out.println("Elements cannot be in corner!");
			this.errorMsg = "Doors cannot be in corner!";
			return false;
		}
//		Element cannot be reached
		if(this.canReach()==false){
//			System.out.println("Cannot reach the element!");
			this.errorMsg = "Cannot reach the element!";
			return false;
		}
//		Put elements on the border
		if (this.elementOnEdge()==false) {
			this.errorMsg = "Cannot put things on the border!";
			return false;
		}
		return true;
	}
	
	/**
	 * Edit the map in the position(i,j) and the element.
	 * @param i
	 * @param j
	 * @param s
	 */
	public void editMap(int i, int j, String s){
		this.setElements(i, j, s);
//		viewModel = 3;
//		setChanged();
//		notifyObservers(this);
	}

	/**
	 * <p>the map contains at least one item or one monster<br/>
	 */
	public Boolean containsThings(){
		for(int i=0;i<this.map.length;i++){
			for(String s : this.map[i]){
				if(s.equals("i")||s.equals("m")||s.equals("h")){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>the entry/exit cannot be set in the corner of the map<br/>
	 */
	public Boolean doorPos(){
		if(this.map[0][0].equals("O")||
			this.map[0][this.map[0].length-1].equals("O")||
			this.map[this.map.length-1][0].equals("O")||
			this.map[this.map.length-1][this.map[0].length-1].equals("O")||
			this.map[0][0].equals("I")||
			this.map[0][this.map[0].length-1].equals("I")||
			this.map[this.map.length-1][0].equals("I")||
			this.map[this.map.length-1][this.map[0].length-1].equals("I")
			){
			return false;
		}
		return true;
	}
	
	/**
	 * <p>the item/character/monster is reachable<br/>
	 */
	public Boolean canReach(){
		String tempMap[][] = this.getMap();
		int posX=0;
		int posY=0;
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				if (this.map[i][j].equals("O")||this.map[i][j].equals("I")||this.map[i][j].equals("i")||this.map[i][j].equals("m")||this.map[i][j].equals("h")) {
					tempMap = this.getMap();
					posX = i;
					posY = j;
					if(posX==0){
						if ((tempMap[i][j-1].equals("x")||tempMap[i][j-1].equals("I")||tempMap[i][j-1].equals("O"))
								&&(tempMap[i][j+1].equals("x")||tempMap[i][j+1].equals("I")||tempMap[i][j+1].equals("O"))
								&&(tempMap[i+1][j].equals("x")||tempMap[i+1][j].equals("I")||tempMap[i+1][j].equals("O"))) {
							return false;
						}
					}
					if(posX==tempMap.length-1){
						if ((tempMap[i][j-1].equals("x")||tempMap[i][j-1].equals("I")||tempMap[i][j-1].equals("O"))
								&&(tempMap[i][j+1].equals("x")||tempMap[i][j+1].equals("O")||tempMap[i][j+1].equals("I"))
								&&(tempMap[i-1][j].equals("x")||tempMap[i-1][j].equals("I")||tempMap[i-1][j].equals("O"))) {
							return false;
						}
					}			
					if (posY==0) {
						if ((tempMap[i-1][j].equals("x")||tempMap[i-1][j].equals("I")||tempMap[i-1][j].equals("O"))
								&&(tempMap[i][j+1].equals("x")||tempMap[i][j+1].equals("I")||tempMap[i][j+1].equals("O"))
								&&(tempMap[i+1][j].equals("x")||tempMap[i+1][j].equals("I")||tempMap[i+1][j].equals("O"))) {
							return false;
						}
					}			
					if(posY==tempMap[0].length-1){
						if ((tempMap[i-1][j].equals("x")||tempMap[i-1][j].equals("I")||tempMap[i-1][j].equals("O"))
								&&(tempMap[i+1][j].equals("x")||tempMap[i+1][j].equals("I")||tempMap[i+1][j].equals("O"))
								&&(tempMap[i][j-1].equals("x")||tempMap[i][j-1].equals("I")||tempMap[i][j-1].equals("O"))) {
							return false;
						}
					}
					if (posX!=0&&posY!=0&&posX!=tempMap.length-1&&posY!=tempMap[0].length-1) {
						if ((tempMap[i-1][j].equals("x")||tempMap[i-1][j].equals("I")||tempMap[i-1][j].equals("O"))
								&&(tempMap[i+1][j].equals("x")||tempMap[i+1][j].equals("I")||tempMap[i+1][j].equals("O"))
								&&(tempMap[i][j-1].equals("x")||tempMap[i][j-1].equals("I")||tempMap[i][j-1].equals("O"))
								&&(tempMap[i][j+1].equals("x")||tempMap[i][j+1].equals("I")||tempMap[i][j+1].equals("O"))) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * <p>the elements excluding doors cannot be set on the border of the map<br/>
	 */
	public Boolean elementOnEdge(){
		String tempMap[][] = this.getMap();
		for (int i = 0; i < tempMap.length; i++) {
			for (int j = 0; j < tempMap[0].length; j++) {
				if (i==0||j==0||i==tempMap.length-1||j==tempMap[0].length-1){
					if(tempMap[i][j].equals("h")||tempMap[i][j].equals("m")||tempMap[i][j].equals("i")||tempMap[i][j].equals("f")){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * <p>the map contains at least an entry<br/>
	 */
	public Boolean hasEntry(){
		for(int i=0;i<this.map.length;i++){
			for(String s : this.map[i]){
				if(s.equals("I")){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>the map contains at least an exit<br/>
	 */
	public Boolean hasExit(){
		for(int i=0;i<this.map.length;i++){
			for(String s : this.map[i]){
				if(s.equals("O")){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * <p>get the value of the viewModel, which will be used in the view module<br/>
	 */
	public int getViewModel(){
		return viewModel;
	}
	
	/**
	 * <p>get the name of the map<br/>
	 */
	public void setMapName(String mapName){
		this.mapName = mapName;
	}
	
	/**
	 * <p>get the height of the map<br/>
	 */
	public int getMapHeight(){
		return this.mapHeight;
	}
	
	/**
	 * <p>get the width of the map<br/>
	 */
	public int getMapWidth(){
		return this.mapWidth;
	}
	
	/**
	 * <p>get the error message of the map, which will be shown during saving the map<br/>
	 */
	public String getErrorMsg(){
		return this.errorMsg;
	}
	
	/**
	 * <p>get the list of all the map files by map names<br/>
	 */
	public ArrayList<String> getMapFileName(){
		return this.mapFileName;
	}
	
	/**
	 * <p>get the list of all the character information<br/>
	 */
	public ArrayList<String> getCharacterName(){
		return this.characterName;
	}
	
	/**
	 * <p>get the list of all the item information<br/>
	 */
	public ArrayList<String> getItemName(){
		return this.itemName;
	}
	
	/**
	 * <p>set the height of the map<br/>
	 */
	public void setMapHeight(int mapHeight){
		this.mapHeight = mapHeight;
	}
	
	/**
	 * <p>set the width of the map<br/>
	 */
	public void setMapWidth(int mapWidth){
		this.mapWidth = mapWidth;
	}
	
	/**
	 * User can save the map in a flie including all the elements set by the user.
	 * @throws IOException
	 */
	public void saving() throws IOException{
		if (this.validateMap() == true){
			File mapFile = new File("src/file/map/"+this.mapName);
			FileWriter fw = new FileWriter(mapFile);
//			String s[][] = this.map;
			for(int i=0;i<this.map.length;i++){
				for(int j=0;j<this.map[i].length;j++){
					fw.write(this.map[i][j]+",");
					}
				fw.write("\r\n");
				}
			fw.close();	
			viewModel = 2;
			setChanged();
			notifyObservers(this);
		}else{
//			System.out.println("Errors in map!");
			viewModel = 6;
			setChanged();
			notifyObservers(this);
		}
	}
	
	/**
	 * Load the map file by selecting in the map file list.
	 */
	public void loading(){
		this.listing();
		viewModel = 4;
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * <p>List all the character in the character file by id<br/>
	 */	
	public void listCharacter() throws IOException{
		this.characterName = new ArrayList<String>();
		String path = "src/file/character.txt";
		File file = new File(path);
		if (!file.exists()) {
			this.errorMsg = "No character in the character file";
		}else{
			BufferedReader read = new BufferedReader(new FileReader(path));
			String line;
			@SuppressWarnings("unused")
			int row = 0;
			while((line = read.readLine()) != null){
				String[] temp = line.split(" "); 
				this.characterName.add(temp[0]);
				row++;
			}
			read.close();
		}
	}
		
	/**
	 * <p>List all the item in the item file by id<br/>
	 */	
	 public void listItems() throws IOException{
		this.itemName = new ArrayList<String>();
		String path = "src/file/item.txt";
		File file = new File(path);
		if (!file.exists()) {
			this.errorMsg = "No items in the item file";
		}else{
			BufferedReader read = new BufferedReader(new FileReader(path));
			String line;
			@SuppressWarnings("unused")
			int row = 0;
			while((line = read.readLine()) != null){
				String[] temp = line.split(" "); 
				this.itemName.add(temp[0]);
				row++;
			}
			read.close();
		}
	}
	
	/**
	 * <p>List all the map in the map file by name<br/>
	 */
	 public void listing(){
		this.mapFileName = new ArrayList<String>();
		String path = "src/file/map/";
		File file = new File(path);
		File[] fileArray = file.listFiles();
		for(int i=0;i<fileArray.length;i++){ 
			if(fileArray[i].isFile()){
				this.mapFileName.add(fileArray[i].getName());
			}else{
				this.errorMsg = "No map files.";
			}
		}
//		for (String tempMapFileName : this.mapFileName) {
//			System.out.println(tempMapFileName);
//		}		
//		viewModel = 4;
//		setChanged();
//		notifyObservers(this);
	}
	
}