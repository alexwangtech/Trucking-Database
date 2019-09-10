/**
 * a very horribly-made imitation of a table
 * to be used temporarily, best replaced as soon as possible
 * from left to right, its components are delete button, item combo box,
 * description text field, quantity text field, rate text field, and amount text field
 * that should automatically adjust itself based on the values of quantity and rate. This table
 * expands and shrinks by using ArrayLists placed in a JScrollPane. Once more experience with creating
 * custom JTables is gained, a new class will be written to replace this current class.
 */

package badStructures;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// top panel of the table. consists of the labels delete, item, description, quantity, rate, and amount
@SuppressWarnings("serial")
class TopPanel extends JPanel
{
	GridBagConstraints c;
	JLabel del, item, descrip, quant, rate, amount;
	
	public TopPanel()
	{
		del = new JLabel("Del");											// delete label
		del.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));		// delete label border
		
		item = new JLabel("Item");											// item label
		item.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 80));		// item label border
		
		descrip = new JLabel("Description");								// description label
		descrip.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));	// description label border
		
		quant = new JLabel("Quantity");										// quantity label
		quant.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 10));		// quantity label border
		
		rate = new JLabel("Rate");											// rate label
		rate.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));		// rate label border
		
		amount = new JLabel("Amount");										// amount label
		amount.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));		// amount label border
		
		// add the components to (this) using GridBagLayout
		this.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; this.add(del);		// delete label
		c.gridx = 1; c.gridy = 0; this.add(item);		// item label
		c.gridx = 2; c.gridy = 0; this.add(descrip);	// description label
		c.gridx = 3; c.gridy = 0; this.add(quant);		// quantity label
		c.gridx = 4; c.gridy = 0; this.add(rate);		// rate label
		c.gridx = 5; c.gridy = 0; this.add(amount);		// amount label
		
		// temporary: add a red border around this panel
		this.setBorder(BorderFactory.createLineBorder(Color.red));
	}
}

// the complete bottom panel. consists of a list of PartialRows and their corresponding delete buttons
@SuppressWarnings("serial")
class BotPanel extends JPanel
{
	GridBagConstraints c;
	JScrollPane scrollPane;
	JPanel panel;
	ArrayList<JButton> buttonList;
	ArrayList<PartialRow> rowList;
	
	public BotPanel()
	{
		buttonList = new ArrayList<JButton>();		// ArrayList for delete buttons
		rowList = new ArrayList<PartialRow>();		// ArrayList for PartialRow objects
		
		// start off the panel with one button and one PartialRow object
		buttonList.add(new JButton("X"));
		rowList.add(new PartialRow());
		
		// give this button the capability to delete itself and its corresponding PartialRow object
		buttonList.get(0).addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				rowList.remove(0);
				buttonList.remove(0);
				update();	// in-class update method
			}
		});
		
		// creating a starting display with GridBagLayout
		panel = new JPanel(); panel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; panel.add(buttonList.get(0));
		c.gridx = 1; c.gridy = 0; panel.add(rowList.get(0));
		
		scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(525, 75));
		this.add(scrollPane);
	}
	
	// method: adds a new button to 'buttonList' and a new PartialRow object to 'rowList'
	public void addRow()
	{
		// add a button and PartialRow to respective lists
		JButton button = new JButton("X");
		PartialRow partialRow = new PartialRow();
		
		buttonList.add(button);
		rowList.add(partialRow);
		
		// ActionListener for the new button
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// find the index of current button in buttonList
				int index = buttonList.indexOf(button);

				// remove the current button and its corresponding PartialRow object in 'rowList'
				buttonList.remove(index);
				rowList.remove(index);
				
				update();	// in-class update method
			}
		});
		
		// update the look of the table
		update();
	}
	
	// method: updates the display. should be called whenever there is a new row added or a delete button is pressed.
	public void update()
	{
		// remove everything from the panel
		panel.removeAll();
		
		// build the panel up from scratch using GridBagLayout
		panel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0;
		
		// use a 'for' loop to iterate through the items of 'buttonList' and 'rowList'.
		// since they are equal length, using the length of one of them is fine.
		for (int i = 0; i < buttonList.size(); i++)
		{
			panel.add(buttonList.get(i), c);
			c.gridx = 1;
			panel.add(rowList.get(i), c);
			c.gridx = 0; c.gridy++;
		}
		
		// refresh the panel
		panel.revalidate();
		panel.repaint();
	}
	
	// method: calls the 'callSetterMethods()' for each object in 'rowList'.
	public void finalSave()
	{
		for (PartialRow object : rowList)
			object.callSetterMethods();
	}
	
	// method: returns 'rowList', an ArrayList<PartialRow> object.
	public ArrayList<PartialRow> getRowList() { return rowList; }
}

// the complete table. contains all of the above components, plus a '+' button that will call the addRow() method from BotPanel.
@SuppressWarnings("serial")
public class LoadTable extends JPanel
{
	GridBagConstraints c;
	JButton plusButton;
	BotPanel botPanel;
	
	public LoadTable()
	{
		// call an instance of TopPanel and BotPanel
		TopPanel topPanel = new TopPanel();
		botPanel = new BotPanel();
		
		plusButton = new JButton("+");
		
		// ActionListener for the '+' button: calls the addRow() method from 'botPanel'.
		plusButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				botPanel.addRow();
			}
		});
		
		// add the components to the panel (this)
		this.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; this.add(topPanel, c);	// top panel
		c.gridx = 0; c.gridy = 1; this.add(botPanel, c);	// bot panel
		c.gridheight = 3;
		c.gridx = 1; c.gridy = 0; this.add(plusButton);		// "+" button
		
		// temporary: add a blue border around the entire panel
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	}
	
	// method: calls the 'finalSave()' method from the BotPanel() class.
	public void callFinalSave()
	{
		botPanel.finalSave();
	}
	
	// method: returns the ArrayList<PartialRow> of the BotPanel object
	public ArrayList<PartialRow> getPartialRowList() { return botPanel.getRowList(); }
	
	// method: takes in a String[][] and sets up the table in the corresponding manner.
	public void setupTable(String[][] inputValues)
	{
		for (int i = 0; i < inputValues.length; i++)
		{
			int currentIndex = botPanel.rowList.size() - 1;
			
			botPanel.rowList.get(currentIndex).inputItem(inputValues[i][0]);
			botPanel.rowList.get(currentIndex).inputDescription(inputValues[i][1]);
			botPanel.rowList.get(currentIndex).inputQuantity(inputValues[i][2]);
			botPanel.rowList.get(currentIndex).inputRate(inputValues[i][3]);
			botPanel.rowList.get(currentIndex).inputAmount(inputValues[i][4]);
			
			if (i != inputValues.length - 1) botPanel.addRow();
		}
	}

	// main method: temporary run method
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				LoadTable table = new LoadTable();
				frame.add(table);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}