// invoice panel of the application

package panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class InvoicePanel extends JPanel
{
	GridBagConstraints c;
	JPanel topPanel, botPanel, topUpperPanel, botUpperPanel;
	JButton topUpdateButton, botUpdateButton, topSearchButton, botSearchButton;
	JTextField topSearchTextField, botSearchTextField;
	JLabel createInvoiceLabel, receiveInvoiceLabel;
	
	public InvoicePanel()
	{
		// initialized components for 'topUpperPanel', the upper panel of 'topPanel'
		createInvoiceLabel = new JLabel("Create Invoice");	// 'Create Invoice' label
		topUpdateButton = new JButton("Update");			// 'Update' button
		topSearchTextField = new JTextField(10);			// text field for the 'Search' button
		topSearchButton = new JButton("Search");			// 'Search' button
		
		
		
		// initialized components for 'botUpperPanel', the upper panel of 'botPanel'.
		receiveInvoiceLabel = new JLabel("Receive Invoice/POD");	// 'Receive Invoice/POD' label
		botUpdateButton = new JButton("Update");					// 'Update' button
		botSearchTextField = new JTextField(10);					// text field for the 'Search' button
		botSearchButton = new JButton("Search");					// 'Search' button
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				InvoicePanel panelInstance = new InvoicePanel();
				
				JFrame frame = new JFrame("Temporary Frame");
				frame.add(panelInstance);
				
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}