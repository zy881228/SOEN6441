package warGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is a item model contains methods that allows user to :
 * <p>Create a new item<br/>
 * <p>Set elements of the item<br/>
 * <p>Edit elements on the item<br/>
 * <p>Show the item including all the elements set/edited by the user<br/>
 * <p>Save the item information in the file<br/>
 * @version build 1
 */

class WarGameItemModel extends Observable{
    
    /**
     * Create a new item
     * @param newItemType item tyoe
     * @param newEnchanType enchantment bonus type
     * @param newEnchanNumber enchantment bonus value
     */


	public void createItem(String newItemType, String newEnchanType, String newEnchanNumber){
		itemType = newItemType;
		enchanType = newEnchanType;
		enchanNumber = newEnchanNumber;
		viewType = 1;
		setChanged();
		notifyObservers(this);
	}
	
    /**
     * Load a new item
     * @param newItemID item ID number
     * @throws IOException
     */

	public void loadItem(String newItemID) throws IOException{
		
		String equipID[] = newItemID.trim().split("m");
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
				itemID = str[0];
			}
		}
		viewType = 2;
		setChanged();
		notifyObservers(this);
	}
	
    
    /**
     * Get all items
     * @throws IOException
     * @return
     */
    
	public int getTotalNum() throws IOException{
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
	
    /**
     * Save the item
     * @param message item source
     * @throws IOException
     * @return
     */
    
	public Boolean saveItem(String message) throws IOException{
		File logFile = new File("src/file/item.txt");
		FileOutputStream os;
	    os = new FileOutputStream(logFile,true);
        os.write(message.getBytes());
	    os.close();
	    return true;
	}
	
    /**
     * Edit the item
     * @param newItemID item ID number
     * @param newItemType item type
     * @param newEnchanType item enchantment bonus type
     * @param newEnchanNumber item enchantment bonus value
     * @throws IOException
     * @return
     */
    
	public Boolean editItem(String newItemID,String newItemType, String newEnchanType, String newEnchanNumber) throws IOException{
		String buffer = new String();
		String message = new String();
		ArrayList <String> itemList = new ArrayList();
		File file = new File("src/file/item.txt");
		BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
		while((buffer = BF.readLine()) != null)
		{
			String str[] = buffer.trim().split(" ");
			if(str[0].equals(newItemID))
			{
				message = newItemID+" "+newItemType+" "+newEnchanType+" "+newEnchanNumber+"\r\n";
				itemList.add(message);
				System.out.println(message);
			}
			else
			{
				message = new String();
				message = str[0]+" "+str[1]+" "+str[2]+" "+str[3]+"\r\n";
				itemList.add(message);
				System.out.println(message);
			}
		}
		file.delete();
		file = new File("src/file/item.txt");
		FileOutputStream os;
		os = new FileOutputStream(file,true);
		for(int i=0;i<itemList.size();i++)
		{
			os.write(itemList.get(i).getBytes());
		}
		os.close();
		return true;
	}
/****************************added******************************************/
	private
    
    /**
     *<p> Helmet[] is the helmet item of the character.<br/>
     */
	String Helmet[];
    
    /**
     *<p> Armor[] is the armor item of the character.<br/>
     */
	String Armor[];
    
    /**
     *<p> Shield[] is the shield item of the character.<br/>
     */
	String Shield[];
    
    /**
     *<p> Ring[] is the ring item of the character.<br/>
     */
	String Ring[];
    
    /**
     *<p> Belt[] is the belt item of the character.<br/>
     */
	String Belt[];
    
    /**
     *<p> Boots[] is the boots item of the character.<br/>
     */
	String Boots[];
    
    /**
     *<p> Weapon[] is the weapon item of the character.<br/>
     */
	String Weapon[];
	
    /**
     *<p> itemType is the type of the item.<br/>
     */
	String itemType;
    
    /**
     *<p> enchanType is the type of the enchantment bonus.<br/>
     */
	String enchanType;
    
    /**
     *<p> enchanNumber is the value of the enchantment bonus.<br/>
     */
	String enchanNumber;
    
    /**
     *<p> itemID is the ID number of the item.<br/>
     */
	String itemID;
	
    /**
     *<p> viewType is the type of the view of diifferent operations.<br/>
     */
    
	int viewType = 0;
	
    /**
     * <p>get the item type<br/>
     */
	public String getItemType(){
		return itemType;
	}
    
    /**
     * <p>get the enchantment bonus type<br/>
     */
    
	public String getEnchanType(){
		return enchanType;
	}
    
    /**
     * <p>get the enchantment bonus value<br/>
     */
    
	public String getEnchanNumber(){
		return enchanNumber;
	}
    
    /**
     * <p>get the view type<br/>
     */
    
	public int getViewType(){
		return viewType;
	}
    
    /**
     * <p>get the item ID number<br/>
     */
    
	public String getItemID(){
		return itemID;
	}
}
