/**
 * template for creating a new company
 * when "new company" button is pressed from the main window,
 * this template will pop up as a new window
 * after the user is done, the data will be entered into an SQL database table
 */

package windows;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import storageDevices.CompanyContact;
import storageDevices.InsuranceContact;
import storageDevices.InsurerCompany;
import storageDevices.PayableCompany;
import storageDevices.PayableContact;
import storageDevices.ReceivableCompany;
import storageDevices.ReceivableContact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

/* This template will consist of three main panels on a single panel
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

/* Storage for company information
 * 
 * Table: COMPANY GENERAL INFORMATION
 * 
 * NAME
 * DBA
 * TEL
 * TAXID
 * FAX
 * MC#
 * ESTABLISHED
 * ADDRESS LINE 1
 * ADDRESS LINE 2
 * CITY
 * STATE
 * ZIP
 * DOT
 * CONTACT/COMMON
 * SCAC
 * RATING
 * INS. AGENCY
 * GENERAL-POLICY
 * GENERAL-COVERAGE
 * GENERAL-EXPIRATION
 * AUTO-POLICY
 * AUTO-COVERAGE
 * AUTO-EXPIRATION
 * CARGO-POLICY
 * CARGO-COVERAGE
 * CARGO-EXPIRATION
 * TARGET GOOD DEDUCTIBLES
 * CARRIER?
 * BROKER?
 * CUSTOMER?
 * SHIPPER?
 * CONSIGNEE?
 * INS. AGENCY?
 * FACTORY?
 * MY FACTORY?
 * VENDOR?
 * OWNER/OPERATOR?
 * OTHER?
 */

public class CompanyMaker
{
	// ===========================================
	// COMPONENTS. (DECLARED, BUT NOT INITIALIZED)
	// ===========================================
	GridBagConstraints c;
	JFrame mainFrame;
	JPanel mainPanel, ultraMainPanel, tpMstPnl, x0y0Panel, x0y1Panel, x0y2Panel, lftMdPnl, x1y0Pnl, x1y1Pnl, x1y2Pnl, x1y3Pnl, midPnl, trcPnl, brcPnl, rightMdPnl;
	JButton saveBtn, x0y2prevBtn, x0y2nxtBtn, x0y2nwBtn, x0y2delBtn, pyblBtn, rcvblBtn;
	Checkbox carrierType, brokerType, customerType, shipperType, consigneeType, insAgencyType, factoringType, preferredType, vendorType, ownerOperatorType, otherType;
	JLabel nameLbl, nameLbl1, DBALbl, telLbl, taxIDLbl, faxLbl, faxLbl1, mcLbl, establishedLbl, addressLbl, cityLbl, stateLbl, zipLbl, cellLbl, phoneLbl, emailLbl;
	JTextField x0y0Name, x0y0DBA, x0y0Tel, x0y0TaxID, x0y0Fax, x0y0MC, x0y0Established, x1y1cltf;
	JTextField x0y1ad1tf, x0y1ad2tf, x0y1ctf, x0y1stf, x0y1ztf;
	JTextField x0y2ntf, x0y2ctf, x0y2ptf, x0y2ftf, x0y2etf;
	JLabel crdtLnLbl, pmtTrmLbl, fctrLbl;
	JComboBox<String> x1y1ptcb, x1y1fcb;
	String[] x1y1ptcbl = {"30 Days", "21 Days", "15 Days", "7 Days", "2 Days"};
	String[] x1y1fcbl = {"None"};
	JLabel addressLbl1, cityLbl1, stateLbl1, zipLbl1;
	JTextField x1y2a1tf, x1y2a2tf, x1y2ctf, x1y2stf, x1y2ztf;
	JLabel nameLbl2, cellLbl1, phoneLbl1, faxLbl2, emailLbl1;
	JTextField x1y3ntf, x1y3ctf, x1y3ptf, x1y3ftf, x1y3etf;
	JButton x1y3prevBtn, x1y3nxtBtn, x1y3nwBtn, x1y3delBtn;
	JLabel trcdotlbl, trcscaclbl, trcratinglbl, trcinsagencylbl, trcpolicylbl, trccoveragelbl, trcexpirationlbl;
	JLabel trcgenerallbl, trcautolbl, trccargolbl, trctgdlbl;
	JTextField trcdottf, trcscactf, trcgptf, trcgctf, trcgetf, trcaptf, trcactf, trcaetf, trccptf, trccctf, trccetf, trctgdtf;
	JComboBox<String> trcrcb, trccccb, trciacb;
	String[] trcrcbl = {"Unrated", "Satisfactory", "Unsatisfactory", "Conditional"};
	String[] trccccbl = {"Contract", "Common"};
	String[] trciacbl = {"Select One"};
	JButton trcnicb;
	JLabel brcnamelbl, brccelllbl, brcphonelbl, brcfaxlbl, brcemaillbl;
	JTextField brcntf, brcctf, brcptf, brcftf, brcetf;
	JButton brcprevbtn, brcnxtbtn, brcnewbtn, brcdelbtn;
	ArrayList<CompanyContact> companyContactList;
	ArrayList<PayableContact> payableContactList;
	ArrayList<ReceivableContact> receivableContactList;
	ArrayList<InsuranceContact> insuranceContactList;
	ArrayList<InsurerCompany> insurerList;
	PayableCompany payableCompany;
	ReceivableCompany receivableCompany;
	int companyIndex, payableIndex, receivableIndex, insuranceIndex, insurerIndex, companyID;
	boolean midPanelMode, isSavedBefore;
	ArrayList<String> companyGeneralInfo;
	JButton insurerLeft, insurerRight, insurerNew, insurerDelete;
	JLabel insurerLabel;
	JTextField insurerTextField;
	
