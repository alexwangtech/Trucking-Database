/**
 * template for creating a new company
 * when "new company" button is pressed from the main window,
 * this template will pop up as a new window
 * after the user is done, the data will be entered into an SQL database table
 */

package windows;

import contacts.*;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* this template will consist of three main panels on a single panel
 * each of these three panels will consist of three smaller panels
 * there will be one more small panel on the very top
 * one more panel for payable and receivable buttons
 * in total, 3 x 3 + 1 + 1 + 1 = 12 panels in total
 * 
 * topmost panel consists of the save button and the check boxes for company type
 * leftmost panel consists of company information, address, and contacts
 * middle panel consists of payable and receivables, as well as address and contacts
 * rightmost panel consists of company insurance, as well as contacts
 * 
 * after the layout is finished, action listeners must be implemented, as well as a connection
 * to the sql database, for data inputing. data will have to be organized into tables that will
 * allow for consistent retrieving.
 */

public class CompanyMaker {
	GridBagConstraints c;
	JFrame mainFrame;
	JPanel mainPanel, ultraMainPanel, tpMstPnl, x0y0Panel, x0y1Panel, x0y2Panel, lftMdPnl, x1y0Pnl, x1y1Pnl, x1y2Pnl, x1y3Pnl, midPnl, trcPnl, brcPnl, rightMdPnl;
	JButton saveBtn, x0y2prevBtn, x0y2nxtBtn, x0y2nwBtn, x0y2delBtn, pyblBtn, rcvblBtn;
	Checkbox carrierType, brokerType, customerType, shipperType, consigneeType, insAgencyType, factoringType, myFactoringType, vendorType, ownerOperatorType, otherType;
	JLabel nameLbl, nameLbl1, DBALbl, telLbl, taxIDLbl, faxLbl, faxLbl1, mcLbl, establishedLbl, addressLbl, cityLbl, stateLbl, zipLbl, cellLbl, phoneLbl, emailLbl;
	JTextField x0y0Name, x0y0DBA, x0y0Tel, x0y0TaxID, x0y0Fax, x0y0MC, x0y0Established, x1y1cltf;
	JTextField x0y1ad1tf, x0y1ad2tf, x0y1ctf, x0y1stf, x0y1ztf;
	JTextField x0y2ntf, x0y2ctf, x0y2ptf, x0y2ftf, x0y2etf;
	JLabel crdtLnLbl, pmtTrmLbl, fctrLbl;
	@SuppressWarnings("rawtypes")
	JComboBox x1y1ptcb, x1y1fcb;
	String[] x1y1ptcbl = {"NET 30"};
	String[] x1y1fcbl = {"None"};
	JLabel addressLbl1, cityLbl1, stateLbl1, zipLbl1;
	JTextField x1y2a1tf, x1y2a2tf, x1y2ctf, x1y2stf, x1y2ztf;
	JLabel nameLbl2, cellLbl1, phoneLbl1, faxLbl2, emailLbl1;
	JTextField x1y3ntf, x1y3ctf, x1y3ptf, x1y3ftf, x1y3etf;
	JButton x1y3prevBtn, x1y3nxtBtn, x1y3nwBtn, x1y3delBtn;
	JLabel trcdotlbl, trcscaclbl, trcratinglbl, trcinsagencylbl, trcpolicylbl, trccoveragelbl, trcexpirationlbl;
	JLabel trcgenerallbl, trcautolbl, trccargolbl, trctgdlbl;
	JTextField trcdottf, trcscactf, trcgptf, trcgctf, trcgetf, trcaptf, trcactf, trcaetf, trccptf, trccctf, trccetf, trctgdtf;
	@SuppressWarnings("rawtypes")
	JComboBox trcrcb, trccccb, trciacb;
	String[] trcrcbl = {"Select One"};
	String[] trccccbl = {"Contact", "Common"};
	String[] trciacbl = {"Select One"};
	JButton trcnicb;
	JLabel brcnamelbl, brccelllbl, brcphonelbl, brcfaxlbl, brcemaillbl;
	JTextField brcntf, brcctf, brcptf, brcftf, brcetf;
	JButton brcprevbtn, brcnxtbtn, brcnewbtn, brcdelbtn;
	
	ArrayList<CompanyContact> companyContactList;
	ArrayList<PayableContact> payableContactList;
	ArrayList<ReceivableContact> receivableContactList;
	ArrayList<InsuranceContact> insuranceContactList;
	
	int companyIndex, payableIndex, receivableIndex, insuranceIndex;
	boolean midPanelMode;
	
