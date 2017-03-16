package warGame.Controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import warGame.Model.*;
import warGame.View.*;
/**
 * This class is a controller class contains methods that allows user to :
 * <p>Create and edit character<br/>
 * <p>Create and edit map<br/>
 * <p>Create and edit item<br/>
 * <p>Create and edit campaign<br/>
 * @version build 1
 */

public class WarGameController extends JFrame{
    WarGameCharacterModel characterModel;
    WarGameCharacterView  characterView;
    WarGameItemModel itemModel;
    WarGameItemView itemView;
    WarGameCampaignModel campaignModel;
    WarGameCampaignView campaignView;
    WarGameMapModel mapModel;
    WarGameMapView mapView;
    WarGameStartModel startModel;
    WarGameStartView startView;
    
    JTextField text_itemType = new JTextField(15);
    JTextField text_enchanType = new JTextField(15);
    JTextField text_enchanNumber = new JTextField(15);
    JTextField text_campaign = new JTextField(15);
    

    
	public WarGameController(){
		characterModel = new WarGameCharacterModel();
		characterView = new WarGameCharacterView();
		characterModel.addObserver(characterView);
		
		itemModel = new WarGameItemModel();
		itemView = new WarGameItemView();
		itemModel.addObserver(itemView);
		
		campaignModel = new WarGameCampaignModel();
		campaignView = new WarGameCampaignView();
		campaignModel.addObserver(campaignView);
		
		mapModel = new WarGameMapModel();
		mapView = new WarGameMapView();
		mapModel.addObserver(mapView);
		
		startModel = new WarGameStartModel();
		startView = new WarGameStartView();
		startModel.addObserver(startView);
		
		this.setTitle("WarGame");
		this.setBounds(300, 200, 800, 600);
		this.setSize(800,600);
		//this.setLocation(300,200);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		JButton button = new JButton("Create Character");
		this.add(button);
		button.setBounds(325,210,150,50);
		button.setBackground(Color.green);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				characterModel.createCharacterFrame();
			}
						
		});
		
		JButton button2 = new JButton("Create Map");
		this.add(button2);
		button2.setBounds(325,270,150,50);
		button2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mapModel.setMapCreationView();
			}
		});
		
		
		//create item
		JButton button4 = new JButton("Create Item");
		this.add(button4);
		button4.setBounds(325,330,150,50);
		button4.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Item");
				frame.setBounds(0, 0, 700, 500);
				frame.setSize(700,500);
				frame.setLocation(0,0);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
				frame.setLayout(null);
				
				
				//item type view
				JLabel label_itemType = new JLabel("Item Type:");
				label_itemType.setBounds(new Rectangle(0, 0, 150, 30));
				frame.add(label_itemType);
				
				
				//item enchantment type view
				JLabel label_enchanType = new JLabel("Enchantment Type:");
				label_enchanType.setBounds(new Rectangle(0, 50, 150, 30));
				frame.add(label_enchanType);
				
				
				//enchantment number view
				JLabel label_enchanNumber = new JLabel("Enchantment Number:");
				label_enchanNumber.setBounds(new Rectangle(0, 100, 150, 30));
				frame.add(label_enchanNumber);
				
				
				final JComboBox cbox_itemType = new JComboBox();
				cbox_itemType.setBounds(160, 0, 150, 30);
				frame.add(cbox_itemType);
				cbox_itemType.addItem("Helmet");
				cbox_itemType.addItem("Armor");
				cbox_itemType.addItem("Shield");
				cbox_itemType.addItem("Ring");
				cbox_itemType.addItem("Belt");
				cbox_itemType.addItem("Boots");
				cbox_itemType.addItem("Weapon");
				final JComboBox cbox_enchanType = new JComboBox();
				cbox_enchanType.setBounds(160, 50, 150, 30);
				frame.add(cbox_enchanType);
				cbox_enchanType.setEnabled(false);
				cbox_itemType.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						cbox_enchanType.removeAllItems();
						if(cbox_itemType.getSelectedItem().toString().equals("Helmet"))
						{
							cbox_enchanType.addItem("Intelligence");
							cbox_enchanType.addItem("Wisdom");
							cbox_enchanType.addItem("Armor_class");
							cbox_enchanType.setEnabled(true);							
						}
						if(cbox_itemType.getSelectedItem().toString().equals("Armor"))
						{
							cbox_enchanType.addItem("Armor_class");
							cbox_enchanType.setEnabled(true);
						}
						if(cbox_itemType.getSelectedItem().toString().equals("Shield"))
						{
							cbox_enchanType.addItem("Armor_class");
							cbox_enchanType.setEnabled(true);
						}
						if(cbox_itemType.getSelectedItem().toString().equals("Ring"))
						{
							cbox_enchanType.addItem("Armor_class");
							cbox_enchanType.addItem("Strength");
							cbox_enchanType.addItem("Constitution");
							cbox_enchanType.addItem("Wisdom");
							cbox_enchanType.addItem("Charisma");
							cbox_enchanType.setEnabled(true);
						}
						if(cbox_itemType.getSelectedItem().toString().equals("Belt"))
						{
							cbox_enchanType.addItem("Constitution");
							cbox_enchanType.addItem("Strength");
							cbox_enchanType.setEnabled(true);
						}
						if(cbox_itemType.getSelectedItem().toString().equals("Boots"))
						{
							cbox_enchanType.addItem("Dexterity");
							cbox_enchanType.addItem("Armor_class");
							cbox_enchanType.setEnabled(true);
						}
						if(cbox_itemType.getSelectedItem().toString().equals("Weapon"))
						{
							cbox_enchanType.addItem("Attack_bonus");
							cbox_enchanType.addItem("Damage_bonus");
							cbox_enchanType.setEnabled(true);
						}
					}
				});
				
				final JComboBox cbox_enchanNum = new JComboBox();
				cbox_enchanNum.setBounds(160, 100, 150, 30);
				frame.add(cbox_enchanNum);
				cbox_enchanNum.addItem("1");
				cbox_enchanNum.addItem("2");
				cbox_enchanNum.addItem("3");
				cbox_enchanNum.addItem("4");
				cbox_enchanNum.addItem("5");
				
				
				//create item button
				JButton button_createItem = new JButton("Create");
				frame.add(button_createItem);
				button_createItem.setBounds(160,150,150,50);
				button_createItem.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						String itemType = cbox_itemType.getSelectedItem().toString();
						String enchanType = cbox_enchanType.getSelectedItem().toString();
						String enchanNumber = cbox_enchanNum.getSelectedItem().toString();
						itemModel.createItem(itemType,enchanType,enchanNumber);
					}
				});
				
				
			}
		});
		
		//create campaign
		JButton button5 = new JButton("Create Campaign");
		this.add(button5);
		button5.setBounds(325,390,150,50);
		button5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				campaignModel.createCampaign();
			}
		});
		
		JButton button6 = new JButton("Save/Load");
		this.add(button6);
		button6.setBounds(325,450,150,50);
		
		JButton button7 = new JButton("Exit");
		this.add(button7);
		button7.setBounds(325,510,150,50);
		
		JButton button_start = new JButton("Start Game");
		this.add(button_start);
		button_start.setBounds(325,150,150,50);
		button_start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startModel.DisplayMapView();
			}
		});
		
		JButton button_loadChar = new JButton("Load Character");
		this.add(button_loadChar);
		button_loadChar.setBounds(475,210,150,50);
		button_loadChar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					final JFrame frame = new JFrame("Load Char");
					frame.setBounds(300,400, 300, 200);
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setVisible(true);
					frame.setLayout(null);
					final JComboBox cbox_loadChar = new JComboBox();
					cbox_loadChar.setBounds(30, 30, 200, 30);
					frame.add(cbox_loadChar);
					cbox_loadChar.setEditable(false);
					String buffer = new String();
					File file = new File("src/file/character.txt");
					BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
					while((buffer = BF.readLine()) != null)
					{
						String str[] = buffer.trim().split(" ");
						cbox_loadChar.addItem("Character"+str[0]);
					}
					JButton button_loadChar = new JButton("Load");
					button_loadChar.setBounds(100, 80, 100, 30);
					frame.add(button_loadChar);
					button_loadChar.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							String charID = cbox_loadChar.getSelectedItem().toString();
						    frame.dispose();
								try {
									characterModel.loadChar(charID);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}
					});
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
						
		});
		
		JButton button_loadMap = new JButton("Load Map");
		this.add(button_loadMap);
		button_loadMap.setBounds(475,270,150,50);
		button_loadMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mapModel.setMapCreationView();;
			}
						
		});
		
		JButton button_loadItem = new JButton("Load Item");
		this.add(button_loadItem);
		button_loadItem.setBounds(475,330,150,50);
		button_loadItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					final JFrame frame = new JFrame("Load Item");
					frame.setBounds(300,400, 300, 200);
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setVisible(true);
					frame.setLayout(null);
					final JComboBox cbox_loadChar = new JComboBox();
					cbox_loadChar.setBounds(30, 30, 200, 30);
					frame.add(cbox_loadChar);
					cbox_loadChar.setEditable(false);
					String buffer = new String();
					File file = new File("src/file/item.txt");
					BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
					while((buffer = BF.readLine()) != null)
					{
						String str[] = buffer.trim().split(" ");
						cbox_loadChar.addItem("Item"+str[0]);
					}
					JButton button_loadChar = new JButton("Load");
					button_loadChar.setBounds(100, 80, 100, 30);
					frame.add(button_loadChar);
					button_loadChar.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							String charID = cbox_loadChar.getSelectedItem().toString();
						    frame.dispose();
								try {
									itemModel.loadItem(charID);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						}
					});
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			
						
		});
		
		JButton button_loadCampaign = new JButton("Load Campaign");
		this.add(button_loadCampaign);
		button_loadCampaign.setBounds(475,390,150,50);
		button_loadCampaign.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				campaignModel.loadCampaignList();
			}
						
		});
		
		JLabel j_backgroud = new JLabel();
		ImageIcon img = new ImageIcon("src/image/123.jpg");
		j_backgroud= new JLabel(img);
		this.getLayeredPane().add(j_backgroud, new Integer(Integer.MIN_VALUE));
		j_backgroud.setBounds(0, 0, 800, 600);
		Container contain = this.getContentPane();
		((JPanel) contain).setOpaque(false);
		
	}
    
    /**
     *<p> main function<br/>
     */
	public static void main(String[] av){
		new WarGameController();
		
		
		
	}
}
