package warGame.View;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import warGame.Model.*;

@SuppressWarnings("serial")
public class WarGameStartView extends JFrame implements Observer{
	
	private WarGameMapModel mapOnPage;
	private Map<String, WarGameMapModel> mapsByMap;
	private ArrayList<JLabel> mapElementsLbls;
	private String heroPos;
	String map[][];
	@Override
	public void update(Observable o, Object arg) {
		ImageIcon wall = new ImageIcon("src/image/Map/wall.jpg");
		ImageIcon door = new ImageIcon("src/image/Map/door.jpg");
		ImageIcon chest = new ImageIcon("src/image/Map/chest.jpg");
		ImageIcon floor = new ImageIcon("src/image/Map/floor.jpg");
		ImageIcon monster = new ImageIcon("src/image/Map/monster.jpg");
		ImageIcon hero = new ImageIcon("src/image/Map/hero.jpg");
		
		final JFrame frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle("Tower of Fighters");
		frame.setSize(1280, 800);
		frame.getContentPane().setLayout(null);
		try {
			mapsByMap = WarGameMapModel.listAllMaps();
			mapOnPage = mapsByMap.get("2");
		} catch (UnsupportedEncodingException | FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
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
						//��������
					}else if(upPos[2].equals("I")){
						//�������
					}else if(upPos[2].equals("O")){
						//��������
					}else if(upPos[2].equals("c")){
						//��������
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
						//��������
					}else if(downPos[2].equals("I")){
						//�������
					}else if(downPos[2].equals("O")){
						//��������
					}else if(downPos[2].equals("c")){
						//��������
					}
				}
			}
		});
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				JLabel mapElement = new JLabel();
				
				switch (map[i][j]) {
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
					mapElement = new JLabel(i+" "+j+" "+"m");
					mapElement.setIcon(monster);
					mapElementsLbls.add(mapElement);
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
		characterViewPanel.setBounds(750, 0, 520, 540);
		frame.getContentPane().add(characterViewPanel);
		characterViewPanel.setLayout(null);
		
		JPanel inventoryViewPanel = new JPanel();
		inventoryViewPanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		inventoryViewPanel.setBounds(750, 550, 520, 200);
		frame.getContentPane().add(inventoryViewPanel);
		inventoryViewPanel.setLayout(null);
	}

	public void show(){}
}
