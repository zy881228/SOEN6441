package warGame.View;

import java.awt.Font;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import warGame.Model.*;

/**
 * This viewer allow users to manipulate the campaign loading in GUI
 * 
 */
@SuppressWarnings("serial")
public class WarGameCampaignLoadView extends JFrame implements Observer{
	private Map<String, WarGameCampaignModel> campaignsByMap;
	private WarGameCampaignModel campaignOnPage;
	private Map<String, WarGameMapModel> mapsByMap;
	private ArrayList<JLabel> mapsLbls;
	private ArrayList<String> mapsOnCampaign;

	 /**
     * Update the campaign's information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */
	@Override
	public void update(Observable o, Object arg) {
		try {
			campaignsByMap = WarGameCampaignModel.listAllCampaigns();
			mapsByMap = WarGameMapModel.listAllMaps();
			campaignOnPage = null;
			mapsOnCampaign = new ArrayList<String>();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		final JFrame frame = new JFrame();
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		frame.setResizable(false);
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		frame.setTitle("Campaigns");
		frame.setSize(500, 600);
		mapsLbls = new ArrayList<JLabel>();
		campaignOnPage = new WarGameCampaignModel();
		
		final JPanel manipulatePanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.setBounds(0, 40, 500, 600);
		frame.getContentPane().add(manipulatePanel);
		manipulatePanel.setLayout(null);
		
		JLabel lblCampaignName = new JLabel("Campaign name:");
		lblCampaignName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblCampaignName.setBounds(30, 70, 120, 30);
		manipulatePanel.add(lblCampaignName);
		
		final JComboBox<WarGameCampaignModel> campaignToChooseCbox = new JComboBox<WarGameCampaignModel>();
		campaignToChooseCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		campaignToChooseCbox.setBounds(150, 70, 100, 30);
		manipulatePanel.add(campaignToChooseCbox);
		for (String campaignID : campaignsByMap.keySet()) {
			WarGameCampaignModel campaignModelToChoose = new WarGameCampaignModel();
			campaignModelToChoose = campaignsByMap.get(campaignID);
			campaignToChooseCbox.addItem(campaignModelToChoose);
		}
		campaignToChooseCbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campaignOnPage = (WarGameCampaignModel) campaignToChooseCbox.getSelectedItem();
				mapsOnCampaign = campaignOnPage.getMapLists();
			}
		});
		
		final JPanel mapListPanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mapListPanel.setBounds(30, 160, 400, 300);
		manipulatePanel.add(mapListPanel);
		mapListPanel.setLayout(null);
		
		final JComboBox<String> mapPosCbox = new JComboBox<String>();
		mapPosCbox.setBounds(250, 120, 150, 30);
		mapPosCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.add(mapPosCbox);
		
		final JButton btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		btnAdd.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnAdd.setBounds(400, 120, 80, 30);
		manipulatePanel.add(btnAdd);
		
        final JButton btnAdd = new JButton("Delete");
        btnAdd.setEnabled(false);
        btnAdd.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
        btnAdd.setBounds(400, 120, 80, 30);
        manipulatePanel.add(btnAdd);
        
		final JButton btnLoad = new JButton("Load");
		btnLoad.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnLoad.setBounds(400, 70, 80, 30);
		manipulatePanel.add(btnLoad);
		
		final JComboBox<WarGameMapModel> mapToChooseCbox = new JComboBox<>();
		mapToChooseCbox.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		mapToChooseCbox.setBounds(150, 120, 100, 30);
		manipulatePanel.add(mapToChooseCbox);
		for (String mapID : mapsByMap.keySet()) {
			mapToChooseCbox.addItem(mapsByMap.get(mapID));
		}
		
		final JButton btnSave = new JButton("Save");
		btnSave.setEnabled(false);
		btnSave.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnSave.setBounds(180, 470, 120, 30);
		manipulatePanel.add(btnSave);
		
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < mapsOnCampaign.size(); i++) {
					JLabel mapsLbl = new JLabel();
					mapsLbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
					mapsLbl.setText(i+" "+mapsOnCampaign.get(i));
					mapsLbls.add(mapsLbl);
				}
				for (int i = 0; i < mapsLbls.size(); i++) {
					if (i<8) {
						mapsLbls.get(i).setBounds(30, i*30, 100, 30);
						mapListPanel.add(mapsLbls.get(i));
					}else if(i>=8&&i<16){
						mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
						mapListPanel.add(mapsLbls.get(i));
					}else if(i>=16){
						mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
						mapListPanel.add(mapsLbls.get(i));
					}
				}
				mapPosCbox.removeAllItems();
				for (JLabel lbl : mapsLbls) {
					mapPosCbox.addItem(lbl.getText());
				}
				mapPosCbox.addItem("Add end");

				
				mapListPanel.repaint();
				btnSave.setEnabled(true);
				btnLoad.setEnabled(false);
				btnAdd.setEnabled(true);
				
				for (final JLabel lbl : mapsLbls) {
					lbl.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getButton()==MouseEvent.BUTTON1) {
								String str[] = lbl.getText().split(" ");
								lbl.setText(str[0]+" "+mapToChooseCbox.getSelectedItem().toString());
								for (int i = 0; i < mapsLbls.size(); i++) {
									if (i<8) {
										mapsLbls.get(i).setBounds(30, i*30, 100, 30);
										mapListPanel.add(mapsLbls.get(i));
									}else if(i>=8&&i<16){
										mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
										mapListPanel.add(mapsLbls.get(i));
									}else if(i>=16){
										mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
										mapListPanel.add(mapsLbls.get(i));
									}
								}
								mapPosCbox.removeAllItems();
								for (JLabel lbl : mapsLbls) {
									mapPosCbox.addItem(lbl.getText());
								}
								mapPosCbox.addItem("Add end");
								mapListPanel.repaint();
							}
							if (e.getButton()==MouseEvent.BUTTON3) {
								
								mapsLbls.remove(lbl);
								mapListPanel.remove(lbl);
								for (int i = 0; i < mapsLbls.size(); i++) {
									if (i<8) {
										String str[] = mapsLbls.get(i).getText().split(" ");
										mapsLbls.get(i).setText(i+" "+str[1]);
										mapsLbls.get(i).setBounds(30, i*30, 100, 30);
										mapListPanel.add(mapsLbls.get(i));
									}else if(i>=8&&i<16){
										String str[] = mapsLbls.get(i).getText().split(" ");
										mapsLbls.get(i).setText(i+" "+str[1]);
										mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
										mapListPanel.add(mapsLbls.get(i));
									}else if(i>=16){
										String str[] = mapsLbls.get(i).getText().split(" ");
										mapsLbls.get(i).setText(i+" "+str[1]);
										mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
										mapListPanel.add(mapsLbls.get(i));
									}
								}
								mapPosCbox.removeAllItems();
								for (JLabel lbl : mapsLbls) {
									mapPosCbox.addItem(lbl.getText());
								}
								mapPosCbox.addItem("Add end");
								mapListPanel.repaint();
							}
						}
					});
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {					
				if (mapsOnCampaign.size()>=20) {
					JOptionPane.showMessageDialog(null, "No more than 20 maps");
				}else{
					if (mapPosCbox.getSelectedItem().toString().equals("Add end")) {
						JLabel mapsLbl = new JLabel();
						mapsLbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
						mapsLbl.setText(mapsLbls.size()+" "+mapToChooseCbox.getSelectedItem().toString());
						mapsLbls.add(mapsLbl);
					}else{
						String str[] = mapPosCbox.getSelectedItem().toString().split(" ");
						JLabel mapsLbl = new JLabel();
						mapsLbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
						mapsLbl.setText(str[0]+" "+mapToChooseCbox.getSelectedItem().toString());
						mapsLbls.add(Integer.parseInt(str[0]), mapsLbl);
					}
					for (int i = 0; i < mapsLbls.size(); i++) {
						if (i<8) {
							String str1[] = mapsLbls.get(i).getText().split(" ");
							mapsLbls.get(i).setText(i+" "+str1[1]);
							mapsLbls.get(i).setBounds(30, i*30, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else if(i>=8&&i<16){
							String str1[] = mapsLbls.get(i).getText().split(" ");
							mapsLbls.get(i).setText(i+" "+str1[1]);
							mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else if(i>=16&&i<20){
							String str1[] = mapsLbls.get(i).getText().split(" ");
							mapsLbls.get(i).setText(i+" "+str1[1]);
							mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else{
						}
					}
					mapPosCbox.removeAllItems();
					for (JLabel lbl : mapsLbls) {
						mapPosCbox.addItem(lbl.getText());
					}
					mapPosCbox.addItem("Add end");
					mapListPanel.repaint();
					btnSave.setEnabled(true);
					for (final JLabel lbl : mapsLbls) {
						lbl.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								if (e.getButton()==MouseEvent.BUTTON1) {
									String str[] = lbl.getText().split(" ");
									lbl.setText(str[0]+" "+mapToChooseCbox.getSelectedItem().toString());
									for (int i = 0; i < mapsLbls.size(); i++) {
										if (i<8) {
											mapsLbls.get(i).setBounds(30, i*30, 100, 30);
											mapListPanel.add(mapsLbls.get(i));
										}else if(i>=8&&i<16){
											mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
											mapListPanel.add(mapsLbls.get(i));
										}else if(i>=16){
											mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
											mapListPanel.add(mapsLbls.get(i));
										}
									}
									mapPosCbox.removeAllItems();
									for (JLabel lbl : mapsLbls) {
										mapPosCbox.addItem(lbl.getText());
									}
									mapPosCbox.addItem("Add end");
									mapListPanel.repaint();
								}
								if (e.getButton()==MouseEvent.BUTTON3) {
									mapsLbls.remove(lbl);
									mapListPanel.remove(lbl);
									for (int i = 0; i < mapsLbls.size(); i++) {
										if (i<8) {
											String str[] = mapsLbls.get(i).getText().split(" ");
											mapsLbls.get(i).setText(i+" "+str[1]);
											mapsLbls.get(i).setBounds(30, i*30, 100, 30);
											mapListPanel.add(mapsLbls.get(i));
										}else if(i>=8&&i<16){
											String str[] = mapsLbls.get(i).getText().split(" ");
											mapsLbls.get(i).setText(i+" "+str[1]);
											mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
											mapListPanel.add(mapsLbls.get(i));
										}else if(i>=16){
											String str[] = mapsLbls.get(i).getText().split(" ");
											mapsLbls.get(i).setText(i+" "+str[1]);
											mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
											mapListPanel.add(mapsLbls.get(i));
										}
									}
									mapPosCbox.removeAllItems();
									for (JLabel lbl : mapsLbls) {
										mapPosCbox.addItem(lbl.getText());
									}
									mapPosCbox.addItem("Add end");
									mapListPanel.repaint();
								}
							}
						});
					}
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapsOnCampaign.clear();
				try {
					
					for (JLabel lbl : mapsLbls) {
						String arr[] = lbl.getText().split(" ");
						mapsOnCampaign.add(arr[1]);
					}
					campaignOnPage.setMapLists(mapsOnCampaign);
					campaignOnPage.saveCampaign(campaignOnPage);
					btnSave.setEnabled(false);
					btnLoad.setEnabled(true);
					btnAdd.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Save successfully");
				frame.dispose();
			}
		});
		
		JLabel lblMapName = new JLabel("Map name:");
		lblMapName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblMapName.setBounds(30, 120, 120, 30);
		manipulatePanel.add(lblMapName);
	}
}
