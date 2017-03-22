package warGame.Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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

@SuppressWarnings("serial")
public class WarGameController extends JFrame{

	WarGameMapModel mapModel;
	WarGameMapCreateView mapCreateView;
	WarGameMapLoadView mapLoadView;
	WarGameItemModel itemModel;
	WarGameItemView itemView;
	WarGameCharacterModel characterModel;
	WarGameCharacterView characterView;
	WarGameStartModel startModel;
	WarGameStartView startView;
	WarGameChooseView chooseView;
	WarGameCampaignModel campaignModel;
	WarGameCampaignCreationView campaignCreateView;
	WarGameCampaignLoadView campaignLoadView;
	
	/**
	 * custom construct method 
	 */
	public WarGameController(){
		
		itemModel = new WarGameItemModel();
		itemView = new WarGameItemView();
		itemModel.addObserver(itemView);
		
		characterModel = new WarGameCharacterModel();
		characterView = new WarGameCharacterView();
		characterModel.addObserver(characterView);
		
		
		JLayeredPane layeredPanel = new JLayeredPane();
		layeredPanel.setForeground(Color.BLACK);
		layeredPanel.setLayout(null);
				
		JPanel panel_background = new JPanel();
		panel_background.setBounds(0, 20, 1280, 750);
		layeredPanel.add(panel_background, JLayeredPane.DEFAULT_LAYER);
		
		JLabel background = new JLabel(new ImageIcon("src/image/main.jpg"));
		background.setBounds(new Rectangle(0, 0, 1000, 800));
		panel_background.add(background);	
		getContentPane().add(layeredPanel, BorderLayout.CENTER);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(255, 240, 200, 50);
		layeredPanel.add(btnNewGame, JLayeredPane.MODAL_LAYER);
		btnNewGame.setBackground(Color.BLACK);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startModel = new WarGameStartModel();
				startView = new WarGameStartView();
				chooseView = new WarGameChooseView();
				startModel.addObserver(chooseView);
				startModel.chooseView();
				dispose();
			}
		});
		btnNewGame.setFont(new Font("Simplified Arabic", Font.PLAIN, 30));
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(255, 300, 200, 50);
		layeredPanel.add(btnLoad, JLayeredPane.MODAL_LAYER);
		btnLoad.setBackground(Color.BLACK);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLoad.setFont(new Font("Simplified Arabic", Font.PLAIN, 30));
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(255, 360, 200, 50);
		layeredPanel.add(btnExit, JLayeredPane.MODAL_LAYER);
		btnExit.setBackground(Color.BLACK);
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});	
		btnExit.setFont(new Font("Simplified Arabic", Font.PLAIN, 30));
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		menuBar.setBounds(0, 0, 1274, 23);
		layeredPanel.add(menuBar, JLayeredPane.MODAL_LAYER);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		mnFile.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnFile);
		
		JMenu mnCharacter = new JMenu("Character");
		mnCharacter.setForeground(Color.BLACK);
		mnCharacter.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnFile.add(mnCharacter);
		
		JMenuItem mntmCreateCharacter = new JMenuItem("Create character");
		mnCharacter.add(mntmCreateCharacter);
		mntmCreateCharacter.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmCreateCharacter.setForeground(Color.BLACK);
		mntmCreateCharacter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				characterModel = new WarGameCharacterModel();
				characterView = new WarGameCharacterView();
				characterModel.addObserver(characterView);
				characterModel.createCharacterFrame();
			}
		});
		
		JMenuItem mntmViewCharacters = new JMenuItem("Load character");
		mnCharacter.add(mntmViewCharacters);
		mntmViewCharacters.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmViewCharacters.setForeground(Color.BLACK);
		mntmViewCharacters.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
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
					Map<String, WarGameCharacterModel> mapList = characterModel.listAllCharacters();
					for (Map.Entry<String, WarGameCharacterModel> entry : mapList.entrySet()) {
						WarGameCharacterModel characterModel_buffer = entry.getValue();
						String id = characterModel_buffer.getCharacterID();
						cbox_loadChar.addItem("Character"+id);
					}
					JButton button_loadChar = new JButton("Load");
					button_loadChar.setBounds(100, 80, 100, 30);
					frame.add(button_loadChar);
					button_loadChar.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							String charID = cbox_loadChar.getSelectedItem().toString();
						    frame.dispose();
						    String str[] = charID.trim().split("r");
								try {
									characterModel.loadCharacterJson(str[2]);
								} catch (IOException e1) {
									
									e1.printStackTrace();
								}
						}
					});
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
			}
						
		});
		
		JMenu mnItem = new JMenu("Item");
		mnItem.setForeground(Color.BLACK);
		mnItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnFile.add(mnItem);
		
		JMenuItem mntmCreateItem = new JMenuItem("Create item");
		mntmCreateItem.setForeground(Color.BLACK);
		mntmCreateItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnItem.add(mntmCreateItem);
		mntmCreateItem.addActionListener(new ActionListener(){

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
						try {
							itemModel.createItem(itemType,enchanType,enchanNumber);
						} catch (NumberFormatException
								| UnsupportedEncodingException
								| FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
				
			}
		});
		
		JMenuItem mntmLoadItem = new JMenuItem("Load item");
		mntmLoadItem.setForeground(Color.BLACK);
		mntmLoadItem.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnItem.add(mntmLoadItem);
		mntmLoadItem.addActionListener(new ActionListener(){

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
					Map<String, WarGameItemModel> mapList = itemModel.listAllItems();
					for (Map.Entry<String, WarGameItemModel> entry : mapList.entrySet()) {
						WarGameItemModel characterModel_buffer = entry.getValue();
						String id = characterModel_buffer.getItemID();
						String itemType = characterModel_buffer.getItemType();
						String enchanType = characterModel_buffer.getEnchanType();
						String enchanNum = characterModel_buffer.getEnchanNumber();
						cbox_loadChar.addItem("Item "+id+" "+itemType+" "+enchanType+" "+enchanNum);
					}
					JButton button_loadChar = new JButton("Load");
					button_loadChar.setBounds(100, 80, 100, 30);
					frame.add(button_loadChar);
					button_loadChar.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							String ID[] = cbox_loadChar.getSelectedItem().toString().trim().split(" ");
						    frame.dispose();
								try {
									itemModel.loadItemJson(ID[1]);
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
		
		JMenu mnMap = new JMenu("Map");
		mnMap.setForeground(Color.BLACK);
		mnMap.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnFile.add(mnMap);
		
		JMenuItem mntmMapCreate = new JMenuItem("Create map");
		mntmMapCreate.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmMapCreate.setForeground(Color.BLACK);
		mnMap.add(mntmMapCreate);
		mntmMapCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapModel = new WarGameMapModel();
				mapCreateView = new WarGameMapCreateView();
				mapModel.addObserver(mapCreateView);
				mapModel.setMapCreationView();
			}
		});
		
		JMenuItem mntmMapLoad = new JMenuItem("Load map");
		mntmMapLoad.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmMapLoad.setForeground(Color.BLACK);
		mnMap.add(mntmMapLoad);
		mntmMapLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapModel = new WarGameMapModel();
				mapLoadView = new WarGameMapLoadView();
				mapModel.addObserver(mapLoadView);
				mapModel.setMapLoadView();
			}
		});
		
		JMenu mnCampaign = new JMenu("Campaign");
		mnCampaign.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mnCampaign.setForeground(Color.BLACK);
		mnFile.add(mnCampaign);
		
		JMenuItem mntmCampaign = new JMenuItem("Create campaign");
		mntmCampaign.setForeground(Color.BLACK);
		mnCampaign.add(mntmCampaign);
		mntmCampaign.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmCampaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campaignModel = new WarGameCampaignModel();
				campaignCreateView = new WarGameCampaignCreationView();
				campaignModel.addObserver(campaignCreateView);
				campaignModel.setCampaignCreationView();
			}
		});
				
		JMenuItem mntmLoadCampaign = new JMenuItem("Load campaign");
		mntmLoadCampaign.setForeground(Color.BLACK);
		mnCampaign.add(mntmLoadCampaign);
		mntmLoadCampaign.setFont(new Font("Simplified Arabic", Font.PLAIN, 13));
		mntmLoadCampaign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campaignModel = new WarGameCampaignModel();
				campaignLoadView = new WarGameCampaignLoadView();
				campaignModel.addObserver(campaignLoadView);
				campaignModel.setCampaignLoadView();
			}
		});
		
		JMenu mnAbout = new JMenu("About");
		mnAbout.setFont(new Font("Simplified Arabic", Font.PLAIN, 18));
		menuBar.add(mnAbout);
				
		JMenuItem mntmAuthor = new JMenuItem("Author");
		mntmAuthor.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mntmAuthor.setForeground(Color.BLACK);
		mnAbout.add(mntmAuthor);
			
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/map/hero.jpg"));
		setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("WarGame");
		setSize(1280, 800);
		
	}
	
	/**
	 * main method
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new WarGameController();
	}

}
