package warGame.View;
import warGame.Model.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class WarGameMapLoadView extends JFrame implements Observer{
	private WarGameMapModel mapOnPage;
	private WarGameCharacterModel characterOnPage;
	private WarGameItemModel itemOnPage;
	private ArrayList<WarGameItemModel> itemsOnPage;
	private ArrayList<WarGameCharacterModel> friendsOnPage;
	private ArrayList<WarGameCharacterModel> enemiesOnPage;
	private ArrayList<JLabel> mapElementsLbls;
	private Map<String, WarGameMapModel> mapsByMap;
	private Map<String, WarGameItemModel> itemsByMap;
	private Map<String, WarGameCharacterModel> charactersByMap;
	private String element;
	private String map[][];
	private Boolean hasEntry;
	private Boolean hasExit;

	@Override
	public void update(Observable o, Object arg) {
		final ImageIcon wall = new ImageIcon("image/Map/wall.jpg");
		final ImageIcon door = new ImageIcon("image/Map/door.jpg");
		final ImageIcon chest = new ImageIcon("image/Map/chest.jpg");
		final ImageIcon floor = new ImageIcon("image/Map/floor.jpg");
		final ImageIcon monster = new ImageIcon("image/Map/monster.jpg");
		final ImageIcon hero = new ImageIcon("image/Map/hero.jpg");
		
		mapElementsLbls = new ArrayList<JLabel>();
		element = "x";
		try {
			hasEntry = true;
			hasExit = true;
			mapsByMap = WarGameMapModel.listAllMaps();
			itemsByMap = WarGameItemModel.listAllItems();
			charactersByMap = WarGameCharacterModel.listAllCharacters();
			itemsOnPage = new ArrayList<WarGameItemModel>();
			friendsOnPage = new ArrayList<WarGameCharacterModel>();
			enemiesOnPage = new ArrayList<WarGameCharacterModel>();
		} catch (UnsupportedEncodingException | FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		final JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("image/Map/hero.jpg"));
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle("Maps");
		frame.setSize(1280, 800);
		frame.getContentPane().setLayout(null);
		
		JPanel manipulatePanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.setBounds(0, 40, 500, 720);
		frame.getContentPane().add(manipulatePanel);
		manipulatePanel.setLayout(null);
		
		final JPanel displayPanel = new JPanel();
		displayPanel.setBounds(524, 0, 750, 750);
		frame.getContentPane().add(displayPanel);
		displayPanel.setLayout(null);
		
		JLabel lblMapName = new JLabel("Map name:");
		lblMapName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblMapName.setBounds(30, 70, 120, 30);
		manipulatePanel.add(lblMapName);
		
		final JComboBox<WarGameMapModel> mapToChooseCbox = new JComboBox<>();
		mapToChooseCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mapToChooseCbox.setBounds(150, 70, 120, 30);
		manipulatePanel.add(mapToChooseCbox);
		for (String mapID : mapsByMap.keySet()) {
			mapOnPage = mapsByMap.get(mapID);
			mapToChooseCbox.addItem(mapOnPage);
		}

		final JButton btnLoad = new JButton("Load");
		btnLoad.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnLoad.setBounds(310, 70, 120, 30);
		manipulatePanel.add(btnLoad);
		
		final JButton btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnSave.setBounds(180, 470, 120, 30);
		manipulatePanel.add(btnSave);
		
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					map = mapOnPage.getMap();
					itemsOnPage = mapOnPage.getContainItems();
					friendsOnPage = mapOnPage.getContainFriends();
					enemiesOnPage = mapOnPage.getContainEnemies();
					for (int i = 0; i < map.length; i++) {
						for (int j = 0; j < map[i].length; j++) {
							JLabel mapElement = new JLabel();
							if (map[i][j].equals("x")) {
								mapElement = new JLabel(i+" "+j+" "+"x");
								mapElement.setIcon(wall);
								mapElementsLbls.add(mapElement);
							}
							if (map[i][j].equals("f")) {
								mapElement = new JLabel(i+" "+j+" "+"f");
								mapElement.setIcon(floor);
								mapElementsLbls.add(mapElement);
							}
							if (map[i][j].startsWith("I")) {
								mapElement = new JLabel(i+" "+j+" "+"I");
								mapElement.setIcon(door);
								mapElementsLbls.add(mapElement);
							}
							if (map[i][j].startsWith("O")) {
								mapElement = new JLabel(i+" "+j+" "+"O");
								mapElement.setIcon(door);
								mapElementsLbls.add(mapElement);
							}
							if (map[i][j].startsWith("i")) {
								String itemArr[] = map[i][j].split(" ");
								System.out.println(map[i][j]);
								mapElement = new JLabel(i+" "+j+" "+"i"+" "+itemArr[1]);
								mapElement.setIcon(chest);
								mapElementsLbls.add(mapElement);
							}
							if (map[i][j].startsWith("m")) {
								String enemyArr[] = map[i][j].split(" ");
								System.out.println(map[i][j]);
								mapElement = new JLabel(i+" "+j+" "+"i"+" "+enemyArr[1]);
								mapElement.setIcon(monster);
								mapElementsLbls.add(mapElement);
							}
							if (map[i][j].startsWith("n")) {
								String friendArr[] = map[i][j].split(" ");
								System.out.println(map[i][j]);
								mapElement = new JLabel(i+" "+j+" "+"i"+" "+friendArr[1]);
								mapElement.setIcon(hero);
								mapElementsLbls.add(mapElement);
							}
						}
					}
					for (final JLabel lbl : mapElementsLbls) {
						final int posX;
						final int posY;
						String[] lblArray = lbl.getText().split(" ");
						posY = Integer.parseInt(lblArray[0]);
						posX = Integer.parseInt(lblArray[1]);
						lbl.setBounds(posX*30, posY*30, 30, 30);
						displayPanel.add(lbl);
						lbl.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								if (e.getButton()== MouseEvent.BUTTON1) {
									switch (element) {
									case "x":
										if (map[posY][posX].startsWith("i") || 
												map[posY][posX].startsWith("m") ||
												map[posY][posX].startsWith("n")) {
											JOptionPane.showMessageDialog(null, "Remove current element first");
										}else{
											if (map[posY][posX].startsWith("I")) {
												hasEntry = false;
												lbl.setIcon(wall);
												map[posY][posX] = "x";
											}else if (map[posY][posX].startsWith("O")) {
												hasExit = false;
												lbl.setIcon(wall);
												map[posY][posX] = "x";
											}else{
												lbl.setIcon(wall);
												map[posY][posX] = "x";
											}
										}
										break;
									case "i":
										if (map[posY][posX].startsWith("i") || 
												map[posY][posX].startsWith("m") ||
												map[posY][posX].startsWith("n")) {
											JOptionPane.showMessageDialog(null, "Remove current element first");
										}else if (posY==0 || posX==0 || posY==map.length-1 || posX==map[0].length-1) {
											JOptionPane.showMessageDialog(null, "This element cannot be on edge");
										}else if ((map[posY-1][posX].equals("x")) &&
												(map[posY+1][posX].equals("x")) &&
												(map[posY][posX-1].equals("x")) &&
												(map[posY][posX+1].equals("x"))) {
											JOptionPane.showMessageDialog(null, "This element cannot be reached");
										}else{
											if (itemOnPage != null) {
												itemsOnPage.add(itemOnPage);
												lbl.setIcon(chest);
												map[posY][posX] = "i"+" "+itemOnPage.getItemID()+" "+itemsOnPage.indexOf(itemOnPage);
												itemOnPage = null;
											}else{
												JOptionPane.showMessageDialog(null, "Choose an item to add");
											}
										}
										break;
									case "m":
										if (map[posY][posX].startsWith("i") || 
												map[posY][posX].startsWith("m") ||
												map[posY][posX].startsWith("n")) {
											JOptionPane.showMessageDialog(null, "Remove current element first");
										}else if (posY==0 || posX==0 || posY==map.length-1 || posX==map[0].length-1) {
											JOptionPane.showMessageDialog(null, "This element cannot be on edge");
										}else if ((map[posY-1][posX].equals("x")) &&
												(map[posY+1][posX].equals("x")) &&
												(map[posY][posX-1].equals("x")) &&
												(map[posY][posX+1].equals("x"))) {
											JOptionPane.showMessageDialog(null, "This element cannot be reached");
										}else{
											if (characterOnPage != null) {
												enemiesOnPage.add(characterOnPage);
												lbl.setIcon(monster);
												map[posY][posX] = "m"+" "+characterOnPage.getCharacterID()+" "+enemiesOnPage.indexOf(characterOnPage);
												characterOnPage = null;
											}else{
												JOptionPane.showMessageDialog(null, "Choose an enemy to add");
											}
										}
										break;
									case "n":
										if (map[posY][posX].startsWith("i") || 
												map[posY][posX].startsWith("m") ||
												map[posY][posX].startsWith("n")) {
											JOptionPane.showMessageDialog(null, "Remove current element first");
										}else if (posY==0 || posX==0 || posY==map.length-1 || posX==map[0].length-1) {
											JOptionPane.showMessageDialog(null, "This element cannot be on edge");
										}else if ((map[posY-1][posX].equals("x")) &&
												(map[posY+1][posX].equals("x")) &&
												(map[posY][posX-1].equals("x")) &&
												(map[posY][posX+1].equals("x"))) {
											JOptionPane.showMessageDialog(null, "This element cannot be reached");
										}else{
											if (characterOnPage != null) {
												friendsOnPage.add(characterOnPage);
												lbl.setIcon(hero);
												map[posY][posX] = "n"+" "+characterOnPage.getCharacterID()+" "+friendsOnPage.indexOf(characterOnPage);
												characterOnPage = null;
											}else{
												JOptionPane.showMessageDialog(null, "Choose a friend to add");
											}
										}
										break;
									case "I":
										if (hasEntry==false) {
											if ((posY==0 && posX==0)||
													(posY==map.length-1 && posX==0)||
													(posY==0 && posX==map[0].length-1)||
													(posY==map.length-1 && posX==map[0].length-1)) {
												JOptionPane.showMessageDialog(null, "Doors cannot be in corner");
											}else{
												lbl.setIcon(door);
												map[posY][posX] = "I"+" "+mapOnPage.getMapID();
												hasEntry = true;
											}
										}else{
											JOptionPane.showMessageDialog(null, "Can only have one entry");
										}								
										break;
									case "O":
										if (hasExit==false) {
											if ((posY==0 && posX==0)||
													(posY==map.length-1 && posX==0)||
													(posY==0 && posX==map[0].length-1)||
													(posY==map.length-1 && posX==map[0].length-1)) {
												JOptionPane.showMessageDialog(null, "Doors cannot be in corner");
											}else{
												lbl.setIcon(door);
												map[posY][posX] = "O"+" "+mapOnPage.getMapID();
												hasExit = true;
											}
										}else{
											JOptionPane.showMessageDialog(null, "Can only have one exit");
										}
										break;
									case "":
										if (map[posY][posX].startsWith("I")) {
											if (posY==0 || posX==0 || posY==map.length-1 || posX==map[0].length-1) {
												map[posY][posX] = "x";
												hasEntry = false;
												lbl.setIcon(wall);
											}else{
												hasEntry = false;
												lbl.setIcon(floor);
												map[posY][posX] = "f";
											}
										}
										if (map[posY][posX].startsWith("O")) {
											if (posY==0 || posX==0 || posY==map.length-1 || posX==map[0].length-1) {
												map[posY][posX] = "x";
												hasExit = false;
												lbl.setIcon(wall);
											}else{
												hasExit = false;
												lbl.setIcon(floor);
												map[posY][posX] = "f";
											}
										}
										if (map[posY][posX].startsWith("i")) {
											String itemArr[] = map[posY][posX].split(" ");
											itemsOnPage.remove(Integer.parseInt(itemArr[2]));
											lbl.setIcon(floor);
											map[posY][posX] = "f";
										}
										if (map[posY][posX].startsWith("m")) {
											String enemyArr[] = map[posY][posX].split(" ");
											enemiesOnPage.remove(Integer.parseInt(enemyArr[2]));
											lbl.setIcon(floor);
											map[posY][posX] = "f";
										}
										if (map[posY][posX].startsWith("n")) {
											String friendArr[] = map[posY][posX].split(" ");
											friendsOnPage.remove(Integer.parseInt(friendArr[2]));
											lbl.setIcon(floor);
											map[posY][posX] = "f";
										}
										if (map[posY][posX].equals("x")) {
											if (posY==0 || posX==0 || posY==map.length-1 || posX==map[0].length-1) {
												map[posY][posX] = "x";
												lbl.setIcon(wall);
											}else{
												lbl.setIcon(floor);
												map[posY][posX] = "f";
											}
										}
										break;
									}
								}
							};
						});
					}
					displayPanel.repaint();
					mapToChooseCbox.setEnabled(false);
					btnLoad.setEnabled(false);
					btnSave.setEnabled(true);
				
				
			}
		});
		
		
		JLabel lblSetElements = new JLabel("Set elements:");
		lblSetElements.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblSetElements.setBounds(30, 170, 120, 30);
		manipulatePanel.add(lblSetElements);
		
		JLabel lblWall = new JLabel("wall");
		lblWall.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblWall.setBounds(260, 250, 30, 30);
		manipulatePanel.add(lblWall);
		lblWall.setIcon(wall);
		lblWall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()== MouseEvent.BUTTON1) {
					element = new String();
					element = "x";
				}
			}
		});
		
		JLabel lblEntry = new JLabel("entry");
		lblEntry.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblEntry.setBounds(260, 170, 30, 30);
		manipulatePanel.add(lblEntry);
		lblEntry.setIcon(door);
		lblEntry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()== MouseEvent.BUTTON1) {
					element = new String();
					element = "I";
				}
			}
		});
		
		JLabel lblExit = new JLabel("exit");
		lblExit.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblExit.setBounds(260, 210, 30, 30);
		manipulatePanel.add(lblExit);
		lblExit.setIcon(door);
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()== MouseEvent.BUTTON1) {
					element = new String();
					element = "O";
				}
			}
		});
		
		JLabel lblEntryDoorlbl = new JLabel("Entry door");
		lblEntryDoorlbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblEntryDoorlbl.setBounds(170, 170, 80, 30);
		manipulatePanel.add(lblEntryDoorlbl);
		
		JLabel lblExitDoorlbl = new JLabel("Exit door");
		lblExitDoorlbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblExitDoorlbl.setBounds(170, 210, 80, 30);
		manipulatePanel.add(lblExitDoorlbl);
		
		JLabel lblWalllbl = new JLabel("Wall");
		lblWalllbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblWalllbl.setBounds(170, 250, 80, 30);
		manipulatePanel.add(lblWalllbl);
		
		JLabel lblRemove = new JLabel("Remove");
		lblRemove.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblRemove.setBounds(170, 410, 80, 30);
		manipulatePanel.add(lblRemove);
		lblRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton()== MouseEvent.BUTTON1) {
					element = new String();
					element = "";
				}
			}
		});
		
		JLabel lblFriend = new JLabel("Friend");
		lblFriend.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblFriend.setBounds(170, 330, 80, 30);
		manipulatePanel.add(lblFriend);
		
		final JComboBox<WarGameCharacterModel> friendCbox = new JComboBox<WarGameCharacterModel>();
		friendCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		friendCbox.setBounds(260, 335, 100, 21);
		manipulatePanel.add(friendCbox);
		for (String characterID : charactersByMap.keySet()) {
			characterOnPage = new WarGameCharacterModel();
			characterOnPage = charactersByMap.get(characterID);
			friendCbox.addItem(characterOnPage);
			characterOnPage = null;
		}
		friendCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				characterOnPage = new WarGameCharacterModel();
				characterOnPage = (WarGameCharacterModel) friendCbox.getSelectedItem();
				element = new String();
				element = "n";
			}
		});
		
		JLabel lblItem = new JLabel("Item");
		lblItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblItem.setBounds(170, 290, 80, 30);
		manipulatePanel.add(lblItem);
		
		final JComboBox<WarGameItemModel> itemCbox = new JComboBox<WarGameItemModel>();
		itemCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		itemCbox.setBounds(260, 296, 100, 21);
		manipulatePanel.add(itemCbox);
		for (String itemID : itemsByMap.keySet()) {
			itemOnPage = new WarGameItemModel();
			itemOnPage = itemsByMap.get(itemID);
			itemCbox.addItem(itemOnPage);
			itemOnPage = null;
		}
		itemCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				itemOnPage = new WarGameItemModel();
				itemOnPage = (WarGameItemModel) itemCbox.getSelectedItem();
				element = new String();
				element = "i";
			}
		});
		

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean validate = true;
				for (JLabel lbl : mapElementsLbls) {
					int posX;
					int posY;
					String[] lblArray = lbl.getText().split(" ");
					posY = Integer.parseInt(lblArray[0]);
					posX = Integer.parseInt(lblArray[1]);
					if (map[posY][posX].startsWith("O")||map[posY][posX].startsWith("I")||map[posY][posX].startsWith("i")||map[posY][posX].startsWith("m")||map[posY][posX].startsWith("n")) {
						String tempMap[][] = map;
						if(posY==0){
							if ((tempMap[posY][posX-1].equals("x"))&&(tempMap[posY][posX+1].equals("x"))&&(tempMap[posY+1][posX].equals("x"))) {
								validate = false;
							}
						}
						if(posY==tempMap.length-1){
							if ((tempMap[posY][posX-1].equals("x"))&&(tempMap[posY][posX+1].equals("x"))&&(tempMap[posY-1][posX].equals("x"))) {
								validate = false;
							}
						}			
						if (posX==0) {
							if ((tempMap[posY-1][posX].equals("x"))&&(tempMap[posY][posX+1].equals("x"))&&(tempMap[posY+1][posX].equals("x"))) {
								validate = false;
							}
						}			
						if(posX==tempMap[0].length-1){
							if ((tempMap[posY-1][posX].equals("x"))&&(tempMap[posY+1][posX].equals("x"))&&(tempMap[posY][posX-1].equals("x"))) {
								validate = false;
							}
						}
						if (posY!=0&&posX!=0&&posY!=tempMap.length-1&&posX!=tempMap[0].length-1) {
							if ((tempMap[posY-1][posX].equals("x"))&&(tempMap[posY+1][posX].equals("x"))&&(tempMap[posY][posX-1].equals("x"))&&(tempMap[posY][posX+1].equals("x"))) {
								validate = false;
							}
						}
					}
				}
				if (validate==true&&hasEntry==true&&hasExit==true) {	
					mapOnPage.setMap(map);
					mapOnPage.setContainEnemies(enemiesOnPage);
					mapOnPage.setContainFriends(friendsOnPage);
					mapOnPage.setContainItems(itemsOnPage);
					try {
						mapOnPage.saveMap(mapOnPage);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Save successfully");
					mapToChooseCbox.setEnabled(true);
					btnSave.setEnabled(false);
					btnLoad.setEnabled(true);
				}else{
					JOptionPane.showMessageDialog(null, "Not validate");
				}
			}
		});
		
		JLabel lblEnemy = new JLabel("Enemy");
		lblEnemy.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblEnemy.setBounds(170, 370, 80, 30);
		manipulatePanel.add(lblEnemy);
		
		final JComboBox<WarGameCharacterModel> enemyCbox = new JComboBox<WarGameCharacterModel>();
		enemyCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 10));
		enemyCbox.setBounds(260, 375, 100, 21);
		manipulatePanel.add(enemyCbox);
		for (String characterID : charactersByMap.keySet()) {
			characterOnPage = new WarGameCharacterModel();
			characterOnPage = charactersByMap.get(characterID);
			enemyCbox.addItem(characterOnPage);
			characterOnPage = null;
		}
		enemyCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				characterOnPage = new WarGameCharacterModel();
				characterOnPage = (WarGameCharacterModel) enemyCbox.getSelectedItem();
				element = new String();
				element = "m";
			}
		});
		
	}
}