	PayableCompany payableCompany;
	ReceivableCompany receivableCompany;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompanyMaker() {
		// initialize ArrayLists that will be used for temporary storage
		companyContactList = new ArrayList<CompanyContact>();
		payableContactList = new ArrayList<PayableContact>();
		receivableContactList = new ArrayList<ReceivableContact>();
		insuranceContactList = new ArrayList<InsuranceContact>();
		
		// used to keep track of where the array list "pointer" should be
		companyIndex = 0;
		payableIndex = 0;
		receivableIndex = 0;
		insuranceIndex = 0;
		
		// true means payable mode, false means receivable mode (default is payable)
		midPanelMode = true;
		
		// initialize PayableCompany and ReceivableCompany objects, everything no value
		payableCompany = new PayableCompany();
		receivableCompany = new ReceivableCompany();
		payableCompany.everythingEmpty();
		receivableCompany.everythingEmpty();
		
		/* create "global glossary" for labels
		 * reduces messiness and makes it easier to find labels
		 * multiples of same labels will be declared underneath
		 * the previous one
		 */
		nameLbl = new JLabel("Name: ");					// name
		nameLbl1 = new JLabel("Name: ");
		nameLbl2 = new JLabel("Name: ");
		DBALbl = new JLabel("DBA: ");					// DBA
		telLbl = new JLabel("Tel: ");					// tel
		taxIDLbl = new JLabel("Tax ID: ");				// tax id
		faxLbl = new JLabel("Fax: ");					// fax
		faxLbl1 = new JLabel("Fax: ");
		faxLbl2 = new JLabel("Fax: ");
		mcLbl = new JLabel("MC#: ");					// mc#
		establishedLbl = new JLabel("Established: ");	// established
		addressLbl = new JLabel("Address: ");			// address
		addressLbl1 = new JLabel("Address: ");
		cityLbl = new JLabel("City: ");					// city
		cityLbl1 = new JLabel("City: ");
		stateLbl = new JLabel("State: ");				// state
		stateLbl1 = new JLabel("State: ");
		zipLbl = new JLabel("Zip: ");					// zip
		zipLbl1 = new JLabel("Zip: ");
		cellLbl = new JLabel("Cell: ");					// cell
		cellLbl1 = new JLabel("Cell: ");
		phoneLbl = new JLabel("Phone: ");				// phone
		phoneLbl1 = new JLabel("Phone: ");
		emailLbl = new JLabel("Email: ");				// email
		emailLbl1 = new JLabel("Email: ");
		crdtLnLbl = new JLabel("Credit Line: ");		// credit line
		pmtTrmLbl = new JLabel("Pmt. Term: ");			// pmt term
		fctrLbl = new JLabel("Factor: ");				// factor
		
		trcdotlbl = new JLabel("DOT#: ");						// dot
		trcscaclbl = new JLabel("SCAC: ");						// scac
		trcratinglbl = new JLabel("Rating: ");					// rating
		trcinsagencylbl = new JLabel("Ins. Agency: ");			// ins agency
		trcpolicylbl = new JLabel("Policy#: ");					// policy#
		trccoveragelbl = new JLabel("Coverage: ");				// coverage
		trcexpirationlbl = new JLabel("Expiration: ");			// expiration
		trcgenerallbl = new JLabel("General: ");				// general
		trcautolbl = new JLabel("Auto: ");						// auto
		trccargolbl = new JLabel("Cargo: ");					// cargo
		trctgdlbl = new JLabel("Target Goods Deductibles: ");	// target goods deductibles
		
		brcnamelbl = new JLabel("Name: ");		// name
		brccelllbl = new JLabel("Cell: ");		// cell
		brcphonelbl = new JLabel("Phone: ");	// phone
		brcfaxlbl = new JLabel("Fax: ");		// fax
		brcemaillbl = new JLabel("Email: ");	// email
		
		/* the top most panel of the template
		 * consists of the save button and the check boxes of different company types
		 */
		
		carrierType = new Checkbox("Carrier");				// carrier check box
		brokerType = new Checkbox("Broker");				// broker check box
		customerType = new Checkbox("Customer");			// customer check box
		shipperType = new Checkbox("Shipper");				// shipper check box
		consigneeType = new Checkbox("Consignee");			// consigneee check box
		insAgencyType = new Checkbox("Ins. Agency");		// insurance agency check box
		factoringType = new Checkbox("Factoring");			// factoring check box
		myFactoringType = new Checkbox("My Factoring");		// my factoring check box
		vendorType = new Checkbox("Vendor");				// vendor check box
		ownerOperatorType = new Checkbox("Owner/Operator");	// owner/operator check box
		otherType = new Checkbox("Other");					// other type check box
		
		saveBtn = new JButton("Save Company");				// save button
		
		// add the components to the topmost panel
		tpMstPnl = new JPanel();
		tpMstPnl.add(carrierType);
		tpMstPnl.add(brokerType);
		tpMstPnl.add(customerType);
		tpMstPnl.add(shipperType);
		tpMstPnl.add(consigneeType);
		tpMstPnl.add(insAgencyType);
		tpMstPnl.add(factoringType);
		tpMstPnl.add(myFactoringType);
		tpMstPnl.add(vendorType);
		tpMstPnl.add(ownerOperatorType);
		tpMstPnl.add(otherType);
		tpMstPnl.add(saveBtn);
		
		// action listener for save button
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// save the data by adding it to an SQL database
			}
		});
		
		/* the top panel of the leftmost panel
		 * consists of the company name, DBA, Tel, TaxID, Fax, MC#, Established
		 * requires unique corresponding jtextfields for each one of those labels
		 * text fields will start with 'x0y0' to indicate top left corner of the
		 * 9 different midsection panels
		 */
		x0y0Name = new JTextField(20);			// name text field
		x0y0DBA = new JTextField(10);			// dba text field
		x0y0Tel = new JTextField(10);			// tel text field
		x0y0TaxID = new JTextField(10);			// tax id text field
		x0y0Fax = new JTextField(10);			// fax text field
		x0y0MC = new JTextField(10);			// mc# text field
		x0y0Established = new JTextField(10);	// established date text field
		
		// use GridBagLayout to put the components onto the panel
		x0y0Panel = new JPanel(); x0y0Panel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; x0y0Panel.add(nameLbl, c);			// name label at (0, 0)
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 0; x0y0Panel.add(x0y0Name, c);			// name text field at (1, 0)
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		
		c.gridx = 0; c.gridy = 1; x0y0Panel.add(DBALbl, c);				// dba label at (0, 1)
		c.gridx = 1; c.gridy = 1; x0y0Panel.add(x0y0DBA, c);			// dba text field at (1, 1)
		c.gridx = 2; c.gridy = 1; x0y0Panel.add(telLbl, c);				// tel label at (2, 1)
		c.gridx = 3; c.gridy = 1; x0y0Panel.add(x0y0Tel, c);			// tel text field at (3, 1)
	
		c.gridx = 0; c.gridy = 2; x0y0Panel.add(taxIDLbl, c);			// tax id label at (0, 2)
		c.gridx = 1; c.gridy = 2; x0y0Panel.add(x0y0TaxID, c);			// tax id text field at (1, 2)
		c.gridx = 2; c.gridy = 2; x0y0Panel.add(faxLbl, c);				// fax label at (2, 2)
		c.gridx = 3; c.gridy = 2; x0y0Panel.add(x0y0Fax, c);			// fax text field at (3, 2)
		
		c.gridx = 0; c.gridy = 3; x0y0Panel.add(mcLbl, c);				// mc# label at (0, 3)
		c.gridx = 1; c.gridy = 3; x0y0Panel.add(x0y0MC, c);				// mc# text field at (1, 3)
		c.gridx = 2; c.gridy = 3; x0y0Panel.add(establishedLbl, c);		// established label at (2, 3)
		c.gridx = 3; c.gridy = 3; x0y0Panel.add(x0y0Established, c);	// established text field at (3, 3)
		
		// temporary
		x0y0Panel.setBorder(BorderFactory.createLineBorder(Color.red));
		
		/* the middle panel of the leftmost panel
		 * contains address, city, state, and zip
		 * contains 2 text fields for address, 1 for the rest
		 * this section will start with the name 'x0y1'
		 */
		x0y1ad1tf = new JTextField(20);		// address text field 1
		x0y1ad2tf = new JTextField(20);		// address text field 2
		x0y1ctf = new JTextField(20);		// city text field
		x0y1stf = new JTextField(10);		// state text field
		x0y1ztf = new JTextField(10);		// zip text field
		
		// using GridBagLayout, add the components to a panel
		x0y1Panel = new JPanel(); x0y1Panel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; x0y1Panel.add(addressLbl, c);		// address label at (0, 0)
		
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 0; x0y1Panel.add(x0y1ad1tf, c);		// address text field at (1, 0)
		c.gridx = 1; c.gridy = 1; x0y1Panel.add(x0y1ad2tf, c);		// address text field at (1, 1)
		
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 2; x0y1Panel.add(cityLbl, c);		// city label at (0, 2)
		
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 2; x0y1Panel.add(x0y1ctf, c);		// city text field at (1, 2)
		
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 3; x0y1Panel.add(stateLbl, c);		// state label at (0, 3)
		c.gridx = 1; c.gridy = 3; x0y1Panel.add(x0y1stf, c);		// state text field at (1, 3)
		c.gridx = 2; c.gridy = 3; x0y1Panel.add(zipLbl, c);			// zip label at (2, 3)
		c.gridx = 3; c.gridy = 3; x0y1Panel.add(x0y1ztf, c);		// zip text field at (3, 3)
		
		// temporary
		x0y1Panel.setBorder(BorderFactory.createLineBorder(Color.red));
		
		/* the bottom panel of the leftmost panel
		 * contains contact name, cell, phone, fax, and email
		 * also contains buttons "<", ">", "new", and "delete"
		 * for purposes of entering and deleting multiple contacts
		 * this section will start with the name 'x0y2'
		 */
		x0y2prevBtn = new JButton("<");		// previous contact button
		x0y2nxtBtn = new JButton(">");		// next contact button
		x0y2nwBtn = new JButton("New");		// new contact button
		x0y2delBtn = new JButton("Delete");	// delete contact button
		
		x0y2ntf = new JTextField(10);	// name text field
		x0y2ctf = new JTextField(10);	// cell text field
		x0y2ptf = new JTextField(10);	// phone text field
		x0y2ftf = new JTextField(10);	// fax text field
		x0y2etf = new JTextField(10);	// email text field
		
		// use GridBagLayout to add components to a panel
		x0y2Panel = new JPanel(); x0y2Panel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; x0y2Panel.add(x0y2prevBtn, c);	// "<" button at (0, 0)
		c.gridx = 1; c.gridy = 0; x0y2Panel.add(x0y2nxtBtn, c);		// ">" button at (1, 0)
		c.gridx = 2; c.gridy = 0; x0y2Panel.add(x0y2nwBtn, c);		// new button at (2, 0)
		c.gridx = 3; c.gridy = 0; x0y2Panel.add(x0y2delBtn, c);		// delete button at (3, 0)
		
		c.gridx = 0; c.gridy = 1; x0y2Panel.add(nameLbl1, c);		// name label at (0, 1)
		c.gridx = 1; c.gridy = 1; x0y2Panel.add(x0y2ntf, c);		// name text field at (1, 1)
		c.gridx = 2; c.gridy = 1; x0y2Panel.add(cellLbl, c);		// cell label at (2, 1)
		c.gridx = 3; c.gridy = 1; x0y2Panel.add(x0y2ctf, c);		// cell text field at (3, 1)
		
		c.gridx = 0; c.gridy = 2; x0y2Panel.add(phoneLbl, c);		// phone label at (0, 2)
		c.gridx = 1; c.gridy = 2; x0y2Panel.add(x0y2ptf, c);		// phone text field at (1, 2)
		c.gridx = 2; c.gridy = 2; x0y2Panel.add(faxLbl1, c);		// fax label at (2, 2)
		c.gridx = 3; c.gridy = 2; x0y2Panel.add(x0y2ftf, c);		// fax text field at (3, 2)

		c.gridx = 0; c.gridy = 3; x0y2Panel.add(emailLbl, c);		// email label at (0, 3)
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 3; x0y2Panel.add(x0y2etf, c);		// email text field at (1, 3)
		
		/* action listener for new button
		 * when user clicks on it, save current contact
		 * data to an arraylist of CompanyContact objects,
		 * which can be modified later by other objects
		 */
		x0y2nwBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the index is one that is not saved in array list, call the save method
				if (companyIndex == companyContactList.size()) {
					// make sure that at least one value is not null
					if ((x0y2ntf.getText().length() != 0) ||
						(x0y2ctf.getText().length() != 0) ||
						(x0y2ptf.getText().length() != 0) ||
						(x0y2ftf.getText().length() != 0) ||
						(x0y2etf.getText().length() != 0)) {
						saveObject(1);
					} else {
						return;
					}
				// however, if object already exists, update any changes that might have been made	
				} else {
					updateObject(1);
				}
				
				// hop the index to array list max index + 1
				companyIndex = companyContactList.size();
				
				// set all the text fields to null
				x0y2ntf.setText(null);
				x0y2ctf.setText(null);
				x0y2ptf.setText(null);
				x0y2ftf.setText(null);
				x0y2etf.setText(null);
			}
		});
		
		// action listener for delete button
		x0y2delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if object is not save in array list yet, simply clear the text fields
				if (companyIndex >= companyContactList.size()) {
					x0y2ntf.setText(null);
					x0y2ctf.setText(null);
					x0y2ptf.setText(null);
					x0y2ftf.setText(null);
					x0y2etf.setText(null);
					return;
				}
				
				// otherwise, delete the current CompanyContact object
				companyContactList.remove(companyIndex);
				
				// if array list is empty, reset everything to null
				if (companyContactList.size() == 0) {
					x0y2ntf.setText(null);
					x0y2ctf.setText(null);
					x0y2ptf.setText(null);
					x0y2ftf.setText(null);
					x0y2etf.setText(null);	
				} else {
					// if index is still within bounds, set to next object in list
					if (companyIndex <= companyContactList.size() - 1) {
						x0y2ntf.setText(companyContactList.get(companyIndex).getName());
						x0y2ctf.setText(companyContactList.get(companyIndex).getCell());
						x0y2ptf.setText(companyContactList.get(companyIndex).getPhone());
						x0y2ftf.setText(companyContactList.get(companyIndex).getFax());
						x0y2etf.setText(companyContactList.get(companyIndex).getEmail());
						
					// if index is out of bounds, move index one backwards and display values of that object
					} else {
						companyIndex--;
						x0y2ntf.setText(companyContactList.get(companyIndex).getName());
						x0y2ctf.setText(companyContactList.get(companyIndex).getCell());
						x0y2ptf.setText(companyContactList.get(companyIndex).getPhone());
						x0y2ftf.setText(companyContactList.get(companyIndex).getFax());
						x0y2etf.setText(companyContactList.get(companyIndex).getEmail());
					}
				}
			}
		});
		
		// action listener for the "<" button (previous button)
		x0y2prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the index is one that is not saved in array list, call the save method
				if (companyIndex == companyContactList.size()) {
					// make sure that at least one value is not null
					if ((x0y2ntf.getText().length() != 0) ||
						(x0y2ctf.getText().length() != 0) ||
						(x0y2ptf.getText().length() != 0) ||
						(x0y2ftf.getText().length() != 0) ||
						(x0y2etf.getText().length() != 0)) {
						saveObject(1);
					}
				// however, if object already exists, update any changes that might have been made	
				} else {
					updateObject(1);
				}
				
				// simply return if the index is already 0, or array list is empty
				if ((companyIndex == 0) || (companyContactList.size() == 0)) {
					return;
				} else {
					// otherwise, set index to one before and display that object's values
					companyIndex--;
					x0y2ntf.setText(companyContactList.get(companyIndex).getName());
					x0y2ctf.setText(companyContactList.get(companyIndex).getCell());
					x0y2ptf.setText(companyContactList.get(companyIndex).getPhone());
					x0y2ftf.setText(companyContactList.get(companyIndex).getFax());
					x0y2etf.setText(companyContactList.get(companyIndex).getEmail());
				}
			}
		});
		
		//action listener for the ">" button (next button)
		x0y2nxtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the index is one that is not saved in array list, call the save method
				if (companyIndex == companyContactList.size()) {
					// make sure that at least one value is not null
					if ((x0y2ntf.getText().length() != 0) ||
						(x0y2ctf.getText().length() != 0) ||
						(x0y2ptf.getText().length() != 0) ||
						(x0y2ftf.getText().length() != 0) ||
						(x0y2etf.getText().length() != 0)) {
						saveObject(1);
					}
				// however, if object already exists, update any changes that might have been made	
				} else {
					updateObject(1);
				}
				
				// if index is already reached the max, do nothing
				if (companyIndex >= companyContactList.size() - 1) {
					return;
				} else {
					// add one to the index, and display the corresponding object's values
					companyIndex++;
					x0y2ntf.setText(companyContactList.get(companyIndex).getName());
					x0y2ctf.setText(companyContactList.get(companyIndex).getCell());
					x0y2ptf.setText(companyContactList.get(companyIndex).getPhone());
					x0y2ftf.setText(companyContactList.get(companyIndex).getFax());
					x0y2etf.setText(companyContactList.get(companyIndex).getEmail());
				}
			}
		});
		
		// temporary
		x0y2Panel.setBorder(BorderFactory.createLineBorder(Color.red));
		
		// combine the three left smaller panels and give it a GridBagLayout
		lftMdPnl = new JPanel(); lftMdPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1; c.gridx = 0;
		lftMdPnl.add(x0y0Panel, c);		// leftmost top panel
		lftMdPnl.add(x0y1Panel, c);		// leftmost middle panel
		lftMdPnl.add(x0y2Panel, c);		// leftmost bottom panel
		
		
		/* create the top panel of the middle panel
		 * consists of the buttons payable and receivable
		 * these buttons will allow for interchange between
		 * the rest of the panels that make up the middle
		 * section
		 */
		pyblBtn = new JButton("Payable");		// payable button
		rcvblBtn = new JButton("Receivable");	// receivable button
		
		// add the buttons to a panel with flowlayout (default layout)
		x1y0Pnl = new JPanel();
		x1y0Pnl.add(pyblBtn);
		x1y0Pnl.add(rcvblBtn);
		
		// action listener for payable button
		pyblBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if panel mode is already true, do nothing
				if (midPanelMode == true) {
					return;
				} else {
					// call the switchMidPanel method, option 2
					switchMidPanel(2);
				}
			}
		});
		
		// action listener for receivable button
		rcvblBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if mid panel mode is already false, do nothing
				if (midPanelMode == false) {
					return;
				} else {
					// call the switchMidPanel method, option 1
					switchMidPanel(1);
				}
			}
		});
		
		/* create the second panel of the middle panel
		 * consists of credit line, pmt term, and factor
		 * can be refreshed depending on action listeners of
		 * payable and receivable buttons
		 * this section will begin with 'x1y1'
		 */
		x1y1cltf = new JTextField(15);			// credit line text field
		x1y1ptcb = new JComboBox(x1y1ptcbl);	// pmt term combo box
		x1y1fcb = new JComboBox(x1y1fcbl);		// factor combo box
		
		// add the components to a panel using GridBagLayout
		x1y1Pnl = new JPanel(); x1y1Pnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; x1y1Pnl.add(crdtLnLbl, c);	// credit line label at (0, 0)
		c.gridx = 1; c.gridy = 0; x1y1Pnl.add(x1y1cltf, c);		// credit line text field at (1, 0)
		c.gridx = 0; c.gridy = 1; x1y1Pnl.add(pmtTrmLbl, c);	// pmt term label at (0, 1)
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 1; x1y1Pnl.add(x1y1ptcb, c);		// pmt term combo box at (1, 1)
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 2; x1y1Pnl.add(fctrLbl, c);		// factor label at (0, 2)
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 2; x1y1Pnl.add(x1y1fcb, c);		// factor combo box at (1, 2)
		
		/* create the third panel of the middle panel
		 * consists of address (2 text fields), city,
		 * state, and zip code
		 * can be refreshed depending on action listeners of
		 * payable and receivable buttons
		 * this section will begin with 'x1y2'
		 */
		x1y2a1tf = new JTextField(20);	// address text field 1
		x1y2a2tf = new JTextField(20);	// address text field 2
		x1y2ctf = new JTextField(20);	// city text field
		x1y2stf = new JTextField(10);	// state text field
		x1y2ztf = new JTextField(10);	// zip text field
		
		// add the components to a panel using GridBagLayout
		x1y2Pnl = new JPanel(); x1y2Pnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; x1y2Pnl.add(addressLbl1, c);		// address label at (0, 0)
		
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 0; x1y2Pnl.add(x1y2a1tf, c);			// address text field 1 at (1, 0)
		c.gridx = 1; c.gridy = 1; x1y2Pnl.add(x1y2a2tf, c);			// address text field 2 at (1, 1)
		
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 2; x1y2Pnl.add(cityLbl1, c);			// city label at (0, 2)
		
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 2; x1y2Pnl.add(x1y2ctf, c);			// city text field at (1, 2)
		
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 3; x1y2Pnl.add(stateLbl1, c);		// state label at (0, 3)
		c.gridx = 1; c.gridy = 3; x1y2Pnl.add(x1y2stf, c);			// state text field at (1, 3)
		c.gridx = 2; c.gridy = 3; x1y2Pnl.add(zipLbl1, c);			// zip label at (2, 3)
		c.gridx = 3; c.gridy = 3; x1y2Pnl.add(x1y2ztf, c);			// zip text field at (3, 3)
		
		/* create the fourth panel of the middle panel
		 * consists of contact name, cell, phone, fax,
		 * and email. also contains buttons <, >, new,
		 * and delete for adding / deleting contacts
		 * this section will be named 'x1y3'
		 */
		x1y3prevBtn = new JButton("<");		// previous button
		x1y3nxtBtn = new JButton(">");		// next button
		x1y3nwBtn = new JButton("New");		// new button
		x1y3delBtn = new JButton("Delete");	// delete button
		
		x1y3ntf = new JTextField(10);		// name text field
		x1y3ctf = new JTextField(10);		// cell text field
		x1y3ptf = new JTextField(10);		// phone text field
		x1y3ftf = new JTextField(10);		// fax text field
		x1y3etf = new JTextField(10);		// email text field
		
		// add the components to a panel using GridBagLayout
		x1y3Pnl = new JPanel(); x1y3Pnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; x1y3Pnl.add(x1y3prevBtn, c);		// "<" button at (0, 0)
		c.gridx = 1; c.gridy = 0; x1y3Pnl.add(x1y3nxtBtn, c);		// ">" button at (1, 0)
		c.gridx = 2; c.gridy = 0; x1y3Pnl.add(x1y3nwBtn, c);		// new button at (2, 0)
		c.gridx = 3; c.gridy = 0; x1y3Pnl.add(x1y3delBtn, c);		// delete button at (3, 0)
		
		c.gridx = 0; c.gridy = 1; x1y3Pnl.add(nameLbl2, c);			// name label at (0, 1)
		c.gridx = 1; c.gridy = 1; x1y3Pnl.add(x1y3ntf, c);			// name text field at (1, 1)
		c.gridx = 2; c.gridy = 1; x1y3Pnl.add(cellLbl1, c);			// cell label at (2, 1)
		c.gridx = 3; c.gridy = 1; x1y3Pnl.add(x1y3ctf, c);			// cell text field at (3, 1)
		
		c.gridx = 0; c.gridy = 2; x1y3Pnl.add(phoneLbl1, c);		// phone label at (0, 2)
		c.gridx = 1; c.gridy = 2; x1y3Pnl.add(x1y3ptf, c);			// phone text field at (1, 2)
		c.gridx = 2; c.gridy = 2; x1y3Pnl.add(faxLbl2, c);			// fax label at (2, 2)
		c.gridx = 3; c.gridy = 2; x1y3Pnl.add(x1y3ftf, c);			// fax text field at (3, 2)

		c.gridx = 0; c.gridy = 3; x1y3Pnl.add(emailLbl1, c);		// email label at (0, 3)
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 3; x1y3Pnl.add(x1y3etf, c);			// email text field at (1, 3)
		
		// combine the previous 4 panels into a single middle panel using GridBagLayout
		midPnl = new JPanel(); midPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1; c.gridx = 0;
		midPnl.add(x1y0Pnl, c);	// 1st middle panel
		midPnl.add(x1y1Pnl, c);	// 2nd middle panel
		midPnl.add(x1y2Pnl, c);	// 3rd middle panel
		midPnl.add(x1y3Pnl, c);	// 4th middle panel
		
		/* action listener for new button
		 * depending on which panel (payable vs receivable)
		 * is currently on display, the contact information
		 * will have to be allocated to different storage
		 */
		x1y3nwBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if middle panel is currently on payable
				if (midPanelMode == true) {
					// if index is not saved in array list, call save method
					if (payableIndex == payableContactList.size()) {
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0)) {
							saveObject(3);
						} else {
							return;
						}
					// however, if object already exists, update the object
					} else {
						updateObject(3);
					}
					
					// hop the index to array list max index + 1
					payableIndex = payableContactList.size();
					
					// set all the text fields to null
					x1y3ntf.setText(null);
					x1y3ctf.setText(null);
					x1y3ptf.setText(null);
					x1y3ftf.setText(null);
					x1y3etf.setText(null);
					
				// if middle panel is currently on receivable
				} else {
					// if the index is one that is not saved, call the save method
					if (receivableIndex == receivableContactList.size()) {
						// make sure that at least one value != null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0)) {
							saveObject(4);
						} else {
							return;
						}
					// however, if object already exists, update the object
					} else {
						updateObject(4);
					}
					
					// hop the index to array list max index + 1
					receivableIndex = receivableContactList.size();
					
					// set all the text fields to null
					x1y3ntf.setText(null);
					x1y3ctf.setText(null);
					x1y3ptf.setText(null);
					x1y3ftf.setText(null);
					x1y3etf.setText(null);	
				}
			}
		});
		
		// action listener for delete button
		x1y3delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if middle mode is currently payable mode
				if (midPanelMode == true) {
					// if object is not saved in list yet, clear the text fields
					if (payableIndex >= payableContactList.size()) {
						x1y3ntf.setText(null);
						x1y3ctf.setText(null);
						x1y3ptf.setText(null);
						x1y3ftf.setText(null);
						x1y3etf.setText(null);
						return;
					}
					
					// otherwise, delete the current PayableContact object
					payableContactList.remove(payableIndex);
					
					// if payable list is empty, reset everything to null
					if (payableContactList.size() == 0) {
						x1y3ntf.setText(null);
						x1y3ctf.setText(null);
						x1y3ptf.setText(null);
						x1y3ftf.setText(null);
						x1y3etf.setText(null);
					} else {
						// if index is within bounds, set to next object in list
						if (payableIndex <= payableContactList.size() - 1) {
							x1y3ntf.setText(payableContactList.get(payableIndex).getName());
							x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
							x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
							x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
							x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
						
						// if index is out of bounds, move one backwards and display that object's values	
						} else {
							payableIndex--;
							x1y3ntf.setText(payableContactList.get(payableIndex).getName());
							x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
							x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
							x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
							x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
						}
					}
				// if middle mode is currently receivable mode	
				} else {
					// if object is not saved in list yet, clear the text fields
					if (receivableIndex >= receivableContactList.size()) {
						x1y3ntf.setText(null);
						x1y3ctf.setText(null);
						x1y3ptf.setText(null);
						x1y3ftf.setText(null);
						x1y3etf.setText(null);
						return;
					}
					
					// otherwise, delete the current PayableContact object
					receivableContactList.remove(receivableIndex);
					
					// if payable list is empty, reset everything to null
					if (receivableContactList.size() == 0) {
						x1y3ntf.setText(null);
						x1y3ctf.setText(null);
						x1y3ptf.setText(null);
						x1y3ftf.setText(null);
						x1y3etf.setText(null);
					} else {
						// if index is within bounds, set to next object in list
						if (receivableIndex <= receivableContactList.size() - 1) {
							x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
							x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
							x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
							x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
							x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
						
						// if index is out of bounds, move one backwards and display that object's values	
						} else {
							receivableIndex--;
							x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
							x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
							x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
							x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
							x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
						}
					}	
				}
			}
		});
		
		// action listener for "<" button (previous button)
		x1y3prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the current mode is on payable
				if (midPanelMode == true) {
					// if index is one that is not saved in list, call the save method
					if (payableIndex == payableContactList.size()) {
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0)) {
							saveObject(3);
						}
					// however, if object already exists, call the update method	
					} else {
						updateObject(3);
					}
					
					// if index is already 0, or list is empty, do nothing
					if ((payableIndex == 0) || (payableContactList.size() == 0)) {
						return;
					// otherwise, set index to one before and display that object's values
					} else {
						payableIndex--;
						x1y3ntf.setText(payableContactList.get(payableIndex).getName());
						x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
						x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
						x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
						x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
					}	
				// if current mode is on receivable	
				} else {
					// if index is one that is not saved in list, call the save method
					if (receivableIndex == receivableContactList.size()) {
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0)) {
							saveObject(4);
						}
					// however, if object already exists, call the update method	
					} else {
						updateObject(4);
					}
					
					// if index is already 0, or list is empty, do nothing
					if ((receivableIndex == 0) || (receivableContactList.size() == 0)) {
						return;
					// otherwise, set index to one before and display that object's values
					} else {
						receivableIndex--;
						x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
						x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
						x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
						x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
						x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
					}
				}	
			}
		});
		
		// action listener for ">" button (next button)
		x1y3nxtBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the current mode is on payable
				if (midPanelMode == true) {
					// if the index is one that is not save in list, call the save method
					if (payableIndex == payableContactList.size()) {
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0)) {
							saveObject(3);
						}
					// however, if object already exists, call the update method
					} else {
						updateObject(3);
					}
					
					// if the index has already reached the max, do nothing
					if (payableIndex >= payableContactList.size() - 1) {
						return;
					} else {
						// add one to the index, and display the corresponding object
						payableIndex++;
						x1y3ntf.setText(payableContactList.get(payableIndex).getName());
						x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
						x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
						x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
						x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
					}
				// if the current mode is on receivable	
				} else {
					// if the index is one that is not save in list, call the save method
					if (receivableIndex == receivableContactList.size()) {
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0)) {
							saveObject(4);
						}
					// however, if object already exists, call the update method
					} else {
						updateObject(4);
					}
					
					// if the index has already reached the max, do nothing
					if (receivableIndex >= receivableContactList.size() - 1) {
						return;
					} else {
						// add one to the index, and display the corresponding object
						receivableIndex++;
						x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
						x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
						x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
						x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
						x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
					}	
				}
			}
		});
		
		// temporary: add borders to the four panels
		x1y0Pnl.setBorder(BorderFactory.createLineBorder(Color.red));
		x1y1Pnl.setBorder(BorderFactory.createLineBorder(Color.red));
		x1y2Pnl.setBorder(BorderFactory.createLineBorder(Color.red));
		x1y3Pnl.setBorder(BorderFactory.createLineBorder(Color.red));
		
		/* create the top right corner
		 * consists of dot#, scac, contract/common,
		 * rating, ins agency, new agency button,
		 * a table of general, auto, cargo vs policy#,
		 * coverage, expiration. also contains target
		 * goods deductibles. this section will be called
		 * 'trc'
		 */
		trcnicb = new JButton("New");		// new insurance company button
		
		trcdottf = new JTextField(10);		// dot# text field
		trcscactf = new JTextField(10);		// scac# text field
		
		trcgptf = new JTextField(10);		// general-policy# text field
		trcgctf = new JTextField(10);		// general-coverage text field
		trcgetf = new JTextField(10);		// general-expiration text field
		
		trcaptf = new JTextField(10);		// auto-policy# text field
		trcactf = new JTextField(10);		// auto-coverage text field
		trcaetf = new JTextField(10);		// auto-expiration text field
		
		trccptf = new JTextField(10);		// cargo-policy# text field
		trccctf = new JTextField(10);		// cargo-coverage text field
		trccetf = new JTextField(10);		// cargo-expiration text field
		trctgdtf = new JTextField(10);		// target goods deductibles text field
		
		trccccb = new JComboBox(trccccbl);	// contact/common combo box
		trcrcb = new JComboBox(trcrcbl);	// rating combo box
		trciacb = new JComboBox(trciacbl);	// ins agency combo box
		
		// using GridBagLayout, add the components to a panel
		trcPnl = new JPanel(); trcPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; trcPnl.add(trcdotlbl, c);			// dot# label at (0, 0)
		c.gridx = 1; c.gridy = 0; trcPnl.add(trcdottf, c);			// dot text field at (1, 0)
		c.gridx = 3; c.gridy = 0; trcPnl.add(trccccb, c);			// contact/common combo box at (3, 0)
		
		c.gridx = 0; c.gridy = 1; trcPnl.add(trcscaclbl, c);		// scac label at (0, 1)
		c.gridx = 1; c.gridy = 1; trcPnl.add(trcscactf, c);			// scac text field at (1, 1)
		
		c.gridx = 0; c.gridy = 2; trcPnl.add(trcratinglbl, c);		// rating label at (0, 2)
		c.gridx = 1; c.gridy = 2; trcPnl.add(trcrcb, c);			// rating combo box at (1, 2)
		
		c.gridx = 0; c.gridy = 3; trcPnl.add(trcinsagencylbl, c);	// ins agency label at (0, 3)
		c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 3; trcPnl.add(trciacb, c);			// ins agency combo box at (1, 3)
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 3; c.gridy = 3; trcPnl.add(trcnicb, c);			// new button at (3, 3)
		
		c.gridx = 1; c.gridy = 4; trcPnl.add(trcpolicylbl, c);		// policy label at (1, 4)
		c.gridx = 2; c.gridy = 4; trcPnl.add(trccoveragelbl, c);	// coverage label at (2, 4)
		c.gridx = 3; c.gridy = 4; trcPnl.add(trcexpirationlbl, c);	// expiration label at (3, 4)
		
		c.gridx = 0; c.gridy = 5; trcPnl.add(trcgenerallbl, c);		// general label at (0, 5)
		c.gridx = 1; c.gridy = 5; trcPnl.add(trcgptf, c);			// general-policy at (1, 5)
		c.gridx = 2; c.gridy = 5; trcPnl.add(trcgctf, c);			// general-coverage at (2, 5)
		c.gridx = 3; c.gridy = 5; trcPnl.add(trcgetf, c);			// general-expiration at (3, 5)
		
		c.gridx = 0; c.gridy = 6; trcPnl.add(trcautolbl, c);		// auto label at (0, 6)
		c.gridx = 1; c.gridy = 6; trcPnl.add(trcaptf, c);			// auto-policy at (1, 6)
		c.gridx = 2; c.gridy = 6; trcPnl.add(trcactf, c);			// auto-coverage at (2, 6)
		c.gridx = 3; c.gridy = 6; trcPnl.add(trcaetf, c);			// auto-expiration at (3, 6)
		
		c.gridx = 0; c.gridy = 7; trcPnl.add(trccargolbl, c);		// cargo label at (0, 7)
		c.gridx = 1; c.gridy = 7; trcPnl.add(trccptf, c);			// cargo-policy at (1, 7)
		c.gridx = 2; c.gridy = 7; trcPnl.add(trccctf, c);			// cargo-coverage at (2, 7)
		c.gridx = 3; c.gridy = 7; trcPnl.add(trccetf, c);			// cargo-expiration at (3, 7)
		
		c.insets = new Insets(5, 0, 0, 0); c.gridwidth = 2;
		c.gridx = 0; c.gridy = 8; trcPnl.add(trctgdlbl, c);			// target goods deductibles label at (0, 8)
		c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2; c.gridy = 8; trcPnl.add(trctgdtf, c);			// target goods deductibles text field at (2, 8)
		
		// action listener for "new insurance company" button
		trcnicb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// open a new instance of CompanyMaker so that the user can create an insurance company
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new CompanyMaker();
					}
				});
			}
		});
		
		/* create the bottom right corner
		 * consists of contact name, cell#, phone,
		 * fax, and email
		 * for contacting insurance agency
		 * requires buttons <, >, new, and delete for
		 * creating and deleting multiple contacts
		 * this section will be called 'brc'
		 */
		brcprevbtn = new JButton("<");		// previous button
		brcnxtbtn = new JButton(">");		// next button
		brcnewbtn = new JButton("New");		// new button
		brcdelbtn = new JButton("Delete");	// delete button
		
		brcntf = new JTextField(10);	// name text field
		brcctf = new JTextField(10);	// cell text field
		brcptf = new JTextField(10);	// phone text field
		brcftf = new JTextField(10);	// fax text field
		brcetf = new JTextField(10);	// email text field
		
		// add the components to a panel using GridBagLayout
		brcPnl = new JPanel(); brcPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; brcPnl.add(brcprevbtn, c);	// "<" button at (0, 0)
		c.gridx = 1; c.gridy = 0; brcPnl.add(brcnxtbtn, c);		// ">" button at (1, 0)
		c.gridx = 2; c.gridy = 0; brcPnl.add(brcnewbtn, c);		// new button at (2, 0)
		c.gridx = 3; c.gridy = 0; brcPnl.add(brcdelbtn, c);		// delete button at (3, 0)
		
		c.gridx = 0; c.gridy = 1; brcPnl.add(brcnamelbl, c);		// name label at (0, 1)
		c.gridx = 1; c.gridy = 1; brcPnl.add(brcntf, c);			// name text field at (1, 1)
		c.gridx = 2; c.gridy = 1; brcPnl.add(brccelllbl, c);		// cell label at (2, 1)
		c.gridx = 3; c.gridy = 1; brcPnl.add(brcctf, c);			// cell text field at (3, 1)
		
		c.gridx = 0; c.gridy = 2; brcPnl.add(brcphonelbl, c);		// phone label at (0, 2)
		c.gridx = 1; c.gridy = 2; brcPnl.add(brcptf, c);			// phone text field at (1, 2)
		c.gridx = 2; c.gridy = 2; brcPnl.add(brcfaxlbl, c);			// fax label at (2, 2)
		c.gridx = 3; c.gridy = 2; brcPnl.add(brcftf, c);			// fax text field at (3, 2)

		c.gridx = 0; c.gridy = 3; brcPnl.add(brcemaillbl, c);		// email label at (0, 3)
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 3; brcPnl.add(brcetf, c);			// email text field at (1, 3)
		
		/* action listener for new button
		 * when user clicks on it, save current contact
		 * data to an arraylist of InsuranceContact objects,
		 * which can be modified later by other objects
		 */
		brcnewbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//if the index is one that is not save in array list, call the save method
				if (insuranceIndex == insuranceContactList.size()) {
					// make sure that at least one value is not null
					if ((brcntf.getText().length() != 0) ||
						(brcctf.getText().length() != 0) ||
						(brcptf.getText().length() != 0) ||
						(brcftf.getText().length() != 0) ||
						(brcetf.getText().length() != 0)) {
						saveObject(2);
					} else {
						return;
					}
				// however, if object already exists, call the update method
				} else {
					updateObject(2);
				}
				
				// hop the index to array list max index + 1
				insuranceIndex = insuranceContactList.size();
				
				// set all the text fields to null
				brcntf.setText(null);
				brcctf.setText(null);
				brcptf.setText(null);
				brcftf.setText(null);
				brcetf.setText(null);	
			}
		});
		
		// action listener for delete button
		brcdelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if object is not saved in array list yet, simply clear the text fields
				if (insuranceIndex >= insuranceContactList.size()) {
					brcntf.setText(null);
					brcctf.setText(null);
					brcptf.setText(null);
					brcftf.setText(null);
					brcetf.setText(null);
					return;
				}
				
				// otherwise, delete the current InsuranceContact object
				insuranceContactList.remove(insuranceIndex);
				
				// if array list is empty, reset everything to null
				if (insuranceContactList.size() == 0) {
					brcntf.setText(null);
					brcctf.setText(null);
					brcptf.setText(null);
					brcftf.setText(null);
					brcetf.setText(null);
				} else {
					// if index is still within bounds, set to next object in list
					if (insuranceIndex <= insuranceContactList.size() - 1) {
						brcntf.setText(insuranceContactList.get(insuranceIndex).getName());
						brcctf.setText(insuranceContactList.get(insuranceIndex).getCell());
						brcptf.setText(insuranceContactList.get(insuranceIndex).getPhone());
						brcftf.setText(insuranceContactList.get(insuranceIndex).getFax());
						brcetf.setText(insuranceContactList.get(insuranceIndex).getEmail());
					
					// if index is out of bounds, move index one backwards and display values
					} else {
						insuranceIndex--;
						brcntf.setText(insuranceContactList.get(insuranceIndex).getName());
						brcctf.setText(insuranceContactList.get(insuranceIndex).getCell());
						brcptf.setText(insuranceContactList.get(insuranceIndex).getPhone());
						brcftf.setText(insuranceContactList.get(insuranceIndex).getFax());
						brcetf.setText(insuranceContactList.get(insuranceIndex).getEmail());
					}
				}
			}
		});
		
		// action listener for the "<" button (previous button)
		brcprevbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the index is one that is not saved in array list, call the save method
				if (insuranceIndex == insuranceContactList.size()) {
					// make sure that at least one value is not null
					if ((brcntf.getText().length() != 0) ||
						(brcctf.getText().length() != 0) ||
						(brcptf.getText().length() != 0) ||
						(brcftf.getText().length() != 0) ||
						(brcetf.getText().length() != 0)) {
						saveObject(2);
					}
				// however, if object already exists, call the update method
				} else {
					updateObject(2);
				}
				
				// simply return if the index is already 0, or the array list is empty
				if ((insuranceIndex == 0) || (insuranceContactList.size() == 0)) {
					return;
				} else {
					// otherwise, set index to one before and display that object's values
					insuranceIndex--;
					brcntf.setText(insuranceContactList.get(insuranceIndex).getName());
					brcctf.setText(insuranceContactList.get(insuranceIndex).getCell());
					brcptf.setText(insuranceContactList.get(insuranceIndex).getPhone());
					brcftf.setText(insuranceContactList.get(insuranceIndex).getFax());
					brcetf.setText(insuranceContactList.get(insuranceIndex).getEmail());
				}
			}
		});
		
		// action listener for the ">" button (next button)
		brcnxtbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// if the index is one that is not saved in array list, call the save method
				if (insuranceIndex == insuranceContactList.size()) {
					// make sure that at least one value is not null
					if ((brcntf.getText().length() != 0) ||
						(brcctf.getText().length() != 0) ||
						(brcptf.getText().length() != 0) ||
						(brcftf.getText().length() != 0) ||
						(brcetf.getText().length() != 0)) {
						saveObject(2);
					}
				// however, if object already exists, call the update method
				} else {
					updateObject(2);
				}
				
				// if the index is already reached the max, do nothing
				if (insuranceIndex >= insuranceContactList.size() - 1) {
					return;
				} else {
					// add one to the index, and display the corresponding object's values
					insuranceIndex++;
					brcntf.setText(insuranceContactList.get(insuranceIndex).getName());
					brcctf.setText(insuranceContactList.get(insuranceIndex).getCell());
					brcptf.setText(insuranceContactList.get(insuranceIndex).getPhone());
					brcftf.setText(insuranceContactList.get(insuranceIndex).getFax());
					brcetf.setText(insuranceContactList.get(insuranceIndex).getEmail());
				}
			}
		});
		
		/* combine the top right corner
		 * and bottom left corner panels 
		 * into one panel named rightMdPnl
		 */
		rightMdPnl = new JPanel(); rightMdPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1; c.gridx = 0;
		rightMdPnl.add(trcPnl, c);
		rightMdPnl.add(brcPnl, c);
		
		// temporary: add borders around the smaller right panels
		trcPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		brcPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		
		/* add the larger panels onto the main panel
		 * use a GridBagLayout to ensure that the three large
		 * panels fit well onto the main panel
		 */
		mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 1; mainPanel.add(lftMdPnl, c);
		c.gridx = 1; c.gridy = 1; mainPanel.add(midPnl, c);
		c.gridx = 2; c.gridy = 1; mainPanel.add(rightMdPnl, c);
		
		/* edit: since the layout is not coming out as 
		 * desired, create a new panel called "ultraMainPanel"
		 * and use a GridBagLayout vertically to add topmost panel
		 * and the rest of the panels (mainPanel) together
		 */
		ultraMainPanel = new JPanel(); ultraMainPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1; c.gridx = 0;
		ultraMainPanel.add(tpMstPnl, c);
		ultraMainPanel.add(mainPanel, c);
		
		// temporary: add borders around large panels
		tpMstPnl.setBorder(BorderFactory.createLineBorder(Color.blue));
		lftMdPnl.setBorder(BorderFactory.createLineBorder(Color.blue));
		midPnl.setBorder(BorderFactory.createLineBorder(Color.blue));
		rightMdPnl.setBorder(BorderFactory.createLineBorder(Color.blue));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
		
		// create the main frame and add ultraMainPanel to it
		mainFrame = new JFrame("New Company");
		mainFrame.add(ultraMainPanel);
		mainFrame.pack();
		
		// set mainFrame size, close option, and visibility
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setVisible(true);
	}
	
	/* method: saves an object to an object arraylist
	 * input: which type of save to do (int)
	 * output: none
	 */
	private void saveObject(int saveOption) {
		// type 1 save is for CompanyContact objects
		if (saveOption == 1) {
			CompanyContact object = new CompanyContact();
			if (x0y2ntf.getText().length() != 0) {
				object.storeName(x0y2ntf.getText());
			} else {
				object.storeName("");
			}
			if (x0y2ctf.getText().length() != 0) {
				object.storeCell(x0y2ctf.getText());
			} else {
				object.storeCell("");
			}
			if (x0y2ptf.getText().length() != 0) {
				object.storePhone(x0y2ptf.getText());
			} else {
				object.storePhone("");
			}
			if (x0y2ftf.getText().length() != 0) {
				object.storeFax(x0y2ftf.getText());
			} else {
				object.storeFax("");
			}
			if (x0y2etf.getText().length() != 0) {
				object.storeEmail(x0y2etf.getText());
			} else {
				object.storeEmail("");
			}
			companyContactList.add(object);
		
		// save option 2 is for InsuranceContact objects
		} else if (saveOption == 2) {
			InsuranceContact object = new InsuranceContact();
			if (brcntf.getText().length() != 0) {
				object.storeName(brcntf.getText());
			} else {
				object.storeName("");
			}
			if (brcctf.getText().length() != 0) {
				object.storeCell(brcctf.getText());
			} else {
				object.storeCell("");
			}
			if (brcptf.getText().length() != 0) {
				object.storePhone(brcptf.getText());
			} else {
				object.storePhone("");
			}
			if (brcftf.getText().length() != 0) {
				object.storeFax(brcftf.getText());
			} else {
				object.storeFax("");
			}
			if (brcetf.getText().length() != 0) {
				object.storeEmail(brcetf.getText());
			} else {
				object.storeEmail("");
			}
			insuranceContactList.add(object);
			
		// save option 3 is for PayableContact objects
		} else if (saveOption == 3) {
			PayableContact object = new PayableContact();
			if (x1y3ntf.getText().length() != 0) {
				object.storeName(x1y3ntf.getText());
			} else {
				object.storeName("");
			}
			if (x1y3ctf.getText().length() != 0) {
				object.storeCell(x1y3ctf.getText());
			} else {
				object.storeCell("");
			}
			if (x1y3ptf.getText().length() != 0) {
				object.storePhone(x1y3ptf.getText());
			} else {
				object.storePhone("");
			}
			if (x1y3ftf.getText().length() != 0) {
				object.storeFax(x1y3ftf.getText());
			} else {
				object.storeFax("");
			}
			if (x1y3etf.getText().length() != 0) {
				object.storeEmail(x1y3etf.getText());
			} else {
				object.storeEmail("");
			}
			payableContactList.add(object);
			
		// save option 4 is for ReceivableContact objects	
		} else if (saveOption == 4) {
			ReceivableContact object = new ReceivableContact();
			if (x1y3ntf.getText().length() != 0) {
				object.storeName(x1y3ntf.getText());
			} else {
				object.storeName("");
			}
			if (x1y3ctf.getText().length() != 0) {
				object.storeCell(x1y3ctf.getText());
			} else {
				object.storeCell("");
			}
			if (x1y3ptf.getText().length() != 0) {
				object.storePhone(x1y3ptf.getText());
			} else {
				object.storePhone("");
			}
			if (x1y3ftf.getText().length() != 0) {
				object.storeFax(x1y3ftf.getText());
			} else {
				object.storeFax("");
			}
			if (x1y3etf.getText().length() != 0) {
				object.storeEmail(x1y3etf.getText());
			} else {
				object.storeEmail("");
			}
			receivableContactList.add(object);
		}
	}
	
	/* method: updates a current object
	 * input: update option type (int)
	 * output: none
	 */
	private void updateObject(int option) {
		// option type 1 is for CompanyContact objects
		if (option == 1) {
			if (x0y2ntf.getText().length() != 0) {
				companyContactList.get(companyIndex).storeName(x0y2ntf.getText());
			} else {
				companyContactList.get(companyIndex).storeName("");
			}
			if (x0y2ctf.getText().length() != 0) {
				companyContactList.get(companyIndex).storeCell(x0y2ctf.getText());
			} else {
				companyContactList.get(companyIndex).storeCell("");
			}
			if (x0y2ptf.getText().length() != 0) {
				companyContactList.get(companyIndex).storePhone(x0y2ptf.getText());
			} else {
				companyContactList.get(companyIndex).storePhone("");
			}
			if (x0y2ftf.getText().length() != 0) {
				companyContactList.get(companyIndex).storeFax(x0y2ftf.getText());
			} else {
				companyContactList.get(companyIndex).storeFax("");
			}
			if (x0y2etf.getText().length() != 0) {
				companyContactList.get(companyIndex).storeEmail(x0y2etf.getText());
			} else {
				companyContactList.get(companyIndex).storeEmail("");
			}
			
		// option type 2 is for InsuranceContact objects
		} else if (option == 2) {
			if (brcntf.getText().length() != 0) {
				insuranceContactList.get(insuranceIndex).storeName(brcntf.getText());
			} else {
				insuranceContactList.get(insuranceIndex).storeName("");
			}
			if (brcctf.getText().length() != 0) {
				insuranceContactList.get(insuranceIndex).storeCell(brcctf.getText());
			} else {
				insuranceContactList.get(insuranceIndex).storeCell("");
			}
			if (brcptf.getText().length() != 0) {
				insuranceContactList.get(insuranceIndex).storePhone(brcptf.getText());
			} else {
				insuranceContactList.get(insuranceIndex).storePhone("");
			}
			if (brcftf.getText().length() != 0) {
				insuranceContactList.get(insuranceIndex).storeFax(brcftf.getText());
			} else {
				insuranceContactList.get(insuranceIndex).storeFax("");
			}
			if (brcetf.getText().length() != 0) {
				insuranceContactList.get(insuranceIndex).storeEmail(brcetf.getText());
			} else {
				insuranceContactList.get(insuranceIndex).storeEmail("");
			}
			
		// option type 3 is for PayableContact objects
		} else if (option == 3) {
			if (x1y3ntf.getText().length() != 0) {
				payableContactList.get(payableIndex).storeName(x1y3ntf.getText());
			} else {
				payableContactList.get(payableIndex).storeName("");
			}
			if (x1y3ctf.getText().length() != 0) {
				payableContactList.get(payableIndex).storeCell(x1y3ctf.getText());
			} else {
				payableContactList.get(payableIndex).storeCell("");
			}
			if (x1y3ptf.getText().length() != 0) {
				payableContactList.get(payableIndex).storePhone(x1y3ptf.getText());
			} else {
				payableContactList.get(payableIndex).storePhone("");
			}
			if (x1y3ftf.getText().length() != 0) {
				payableContactList.get(payableIndex).storeFax(x1y3ftf.getText());
			} else {
				payableContactList.get(payableIndex).storeFax("");
			}
			if (x1y3etf.getText().length() != 0) {
				payableContactList.get(payableIndex).storeEmail(x1y3etf.getText());
			} else {
				payableContactList.get(payableIndex).storeEmail("");
			}
			
		// option type 4 is for ReceivableContact objects
		} else if (option == 4) {
			if (x1y3ntf.getText().length() != 0) {
				receivableContactList.get(receivableIndex).storeName(x1y3ntf.getText());
			} else {
				receivableContactList.get(receivableIndex).storeName("");
			}
			if (x1y3ctf.getText().length() != 0) {
				receivableContactList.get(receivableIndex).storeCell(x1y3ctf.getText());
			} else {
				receivableContactList.get(receivableIndex).storeCell("");
			}
			if (x1y3ptf.getText().length() != 0) {
				receivableContactList.get(receivableIndex).storePhone(x1y3ptf.getText());
			} else {
				receivableContactList.get(receivableIndex).storePhone("");
			}
			if (x1y3ftf.getText().length() != 0) {
				receivableContactList.get(receivableIndex).storeFax(x1y3ftf.getText());
			} else {
				receivableContactList.get(receivableIndex).storeFax("");
			}
			if (x1y3etf.getText().length() != 0) {
				receivableContactList.get(receivableIndex).storeEmail(x1y3etf.getText());
			} else {
				receivableContactList.get(receivableIndex).storeEmail("");
			}
		}
	}
	
	/* method: controls the actions that take place when a user
	 * clicks the payable or receivable button. depending on the
	 * boolean value of midPanelMode, this method will the necessary
	 * steps to save the data on the panel, and then switch to a new
	 * blank panel
	 */
	private void switchMidPanel(int switchType) {
		// switchType 1 is payable to receivable
		if (switchType == 1) {
			// switch panel mode to false
			midPanelMode = false;
			
			// save the information on the payable panel
			if (x1y1cltf.getText().length() != 0) {
				payableCompany.storeCreditLine(x1y1cltf.getText());
			} else {
				payableCompany.storeCreditLine("");
			}
			payableCompany.storePmtTerm((String)x1y1ptcb.getSelectedItem());
			payableCompany.storeFactor((String)x1y1fcb.getSelectedItem());
			if (x1y2a1tf.getText().length() != 0) {
				payableCompany.storeAddressLine1(x1y2a1tf.getText());
			} else {
				payableCompany.storeAddressLine1("");
			}
			if (x1y2a2tf.getText().length() != 0) {
				payableCompany.storeAddressLine2(x1y2a2tf.getText());
			} else {
				payableCompany.storeAddressLine2("");
			}
			if (x1y2ctf.getText().length() != 0) {
				payableCompany.storeCity(x1y2ctf.getText());
			} else {
				payableCompany.storeCity("");
			}
			if (x1y2stf.getText().length() != 0) {
				payableCompany.storeState(x1y2stf.getText());
			} else {
				payableCompany.storeState("");
			}
			if (x1y2ztf.getText().length() != 0) {
				payableCompany.storeZip(x1y2ztf.getText());
			} else {
				payableCompany.storeZip("");
			}
			
			// save the last entry for payable contact, if it is not already saved
			if (payableIndex == payableContactList.size()) {
				// make sure at least one value is not null
				if ((x1y3ntf.getText().length() != 0) ||
					(x1y3ctf.getText().length() != 0) ||
					(x1y3ptf.getText().length() != 0) ||
					(x1y3ftf.getText().length() != 0) ||
					(x1y3etf.getText().length() != 0)) {
					saveObject(3);
				}
			// however, if it is not the last entry, update that object	
			} else {
				updateObject(3);
			}
				
			// display the information of the receivable panel
			x1y1cltf.setText(receivableCompany.getCreditLine());
			x1y1ptcb.setSelectedItem(receivableCompany.getPmtTerm());
			x1y1fcb.setSelectedItem(receivableCompany.getFactor());
			x1y2a1tf.setText(receivableCompany.getAddressLine1());
			x1y2a2tf.setText(receivableCompany.getAddressLine2());
			x1y2ctf.setText(receivableCompany.getCity());
			x1y2stf.setText(receivableCompany.getState());
			x1y2ztf.setText(receivableCompany.getZip());
			
			// display the last receivable contact entry. if list is empty, display blanks
			if (receivableContactList.size() != 0) {
				// set the receivable index to that of the last entry in the list
				receivableIndex = receivableContactList.size() - 1;
				x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
				x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
				x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
				x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
				x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
			} else {
				receivableIndex = 0;
				x1y3ntf.setText(null);
				x1y3ctf.setText(null);
				x1y3ptf.setText(null);
				x1y3ftf.setText(null);
				x1y3etf.setText(null);
			}
		
		// if switchType == 2, transition from receivable to payable
		} else {
			// switch panel mode to true
			midPanelMode = true;
			
			// save the information on the receivable panel
			if (x1y1cltf.getText().length() != 0) {
				receivableCompany.storeCreditLine(x1y1cltf.getText());
			} else {
				receivableCompany.storeCreditLine("");
			}
			receivableCompany.storePmtTerm((String)x1y1ptcb.getSelectedItem());
			receivableCompany.storeFactor((String)x1y1fcb.getSelectedItem());
			if (x1y2a1tf.getText().length() != 0) {
				receivableCompany.storeAddressLine1(x1y2a1tf.getText());
			} else {
				receivableCompany.storeAddressLine1("");
			}
			if (x1y2a2tf.getText().length() != 0) {
				receivableCompany.storeAddressLine2(x1y2a2tf.getText());
			} else {
				receivableCompany.storeAddressLine2("");
			}
			if (x1y2ctf.getText().length() != 0) {
				receivableCompany.storeCity(x1y2ctf.getText());
			} else {
				receivableCompany.storeCity("");
			}
			if (x1y2stf.getText().length() != 0) {
				receivableCompany.storeState(x1y2stf.getText());
			} else {
				receivableCompany.storeState("");
			}
			if (x1y2ztf.getText().length() != 0) {
				receivableCompany.storeZip(x1y2ztf.getText());
			} else {
				receivableCompany.storeZip("");
			}
			
			// if current entry of contact is not saved, add the object to the receivable list
			if (receivableIndex == receivableContactList.size()) {
				// make sure that at least one value is not null
				if ((x1y3ntf.getText().length() != 0) ||
					(x1y3ctf.getText().length() != 0) ||
					(x1y3ptf.getText().length() != 0) ||
					(x1y3ftf.getText().length() != 0) ||
					(x1y3etf.getText().length() != 0)) {
					saveObject(4);
				}
			// however, if object already exists, call the update method	
			} else {
				updateObject(4);
			}
			
			// display the information of the payable panel
			x1y1cltf.setText(payableCompany.getCreditLine());
			x1y1ptcb.setSelectedItem(payableCompany.getPmtTerm());
			x1y1fcb.setSelectedItem(payableCompany.getFactor());
			x1y2a1tf.setText(payableCompany.getAddressLine1());
			x1y2a2tf.setText(payableCompany.getAddressLine2());
			x1y2ctf.setText(payableCompany.getCity());
			x1y2stf.setText(payableCompany.getState());
			x1y2ztf.setText(payableCompany.getZip());
			
			// display the last contact entry. if list is empty, display blanks
			if (payableContactList.size() != 0) {
				payableIndex = payableContactList.size() - 1;
				x1y3ntf.setText(payableContactList.get(payableIndex).getName());
				x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
				x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
				x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
				x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
			} else {
				payableIndex = 0;
				x1y3ntf.setText(null);
				x1y3ctf.setText(null);
				x1y3ptf.setText(null);
				x1y3ftf.setText(null);
				x1y3etf.setText(null);
			}
		}
	}
	
	// main method
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CompanyMaker();
			}
		});
	}
}