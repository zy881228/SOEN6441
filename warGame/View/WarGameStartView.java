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
	WarGameCharacterModel characterForStrategy = new WarGameCharacterModel();
	ArrayList<String> actionList = new ArrayList<String>();
	Map<String, ArrayList<String>> characterActionsByMap;
	JFrame frame;
	/**
     * Update the start game information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */
	
	@Override
	public void update(final Observable o, Object arg) {
		ImageIcon wall = new ImageIcon("src/image/Map/wall.jpg");
		ImageIcon door = new ImageIcon("src/image/Map/door.jpg");
		ImageIcon chest = new ImageIcon("src/image/Map/chest.jpg");
		ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
		ImageIcon monster = new ImageIcon("src/image/Map/monster.jpg");
		ImageIcon hero = new ImageIcon("src/image/Map/hero.jpg");
		
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
					System.out.println("Player  roll "+randomNum);
					System.out.println("Player's dexterity modifier is "+characterModel.getScore(13)[1]);
					randomNum += Integer.parseInt(characterModel.getScore(13)[1]);
				}else if (orderList.get(i).startsWith("m")) {
					System.out.println("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+" roll "+randomNum);
					System.out.println("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s dexterity modifier is "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
					randomNum += Integer.parseInt(mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
				}else if (orderList.get(i).startsWith("n")) {
					System.out.println("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+" roll "+randomNum);
					System.out.println("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s dexterity modifier is "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1])).getScore(13)[1]);
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
			System.out.println("The order to take turn:");
			for (String string : orderList) {
				if (string.equals("Player")) {
					System.out.println("Player");
				}else if (string.startsWith("m")) {
					System.out.println("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1])));
				}else if (string.startsWith("n")) {
					System.out.println("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(string.split(" ")[1])));
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
					if(characterForStrategy.equals(characterModel))
					{
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
						}else if(leftPos[2].equals("m")){
							String str[] = map[posY][posX-1].trim().split(" ");
							nonePlayerModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
							if(leftPos[3].equals("alive"))
							{
								JOptionPane.showMessageDialog(null, "Monster Dead!");
								mapElementsLbls.get(index-1).setText(posY+" "+(posX-1)+" "+"m dead");
							}
							if(leftPos[3].equals("dead"))
							{
								getAllItem();
								mapOnPage.getContainEnemies().set(Integer.parseInt(str[2]), nonePlayerModel);
							}
						}else if(leftPos[2].equals("i")){
							String str[] = map[posY][posX-1].trim().split(" ");
							itemModel = mapOnPage.getContainItems().get(Integer.parseInt(str[2]));
							String itemType = itemModel.getItemType();
							String enchanType = itemModel.getEnchanType();
							String enchanNum = itemModel.getEnchanNumber();
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemType+" "+enchanType+" "+enchanNum;
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
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					if(characterForStrategy.equals(characterModel))
					{
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
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemType+" "+enchanType+" "+enchanNum;
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
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
					if(characterForStrategy.equals(characterModel))
					{
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
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemType+" "+enchanType+" "+enchanNum;
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
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					if(characterForStrategy.equals(characterModel))
					{
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
							WarGameStartModel startModel = new WarGameStartModel();
							Boolean result = startModel.checkBackpack(backpack);
							if(result == true)
							{
								for(int i =0;i<10;i++)
								{
									if((backpack[i] == null)||(backpack[i].equals("null")))
									{
										backpack[i] = itemType+" "+enchanType+" "+enchanNum;
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
				}
				else if(e.getKeyCode() == KeyEvent.VK_SPACE){
					System.out.println("Round "+round);
					for (String string : orderList) {
						if (string.equals("Player")) {
							System.out.println("Player turn");
							characterForStrategy = characterModel;
							((WarGameStartModel) o).setStrategy(new HumanPlayer(), characterForStrategy);
							actionList = ((WarGameStartModel) o).turn();
							break;
//							characterActionsByMap.put(string, actionList);
//							takeAction(characterActionsByMap);
//							if (actionList.contains("Freezing")) {
//								if (specialTurn<1) {
//									characterForStrategy = characterModel;
//									actionList.remove("Freezing");
//									break;
//								}else{
//									System.out.println("Play is freezing for "+specialTurn+" turn(s)");
//									break;
//								}
//							}else{
//								characterForStrategy = characterModel;
//								break;
//							}
							
						}else if (string.startsWith("m")) {
							System.out.println("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(string.split(" ")[1]))+"'s turn");
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
//							frame.repaint();
						}else if (string.startsWith("n")) {
							System.out.println("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(string.split(" ")[1]))+"'s turn");
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
//							frame.repaint();
						}
					}//for	
				}
				else if(e.getKeyCode() == KeyEvent.VK_O){
					for (int i = orderList.indexOf("Player")+1; i < orderList.size(); i++) {
						if (orderList.get(i).startsWith("m")) {
							System.out.println("Aggressive "+mapOnPage.getContainEnemies().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s turn");
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
//							frame.repaint();
						}else if (orderList.get(i).startsWith("n")) {
							System.out.println("Friendly "+mapOnPage.getContainFriends().get(Integer.parseInt(orderList.get(i).split(" ")[1]))+"'s turn");
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
//							frame.repaint();
						}
					}
					round++;
					JOptionPane.showMessageDialog(null, "Round End!");
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
					/*mapElement.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e){
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								
							}
						}
					});*/
					break;
				case "m":
					mapElement = new JLabel(i+" "+j+" "+"m alive");
					mapElement.setIcon(monster);
					mapElementsLbls.add(mapElement);
					enemyPos = i+" "+j+" "+(mapElementsLbls.size()-1+" "+str[2]);
					enemyPosList.add(enemyPos);
					/*mapElement.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e){
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								String str[] = map[i_buffer][j_buffer].trim().split(" ");
								WarGameCharacterModel characterModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
								//createCharacterView(characterModel);
								setPanel(characterModel);
							}
						}
					});*/
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
					mapElement = new JLabel(i+" "+j+" "+"n alive");
					mapElement.setIcon(hero);
					mapElementsLbls.add(mapElement);
					friendPos = i+" "+j+" "+(mapElementsLbls.size()-1+" "+str[2]);
					friendPosList.add(friendPos);
					/*mapElement.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e){
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								String str[] = map[i_buffer][j_buffer].trim().split(" ");
								WarGameCharacterModel characterModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
								//createCharacterView(characterModel);
								setPanel(characterModel);
							}
						}
					});*/
					break;
				}
			}
		}
		
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
						}
					}
				});
			}
			else if(lblArray[2].equals("m"))
			{
				posY = Integer.parseInt(lblArray[0]);
				posX = Integer.parseInt(lblArray[1]);
				final int i_buffer = posY;
				final int j_buffer = posX;
				label_noneplayer = new JLabel(posY+" "+posX+" "+"m alive");
				label_noneplayer.setBounds(posX*30, posY*30, 30, 30);
				label_noneplayer.setIcon(monster);
				mapPanel.add(label_noneplayer);
				mapElementsLbls.set(mapElementsLbls.indexOf(lbl), label_noneplayer);
				label_noneplayer.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							String str[] = map[i_buffer][j_buffer].trim().split(" ");
							WarGameCharacterModel characterModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
							//createCharacterView(characterModel);
							setPanel(characterModel);
						}
					}
				});
			}
			else if(lblArray[2].equals("n"))
			{
				posY = Integer.parseInt(lblArray[0]);
				posX = Integer.parseInt(lblArray[1]);
				final int i_buffer = posY;
				final int j_buffer = posX;
				label_noneplayer = new JLabel(posY+" "+posX+" "+"n alive");
				label_noneplayer.setBounds(posX*30, posY*30, 30, 30);
				label_noneplayer.setIcon(hero);
				mapPanel.add(label_noneplayer);
				mapElementsLbls.set(mapElementsLbls.indexOf(lbl), label_noneplayer);
				label_noneplayer.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e){
						if(e.getButton() == MouseEvent.BUTTON1)
						{
							String str[] = map[i_buffer][j_buffer].trim().split(" ");
							WarGameCharacterModel characterModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
							//createCharacterView(characterModel);
							setPanel(characterModel);
						}
					}
				});
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
			label_equip[i].addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
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
			if(backpack[i].equals("null"))
			{
				label_backpack[i].setBackground(Color.black);
			}
			else
			{
				String prefix[] = backpack[i].trim().split(" ");
				String itemName = prefix[0]+prefix[1];
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_backpack[i].setIcon(img_item);
			}
			label_backpack[i].addMouseListener(new MouseAdapter(){
				@Override
				public void mousePressed(MouseEvent e){
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
		}//backpack for
		//player panel end
		
		//logging window panel
		JPanel logViewPanel = new JPanel();
		logViewPanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		logViewPanel.setBounds(750, 480, 520, 200);
		frame.getContentPane().add(logViewPanel);
		logViewPanel.setLayout(null);
		logViewPanel.setBackground(Color.gray);
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
     * create the character view frame
     * @param characterModel
     */
	/*
	public void createCharacterView(WarGameCharacterModel characterModel){
		JFrame frame = new JFrame("Character Info");
		frame.setBounds(300, 0, 520, 700);
		frame.setVisible(true);
		frame.setLayout(null);
		JLabel label_pic = new JLabel();
		JLabel label_scores[] = new JLabel[12];
		final JLabel label_equip[] = new JLabel[7];
		final JLabel label_backpack[] = new JLabel[10];
		JLabel label_showScore[] = new JLabel[12];
		final String backpack[] = characterModel.getBackpack();
		final String equip[] = characterModel.getEquip();
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
	*/

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
		String equip[] = new String[7];
		String backpack[] = new String[10];
		equip = characterModel.getEquip();
		backpack = characterModel.getBackpack();
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
						System.out.println("Freezing, Cannot move");
						break;
					}
					if(action.equals("Frightening")){
						//反向移动
						System.out.println("Frightening, Escape");
						for (int i = 0; i < 3; i++) {
							for (String strPos : enemyPosList) {
								if (strPos.split(" ")[3].equals(indexInList)) {
									enemyPos = strPos;
								}
							}
							characterMovement(enemyPos, "Escape");
						}
					}
					if(action.equals("Move_Aggressively")){
						//目标移动
						System.out.println("Move aggressively");
						String result[];
						for (int i = 0; i < 3; i++) {
							for (String strPos : enemyPosList) {
								if (strPos.split(" ")[3].equals(indexInList)) {
									enemyPos = strPos;
								}
							}
							result = characterMovement(enemyPos, "Move_Aggressively").split(" ");
							if (result[0].equals("i")) {
								//loot a chest
								System.out.println("Reach item "+ mapOnPage.getContainItems().get(Integer.parseInt(result[1])));
							}else if(result[0].equals("m")){
								//attack
								System.out.println("Reach monster "+ mapOnPage.getContainEnemies().get(Integer.parseInt(result[1])));
							}else if(result[0].equals("n")){
								//attack
								System.out.println("Reach friend "+ mapOnPage.getContainFriends().get(Integer.parseInt(result[1])));
							}
						}
					}
				}
			}else if (string.startsWith("n")) {
				ArrayList<String> actions = characterActionsByMap.get(string);
				for (String action : actions) {
					if(action.equals("Move_Randomly")){
						System.out.println("Move Randomly");
						String result[];
						for (int i = 0; i < 3; i++) {
							for (String strPos : friendPosList) {
								if (strPos.split(" ")[3].equals(indexInList)) {
									friendPos = strPos;
								}
							}
							result = characterMovement(friendPos, "Move_Randomly").split(" ");
							if (result[0].equals("i")) {
								//loot chest
								System.out.println("Reach item "+ mapOnPage.getContainItems().get(Integer.parseInt(result[1])));
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
			if (posX<posXC) {
				if (rightPos[2].equals("i")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "i"+" "+str[2];
				}
				if (rightPos[2].equals("m")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "m"+" "+str[2];
				}
				if (rightPos[2].equals("n")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "n"+" "+str[2];
				}
				if (rightPos[2].equals("f")) {
					JLabel characterPosLbl = mapElementsLbls.get(index);
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index+1);
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index+1), characterPosLbl);
					result = posY + " " + (posX+1) + " " + (index+1) + " " + indexInList;
					enemyPosList.remove(result);
					enemyPosList.add(result);
					map[posY][posX] = "f";
					map[posY][posX+1] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
				}
			}else if(posX>posXC){
				if (leftPos[2].equals("i")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "i"+" "+str[2];
				}
				if (leftPos[2].equals("m")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "m"+" "+str[2];
				}
				if (leftPos[2].equals("n")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "n"+" "+str[2];
				}
				if (leftPos[2].equals("f")) {
					JLabel characterPosLbl = mapElementsLbls.get(index);
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index-1);
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index-1), characterPosLbl);
					result = posY + " " + (posX-1) + " " + (index-1) + " " + indexInList;
					enemyPosList.remove(result);
					enemyPosList.add(result);
					map[posY][posX] = "f";
					map[posY][posX-1] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
				}
			}else{
				if (posY<posYC) {
					if (downPos[2].equals("i")) {
						String str[] = map[posY+1][posX].trim().split(" ");
						result = "i"+" "+str[2];
					}
					if (downPos[2].equals("m")) {
						String str[] = map[posY+1][posX].trim().split(" ");
						result = "m"+" "+str[2];
					}
					if (downPos[2].equals("n")) {
						String str[] = map[posY+1][posX].trim().split(" ");
						result = "n"+" "+str[2];
					}
					if (downPos[2].equals("f")) {
						JLabel characterPosLbl = mapElementsLbls.get(index);
						Rectangle posB = characterPosLbl.getBounds();
						JLabel characterDesLbl = mapElementsLbls.get(index+(map[0].length));
						Rectangle desB = characterDesLbl.getBounds();
						characterPosLbl.setBounds(desB);
						characterDesLbl.setBounds(posB);
						mapElementsLbls.set(index, characterDesLbl);
						mapElementsLbls.set((index+(map[0]).length), characterPosLbl);
						result = (posY+1) + " " + posX + " " + (index+(map[0].length)) + " " + indexInList;
						enemyPosList.remove(result);
						enemyPosList.add(result);
						map[posY][posX] = "f";
						map[posY+1][posX] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
						System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
					}
				}else if(posY>posYC){
					if (upPos[2].equals("i")) {
						String str[] = map[posY-1][posX].trim().split(" ");
						result = "i"+" "+str[2];
					}
					if (upPos[2].equals("m")) {
						String str[] = map[posY-1][posX].trim().split(" ");
						result = "m"+" "+str[2];
					}
					if (upPos[2].equals("n")) {
						String str[] = map[posY-1][posX].trim().split(" ");
						result = "n"+" "+str[2];
					}
					if (upPos[2].equals("f")) {
						JLabel characterPosLbl = mapElementsLbls.get(index);
						Rectangle posB = characterPosLbl.getBounds();
						JLabel characterDesLbl = mapElementsLbls.get(index-(map[0].length));
						Rectangle desB = characterDesLbl.getBounds();
						characterPosLbl.setBounds(desB);
						characterDesLbl.setBounds(posB);
						mapElementsLbls.set(index, characterDesLbl);
						mapElementsLbls.set((index-(map[0]).length), characterPosLbl);
						result = (posY-1) + " " + posX + " " + (index-(map[0].length)) + " " + indexInList;
						enemyPosList.remove(result);
						enemyPosList.add(result);
						map[posY][posX] = "f";
						map[posY-1][posX] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
						System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
					}
				}
			}
		}else if(moveType.equals("Escape")){
			if (posX>posXC) {
				if (rightPos[2].equals("i")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "i"+" "+str[2];
				}
				if (rightPos[2].equals("m")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "m"+" "+str[2];
				}
				if (rightPos[2].equals("n")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "n"+" "+str[2];
				}
				if (rightPos[2].equals("f")) {
					JLabel characterPosLbl = mapElementsLbls.get(index);
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index+1);
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index+1), characterPosLbl);
					result = posY + " " + (posX+1) + " " + (index+1) + " " + indexInList;
					enemyPosList.remove(result);
					enemyPosList.add(result);
					map[posY][posX] = "f";
					map[posY][posX+1] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
				}
			}else if(posX<posXC){
				if (leftPos[2].equals("i")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "i"+" "+str[2];
				}
				if (leftPos[2].equals("m")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "m"+" "+str[2];
				}
				if (leftPos[2].equals("n")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "n"+" "+str[2];
				}
				if (leftPos[2].equals("f")) {
					JLabel characterPosLbl = mapElementsLbls.get(index);
					Rectangle posB = characterPosLbl.getBounds();
					JLabel characterDesLbl = mapElementsLbls.get(index-1);
					Rectangle desB = characterDesLbl.getBounds();
					characterPosLbl.setBounds(desB);
					characterDesLbl.setBounds(posB);
					mapElementsLbls.set(index, characterDesLbl);
					mapElementsLbls.set((index-1), characterPosLbl);
					result = posY + " " + (posX-1) + " " + (index-1) + " " + indexInList;
					enemyPosList.remove(result);
					enemyPosList.add(result);
					map[posY][posX] = "f";
					map[posY][posX-1] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
				}
			}else{
				if (posY>posYC) {
					if (downPos[2].equals("i")) {
						String str[] = map[posY+1][posX].trim().split(" ");
						result = "i"+" "+str[2];
					}
					if (downPos[2].equals("m")) {
						String str[] = map[posY+1][posX].trim().split(" ");
						result = "m"+" "+str[2];
					}
					if (downPos[2].equals("n")) {
						String str[] = map[posY+1][posX].trim().split(" ");
						result = "n"+" "+str[2];
					}
					if (downPos[2].equals("f")) {
						JLabel characterPosLbl = mapElementsLbls.get(index);
						Rectangle posB = characterPosLbl.getBounds();
						JLabel characterDesLbl = mapElementsLbls.get(index+(map[0].length));
						Rectangle desB = characterDesLbl.getBounds();
						characterPosLbl.setBounds(desB);
						characterDesLbl.setBounds(posB);
						mapElementsLbls.set(index, characterDesLbl);
						mapElementsLbls.set((index+(map[0]).length), characterPosLbl);
						result = (posY+1) + " " + posX + " " + (index+(map[0].length)) + " " + indexInList;
						enemyPosList.remove(result);
						enemyPosList.add(result);
						map[posY][posX] = "f";
						map[posY+1][posX] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
						System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
					}
				}else if(posY<posYC){
					if (upPos[2].equals("i")) {
						String str[] = map[posY-1][posX].trim().split(" ");
						result = "i"+" "+str[2];
					}
					if (upPos[2].equals("m")) {
						String str[] = map[posY-1][posX].trim().split(" ");
						result = "m"+" "+str[2];
					}
					if (upPos[2].equals("n")) {
						String str[] = map[posY-1][posX].trim().split(" ");
						result = "n"+" "+str[2];
					}
					if (upPos[2].equals("f")) {
						JLabel characterPosLbl = mapElementsLbls.get(index);
						Rectangle posB = characterPosLbl.getBounds();
						JLabel characterDesLbl = mapElementsLbls.get(index-(map[0].length));
						Rectangle desB = characterDesLbl.getBounds();
						characterPosLbl.setBounds(desB);
						characterDesLbl.setBounds(posB);
						mapElementsLbls.set(index, characterDesLbl);
						mapElementsLbls.set((index-(map[0]).length), characterPosLbl);
						result = (posY-1) + " " + posX + " " + (index-(map[0].length)) + " " + indexInList;
						enemyPosList.remove(result);
						enemyPosList.add(result);
						map[posY][posX] = "f";
						map[posY-1][posX] = "m"+" "+ mapOnPage.getContainEnemies().get(indexInList).getCharacterID() + " " + indexInList;
						System.out.println(mapOnPage.getContainEnemies().get(indexInList)+" moved");
					}
				}
			}
		}else if(moveType.equals("Move_Randomly")){
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
					result = "i"+" "+str[2];
				}
				else if (upPos[2].equals("m")) {
					String str[] = map[posY-1][posX].trim().split(" ");
					result = "m"+" "+str[2];
				}
				else if (upPos[2].equals("n")) {
					String str[] = map[posY-1][posX].trim().split(" ");
					result = "n"+" "+str[2];
				}else if(upPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
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
					map[posY-1][posX] = "m"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainFriends().get(indexInList)+" moved");
				}
			}
			if (canMove.get(direction).equals("down")) {
				if (downPos[2].equals("i")) {
					String str[] = map[posY+1][posX].trim().split(" ");
					result = "i"+" "+str[2];
				}
				else if (downPos[2].equals("m")) {
					String str[] = map[posY+1][posX].trim().split(" ");
					result = "m"+" "+str[2];
				}
				else if (downPos[2].equals("n")) {
					String str[] = map[posY+1][posX].trim().split(" ");
					result = "n"+" "+str[2];
				}else if(downPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
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
					map[posY+1][posX] = "m"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainFriends().get(indexInList)+" moved");
				}
			}
			if (canMove.get(direction).equals("left")) {
				if (leftPos[2].equals("i")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "i"+" "+str[2];
				}
				else if (leftPos[2].equals("m")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "m"+" "+str[2];
				}
				else if (leftPos[2].equals("n")) {
					String str[] = map[posY][posX-1].trim().split(" ");
					result = "n"+" "+str[2];
				}else if(leftPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
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
					map[posY][posX-1] = "m"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainFriends().get(indexInList)+" moved");
				}
			}
			if (canMove.get(direction).equals("right")) {
				if (rightPos[2].equals("i")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "i"+" "+str[2];
				}
				else if (rightPos[2].equals("m")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "m"+" "+str[2];
				}
				else if (rightPos[2].equals("n")) {
					String str[] = map[posY][posX+1].trim().split(" ");
					result = "n"+" "+str[2];
				}else if(rightPos[2].equals("f")){
					JLabel characterPosLbl = mapElementsLbls.get(index);
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
					map[posY][posX+1] = "m"+" "+ mapOnPage.getContainFriends().get(indexInList).getCharacterID() + " " + indexInList;
					System.out.println(mapOnPage.getContainFriends().get(indexInList)+" moved");
				}
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
}
