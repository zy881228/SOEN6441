package warGame.View;

import java.awt.Color;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import warGame.Model.WarGameCampaignModel;


/**
 * This viewer allow users to manipulate the campaign information in GUI
 * @version build 1
 */

public class WarGameCampaignView extends JFrame implements Observer{

	String mapSequence = new String();
    String mapSerial = new String();
    String mapCode = new String();
	ArrayList<String>mapSequenceList = new ArrayList<String>();
	String campaignID = new String();
	ArrayList<String>mapFileNameList = new ArrayList<String>();
	@Override
    
    /**
     * Update the campaign information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */

    public void reset(){
    
    }
    
    public void triggerValue(){
    
    }
    
	public void update(final Observable o, Object arg) {
		// TODO Auto-generated method stub
		int viewType = ((WarGameCampaignModel) o).getViewType();
		switch(viewType)
		{
			case 1:
				mapSequenceList.clear();
				mapSequence = new String();
				JFrame frame = new JFrame("Campaign");
				frame.setBounds(0, 0, 700, 500);
				frame.setSize(700,500);
				frame.setLocation(0,0);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
				frame.setLayout(null);
				final JComboBox<String> fileCbox = new JComboBox<String>();
				((WarGameCampaignModel) o).listing();
				ArrayList<String> mapFiles = new ArrayList<String>();
				mapFiles = ((WarGameCampaignModel) o).getMapFileName();
				for (String s : mapFiles) {
					fileCbox.addItem(s);
				}
				fileCbox.setBounds(50,150,150,30);
				frame.add(fileCbox);
				JButton add = new JButton("Add");
				add.setBounds(200,150,100,30);
				frame.add(add);
		
				
		//		text_campaign = new JTextField();
				
				//campaign sequence
				JLabel label_campaign = new JLabel("Campaign Sequence:");
				label_campaign.setBounds(new Rectangle(0, 0, 150, 30));
				frame.add(label_campaign);
		//		text_campaign.setBounds(new Rectangle(160, 0, 300, 30));
		//		frame.add(text_campaign);
				final JTextArea map_seq = new JTextArea();
				map_seq.setBounds(160, 0, 300, 100);
				map_seq.setEditable(false);
				map_seq.setLineWrap(true); //change line
				map_seq.setWrapStyleWord(true);
				frame.add(map_seq);
				
				add.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mapSequenceList.add(fileCbox.getSelectedItem().toString());
           
						if(mapSequence.isEmpty())
						{
							mapSequence = fileCbox.getSelectedItem().toString();
						}
						else
						{
							mapSequence = mapSequence+"--->"+fileCbox.getSelectedItem().toString();
						}
						map_seq.setText(mapSequence);
						
					}
				});
				//create campaign button
				JButton button_createCampaign = new JButton("Save");
				frame.add(button_createCampaign);
				button_createCampaign.setBounds(200,200,150,50);
				button_createCampaign.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int campaignID = ((WarGameCampaignModel) o).getTotalNum()+1;				
							Boolean result = ((WarGameCampaignModel) o).saveCampaign(campaignID,mapSequenceList);
							if(result == true)
							{
								map_seq.setText("");
								JOptionPane.showMessageDialog(null, "Save Success!");
								mapFileNameList = new ArrayList();
								mapSequence = new String();
								mapSequenceList = new ArrayList();
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 2:
				JFrame frameCase2 = new JFrame("Campaign");
				frameCase2.setBounds(0, 0, 700, 500);
				frameCase2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frameCase2.setVisible(true);
				frameCase2.setLayout(null);
				final JTextArea campaign_view = new JTextArea();
				campaign_view.setBounds(160, 0, 300, 100);
				campaign_view.setEditable(false);
				campaign_view.setLineWrap(true); //change line
				campaign_view.setWrapStyleWord(true);
				frameCase2.add(campaign_view);
				final JComboBox<String> editCbox = new JComboBox<String>();
				editCbox.setBounds(150,150,100,30);
				editCbox.addItem("Add");
				editCbox.addItem("Delete");
				editCbox.addItem("Replace");
				frameCase2.add(editCbox);
				
				final JComboBox<String> mapToEditCbox = new JComboBox<String>();
				mapToEditCbox.setBounds(150,270,100,30);
				mapToEditCbox.setEnabled(false);
				frameCase2.add(mapToEditCbox);
				
				final JComboBox<String> mapSeqToEditCbox = new JComboBox<String>();
				mapSeqToEditCbox.setBounds(150,210,100,30);
				mapSeqToEditCbox.setEnabled(false);
				frameCase2.add(mapSeqToEditCbox);
				
				final JButton button_set = new JButton("Edit");
				button_set.setBounds(400, 400, 100, 30);
				frameCase2.add(button_set);
				button_set.setEnabled(false);
			
				editCbox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						mapToEditCbox.setEnabled(true);
						mapSeqToEditCbox.setEnabled(true);
						mapToEditCbox.removeAllItems();;
						mapSeqToEditCbox.removeAllItems();
						mapFileNameList.clear();
						((WarGameCampaignModel) o).listing();
						mapFileNameList = ((WarGameCampaignModel) o).getMapFileName();
						for (String string : mapFileNameList) {
							mapToEditCbox.addItem(string);
						}

						if (editCbox.getSelectedItem().toString().equals("Add")) {
							for (int i = 0; i < mapSequenceList.size()+1; i++) {
								mapSeqToEditCbox.addItem(i+"");
							}
							button_set.setEnabled(true);
						}
						if(editCbox.getSelectedItem().toString().equals("Delete")){
							mapToEditCbox.setEnabled(false);
							for (int i = 1; i < mapSequenceList.size()+1; i++) {
								mapSeqToEditCbox.addItem(i+"");
							}
							button_set.setEnabled(true);
						}
						if(editCbox.getSelectedItem().toString().equals("Replace")){
							for (int i = 1; i < mapSequenceList.size()+1; i++) {
								mapSeqToEditCbox.addItem(i+"");
							}
							button_set.setEnabled(true);
						}
					
					}
				});
			
				
				button_set.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						ArrayList<String> mapSeList_buffer = new ArrayList();
						if (editCbox.getSelectedItem().toString().equals("Add")) {
							int position = Integer.parseInt(mapSeqToEditCbox.getSelectedItem().toString());
							String mapName = mapToEditCbox.getSelectedItem().toString();
							int count = 0;
							if(position == 0)
							{
								mapSeList_buffer.add(mapName);
								for (String str : mapSequenceList) {
									mapSeList_buffer.add(str);
								}
								mapSequenceList.clear();
								for (String str : mapSeList_buffer) {
									mapSequenceList.add(str);
								}
							}
							else
							{
								for (String str : mapSequenceList) {
									if(count == position)
									{
										mapSeList_buffer.add(mapName);
										mapSeList_buffer.add(str);
									}
									else{
										mapSeList_buffer.add(str);
									}
									count++;
								}
								if(count == position)
								{
									mapSeList_buffer.add(mapName);
								}
								mapSequenceList.clear();
								for (String str : mapSeList_buffer) {
									mapSequenceList.add(str);
								}
							}
						}
						if(editCbox.getSelectedItem().toString().equals("Delete")){
							int position = Integer.parseInt(mapSeqToEditCbox.getSelectedItem().toString());
							String mapName = mapToEditCbox.getSelectedItem().toString();
							int count = 1;
							for (String str : mapSequenceList) {
								if(count != position)
								{
									mapSeList_buffer.add(str);
								}
								count++;
							}
							mapSequenceList.clear();
							for (String str : mapSeList_buffer) {
								mapSequenceList.add(str);
							}
						}
						if(editCbox.getSelectedItem().toString().equals("Replace")){
							int position = Integer.parseInt(mapSeqToEditCbox.getSelectedItem().toString());
							String mapName = mapToEditCbox.getSelectedItem().toString();
							int count = 1;
							for (String str : mapSequenceList) {
								if(count == position)
								{
									mapSeList_buffer.add(mapName);
								}
								else
								{
									mapSeList_buffer.add(str);
								}
								count++;
							}
							mapSequenceList.clear();
							for (String str : mapSeList_buffer) {
								mapSequenceList.add(str);
							}
						}
						mapSequence = new String();
						for (String str : mapSequenceList) {
							if(mapSequence.isEmpty())
							{
								mapSequence = str;
							}
							else
							{
								mapSequence = mapSequence+"--->"+str;
							}
						}
						campaign_view.setText(mapSequence);
						mapSeqToEditCbox.removeAllItems();
						mapToEditCbox.removeAllItems();
					}
				});
				
				JButton button_save = new JButton("Save");
				button_save.setBounds(400, 450, 100, 30);
				frameCase2.add(button_save);
				button_save.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String campaignID = ((WarGameCampaignModel) o).getCampaignID();
						try {
							Boolean result = ((WarGameCampaignModel) o).editCampaign(campaignID,mapSequenceList);
							if(result == true)
							{
								JOptionPane.showMessageDialog(null, "Save Success!");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
				
				ArrayList<String> campaignList = ((WarGameCampaignModel) o).getCampaignList();
//				mapSequenceList = campaignList;
				mapSequence = new String();
				for (String str : campaignList) {
					if(mapSequence.isEmpty())
					{
						mapSequence = str;
					}
					else
					{
						mapSequence = mapSequence+"--->"+str;
					}
				}
				campaign_view.setText(mapSequence);
				
				
				

				
				break;
			case 3:
				try{
					final JFrame frameCase3 = new JFrame("Load Campaign");
					frameCase3.setBounds(300,400, 300, 200);
					frameCase3.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frameCase3.setVisible(true);
					frameCase3.setLayout(null);
					final JComboBox cbox_loadChar = new JComboBox();
					cbox_loadChar.setBounds(30, 30, 200, 30);
					frameCase3.add(cbox_loadChar);
					cbox_loadChar.setEditable(false);
					String buffer = new String();
					File file = new File("src/file/campaign.txt");
					BufferedReader BF = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
					while((buffer = BF.readLine()) != null)
					{
						String str[] = buffer.trim().split(" ");
						cbox_loadChar.addItem("Campaign"+str[0]);
					}
					JButton button_loadChar = new JButton("Load");
					button_loadChar.setBounds(100, 80, 100, 30);
					frameCase3.add(button_loadChar);
					button_loadChar.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							campaignID = cbox_loadChar.getSelectedItem().toString();
							frameCase3.dispose();
								try {
									((WarGameCampaignModel) o).loadCampaign(campaignID);
									mapSequenceList = ((WarGameCampaignModel) o).getCampaignList();
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
				break;
				
		}//switch
	}

}
