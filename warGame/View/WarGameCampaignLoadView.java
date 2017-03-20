package warGame.View;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

@SuppressWarnings("serial")
public class WarGameCampaignLoadView extends JFrame implements Observer{
	private Map<String, WarGameCampaignModel> campaignsByMap;
	private WarGameCampaignModel campaignOnPage;
	private Map<String, WarGameMapModel> mapsByMap;
	private ArrayList<JLabel> mapsLbls;
	private ArrayList<String> mapsOnCampaign;
	
	public WarGameCampaignLoadView() {
		try {
			campaignsByMap = WarGameCampaignModel.listAllCampaigns();
			mapsByMap = WarGameMapModel.listAllMaps();
			campaignOnPage = null;
			mapsOnCampaign = new ArrayList<String>();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/image/Map/hero.jpg"));
		setResizable(false);
		setLocationByPlatform(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Campaigns");
		setSize(500, 600);
		mapsLbls = new ArrayList<JLabel>();
		campaignOnPage = new WarGameCampaignModel();
		
		final JPanel manipulatePanel = new JPanel();
		manipulatePanel.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		manipulatePanel.setBounds(0, 40, 500, 600);
		getContentPane().add(manipulatePanel);
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
		
		final JButton btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		btnAdd.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnAdd.setBounds(300, 120, 80, 30);
		manipulatePanel.add(btnAdd);
		
		final JButton btnRemove = new JButton("Remove");
		btnRemove.setEnabled(false);
		btnRemove.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnRemove.setBounds(380, 120, 80, 30);
		manipulatePanel.add(btnRemove);
		
		final JButton btnLoad = new JButton("Load");
		btnLoad.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		btnLoad.setBounds(300, 70, 80, 30);
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
		
		btnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mapsLbls.remove(mapsLbls.size()-1);
				mapsOnCampaign.remove(mapsLbls.size()-1);
				mapListPanel.removeAll();
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
				mapListPanel.repaint();
			}
		});
		
		btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < mapsOnCampaign.size(); i++) {
					JLabel mapsLbl = new JLabel();
					mapsLbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
					mapsLbl.setText(mapsOnCampaign.get(i));
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
				mapListPanel.repaint();
				btnSave.setEnabled(true);
				btnLoad.setEnabled(false);
				btnAdd.setEnabled(true);
				btnRemove.setEnabled(true);
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				if (mapsOnCampaign.size()>=20) {
					JOptionPane.showMessageDialog(null, "No more than 20 maps");
				}else{
					JLabel mapsLbl = new JLabel();
					mapsLbl.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
					mapsLbl.setText(mapToChooseCbox.getSelectedItem().toString());
					mapsLbls.add(mapsLbl);
					mapsOnCampaign.add(mapToChooseCbox.getSelectedItem().toString());
					for (int i = 0; i < mapsLbls.size(); i++) {
						if (i<8) {
							mapsLbls.get(i).setBounds(30, i*30, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else if(i>=8&&i<16){
							mapsLbls.get(i).setBounds(130, (i*30)-240, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else if(i>=16&&i<20){
							mapsLbls.get(i).setBounds(230, (i*30)-480, 100, 30);
							mapListPanel.add(mapsLbls.get(i));
						}else{
						}
					}
					mapListPanel.repaint();
					btnSave.setEnabled(true);
				}
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					campaignOnPage.setMapLists(mapsOnCampaign);
					campaignOnPage.saveCampaign(campaignOnPage);
					btnSave.setEnabled(false);
					btnLoad.setEnabled(true);
					btnAdd.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		JLabel lblMapName = new JLabel("Map name:");
		lblMapName.setFont(new Font("Simplified Arabic", Font.PLAIN, 15));
		lblMapName.setBounds(30, 120, 120, 30);
		manipulatePanel.add(lblMapName);
		

		

		

	}

	@Override
	public void update(Observable o, Object arg) {
	}
}
