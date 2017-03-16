package warGame.View;

import warGame.Model.WarGameCharacterModel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Observer;
import java.util.Observable;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;



/**
 * This viewer allow users to manipulate the character information in GUI
 * @version build 1
 */


public class WarGameCharacterView extends JFrame implements Observer{

	String backpack[] = new String[10];
	String equip[] = new String[7];
	String message_buffer = new String();
	int picNumber_buffer;
	JLabel label_showScore[] = new JLabel[12];
	JLabel charpic = new JLabel();
	//backpack = Arrays.copyOf(((WarGameCharacterModel) o).getBackpack(),10);
    
    /**
     * Update the character's information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */
    
	public void update(final Observable o, Object arg) {
		// TODO Auto-generated method stub
		int viewType = ((WarGameCharacterModel) o).getViewType();
		switch(viewType){
		
		case 1:
			final JFrame frame = new JFrame("character");
			frame.setBounds(0, 0, 700, 500);
			frame.setSize(700,500);
			this.setLocation(0,0);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frame.setVisible(true);
			frame.setLayout(null);
			/*JLabel label_showScore[] = new JLabel[12];
			for(int i =0;i<12;i++)
			{
				String text[] = ((WarGameCharacterModel) o).getScore(i);
				label_showScore[i] = new JLabel(text[0]+":"+text[1]);
				label_showScore[i].setBounds(new Rectangle(0, i*30, 150, 30));
				frame.add(label_showScore[i]);
			}
			
			
			//add the character pic
			int picNumber = ((WarGameCharacterModel) o).getPicNumber();
			JLabel j1 = new JLabel();
			ImageIcon img = new ImageIcon("src/image/Character/"+picNumber+".png");
			j1= new JLabel(img);
			frame.getLayeredPane().add(j1, new Integer(Integer.MIN_VALUE));
			j1.setBounds(150, 0, 400, 400);
			Container contain = frame.getContentPane();
			((JPanel) contain).setOpaque(false);
			
			int id = 0;
			try {
				id = ((WarGameCharacterModel) o).getTotalChar();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			id = id+1;
			String message_buffer = new String();
			for(int i =0;i<18;i++)
			{
				String text[] = ((WarGameCharacterModel) o).getScore(i);
				message_buffer = message_buffer+" "+text[1];
			}
			final String message_buffer2 = id+message_buffer;
			final int picNumber_buffer = picNumber;*/
			charpic= new JLabel();
			frame.getLayeredPane().add(charpic, new Integer(Integer.MIN_VALUE));
			charpic.setBounds(200, 0, 400, 400);
			Container contain = frame.getContentPane();
			((JPanel) contain).setOpaque(false);
			for(int i =0;i<12;i++)
			{
				label_showScore[i] = new JLabel();
				label_showScore[i].setBounds(new Rectangle(0, i*30, 150, 30));
				frame.getLayeredPane().add(label_showScore[i], new Integer(Integer.MIN_VALUE));
			}
			JButton button_charType[] = new JButton[3];
			for(int i=0;i<3;i++)
			{
				final int charType_i = i;
				if(i ==0)
				{
					button_charType[i] = new JButton("Create Bully");
				}
				if(i ==1)
				{
					button_charType[i] = new JButton("Create Nimble");
				}
				if(i ==2)
				{
					button_charType[i] = new JButton("Create Tank");
				}
				button_charType[i].setBounds(550, 150+i*40, 120, 30);
				frame.add(button_charType[i]);
				button_charType[i].addActionListener(new ActionListener(){
	
					@Override
					public void actionPerformed(ActionEvent e) {
							((WarGameCharacterModel) o).createCharacter(charType_i);
							message_buffer = new String();
							for(int i =0;i<12;i++)
							{
								String text[] = ((WarGameCharacterModel) o).getScore(i);
								label_showScore[i].setText(text[0]+":"+text[1]);
							}
							//add the character pic
							int picNumber = ((WarGameCharacterModel) o).getPicNumber();
							ImageIcon img = new ImageIcon("src/image/Character/"+picNumber+".png");
							charpic.setIcon(img);
							int id = 0;
							try {
								id = ((WarGameCharacterModel) o).getTotalChar();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							id = id+1;
							for(int i =0;i<18;i++)
							{
								String text[] = ((WarGameCharacterModel) o).getScore(i);
								message_buffer = message_buffer+" "+text[1];
							}
							message_buffer = id+message_buffer;
							picNumber_buffer = picNumber;
					}
				});
			}
			
			
			JButton button_save = new JButton("Save");
			button_save.setBounds(new Rectangle(200,400,100,30));
			frame.add(button_save);
			button_save.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
	               try {
	            	    String message = new String();
	            	    message = message_buffer;
	                	String equipID[] = ((WarGameCharacterModel) o).getEquipID();
	          			for(int i =0;i<7;i++)
	          			{
	          				message = message+" "+equipID[i];
	          			}
	          			String backpackID[] = ((WarGameCharacterModel) o).getBackpackID();
	          			for(int i=0;i<10;i++)
	          			{
	          				message = message+" "+backpackID[i];
	          			}
	          			message = message+" "+picNumber_buffer+"\r\n";
						Boolean result = ((WarGameCharacterModel) o).saveChar(message);
						if(result == true)
						{
							JOptionPane.showMessageDialog(null, "Save Success!");
							frame.dispose();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			JButton button_cancel = new JButton("Cancel");
			button_cancel.setBounds(new Rectangle(400,400,100,30));
			frame.add(button_cancel);
			//set item
			try{
			final JComboBox cbox_loadChar = new JComboBox();
			cbox_loadChar.setBounds(450, 30, 250, 30);
			frame.add(cbox_loadChar);
			cbox_loadChar.setEditable(false);
			String buffer = new String();
			File file = new File("src/file/item.txt");
			BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			while((buffer = BF.readLine()) != null)
			{
				String str[] = buffer.trim().split(" ");
				cbox_loadChar.addItem("Item"+str[0]+" "+str[1]+" "+str[2]+" "+str[3]);
			}
			JButton button_loadChar = new JButton("Set");
			button_loadChar.setBounds(550, 80, 100, 30);
			frame.add(button_loadChar);
			button_loadChar.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					String itemInfo = cbox_loadChar.getSelectedItem().toString();
						
							((WarGameCharacterModel) o).setItem(itemInfo);
						
				}
			});
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
			//load char
		case 2:
			JFrame frameCase2 = new JFrame("Character Info");
			frameCase2.setBounds(0, 0, 700, 500);
			frameCase2.setSize(700,500);
			frameCase2.setLocation(0,0);
			frameCase2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frameCase2.setVisible(true);
			frameCase2.setLayout(null);
			final JLabel label_backpack[] = new JLabel[10];
			final JLabel label_equip[] = new JLabel[7];
			backpack = Arrays.copyOf(((WarGameCharacterModel) o).getBackpack(),10);
			equip = Arrays.copyOf(((WarGameCharacterModel) o).getEquip(),7);
			
			//pic
			JLabel label_pic = new JLabel();
			label_pic.setBounds(20, 20, 150, 250);
			int load_picNumber = ((WarGameCharacterModel) o).getPicNumber();
			ImageIcon load_img = new ImageIcon("src/image/Character/"+load_picNumber+".png");
			label_pic.setIcon(load_img);
			frameCase2.add(label_pic);
			
			//item
			for(int i=0;i<7;i++)
			{
				final int event_i = i;
				label_equip[i] = new JLabel();
				frameCase2.add(label_equip[i]);
				//label_equip[i].setBounds(i*90, 100, 66, 66);
			    if(i==0)
			    {
						label_equip[i].setBounds(200, 20, 66, 66);
				}
			    else if(i==3)
			    {
						label_equip[i].setBounds(270, 20, 66, 66);
				}
			    else if(i==1)
			    {
						label_equip[i].setBounds(200, 90, 66, 66);
				}
			    else if(i==2)
			    {
						label_equip[i].setBounds(270, 90, 66, 66);
				}
			    else if(i==4)
			    {
						label_equip[i].setBounds(200, 160, 66, 66);
				}
			    else if(i==5)
			    {
						label_equip[i].setBounds(270, 160, 66, 66);
				}
			    else if(i==6)
			    {
						label_equip[i].setBounds(237, 230, 66, 66);
				}
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
										((WarGameCharacterModel) o).setBackpack(backpack[i], i);
										break;
									}
								}
								String buffer = new String();
								label_equip[event_i].setIcon(null);
								buffer = equip[event_i];
								equip[event_i] = "null";
								label_equip[event_i].setBackground(Color.GRAY);
								((WarGameCharacterModel) o).setEquip(equip[event_i], event_i);
								((WarGameCharacterModel) o).setEquipChanged(buffer,null);
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
				frameCase2.add(label_backpack[i]);
				label_backpack[i].setBounds(i*70, 300, 66, 66);
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
									((WarGameCharacterModel) o).setEquip(buffer, index);
									((WarGameCharacterModel) o).setBackpack(backpack[event_i], event_i);
									((WarGameCharacterModel) o).setEquipChanged(null,buffer);
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
									((WarGameCharacterModel) o).setEquip(equip[index], index);
									((WarGameCharacterModel) o).setBackpack(backpack[event_i], event_i);
									((WarGameCharacterModel) o).setEquipChanged(buffer,buffer2);
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
			JButton button_edit = new JButton("Edit");
			button_edit.setBounds(new Rectangle(200,400,100,30));
			frameCase2.add(button_edit);
			button_edit.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					((WarGameCharacterModel) o).editCharacter();
				}
			});
			
			/*JTextField char_info = new JTextField();
			char_info.setBounds(400, 20, 150, 200);
			frameCase2.add(char_info);
			level = ((WarGameCharacterModel) o).getLevel();
			ability_scores = ((WarGameCharacterModel) o).getAbility_scores();
			armor_class = ((WarGameCharacterModel) o).getArmor_class();
			hit_points = ((WarGameCharacterModel) o).getHit_points();
			attack_bonus = ((WarGameCharacterModel) o).getAttack_bonus();
			damage_bonus = ((WarGameCharacterModel) o).getDamage_bonus();
			char_info.setText("Level:"+level+"\r\n");
			char_info.setText("Ability_scores:"+level+"\r\n");
			char_info.setText("Armor_class:"+level+"\r\n");
			char_info.setText("Hit_points:"+level+"\r\n");
			char_info.setText("Attack_bonus:"+level+"\r\n");
			char_info.setText("Damage_bonus:"+level+"\r\n");*/
			
			break;
			//edit info
		case 3:
			JFrame frameCase3 = new JFrame("Info Change");
			frameCase3.setBounds(400, 400, 200, 350);
			frameCase3.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frameCase3.setVisible(true);
			frameCase3.setLayout(null);
			
			JLabel label_info[] = new JLabel[11];
			for(int i =0;i<11;i++)
			{
				label_info[i] = new JLabel();
				frameCase3.add(label_info[i]);
				label_info[i].setBounds(0, i*30, 200, 30);
				label_info[i].setOpaque(true);
				int ori = 0;
				int change = 0;
				String str[] = ((WarGameCharacterModel) o).getScore(i);
				String str_ori[] = ((WarGameCharacterModel) o).getScore_ori(i);
				ori = Integer.parseInt(str_ori[1]);
				change = Integer.parseInt(str[1]);
				if((change - ori) > 0)
				{
					label_info[i].setText(str[0]+":"+ori+"--->"+change);
					label_info[i].setBackground(Color.green);
				}
				else if((change - ori) < 0)
				{
					label_info[i].setText(str[0]+":"+ori+"--->"+change);
					label_info[i].setBackground(Color.red);
				}
				else
				{
					label_info[i].setText(str[0]+":"+ori+"--->"+change);
				}
			}
			break;
		case 4:
			final JFrame frameCase4 = new JFrame("Edit Character");
			frameCase4.setBounds(400, 400, 200, 250);
			frameCase4.setSize(200,250);
			frameCase4.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			frameCase4.setVisible(true);
			frameCase4.setLayout(null);
			JLabel label_editInfo[] = new JLabel[7];
			String label_text[] = {"Level:","Strength:","Dexterity:","Constitution:","Intelligence:","Wisdom:"
					                ,"Charisma"};
			final JTextField text_editInfo[] = new JTextField[7];
			for(int i =0;i<7;i++)
			{
				
				label_editInfo[i] = new JLabel(label_text[i]);
				frameCase4.add(label_editInfo[i]);
				label_editInfo[i].setBounds(0, i*30, 100, 30);
				label_editInfo[i].setOpaque(true);
				text_editInfo[i] = new JTextField(10);
				text_editInfo[i].setBounds(100, i*30, 80, 30);
				frameCase4.add(text_editInfo[i]);
			}
			JButton button_edit_save = new JButton("Save");
			button_edit_save.setBounds(new Rectangle(0,200,100,30));
			frameCase4.add(button_edit_save);
			button_edit_save.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						String edit_level = text_editInfo[0].getText();
						String edit_strength = text_editInfo[1].getText();
						String edit_dexterity = text_editInfo[2].getText();
						String edit_constitution = text_editInfo[3].getText();
						String edit_intelligence = text_editInfo[4].getText();
						String edit_wisdom = text_editInfo[5].getText();
						String edit_charisma = text_editInfo[6].getText();
						String characterID = ((WarGameCharacterModel) o).getCharacterID();
						//String picNum = ((WarGameCharacterModel) o).getPicNumber()+"";
						String edit_message[] = {characterID,edit_level,edit_strength,edit_dexterity,edit_constitution
			                      ,edit_intelligence,edit_wisdom,edit_charisma};
						Boolean result = ((WarGameCharacterModel) o).replaceCharacter(edit_message);
						if(result == true)
						{
							frameCase4.dispose();
							JOptionPane.showMessageDialog(null, "Save Success!");
							//((WarGameCharacterModel) o).loadChar("Character"+characterID);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			
		}//switch
		
		
	}

}
