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

import warGame.Decorator.*;

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
		this.attackRange = o.getAttackRange();
		this.enchantList = o.getEnchantList();
	}
    /**
     * Create a new item
     * @param newItemType item tyoe
     * @param newEnchanType enchantment bonus type
     * @param newEnchanNumber enchantment bonus value
     * @param newEnchanSpeList enchantmentSpe bonus type
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     * @throws NumberFormatException 
     */


	public void createItem(String newItemType, String newEnchanType, String newEnchanNumber, 
			               ArrayList<String> newEnchanSpeList,String newRange) throws NumberFormatException, UnsupportedEncodingException, FileNotFoundException{
		itemType = newItemType;
		enchanType = newEnchanType;
		enchanNumber = newEnchanNumber;
		WarGameItemModel weapon = new WarGameItemModel();
		for (String str : newEnchanSpeList) {
			switch(str)
			{
				case "Freezing":
					weapon = new Freezing(weapon);
					weapon = weapon.addEnchantment();
					break;
				case "Burning":
					weapon = new Burning(weapon);
					weapon = weapon.addEnchantment();
					break;
				case "Slaying":
					weapon = new Slaying(weapon);
					weapon = weapon.addEnchantment();
					break;
				case "Frightening":
					weapon = new Frightening(weapon);
					weapon = weapon.addEnchantment();
					break;
				case "Pacifying":
					weapon = new Pacifying(weapon);
					weapon = weapon.addEnchantment();
					break;		
			}
		}
		attackRange = Integer.parseInt(newRange);
		enchantList = weapon.getEnchantList();
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
    
    public void setItemView(){
        setChanged();
        notifyObservers(this);
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
	
	//add retry
	public void retryPlease()
    {
        init(mH, sE);
        repaint();
    }
	
	//add resume
	public void resumePlease()
    {
        if(tgS == 1)
        {
            resumeFlag = true;
            if(pS < 0)
            {
                drawBobbles(ofSg2, 0);
                drawGameOver(ofSg2);
            }
            repaint();
            serviceRepaints();
        } else
        {
            repaint();
        }
    }
	
	//change set
	public void setGConf(int i, boolean flag)
    {
        mH = i;
        sE = flag;
    }
	
	//change save
	public void saveData()
    {
        if(rStore == null)
            return;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        try
        {
            dataoutputstream.writeInt(mH);
            dataoutputstream.writeBoolean(sE);
        }
        catch(IOException ioexception) { }
        byte abyte0[] = bytearrayoutputstream.toByteArray();
        try
        {
            rStore.setRecord(1, abyte0, 0, abyte0.length);
        }
        catch(RecordStoreException recordstoreexception) { }
    }
	
	//change load
	private void loadData()
    {
        if(rStore == null)
            return;
        Object obj = null;
        try
        {
            if(rStore.getNumRecords() == 0)
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                try
                {
                    dataoutputstream.writeInt(0);
                    dataoutputstream.writeBoolean(false);
                }
                catch(IOException ioexception) { }
                byte abyte0[] = bytearrayoutputstream.toByteArray();
                rStore.addRecord(abyte0, 0, abyte0.length);
            } else
            {
                byte abyte1[] = rStore.getRecord(1);
                if(abyte1 != null)
                {
                    ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte1);
                    DataInputStream datainputstream = new DataInputStream(bytearrayinputstream);
                    mH = datainputstream.readInt();
                    sE = datainputstream.readBoolean();
                } else
                {
                    mH = 0;
                    sE = false;
                }
            }
        }
        catch(Exception exception) { }
    }
	//motify
	 protected void hideNotify()
    {
        pauseFlag = true;
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
    public void setItemType(String newType){
        this.itemType = newType;
    }
    public void setViewType(int newViewTpe){
        this.viewType = newViewTpe;
    }
    public void setItemID(String newItemID){
        this.ItemID = newItemID;
    }
	 /**
     * <p>set the item enchanNum<br/>
     */
	public void setEnchanNum(String newNum){
		this.enchanNumber = newNum;
	}
	
    public void setEnchanType(String newType){
        this.enchanType = newType;
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
	
	//bulid3
		private int attackRange;
		private ArrayList<String> enchantList;
		
		public WarGameItemModel addEnchantment() {
			return this;
		}
		public ArrayList<String> getEnchantList() {
			return enchantList;
		}
		public void setEnchantList(ArrayList<String> enchantList) {
			this.enchantList = enchantList;
		}
		public int getAttackRange() {
			return attackRange;
		}
		public void setAttackRange(int attackRange) {
			this.attackRange = attackRange;
		}
		//bulid3


	
}
