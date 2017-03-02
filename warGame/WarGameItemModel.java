package warGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;

class WarGameItemModel extends Observable{

	public void createItem(String newItemType, String newEnchanType, String newEnchanNumber){
		itemType = newItemType;
		enchanType = newEnchanType;
		enchanNumber = newEnchanNumber;
		viewType = 1;
		setChanged();
		notifyObservers(this);
	}
	
	public void loadItem(String itemID) throws IOException{
		
		String equipID[] = itemID.trim().split("m");
		String buffer = new String();
		File file = new File("src/file/item.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			String str[] = buffer.trim().split(" ");
			if(str[0].equals(equipID[1]))
			{
				itemType = str[1];
				enchanType = str[2];
				enchanNumber = str[3];
			}
		}
		viewType = 2;
		setChanged();
		notifyObservers(this);
	}
	
	public int getItemID() throws IOException{
		String buffer = new String();
		int count = 0;
		File file = new File("src/file/item.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			count++;
		}
		return count;
	}
	
	public Boolean saveItem(String message) throws IOException{
		File logFile = new File("src/file/item.txt");
		FileOutputStream os;
	    os = new FileOutputStream(logFile,true);
        os.write(message.getBytes());
	    os.close();
	    return true;
	}
/****************************added******************************************/
	private 
	String Helmet[];
	String Armor[];
	String Shield[];
	String Ring[];
	String Belt[];
	String Boots[];
	String Weapon[];
	
	String itemType;
	String enchanType;
	String enchanNumber;
	
	int viewType = 0;
	
	public String getItemType(){
		return itemType;
	}
	public String getEnchanType(){
		return enchanType;
	}
	public String getEnchanNumber(){
		return enchanNumber;
	}
	public int getViewType(){
		return viewType;
	}
}
