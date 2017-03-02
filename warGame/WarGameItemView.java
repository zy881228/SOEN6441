package warGame;

import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class WarGameItemView extends JFrame implements Observer{

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
				JFrame frame = new JFrame(itemType);
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
				
				//create save and cancel button
				int id = 0;
				try {
					 id = ((WarGameItemModel) o).getItemID();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				id = id+1;
				final String message = id+" "+itemType+" "+enchanType+" "+enchanNumber+"\r\n";
				JButton button_save = new JButton("Save");
				button_save.setBounds(new Rectangle(30,260,100,30));
				frame.add(button_save);
				button_save.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
		                  try {
							Boolean result = ((WarGameItemModel) o).saveItem(message);
							if(result == true)
							{
								JFrame frame = new JFrame("Save");
								frame.setBounds(300, 200, 200, 100);
								frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								frame.setVisible(true);
								frame.setLayout(null);
								JLabel label_result = new JLabel("Save Item Success!");
								label_result.setBounds(20, 25, 200, 30);
								frame.add(label_result);
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
				frame = new JFrame(itemType);
				frame.setBounds(300, 300, 300, 320);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
				frame.setLayout(null);
				j1 = new JLabel();
				img = new ImageIcon("src/image/Item/"+itemType+"/"+enchanType+".jpeg");
				j1.setIcon(img);
				frame.add(j1);
				j1.setBounds(127, 80, 66, 66);
				label_result = new JLabel("Item:"+itemType+" "+enchanType+" +"+enchanNumber);
				label_result.setBounds(new Rectangle(80, 160, 300, 30));
				frame.add(label_result);
				
		}//switch
	}//update
	

	
}
