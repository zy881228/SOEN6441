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
	
	//modify move
	public static int move()
    {
        byte byte0 = 2;
        int k = CO[mD];
        int l = SI[mD];
        cX += 1 * k;
        cY += 1 * l;
        aX = cX / 1000;
        aY = cY / 1000;
        bY = aY / 16;
        int i;
        if(aA[5 + 6 * bY] == -1)
        {
            i = 1;
            bX = (aX - 8) / 16;
        } else
        {
            i = 0;
            bX = aX / 16;
        }
        int j = bX + 6 * bY;
        if(mD != 21 && (bX > 0 && bX < 5 - i && aA[j - 1] > 0 && aA[j + 1] > 0 || i == 0 && bX == 0 && aA[j + 1] > 0 || i == 0 && bX == 5 && aA[j - 1] > 0))
        {
            aA[j] = aM;
            sbA();
            nA++;
            return 0;
        }
        int i1 = aX + 1;
        int j1 = aX - 1;
        int k1 = 89;
        byte byte1 = 7;
        if(i1 > k1 || j1 < byte1)
        {
            if(i1 > k1)
                aX = k1;
            if(j1 < byte1)
                aX = byte1 + 1;
            mD = 42 - mD;
            k = CO[mD];
            l = SI[mD];
            byte0 = 1;
        }
        if(aY - 1 < 15)
        {
            aY = 8;
            bY = aY / 16;
            if(aA[5 + 6 * bY] == -1)
                bX = (aX - 8) / 16;
            else
                bX = aX / 16;
            j = bX + 6 * bY;
            aA[j] = aM;
            sbA();
            nA++;
            return 0;
        }
        j = bX + 6 * bY;
        int l1 = (cY + 1 * l) / 1000 / 16;
        int i2;
        if(aA[5 + 6 * l1] == -1)
            i2 = ((cX + 1 * k) / 1000 - 8) / 16;
        else
            i2 = ((cX + 1 * k) / 1000 - 0) / 16;
        int j2 = i2 + 6 * l1;
        if(aA[j2] > 0)
        {
            if(aA[j] == -1)
            {
                if(aA[j - 1] > 0)
                {
                    bY++;
                    aA[j + 6] = aM;
                } else
                {
                    bX--;
                    aA[j - 1] = aM;
                }
            } else
            {
                aA[j] = aM;
            }
            sbA();
            nA++;
            return 0;
        }
        if(j2 > 0 && j2 < 59)
        {
            if((j + 1) % 6 == 0 || j % 6 == 0)
                return byte0;
            if(mD == 21 && (aA[j2 - 1] > 0 || aA[j2] > 0))
            {
                if(aA[5 + 6 * bY] == -1 && j2 == (j - 6) + 1 || aA[5 + 6 * bY] != -1 && j2 == j - 6)
                {
                    aA[j] = aM;
                    sbA();
                    nA++;
                    return 0;
                }
            } else
            if(mD > 21 && aA[j2] > 0)
            {
                if(aA[5 + 6 * bY] == -1 && j2 == (j - 6) + 1 || aA[5 + 6 * bY] != -1 && j2 == j - 6)
                {
                    aA[j] = aM;
                    sbA();
                    nA++;
                    return 0;
                }
            } else
            if(mD > 21 && aA[j2 - 1] > 0 && aA[j + 1] > 0)
            {
                if(aA[5 + 6 * bY] == -1 && j2 == (j - 6) + 1 || aA[5 + 6 * bY] != -1 && j2 == j - 6)
                {
                    aA[j] = aM;
                    sbA();
                    nA++;
                    return 0;
                }
            } else
            if(mD > 21 && aA[j2 - 1] > 0 && aA[j2 + 1] > 0)
            {
                if(aA[5 + 6 * bY] == -1 && j2 == (j - 6) + 1 || aA[5 + 6 * bY] != -1 && j2 == j - 6)
                {
                    aA[j2] = aM;
                    bY = l1;
                    bX = i2;
                    sbA();
                    nA++;
                    return 0;
                }
            } else
            if(mD < 21 && aA[j2] > 0)
            {
                if(aA[5 + 6 * bY] == -1 && j2 == j - 6 || aA[5 + 6 * bY] != -1 && j2 == j - 6 - 1)
                {
                    aA[j] = aM;
                    sbA();
                    nA++;
                    return 0;
                }
            } else
            if(mD < 21 && aA[j2 + 1] > 0 && aA[j - 1] > 0)
            {
                if(aA[5 + 6 * bY] == -1 && j2 == j - 6 || aA[5 + 6 * bY] != -1 && j2 == j - 6 - 1)
                {
                    aA[j] = aM;
                    sbA();
                    nA++;
                    return 0;
                }
            } else
            if(mD < 21 && aA[j2 + 1] > 0 && aA[j2 - 1] > 0 && (aA[5 + 6 * bY] == -1 && j2 == j - 6 || aA[5 + 6 * bY] != -1 && j2 == j - 6 - 1))
            {
                aA[j2] = aM;
                bY = l1;
                bX = i2;
                sbA();
                nA++;
                return 0;
            }
        }
        if(bX > 0 && bX < 5 - i && mD > 21)
        {
            int k2;
            if(aA[5 + 6 * bY] == -1)
                k2 = aX - 8;
            else
                k2 = aX - 0;
            if(k2 % 16 >= 13 && k2 % 16 <= 15 && aA[j + 1] > 0)
            {
                aA[j] = aM;
                sbA();
                nA++;
                return 0;
            }
        } else
        if(bX > 0 && bX < 5 - i && mD < 21)
        {
            int l2;
            if(aA[5 + 6 * bY] == -1)
                l2 = aX - 8;
            else
                l2 = aX - 0;
            if(l2 % 16 >= 0 && l2 % 16 <= 2 && aA[j - 1] > 0)
            {
                aA[j] = aM;
                sbA();
                nA++;
                return 0;
            }
        }
        return byte0;
    }
	
	//modify item final
	public static final void dtR(int i)
    {
        int ai[] = new int[60];
        for(int j = 0; j < 60; j++)
            ai[j] = aA[j];

        i = i / 2 + 3;
        if(i > 7)
            i = 7;
        int i1 = 0;
        for(int k = 0; k < 6; k++)
        {
            int j1;
            while((j1 = (rA.nextInt() >>> 1) % i + 1) == i1) ;
            if(j1 == 7 && (ai[k] & 0xf) == j1)
                j1 = (rA.nextInt() >>> 1) % 6 + 1;
            if(j1 == 7 && ai[5] == -1 && k > 0 && (ai[k - 1] & 0xf) == j1)
                j1 = (rA.nextInt() >>> 1) % 6 + 1;
            if(j1 == 7 && ai[5] != -1 && (ai[k + 1] & 0xf) == j1)
                j1 = (rA.nextInt() >>> 1) % 6 + 1;
            i1 = j1;
            aA[k] = j1;
            nA++;
        }

        i1 = 1;
        for(int k1 = 0; i1 < 10; k1++)
        {
            for(int l = 0; l < 6; l++)
                aA[l + 6 * i1] = ai[l + 6 * k1];

            i1++;
        }

        if(aA[11] != -1)
        {
            nA--;
            aA[5] = -1;
        }
        sbA();
    }
	
	//modify bom
	public static int bom()
    {
        int ai[] = new int[60];
        for(int i = 0; i < 60; i++)
            ai[i] = aA[i];

        int k = sNB(bX + 6 * bY);
        if(k >= 3)
        {
            nA -= k;
        } else
        {
            for(int j = 0; j < 60; j++)
                aA[j] = ai[j];

            k = 0;
        }
        return k;
    }
	//add key
	protected void keyPressed(int i)
    {
        int j = 0;
        int k = i;
        try
        {
            j = getGameAction(k);
        }
        catch(Exception exception) { }
        if(tgS == 0)
        {
            if(k == -6)
            {
                bgS = 0;
                tgS = 2;
                indeX = 0;
                repaint();
            } else
            if(k == -7)
                owM.exitApp();
            else
            if(k == 49 || j == 8)
            {
                init(mH, sE);
                tgS = 1;
                repaint();
                if(sD[0] != null && !sE)
                {
                    if(sD[0].getState() == 0)
                        sD[0].stop();
                    sD[0].play(1);
                }
            }
        } else
        if(tgS == 2)
        {
            if(k == -7)
            {
                if(indeX == 2)
                    indeX = 0;
                else
                    tgS = bgS;
                if(tgS == 1)
                {
                    pauseFlag = false;
                    resumeFlag = true;
                }
                repaint();
                serviceRepaints();
            } else
            if(k == -2)
            {
                if(indeX == 0)
                    indeX = 1;
                repaint();
            } else
            if(k == -1)
            {
                if(indeX == 1)
                    indeX = 0;
                repaint();
            } else
            if(j == 8)
                if(indeX == 0)
                {
                    sE = !sE;
                    setGConf(mH, sE);
                    saveData();
                    repaint();
                } else
                if(indeX == 1)
                {
                    indeX = 2;
                    Display.getDisplay(owM).setCurrent(hForm);
                }
        } else
        if(tgS == 1)
        {
            if(pS == -2)
            {
                if(k == -6)
                    retryPlease();
            } else
            if(k == -6)
            {
                bgS = 1;
                tgS = 2;
                pauseFlag = true;
                indeX = 0;
                repaint();
            }
            if(k == 50 || k == -1)
            {
                if(gF)
                    synchronized(this)
                    {
                        upA = true;
                    }
            } else
            if(k == -3 || k == -4)
            {
                if(gF)
                    if(k == -3 && mV > 0)
                        mV--;
                    else
                    if(k == -4 && mV < 42)
                        mV++;
            } else
            if(k == 52 || k == 54)
            {
                if(gF && !kF)
                {
                    kY = k;
                    kF = true;
                }
            } else
            if(k == 53 || j == 8)
            {
                if(gF && pS == 1)
                {
                    System.gc();
                    kF = false;
                    synchronized(this)
                    {
                        pS = 2;
                    }
                    GameCanvas _tmp = this;
                    Bobble.mD = mV;
                    sC++;
                    if(sD[2] != null && !sE)
                    {
                        if(sD[2].getState() == 0)
                            sD[2].stop();
                        sD[2].play(1);
                    }
                }
            } else
            if(k == -7)
            {
                setGConf(mH, sE);
                saveData();
                if(pS == -2)
                {
                    initTitle();
                    tgS = 0;
                    repaint();
                } else
                {
                    dF = true;
                    gF = false;
                    while(pS != -2) 
                        try
                        {
                            Thread.sleep(10L);
                        }
                        catch(Exception exception1) { }
                    initTitle();
                    tgS = 0;
                    repaint();
                }
            }
        }
    }
	//score
	 private void drawScore(Graphics g)
    {
        int i = lV + 1;
        int j = mS;
        if(j > 0x1869f)
            j = 0x1869f;
        if(mS > mH)
        {
            mH = mS;
            setGConf(mH, sE);
            saveData();
        }
        int k = mH;
        if(k > 0x1869f)
            k = 0x1869f;
        try
        {
            char ac[] = Integer.toString(i).toCharArray();
            int l = ac.length;
            for(int i1 = 0; i1 < l; i1++)
                g.drawImage(numI[Character.digit(ac[i1], 10)], 132 + (5 - (l - i1)) * 8, 36, 0x10 | 0x4);

            ac = Integer.toString(j).toCharArray();
            l = ac.length;
            for(int j1 = 0; j1 < l; j1++)
                g.drawImage(numI[Character.digit(ac[j1], 10)], 132 + (5 - (l - j1)) * 8, 84, 0x10 | 0x4);

            ac = Integer.toString(k).toCharArray();
            l = ac.length;
            for(int k1 = 0; k1 < l; k1++)
                g.drawImage(numI[Character.digit(ac[k1], 10)], 132 + (5 - (l - k1)) * 8, 132, 0x10 | 0x4);

        }
        catch(Exception exception) { }
    }
	//release key
	protected void keyReleased(int i)
    {
        int j = 0;
        int k = i;
        try
        {
            j = getGameAction(k);
        }
        catch(Exception exception) { }
        if(k == 52 || k == 54)
            kF = false;
    }
	
	//key repeat
	 protected void keyRepeated(int i)
    {
        int j = 0;
        int k = i;
        try
        {
            j = getGameAction(k);
        }
        catch(Exception exception) { }
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
