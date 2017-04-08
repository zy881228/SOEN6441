package warGame.Model;

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
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This class is a item model contains methods that allows user to :
 * <p>Create a new item<br/>
 * <p>Set elements of the item<br/>
 * <p>Edit elements on the item<br/>
 * <p>Show the item including all the elements set/edited by the user<br/>
 * <p>Save the item information in the file<br/>
 * @version build 1
 */

public class WarGameItemModel extends Observable{
    
	public WarGameItemModel(){
		
	}
	public WarGameItemModel(WarGameItemModel o){
		this.itemID = o.getItemID();
		this.itemType = o.getItemType();
		this.enchanType = o.getEnchanType();
		this.enchanNumber = o.getEnchanNumber();
	}
    /**
     * Create a new item
     * @param newItemType item tyoe
     * @param newEnchanType enchantment bonus type
     * @param newEnchanNumber enchantment bonus value
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     * @throws NumberFormatException 
     */


	public void createItem(String newItemType, String newEnchanType, String newEnchanNumber) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		itemType = newItemType;
		enchanType = newEnchanType;
		enchanNumber = newEnchanNumber;
		itemID = Integer.parseInt(lastMapID())+1+"";
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
				message = new String();
				message = newItemID+" "+newItemType+" "+newEnchanType+" "+newEnchanNumber+"\r\n";
				itemList.add(message);
				
			}
			else
			{
				message = new String();
				message = str[0]+" "+str[1]+" "+str[2]+" "+str[3]+"\r\n";
				itemList.add(message);
				
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
	
	/**
	 * @return the last key of the item in the json file
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */

	public String lastMapID() throws UnsupportedEncodingException, FileNotFoundException{
		ArrayList<String> allKeysInMap = new ArrayList<String>();
		String lastMapID;
		Map<String, WarGameItemModel> mapsByMap = WarGameItemModel.listAllItems();
		Iterator<Entry<String, WarGameItemModel>> it = mapsByMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, WarGameItemModel> entry = (Map.Entry<String, WarGameItemModel>)it.next();
			allKeysInMap.add(entry.getKey());
		}
		lastMapID = allKeysInMap.get(allKeysInMap.size()-1);
		System.out.println(lastMapID);
		return lastMapID;
	}
	
	/**
	 * list all the items from the item file
	 * @return a item
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	
	public static Map<String, WarGameItemModel> listAllItems() throws UnsupportedEncodingException, FileNotFoundException{
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		InputStreamReader isreader = new InputStreamReader(new FileInputStream("src/file/items.json"), "UTF-8");
		Map<String, WarGameItemModel> mapsByMap = gson.fromJson(isreader, new TypeToken<Map<String, WarGameItemModel>>(){}.getType());
		return mapsByMap;
	}
	
	/**
	 * save the item into the item file
	 * @param itemModel
	 * @throws IOException 
	 */
	
	public Boolean saveItemJson(WarGameItemModel itemModel) throws IOException{
		Map<String, WarGameItemModel> mapsByMap = WarGameItemModel.listAllItems();
		mapsByMap.put(itemModel.getItemID(), itemModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/items.json");
		fw.write(gson.toJson(mapsByMap));
		fw.close();
		return true;
	}
	
	/**
	 * load the specific item by itemID
	 * @param itemID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	
	public void loadItemJson(String itemID) throws UnsupportedEncodingException, FileNotFoundException{
		Map<String, WarGameItemModel> mapsByMap = WarGameItemModel.listAllItems();
		WarGameItemModel itemModel = mapsByMap.get(itemID);
		this.itemID = itemModel.getItemID();
		this.itemType = itemModel.getItemType();
		this.enchanType = itemModel.getEnchanType();
		this.enchanNumber = itemModel.getEnchanNumber();
		viewType = 2;
		setChanged();
    	notifyObservers(this);
	}
	
	/**
	 * edit the item
	 * @param ItemID
	 * @param ItemType
	 * @param EnchanType
	 * @param EnchanNumber
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	
	public Boolean editItemJson(String newItemID,String newItemType, String newEnchanType, String newEnchanNumber) throws IOException{
		Map<String, WarGameItemModel> mapsByMap = WarGameItemModel.listAllItems();
		WarGameItemModel itemModel = mapsByMap.get(newItemID);
		itemModel.itemType = newItemType;
		itemModel.enchanType = newEnchanType;
		itemModel.enchanNumber = newEnchanNumber;
		mapsByMap.put(newItemID, itemModel);
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		FileWriter fw = new FileWriter("src/file/items.json");
		fw.write(gson.toJson(mapsByMap));
		fw.close();
		return true;
	}
	
	/**
	 * item adaption
	 * @param level
	 * @return enchanNumber
	 */
	public String itemAdaption(int level){
		String enchanNumber = new String();
		if(level<5)
		{
			enchanNumber = "1";
		}
		if(level>4 && level<9)
		{
			enchanNumber = "2";
		}
		if(level>8 && level<13)
		{
			enchanNumber = "3";
		}
		if(level>12 && level<17)
		{
			enchanNumber = "4";
		}
		if(level>16)
		{
			enchanNumber = "5";
		}
		return enchanNumber;
	}
	
	//set
	public void setItemEquip(String itemInfo){
		String str[] = itemInfo.trim().split(" ");
		int count = 0;
		switch(str[0])
		{
		case "Helmet":
			equip[0] = str[0]+" "+str[1]+" "+str[2];
			break;
		case "Armor":
			equip[1] = str[0]+" "+str[1]+" "+str[2];
			break;
		case "Shield":
			equip[2] = str[0]+" "+str[1]+" "+str[2];
			break;
		case "Ring":
			equip[3] = str[0]+" "+str[1]+" "+str[2];
			break;
		case "Belt":
			equip[4] = str[0]+" "+str[1]+" "+str[2];
			break;
		case "Boots":
			equip[5] = str[0]+" "+str[1]+" "+str[2];
			break;
		case "Weapon":
			equip[6] = str[0]+" "+str[1]+" "+str[2];
			break;
		}
	}
	
	//modify
	public void paint(Graphics g)
    {
        g.setFont(sFont);
        if(tgS == -1)
        {
            g.setColor(0);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(nI, (getWidth() - nI.getWidth()) / 2, (getHeight() - nI.getHeight()) / 2, 0x10 | 0x4);
        } else
        if(tgS == 0)
        {
            int i = sFont.getHeight();
            g.setColor(0);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(pI, (getWidth() - pI.getWidth()) / 2, 0, 0x10 | 0x4);
            g.drawImage(sI, (getWidth() - sI.getWidth()) / 2, getHeight() - eI.getHeight(), 0x20 | 0x4);
            g.drawImage(eI, getWidth() - eI.getWidth(), getHeight() - eI.getHeight(), 0x10 | 0x4);
            g.drawImage(oI, 0, getHeight() - oI.getHeight(), 0x10 | 0x4);
            int k = (getHeight() - eI.getHeight() - sI.getHeight() - pI.getHeight()) / 2 + pI.getHeight();
            g.setColor(0xffffff);
            g.drawString("PRESS JOYSTICK", (getWidth() - sFont.stringWidth("PRESS JOYSTICK")) / 2, k - 2, 0x20 | 0x4);
            g.drawString("TO START", (getWidth() - sFont.stringWidth("TO START")) / 2, k + 2, 0x10 | 0x4);
        } else
        if(tgS == 2)
        {
            int j = sFont.getHeight();
            g.setColor(0);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(bI, getWidth() - bI.getWidth(), getHeight() - bI.getHeight(), 0x10 | 0x4);
            if(indeX < 2)
            {
                g.setColor(0xff0000);
                if(indeX == 0)
                    g.fillRect((getWidth() - sFont.stringWidth(" Audio off")) / 2, sFont.getHeight() * 4 - 1, sFont.stringWidth(" Audio off"), sFont.getHeight());
                else
                if(indeX == 1)
                    g.fillRect((getWidth() - sFont.stringWidth(" Show Help")) / 2, sFont.getHeight() * 6 - 1, sFont.stringWidth(" Show Help"), sFont.getHeight());
                g.setColor(0xffffff);
                g.drawString("== Select ==", (getWidth() - sFont.stringWidth("== Select ==")) / 2, sFont.getHeight() * 2, 0x10 | 0x4);
                if(!sE)
                    g.drawString("Audio on ", (getWidth() - sFont.stringWidth("Audio on ")) / 2, sFont.getHeight() * 4, 0x10 | 0x4);
                else
                    g.drawString("Audio off", (getWidth() - sFont.stringWidth("Audio off")) / 2, sFont.getHeight() * 4, 0x10 | 0x4);
                g.drawString("Show Help", (getWidth() - sFont.stringWidth("Show Help")) / 2, sFont.getHeight() * 6, 0x10 | 0x4);
            }
        } else
        {
            if(pS == 0 || resumeFlag)
            {
                g.setColor(0);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawImage(ofSc1, 0, 0, 0x10 | 0x4);
                if(pS == 0)
                    pS = 1;
                resumeFlag = false;
            }
            g.drawImage(tI, getWidth() - tI.getWidth(), getHeight() - tI.getHeight(), 0x10 | 0x4);
            if(pS < 0)
                g.drawImage(rI, 0, getHeight() - rI.getHeight(), 0x10 | 0x4);
            else
                g.drawImage(oI, 0, getHeight() - oI.getHeight(), 0x10 | 0x4);
            g.drawImage(ofSc2, 16, 16, 0x10 | 0x4);
            drawScore(g);
        }
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
	
	 /**
     * <p>get the item info<br/>
     */
	public String getItemInfo(){
		String info = itemType+" "+enchanType+" "+enchanNumber;
		return info;
	}
	
	 /**
     * <p>set the item enchanNum<br/>
     */
	public void setEnchanNum(String newNum){
		this.enchanNumber = newNum;
	}
	
	/**
     * <p>change item info to string<br/>
     */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String message = "Item"+this.getItemID()+" "+this.itemType+" "+this.enchanType+" "+this.enchanNumber;
		return message;
	}

	
}
