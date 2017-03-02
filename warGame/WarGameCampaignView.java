package warGame;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class WarGameCampaignView extends JFrame implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		String campaignSe[] = ((WarGameCampaignModel) o).getCampaignSe();
		String graph = new String();
		for(int i=0;i<campaignSe.length;i++)
		{
			if(i == 0)
			{
				graph = campaignSe[0];
			}
			else
			{
				graph = graph + "--->" + campaignSe[i];
			}
		}
		JFrame frame = new JFrame("Campaign Create Success");
		frame.setBounds(300, 300, 300, 320);
		frame.setSize(300,320);
		//frame.setLocation(0,0);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(null);
		JLabel label_title = new JLabel("Campaign Graph");
		label_title.setBounds(new Rectangle(100, 0, 150, 30));
		frame.add(label_title);
		JTextArea text_sequence = new JTextArea(graph,50,100);
		text_sequence.setBounds(new Rectangle(0, 50, 300, 100));
		text_sequence.setLineWrap(true); //change line
		text_sequence.setWrapStyleWord(true);
		text_sequence.setEditable(false);
		text_sequence.setBackground(Color.lightGray);
		frame.add(text_sequence);
		//create save and cancel button
		JButton button_save = new JButton("Save");
		button_save.setBounds(new Rectangle(30,260,100,30));
		frame.add(button_save);
		JButton button_cancel = new JButton("Cancel");
		button_cancel.setBounds(new Rectangle(170,260,100,30));
		frame.add(button_cancel);
	}

}
