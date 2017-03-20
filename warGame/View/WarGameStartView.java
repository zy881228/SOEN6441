package warGame.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import warGame.Model.*;

@SuppressWarnings("serial")
public class WarGameStartView extends JFrame implements Observer{
	
	private WarGameMapModel mapOnPage;
	private Map<String, WarGameMapModel> mapsByMap;
	private ArrayList<JLabel> mapElementsLbls;
	private String heroPos;
	String map[][];
	JLabel label_pic = new JLabel();
	JLabel label_scores[] = new JLabel[12];
	JLabel label_equip[] = new JLabel[7];
	JLabel label_backpack[] = new JLabel[10];
	JLabel label_showScore[] = new JLabel[12];
	String backpack[] = new String[10];
	String equip[] = new String[7];
	WarGameCharacterModel characterModel = new WarGameCharacterModel();
	@Override
	public void update(final Observable o, Object arg) {
		ImageIcon wall = new ImageIcon("src/image/Map/wall.jpg");
		ImageIcon door = new ImageIcon("src/image/Map/door.jpg");
		ImageIcon chest = new ImageIcon("src/image/Map/chest.jpg");
		ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
		ImageIcon monster = new ImageIcon("src/image/Map/monster.jpg");
		ImageIcon hero = new ImageIcon("src/image/Map/hero.jpg");
		
		final JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		//frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle("WarGame");
		frame.setBounds(0, 0, 1280, 1000);
		frame.setSize(1280, 1000);
		frame.getContentPane().setLayout(null);
		
		try {
			mapsByMap = WarGameMapModel.listAllMaps();
			mapOnPage = mapsByMap.get("8");
			characterModel = characterModel.listAllCharacters().get("7");
		} catch (UnsupportedEncodingException | FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		map = mapOnPage.getMap();
		mapElementsLbls = new ArrayList<JLabel>();
		map[2][1] = "h";
	
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
						
					}else if(leftPos[2].equals("I")){
						
					}else if(leftPos[2].equals("O")){
					
					}else if(leftPos[2].equals("c")){
						
					}
					
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
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
	//					for(int i=0;i<map.length;i++){
	//					for(int j=0;j<map[i].length;j++){
	//						System.out.print(map[i][j]);
	//						}
	//					System.out.print("\n");
	//					}	
					}else if(rightPos[2].equals("m")){
						
					}else if(rightPos[2].equals("I")){
						
					}else if(rightPos[2].equals("O")){
						
					}else if(rightPos[2].equals("c")){
						
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_UP){
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
						
					}else if(upPos[2].equals("I")){
						
					}else if(upPos[2].equals("O")){
						
					}else if(upPos[2].equals("c")){
					
					}
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
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
						
					}else if(downPos[2].equals("I")){
						
					}else if(downPos[2].equals("O")){
						
					}else if(downPos[2].equals("c")){
						
					}
				}
			}
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
					mapElement.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e){
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								
							}
						}
					});
					break;
				case "m":
					mapElement = new JLabel(i+" "+j+" "+"m");
					mapElement.setIcon(monster);
					mapElementsLbls.add(mapElement);
					mapElement.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e){
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								String str[] = map[i_buffer][j_buffer].trim().split(" ");
								characterModel = mapOnPage.getContainEnemies().get(Integer.parseInt(str[2]));
								createCharacterView(characterModel);
							}
						}
					});
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
					mapElement = new JLabel(i+" "+j+" "+"n");
					mapElement.setIcon(hero);
					mapElementsLbls.add(mapElement);
					mapElement.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e){
							if(e.getButton() == MouseEvent.BUTTON1)
							{
								String str[] = map[i_buffer][j_buffer].trim().split(" ");
								characterModel = mapOnPage.getContainFriends().get(Integer.parseInt(str[2]));
								createCharacterView(characterModel);
							}
						}
					});
					break;
				}
			}
		}
		
		for (JLabel lbl : mapElementsLbls) {
			int posX;
			int posY;
			String[] lblArray = lbl.getText().split(" ");
			posY = Integer.parseInt(lblArray[0]);
			posX = Integer.parseInt(lblArray[1]);
			lbl.setBounds(posX*30, posY*30, 30, 30);
			mapPanel.add(lbl);
		}	
		
		JPanel characterViewPanel = new JPanel();
		characterViewPanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		characterViewPanel.setBounds(750, 0, 520, 650);
		frame.getContentPane().add(characterViewPanel);
		characterViewPanel.setLayout(null);
		int picNum = characterModel.getPicNumber();
		ImageIcon img = new ImageIcon("src/image/Character/"+picNum+".png");
		label_pic.setIcon(img);
		label_pic.setBounds(210, 150, 150, 250);
		characterViewPanel.add(label_pic);
		characterViewPanel.setBackground(Color.lightGray);
		//characterModel = mapOnPage.getContainFriends().get(0);
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
				//String itemName = prefix[0]+prefix[1];
				ImageIcon img_item = new ImageIcon("src/image/item/"+prefix[0]+"/"+prefix[1]+".jpeg");
				label_equip[i].setIcon(img_item);
			}
			label_equip[i].addMouseListener(new MouseAdapter(){
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
		
		//backpack
		for(int i=0;i<10;i++)
		{
			final int event_i = i;
			label_backpack[i] = new JLabel();
			characterViewPanel.add(label_backpack[i]);
			if(i<5)
			{
				label_backpack[i].setBounds(i*70+85, 500, 66, 66);
			}
			if(i>4)
			{
				label_backpack[i].setBounds(i*70-265, 570, 66, 66);
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
		}//backpack for
		
		
		
		/*JPanel inventoryViewPanel = new JPanel();
		inventoryViewPanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		inventoryViewPanel.setBounds(750, 550, 520, 200);
		frame.getContentPane().add(inventoryViewPanel);
		inventoryViewPanel.setLayout(null);*/
	}
	
	public void createCharacterView(WarGameCharacterModel characterModel){
		JFrame frame = new JFrame("Character Info");
		frame.setBounds(300, 0, 520, 700);
		frame.setVisible(true);
		frame.setLayout(null);
		JLabel label_pic = new JLabel();
		JLabel label_scores[] = new JLabel[12];
		JLabel label_equip[] = new JLabel[7];
		JLabel label_backpack[] = new JLabel[10];
		JLabel label_showScore[] = new JLabel[12];
		String backpack[] = new String[10];
		String equip[] = new String[7];
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
		
		equip = characterModel.getEquip();
		backpack = characterModel.getBackpack();
		for(int i=0;i<7;i++)
		{
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
		}
		for(int i=0;i<10;i++)
		{
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
		}
	}
}
