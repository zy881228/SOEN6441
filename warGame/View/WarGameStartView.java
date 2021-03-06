package warGame.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import warGame.Controller.WarGameController;
import warGame.Model.*;
import warGame.Strategy.*;

/**
 * This viewer allow users to manipulate the start game information in GUI
 * @version build 2
 */

@SuppressWarnings("serial")
public class WarGameStartView extends JFrame implements Observer{
	
	private WarGameMapModel mapOnPage;
	private Map<String, WarGameMapModel> mapsByMap;
	private ArrayList<JLabel> mapElementsLbls;
	private String heroPos;
	private String enemyPos;
	private String friendPos;
	private ArrayList<String> enemyPosList;
	private ArrayList<String> friendPosList;	
	String map[][];
	JLabel label_pic = new JLabel();
	JLabel label_scores[] = new JLabel[12];
	JLabel label_equip[] = new JLabel[7];
	JLabel label_backpack[] = new JLabel[10];
	JLabel label_showScore[] = new JLabel[12];
	String backpack[] = new String[10];
	String equip[] = new String[7];
	String backpack_view[] = new String[10];
	String equip_view[] = new String[7];
	String backpack_npc[] = new String[10];
	String equip_npc[] = new String[7];
	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	WarGameCharacterModel nonePlayerModel = new WarGameCharacterModel();
	WarGameCharacterModel nonePlayerFight1 = new WarGameCharacterModel();
	WarGameCharacterModel nonePlayerFight2 = new WarGameCharacterModel();
	WarGameItemModel itemModel = new WarGameItemModel();
	ArrayList<WarGameMapModel> mapModelList = new ArrayList();
	JLabel label_player = new JLabel();
	JLabel label_noneplayer = new JLabel();
	ArrayList<String> orderList;
	int round = 1;
	int specialTurn = 0;
	int attackRange = 1;
	static JTextArea text_logging = new JTextArea();
	ArrayList<String> enchantList = new ArrayList<String>();
	WarGameCharacterModel characterForStrategy = new WarGameCharacterModel();
	ArrayList<String> actionList = new ArrayList<String>();
	Map<String, ArrayList<String>> characterActionsByMap;
	ArrayList<String> highLightList = new ArrayList<String>();
	JFrame frame;
	ImageIcon wall;
	ImageIcon door;
	ImageIcon chest;
	ImageIcon floor;
	ImageIcon monster;
	ImageIcon hero;
	ImageIcon friend;
	ImageIcon heroHighlight;
	ImageIcon friendHighlight;
	ImageIcon monsterHighlight;
	ImageIcon floorHighlight;
	ArrayList<JLabel> label_noneplayer_list = new ArrayList<JLabel>();
	int noneplayer_label_count = 0;
	int step_times = 0;
	int fight_times = 0;
	/**
     * Update the start game information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */
	
