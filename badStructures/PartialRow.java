package badStructures;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

//a row object. does not consist of the entire row, but rather, contains information entered by the user.
//this class is to be paired with a button in BotPanel class
@SuppressWarnings("serial")
public class PartialRow extends JPanel
{
	GridBagConstraints c;
	JTextField descripTF, quantityTF, rateTF, amountTF;
	String itemInfo, descripInfo, quantityInfo, rateInfo, amountInfo;
	JComboBox<String> itemCB;
	String[] itemCBOptions = {"Freight Charges", "Lumper Charge", "Detention @ Shipper",
							  "Detention @ Consignee", "Miscellaneous", "Defection", "Advance"};
	
	public PartialRow()
	{
		itemCB = new JComboBox<String>(itemCBOptions);	// item combo box
		descripTF = new JTextField(10);					// description text field
		quantityTF = new JTextField(10);				// quantity text field
		rateTF = new JTextField(5);						// rate text field
		amountTF = new JTextField(5);					// amount text field
		
		// add everything to (this) using GridBagLayout
		this.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; this.add(itemCB, c);
		c.gridx = 1; c.gridy = 0; this.add(descripTF, c);
		c.gridx = 2; c.gridy = 0; this.add(quantityTF, c);
		c.gridx = 3; c.gridy = 0; this.add(rateTF, c);
		c.gridx = 4; c.gridy = 0; this.add(amountTF, c);
	}
	
	// method: calls all of the setter methods, which retrieves the info from the text fields.
	public void callSetterMethods()
	{
		setItem();
		setDescription();
		setQuantity();
		setRate();
		setAmount();
	}
	
	// input methods: allows manual input/setting of the values of the members.
	public void inputItem(String itemInput) { itemInfo = itemInput; }
	public void inputDescription(String descriptionInput) { descripInfo = descriptionInput; }
	public void inputQuantity(String quantityInput) { quantityInfo = quantityInput; }
	public void inputRate(String rateInput) { rateInfo = rateInput; }
	public void inputAmount(String amountInput) { amountInfo = amountInput; }
	
	// setter methods: gets the info from the text fields and stores it in the variables.
	private void setItem() { itemInfo = (String)itemCB.getSelectedItem(); }
	private void setDescription() { descripInfo = descripTF.getText(); }
	private void setQuantity() { quantityInfo = quantityTF.getText(); }
	private void setRate() { rateInfo = rateTF.getText(); }
	private void setAmount() { amountInfo = amountTF.getText(); }
	
	// getter methods: returns the info stored in the info variables.
	public String getItem() { return itemInfo; }
	public String getDescription() { return descripInfo; }
	public String getQuantity() { return quantityInfo; }
	public String getRate() { return rateInfo; }
	public String getAmount() { return amountInfo; }
}
