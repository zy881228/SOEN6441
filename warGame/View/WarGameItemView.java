package warGame.View;

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import warGame.Model.WarGameItemModel;


/**
 * This viewer allow users to manipulate the item information in GUI
 *
 */

public class WarGameItemView extends JFrame implements Observer{

	WarGameItemModel itemModel;
	
	/**
     * Update the item's information according to the value that get from Model and show the view frame.
     * @param o
     * @param arg
     */
	@Override
	public void update(final Observable o, Object arg) {
		// TODO Auto-generated method stub
		int viewType = ((WarGameItemModel) o).getViewType();
		switch(viewType)
		{
			case 1:
				String itemType = ((WarGameItemModel) o).getItemType();
				String enchanType = ((WarGameItemModel) o).getEnchanType();
				String enchanNumber = ((WarGameItemModel) o).getEnchanNumber();
				final JFrame frame = new JFrame(itemType);
				frame.setBounds(300, 300, 300, 320);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
				frame.setLayout(null);
				JLabel j1 = new JLabel();
				ImageIcon img = new ImageIcon("src/image/Item/"+itemType+"/"+enchanType+".jpeg");
				j1.setIcon(img);
				frame.add(j1);
				j1.setBounds(127, 80, 66, 66);
				JLabel label_result = new JLabel("Item:"+itemType+" "+enchanType+" +"+enchanNumber);
				label_result.setBounds(new Rectangle(80, 160, 200, 30));
				frame.add(label_result);
				
				JButton button_save = new JButton("Save");
				button_save.setBounds(new Rectangle(30,260,100,30));
				frame.add(button_save);
				button_save.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
		                  try {
		                	itemModel = new WarGameItemModel((WarGameItemModel) o);
		                	Boolean result = ((WarGameItemModel) o).saveItemJson(itemModel);
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
				button_cancel.setBounds(new Rectangle(170,260,100,30));
				frame.add(button_cancel);
				break;
				
			case 2:
				itemType = ((WarGameItemModel) o).getItemType();
				enchanType = ((WarGameItemModel) o).getEnchanType();
				enchanNumber = ((WarGameItemModel) o).getEnchanNumber();
				final JFrame frameCase2 = new JFrame(itemType);
				frameCase2.setBounds(0, 0, 700, 500);
				frameCase2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frameCase2.setVisible(true);
				frameCase2.setLayout(null);
				j1 = new JLabel();
				img = new ImageIcon("src/image/Item/"+itemType+"/"+enchanType+".jpeg");
				j1.setIcon(img);
				frameCase2.add(j1);
				j1.setBounds(100, 50, 66, 66);
				label_result = new JLabel("Item:"+itemType+" "+enchanType+" +"+enchanNumber);
				label_result.setBounds(new Rectangle(80, 130, 300, 30));
				frameCase2.add(label_result);
				final String itemType_selec = itemType;
				
				JButton button_edit = new JButton("Edit");
				button_edit.setBounds(400, 200, 100, 30);
				frameCase2.add(button_edit);
				
				JLabel label_enchanType = new JLabel("Enchantment Type:");
				label_enchanType.setBounds(new Rectangle(400, 50, 150, 30));
				frameCase2.add(label_enchanType);
				JLabel label_enchanNumber = new JLabel("Enchantment Number:");
				label_enchanNumber.setBounds(new Rectangle(400, 100, 150, 30));
				frameCase2.add(label_enchanNumber);
				final JComboBox cbox_enchanType = new JComboBox();
				cbox_enchanType.setBounds(550, 50, 150, 30);
				frameCase2.add(cbox_enchanType);
				if(itemType_selec.equals("Helmet"))
				{
					cbox_enchanType.addItem("Intelligence");
					cbox_enchanType.addItem("Wisdom");
					cbox_enchanType.addItem("Armor_class");
					cbox_enchanType.setEnabled(true);							
				}
				if(itemType_selec.equals("Armor"))
				{
					cbox_enchanType.addItem("Armor_class");
					cbox_enchanType.setEnabled(true);
				}
				if(itemType_selec.equals("Shield"))
				{
					cbox_enchanType.addItem("Armor_class");
					cbox_enchanType.setEnabled(true);
				}
				if(itemType_selec.equals("Ring"))
				{
					cbox_enchanType.addItem("Armor_class");
					cbox_enchanType.addItem("Strength");
					cbox_enchanType.addItem("Constitution");
					cbox_enchanType.addItem("Wisdom");
					cbox_enchanType.addItem("Charisma");
					cbox_enchanType.setEnabled(true);
				}
				if(itemType_selec.equals("Belt"))
				{
					cbox_enchanType.addItem("Constitution");
					cbox_enchanType.addItem("Strength");
					cbox_enchanType.setEnabled(true);
				}
				if(itemType_selec.equals("Boots"))
				{
					cbox_enchanType.addItem("Dexterity");
					cbox_enchanType.addItem("Armor_class");
					cbox_enchanType.setEnabled(true);
				}
				if(itemType_selec.equals("Weapon"))
				{
					cbox_enchanType.addItem("Attack_bonus");
					cbox_enchanType.addItem("Damage_bonus");
					cbox_enchanType.setEnabled(true);
				}
				final JComboBox cbox_enchanNum = new JComboBox();
				cbox_enchanNum.setBounds(550, 100, 150, 30);
				frameCase2.add(cbox_enchanNum);
				cbox_enchanNum.addItem("1");
				cbox_enchanNum.addItem("2");
				cbox_enchanNum.addItem("3");
				cbox_enchanNum.addItem("4");
				cbox_enchanNum.addItem("5");
				button_edit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String enchanType = cbox_enchanType.getSelectedItem().toString();
						String enchanNum = cbox_enchanNum.getSelectedItem().toString();
						String itemID = ((WarGameItemModel) o).getItemID();
						try {
							Boolean result = ((WarGameItemModel) o).editItemJson(itemID, itemType_selec, enchanType, enchanNum);
							if(result == true)
							{
								frameCase2.dispose();
								((WarGameItemModel) o).loadItemJson(itemID);
							}
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
		}//switch
	}//update
	

	
}