	@Override
	public void update(final Observable o, Object arg) {
		wall = new ImageIcon("src/image/Map/wall.jpg");
		door = new ImageIcon("src/image/Map/door.jpg");
		chest = new ImageIcon("src/image/Map/chest.jpg");
		floor = new ImageIcon("src/image/Map/floor.jpg");
		monster = new ImageIcon("src/image/Map/monster.jpg");
		hero = new ImageIcon("src/image/Map/hero.jpg");
		friend = new ImageIcon("src/image/Map/friend.jpg");
		heroHighlight = new ImageIcon("src/image/Map/heroHighlight.jpg");
		friendHighlight = new ImageIcon("src/image/Map/friendHighlight.jpg");
		monsterHighlight = new ImageIcon("src/image/Map/monsterHighlight.jpg");
		floorHighlight = new ImageIcon("src/image/Map/floorHighlight.jpg");

		
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle("WarGame");
		frame.setBounds(0, 0, 1280, 1000);
		frame.setSize(1280, 1000);
		frame.getContentPane().setLayout(null);
		characterForStrategy = new WarGameCharacterModel();
		round = 1;
		characterModel=((WarGameStartModel) o).getCharacterToPlay();
		mapModelList = ((WarGameStartModel) o).getMapsModel();
		characterActionsByMap = new HashMap<String, ArrayList<String>>();
		if(mapModelList.isEmpty())
		{
			frame.dispose();
			JOptionPane.showMessageDialog(null, "Complete Campaign!");
			@SuppressWarnings("unused")
			WarGameController newConroller = new WarGameController();
		}
		else
		{
			mapOnPage = mapModelList.get(0);
			mapModelList.remove(0);
			((WarGameStartModel) o).setMapsModel(mapModelList);		
			
			enemyPos = new String();
			friendPos = new String();
			enemyPosList = new ArrayList<String>();
			friendPosList = new ArrayList<String>();
			characterActionsByMap.clear();
			actionList.clear();
			orderList = new ArrayList<String>();
			orderList.add("Player");
			for (int i = 0; i < mapOnPage.getContainEnemies().size(); i++) {
				orderList.add("m "+i);
			}
			for (int i = 0; i < mapOnPage.getContainFriends().size(); i++) {
				orderList.add("n "+i);
			}
			Random random = new Random();
			Map<Integer, String> orderListMap = new HashMap<Integer, String>();
			orderListMap.clear();
			
			for (int i = 0; i < orderList.size(); i++) {
				int randomNum = random.nextInt(20)+1;
				if (orderList.get(i).equals("Player")) {
					logging("Player  roll "+randomNum);
					logging("Player's dexterity modifier is "+characterModel.getScore(13)[1]);
					randomNum += Integer.parseInt(characterModel.getScore(13)[1]);
				}else if (orderList.get(i).startsWith("m")) {
					logging("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+" roll "+randomNum);
					logging("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s dexterity modifier is "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
					randomNum += Integer.parseInt(mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
				}else if (orderList.get(i).startsWith("n")) {
					logging("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+" roll "+randomNum);
					logging("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s dexterity modifier is "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
					randomNum += Integer.parseInt(mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
				}
				if (orderListMap.containsKey(randomNum)) {
					randomNum += 1;
				}
				orderListMap.put(randomNum, orderList.get(i));
			}
			ArrayList<Integer> allKeysInMap = new ArrayList<Integer>();
			allKeysInMap.clear();
			Iterator<Entry<Integer, String>> it = orderListMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) it.next();
				allKeysInMap.add(entry.getKey());
			}
			for (int i = 0; i < allKeysInMap.size()-1; i++) {
				for (int j = 0; j <allKeysInMap.size()-1-i; j++) {
					if (allKeysInMap.get(j)<=allKeysInMap.get(j+1)) {
						int tempInt = allKeysInMap.get(j);
						allKeysInMap.set(j, allKeysInMap.get(j+1));
						allKeysInMap.set(j+1, tempInt);
					}
				}
			}
			orderList.clear();
			for (Integer integer : allKeysInMap) {
				orderList.add(orderListMap.get(integer));
			}
			logging("The order to take turn:");
			for (String string : orderList) {
				if (string.equals("Player")) {
					logging("Player");
				}else if (string.startsWith("m")) {
					logging("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1])));
				}else if (string.startsWith("n")) {
					logging("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(string.split(" ")[1])));
				}
			}
			((WarGameStartModel) o).setOrderList(orderList);
		}

		int levelChange = characterModel.getLevel();
		((WarGameStartModel) o).setAdaption(mapOnPage, levelChange);
		
		map = mapOnPage.getMap();
		mapElementsLbls = new ArrayList<JLabel>();
		
	
		JPanel mapPanel = new JPanel();
		mapPanel.setBounds(0, 0, 750, 750);
		frame.getContentPane().add(mapPanel);
		mapPanel.setLayout(null);
		
		frame.addKeyListener(new KeyListener() {	
			@Override
			public void keyTyped(KeyEvent e) {	
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					if(characterForStrategy.equals(characterModel)&&(step_times<3)&&(!characterModel.getStatus().contains("Freezing")))
					{
						setPanel(characterModel);
						frame.repaint();
						int posX;
						int posY;
						int index;
						String[] heroPosArray = heroPos.split(" ");
						posY = Integer.parseInt(heroPosArray[0]);//12
						posX = Integer.parseInt(heroPosArray[1]);//9
						index = Integer.parseInt(heroPosArray[2]);//309
						String[] leftPos = mapElementsLbls.get(index-1).getText().split(" ");
						if(leftPos[2].equals("f")){
							JLabel heroPosLbl = mapElementsLbls.get(index);
							Rectangle posB = heroPosLbl.getBounds();
							JLabel heroDesLbl = mapElementsLbls.get(index-1);
							Rectangle desB = heroDesLbl.getBounds();
							heroPosLbl.setBounds(desB);
							heroDesLbl.setBounds(posB);
							mapElementsLbls.set(index, heroDesLbl);
							mapElementsLbls.set((index-1), heroPosLbl);
							heroPos = posY + " " + (posX-1) + " " + (index-1);
							map[posY][posX] = "f";
							map[posY][posX-1] = "h";
							step_times++;
							showHighlight(characterForStrategy, "Player",heroPos);
						}else if(leftPos[2].equals("m")){
//							String str[] = map[posY][posX-1].trim().split(" ");
//							nonePlayerModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
//							if(leftPos[3].equals("alive"))
//							{
//								JOptionPane.showMessageDialog(null, "Monster Dead!");
//								mapElementsLbls.get(index-1).setText(posY+" "+(posX-1)+" "+"m dead");
//							}
//							if(leftPos[3].equals("dead"))
//							{
//								getAllItem();
//								mapOnPage.getContainEnemies().set(Integer.parseInt(str[2]), nonePlayerModel);
//							}
						}else if(leftPos[2].equals("i")){
							String str[] = map[posY][posX-1].trim().split(" ");
							itemModel = mapOnPage.getContainItems().get(Integer.parseInt(str[2]));
							String itemType = itemModel.getItemType();
							String enchanType = itemModel.getEnchanType();
							String enchanNum = itemModel.getEnchanNumber();
							String itemInfo = new String();
							if(itemType.equals("Weapon"))
							{
								ArrayList<String> speList = itemModel.getEnchantList();
								int attackRange = itemModel.getAttackRange();
								String spe = new String();
								for (String strspe : speList) {
									int i = speList.indexOf(strspe);
									if(i == 0)
									{
										spe = strspe;
									}
									else
									{
										spe = spe+","+strspe;
									}
								}
								itemInfo = itemType+" "+enchanType+" "+enchanNum+" "+spe+" "+attackRange;
							}
							else
							{
								itemInfo = itemType+" "+enchanType+" "+enchanNum;
							}
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemInfo;
										ImageIcon img_item = new ImageIcon("src/image/item/"+itemType+"/"+enchanType+".jpeg");
										label_backpack[i].setIcon(img_item);
										map[posY][posX-1] = "f";
										mapElementsLbls.get(index-1).setText(posY+" "+(posX-1)+" "+"f");
										ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
										mapElementsLbls.get(index-1).setIcon(floor);
										break;
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Backpack is full !");
							}
													
						}else if(leftPos[2].equals("O")){
							int count = 0;
							for (JLabel label : mapElementsLbls) 
							{
								String str[] = mapElementsLbls.get(count).getText().split(" ");
								
								if(str[2].equals("i"))
								{
									JOptionPane.showMessageDialog(null, "Chest not fulfilled!");
									break;
								}
								if(str[2].equals("m"))
								{
									if(str[3].equals("alive"))
									{
										JOptionPane.showMessageDialog(null, "Monster not fulfilled!");
										break;
									}
								}
								count++;
							}
							
							if(count == mapElementsLbls.size())
							{
								frame.dispose();
								characterModel.setLevel(characterModel.getLevel()+1);
								characterModel.scoresChange();
								((WarGameStartModel) o).setCharacterModel(characterModel);
								((WarGameStartModel) o).DisplayMapView();
							}
						}else if(leftPos[2].equals("n")){
							String str[] = map[posY][posX-1].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
							switchItem();
							mapOnPage.getContainFriends().set(Integer.parseInt(str[2]), nonePlayerModel);
						}
					}
					else
					{
						if(step_times>2)
						{
							logging("Player had move 3 times, Player can't move!");
						}
						else if(characterModel.getStatus().contains("Freezing"))
						{
							logging("Player's status is Freezing, Player can't move!");
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					if(characterForStrategy.equals(characterModel)&&(step_times<3)&&(!characterModel.getStatus().contains("Freezing")))
					{
						setPanel(characterModel);
						frame.repaint();
						int posX;
						int posY;
						int index;
						String[] heroPosArray = heroPos.split(" ");
						posY = Integer.parseInt(heroPosArray[0]);//12
						posX = Integer.parseInt(heroPosArray[1]);//9
						index = Integer.parseInt(heroPosArray[2]);//309
						String[] rightPos = mapElementsLbls.get(index+1).getText().split(" ");
						if(rightPos[2].equals("f")){
							JLabel heroPosLbl = mapElementsLbls.get(index);
							Rectangle posB = heroPosLbl.getBounds();
							JLabel heroDesLbl = mapElementsLbls.get(index+1);
							Rectangle desB = heroDesLbl.getBounds();
							heroPosLbl.setBounds(desB);
							heroDesLbl.setBounds(posB);
							mapElementsLbls.set(index, heroDesLbl);
							mapElementsLbls.set((index+1), heroPosLbl);
							heroPos = posY + " " + (posX+1) + " " + (index+1);
							map[posY][posX] = "f";
							map[posY][posX+1] = "h";		
							step_times++;
							showHighlight(characterForStrategy, "Player",heroPos);
						}else if(rightPos[2].equals("m")){
							String str[] = map[posY][posX+1].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
							if(rightPos[3].equals("alive"))
							{
								JOptionPane.showMessageDialog(null, "Monster Dead!");
								mapElementsLbls.get(index+1).setText(posY+" "+(posX+1)+" "+"m dead");
							}
							if(rightPos[3].equals("dead"))
							{
								getAllItem();
								mapOnPage.getContainEnemies().set(Integer.parseInt(str[2]), nonePlayerModel);
							}
						}else if(rightPos[2].equals("i")){
							String str[] = map[posY][posX+1].trim().split(" ");
							itemModel = mapOnPage.getContainItems().get(Integer.parseInt(str[2]));
							String itemType = itemModel.getItemType();
							String enchanType = itemModel.getEnchanType();
							String enchanNum = itemModel.getEnchanNumber();
							String itemInfo = new String();
							if(itemType.equals("Weapon"))
							{
								ArrayList<String> speList = itemModel.getEnchantList();
								int attackRange = itemModel.getAttackRange();
								String spe = new String();
								for (String strspe : speList) {
									int i = speList.indexOf(strspe);
									if(i == 0)
									{
										spe = strspe;
									}
									else
									{
										spe = spe+","+strspe;
									}
								}
								itemInfo = itemType+" "+enchanType+" "+enchanNum+" "+spe+" "+attackRange;
							}
							else
							{
								itemInfo = itemType+" "+enchanType+" "+enchanNum;
							}
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemInfo;
										ImageIcon img_item = new ImageIcon("src/image/item/"+itemType+"/"+enchanType+".jpeg");
										label_backpack[i].setIcon(img_item);
										map[posY][posX+1] = "f";
										mapElementsLbls.get(index+1).setText(posY+" "+(posX+1)+" "+"f");
										ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
										mapElementsLbls.get(index+1).setIcon(floor);
										break;
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Backpack is full !");
							}
							
						}else if(rightPos[2].equals("O")){
							int count = 0;
							for (JLabel label : mapElementsLbls) 
							{
								String str[] = mapElementsLbls.get(count).getText().split(" ");
								
								if(str[2].equals("i"))
								{
									JOptionPane.showMessageDialog(null, "Chest not fulfilled!");
									break;
								}
								if(str[2].equals("m"))
								{
									if(str[3].equals("alive"))
									{
										JOptionPane.showMessageDialog(null, "Monster not fulfilled!");
										break;
									}
								}
								count++;
							}
							
							if(count == mapElementsLbls.size())
							{
								frame.dispose();
								characterModel.setLevel(characterModel.getLevel()+1);
								characterModel.scoresChange();
								((WarGameStartModel) o).setCharacterModel(characterModel);
								((WarGameStartModel) o).DisplayMapView();
							}
						}else if(rightPos[2].equals("n")){
							String str[] = map[posY][posX+1].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
							switchItem();
							mapOnPage.getContainFriends().set(Integer.parseInt(str[2]), nonePlayerModel);
						}
					}
					else
					{
						if(step_times>2)
						{
							logging("Player had move 3 times, Player can't move!");
						}
						else if(characterModel.getStatus().contains("Freezing"))
						{
							logging("Player's status is Freezing, Player can't move!");
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					if(characterForStrategy.equals(characterModel)&&(step_times<3)&&(!characterModel.getStatus().contains("Freezing")))
					{
						setPanel(characterModel);
						frame.repaint();
						int posX;
						int posY;
						int index;
						String[] heroPosArray = heroPos.split(" ");
						posY = Integer.parseInt(heroPosArray[0]);//12
						posX = Integer.parseInt(heroPosArray[1]);//9
						index = Integer.parseInt(heroPosArray[2]);//309
						String[] upPos = null;
						if(index-map[0].length>0){
							upPos = mapElementsLbls.get(index-map[0].length).getText().split(" ");
						}
						if(upPos[2].equals("f")){
							JLabel heroPosLbl = mapElementsLbls.get(index);
							Rectangle posB = heroPosLbl.getBounds();
							JLabel heroDesLbl = mapElementsLbls.get(index-(map[0].length));
							Rectangle desB = heroDesLbl.getBounds();
							heroPosLbl.setBounds(desB);
							heroDesLbl.setBounds(posB);
							mapElementsLbls.set(index, heroDesLbl);
							mapElementsLbls.set((index-(map[0].length)), heroPosLbl);
							heroPos = (posY-1) + " " + posX + " " + (index-(map[0].length));
							map[posY][posX] = "f";
							map[posY-1][posX] = "h";
							step_times++;
							showHighlight(characterForStrategy, "Player",heroPos);
						}else if(upPos[2].equals("m")){
							String str[] = map[posY-1][posX].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
							if(upPos[3].equals("alive"))
							{
								JOptionPane.showMessageDialog(null, "Monster Dead!");
								mapElementsLbls.get(index-(map[0].length)).setText((posY-1)+" "+posX+" "+"m dead");
							}
							if(upPos[3].equals("dead"))
							{
								getAllItem();
								mapOnPage.getContainEnemies().set(Integer.parseInt(str[2]), nonePlayerModel);
							}
						}else if(upPos[2].equals("i")){
							String str[] = map[posY-1][posX].trim().split(" ");
							itemModel = mapOnPage.getContainItems().get(Integer.parseInt(str[2]));
							String itemType = itemModel.getItemType();
							String enchanType = itemModel.getEnchanType();
							String enchanNum = itemModel.getEnchanNumber();
							String itemInfo = new String();
							if(itemType.equals("Weapon"))
							{
								ArrayList<String> speList = itemModel.getEnchantList();
								int attackRange = itemModel.getAttackRange();
								String spe = new String();
								for (String strspe : speList) {
									int i = speList.indexOf(strspe);
									if(i == 0)
									{
										spe = strspe;
									}
									else
									{
										spe = spe+","+strspe;
									}
								}
								itemInfo = itemType+" "+enchanType+" "+enchanNum+" "+spe+" "+attackRange;
							}
							else
							{
								itemInfo = itemType+" "+enchanType+" "+enchanNum;
							}
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemInfo;
										ImageIcon img_item = new ImageIcon("src/image/item/"+itemType+"/"+enchanType+".jpeg");
										label_backpack[i].setIcon(img_item);
										map[posY-1][posX] = "f";
										mapElementsLbls.get(index-(map[0].length)).setText((posY-1)+" "+posX+" "+"f");
										ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
										mapElementsLbls.get(index-(map[0].length)).setIcon(floor);
										break;
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Backpack is full !");
							}
							
						}else if(upPos[2].equals("O")){
							int count = 0;
							for (JLabel label : mapElementsLbls) 
							{
								String str[] = mapElementsLbls.get(count).getText().split(" ");
								
								if(str[2].equals("i"))
								{
									JOptionPane.showMessageDialog(null, "Chest not fulfilled!");
									break;
								}
								if(str[2].equals("m"))
								{
									if(str[3].equals("alive"))
									{
										JOptionPane.showMessageDialog(null, "Monster not fulfilled!");
										break;
									}
								}
								count++;
							}
							
							if(count == mapElementsLbls.size())
							{
								frame.dispose();
								characterModel.setLevel(characterModel.getLevel()+1);
								characterModel.scoresChange();
								((WarGameStartModel) o).setCharacterModel(characterModel);
								((WarGameStartModel) o).DisplayMapView();
							}
							
						}else if(upPos[2].equals("n")){
							String str[] = map[posY-1][posX].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
							switchItem();
							mapOnPage.getContainFriends().set(Integer.parseInt(str[2]), nonePlayerModel);
						}
					}
					else
					{
						if(step_times>2)
						{
							logging("Player had move 3 times, Player can't move!");
						}
						else if(characterModel.getStatus().contains("Freezing"))
						{
							logging("Player's status is Freezing, Player can't move!");
						}
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					if(characterForStrategy.equals(characterModel)&&(step_times<3)&&(!characterModel.getStatus().contains("Freezing")))
					{
						setPanel(characterModel);
						frame.repaint();
						int posX;
						int posY;
						int index;
						String[] heroPosArray = heroPos.split(" ");
						posY = Integer.parseInt(heroPosArray[0]);//12
						posX = Integer.parseInt(heroPosArray[1]);//9
						index = Integer.parseInt(heroPosArray[2]);//309
						String[] downPos = null;
						if(index+map[0].length<(map.length)*(map[0].length)){
							downPos = mapElementsLbls.get(index+map[0].length).getText().split(" ");
						}
						if(downPos[2].equals("f")){
							JLabel heroPosLbl = mapElementsLbls.get(index);
							Rectangle posB = heroPosLbl.getBounds();
							JLabel heroDesLbl = mapElementsLbls.get(index+(map[0].length));
							Rectangle desB = heroDesLbl.getBounds();
							heroPosLbl.setBounds(desB);
							heroDesLbl.setBounds(posB);
							mapElementsLbls.set(index, heroDesLbl);
							mapElementsLbls.set((index+(map[0].length)), heroPosLbl);
							heroPos = (posY+1) + " " + posX + " " + (index+(map[0].length));
							map[posY][posX] = "f";
							map[posY+1][posX] = "h";		
							step_times++;
							showHighlight(characterForStrategy, "Player",heroPos);
						}else if(downPos[2].equals("m")){
							String str[] = map[posY+1][posX].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
							if(downPos[3].equals("alive"))
							{
								JOptionPane.showMessageDialog(null, "Monster Dead!");
								mapElementsLbls.get(index+(map[0].length)).setText((posY+1)+" "+posX+" "+"m dead");
							}
							if(downPos[3].equals("dead"))
							{
								getAllItem();
								mapOnPage.getContainEnemies().set(Integer.parseInt(str[2]), nonePlayerModel);
							}
						}else if(downPos[2].equals("i")){
							String str[] = map[posY+1][posX].trim().split(" ");
							itemModel = mapOnPage.getContainItems().get(Integer.parseInt(str[2]));
							String itemType = itemModel.getItemType();
							String enchanType = itemModel.getEnchanType();
							String enchanNum = itemModel.getEnchanNumber();
							String itemInfo = new String();
							if(itemType.equals("Weapon"))
							{
								ArrayList<String> speList = itemModel.getEnchantList();
								int attackRange = itemModel.getAttackRange();
								String spe = new String();
								for (String strspe : speList) {
									int i = speList.indexOf(strspe);
									if(i == 0)
									{
										spe = strspe;
									}
									else
									{
										spe = spe+","+strspe;
									}
								}
								itemInfo = itemType+" "+enchanType+" "+enchanNum+" "+spe+" "+attackRange;
							}
							else
							{
								itemInfo = itemType+" "+enchanType+" "+enchanNum;
							}
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemInfo;
										ImageIcon img_item = new ImageIcon("src/image/item/"+itemType+"/"+enchanType+".jpeg");
										label_backpack[i].setIcon(img_item);
										map[posY+1][posX] = "f";
										mapElementsLbls.get(index+(map[0].length)).setText((posY+1)+" "+posX+" "+"f");
										ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
										mapElementsLbls.get(index+(map[0].length)).setIcon(floor);
										break;
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Backpack is full !");
							}
							
						}else if(downPos[2].equals("O")){
							int count = 0;
							for (JLabel label : mapElementsLbls) 
							{
								String str[] = mapElementsLbls.get(count).getText().split(" ");
								
								if(str[2].equals("i"))
								{
									JOptionPane.showMessageDialog(null, "Chest not fulfilled!");
									break;
								}
								if(str[2].equals("m"))
								{
									if(str[3].equals("alive"))
									{
										JOptionPane.showMessageDialog(null, "Monster not fulfilled!");
										break;
									}
								}
								count++;
							}
							
							if(count == mapElementsLbls.size())
							{
								frame.dispose();
								characterModel.setLevel(characterModel.getLevel()+1);
								characterModel.scoresChange();
								((WarGameStartModel) o).setCharacterModel(characterModel);
								((WarGameStartModel) o).DisplayMapView();
							}
						}else if(downPos[2].equals("n")){
							String str[] = map[posY+1][posX].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
							switchItem();
							mapOnPage.getContainFriends().set(Integer.parseInt(str[2]), nonePlayerModel);
						}
					}
					else
					{
						if(step_times>2)
						{
							logging("Player had move 3 times, Player can't move!");
						}
						else if(characterModel.getStatus().contains("Freezing"))
						{
							logging("Player's status is Freezing, Player can't move!");
						}
					}
				}
				else if(e.getKeyCode() == KeyEvent.VK_SPACE){
					logging("Round "+round);
					for (String string : orderList) {
						if (string.equals("Player")) {
							fight_times = 0;
							step_times = 0;
							logging("Player turn");
							characterForStrategy = characterModel;
							((WarGameStartModel) o).setStrategy(new HumanPlayer(), characterForStrategy);
							actionList = ((WarGameStartModel) o).turn();
							showHighlight(characterForStrategy, "Player", heroPos);
							break;
						}else if (string.startsWith("m")) {
							fight_times = 0;
							logging("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1]))+"'s turn");
							characterForStrategy = mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1]));
							((WarGameStartModel) o).setStrategy(new AggressiveNPC(), characterForStrategy);
							actionList = ((WarGameStartModel) o).turn();
							characterActionsByMap.clear();
							characterActionsByMap.put(string, actionList);
							try {
								takeAction(characterActionsByMap);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}else if (string.startsWith("n")) {
							fight_times = 0;
							logging("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(string.split(" ")[1]))+"'s turn");
							characterForStrategy = mapOnPage.getContainFriends().get(Integer.parseInt(string.split(" ")[1]));
							((WarGameStartModel) o).setStrategy(new FriendlyNPC(), characterForStrategy);
							actionList = ((WarGameStartModel) o).turn();
							characterActionsByMap.clear();
							characterActionsByMap.put(string, actionList);
							try {
								takeAction(characterActionsByMap);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}//for
					
				}
				else if(e.getKeyCode() == KeyEvent.VK_O){
					if (orderList.indexOf("Player")==orderList.size()-1) {
						round++;
						JOptionPane.showMessageDialog(null, "Round End!");
						if (characterModel.getStatus().contains("Freezing")) {
							characterModel.getStatus().remove("Freezing");
						}
					}else{
						for (int i = orderList.indexOf("Player")+1; i < orderList.size(); i++) {
							if (orderList.get(i).startsWith("m")) {
								fight_times = 0;
								logging("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s turn");
								characterForStrategy = mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]));
								((WarGameStartModel) o).setStrategy(new AggressiveNPC(), characterForStrategy);
								actionList = ((WarGameStartModel) o).turn();
								characterActionsByMap.clear();
								characterActionsByMap.put(orderList.get(i), actionList);
								try {
									takeAction(characterActionsByMap);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}else if (orderList.get(i).startsWith("n")) {
								fight_times = 0;
								logging("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s turn");
								characterForStrategy = mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]));
								((WarGameStartModel) o).setStrategy(new FriendlyNPC(), characterForStrategy);
								actionList = ((WarGameStartModel) o).turn();
								characterActionsByMap.clear();
								characterActionsByMap.put(orderList.get(i), actionList);
								try {
									takeAction(characterActionsByMap);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							}
						}
						round++;
						JOptionPane.showMessageDialog(null, "Round End!");
						if (characterModel.getStatus().contains("Freezing")) {
							characterModel.getStatus().remove("Freezing");
						}
					}
				}
			}//keypressed
		});
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				JLabel mapElement = new JLabel();
				final int i_buffer = i;
				final int j_buffer = j;
				String str[] = map[i][j].trim().split(" ");
				switch (str[0]) {
				case "x":
					mapElement = new JLabel(i+" "+j+" "+"x");
					mapElement.setIcon(wall);
					mapElementsLbls.add(mapElement);
					break;
				case "i":
					mapElement = new JLabel(i+" "+j+" "+"i");
					mapElement.setIcon(chest);
					mapElementsLbls.add(mapElement);
					break;
				case "h":
					mapElement = new JLabel(i+" "+j+" "+"h");
					mapElement.setIcon(hero);
					mapElementsLbls.add(mapElement);
					heroPos = i+" "+j+" "+(mapElementsLbls.size()-1);
					break;
				case "m":
					mapElement = new JLabel(i+" "+j+" "+"m alive"+" "+str[2]);
					mapElement.setIcon(monster);
					mapElementsLbls.add(mapElement);
					enemyPos = i+" "+j+" "+(mapElementsLbls.size()-1+" "+str[2]);
					enemyPosList.add(enemyPos);
					break;
				case "I":
					mapElement = new JLabel(i+" "+j+" "+"I");
					mapElement.setIcon(door);
					mapElementsLbls.add(mapElement);
					break;
				case "O":
					mapElement = new JLabel(i+" "+j+" "+"O");
					mapElement.setIcon(door);
					mapElementsLbls.add(mapElement);
					break;
				case "f":
					mapElement = new JLabel(i+" "+j+" "+"f");
					mapElement.setIcon(floor);
					mapElementsLbls.add(mapElement);
					break;
				case "n":
					mapElement = new JLabel(i+" "+j+" "+"n alive"+" "+str[2]);
					mapElement.setIcon(friend);
					mapElementsLbls.add(mapElement);
					friendPos = i+" "+j+" "+(mapElementsLbls.size()-1+" "+str[2]);
					friendPosList.add(friendPos);
					break;
				}
			}
		}
		
		
		//initialize map view
		for (JLabel lbl : mapElementsLbls) {
			int posX;
			int posY;
			String[] lblArray = lbl.getText().split(" ");
			if(lblArray[2].equals("h"))
			{
				posY = Integer.parseInt(lblArray[0]);
				posX = Integer.parseInt(lblArray[1]);
				label_player = new JLabel(posY+" "+posX+" "+"h");
				label_player.setBounds(posX*30, posY*30, 30, 30);
				label_player.setIcon(hero);
				mapPanel.add(label_player);
				mapElementsLbls.set(mapElementsLbls.indexOf(lbl), label_player);
				label_player.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							createPlayerView();
							setPanel(characterModel);
							showHighlight(characterModel, "Player",heroPos);
						}
					}
				});
			}
			else if(lblArray[2].equals("m"))
			{
				posY = Integer.parseInt(lblArray[0]);
				posX = Integer.parseInt(lblArray[1]);
				JLabel label_buffer = new JLabel();
				label_buffer = new JLabel(posY+" "+posX+" "+"m alive"+" "+lblArray[4]);
				label_buffer.setBounds(posX*30, posY*30, 30, 30);
				label_buffer.setIcon(monster);
				mapPanel.add(label_buffer);
				mapElementsLbls.set(mapElementsLbls.indexOf(lbl), label_buffer);
				label_noneplayer_list.add(label_buffer);
				final int event_i = noneplayer_label_count;
				
				label_buffer.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							JLabel label_buffer = new JLabel();
							label_buffer = label_noneplayer_list.get(event_i);
							String text = label_buffer.getText();
							
							String str[] = text.trim().split(" ");
							String pos = str[0]+" "+str[1]+" "+(Integer.parseInt(str[0])*(map[0].length)+Integer.parseInt(str[1]));
							WarGameCharacterModel characterModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[4]));
							setPanel(characterModel);
							showHighlight(characterModel, "m",pos);
						}
						if(e.getButton() == MouseEvent.BUTTON3)
						{
							if(fight_times == 0)
							{
								int count = 0;
								JLabel label_buffer = new JLabel();
								label_buffer = label_noneplayer_list.get(event_i);
								String text = label_buffer.getText();
								String strTarget[] = text.trim().split(" ");
								WarGameCharacterModel characterModelTarget = mapOnPage.getContainEnemies().get(Integer.parseInt(strTarget[4]));
								for (String hlTarget : highLightList) {
									String strHL[] = hlTarget.trim().split(" ");
									if(strHL[1].equals(strTarget[0])&&strHL[2].equals(strTarget[1]))
									{
										String hitPoints[] = characterModelTarget.getScore(7);
										if(Integer.parseInt(hitPoints[1]) > 0)
										{
											characterModelTarget = ((WarGameStartModel) o).fightChange(characterModel, characterModelTarget);
											mapOnPage.getContainEnemies().set(Integer.parseInt(strTarget[4]), characterModelTarget);
											fight_times =1;
										}
										else
										{
											logging("Character"+characterModelTarget.getCharacterID()+" is dead! Can't be attacked!");
										}
										count = 0;
										break;
									}
									count++;
								}
								if(count == highLightList.size())
								{
									logging("Character"+characterModelTarget.getCharacterID()+" is not in the attack range");
								}
							}
							else
							{
								logging("Player had attacked!");
							}
						}
					}
				});
				noneplayer_label_count++;
			}
			else if(lblArray[2].equals("n"))
			{
				posY = Integer.parseInt(lblArray[0]);
				posX = Integer.parseInt(lblArray[1]);
				JLabel label_buffer = new JLabel();
				label_buffer = new JLabel(posY+" "+posX+" "+"n alive"+" "+lblArray[4]);
				label_buffer.setBounds(posX*30, posY*30, 30, 30);
				label_buffer.setIcon(friend);
				mapPanel.add(label_buffer);
			
				mapElementsLbls.set(mapElementsLbls.indexOf(lbl), label_buffer);
				label_noneplayer_list.add(label_buffer);
				final int event_i = noneplayer_label_count;
				
				label_buffer.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							JLabel label_buffer = new JLabel();
							label_buffer = label_noneplayer_list.get(event_i);
							String text = label_buffer.getText();
							String str[] = text.trim().split(" ");
							String pos = str[0]+" "+str[1]+" "+(Integer.parseInt(str[0])*(map[0].length)+Integer.parseInt(str[1]));
							WarGameCharacterModel characterModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[4]));
							setPanel(characterModel);
							showHighlight(characterModel, "n",pos);
						}
						if(e.getButton() == MouseEvent.BUTTON3)
						{
							if(fight_times == 0)
							{
								int count = 0;
								JLabel label_buffer = new JLabel();
								label_buffer = label_noneplayer_list.get(event_i);
								String text = label_buffer.getText();
								String strTarget[] = text.trim().split(" ");
								WarGameCharacterModel characterModelTarget = mapOnPage.getContainEnemies().get(Integer.parseInt(strTarget[4]));
								for (String hlTarget : highLightList) {
									String strHL[] = hlTarget.trim().split(" ");
									if(strHL[1].equals(strTarget[0])&&strHL[2].equals(strTarget[1]))
									{
										String hitPoints[] = characterModelTarget.getScore(7);
										if(Integer.parseInt(hitPoints[1]) > 0)
										{
											characterModelTarget = ((WarGameStartModel) o).fightChange(characterModel, characterModelTarget);
											mapOnPage.getContainEnemies().set(Integer.parseInt(strTarget[4]), characterModelTarget);
											fight_times =1;
										}
										else
										{
											logging("Character"+characterModelTarget.getCharacterID()+" is dead! Can't be attack!");
										}
										count = 0;
										break;
									}
									count++;
								}
								if(count == highLightList.size())
								{
									logging("Character"+characterModelTarget.getCharacterID()+" is not in the attack range");
								}
							}
							else
							{
								logging("Player had attacked!");
							}
						}
					}
				});
				noneplayer_label_count++;
			}
			
			else
			{
				posY = Integer.parseInt(lblArray[0]);
				posX = Integer.parseInt(lblArray[1]);
				lbl.setBounds(posX*30, posY*30, 30, 30);
				mapPanel.add(lbl);
			}
		}	
		
		//player panel
		JPanel characterViewPanel = new JPanel();
		characterViewPanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		characterViewPanel.setBounds(750, 0, 520, 480);
		frame.getContentPane().add(characterViewPanel);
		characterViewPanel.setLayout(null);
		int picNum = characterModel.getPicNumber();
		ImageIcon img = new ImageIcon("src/image/Character/"+picNum+".png");
		label_pic.setIcon(img);
		label_pic.setBounds(0, 150, 150, 250);
		characterViewPanel.add(label_pic);
		characterViewPanel.setBackground(Color.lightGray);
		equip = characterModel.getEquip();
		backpack = characterModel.getBackpack();
		equip_view = characterModel.getEquip();
		backpack_view = characterModel.getBackpack();
		for(int i=0;i<12;i++)
		{
			
			String result[] = characterModel.getScore(i);
			label_scores[i] = new JLabel(result[0]+":"+result[1]);
			
			if(i<4)
			{
				label_scores[i].setBounds(25, i*30, 150, 30);
			}
			if(i>3 && i<8)
			{
				label_scores[i].setBounds(192, i*30-120, 150, 30);
			}
			if(i>7)
			{
				label_scores[i].setBounds(359, i*30-240, 150, 30);
			}
			characterViewPanel.add(label_scores[i]);
		}
		for(int i=0;i<7;i++)
		{
			final int event_i = i;
			label_equip[i] = new JLabel();
			characterViewPanel.add(label_equip[i]);
			label_equip[i].setBounds(i*70+18, 400, 66, 66);
			label_equip[i].setOpaque(true);
			if(equip_view[i].equals("null"))
			{
				label_equip[i].setBackground(Color.GRAY);
			}
			else
			{
				String prefix[] = equip_view[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip[i].setIcon(img_item);
			}
			label_equip[i].addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(!equip_view[event_i].equals("null"))
						{
							JPopupMenu pop_info = new JPopupMenu();
							pop_info.add(equip_view[event_i]);
							pop_info.show(label_equip[event_i], e.getX(), e.getY());
						}
					}
				}
			});
		}
		
		//backpack
		for(int i=0;i<10;i++)
		{
			final int event_i = i;
			label_backpack[i] = new JLabel();
			characterViewPanel.add(label_backpack[i]);
			if(i<5)
			{
				label_backpack[i].setBounds(i*70+150, 230, 66, 66);
			}
			if(i>4)
			{
				label_backpack[i].setBounds(i*70-200, 300, 66, 66);
			}
			label_backpack[i].setOpaque(true);
			if(backpack_view[i].equals("null"))
			{
				label_backpack[i].setBackground(Color.black);
			}
			else
			{
				String prefix[] = backpack_view[i].trim().split(" ");
				String itemName = prefix[0]+prefix[1];
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack[i].setIcon(img_item);
			}
			label_backpack[i].addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(!backpack_view[event_i].equals("null"))
						{
							JPopupMenu pop_info = new JPopupMenu();
							pop_info.add(backpack_view[event_i]);
							pop_info.show(label_backpack[event_i], e.getX(), e.getY());
						}
					}
				}
			});
		}//backpack for
		//player panel end
		
		//logging window panel
		text_logging.setBounds(750, 480, 520, 220);
		text_logging.setLineWrap(true);
		text_logging.setWrapStyleWord(true);
		text_logging.setEditable(false);
		text_logging.setLayout(null);
		text_logging.setEnabled(false);
		DefaultCaret caret = (DefaultCaret)text_logging.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane jsp = new JScrollPane(text_logging);
		jsp.setBounds(750, 480, 520, 220);
		frame.add(jsp);
		//loogging window panel end
		
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e)
			{
				
			}
		});
		
	}
	/**
     * create the player view frame
     * @param characterModel
     */
	public void createPlayerView(){
		JFrame frame = new JFrame("Character Info");
		frame.setBounds(300, 0, 520, 700);
		frame.setVisible(true);
		frame.setLayout(null);
		JLabel label_pic = new JLabel();
		final JLabel label_scores[] = new JLabel[12];
		final JLabel label_equip[] = new JLabel[7];
		final JLabel label_backpack[] = new JLabel[10];
		JLabel label_showScore[] = new JLabel[12];
		backpack = characterModel.getBackpack();
		equip = characterModel.getEquip();
		for(int i=0;i<12;i++)
		{
			
			String result[] = characterModel.getScore(i);
			label_scores[i] = new JLabel(result[0]+":"+result[1]);
			
			if(i<4)
			{
				label_scores[i].setBounds(25, i*30, 150, 30);
			}
			if(i>3 && i<8)
			{
				label_scores[i].setBounds(192, i*30-120, 150, 30);
			}
			if(i>7)
			{
				label_scores[i].setBounds(359, i*30-240, 150, 30);
			}
			frame.add(label_scores[i]);
		}
		int picNum = characterModel.getPicNumber();
		ImageIcon img = new ImageIcon("src/image/Character/"+picNum+".png");
		label_pic.setIcon(img);
		label_pic.setBounds(210, 150, 150, 250);
		frame.add(label_pic);
		
		for(int i=0;i<7;i++)
		{
			final int event_i = i;
			label_equip[i] = new JLabel();
			frame.add(label_equip[i]);
			label_equip[i].setBounds(i*70+18, 400, 66, 66);
			label_equip[i].setOpaque(true);
			if(equip[i].equals("null"))
			{
				label_equip[i].setBackground(Color.GRAY);
			}
			else
			{
				String prefix[] = equip[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip[i].setIcon(img_item);
			}
			label_equip[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						if(!equip[event_i].equals("null"))
						{
							
							for(int i =0;i<10;i++)
							{
								if(backpack[i].equals("null"))
								{
									label_backpack[i].setIcon(label_equip[event_i].getIcon());
									backpack[i] = equip[event_i];
									characterModel.setBackpack(backpack[i], i);
									break;
								}
							}
							String buffer = new String();
							label_equip[event_i].setIcon(null);
							buffer = equip[event_i];
							equip[event_i] = "null";
							label_equip[event_i].setBackground(Color.GRAY);
							characterModel.setEquip(equip[event_i], event_i);
							characterModel.setEquipChanged(buffer,null);
							for(int i=0;i<12;i++)
							{
								String result[] = characterModel.getScore(i);
								label_scores[i].setText(result[0]+":"+result[1]);
							}
							refreshInventory();
						}
						
					}
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(!equip[event_i].equals("null"))
						{
							JPopupMenu pop_info = new JPopupMenu();
							pop_info.add(equip[event_i]);
							pop_info.show(label_equip[event_i], e.getX(), e.getY());
						}
					}
				}
			});
		}
		for(int i=0;i<10;i++)
		{
			final int event_i = i;
			label_backpack[i] = new JLabel();
			label_backpack[i].setOpaque(true);
			frame.add(label_backpack[i]);
			if(i<5)
			{
				label_backpack[i].setBounds(i*70+85, 500, 66, 66);
			}
			if(i>4)
			{
				label_backpack[i].setBounds(i*70-265, 570, 66, 66);
			}
			if(backpack[i].equals("null"))
			{
				label_backpack[i].setBackground(Color.BLACK);
			}
			else
			{
				String prefix[] = backpack[i].trim().split(" ");
				String itemName = prefix[0]+prefix[1];
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack[i].setIcon(img_item);
			}
			label_backpack[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						if(!backpack[event_i].equals("null"))
						{
							String prefix[] = backpack[event_i].trim().split(" ");
							String itemName = prefix[0];
							int index = 0;
							switch(itemName)
							{
								case "Helmet":
									index = 0;
									break;
								case "Armor":
									index = 1;
									break;
								case "Shield":
									index = 2;
									break;
								case "Ring":
									index = 3;
									break;
								case "Belt":
									index = 4;
									break;
								case "Boots":
									index = 5;
									break;
								case "Weapon":
									index = 6;
									break;
							}
							if(equip[index].equals("null"))
							{
								String buffer = new String();
								buffer = backpack[event_i];
								label_equip[index].setIcon(label_backpack[event_i].getIcon());
								equip[index] = backpack[event_i];
								label_backpack[event_i].setIcon(null);
								label_backpack[event_i].setBackground(Color.black);
								backpack[event_i] = "null";
								characterModel.setEquip(buffer, index);
								characterModel.setBackpack(backpack[event_i], event_i);
								characterModel.setEquipChanged(null,buffer);
								for(int i=0;i<12;i++)
								{
									String result[] = characterModel.getScore(i);
									label_scores[i].setText(result[0]+":"+result[1]);
								}
							}
							else
							{
								String buffer = new String();
								String buffer2 = new String();
								Icon img_buffer = new ImageIcon();
								buffer = equip[index];
								buffer2 = backpack[event_i];
								img_buffer = label_equip[index].getIcon();
								label_equip[index].setIcon(label_backpack[event_i].getIcon());
								equip[index] = backpack[event_i];
								label_backpack[event_i].setIcon(img_buffer);
								backpack[event_i] = buffer;
								characterModel.setEquip(equip[index], index);
								characterModel.setBackpack(backpack[event_i], event_i);
								characterModel.setEquipChanged(buffer,buffer2);
								for(int i=0;i<12;i++)
								{
									String result[] = characterModel.getScore(i);
									label_scores[i].setText(result[0]+":"+result[1]);
								}
							}
							refreshInventory();
						}
						
					}
					if(e.getButton() == MouseEvent.BUTTON3)
					{
						if(!backpack[event_i].equals("null"))
						{
							JPopupMenu pop_info = new JPopupMenu();
							pop_info.add(backpack[event_i]);
							pop_info.show(label_backpack[event_i], e.getX(), e.getY());
						}
					}
				}
			});
		}
	}

	/**
     * get the item from the loot when monster dead
     * 
     */
	public void getAllItem()
	{
		String equip_buffer[] = nonePlayerModel.getEquip();
		String backpack_buffer[] = nonePlayerModel.getBackpack();
		int count = 0;

		final JFrame frame = new JFrame("Loot");
		frame.setBounds(300, 0, 220, 400);
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setBackground(Color.DARK_GRAY);
		for(int i=0;i<7;i++)
		{
			final int event_i = i;
			if(!equip_buffer[i].equals("null"))
			{
				String str[] = equip_buffer[i].trim().split(" ");
				final JLabel label_pic = new JLabel();
				final JLabel label_text = new JLabel(str[0]+" "+str[1]+" "+str[2]);
				ImageIcon img_item = new ImageIcon("src/image/item/"+str[0]+"/"+str[1]+".jpeg");
				label_pic.setIcon(img_item);
				label_pic.setBounds(0, count*66, 66, 66);
				label_text.setBounds(70,count*66,150,66);
				frame.add(label_pic);
				frame.add(label_text);
				final String equip_info = equip_buffer[i];

				label_pic.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){

						if(e.getButton() == MouseEvent.BUTTON1)
						{
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										String str[] = equip_info.trim().split(" ");
										String itemType = str[0];
										String enchanType = str[1];
										String enchanNum = str[2];
										backpack[i] = itemType+" "+enchanType+" "+enchanNum;
										ImageIcon img_item = new ImageIcon("src/image/item/"+itemType+"/"+enchanType+".jpeg");
										label_backpack[i].setIcon(img_item);
										label_pic.setIcon(null);
										label_text.setText(null);
										nonePlayerModel.setEquip("null",event_i);
										break;
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Backpack is full !");
							}
						}
					}
				});
				count++;
			}
		}
		for(int i=0;i<10;i++)
		{
			final int event_i = i;
			if(!backpack_buffer[i].equals("null"))
			{
				String str[] = backpack_buffer[i].trim().split(" ");
				final JLabel label_pic = new JLabel();
				final JLabel label_text = new JLabel(str[0]+" "+str[1]+" "+str[2]);
				ImageIcon img_item = new ImageIcon("src/image/item/"+str[0]+"/"+str[1]+".jpeg");
				label_pic.setIcon(img_item);
				label_pic.setBounds(0, count*66, 66, 66);
				label_text.setBounds(70,count*66,150,66);
				frame.add(label_pic);
				frame.add(label_text);
				final String backpack_info = backpack_buffer[i];
				label_pic.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){
						
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if(label_pic.getIcon()!=null)
									{
										if((backpack[i] == null)||(backpack[i].equals("null")))
										{
											String str[] = backpack_info.trim().split(" ");
											String itemType = str[0];
											String enchanType = str[1];
											String enchanNum = str[2];
											backpack[i] = itemType+" "+enchanType+" "+enchanNum;
											ImageIcon img_item = new ImageIcon("src/image/item/"+itemType+"/"+enchanType+".jpeg");
											label_backpack[i].setIcon(img_item);
											label_pic.setIcon(null);
											label_text.setText(null);
											nonePlayerModel.setBackpack("null",event_i);
											break;
										}
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Backpack is full !");
							}
						}
					}
				});
				count++;
			}
		}
	}
	

	/**
     * switch the player's item with none player's item
     * 
     */
	
	public void switchItem(){
		JFrame frame = new JFrame("Switch Item");
		frame.setBounds(300, 0, 800, 700);
		frame.setVisible(true);
		frame.setLayout(null);
		final JLabel label_equip[] = new JLabel[7];
		final JLabel label_backpack[] = new JLabel[10];
		final JLabel label_equip_npc[] = new JLabel[7];
		final JLabel label_backpack_npc[] = new JLabel[10];
		
		equip = characterModel.getEquip();
		backpack = characterModel.getBackpack();
		equip_npc = nonePlayerModel.getEquip();
		backpack_npc = nonePlayerModel.getBackpack();
		for(int i=0;i<7;i++)
		{
			final int event_i = i;
			label_equip[i] = new JLabel();
			frame.add(label_equip[i]);
			label_equip[i].setBounds(100, i*70+100, 66, 66);
			label_equip[i].setOpaque(true);
			if(equip[i].equals("null"))
			{
				label_equip[i].setBackground(Color.GRAY);
			}
			else
			{
				String prefix[] = equip[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip[i].setIcon(img_item);
			}
			label_equip[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						if(!equip[event_i].equals("null"))
						{
							ArrayList<String> allItem = new ArrayList();
							for(int i=0;i<7;i++)
							{
								if(!equip_npc[i].equals("null"))
								{
									allItem.add("equip"+" "+i);
								}
							}
							for(int i=0;i<10;i++)
							{
								if(!backpack_npc[i].equals("null"))
								{
									allItem.add("backpack"+" "+i);
								}
							}
							if(allItem.size() != 0)
							{
								Random rand = new Random();
								int a = rand.nextInt(allItem.size())+0;
								String switchItem[] = allItem.get(a).trim().split(" ");
								if(switchItem[0].equals("equip"))
								{
									String player = equip_npc[Integer.parseInt(switchItem[1])];
									String npc = equip[event_i];
									Icon buffer = new ImageIcon();
									buffer = label_equip[event_i].getIcon();								
									for(int i=0;i<10;i++)
									{
										if(backpack[i].equals("null"))
										{
											backpack[i] = player;
											label_backpack[i].setIcon(label_equip_npc[Integer.parseInt(switchItem[1])].getIcon());
											characterModel.setEquipChanged(equip[event_i], null);
											equip[event_i] = "null";
											break;
										}
									}
									for(int i=0;i<10;i++)
									{
										if(backpack_npc[i].equals("null"))
										{
											backpack_npc[i] = npc;
											label_backpack_npc[i].setIcon(buffer);
											nonePlayerModel.setEquipChanged(equip_npc[Integer.parseInt(switchItem[1])], null);
											equip_npc[Integer.parseInt(switchItem[1])] = "null";
											break;
										}
									}
									label_equip_npc[Integer.parseInt(switchItem[1])].setIcon(null);
									label_equip[event_i].setIcon(null);
									label_equip_npc[Integer.parseInt(switchItem[1])].setBackground(Color.gray);
									label_equip[event_i].setBackground(Color.gray);
								}
								if(switchItem[0].equals("backpack"))
								{
									String player = backpack_npc[Integer.parseInt(switchItem[1])];
									String npc = equip[event_i];
									Icon buffer = new ImageIcon();
									buffer = label_equip[event_i].getIcon();
									backpack_npc[Integer.parseInt(switchItem[1])] = "null";
									for(int i=0;i<10;i++)
									{
										if(backpack[i].equals("null"))
										{
											backpack[i] = player;
											label_backpack[i].setIcon(label_backpack_npc[Integer.parseInt(switchItem[1])].getIcon());
											characterModel.setEquipChanged(equip[event_i], null);
											equip[event_i] = "null";
											break;
										}
									}
									backpack_npc[Integer.parseInt(switchItem[1])] = npc;
									label_backpack_npc[Integer.parseInt(switchItem[1])].setIcon(buffer);
									label_equip[event_i].setIcon(null);
									label_equip[event_i].setBackground(Color.gray);
								}
								refreshInventory();				
							}
						}
					}
				}
			});
			//npc view
			label_equip_npc[i] = new JLabel();
			frame.add(label_equip_npc[i]);
			label_equip_npc[i].setBounds(630, i*70+100, 66, 66);
			label_equip_npc[i].setOpaque(true);
			if(equip_npc[i].equals("null"))
			{
				label_equip_npc[i].setBackground(Color.GRAY);
			}
			else
			{
				String prefix[] = equip_npc[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip_npc[i].setIcon(img_item);
			}
		}
		for(int i=0;i<10;i++)
		{
			final int event_i = i;
			label_backpack[i] = new JLabel();
			label_backpack[i].setOpaque(true);
			frame.add(label_backpack[i]);
			if(i<5)
			{
				label_backpack[i].setBounds(170, i*70+185, 66, 66);
			}
			if(i>4)
			{
				label_backpack[i].setBounds(240, i*70-165, 66, 66);
			}
			if(backpack[i].equals("null"))
			{
				label_backpack[i].setBackground(Color.BLACK);
			}
			else
			{
				String prefix[] = backpack[i].trim().split(" ");
				String itemName = prefix[0]+prefix[1];
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack[i].setIcon(img_item);
			}
			label_backpack[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					
					if(e.getButton() == MouseEvent.BUTTON1)
					{
						if(!backpack[event_i].equals("null"))
						{
							ArrayList<String> allItem = new ArrayList();
							for(int i=0;i<7;i++)
							{
								if(!equip_npc[i].equals("null"))
								{
									allItem.add("equip"+" "+i);
								}
							}
							for(int i=0;i<10;i++)
							{
								if(!backpack_npc[i].equals("null"))
								{
									allItem.add("backpack"+" "+i);
								}
							}
							if(allItem.size() !=0)
							{
								Random rand = new Random();
								int a = rand.nextInt(allItem.size())+0;
								String switchItem[] = allItem.get(a).trim().split(" ");
								if(switchItem[0].equals("equip"))
								{
									String player = equip_npc[Integer.parseInt(switchItem[1])];
									String npc = backpack[event_i];
									Icon buffer = new ImageIcon();
									buffer = label_backpack[event_i].getIcon();
									equip_npc[Integer.parseInt(switchItem[1])] = "null";
									backpack[event_i] = "null";
									backpack[event_i] = player;
									label_backpack[event_i].setIcon(label_equip_npc[Integer.parseInt(switchItem[1])].getIcon());
									for(int i=0;i<10;i++)
									{
										if(backpack_npc[i].equals("null"))
										{
											backpack_npc[i] = npc;
											label_backpack_npc[i].setIcon(buffer);
											break;
										}
									}
									label_equip_npc[Integer.parseInt(switchItem[1])].setIcon(null);
									label_equip_npc[Integer.parseInt(switchItem[1])].setBackground(Color.gray);
								}
								if(switchItem[0].equals("backpack"))
								{
									String player = backpack_npc[Integer.parseInt(switchItem[1])];
									String npc = backpack[event_i];
									Icon buffer = new ImageIcon();
									backpack_npc[Integer.parseInt(switchItem[1])] = "null";
									backpack[event_i] = "null";
									buffer = label_backpack[event_i].getIcon();
									backpack[event_i] = player;
									label_backpack[event_i].setIcon(label_backpack_npc[Integer.parseInt(switchItem[1])].getIcon());
									backpack_npc[Integer.parseInt(switchItem[1])] = npc;
									label_backpack_npc[Integer.parseInt(switchItem[1])].setIcon(buffer);
								}
								refreshInventory();
							}
						}
					}
				}
			});
			
			//npc view
			label_backpack_npc[i] = new JLabel();
			label_backpack_npc[i].setOpaque(true);
			frame.add(label_backpack_npc[i]);
			if(i<5)
			{
				label_backpack_npc[i].setBounds(560, i*70+185, 66, 66);
			}
			if(i>4)
			{
				label_backpack_npc[i].setBounds(490, i*70-165, 66, 66);
			}
			if(backpack_npc[i].equals("null"))
			{
				label_backpack_npc[i].setBackground(Color.BLACK);
			}
			else
			{
				String prefix[] = backpack_npc[i].trim().split(" ");
				String itemName = prefix[0]+prefix[1];
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack_npc[i].setIcon(img_item);
			}
		}
	}
	

	/**
     * refresh the player's inventory
     * l
     */
	
	public void refreshInventory(){
		for(int i=0;i<12;i++)
		{
			String result[] = characterModel.getScore(i);
			label_scores[i].setText(result[0]+":"+result[1]);
		}
		for(int i=0;i<7;i++)
		{
			if(equip[i].equals("null"))
			{
				label_equip[i].setIcon(null);
				label_equip[i].setBackground(Color.gray);
			}
			else
			{
				String prefix[] = equip[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip[i].setIcon(img_item);
			}
		}
		
		for(int i=0;i<10;i++)
		{
			if(backpack[i].equals("null"))
			{
				label_backpack[i].setIcon(null);
				label_backpack[i].setBackground(Color.black);
			}
			else
			{
				String prefix[] = backpack[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack[i].setIcon(img_item);
			}
		}
	}
	
	/**
	 * set corresponde value to panel
	 */
	public void setPanel(WarGameCharacterModel characterModel)
	{
		int picNum = characterModel.getPicNumber();
		ImageIcon img = new ImageIcon("src/image/Character/"+picNum+".png");
		label_pic.setIcon(img);
		equip_view = new String[7];
		backpack_view = new String[10];
		equip_view = characterModel.getEquip();
		backpack_view = characterModel.getBackpack();
		for(int i=0;i<12;i++)
		{
			String result[] = characterModel.getScore(i);
			label_scores[i].setText(result[0]+":"+result[1]);
		}
		for(int i=0;i<7;i++)
		{
			if(equip_view[i].equals("null"))
			{
				label_equip[i].setIcon(null);
				label_equip[i].setBackground(Color.gray);
			}
			else
			{
				String prefix[] = equip_view[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip[i].setIcon(img_item);
			}
		}
		
		for(int i=0;i<10;i++)
		{
			if(backpack_view[i].equals("null"))
			{
				label_backpack[i].setIcon(null);
				label_backpack[i].setBackground(Color.black);
			}
			else
			{
				String prefix[] = backpack_view[i].trim().split(" ");
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack[i].setIcon(img_item);
			}
		}
	}
	
	/**
	 * show log widow
	 */
	public static void logging(String newMessage)
	{
		text_logging.append(newMessage+"\r\n");
	}
	/**
	 * character take action
	 * @throws InterruptedException 
	 */
	public void takeAction(Map<String, ArrayList<String>> characterActionsByMap) throws InterruptedException{
		for (String string : characterActionsByMap.keySet()) {
			String indexInList = string.split(" ")[1];
			String enemyPos = null;
			String friendPos = null;
			
			if (string.startsWith("m")) {
				ArrayList<String> actions = characterActionsByMap.get(string);
				for (String action : actions) {
					if (action.equals("Freezing")) {
						logging("Freezing, Cannot move");
						break;
					}
					if(action.equals("Frightening")){
						//反向移动
						logging("Frightening, Escape");
						for (int i = 0; i < 3; i++) {
							for (String strPos : enemyPosList) {
								if (strPos.split(" ")[3].equals(indexInList)) {
									enemyPos = strPos;
									showHighlight(characterForStrategy, "m",enemyPos);
									getTargetAndAttack();
								}
							}
							String result[];
							result = characterMovement(enemyPos, "Escape").split(" ");
							String pos = new String();
							if(result[0].equals("f"))
							{
								pos = result[1]+" "+result[2]+" "+result[3];
								showHighlight(characterForStrategy, "m",pos);
								getTargetAndAttack();
							}
							else
							{
								String str[] = enemyPos.trim().split(" ");
								pos = str[0]+" "+str[1]+" "+str[2];
								showHighlight(characterForStrategy, "m",pos);
							}
						}
					}
					if(action.equals("Move_Aggressively")){
						//目标移动
						logging("Move aggressively");
						String result[];
						for (int i = 0; i < 3; i++) {
							
							for (String strPos : enemyPosList) {
								if (strPos.split(" ")[3].equals(indexInList)) {
									enemyPos = strPos;
									showHighlight(characterForStrategy, "m",enemyPos);
									getTargetAndAttack();
								}
							}
							result = characterMovement(enemyPos, "Move_Aggressively").split(" ");
							String pos = new String();
							if(result[0].equals("f"))
							{
								pos = result[1]+" "+result[2]+" "+result[3];
								showHighlight(characterForStrategy, "m",pos);
								getTargetAndAttack();
							}
							else
							{
								String str[] = enemyPos.trim().split(" ");
								pos = str[0]+" "+str[1]+" "+str[2];
								showHighlight(characterForStrategy, "m",pos);
							}
							if (result[0].equals("i")) {
								//loot a chest
								i = i-1;
								itemModel = mapOnPage.getContainItems().get(Integer.parseInt(result[2]));
								String itemType = itemModel.getItemType();
								String enchanType = itemModel.getEnchanType();
								String enchanNum = itemModel.getEnchanNumber();
								String itemInfo = new String();
								if(itemType.equals("Weapon"))
								{
									ArrayList<String> speList = itemModel.getEnchantList();
									int attackRange = itemModel.getAttackRange();
									String spe = new String();
									for (String strspe : speList) {
										int k = speList.indexOf(strspe);
										if(k == 0)
										{
											spe = strspe;
										}
										else
										{
											spe = spe+","+strspe;
										}
									}
									itemInfo = itemType+" "+enchanType+" "+enchanNum+" "+spe+" "+attackRange;
								}
								else
								{
									itemInfo = itemType+" "+enchanType+" "+enchanNum;
								}
								WarGameStartModel startModel = new WarGameStartModel();
								WarGameCharacterModel character = mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1]));
								backpack_npc = character.getBackpack();
								Boolean result_full = startModel.checkBackpack(backpack_npc);
								if(result_full == true)
								{
									for(int j =0;j<10;j++)
									{
										if((backpack_npc[j] == null)||(backpack_npc[j].equals("null")))
										{
											backpack_npc[j] = itemInfo;
											map[Integer.parseInt(result[3])][Integer.parseInt(result[4])] = "f";
											mapElementsLbls.get(Integer.parseInt(result[5])).setIcon(floor);
											mapElementsLbls.get(Integer.parseInt(result[5])).setText(result[3]+" "+result[4]+" f");
											character.setBackpack(backpack_npc[j], j);
											mapOnPage.getContainEnemies().set(Integer.parseInt(string.split(" ")[1]), character);
											break;
										}
									}
								}
								logging("Reach item "+ mapOnPage.getContainItems().get(Integer.parseInt(result[2])));
							}else if(result[0].equals("m")){
								//attack	
							}else if(result[0].equals("n")){
								//attack										
							}else if(result[0].equals("h")){
								//attack
							}
						}
					}
				}
			}else if (string.startsWith("n")) {
				ArrayList<String> actions = characterActionsByMap.get(string);
				for (String action : actions) {
					if(action.equals("Move_Randomly")){
						logging("Move Randomly");
						String result[];
						for (int i = 0; i < 3; i++) {
							for (String strPos : friendPosList) {
								if (strPos.split(" ")[3].equals(indexInList)) {
									friendPos = strPos;
								}
							}
							result = characterMovement(friendPos, "Move_Randomly").split(" ");
							String pos = new String();
							if(result[0].equals("f"))
							{
								pos = result[1]+" "+result[2]+" "+result[3];
								showHighlight(characterForStrategy, "n",pos);
							}
							else
							{
								String str[] = enemyPos.trim().split(" ");
								pos = str[0]+" "+str[1]+" "+str[2];
								showHighlight(characterForStrategy, "n",pos);
							}
							if (result[0].equals("i")) {
								//loot chest
								i = i-1;
								itemModel = mapOnPage.getContainItems().get(Integer.parseInt(result[2]));
								String itemType = itemModel.getItemType();
								String enchanType = itemModel.getEnchanType();
								String enchanNum = itemModel.getEnchanNumber();
								String itemInfo = new String();
								if(itemType.equals("Weapon"))
								{
									ArrayList<String> speList = itemModel.getEnchantList();
									int attackRange = itemModel.getAttackRange();
									String spe = new String();
									for (String strspe : speList) {
										int k = speList.indexOf(strspe);
										if(k == 0)
										{
											spe = strspe;
										}
										else
										{
											spe = spe+","+strspe;
										}
									}
									itemInfo = itemType+" "+enchanType+" "+enchanNum+" "+spe+" "+attackRange;
								}
								else
								{
									itemInfo = itemType+" "+enchanType+" "+enchanNum;
								}
								WarGameStartModel startModel = new WarGameStartModel();
								WarGameCharacterModel character = mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1]));
								backpack_npc = character.getBackpack();
								Boolean result_full = startModel.checkBackpack(backpack_npc);
								if(result_full == true)
								{
									for(int j =0;j<10;j++)
									{
										if((backpack_npc[j] == null)||(backpack_npc[j].equals("null")))
										{
											backpack_npc[j] = itemInfo;
											map[Integer.parseInt(result[3])][Integer.parseInt(result[4])] = "f";
											mapElementsLbls.get(Integer.parseInt(result[5])).setIcon(floor);
											mapElementsLbls.get(Integer.parseInt(result[5])).setText(result[3]+" "+result[4]+" f");
											character.setBackpack(backpack_npc[j], j);
											mapOnPage.getContainEnemies().set(Integer.parseInt(string.split(" ")[1]), character);
											break;
										}
									}
								}
								logging("Reach item "+ mapOnPage.getContainItems().get(Integer.parseInt(result[2])));
							}
						}
					}
				}
			}
		}
	}
	/**
	 * character movement
	 */
	public String characterMovement(String characterPos, String moveType){
		String result = null;

		int posX;
		int posY;
		int index;
		int indexInList;
		String[] characterPosArray = characterPos.split(" ");
		posY = Integer.parseInt(characterPosArray[0]);
		posX = Integer.parseInt(characterPosArray[1]);
		index = Integer.parseInt(characterPosArray[2]);
		indexInList = Integer.parseInt(characterPosArray[3]);
		
		String[] leftPos = mapElementsLbls.get(index-1).getText().split(" ");
		String[] rightPos = mapElementsLbls.get(index+1).getText().split(" ");
		String[] upPos = null;
		String[] downPos = null;
		if (index-map[0].length>0) {
			upPos =  mapElementsLbls.get(index-map[0].length).getText().split(" ");
		}
		if (index+map[0].length<(map.length)*(map[0].length)) {
			downPos =  mapElementsLbls.get(index+map[0].length).getText().split(" ");
		}
		
		int posXC;
		int posYC;
		int indexC;
		String[] heroPosArray = heroPos.split(" ");
		posYC = Integer.parseInt(heroPosArray[0]);
		posXC = Integer.parseInt(heroPosArray[1]);
		indexC = Integer.parseInt(heroPosArray[2]);
		
		if (moveType.equals("Move_Aggressively")) {
			
			ArrayList<String> moveAble =new ArrayList<String>();
			String nextMove = new String();
			int lastDis = 0;
			String right[] = map[posY][posX+1].trim().split(" ");
			String left[] = map[posY][posX-1].trim().split(" ");
			String up[] = map[posY-1][posX].trim().split(" ");
			String down[] = map[posY+1][posX].trim().split(" ");
			if(!(right[0].equals("x")||right[0].equals("n")||right[0].equals("m")||right[0].equals("O")||right[0].equals("I")))
			{
				moveAble.add(right[0]+" "+posY+" "+(posX+1));
			}
			if(!(left[0].equals("x")||left[0].equals("n")||left[0].equals("m")||left[0].equals("O")||left[0].equals("I")))
			{
				moveAble.add(left[0]+" "+posY+" "+(posX-1));
			}
			if(!(up[0].equals("x")||up[0].equals("n")||up[0].equals("m")||up[0].equals("O")||up[0].equals("I")))
			{
				moveAble.add(up[0]+" "+(posY-1)+" "+posX);
			}
			if(!(down[0].equals("x")||down[0].equals("n")||down[0].equals("m")||down[0].equals("O")||down[0].equals("I")))
			{
				moveAble.add(down[0]+" "+(posY+1)+" "+posX);
			}
			for (String str_buffer : moveAble) 
			{
				String str[] =str_buffer.trim().split(" ");
				if(moveAble.indexOf(str_buffer) == 0)
				{
					lastDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					nextMove = str_buffer;
				}
				else
				{
					int curDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					if(lastDis > curDis)
					{
						lastDis = curDis;
						nextMove = str_buffer;
					}
				}
			}
			String nextInfo[] = nextMove.trim().split(" ");
			int nextIndex = 0;
			if(Integer.parseInt(nextInfo[2]) == posX)
			{
				if(Integer.parseInt(nextInfo[1])>posY)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+map[0].length);
					nextIndex = index+map[0].length;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-map[0].length);
					nextIndex = index-map[0].length;
				}
			}
			if(Integer.parseInt(nextInfo[1]) == posY)
			{
				if(Integer.parseInt(nextInfo[2])>posX)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+1);
					nextIndex = index+1;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-1);
					nextIndex = index-1;
				}
			}
			if(nextInfo[0].equals("f"))	
			{
				JLabel characterPosLbl = mapElementsLbls.get(index);
				//change label_noneplayer_list
				int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
				String str[] = characterPosLbl.getText().trim().split(" ");
				String back_text = nextInfo[1]+" "+nextInfo[2]+" "+str[2]+" "+str[3]+" "+str[4];
				label_noneplayer_list.get(index_noneplayer).setText(back_text);
				//end
				Rectangle posB = characterPosLbl.getBounds();
				JLabel characterDesLbl = mapElementsLbls.get(nextIndex);
				Rectangle desB = characterDesLbl.getBounds();
				characterPosLbl.setBounds(desB);
				characterDesLbl.setBounds(posB);
				mapElementsLbls.set(index, characterDesLbl);
				mapElementsLbls.set(nextIndex, characterPosLbl);
				String posIndex = nextInfo[1]+" "+nextInfo[2] + " " + nextIndex + " " + indexInList;
				enemyPosList.remove(posIndex);
				enemyPosList.add(posIndex);
				map[posY][posX] = "f";
				map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
				logging(mapOnPage.getContainEnemies().get(indexInList)+" moved");
			}
		}else if(moveType.equals("Escape")){
			ArrayList<String> moveAble =new ArrayList<String>();
			String nextMove = new String();
			int lastDis = 0;
			String right[] = map[posY][posX+1].trim().split(" ");
			String left[] = map[posY][posX-1].trim().split(" ");
			String up[] = map[posY-1][posX].trim().split(" ");
			String down[] = map[posY+1][posX].trim().split(" ");
			if(!(right[0].equals("x")||right[0].equals("n")||right[0].equals("m")||right[0].equals("O")||right[0].equals("I")))
			{
				moveAble.add(right[0]+" "+posY+" "+(posX+1));
			}
			if(!(left[0].equals("x")||left[0].equals("n")||left[0].equals("m")||left[0].equals("O")||left[0].equals("I")))
			{
				moveAble.add(left[0]+" "+posY+" "+(posX-1));
			}
			if(!(up[0].equals("x")||up[0].equals("n")||up[0].equals("m")||up[0].equals("O")||up[0].equals("I")))
			{
				moveAble.add(up[0]+" "+(posY-1)+" "+posX);
			}
			if(!(down[0].equals("x")||down[0].equals("n")||down[0].equals("m")||down[0].equals("O")||down[0].equals("I")))
			{
				moveAble.add(down[0]+" "+(posY+1)+" "+posX);
			}
			for (String str_buffer : moveAble) 
			{
				String str[] =str_buffer.trim().split(" ");
				if(moveAble.indexOf(str_buffer) == 0)
				{
					lastDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					nextMove = str_buffer;
				}
				else
				{
					int curDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					if(lastDis < curDis)
					{
						lastDis = curDis;
						nextMove = str_buffer;
					}
				}
			}
			String nextInfo[] = nextMove.trim().split(" ");
			int nextIndex = 0;
			if(Integer.parseInt(nextInfo[2]) == posX)
			{
				if(Integer.parseInt(nextInfo[1])>posY)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+map[0].length);
					nextIndex = index+map[0].length;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-map[0].length);
					nextIndex = index-map[0].length;
				}
			}
			if(Integer.parseInt(nextInfo[1]) == posY)
			{
				if(Integer.parseInt(nextInfo[2])>posX)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+1);
					nextIndex = index+1;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-1);
					nextIndex = index-1;
				}
			}
			if(nextInfo[0].equals("f"))	
			{
				JLabel characterPosLbl = mapElementsLbls.get(index);
				//change label_noneplayer_list
				int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
				String str[] = characterPosLbl.getText().trim().split(" ");
				String back_text = nextInfo[1]+" "+nextInfo[2]+" "+str[2]+" "+str[3]+" "+str[4];
				label_noneplayer_list.get(index_noneplayer).setText(back_text);
				//end
				Rectangle posB = characterPosLbl.getBounds();
				JLabel characterDesLbl = mapElementsLbls.get(nextIndex);
				Rectangle desB = characterDesLbl.getBounds();
				characterPosLbl.setBounds(desB);
				characterDesLbl.setBounds(posB);
				mapElementsLbls.set(index, characterDesLbl);
				mapElementsLbls.set(nextIndex, characterPosLbl);
				String posIndex = nextInfo[1]+" "+nextInfo[2] + " " + nextIndex + " " + indexInList;
				enemyPosList.remove(posIndex);
				enemyPosList.add(posIndex);
				map[posY][posX] = "f";
				map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
				logging(mapOnPage.getContainEnemies().get(indexInList)+" moved");
			}
		}
		else if(moveType.equals("Move_Randomly")){
			ArrayList<String> canMove = new ArrayList<String>();

			if (upPos[2].equals("f")) {
				canMove.add("up");
			}
			
			if (downPos[2].equals("f")) {
				canMove.add("down");
			}
			
			if (leftPos[2].equals("f")) {
				canMove.add("left");
			}
			
			if (rightPos[2].equals("f")) {
				canMove.add("right");
			}
			Random random = new Random();
			int direction = random.nextInt(canMove.size());
			if (canMove.get(direction).equals("up")) {
				if (upPos[2].equals("i")) {
					String str[] = map[posY-1][posX].trim().split(" ");
					result = map[posY-1][posX]+" "+(posY-1)+" "+posX+" "+(index-map[0].length);
				}
				else if (upPos[2].equals("m")) {
					String str[] = map[posY-1][posX].trim().split(" ");
					result = map[posY-1][posX]+" "+(posY-1)+" "+posX+" "+(index-map[0].length);
				}
				else if (upPos[2].equals("n")) {
					String str[] = map[posY-1][posX].trim().split(" ");
					result = map[posY-1][posX]+" "+(posY-1)+" "+posX+" "+(index-map[0].length);
				}
				else if (upPos[2].equals("h")) {
					result = "h";
				}
				else if(upPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
					//change label_noneplayer_list
					int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
					String str[] = characterPosLbl.getText().trim().split(" ");
					str[0] = Integer.parseInt(str[0]) -1+"";
					String back_text = str[0]+" "+str[1]+" "+str[2]+" "+str[3]+" "+str[4];
					label_noneplayer_list.get(index_noneplayer).setText(back_text);
					//end
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index-(map[0].length));
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index-(map[0]).length), characterPosLbl);
					result = (posY-1) + " " + posX + " " + (index-(map[0].length)) + " " + indexInList;
					friendPosList.remove(result);
					friendPosList.add(result);
					map[posY][posX] = "f";
					map[posY-1][posX] = "n"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					logging(mapOnPage.getContainFriends().get(indexInList)+" moved");
					result = "f "+result;
				}
			}
			if (canMove.get(direction).equals("down")) {
				if (downPos[2].equals("i")) {
					String str[] = map[posY+1][posX].trim().split(" ");
					result = map[posY+1][posX]+" "+(posY+1)+" "+posX+" "+(index+map[0].length);
				}
				else if (downPos[2].equals("m")) {
					String str[] = map[posY+1][posX].trim().split(" ");
					result = map[posY+1][posX]+" "+(posY+1)+" "+posX+" "+(index+map[0].length);
				}
				else if (downPos[2].equals("n")) {
					String str[] = map[posY+1][posX].trim().split(" ");
					result = map[posY+1][posX]+" "+(posY+1)+" "+posX+" "+(index+map[0].length);
				}
				else if (downPos[2].equals("h")) {
					result = "h";
				}
				else if(downPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
					//change label_noneplayer_list
					int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
					String str[] = characterPosLbl.getText().trim().split(" ");
					str[0] = Integer.parseInt(str[0]) +1+"";
					String back_text = str[0]+" "+str[1]+" "+str[2]+" "+str[3]+" "+str[4];
					label_noneplayer_list.get(index_noneplayer).setText(back_text);
					//end
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index+(map[0].length));
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index+(map[0]).length), characterPosLbl);
					result = (posY+1) + " " + posX + " " + (index+(map[0].length)) + " " + indexInList;
					friendPosList.remove(result);
					friendPosList.add(result);
					map[posY][posX] = "f";
					map[posY+1][posX] = "n"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					logging(mapOnPage.getContainFriends().get(indexInList)+" moved");
					result = "f "+result;
				}
			}
			if (canMove.get(direction).equals("left")) {
				if (leftPos[2].equals("i")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = map[posY][posX-1]+" "+posY+" "+(posX-1)+" "+(index-1);
				}
				else if (leftPos[2].equals("m")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = map[posY][posX-1]+" "+posY+" "+(posX-1)+" "+(index-1);
				}
				else if (leftPos[2].equals("n")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = map[posY][posX-1]+" "+posY+" "+(posX-1)+" "+(index-1);
				}
				else if (leftPos[2].equals("h")) {
					result = "h";
				}
				else if(leftPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
					//change label_noneplayer_list
					int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
					String str[] = characterPosLbl.getText().trim().split(" ");
					str[1] = Integer.parseInt(str[1]) -1+"";
					String back_text = str[0]+" "+str[1]+" "+str[2]+" "+str[3]+" "+str[4];
					label_noneplayer_list.get(index_noneplayer).setText(back_text);
					//end
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index-1);
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index-1), characterPosLbl);
					result = posY + " " + (posX-1) + " " + (index-1) + " " + indexInList;
					friendPosList.remove(result);
					friendPosList.add(result);
					map[posY][posX] = "f";
					map[posY][posX-1] = "n"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					logging(mapOnPage.getContainFriends().get(indexInList)+" moved");
					result = "f "+result;
				}
			}
			if (canMove.get(direction).equals("right")) {
				if (rightPos[2].equals("i")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = map[posY][posX+1]+" "+posY+" "+(posX+1)+" "+(index+1);
				}
				else if (rightPos[2].equals("m")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = map[posY][posX+1]+" "+posY+" "+(posX+1)+" "+(index+1);
				}
				else if (rightPos[2].equals("n")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = map[posY][posX+1]+" "+posY+" "+(posX+1)+" "+(index+1);
				}
				else if (rightPos[2].equals("h")) {
					result = "h";
				}
				else if(rightPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
					//change label_noneplayer_list
					int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
					String str[] = characterPosLbl.getText().trim().split(" ");
					str[1] = Integer.parseInt(str[1]) +1+"";
					String back_text = str[0]+" "+str[1]+" "+str[2]+" "+str[3]+" "+str[4];
					label_noneplayer_list.get(index_noneplayer).setText(back_text);
					//end
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index+1);
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index+1), characterPosLbl);
					result = posY + " " + (posX+1) + " " + (index+1) + " " + indexInList;
					friendPosList.remove(result);
					friendPosList.add(result);
					map[posY][posX] = "f";
					map[posY][posX+1] = "n"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					logging(mapOnPage.getContainFriends().get(indexInList)+" moved");
					result = "f "+result;
				}
			}
		}
		else if(moveType.equals("Loot_Chest"))
		{
			ArrayList<String> moveAble =new ArrayList<String>();
			String nextMove = new String();
			int lastDis = 0;
			String right[] = map[posY][posX+1].trim().split(" ");
			String left[] = map[posY][posX-1].trim().split(" ");
			String up[] = map[posY-1][posX].trim().split(" ");
			String down[] = map[posY+1][posX].trim().split(" ");
			if(!(right[0].equals("x")||right[0].equals("n")||right[0].equals("I")))
			{
				moveAble.add(right[0]+" "+posY+" "+(posX+1));
			}
			if(!(left[0].equals("x")||left[0].equals("n")||left[0].equals("I")))
			{
				moveAble.add(left[0]+" "+posY+" "+(posX-1));
			}
			if(!(up[0].equals("x")||up[0].equals("n")||up[0].equals("I")))
			{
				moveAble.add(up[0]+" "+(posY-1)+" "+posX);
			}
			if(!(down[0].equals("x")||down[0].equals("n")||down[0].equals("I")))
			{
				moveAble.add(down[0]+" "+(posY+1)+" "+posX);
			}
			for(JLabel label_chest : mapElementsLbls)
			{
				String str[] = label_chest.getText().trim().split(" ");
				if(str[0].equals("i"))
				{
					posYC = Integer.parseInt(str[1]);
					posXC = Integer.parseInt(str[2]);
					break;
				}
			}
			for (String str_buffer : moveAble) 
			{
				String str[] =str_buffer.trim().split(" ");
				if(moveAble.indexOf(str_buffer) == 0)
				{
					lastDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					nextMove = str_buffer;
				}
				else
				{
					int curDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					if(lastDis > curDis)
					{
						lastDis = curDis;
						nextMove = str_buffer;
					}
				}
			}
			String nextInfo[] = nextMove.trim().split(" ");
			int nextIndex = 0;
			if(Integer.parseInt(nextInfo[2]) == posX)
			{
				if(Integer.parseInt(nextInfo[1])>posY)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+map[0].length);
					nextIndex = index+map[0].length;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-map[0].length);
					nextIndex = index-map[0].length;
				}
			}
			if(Integer.parseInt(nextInfo[1]) == posY)
			{
				if(Integer.parseInt(nextInfo[2])>posX)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+1);
					nextIndex = index+1;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-1);
					nextIndex = index-1;
				}
			}
			if(nextInfo[0].equals("f"))	
			{
				JLabel characterPosLbl = mapElementsLbls.get(index);
				//change label_noneplayer_list
				int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
				String str[] = characterPosLbl.getText().trim().split(" ");
				String back_text = nextInfo[1]+" "+nextInfo[2]+" "+str[2]+" "+str[3]+" "+str[4];
				label_noneplayer_list.get(index_noneplayer).setText(back_text);
				//end
				Rectangle posB = characterPosLbl.getBounds();
				JLabel characterDesLbl = mapElementsLbls.get(nextIndex);
				Rectangle desB = characterDesLbl.getBounds();
				characterPosLbl.setBounds(desB);
				characterDesLbl.setBounds(posB);
				mapElementsLbls.set(index, characterDesLbl);
				mapElementsLbls.set(nextIndex, characterPosLbl);
				String posIndex = nextInfo[1]+" "+nextInfo[2] + " " + nextIndex + " " + indexInList;
				enemyPosList.remove(posIndex);
				enemyPosList.add(posIndex);
				map[posY][posX] = "f";
				map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
				logging(mapOnPage.getContainEnemies().get(indexInList)+" moved");
			}
		}
		else if(moveType.equals("Kill_Enemy"))
		{
			ArrayList<String> moveAble =new ArrayList<String>();
			String nextMove = new String();
			int lastDis = 0;
			String right[] = map[posY][posX+1].trim().split(" ");
			String left[] = map[posY][posX-1].trim().split(" ");
			String up[] = map[posY-1][posX].trim().split(" ");
			String down[] = map[posY+1][posX].trim().split(" ");
			if(!(right[0].equals("x")||right[0].equals("n")||right[0].equals("I")))
			{
				moveAble.add(right[0]+" "+posY+" "+(posX+1));
			}
			if(!(left[0].equals("x")||left[0].equals("n")||left[0].equals("I")))
			{
				moveAble.add(left[0]+" "+posY+" "+(posX-1));
			}
			if(!(up[0].equals("x")||up[0].equals("n")||up[0].equals("I")))
			{
				moveAble.add(up[0]+" "+(posY-1)+" "+posX);
			}
			if(!(down[0].equals("x")||down[0].equals("n")||down[0].equals("I")))
			{
				moveAble.add(down[0]+" "+(posY+1)+" "+posX);
			}
			for(JLabel label_chest : mapElementsLbls)
			{
				String str[] = label_chest.getText().trim().split(" ");
				if(str[0].equals("m"))
				{
					posYC = Integer.parseInt(str[1]);
					posXC = Integer.parseInt(str[2]);
					break;
				}
			}
			for (String str_buffer : moveAble) 
			{
				String str[] =str_buffer.trim().split(" ");
				if(moveAble.indexOf(str_buffer) == 0)
				{
					lastDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					nextMove = str_buffer;
				}
				else
				{
					int curDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					if(lastDis > curDis)
					{
						lastDis = curDis;
						nextMove = str_buffer;
					}
				}
			}
			String nextInfo[] = nextMove.trim().split(" ");
			int nextIndex = 0;
			if(Integer.parseInt(nextInfo[2]) == posX)
			{
				if(Integer.parseInt(nextInfo[1])>posY)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+map[0].length);
					nextIndex = index+map[0].length;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-map[0].length);
					nextIndex = index-map[0].length;
				}
			}
			if(Integer.parseInt(nextInfo[1]) == posY)
			{
				if(Integer.parseInt(nextInfo[2])>posX)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+1);
					nextIndex = index+1;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-1);
					nextIndex = index-1;
				}
			}
			if(nextInfo[0].equals("f"))	
			{
				JLabel characterPosLbl = mapElementsLbls.get(index);
				//change label_noneplayer_list
				int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
				String str[] = characterPosLbl.getText().trim().split(" ");
				String back_text = nextInfo[1]+" "+nextInfo[2]+" "+str[2]+" "+str[3]+" "+str[4];
				label_noneplayer_list.get(index_noneplayer).setText(back_text);
				//end
				Rectangle posB = characterPosLbl.getBounds();
				JLabel characterDesLbl = mapElementsLbls.get(nextIndex);
				Rectangle desB = characterDesLbl.getBounds();
				characterPosLbl.setBounds(desB);
				characterDesLbl.setBounds(posB);
				mapElementsLbls.set(index, characterDesLbl);
				mapElementsLbls.set(nextIndex, characterPosLbl);
				String posIndex = nextInfo[1]+" "+nextInfo[2] + " " + nextIndex + " " + indexInList;
				enemyPosList.remove(posIndex);
				enemyPosList.add(posIndex);
				map[posY][posX] = "f";
				map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
				logging(mapOnPage.getContainEnemies().get(indexInList)+" moved");
			}
		}
		else if(moveType.equals("Reach_Exit"))
		{
			ArrayList<String> moveAble =new ArrayList<String>();
			String nextMove = new String();
			int lastDis = 0;
			String right[] = map[posY][posX+1].trim().split(" ");
			String left[] = map[posY][posX-1].trim().split(" ");
			String up[] = map[posY-1][posX].trim().split(" ");
			String down[] = map[posY+1][posX].trim().split(" ");
			if(!(right[0].equals("x")||right[0].equals("n")||right[0].equals("I")))
			{
				moveAble.add(right[0]+" "+posY+" "+(posX+1));
			}
			if(!(left[0].equals("x")||left[0].equals("n")||left[0].equals("I")))
			{
				moveAble.add(left[0]+" "+posY+" "+(posX-1));
			}
			if(!(up[0].equals("x")||up[0].equals("n")||up[0].equals("I")))
			{
				moveAble.add(up[0]+" "+(posY-1)+" "+posX);
			}
			if(!(down[0].equals("x")||down[0].equals("n")||down[0].equals("I")))
			{
				moveAble.add(down[0]+" "+(posY+1)+" "+posX);
			}
			for(JLabel label_chest : mapElementsLbls)
			{
				String str[] = label_chest.getText().trim().split(" ");
				if(str[0].equals("O"))
				{
					posYC = Integer.parseInt(str[1]);
					posXC = Integer.parseInt(str[2]);
					break;
				}
			}
			for (String str_buffer : moveAble) 
			{
				String str[] =str_buffer.trim().split(" ");
				if(moveAble.indexOf(str_buffer) == 0)
				{
					lastDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					nextMove = str_buffer;
				}
				else
				{
					int curDis = Math.abs(Integer.parseInt(str[1])-posYC)+Math.abs(Integer.parseInt(str[2])-posXC);
					if(lastDis > curDis)
					{
						lastDis = curDis;
						nextMove = str_buffer;
					}
				}
			}
			String nextInfo[] = nextMove.trim().split(" ");
			int nextIndex = 0;
			if(Integer.parseInt(nextInfo[2]) == posX)
			{
				if(Integer.parseInt(nextInfo[1])>posY)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+map[0].length);
					nextIndex = index+map[0].length;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-map[0].length);
					nextIndex = index-map[0].length;
				}
			}
			if(Integer.parseInt(nextInfo[1]) == posY)
			{
				if(Integer.parseInt(nextInfo[2])>posX)
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index+1);
					nextIndex = index+1;
				}
				else
				{
					result =map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])]+" "+nextInfo[1]
							+" "+nextInfo[2]+" "+(index-1);
					nextIndex = index-1;
				}
			}
			if(nextInfo[0].equals("f"))	
			{
				JLabel characterPosLbl = mapElementsLbls.get(index);
				//change label_noneplayer_list
				int index_noneplayer = label_noneplayer_list.indexOf(characterPosLbl);
				String str[] = characterPosLbl.getText().trim().split(" ");
				String back_text = nextInfo[1]+" "+nextInfo[2]+" "+str[2]+" "+str[3]+" "+str[4];
				label_noneplayer_list.get(index_noneplayer).setText(back_text);
				//end
				Rectangle posB = characterPosLbl.getBounds();
				JLabel characterDesLbl = mapElementsLbls.get(nextIndex);
				Rectangle desB = characterDesLbl.getBounds();
				characterPosLbl.setBounds(desB);
				characterDesLbl.setBounds(posB);
				mapElementsLbls.set(index, characterDesLbl);
				mapElementsLbls.set(nextIndex, characterPosLbl);
				String posIndex = nextInfo[1]+" "+nextInfo[2] + " " + nextIndex + " " + indexInList;
				enemyPosList.remove(posIndex);
				enemyPosList.add(posIndex);
				map[posY][posX] = "f";
				map[Integer.parseInt(nextInfo[1])][Integer.parseInt(nextInfo[2])] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
				logging(mapOnPage.getContainEnemies().get(indexInList)+" moved");
			}
		}
		return result;
	}
	/**
	 * character loot chest
	 */
	public Boolean characterLooting(){
		return false;
	}
	/**
	 * character attack another
	 */
	public Boolean characterAttack(){
		return false;
	}
	/**
	 * show highlight
	 */
	public ArrayList<String> showHighlight(WarGameCharacterModel characterForStrategy, String identity,String pos){
				for (String highLightStr : highLightList) {
					String str[] = highLightStr.trim().split(" ");
					switch (str[0]) {
					case "x":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(wall);
						break;
					case "i":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(chest);
						break;
					case "h":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(hero);
						break;
					case "m":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(monster);
						break;
					case "I":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(door);
						break;
					case "O":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(door);
						break;
					case "f":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(floor);
						break;
					case "n":
						mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(friend);
						break;
					}
				}
						
		highLightList.clear();
		int attackRange = 0;
		int posX = 0;
		int posY = 0;
		int index = 0;
		attackRange = characterForStrategy.getRange();
		
		if (identity.equals("Player")) {
			String str[] = pos.split(" ");
			posY = Integer.parseInt(str[0]);
			posX = Integer.parseInt(str[1]);
			index = Integer.parseInt(str[2]);		
		}
		else if(identity.equals("m")){
			String str[] = pos.trim().split(" ");
			posY = Integer.parseInt(str[0]);
			posX = Integer.parseInt(str[1]);
			index = Integer.parseInt(str[2]);
		}
		else if(identity.equals("n")){
			String str[] = pos.trim().split(" ");
			posY = Integer.parseInt(str[0]);
			posX = Integer.parseInt(str[1]);
			index = Integer.parseInt(str[2]);
		}
		
		//get xy list
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				int result = Math.abs(posY-i)+Math.abs(posX-j);
				if(result<(attackRange+1))
				{
						int indexTarget = index-(posY-i)*(map[0].length)-(posX-j);
						String str[] = map[i][j].trim().split(" ");
						String hlMember = str[0]+" "+i+" "+j+" "+indexTarget;
						highLightList.add(hlMember);
					
				}
			}
		}
		for (String highLightStr : highLightList) {
			String str[] = highLightStr.trim().split(" ");
			switch (str[0]) {
			case "x":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(wall);
				break;
			case "i":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(chest);
				break;
			case "h":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(heroHighlight);
				break;
			case "m":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(monsterHighlight);
				break;
			case "I":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(door);
				break;
			case "O":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(door);
				break;
			case "f":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(floorHighlight);
				break;
			case "n":
				mapElementsLbls.get(Integer.parseInt(str[3])).setIcon(friendHighlight);
				break;
			}
		}
		return highLightList;
	}
	
	public void getTargetAndAttack()
	{
		if (fight_times == 0) 
		{
			if (highLightList.contains("h "+heroPos))
			{
				String hitPoints[] = characterModel.getScore(7);
				if (Integer.parseInt(hitPoints[1])>0) 
				{
					WarGameStartModel startModel = new WarGameStartModel();
					//WarGameCharacterModel character = mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1]));
					characterModel = startModel.fightChange(characterForStrategy, characterModel);
					//mapOnPage.getContainEnemies().set(Integer.parseInt(result[2]), characterTarget);
					fight_times = 1;
				}
				else
				{
					logging("Player is dead! Can't be attacked!");
				}
			}
			for (String hlTarget : highLightList)
			{
				String strHL[] = hlTarget.trim().split(" ");
				String strIndex[] = map[Integer.parseInt(strHL[1])][Integer.parseInt(strHL[2])].trim().split(" ");
				if(strHL[0].equals("m"))
				{
					String currentPos[] = mapElementsLbls.get(Integer.parseInt(strHL[3])).getText().trim().split(" ");
					if(!(strHL[1].equals(currentPos[0])&&strHL[2].equals(currentPos[1])))
					{
						WarGameStartModel startModel = new WarGameStartModel();
						WarGameCharacterModel characterTarget = mapOnPage.getContainEnemies().get(Integer.parseInt(strIndex[2]));
						if(Integer.parseInt(characterTarget.getScore(7)[1])>0)
						{
							characterTarget = startModel.fightChange(characterForStrategy, characterTarget);
							mapOnPage.getContainEnemies().set(Integer.parseInt(strIndex[2]), characterTarget);
							fight_times = 1;
						}
						else
						{
							logging("Character"+characterTarget.getCharacterID()+" is dead! Can't be attacked!");
						}
					}
					
				}
				if(strHL[0].equals("n"))
				{
					WarGameStartModel startModel = new WarGameStartModel();
					WarGameCharacterModel characterTarget = mapOnPage.getContainFriends().get(Integer.parseInt(strIndex[2]));
					if(Integer.parseInt(characterTarget.getScore(7)[1])>0)
					{
						characterTarget = startModel.fightChange(characterForStrategy, characterTarget);
						mapOnPage.getContainFriends().set(Integer.parseInt(strIndex[2]), characterTarget);
						int indexInOrder = orderList.indexOf("n "+strIndex[2]);
						friendPosList.remove(strHL[1]+" "+strHL[2]+" "+strHL[3]+" "+strIndex[2]);
						enemyPosList.add(strHL[1]+" "+strHL[2]+" "+strHL[3]+" "+strIndex[2]);
						map[Integer.parseInt(strHL[1])][Integer.parseInt(strHL[2])] = "m"+" "+characterTarget.getCharacterID()+" "+strIndex[2];
						mapElementsLbls.get(Integer.parseInt(strHL[3])).setIcon(monster);
						mapElementsLbls.get(Integer.parseInt(strHL[3])).setText(strHL[1]+" "+strHL[2]+" "+"m alive"+" "+strIndex[2]);
						frame.repaint();
						orderList.set(indexInOrder, "m "+mapOnPage.getContainEnemies().size());
						mapOnPage.getContainEnemies().add(characterTarget);
						//mapOnPage.getContainEnemies().set(Integer.parseInt(strIndex[2]), characterTarget);
						fight_times = 1;
					}
					else
					{
						logging("Character"+characterTarget.getCharacterID()+" is dead! Can't be attacked!");
					}
				}
			}
		
		}
		else
		{
			logging("Character"+characterForStrategy.getCharacterID()+" had attacked!");
		}
	}
}