	public CompanyMaker()
	{
		// initialize ArrayLists that will be used for temporary storage
		companyContactList = new ArrayList<CompanyContact>();
		payableContactList = new ArrayList<PayableContact>();
		receivableContactList = new ArrayList<ReceivableContact>();
		insuranceContactList = new ArrayList<InsuranceContact>();
		insurerList = new ArrayList<InsurerCompany>();
		
		// used to keep track of where the array list index should be
		companyIndex = 0;
		payableIndex = 0;
		receivableIndex = 0;
		insuranceIndex = 0;
		insurerIndex = 0;
		
		// true means payable mode, false means receivable mode (default is payable)
		midPanelMode = true;
		
		// determines whether the save button has been pressed before
		isSavedBefore = false;
		
		// initialize PayableCompany and ReceivableCompany objects, everything no value
		payableCompany = new PayableCompany();
		receivableCompany = new ReceivableCompany();
		payableCompany.everythingEmpty();
		receivableCompany.everythingEmpty();
		
		// ================================================================
		// large section of declared labels
		// multiples of the same labels are declared under the previous one
		// ================================================================
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
		trcpolicylbl = new JLabel("Policy: ");					// policy#
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
		
		// =======================================================================
		// top most panel of the template
		// consists of the save button, and check boxes of different company types
		// =======================================================================
		carrierType = new Checkbox("Carrier");				// carrier check box
		brokerType = new Checkbox("Broker");				// broker check box
		customerType = new Checkbox("Customer");			// customer check box
		shipperType = new Checkbox("Shipper");				// shipper check box
		consigneeType = new Checkbox("Consignee");			// consigneee check box
		insAgencyType = new Checkbox("Ins. Agency");		// insurance agency check box
		factoringType = new Checkbox("Factoring");			// factoring check box
		preferredType = new Checkbox("Preferred");			// preferred check box
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
		tpMstPnl.add(preferredType);
		tpMstPnl.add(vendorType);
		tpMstPnl.add(ownerOperatorType);
		tpMstPnl.add(otherType);
		tpMstPnl.add(saveBtn);
		
		// ActionListener for save button: calls the saveAndStore() method (stores the info to SQL server)
		saveBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				saveAndStore();
			}
		});
		
		// ===================================================================================================
		// top panel of the leftmost panel
		// consists of company name, dba, tel, taxID, fax, mc#, established.
		// EDIT: ADD DOT AND SCAC
		// requires unique, corresponding jtextfields for each one of those labels
		// text fields will start with 'x0y0' to indicate top left corner of the 9 different midsection panels
		// ===================================================================================================
		x0y0Name = new JTextField(20);			// name text field
		x0y0DBA = new JTextField(10);			// dba text field
		x0y0Tel = new JTextField(10);			// tel text field
		x0y0TaxID = new JTextField(10);			// tax id text field
		x0y0Fax = new JTextField(10);			// fax text field
		x0y0MC = new JTextField(10);			// mc# text field
		x0y0Established = new JTextField(10);	// established date text field
		
		// NEW CHANGES
		trcdottf = new JTextField(10);		// dot# text field
		trcscactf = new JTextField(10);		// scac# text field
		
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
		
		// NEW CHANGES
		c.gridx = 0; c.gridy = 4; x0y0Panel.add(trcdotlbl, c);		// added dot label
		c.gridx = 1; c.gridy = 4; x0y0Panel.add(trcdottf, c);		// added dot text field
		c.gridx = 2; c.gridy = 4; x0y0Panel.add(trcscaclbl, c);		// added scac label
		c.gridx = 3; c.gridy = 4; x0y0Panel.add(trcscactf, c);		// added scac text field
		
		// temporary
		x0y0Panel.setBorder(BorderFactory.createLineBorder(Color.red));
		
		// ===========================================================================
		// the middle panel of the left-most panel
		// contains address, city, state, and zip. contains 2 text fields for address.
		// components of this section will start with the name 'x0y1'
		// ===========================================================================
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
		
		// ==========================================================
		// bottom panel of the left-most panel.
		// contains buttons '<', '>', 'new', and 'delete'.
		// components in this section will start with the name 'x0y2'
		// =========================================================
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
		
		// ActionListener for 'new' button
		// when user clicks on it, saves current contact data to an ArrayList of CompanyContact objects, which can be modified later
		x0y2nwBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (companyIndex == companyContactList.size())
				{
					// make sure that at least one value is not null
					if ((x0y2ntf.getText().length() != 0) ||
						(x0y2ctf.getText().length() != 0) ||
						(x0y2ptf.getText().length() != 0) ||
						(x0y2ftf.getText().length() != 0) ||
						(x0y2etf.getText().length() != 0))
					{
						saveObject(1);
					}
				}
				// however, if object already exists, update any changes that might have been made	
				else
					updateObject(1);
				
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
		
		// ActionListener for delete button
		x0y2delBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if object is not save in array list yet, simply clear the text fields and return
				if (companyIndex >= companyContactList.size())
				{
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
				if (companyContactList.size() == 0)
				{
					x0y2ntf.setText(null);
					x0y2ctf.setText(null);
					x0y2ptf.setText(null);
					x0y2ftf.setText(null);
					x0y2etf.setText(null);	
				}
				else
				{
					// if index is still within bounds, set to next object in list
					if (companyIndex <= companyContactList.size() - 1)
					{
						x0y2ntf.setText(companyContactList.get(companyIndex).getName());
						x0y2ctf.setText(companyContactList.get(companyIndex).getCell());
						x0y2ptf.setText(companyContactList.get(companyIndex).getPhone());
						x0y2ftf.setText(companyContactList.get(companyIndex).getFax());
						x0y2etf.setText(companyContactList.get(companyIndex).getEmail());
					}
					// if index is out of bounds, move index one backwards and display values of that object
					else
					{
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
		x0y2prevBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (companyIndex == companyContactList.size())
				{
					// make sure that at least one value is not null
					if ((x0y2ntf.getText().length() != 0) ||
						(x0y2ctf.getText().length() != 0) ||
						(x0y2ptf.getText().length() != 0) ||
						(x0y2ftf.getText().length() != 0) ||
						(x0y2etf.getText().length() != 0))
					{
						saveObject(1);
					}
				}
				// however, if object already exists, update any changes that might have been made	
				else
					updateObject(1);
				
				// simply return if the index is already 0, or array list is empty
				if ((companyIndex == 0) || (companyContactList.size() == 0))
					return;
				// otherwise, set index to one before and display that object's values
				else
				{
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
		x0y2nxtBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (companyIndex == companyContactList.size())
				{
					// make sure that at least one value is not null
					if ((x0y2ntf.getText().length() != 0) ||
						(x0y2ctf.getText().length() != 0) ||
						(x0y2ptf.getText().length() != 0) ||
						(x0y2ftf.getText().length() != 0) ||
						(x0y2etf.getText().length() != 0))
					{
						saveObject(1);
					}	
				}
				// however, if object already exists, update any changes that might have been made
				else
					updateObject(1);
				
				// if index is already reached the max, do nothing
				if (companyIndex >= companyContactList.size() - 1)
					return;
				// else, add one to the index, and display the corresponding object's values
				else
				{
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
		
		// ========================================================================================================
		// top panel of the middle panel. consists of the buttons 'payable' and 'receivable'
		// these buttons will allow for interchange between the rest of the panels that make up the middle section
		// ========================================================================================================
		pyblBtn = new JButton("Payable");		// payable button
		rcvblBtn = new JButton("Receivable");	// receivable button
		
		// add the buttons to a panel with flowlayout (default layout)
		x1y0Pnl = new JPanel();
		x1y0Pnl.add(pyblBtn);
		x1y0Pnl.add(rcvblBtn);
		
		// action listener for payable button
		pyblBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if 'midPanelMode' is already 'true', do nothing
				// else, call the switchMidPanel() method, option 2
				if (midPanelMode)
					return;
				else
					switchMidPanel(2);
			}
		});
		
		// action listener for receivable button
		rcvblBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if 'midPanelMode' is already 'false', do nothing.
				// else, call the switchMidPanel() method, option 1
				if (midPanelMode == false)
					return;
				else
					switchMidPanel(1);
			}
		});
		
		// =====================================================================================
		// second panel of the main middle panel. consists of credit line, pmt term, and factor.
		// can be refreshed depending on ActionListeners of payable and receivable buttons.
		// components of this section will begin with 'x1y1'
		// =====================================================================================
		x1y1cltf = new JTextField(15);			// credit line text field
		x1y1ptcb = new JComboBox<String>(x1y1ptcbl);	// pmt term combo box
		
		// NEW EDIT: the factor combo box list must be a list of the factoring companies stored in the SQl database.
		// set the string array to the names of the factoring companies in the COMPANIES table
		try
		{
			// get the info needed for SQL connection, stored in 'vitals' folder
			File sqlFile = new File("./vitals/sql_info.txt");
			FileReader sqlIn = new FileReader(sqlFile);
			BufferedReader sqlBuffer = new BufferedReader(sqlIn);
			
			String sqlConnectionString = sqlBuffer.readLine();
			String sqlUsernameString = sqlBuffer.readLine();
			String sqlPasswordString = sqlBuffer.readLine();
						
			// make a connection to the SQL server
			Connection connection = DriverManager.getConnection(sqlConnectionString, sqlUsernameString, sqlPasswordString);
			
			// get the result set for names of all factoring companies
			Statement statement = connection.createStatement();
			ResultSet factoringNames = statement.executeQuery("SELECT NAME FROM COMPANIES WHERE ISFACTORING = 'true'");
			
			// copy names from result set to a temporary ArrayList, then copy from ArrayList to String[] array
			ArrayList<String> tempFactoringNamesList = new ArrayList<String>();
			while (factoringNames.next())
				tempFactoringNamesList.add(factoringNames.getString(1));
			x1y1fcbl = new String[tempFactoringNamesList.size() + 1];
			x1y1fcbl[0] = "Select One";
			for (int i = 0; i < tempFactoringNamesList.size(); i++)
				x1y1fcbl[i + 1] = tempFactoringNamesList.get(i);
			
			sqlBuffer.close();
			sqlIn.close();
			statement.close();
			connection.close();
			factoringNames.close();
		}
		catch (FileNotFoundException e) { JOptionPane.showMessageDialog(null, "Vital file not found. Please contact developer for help."); }
		catch (IOException e) { JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help."); }
		catch (SQLException e) { JOptionPane.showMessageDialog(null, "SQLException. Please contact developer for help."); }
		
		x1y1fcb = new JComboBox<String>(x1y1fcbl);		// factor combo box, list of values is the newly modified list of insurance company names
		
		// add a mouse listener for the combo box (x1y1fcb), where when the list is clicked, it is refreshed.
		// this is for the case scenario where the user creates a new factoring company, while the current window is still open
		x1y1fcb.addMouseListener(new MouseListener()
		{
			public void mouseEntered(MouseEvent event)  { }
			public void mouseExited(MouseEvent event)   { }
			public void mousePressed(MouseEvent event)  { }
			public void mouseReleased(MouseEvent event) { }
			
			public void mouseClicked(MouseEvent event)
			{
				try
				{
					// get the info needed for SQL connection, stored in 'vitals' folder
					File sqlFile = new File("./vitals/sql_info.txt");
					FileReader sqlIn = new FileReader(sqlFile);
					BufferedReader sqlBuffer = new BufferedReader(sqlIn);
					
					String sqlConnectionString = sqlBuffer.readLine();
					String sqlUsernameString = sqlBuffer.readLine();
					String sqlPasswordString = sqlBuffer.readLine();
								
					// make a connection to the SQL server
					Connection connection = DriverManager.getConnection(sqlConnectionString, sqlUsernameString, sqlPasswordString);
					
					// get the result set for names of all insurance agency companies
					Statement statement = connection.createStatement();
					ResultSet insAgencyNames = statement.executeQuery("SELECT NAME FROM COMPANIES WHERE ISFACTORING = 'true'");
					
					// copy names from result set to a temporary ArrayList, then copy from ArrayList to the 'x1y1fcbl' String[] array
					ArrayList<String> tempInsNamesList = new ArrayList<String>();
					while (insAgencyNames.next())
						tempInsNamesList.add(insAgencyNames.getString(1));
					x1y1fcbl = new String[tempInsNamesList.size() + 1];
					x1y1fcbl[0] = "Select One";
					for (int i = 0; i < tempInsNamesList.size(); i++)
						x1y1fcbl[i + 1] = tempInsNamesList.get(i);
					
					// clear the items from x1y1fcb (JComboBox), and add the entries of x1y1fcbl
					x1y1fcb.removeAllItems();
					for(int i = 0; i < x1y1fcbl.length; i++)
						x1y1fcb.addItem(x1y1fcbl[i]);
					
					sqlBuffer.close();
					sqlIn.close();
					statement.close();
					connection.close();
					insAgencyNames.close();
				}
				catch (FileNotFoundException e) { JOptionPane.showMessageDialog(null, "Vital file not found. Please contact developer for help."); }
				catch (IOException e) { JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help."); }
				catch (SQLException e) { JOptionPane.showMessageDialog(null, "SQLException. Please contact developer for help."); }
			}
		});
		
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
		
		// ===========================================================================================
		// third panel of the middle panel. consists of address (2 fields), city, state, and zip code.
		// can be refreshed depending on ActionListeners of payable and receivable buttons.
		// components of this section will begin with 'x1y2'
		// ===========================================================================================
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
		
		// =============================================================================================
		// fourth panel of the main middle panel. consists of contact name, cell, phone, fax, and email.
		// also contains buttons '<', '>', 'new', and 'delete' for adding/deleting contacts.
		// components of this section will start with 'x1y3'
		// =============================================================================================
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
		
		// ActionListener for 'new' button. depending on which panel (payable versus receivable) is on display,
		// the contact information will have to be allocated to different storage.
		x1y3nwBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if middle panel is currently on payable
				if (midPanelMode == true)
				{
					// if index is not saved in array list, call save method
					if (payableIndex == payableContactList.size())
					{
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0))
						{
							saveObject(3);
						}
						else
							return;
					}
					// however, if object already exists, update the object
					else
						updateObject(3);
					
					// hop the index to array list max index + 1
					payableIndex = payableContactList.size();
					
					// set all the text fields to null
					x1y3ntf.setText(null);
					x1y3ctf.setText(null);
					x1y3ptf.setText(null);
					x1y3ftf.setText(null);
					x1y3etf.setText(null);
					
				}
				// if middle panel is currently on receivable
				else
				{
					// if the index is one that is not saved, call the save method
					if (receivableIndex == receivableContactList.size())
					{
						// make sure that at least one value != null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0))
						{
							saveObject(4);
						}
						else
							return;
					}
					// however, if object already exists, update the object
					else
						updateObject(4);
					
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
		x1y3delBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if middle mode is currently payable mode
				if (midPanelMode == true)
				{
					// if object is not saved in list yet, clear the text fields
					if (payableIndex >= payableContactList.size())
					{
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
					if (payableContactList.size() == 0)
					{
						x1y3ntf.setText(null);
						x1y3ctf.setText(null);
						x1y3ptf.setText(null);
						x1y3ftf.setText(null);
						x1y3etf.setText(null);
					}
					else
					{
						// if index is within bounds, set to next object in list
						if (payableIndex <= payableContactList.size() - 1) {
							x1y3ntf.setText(payableContactList.get(payableIndex).getName());
							x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
							x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
							x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
							x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
						
						}
						// if index is out of bounds, move one backwards and display that object's values	
						else
						{
							payableIndex--;
							x1y3ntf.setText(payableContactList.get(payableIndex).getName());
							x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
							x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
							x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
							x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
						}
					}
				}
				// if middle mode is currently receivable mode	
				else
				{
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
					if (receivableContactList.size() == 0)
					{
						x1y3ntf.setText(null);
						x1y3ctf.setText(null);
						x1y3ptf.setText(null);
						x1y3ftf.setText(null);
						x1y3etf.setText(null);
					}
					else
					{
						// if index is within bounds, set to next object in list
						if (receivableIndex <= receivableContactList.size() - 1)
						{
							x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
							x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
							x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
							x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
							x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
						
						}
						// if index is out of bounds, move one backwards and display that object's values	
						else
						{
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
		x1y3prevBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the current mode is on payable
				if (midPanelMode == true)
				{
					// if index is one that is not saved in list, call the save method
					if (payableIndex == payableContactList.size())
					{
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0))
						{
							saveObject(3);
						}
					}
					// however, if object already exists, call the update method	
					else
						updateObject(3);
					
					// if index is already 0, or list is empty, do nothing
					if ((payableIndex == 0) || (payableContactList.size() == 0))
						return;
					// otherwise, set index to one before and display that object's values
					else
					{
						payableIndex--;
						x1y3ntf.setText(payableContactList.get(payableIndex).getName());
						x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
						x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
						x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
						x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
					}
				}
				// if current mode is on receivable	
				else
				{
					// if index is one that is not saved in list, call the save method
					if (receivableIndex == receivableContactList.size())
					{
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0))
						{
							saveObject(4);
						}
					}
					// however, if object already exists, call the update method
					else
						updateObject(4);
					
					// if index is already 0, or list is empty, do nothing
					if ((receivableIndex == 0) || (receivableContactList.size() == 0))
						return;
					// otherwise, set index to one before and display that object's values
					else
					{
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
		x1y3nxtBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the current mode is on payable
				if (midPanelMode == true)
				{
					// if the index is one that is not save in list, call the save method
					if (payableIndex == payableContactList.size())
					{
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0))
						{
							saveObject(3);
						}
					}
					// however, if object already exists, call the update method
					else
						updateObject(3);
					
					// if the index has already reached the max, do nothing
					if (payableIndex >= payableContactList.size() - 1)
						return;
					else
					{
						// add one to the index, and display the corresponding object
						payableIndex++;
						x1y3ntf.setText(payableContactList.get(payableIndex).getName());
						x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
						x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
						x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
						x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
					}
				}
				// if the current mode is on receivable	
				else
				{
					// if the index is one that is not save in list, call the save method
					if (receivableIndex == receivableContactList.size())
					{
						// make sure that at least one value is not null
						if ((x1y3ntf.getText().length() != 0) ||
							(x1y3ctf.getText().length() != 0) ||
							(x1y3ptf.getText().length() != 0) ||
							(x1y3ftf.getText().length() != 0) ||
							(x1y3etf.getText().length() != 0))
						{
							saveObject(4);
						}
					}
					// however, if object already exists, call the update method
					else
						updateObject(4);
					
					// if the index has already reached the max, do nothing
					if (receivableIndex >= receivableContactList.size() - 1)
						return;
					else
					{
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
		
		// ===============================================================================================================================
		// top right corner. consists of dot#, scac, contract/common, table of general, auto, cargo vs. policy#, coverage, and expiration.
		// also contains target goods, deductibles. components of this section will start with 'trc'
		// ===============================================================================================================================
		trcnicb = new JButton("New");		// new insurance company button
		
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
		
		trccccb = new JComboBox<String>(trccccbl);	// contact/common combo box
		trcrcb = new JComboBox<String>(trcrcbl);	// rating combo box
		
		// NEW EDIT: the insurance combo box must be a list of insurance companies stored in the SQL databse.
		// retrieve the list of insurance companies, and set it as the values of trciacbl (String[] array of the combo box)
		try
		{
			// get the info needed for SQL connection, stored in the 'vitals' folder
			File sqlFile = new File("./vitals/sql_info.txt");
			FileReader sqlIn = new FileReader(sqlFile);
			BufferedReader sqlBuffer = new BufferedReader(sqlIn);
			
			String sqlConnectionString = sqlBuffer.readLine();
			String sqlUsernameString = sqlBuffer.readLine();
			String sqlPasswordString = sqlBuffer.readLine();
			
			// make a connection to the SQL server
			Connection connection = DriverManager.getConnection(sqlConnectionString, sqlUsernameString, sqlPasswordString);
			
			// get the result set for names all of insurance agency companies
			Statement statement = connection.createStatement();
			ResultSet insAgencyNames = statement.executeQuery("SELECT NAME FROM COMPANIES WHERE ISINSAGENCY = 'true'");
			
			// copy names from result set to a temporary ArrayList, then copy the data over to trciacbl, the String[] array
			ArrayList<String> tempInsNamesList = new ArrayList<String>();
			while (insAgencyNames.next())
				tempInsNamesList.add(insAgencyNames.getString(1));
			trciacbl = new String[tempInsNamesList.size() + 1];
			trciacbl[0] = "Select One";
			for (int i = 0; i < tempInsNamesList.size(); i++)
				trciacbl[i + 1] = tempInsNamesList.get(i);
			
			sqlBuffer.close();
			sqlIn.close();
			statement.close();
			connection.close();
			insAgencyNames.close();
		}
		catch (FileNotFoundException e) { JOptionPane.showMessageDialog(null, "Vital file not found. Please contact developer for help."); }
		catch (IOException e) { JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help."); }
		catch (SQLException e) { JOptionPane.showMessageDialog(null, "SQLException. Please contact developer for help."); }
				
		trciacb = new JComboBox<String>(trciacbl);	// ins agency combo box
		
		// add a mouse listener for the combo box (trciacb), where when the box is clicked, a list of refreshed insurance agency names is displayed
		// this is for the case scenario where a new insurance company is created while current window is still open
		trciacb.addMouseListener(new MouseListener()
		{
			public void mouseEntered(MouseEvent event)  { };
			public void mouseExited(MouseEvent event)   { };
			public void mousePressed(MouseEvent event)  { };
			public void mouseReleased(MouseEvent event) { };
			
			public void mouseClicked(MouseEvent event)
			{
				try
				{
					// get the info needed for SQL connection, stored in the 'vitals' folder
					File sqlFile = new File("./vitals/sql_info.txt");
					FileReader sqlIn = new FileReader(sqlFile);
					BufferedReader sqlBuffer = new BufferedReader(sqlIn);
					
					String sqlConnectionString = sqlBuffer.readLine();
					String sqlUsernameString = sqlBuffer.readLine();
					String sqlPasswordString = sqlBuffer.readLine();
					
					// make a connection to the SQL server
					Connection connection = DriverManager.getConnection(sqlConnectionString, sqlUsernameString, sqlPasswordString);
					
					// get the result set for names all of insurance agency companies
					Statement statement = connection.createStatement();
					ResultSet insAgencyNames = statement.executeQuery("SELECT NAME FROM COMPANIES WHERE ISINSAGENCY = 'true'");
					
					// copy names from result set to a temporary ArrayList, then copy the data over to trciacbl, the String[] array
					ArrayList<String> tempInsNamesList = new ArrayList<String>();
					while (insAgencyNames.next())
						tempInsNamesList.add(insAgencyNames.getString(1));
					trciacbl = new String[tempInsNamesList.size() + 1];
					trciacbl[0] = "Select One";
					for (int i = 0; i < tempInsNamesList.size(); i++)
						trciacbl[i + 1] = tempInsNamesList.get(i);
					
					// clear the items from trciacb (JComboBox), and add the entries of trciacbl (String[] array) to it
					trciacb.removeAllItems();
					for (int i = 0; i < trciacbl.length; i++)
						trciacb.addItem(trciacbl[i]);
					
					sqlBuffer.close();
					sqlIn.close();
					statement.close();
					connection.close();
					insAgencyNames.close();
				}
				catch (FileNotFoundException e) { JOptionPane.showMessageDialog(null, "Vital file not found. Please contact developer for help."); }
				catch (IOException e) { JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help."); }
				catch (SQLException e) { JOptionPane.showMessageDialog(null, "SQLException. Please contact developer for help."); }
			}
		});
		
		// EDIT: ADDED '<', '>', 'NEW', AND 'DELETE' BUTTONS. ALSO ADDED A 'INSURER' LABEL AND CORRESPONDING TEXT BOX
		insurerLeft = new JButton("<");				// '<' button
		insurerRight = new JButton(">");			// '>' button			
		insurerNew = new JButton("New");			// 'New' button
		insurerDelete = new JButton("Delete");		// 'Delete' button
		insurerLabel = new JLabel("Insurer: ");		// 'Insurer' label
		insurerTextField = new JTextField(10);		// 'Insurer' text field
		
		// using GridBagLayout, add the components to a panel
		trcPnl = new JPanel(); trcPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; trcPnl.add(trcratinglbl, c);		// rating label at (0, 0)
		c.gridx = 1; c.gridy = 0; trcPnl.add(trcrcb, c);			// rating combo box at (1, 0)
		c.gridx = 3; c.gridy = 0; trcPnl.add(trccccb, c);			// contact/common combo box at (3, 0)
				
		c.gridx = 0; c.gridy = 1; trcPnl.add(trcinsagencylbl, c);	// ins agency label at (0, 1)
		c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 1; trcPnl.add(trciacb, c);			// ins agency combo box at (1, 1)
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 3; c.gridy = 1; trcPnl.add(trcnicb, c);			// new button at (3, 1)
		
		c.gridx = 0; c.gridy = 2; trcPnl.add(insurerLeft, c);		// left increment button at (0, 2)
		c.gridx = 1; c.gridy = 2; trcPnl.add(insurerRight, c);		// right increment button at (1, 2)
		c.gridx = 2; c.gridy = 2; trcPnl.add(insurerNew, c);		// new button at (2, 2)
		c.gridx = 3; c.gridy = 2; trcPnl.add(insurerDelete, c);		// delete button at (3, 2)
		
		c.gridx = 0; c.gridy = 3; trcPnl.add(insurerLabel, c);		// insurer label at (0, 3)
		c.gridwidth = 3; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 3; trcPnl.add(insurerTextField, c);	// insurer text field at (1, 3)
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		
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
		
		// action listener for "new insurance company" button. open a new instance of CompanyMaker.
		trcnicb.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new CompanyMaker(); } });
			}
		});
		
		// ActionListener for 'New' button (for insurer objects)
		insurerNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (insurerIndex == insurerList.size())
				{
					// make sure that at least one value is not null
					if (insurerTextField.getText().length() != 0 ||
						trcgptf.getText().length() != 0 ||
						trcgctf.getText().length() != 0 ||
						trcgetf.getText().length() != 0 ||
						trcaptf.getText().length() != 0 ||
						trcactf.getText().length() != 0 ||
						trcaetf.getText().length() != 0 ||
						trccptf.getText().length() != 0 ||
						trccctf.getText().length() != 0 ||
						trccetf.getText().length() != 0)
					{
						saveObject(5);
					}
				}
				// however, if object already exists, update any changes that might have been made	
				else
					updateObject(5);
				
				// hop the index to array list max index + 1
				insurerIndex = insurerList.size();
				
				// set all the text fields to null
				insurerTextField.setText(null);
				trcgptf.setText(null);
				trcgctf.setText(null);
				trcgetf.setText(null);
				trcaptf.setText(null);
				trcactf.setText(null);
				trcaetf.setText(null);
				trccptf.setText(null);
				trccctf.setText(null);
				trccetf.setText(null);
				trctgdtf.setText(null);
			}
		});
		
		// ActionListener for 'Delete' button (for insurer objects)
		insurerDelete.addActionListener(new ActionListener ()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if object is not save in array list yet, simply clear the text fields and return
				if (insurerIndex >= insurerList.size())
				{
					insurerTextField.setText(null);
					trcgptf.setText(null);
					trcgctf.setText(null);
					trcgetf.setText(null);
					trcaptf.setText(null);
					trcactf.setText(null);
					trcaetf.setText(null);
					trccptf.setText(null);
					trccctf.setText(null);
					trccetf.setText(null);
					trctgdtf.setText(null);
					return;
				}
				
				// otherwise, delete the current CompanyContact object
				insurerList.remove(insurerIndex);
				
				// if array list is empty, reset everything to null
				if (companyContactList.size() == 0)
				{
					insurerTextField.setText(null);
					trcgptf.setText(null);
					trcgctf.setText(null);
					trcgetf.setText(null);
					trcaptf.setText(null);
					trcactf.setText(null);
					trcaetf.setText(null);
					trccptf.setText(null);
					trccctf.setText(null);
					trccetf.setText(null);
					trctgdtf.setText(null);
				}
				else
				{
					// if index is still within bounds, set to next object in list
					if (insurerIndex <= insurerList.size() - 1)
					{
						insurerTextField.setText(insurerList.get(insurerIndex).getInsurer());
						trcgptf.setText(insurerList.get(insurerIndex).getGeneral_policy());
						trcgctf.setText(insurerList.get(insurerIndex).getGeneral_coverage());
						trcgetf.setText(insurerList.get(insurerIndex).getGeneral_expiration());
						trcaptf.setText(insurerList.get(insurerIndex).getAuto_policy());
						trcactf.setText(insurerList.get(insurerIndex).getAuto_coverage());
						trcaetf.setText(insurerList.get(insurerIndex).getAuto_expiration());
						trccptf.setText(insurerList.get(insurerIndex).getCargo_policy());
						trccctf.setText(insurerList.get(insurerIndex).getCargo_coverage());
						trccetf.setText(insurerList.get(insurerIndex).getCargo_expiration());
						trctgdtf.setText(insurerList.get(insurerIndex).getTargetGoodsDeductibles());
					}
					// if index is out of bounds, move index one backwards and display values of that object
					else
					{
						insurerIndex--;
						
						insurerTextField.setText(insurerList.get(insurerIndex).getInsurer());
						trcgptf.setText(insurerList.get(insurerIndex).getGeneral_policy());
						trcgctf.setText(insurerList.get(insurerIndex).getGeneral_coverage());
						trcgetf.setText(insurerList.get(insurerIndex).getGeneral_expiration());
						trcaptf.setText(insurerList.get(insurerIndex).getAuto_policy());
						trcactf.setText(insurerList.get(insurerIndex).getAuto_coverage());
						trcaetf.setText(insurerList.get(insurerIndex).getAuto_expiration());
						trccptf.setText(insurerList.get(insurerIndex).getCargo_policy());
						trccctf.setText(insurerList.get(insurerIndex).getCargo_coverage());
						trccetf.setText(insurerList.get(insurerIndex).getCargo_expiration());
						trctgdtf.setText(insurerList.get(insurerIndex).getTargetGoodsDeductibles());
					}
				}
			}
		});
		
		// ActionListener for the '<' button (for insurer objects)
		insurerLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (insurerIndex == insurerList.size())
				{
					// make sure that at least one value is not null
					if (insurerTextField.getText().length() != 0 ||
						trcgptf.getText().length() != 0 ||
						trcgctf.getText().length() != 0 ||
						trcgetf.getText().length() != 0 ||
						trcaptf.getText().length() != 0 ||
						trcactf.getText().length() != 0 ||
						trcaetf.getText().length() != 0 ||
						trccptf.getText().length() != 0 ||
						trccctf.getText().length() != 0 ||
						trccetf.getText().length() != 0)
					{
						saveObject(5);
					}
				}
				// however, if object already exists, update any changes that might have been made	
				else
					updateObject(5);
				
				// simply return if the index is already 0, or array list is empty
				if ((insurerIndex == 0) || (insurerList.size() == 0))
					return;
				// otherwise, set index to one before and display that object's values
				else
				{
					insurerIndex--;
					
					insurerTextField.setText(insurerList.get(insurerIndex).getInsurer());
					trcgptf.setText(insurerList.get(insurerIndex).getGeneral_policy());
					trcgctf.setText(insurerList.get(insurerIndex).getGeneral_coverage());
					trcgetf.setText(insurerList.get(insurerIndex).getGeneral_expiration());
					trcaptf.setText(insurerList.get(insurerIndex).getAuto_policy());
					trcactf.setText(insurerList.get(insurerIndex).getAuto_coverage());
					trcaetf.setText(insurerList.get(insurerIndex).getAuto_expiration());
					trccptf.setText(insurerList.get(insurerIndex).getCargo_policy());
					trccctf.setText(insurerList.get(insurerIndex).getCargo_coverage());
					trccetf.setText(insurerList.get(insurerIndex).getCargo_expiration());
					trctgdtf.setText(insurerList.get(insurerIndex).getTargetGoodsDeductibles());
				}
			}
		});
		
		// ActionListener for '>' button (for insurer objects)
		insurerRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (insurerIndex == insurerList.size())
				{
					// make sure that at least one value is not null
					if (insurerTextField.getText().length() != 0 ||
						trcgptf.getText().length() != 0 ||
						trcgctf.getText().length() != 0 ||
						trcgetf.getText().length() != 0 ||
						trcaptf.getText().length() != 0 ||
						trcactf.getText().length() != 0 ||
						trcaetf.getText().length() != 0 ||
						trccptf.getText().length() != 0 ||
						trccctf.getText().length() != 0 ||
						trccetf.getText().length() != 0)
					{
						saveObject(5);
					}
				}
				// however, if object already exists, update any changes that might have been made
				else
					updateObject(5);
				
				// if index is already reached the max, do nothing
				if (insurerIndex >= insurerList.size() - 1)
					return;
				// else, add one to the index, and display the corresponding object's values
				else
				{
					insurerIndex++;
					
					insurerTextField.setText(insurerList.get(insurerIndex).getInsurer());
					trcgptf.setText(insurerList.get(insurerIndex).getGeneral_policy());
					trcgctf.setText(insurerList.get(insurerIndex).getGeneral_coverage());
					trcgetf.setText(insurerList.get(insurerIndex).getGeneral_expiration());
					trcaptf.setText(insurerList.get(insurerIndex).getAuto_policy());
					trcactf.setText(insurerList.get(insurerIndex).getAuto_coverage());
					trcaetf.setText(insurerList.get(insurerIndex).getAuto_expiration());
					trccptf.setText(insurerList.get(insurerIndex).getCargo_policy());
					trccctf.setText(insurerList.get(insurerIndex).getCargo_coverage());
					trccetf.setText(insurerList.get(insurerIndex).getCargo_expiration());
					trctgdtf.setText(insurerList.get(insurerIndex).getTargetGoodsDeductibles());
				}
			}
		});
		
		// =========================================================================================
		// bottom right corner. consists of contact name, cell#, phone, fax, and email.
		// requires buttons '<', '>', 'new', and 'delete' for creating & deleting multiple contacts.
		// components of this section will start with 'brc'
		// =========================================================================================
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
		
		// ActionListener for 'new' button
		// when activated, saves current contact data to an ArrayList of InsuranceContact objects, which can be modified later
		brcnewbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				//if the index is one that is not save in array list, call the save method
				if (insuranceIndex == insuranceContactList.size())
				{
					// make sure that at least one value is not null
					if ((brcntf.getText().length() != 0) ||
						(brcctf.getText().length() != 0) ||
						(brcptf.getText().length() != 0) ||
						(brcftf.getText().length() != 0) ||
						(brcetf.getText().length() != 0))
					{
						saveObject(2);
					}
					else
						return;
				}
				// however, if object already exists, call the update method
				else
					updateObject(2);
				
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
		brcdelbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if object is not saved in array list yet, simply clear the text fields and return
				if (insuranceIndex >= insuranceContactList.size())
				{
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
				if (insuranceContactList.size() == 0)
				{
					brcntf.setText(null);
					brcctf.setText(null);
					brcptf.setText(null);
					brcftf.setText(null);
					brcetf.setText(null);
				}
				else
				{
					// if index is still within bounds, set to next object in list
					if (insuranceIndex <= insuranceContactList.size() - 1)
					{
						brcntf.setText(insuranceContactList.get(insuranceIndex).getName());
						brcctf.setText(insuranceContactList.get(insuranceIndex).getCell());
						brcptf.setText(insuranceContactList.get(insuranceIndex).getPhone());
						brcftf.setText(insuranceContactList.get(insuranceIndex).getFax());
						brcetf.setText(insuranceContactList.get(insuranceIndex).getEmail());
					
					}
					// if index is out of bounds, move index one backwards and display values
					else
					{
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
		brcprevbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (insuranceIndex == insuranceContactList.size())
				{
					// make sure that at least one value is not null
					if ((brcntf.getText().length() != 0) ||
						(brcctf.getText().length() != 0) ||
						(brcptf.getText().length() != 0) ||
						(brcftf.getText().length() != 0) ||
						(brcetf.getText().length() != 0))
					{
						saveObject(2);
					}
				}
				// however, if object already exists, call the update method
				else
					updateObject(2);
				
				// simply return if the index is already 0, or the array list is empty
				if ((insuranceIndex == 0) || (insuranceContactList.size() == 0))
					return;
				else
				{
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
		brcnxtbtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in array list, call the save method
				if (insuranceIndex == insuranceContactList.size())
				{
					// make sure that at least one value is not null
					if ((brcntf.getText().length() != 0) ||
						(brcctf.getText().length() != 0) ||
						(brcptf.getText().length() != 0) ||
						(brcftf.getText().length() != 0) ||
						(brcetf.getText().length() != 0))
					{
						saveObject(2);
					}
				}
				// however, if object already exists, call the update method
				else
					updateObject(2);
				
				// if the index is already reached the max, do nothing
				if (insuranceIndex >= insuranceContactList.size() - 1)
					return;
				else
				{
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
		
		// combine the top right corner and bottom left corner panels into one panel named 'rightMdPnl'
		rightMdPnl = new JPanel(); rightMdPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5); c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1; c.gridx = 0;
		rightMdPnl.add(trcPnl, c);
		rightMdPnl.add(brcPnl, c);
		
		// temporary: add borders around the smaller right panels
		trcPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		brcPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		
		// add the large panels onto the main panel.
		// use a GridBagLayout to ensure that the three large panels fit well onto the main panel
		mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 1; mainPanel.add(lftMdPnl, c);
		c.gridx = 1; c.gridy = 1; mainPanel.add(midPnl, c);
		c.gridx = 2; c.gridy = 1; mainPanel.add(rightMdPnl, c);
		
		// edit: since the layout is not coming out as desired, created a new panel called 'ultraMainPanel' and
		// used a GridBagLayout vertically to add topmost panel and the rest of the panels (mainPanel) together
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
		
	// method: saves data to an ArrayList object. integer input determines the save type
	private void saveObject(int saveOption)
	{
		// type 1 save is for CompanyContact objects
		if (saveOption == 1)
		{
			CompanyContact object = new CompanyContact();
			
			object.storeName(x0y2ntf.getText());
			object.storeCell(x0y2ctf.getText());
			object.storePhone(x0y2ptf.getText());
			object.storeFax(x0y2ftf.getText());
			object.storeEmail(x0y2etf.getText());

			companyContactList.add(object);
		}
		// type 2 save is for InsuranceContact objects
		else if (saveOption == 2)
		{
			InsuranceContact object = new InsuranceContact();
			
			object.storeName(brcntf.getText());
			object.storeCell(brcctf.getText());
			object.storePhone(brcptf.getText());
			object.storeFax(brcftf.getText());
			object.storeEmail(brcetf.getText());

			insuranceContactList.add(object);
		}
		// type 3 save is for PayableContact objects
		else if (saveOption == 3)
		{
			PayableContact object = new PayableContact();

			object.storeName(x1y3ntf.getText());
			object.storeCell(x1y3ctf.getText());
			object.storePhone(x1y3ptf.getText());
			object.storeFax(x1y3ftf.getText());
			object.storeEmail(x1y3etf.getText());

			payableContactList.add(object);
		}
		// type 4 save is for ReceivableContact objects	
		else if (saveOption == 4)
		{
			ReceivableContact object = new ReceivableContact();

			object.storeName(x1y3ntf.getText());
			object.storeCell(x1y3ctf.getText());
			object.storePhone(x1y3ptf.getText());
			object.storeFax(x1y3ftf.getText());
			object.storeEmail(x1y3etf.getText());

			receivableContactList.add(object);
		}
		// type 5 save is for InsurerCompany object
		else if (saveOption == 5)
		{
			InsurerCompany object = new InsurerCompany();
			
			object.setInsurer(insurerTextField.getText());
			object.setGeneral_policy(trcgptf.getText());
			object.setGeneral_coverage(trcgctf.getText());
			object.setGeneral_expiration(trcgetf.getText());
			object.setAuto_policy(trcaptf.getText());
			object.setAuto_coverage(trcactf.getText());
			object.setAuto_expiration(trcaetf.getText());
			object.setCargo_policy(trccptf.getText());
			object.setCargo_coverage(trccctf.getText());
			object.setCargo_expiration(trccetf.getText());
			object.setTargetGoodsDeductibles(trctgdtf.getText());
			
			insurerList.add(object);
		}
	}
	
	// method: updates a current ArrayList object. integer input determines update type
	private void updateObject(int option)
	{
		// type 1 update is for CompanyContact objects
		if (option == 1)
		{
			companyContactList.get(companyIndex).storeName(x0y2ntf.getText());
			companyContactList.get(companyIndex).storeCell(x0y2ctf.getText());
			companyContactList.get(companyIndex).storePhone(x0y2ptf.getText());
			companyContactList.get(companyIndex).storeFax(x0y2ftf.getText());
			companyContactList.get(companyIndex).storeEmail(x0y2etf.getText());
		}
		// type 2 update is for InsuranceContact objects
		else if (option == 2)
		{
			insuranceContactList.get(insuranceIndex).storeName(brcntf.getText());
			insuranceContactList.get(insuranceIndex).storeCell(brcctf.getText());
			insuranceContactList.get(insuranceIndex).storePhone(brcptf.getText());
			insuranceContactList.get(insuranceIndex).storeFax(brcftf.getText());
			insuranceContactList.get(insuranceIndex).storeEmail(brcetf.getText());
		}
		// type 3 update is for PayableContact objects
		else if (option == 3)
		{
			payableContactList.get(payableIndex).storeName(x1y3ntf.getText());
			payableContactList.get(payableIndex).storeCell(x1y3ctf.getText());
			payableContactList.get(payableIndex).storePhone(x1y3ptf.getText());
			payableContactList.get(payableIndex).storeFax(x1y3ftf.getText());
			payableContactList.get(payableIndex).storeEmail(x1y3etf.getText());
		}
		// type 4 update is for ReceivableContact objects
		else if (option == 4)
		{
			receivableContactList.get(receivableIndex).storeName(x1y3ntf.getText());
			receivableContactList.get(receivableIndex).storeCell(x1y3ctf.getText());
			receivableContactList.get(receivableIndex).storePhone(x1y3ptf.getText());
			receivableContactList.get(receivableIndex).storeFax(x1y3ftf.getText());
			receivableContactList.get(receivableIndex).storeEmail(x1y3etf.getText());
		}
		// type 5 update is for InsurerCompany objects
		else if (option == 5)
		{
			insurerList.get(insurerIndex).setInsurer(insurerTextField.getText());
			insurerList.get(insurerIndex).setGeneral_policy(trcgptf.getText());
			insurerList.get(insurerIndex).setGeneral_coverage(trcgctf.getText());
			insurerList.get(insurerIndex).setGeneral_expiration(trcgetf.getText());
			insurerList.get(insurerIndex).setAuto_policy(trcaptf.getText());
			insurerList.get(insurerIndex).setAuto_coverage(trcactf.getText());
			insurerList.get(insurerIndex).setAuto_expiration(trcaetf.getText());
			insurerList.get(insurerIndex).setCargo_policy(trccptf.getText());
			insurerList.get(insurerIndex).setCargo_coverage(trccctf.getText());
			insurerList.get(insurerIndex).setCargo_expiration(trccetf.getText());
			insurerList.get(insurerIndex).setTargetGoodsDeductibles(trctgdtf.getText());
		}
	}
	
	// method: controls the actions that take place when a user clicks the payable or receivable button.
	// depending on the boolean value of midPanelMode, this method will take the necessary steps to save the data on the panel,
	// and then switch to a new blank panel
	private void switchMidPanel(int switchType)
	{
		// switchType 1 is payable to receivable
		if (switchType == 1)
		{
			// switch panel mode to false
			midPanelMode = false;
			
			// save the information on the payable panel
			payableCompany.storeCreditLine(x1y1cltf.getText());
			payableCompany.storePmtTerm((String)x1y1ptcb.getSelectedItem());
			payableCompany.storeFactor((String)x1y1fcb.getSelectedItem());
			payableCompany.storeAddressLine1(x1y2a1tf.getText());
			payableCompany.storeAddressLine2(x1y2a2tf.getText());
			payableCompany.storeCity(x1y2ctf.getText());
			payableCompany.storeState(x1y2stf.getText());
			payableCompany.storeZip(x1y2ztf.getText());
			
			// save the last entry for payable contact, if it is not already saved
			if (payableIndex == payableContactList.size())
			{
				// make sure at least one value is not null
				if ((x1y3ntf.getText().length() != 0) ||
					(x1y3ctf.getText().length() != 0) ||
					(x1y3ptf.getText().length() != 0) ||
					(x1y3ftf.getText().length() != 0) ||
					(x1y3etf.getText().length() != 0))
				{
					saveObject(3);
				}
			}
			// however, if it is not the last entry, update that object	
			else
				updateObject(3);
				
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
			if (receivableContactList.size() != 0)
			{
				// set the receivable index to that of the last entry in the list
				receivableIndex = receivableContactList.size() - 1;
				x1y3ntf.setText(receivableContactList.get(receivableIndex).getName());
				x1y3ctf.setText(receivableContactList.get(receivableIndex).getCell());
				x1y3ptf.setText(receivableContactList.get(receivableIndex).getPhone());
				x1y3ftf.setText(receivableContactList.get(receivableIndex).getFax());
				x1y3etf.setText(receivableContactList.get(receivableIndex).getEmail());
			}
			else
			{
				receivableIndex = 0;
				x1y3ntf.setText(null);
				x1y3ctf.setText(null);
				x1y3ptf.setText(null);
				x1y3ftf.setText(null);
				x1y3etf.setText(null);
			}
		}
		// if switchType == 2, transition from receivable to payable
		else
		{
			// switch panel mode to true
			midPanelMode = true;
			
			// save the information on the receivable panel
			receivableCompany.storeCreditLine(x1y1cltf.getText());
			receivableCompany.storePmtTerm((String)x1y1ptcb.getSelectedItem());
			receivableCompany.storeFactor((String)x1y1fcb.getSelectedItem());
			receivableCompany.storeAddressLine1(x1y2a1tf.getText());
			receivableCompany.storeAddressLine2(x1y2a2tf.getText());
			receivableCompany.storeCity(x1y2ctf.getText());
			receivableCompany.storeState(x1y2stf.getText());
			receivableCompany.storeZip(x1y2ztf.getText());
			
			// if current entry of contact is not saved, add the object to the receivable list
			if (receivableIndex == receivableContactList.size())
			{
				// make sure that at least one value is not null
				if ((x1y3ntf.getText().length() != 0) ||
					(x1y3ctf.getText().length() != 0) ||
					(x1y3ptf.getText().length() != 0) ||
					(x1y3ftf.getText().length() != 0) ||
					(x1y3etf.getText().length() != 0))
				{
					saveObject(4);
				}
			}
			// however, if object already exists, call the update method	
			else
			{
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
			if (payableContactList.size() != 0)
			{
				payableIndex = payableContactList.size() - 1;
				x1y3ntf.setText(payableContactList.get(payableIndex).getName());
				x1y3ctf.setText(payableContactList.get(payableIndex).getCell());
				x1y3ptf.setText(payableContactList.get(payableIndex).getPhone());
				x1y3ftf.setText(payableContactList.get(payableIndex).getFax());
				x1y3etf.setText(payableContactList.get(payableIndex).getEmail());
			}
			else
			{
				payableIndex = 0;
				x1y3ntf.setText(null);
				x1y3ctf.setText(null);
				x1y3ptf.setText(null);
				x1y3ftf.setText(null);
				x1y3etf.setText(null);
			}
		}
	}
		
	// method: saves all the info and stores it to an SQL Database
	private void saveAndStore()
	{
		// temporarily save all information into storage devices.
		// for fields that do not have "multiples of", save the data to an ArrayList
		// for object types, save/update the information to its respective ArrayList
		
		// save all info that is non-changing (non-object types)
		companyGeneralInfo = new ArrayList<String>();
		
		companyGeneralInfo.add(x0y0Name.getText());					// Name
		companyGeneralInfo.add(x0y0DBA.getText());					// DBA
		companyGeneralInfo.add(x0y0Tel.getText());					// Tel
		companyGeneralInfo.add(x0y0TaxID.getText());				// TaxID
		companyGeneralInfo.add(x0y0Fax.getText());					// Fax
		companyGeneralInfo.add(x0y0MC.getText());					// MC#
		companyGeneralInfo.add(x0y0Established.getText());			// Established
		companyGeneralInfo.add(trcdottf.getText());					// DOT#
		companyGeneralInfo.add(trcscactf.getText());				// SCAC

		companyGeneralInfo.add(x0y1ad1tf.getText());				// Address (line 1)
		companyGeneralInfo.add(x0y1ad2tf.getText());				// Address (line 2)
		companyGeneralInfo.add(x0y1ctf.getText());					// City
		companyGeneralInfo.add(x0y1stf.getText());					// State
		companyGeneralInfo.add(x0y1ztf.getText());					// Zip
		
		companyGeneralInfo.add((String)trcrcb.getSelectedItem());	// Rating
		companyGeneralInfo.add((String)trccccb.getSelectedItem());	// Contract/Common
		companyGeneralInfo.add((String)trciacb.getSelectedItem());	// Ins. Agency		

		companyGeneralInfo.add(carrierType.getState()? "true" : "false");		// carrier?
		companyGeneralInfo.add(brokerType.getState()? "true" : "false");		// broker?
		companyGeneralInfo.add(customerType.getState()? "true" : "false");		// customer?
		companyGeneralInfo.add(shipperType.getState()? "true" : "false");		// shipper?
		companyGeneralInfo.add(consigneeType.getState()? "true" : "false");		// consignee?
		companyGeneralInfo.add(insAgencyType.getState()? "true" : "false");		// insurance agency?
		companyGeneralInfo.add(factoringType.getState()? "true" : "false");		// factoring?
		companyGeneralInfo.add(preferredType.getState()? "true" : "false");		// preferred?
		companyGeneralInfo.add(vendorType.getState()? "true" : "false");		// vendor?
		companyGeneralInfo.add(ownerOperatorType.getState()? "true" : "false");	// owner/operator?
		companyGeneralInfo.add(otherType.getState()? "true" : "false");			// other?
		
		// the contacts panels for company, payable, receivable, and insurance may also have been modified before the save button is pressed
		// check the state of each one of them and save/update the information
		
		// if CompanyContact object does not exist, call the save method
		if (companyIndex == companyContactList.size())
		{
			// make sure that at least one value is not null
			if ((x0y2ntf.getText().length() != 0) ||
				(x0y2ctf.getText().length() != 0) ||
				(x0y2ptf.getText().length() != 0) ||
				(x0y2ftf.getText().length() != 0) ||
				(x0y2etf.getText().length() != 0))
			{
				saveObject(1);
			}	
		}
		// else, object already exists, call the update method
		else
			updateObject(1);
		
		// if InsuranceContact object does not exist, call the save method
		if (insuranceIndex == insuranceContactList.size())
		{
			// make sure at least one value is not null
			if ((brcntf.getText().length() != 0) ||
				(brcctf.getText().length() != 0) ||
				(brcptf.getText().length() != 0) ||
				(brcftf.getText().length() != 0) ||
				(brcetf.getText().length() != 0))
			{
				saveObject(2);
			}
		} 
		// if object already exists, call the update method
		else
			updateObject(2);
		
		// EDIT: DO THE SAME FOR INSURER COMPANY OBJECTS
		// ---------------------------------------------
		// if InsurerCompany object does not exist, call the save method
		if (insurerIndex == insurerList.size())
		{
			// make sure that at least one text field is not null
			if (insurerTextField.getText().length() != 0 ||
				trcgptf.getText().length() != 0 ||
				trcgctf.getText().length() != 0 ||
				trcgetf.getText().length() != 0 ||
				trcaptf.getText().length() != 0 ||
				trcactf.getText().length() != 0 ||
				trcaetf.getText().length() != 0 ||
				trccptf.getText().length() != 0 ||
				trccctf.getText().length() != 0 ||
				trccetf.getText().length() != 0)
			{
				saveObject(5);
			}
		}
		// else, object already exists, call the update method
		else
			updateObject(5);
		
		// =======================================================================================
		// SAVING/UPDATING THE TEXT FIELDS OF THE MIDDLE SECTION DEPENDS ON THE midPanelMode VALUE
		// =======================================================================================
		
		// if the panel is on payable mode
		if (midPanelMode == true)
		{
			// if PayableContact object does not exist, call the save method
			// make sure at least one value is not null
			if (payableIndex == payableContactList.size())
			{
				if ((x1y3ntf.getText().length() != 0) ||
					(x1y3ctf.getText().length() != 0) ||
					(x1y3ptf.getText().length() != 0) ||
					(x1y3ftf.getText().length() != 0) ||
					(x1y3etf.getText().length() != 0))
				{
					saveObject(3);
				}
			}
			// if object exists, call the update method
			else
				updateObject(3);
			
			// save/update the data of the payable panel
			payableCompany.storeCreditLine(x1y1cltf.getText());
			payableCompany.storePmtTerm((String)x1y1ptcb.getSelectedItem());
			payableCompany.storeFactor((String)x1y1fcb.getSelectedItem());
			payableCompany.storeAddressLine1(x1y2a1tf.getText());
			payableCompany.storeAddressLine2(x1y2a2tf.getText());
			payableCompany.storeCity(x1y2ctf.getText());
			payableCompany.storeState(x1y2stf.getText());
			payableCompany.storeZip(x1y2ztf.getText());
		}
		// if panel is on receivable mode
		else
		{
			// if ReceivableContact object does not exist, call the save method
			if (receivableIndex == receivableContactList.size())
			{
				// make sure at least one value is not null
				if ((x1y3ntf.getText().length() != 0) ||
					(x1y3ctf.getText().length() != 0) ||
					(x1y3ptf.getText().length() != 0) ||
					(x1y3ftf.getText().length() != 0) ||
					(x1y3etf.getText().length() != 0))
				{
					saveObject(4);
				}
			}
			// if object exists, call the update method
			else
				updateObject(4);
			
			// save/update the info of the receivable panel
			receivableCompany.storeCreditLine(x1y1cltf.getText());
			receivableCompany.storePmtTerm((String)x1y1ptcb.getSelectedItem());
			receivableCompany.storeFactor((String)x1y1fcb.getSelectedItem());
			receivableCompany.storeAddressLine1(x1y2a1tf.getText());
			receivableCompany.storeAddressLine2(x1y2a2tf.getText());
			receivableCompany.storeCity(x1y2ctf.getText());
			receivableCompany.storeState(x1y2stf.getText());
			receivableCompany.storeZip(x1y2ztf.getText());
		}
		
		// to do: connect to the sql server and save the data
		
		// create a connection to the SQL server.
		// read the file containing the increment counter for a "unique ID"
		// read the file containing the SQL URL and login information.
		// next, input all of the information into the SQL tables.
		// finally, close all of the connections. which prevent memory leak.		
		try
		{
			// conditional: if the save button has not been pressed before, get the company increment information,
			// and update the entry in the corresponding text file.
			// otherwise, if this boolean is true, that means that the info for increment has already been done
			if (isSavedBefore == false)
			{
				// get the file containing the company increment info
				File file = new File("./vitals/company_counter.txt");
				
				// create an input stream to read the line of text
				FileReader in = new FileReader(file);
				BufferedReader reader = new BufferedReader(in);
				
				// get the number in the file. should only be 1 line of text in the file
				// also increment the number by 1, because the number will be the last entered company's number
				companyID = Integer.parseInt(reader.readLine()) + 1;
				
				// close the input stream
				reader.close();
				in.close();
				
				// also need to update the number in the text file. start by creating an output stream
				FileWriter out = new FileWriter(file);
				BufferedWriter writer = new BufferedWriter(out);
				
				// write the newly created integer 'companyID' to the file
				writer.write(Integer.toString(companyID)); writer.newLine();
				
				// close the output streams
				writer.close();
				out.close();	
			}

			// get the information needed for SQL connection.
			// ----------------------------------------------
			// set up the reader
			File file = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(file);
			BufferedReader reader = new BufferedReader(in);
			
			// get the info and close the input streams
			String sqlUrl = reader.readLine();
			String sqlUsername = reader.readLine();
			String sqlPassword = reader.readLine();
			
			reader.close();
			in.close();
			
			// make a connection to the SQL server
			// -----------------------------------
			// set up the SQL connection link & create a statement
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			
			// set up a StringBuilder object, which will be used to create SQL commands
			StringBuilder builder = new StringBuilder();
			
			// the SQL command will depend on whether this information has been saved before.
			// beware: using the INSERT command more than once will result in duplicate entries.
			// depending on the boolean value of 'isSavedBefore', either create a new entry, or update the existing one
			
			// the if-statement is for the INSERT SQL command
			// the else-statement is for the the UPDATE SQL COMMAND
			if (isSavedBefore == false)
			{
				// add the first part of the SQL command to the StringBuilder
				builder.append("INSERT INTO COMPANIES VALUES ('" + companyID + "', ");
				
				// use a for loop to iterate through the values in 'companyGeneralInfo'
				for (int i = 0; i < companyGeneralInfo.size(); i++)
				{
					if (i == companyGeneralInfo.size() - 1)
						builder.append("'" + companyGeneralInfo.get(i) + "');");
					else
						builder.append("'" + companyGeneralInfo.get(i) + "', ");
				}
				
				// execute the SQL command
				statement.executeUpdate(builder.toString());
				
				// set up the SQL command to create a new table for associated CompanyContact objects
				builder = new StringBuilder();
				String tableName = "COMPANYCONTACTS" + companyID;
				String[] columnList = new String[] {"NAME", "CELL", "PHONE", "FAX", "EMAIL"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				
				// execute the SQL (create table) command
				statement.executeUpdate(builder.toString());
				
				// iterate through companyContactList (ArrayList) and save its entries to the table
				for (int i = 0; i < companyContactList.size(); i++)
				{
					builder = new StringBuilder();	// clear the StringBuilder
					
					// create the SQL command for inserting a new entry
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + companyContactList.get(i).getName() + "', ");
					builder.append("'" + companyContactList.get(i).getCell() + "', ");
					builder.append("'" + companyContactList.get(i).getPhone() + "', ");
					builder.append("'" + companyContactList.get(i).getFax() + "', ");
					builder.append("'" + companyContactList.get(i).getEmail() + "');");
					
					// execute the command
					statement.executeUpdate(builder.toString());
				}
				
				// set up SQL command to create new table for associated Payable panel
				builder = new StringBuilder();
				tableName = "COMPANYPAYABLEPANEL" + companyID;
				columnList = new String[] {"CREDITLINE", "PMTTERM", "FACTOR", "ADDRESS1", "ADDRESS2", "CITY", "STATE", "ZIP"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				
				// execute the command
				statement.executeUpdate(builder.toString());
				
				// save the values of the PayableCompany objects to the table
				builder = new StringBuilder();
				
				builder.append("INSERT INTO " + tableName + " VALUES (");
				builder.append("'" + payableCompany.getCreditLine() + "', ");
				builder.append("'" + payableCompany.getPmtTerm() + "', ");
				builder.append("'" + payableCompany.getFactor() + "', ");
				builder.append("'" + payableCompany.getAddressLine1() + "', ");
				builder.append("'" + payableCompany.getAddressLine2() + "', ");
				builder.append("'" + payableCompany.getCity() + "', ");
				builder.append("'" + payableCompany.getState() + "', ");
				builder.append("'" + payableCompany.getZip() + "');");
				
				statement.executeUpdate(builder.toString());
				
				// set up SQL command to create new table for associated PayableContact objects
				builder = new StringBuilder();
				tableName = "PAYABLECONTACTS" + companyID;
				columnList = new String[] {"NAME", "CELL", "PHONE", "FAX", "EMAIL"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				
				// execute the command
				statement.executeUpdate(builder.toString());
				
				// iterate through the payableContactList (ArrayList) and save its values to the table
				for (int i = 0; i < payableContactList.size(); i++)
				{
					builder = new StringBuilder();	// clear the StringBuilder
					
					// create the SQL command for inserting a new entry
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + payableContactList.get(i).getName() + "', ");
					builder.append("'" + payableContactList.get(i).getCell() + "', ");
					builder.append("'" + payableContactList.get(i).getPhone() + "', ");
					builder.append("'" + payableContactList.get(i).getFax() + "', ");
					builder.append("'" + payableContactList.get(i).getEmail() + "');");
				
					statement.executeUpdate(builder.toString());	// execute the command
				}
				
				// set up SQL command to create table for associated Receivable panel
				builder = new StringBuilder();
				tableName = "COMPANYRECEIVABLEPANEL" + companyID;
				columnList = new String[] {"CREDITLINE", "PMTTERM", "FACTOR", "ADDRESS1", "ADDRESS2", "CITY", "STATE", "ZIP"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				
				// execute the command
				statement.executeUpdate(builder.toString());
				
				// save the values of the ReceivableCompany object to the table
				builder = new StringBuilder();
				
				builder.append("INSERT INTO " + tableName + " VALUES (");
				builder.append("'" + receivableCompany.getCreditLine() + "', ");
				builder.append("'" + receivableCompany.getPmtTerm() + "', ");
				builder.append("'" + receivableCompany.getFactor() + "', ");
				builder.append("'" + receivableCompany.getAddressLine1() + "', ");
				builder.append("'" + receivableCompany.getAddressLine2() + "', ");
				builder.append("'" + receivableCompany.getCity() + "', ");
				builder.append("'" + receivableCompany.getState() + "', ");
				builder.append("'" + receivableCompany.getZip() + "');");
				
				statement.executeUpdate(builder.toString());
				
				// set up the SQL command to create table for associated ReceivableContact objects
				builder = new StringBuilder();
				tableName = "RECEIVABLECONTACTS" + companyID;
				columnList = new String[] {"NAME", "CELL", "PHONE", "FAX", "EMAIL"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				
				// execute the command
				statement.executeUpdate(builder.toString());
				
				// iterate through the receivableContactList (ArrayList) and save its values to the table
				for (int i = 0; i < receivableContactList.size(); i++)
				{
					builder = new StringBuilder();	// clear the StringBuilder
					
					// create the SQL command for inserting a new entry
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + receivableContactList.get(i).getName() + "', ");
					builder.append("'" + receivableContactList.get(i).getCell() + "', ");
					builder.append("'" + receivableContactList.get(i).getPhone() + "', ");
					builder.append("'" + receivableContactList.get(i).getFax() + "', ");
					builder.append("'" + receivableContactList.get(i).getEmail() + "');");
					
					statement.executeUpdate(builder.toString());	// execute the command
				}
				
				// create the SQL command for a table of InsurerCompany objects
				builder = new StringBuilder();
				tableName = "INSURERCOMPANIES" + companyID;
				columnList = new String[] {"INSURER", 
										   "GENERALPOLICY", "GENERALCOVERAGE", "GENERALEXPIRATION",
										   "AUTOPOLICY", "AUTOCOVERAGE", "AUTOEXPIRATION",
										   "CARGOPOLICY", "CARGOCOVERAGE", "CARGOEXPIRATION",
										   "TARGETGOODSDEDUCTIBLES"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				statement.executeUpdate(builder.toString());
				
				// iterate through the insurerList (ArrayList) and save each object as an entry in the table
				for (int i = 0; i < insurerList.size(); i++)
				{
					builder = new StringBuilder();	// clear the StringBuilder
					
					// create the SQL command for inserting a new entry
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + insurerList.get(i).getInsurer() + "', ");
					builder.append("'" + insurerList.get(i).getGeneral_policy() + "', ");
					builder.append("'" + insurerList.get(i).getGeneral_coverage() + "', ");
					builder.append("'" + insurerList.get(i).getGeneral_expiration() + "', ");
					builder.append("'" + insurerList.get(i).getAuto_policy() + "', ");
					builder.append("'" + insurerList.get(i).getAuto_coverage() + "', ");
					builder.append("'" + insurerList.get(i).getAuto_expiration() + "', ");
					builder.append("'" + insurerList.get(i).getCargo_policy() + "', ");
					builder.append("'" + insurerList.get(i).getCargo_coverage() + "', ");
					builder.append("'" + insurerList.get(i).getCargo_expiration() + "', ");
					builder.append("'" + insurerList.get(i).getTargetGoodsDeductibles() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// create the SQl command for creating a new table for associated insurance contacts
				builder = new StringBuilder();
				tableName = "INSURANCECONTACTS" + companyID;
				columnList = new String[] {"NAME", "CELL", "PHONE", "FAX", "EMAIL"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnList.length; i++)
				{
					if (i == columnList.length - 1)
						builder.append(columnList[i] + " varchar(300));");
					else
						builder.append(columnList[i] + " varchar(300), ");
				}
				statement.executeUpdate(builder.toString());
				
				// iterate through the insuranceContactList (ArrayList) and save each object as an entry in the table
				for (int i = 0; i < insuranceContactList.size(); i++)
				{
					builder = new StringBuilder();	// clear the StringBuilder
					
					// create the SQL command for inserting a new entry
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + insuranceContactList.get(i).getName() + "', ");
					builder.append("'" + insuranceContactList.get(i).getCell() + "', ");
					builder.append("'" + insuranceContactList.get(i).getPhone() + "', ");
					builder.append("'" + insuranceContactList.get(i).getFax() + "', ");
					builder.append("'" + insuranceContactList.get(i).getEmail() + "');");
					
					statement.executeUpdate(builder.toString());	// execute the command
				}
								
				// change the value of the boolean to true, to indicate that the save button has been pressed before
				isSavedBefore = true;
				
				// show a message indicating that the case has been saved
				JOptionPane.showMessageDialog(null, "Company saved.");
			}
			else
			{
				// array of column names, to be used for pairing columns with values
				String[] columnList = new String[] {"NAME", "DBA", "TEL", "TAXID", "FAX", "MC#", "ESTABLISHED",
													"DOT#", "SCAC", "ADDRESS1", "ADDRESS2", "CITY", "STATE", "ZIP", "RATING",
													"CONTRACT_COMMON", "INSURANCEAGENCY", "ISCARRIER", "ISBROKER", "ISCUSTOMER",
													"ISSHIPPER", "ISCONSIGNEE", "ISINSAGENCY", "ISFACTORING", "ISPREFERRED",
													"ISVENDOR", "ISOWNEROPERATOR", "ISOTHER"};
				
				// update the general info of the company
				// ----------------------------------------------------------
				// add the first part of the SQL command to the StringBuilder
				builder.append("UPDATE COMPANIES SET ");
				
				// iterate through the columns of 'columnList' and values of 'companyGeneralInfo'
				for (int i = 0; i < companyGeneralInfo.size(); i++)
				{
					if (i == companyGeneralInfo.size() - 1)
						builder.append(columnList[i] + " = '" + companyGeneralInfo.get(i) + "' ");
					else
						builder.append(columnList[i] + " = '" + companyGeneralInfo.get(i) + "', ");
				}
				
				// add the last part of the SQL command to the StringBuilder
				builder.append("WHERE COMPANYID = " + companyID + ";");
				
				// execute the SQL command
				statement.executeUpdate(builder.toString());
				
				
				// TODO: get rid of the drop table commands, get instead, do "Delete From " + table
				
				// each table associated with the company must be updated. as there are multiple entries in each table, and
				// there is no associated index / way to specify the individual rows / entries, for now, delete and create a new table
				// for each associated table of the company.
				// for the payable panel and the receivable panel, the tables do not need to be deleted and remade, only need to update the entries

				// delete the current entries from the COMPANYCONTACTS table and add in the new entries.
				String tableName = "COMPANYCONTACTS" + companyID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				for (int i = 0; i < companyContactList.size(); i++)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + companyContactList.get(i).getName() + "', ");
					builder.append("'" + companyContactList.get(i).getCell() + "', ");
					builder.append("'" + companyContactList.get(i).getPhone() + "', ");
					builder.append("'" + companyContactList.get(i).getFax() + "', ");
					builder.append("'" + companyContactList.get(i).getEmail() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// update the info of the Payable panel
				tableName = "COMPANYPAYABLEPANEL" + companyID;
				columnList = new String[] {"CREDITLINE", "PMTTERM", "FACTOR", "ADDRESS1", "ADDRESS2", "CITY", "STATE", "ZIP"};
				builder = new StringBuilder();
				builder.append("UPDATE " + tableName + " SET ");
				builder.append(columnList[0] + " = '" + payableCompany.getCreditLine() + "', ");
				builder.append(columnList[1] + " = '" + payableCompany.getPmtTerm() + "', ");
				builder.append(columnList[2] + " = '" + payableCompany.getFactor() + "', ");
				builder.append(columnList[3] + " = '" + payableCompany.getAddressLine1() + "', ");
				builder.append(columnList[4] + " = '" + payableCompany.getAddressLine2() + "', ");
				builder.append(columnList[5] + " = '" + payableCompany.getCity() + "', ");
				builder.append(columnList[6] + " = '" + payableCompany.getState() + "', ");
				builder.append(columnList[7] + " = '" + payableCompany.getZip() + "';");
				statement.executeUpdate(builder.toString());

				// delete the current entries of the PAYABLECONTACTS table, and add the new entries to it.
				tableName = "PAYABLECONTACTS" + companyID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				for (int i = 0; i < payableContactList.size(); i++)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + payableContactList.get(i).getName() + "', ");
					builder.append("'" + payableContactList.get(i).getCell() + "', ");
					builder.append("'" + payableContactList.get(i).getPhone() + "', ");
					builder.append("'" + payableContactList.get(i).getFax() + "', ");
					builder.append("'" + payableContactList.get(i).getEmail() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// update the info of the Receivable panel
				tableName = "COMPANYRECEIVABLEPANEL" + companyID;
				columnList = new String[] {"CREDITLINE", "PMTTERM", "FACTOR", "ADDRESS1", "ADDRESS2", "CITY", "STATE", "ZIP"};
				builder = new StringBuilder();
				builder.append("UPDATE " + tableName + " SET ");
				builder.append(columnList[0] + " = '" + receivableCompany.getCreditLine() + "', ");
				builder.append(columnList[1] + " = '" + receivableCompany.getPmtTerm() + "', ");
				builder.append(columnList[2] + " = '" + receivableCompany.getFactor() + "', ");
				builder.append(columnList[3] + " = '" + receivableCompany.getAddressLine1() + "', ");
				builder.append(columnList[4] + " = '" + receivableCompany.getAddressLine2() + "', ");
				builder.append(columnList[5] + " = '" + receivableCompany.getCity() + "', ");
				builder.append(columnList[6] + " = '" + receivableCompany.getState() + "', ");
				builder.append(columnList[7] + " = '" + receivableCompany.getZip() + "';");
				statement.executeUpdate(builder.toString());

				// delete the current entries of the RECEIVABLECONTACTS table and add the new entries to it.
				tableName = "RECEIVABLECONTACTS" + companyID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				for (int i = 0; i < receivableContactList.size(); i++)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + receivableContactList.get(i).getName() + "', ");
					builder.append("'" + receivableContactList.get(i).getCell() + "', ");
					builder.append("'" + receivableContactList.get(i).getPhone() + "', ");
					builder.append("'" + receivableContactList.get(i).getFax() + "', ");
					builder.append("'" + receivableContactList.get(i).getEmail() + "');");
					
					statement.executeUpdate(builder.toString());
				}

				// delete the current entries from INSURERCOMPANIES and fill with new entries from insurerList (ArrayList)
				tableName = "INSURERCOMPANIES" + companyID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				for (int i = 0; i < insurerList.size(); i++)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + insurerList.get(i).getInsurer() + "', ");
					builder.append("'" + insurerList.get(i).getGeneral_policy() + "', ");
					builder.append("'" + insurerList.get(i).getGeneral_coverage() + "', ");
					builder.append("'" + insurerList.get(i).getGeneral_expiration() + "', ");
					builder.append("'" + insurerList.get(i).getAuto_policy() + "', ");
					builder.append("'" + insurerList.get(i).getAuto_coverage() + "', ");
					builder.append("'" + insurerList.get(i).getAuto_expiration() + "', ");
					builder.append("'" + insurerList.get(i).getCargo_policy() + "', ");
					builder.append("'" + insurerList.get(i).getCargo_coverage() + "', ");
					builder.append("'" + insurerList.get(i).getCargo_expiration() + "', ");
					builder.append("'" + insurerList.get(i).getTargetGoodsDeductibles() + "');");
					
					statement.executeUpdate(builder.toString());
				}

				// delete the current entries from INSURANCECONTACTS table, and fill with entries from insuranceContactList (ArrayList)
				tableName = "INSURANCECONTACTS" + companyID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				for (int i = 0; i < insuranceContactList.size(); i++)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + insuranceContactList.get(i).getName() + "', ");
					builder.append("'" + insuranceContactList.get(i).getCell() + "', ");
					builder.append("'" + insuranceContactList.get(i).getPhone() + "', ");
					builder.append("'" + insuranceContactList.get(i).getFax() + "', ");
					builder.append("'" + insuranceContactList.get(i).getEmail() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// show a message indicating that the changes have been saved
				JOptionPane.showMessageDialog(null, "Changes saved.");
			}
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Vital SQL File Not Found");
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "IOException. Please contact developer for help.");
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "SQLException. Please contact developer for help.");
		}
	}
	
	// main method: temporary run method:
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new CompanyMaker(); } });
	}
}
