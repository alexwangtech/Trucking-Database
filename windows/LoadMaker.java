/**
 * to be used when creating a new load case
 * when the button "new" is clicked from MainWindow, an instance
 * of this class will be called. the user will then be able to create
 * a new load, which will be save to an SQL Database
 */

package windows;

import badStructures.LoadTable;
import badStructures.NLBotPanel;
import badStructures.PartialRow;
import storageDevices.NLBotPanelDataHolder;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class LoadMaker extends JFrame
{
	GridBagConstraints c;
	JPanel mainPanel, tpLftPnl, tpRghtPnl, tpMstPnl, fillerPanel1, fillerPanel2, scndTpLftPnl, scndTpRghtPnl, scndTpPnl, thrdTpLftPnl, thrdTpRghtPnl, thrdTpPnl, mdLftPnl, mdRghtPnl, mdPnl, 
		   botPanel;
	JLabel incmLbl1, incmLbl2, mrgnLbl1, mrgnLbl2, ldNmLbl1, ldNmLbl2, entrdLbl1, entrdLbl2, eqpLbl, lngthLbl, instructionLbl, cmmdtyLbl, qnttyLbl, unitLbl, wghtLbl, billToLblLft, 
		   addrssLblLft1, crdtLblLft, emlLblLft, telLblLft, faxLblLft, cellLblLft, refLblLft, dscntLblLft, trmLblLft, rvnueLblLft, invceLblLft, noteLblLft,payToLblRght, 
		   mcLblRght, emlLblRght, telLblRght, faxLblRght, cellLblR1, cellLblR2, trkLblRght, trlLblRght, dscntLblRght, trmLblRght, expnseLblRght, invceLblRght, noteLblRght;
	JButton saveBtn, printBtn, crtNwBtnLft, cntctBtnLft, cntctBtnRght, drvrBtnRght;
	JTextField lngthTF, instructionTF, cmmdtyTF, qnttyTF, wghtTF, cntctTFLft, emlTFLft, telTFLft, faxTFLft, cellTFLft, refTFLft, dscntTFLft, trmTFLft, noteTFLft, mcTFRght, 
			   cntctTFRght, emlTFRght, telTFRght, faxTFRght, cntctCellTFRght, drvrTFRght, drvrCellTFRght, trkTFRght, trlTFRght, dscntTFRght, trmTFRght, noteTFRght, addrssLblLft2;
	Checkbox airRideCB, logisticCB, teamCB, withholdPayment;
	JComboBox<String> eqpCmboBx, unitCB, billToCB, pyToCBRght;
	String[] eqpCmboBxOptions = {"Dry Van"}, unitCBOptions = {"Select One"}, billToCBOptions = {"TBD--"}, pyToCBRghtOptions = {"Select One"};
	LoadTable leftTable, rightTable;
	NLBotPanel leftBot, rightBot;
	String sqlUrl, sqlUsername, sqlPassword;
	ArrayList<String> tempList, generalInfoList;
	boolean isSavedBefore;
	int loadID;
	
	public LoadMaker()
	{
		// NEW EDIT: the lists for 'bill to' and 'pay to' must be a list of companies from the COMPANIES table from the SQL server
		try
		{
			// get the info needed for connection to the SQL server (vitals folder) (one time only, since the variables will be global)
			File sqlFile = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(sqlFile);
			BufferedReader sqlReader = new BufferedReader(in);
			
			sqlUrl = sqlReader.readLine();
			sqlUsername = sqlReader.readLine();
			sqlPassword = sqlReader.readLine();
			
			sqlReader.close();
			in.close();
			
			// make a connection to the SQL server
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			
			// retrieve the information from the SQL server and store it in a ResultSet object
			Statement statement = connection.createStatement();
			ResultSet result_set = statement.executeQuery("SELECT NAME FROM COMPANIES;");
			
			// move the data from the ResultSet to a temporary ArrayList
			tempList = new ArrayList<String>();
			while (result_set.next())
				tempList.add(result_set.getString(1));
			
			// copy the data from 'tempList' to both 'billToCBOptions' and 'pyToCBRghtOptions'
			billToCBOptions = new String[tempList.size() + 1]; billToCBOptions[0] = "TBD--";
			pyToCBRghtOptions = new String[tempList.size() + 1]; pyToCBRghtOptions[0] = "Select One";
			for (int i = 0; i < tempList.size(); i++)
			{
				billToCBOptions[i + 1] = tempList.get(i);
				pyToCBRghtOptions[i + 1] = tempList.get(i);
			}
			
			result_set.close();
			statement.close();
			connection.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		// initialized boolean 'isSavedBefore' checks whether the save button has been pressed before
		isSavedBefore = false;
		
		// ==========================================================================================================
		// top most panel of the window. consists of two smaller panels.
		// the left panel has the 'save' and 'print button'. the right panel has the the income info and margin info.
		// ==========================================================================================================
		saveBtn = new JButton("Save");		// save button
		printBtn = new JButton("Print");	// print button
		
		// add the save and print buttons to tpLftPnl (JPanel)
		tpLftPnl = new JPanel();
		tpLftPnl.add(saveBtn);
		tpLftPnl.add(printBtn);
		
		incmLbl1 = new JLabel("Income: ");		// income label
		incmLbl2 =  new JLabel("$0.00");		// income label answer
		mrgnLbl1 = new JLabel("Margin(%): ");	// margin label
		mrgnLbl2 = new JLabel("0.00");			// margin label answer
		
		// add the four labels to tpRghtPnl (JPanel)
		tpRghtPnl = new JPanel();
		tpRghtPnl.add(incmLbl1);
		tpRghtPnl.add(incmLbl2);
		tpRghtPnl.add(mrgnLbl1);
		tpRghtPnl.add(mrgnLbl2);
		
		// combine tpLftPnl and tpRghtPnl to tpMstPnl (JPanel) using GridBagLayout
		tpMstPnl = new JPanel(); tpMstPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; tpMstPnl.add(tpLftPnl, c);		// top left panel
		fillerPanel1 = new JPanel();
		c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
		c.gridx = 1; c.gridy = 0; tpMstPnl.add(fillerPanel1, c);	// filler panel
		c.fill = GridBagConstraints.NONE; c.weightx = 0;
		c.gridx = 2; c.gridy = 0; tpMstPnl.add(tpRghtPnl, c);		// top right panel
		
		// ================================================================================================================================
		// the second-to-top panel. consists of two smaller panels.
		// the left panel contains load number and the corresponding number. the right panel contains entered label and entered date & time
		// ================================================================================================================================
		ldNmLbl1 = new JLabel("Load#: ");	// load number label
		ldNmLbl2 = new JLabel("TBD");		// load number answer
		
		// add load number labels to scndTpLftPnl (JPanel)
		scndTpLftPnl = new JPanel();
		scndTpLftPnl.add(ldNmLbl1);
		scndTpLftPnl.add(ldNmLbl2);
		
		entrdLbl1 = new JLabel("Entered: ");		// entered label
		entrdLbl2 = new JLabel(findDateAndTime());	// date entered (calls method findDateAndTime())
		
		// add entered label and entered answer label to scndTpRghtPnl (JPanel)
		scndTpRghtPnl = new JPanel();
		scndTpRghtPnl.add(entrdLbl1);
		scndTpRghtPnl.add(entrdLbl2);
		
		// add scndTpLftPnl & scndTpRghtPnl to scndTpPnl (JPanel) using GridBagLayout
		scndTpPnl = new JPanel(); scndTpPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; scndTpPnl.add(scndTpLftPnl, c);	// second top left panel
		fillerPanel2 = new JPanel();
		c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
		c.gridx = 1; c.gridy = 0; scndTpPnl.add(fillerPanel2, c);	// filler panel
		c.fill = GridBagConstraints.NONE; c.weightx = 0;
		c.gridx = 2; c.gridy = 0; scndTpPnl.add(scndTpRghtPnl, c);	// second top right panel
		
		// ==============================================================================================================================
		// the third-to-top panel. consists of two smaller panels. the left panel consists of equip, dry van combo box, length,
		// air ride, logistic, team check boxes, instruction label, and instruction text field.
		// the right panel consists of commodity label + commodity text field, quantity label + quantity text field, unit combo box, and
		// weight label + weight text field.
		// ==============================================================================================================================
		eqpLbl = new JLabel("Equip: ");							// equip label
		eqpCmboBx = new JComboBox<String>(eqpCmboBxOptions);	// equip combo box
		lngthLbl = new JLabel("Length: ");						// length label
		lngthTF = new JTextField(10);							// length text field
		airRideCB = new Checkbox("Air Ride");					// air ride check box
		logisticCB = new Checkbox("Logistic");					// logistic check box
		teamCB = new Checkbox("Team");							// team check box
		instructionLbl = new JLabel("Instruction: ");			// instruction label
		instructionTF = new JTextField(20);						// instruction text field
		
		// add above components to thrdTpLftPnl (JPanel) using GridBagLayout
		thrdTpLftPnl = new JPanel(); thrdTpLftPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0; c.gridy = 0; thrdTpLftPnl.add(eqpLbl, c);			// equip label
		c.gridx = 1; c.gridy = 0; thrdTpLftPnl.add(eqpCmboBx, c);		// equip combo box
		c.gridx = 2; c.gridy = 0; thrdTpLftPnl.add(lngthLbl, c);		// length label
		c.gridx = 3; c.gridy = 0; thrdTpLftPnl.add(lngthTF, c);			// length text field
		c.gridx = 4; c.gridy = 0; thrdTpLftPnl.add(airRideCB, c);		// air ride check box
		c.gridx = 5; c.gridy = 0; thrdTpLftPnl.add(logisticCB, c);		// logistic check box
		c.gridx = 6; c.gridy = 0; thrdTpLftPnl.add(teamCB, c);			// team check box
		c.gridx = 0; c.gridy = 1; thrdTpLftPnl.add(instructionLbl, c);	// instruction label
		c.fill = GridBagConstraints.HORIZONTAL; c.gridwidth = 6;
		c.gridx = 1; c.gridy = 1; thrdTpLftPnl.add(instructionTF, c);	// instruction text field
		
		// components for thrdTpRghtPnl (JPanel)
		cmmdtyLbl = new JLabel("Commodity: ");			// commodity label
		cmmdtyTF = new JTextField(20);					// commodity text field
		qnttyLbl = new JLabel("Quantity: ");			// quantity label
		qnttyTF = new JTextField(10);					// quantity text field
		unitLbl = new JLabel("Unit: ");					// unit label
		unitCB = new JComboBox<String>(unitCBOptions);	// unit combo box
		wghtLbl = new JLabel("Weight: ");				// weight label
		wghtTF = new JTextField(10);					// weight text field
		
		// add above components to thrdTpRghtPnl (JPanel) using GridBagLayout
		thrdTpRghtPnl = new JPanel(); thrdTpRghtPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0; c.gridy = 0; thrdTpRghtPnl.add(cmmdtyLbl, c);	// commodity label
		c.fill = GridBagConstraints.HORIZONTAL; c.gridwidth = 5;
		c.gridx = 1; c.gridy = 0; thrdTpRghtPnl.add(cmmdtyTF, c);	// commodity text field
		c.fill = GridBagConstraints.NONE; c.gridwidth = 1;
		c.gridx = 0; c.gridy = 1; thrdTpRghtPnl.add(qnttyLbl, c);	// quantity label
		c.gridx = 1; c.gridy = 1; thrdTpRghtPnl.add(qnttyTF, c);	// quantity text field
		c.gridx = 2; c.gridy = 1; thrdTpRghtPnl.add(unitLbl, c);	// unit label
		c.gridx = 3; c.gridy = 1; thrdTpRghtPnl.add(unitCB, c);		// unit combo box
		c.gridx = 4; c.gridy = 1; thrdTpRghtPnl.add(wghtLbl, c);	// weight label
		c.gridx = 5; c.gridy = 1; thrdTpRghtPnl.add(wghtTF, c);		// weight text field
		
		// combine thrdTpLftPnl & thrdTpRghtPnl to thrdTpPnl (JPanel) using GridBagLayout
		thrdTpPnl = new JPanel(); thrdTpPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; thrdTpPnl.add(thrdTpLftPnl, c);		// third top left panel
		c.gridx = 1; c.gridy = 0; thrdTpPnl.add(thrdTpRghtPnl, c);		// third top right panel
		
		// ===================================================================================================================================================
		// the middle section panel. consists of two smaller panels. the left section consists of bill to, bill to combo box, create new button,
		// address, blank address label, credit, contact selection button, manual entry mode, ref#, discount, term, table for entering charges, revenue label,
		// invoice label, and notes.
		// the right section consists of pay to, pay to combo box, mc#, contact selection button, manual entry mode, driver, driver manual entry mode,
		// withhold payment check box, discount, term, table for entering charges, expense, invoice, and notes.
		// ===================================================================================================================================================
		billToLblLft = new JLabel("Bill to: ");					// bill to label
		billToCB = new JComboBox<String>(billToCBOptions);		// bill to combo box
		crtNwBtnLft = new JButton("Create New");				// create new button
		addrssLblLft1 = new JLabel("Address: ");				// address label
		addrssLblLft2 = new JTextField(10);						// address text field
		crdtLblLft = new JLabel("Credit: 4,200");				// credit label
		cntctBtnLft = new JButton("Contact: ");					// contact button
		cntctTFLft = new JTextField(10);						// contact text field
		emlLblLft = new JLabel("Email: ");						// email label
		emlTFLft = new JTextField(10);							// email text field
		telLblLft = new JLabel("Tel: ");						// tel label
		telTFLft = new JTextField(10);							// tel text field
		faxLblLft = new JLabel("Fax: ");						// fax label
		faxTFLft = new JTextField(10);							// fax text field
		cellLblLft = new JLabel("Cell: ");						// cell label
		cellTFLft = new JTextField(10);							// cell text field
		refLblLft = new JLabel("Ref#: ");						// ref# label
		refTFLft = new JTextField(10);							// ref# text field
		dscntLblLft = new JLabel("Discount(%): ");				// discount label
		dscntTFLft = new JTextField(10);						// discount text field
		trmLblLft = new JLabel("Term: ");						// term label
		trmTFLft = new JTextField(10);							// term text field
		
		// call a LoadTable object from package badStructures
		leftTable = new LoadTable();
		
		rvnueLblLft = new JLabel("Revenue: $0.00");			// revenue label + answer
		invceLblLft = new JLabel("Invoice: $0.00");			// invoice label + answer
		noteLblLft = new JLabel("Note: ");					// notes label
		noteTFLft = new JTextField(30);						// notes text field
		
		// add the above components to mdLftPnl using GridBagLayout
		mdLftPnl = new JPanel(); mdLftPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; mdLftPnl.add(billToLblLft, c);		// bill to label
		c.gridwidth = 4; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 0; mdLftPnl.add(billToCB, c);			// bill to combo box
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 5; c.gridy = 0; mdLftPnl.add(crtNwBtnLft, c);			// create new button
		c.gridx = 0; c.gridy = 1; mdLftPnl.add(addrssLblLft1, c);		// address label 1
		c.gridwidth = 4; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 1; mdLftPnl.add(addrssLblLft2, c);		// address label 2
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 5; c.gridy = 1; mdLftPnl.add(crdtLblLft, c);			// credit label
		c.gridx = 0; c.gridy = 2; mdLftPnl.add(cntctBtnLft, c);			// contact button
		c.gridx = 1; c.gridy = 2; mdLftPnl.add(cntctTFLft, c);			// contact text field
		c.gridx = 2; c.gridy = 2; mdLftPnl.add(emlLblLft, c);			// email label
		c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1; c.gridwidth = 3;
		c.gridx = 3; c.gridy = 2; mdLftPnl.add(emlTFLft, c);			// email text field
		c.fill = GridBagConstraints.NONE; c.weightx = 0; c.gridwidth = 1;
		c.gridx = 0; c.gridy = 3; mdLftPnl.add(telLblLft, c);			// tel label
		c.gridx = 1; c.gridy = 3; mdLftPnl.add(telTFLft, c);			// tel text field
		c.gridx = 2; c.gridy = 3; mdLftPnl.add(faxLblLft, c);			// fax label
		c.gridx = 3; c.gridy = 3; mdLftPnl.add(faxTFLft, c);			// fax text field
		c.gridx = 4; c.gridy = 3; mdLftPnl.add(cellLblLft, c);			// cell label
		c.gridx = 5; c.gridy = 3; mdLftPnl.add(cellTFLft, c);			// cell text field
		c.gridx = 0; c.gridy = 4; mdLftPnl.add(refLblLft, c);			// ref# label
		c.gridx = 1; c.gridy = 4; mdLftPnl.add(refTFLft, c);			// ref# text field
		c.gridx = 2; c.gridy = 4; mdLftPnl.add(dscntLblLft, c);			// discount label
		c.gridx = 3; c.gridy = 4; mdLftPnl.add(dscntTFLft, c);			// discount text field
		c.gridx = 4; c.gridy = 4; mdLftPnl.add(trmLblLft, c);			// term label
		c.gridx = 5; c.gridy = 4; mdLftPnl.add(trmTFLft, c);			// term text field
		
		// add in the LoadTable object
		c.gridwidth = 6; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; c.gridy = 5; mdLftPnl.add(leftTable, c);			// LoadTable object
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		
		c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2; c.gridy = 6; mdLftPnl.add(rvnueLblLft, c);			// revenue label + answer
		c.gridx = 4; c.gridy = 6; mdLftPnl.add(invceLblLft, c);			// invoice label + answer
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 7; mdLftPnl.add(noteLblLft, c);			// note label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 7; mdLftPnl.add(noteTFLft, c);			// note text field
		
		// temporary: add a border around left mid panel
		mdLftPnl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// initialize the components of the right middle panel
		payToLblRght = new JLabel("Pay to: ");					// pay to label
		pyToCBRght = new JComboBox<String>(pyToCBRghtOptions);	// pay to combo box
		mcLblRght = new JLabel("MC#: ");						// mc# label
		mcTFRght = new JTextField(10);							// mc text field
		cntctBtnRght = new JButton("Contact");					// contact button
		cntctTFRght = new JTextField(10);						// contact text field
		emlLblRght = new JLabel("Email: ");						// email label
		emlTFRght = new JTextField(10);							// email text field
		telLblRght = new JLabel("Tel: ");						// tel label
		telTFRght = new JTextField(10);							// tel text field
		faxLblRght = new JLabel("Fax: ");						// fax label
		faxTFRght = new JTextField(10);							// fax text field
		cellLblR1 = new JLabel("Cell: ");						// contact cell label
		cntctCellTFRght = new JTextField(10);					// contact cell text field
		drvrBtnRght = new JButton("Driver");					// driver button
		drvrTFRght = new JTextField(10);						// driver text field
		cellLblR2 = new JLabel("Cell: ");						// driver cell label
		drvrCellTFRght = new JTextField(10);					// driver text field
		withholdPayment = new Checkbox("Withhold Payment");		// withhold payment check box
		trkLblRght = new JLabel("Truck#: ");					// truck# label
		trkTFRght = new JTextField(10);							// truck# text field
		trlLblRght = new JLabel("Trailer#: ");					// trailer# label
		trlTFRght = new JTextField(10);							// trailer# text field
		dscntLblRght = new JLabel("Discount(%): ");				// discount label
		dscntTFRght = new JTextField(10);						// discount text field
		trmLblRght = new JLabel("Term: ");						// term label
		trmTFRght = new JTextField(10);							// term text field
		
		// initialize the LoadTable object
		rightTable = new LoadTable();							// LoadTable object
		
		expnseLblRght = new JLabel("Expense: $0.00");			// expense label + answer
		invceLblRght = new JLabel("Invoice: $0.00");			// invoice label + answer
		noteLblRght = new JLabel("Note: ");						// note label
		noteTFRght = new JTextField(10);						// note text field
		
		// add the above components to mdRghtPnl using GridBagLayout
		mdRghtPnl = new JPanel(); mdRghtPnl.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; mdRghtPnl.add(payToLblRght, c);		// pay to label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 0; mdRghtPnl.add(pyToCBRght, c);			// pay to combo box
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 6; c.gridy = 0; mdRghtPnl.add(mcLblRght, c);			// mc# label
		c.gridwidth = 1;
		c.gridx = 7; c.gridy = 0; mdRghtPnl.add(mcTFRght, c);			// mc# text field
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 1; mdRghtPnl.add(cntctBtnRght, c);		// contact button
		c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 1; mdRghtPnl.add(cntctTFRght, c);		// contact text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 3; c.gridy = 1; mdRghtPnl.add(emlLblRght, c);			// email label
		c.gridwidth = 4; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4; c.gridy = 1; mdRghtPnl.add(emlTFRght, c);			// email text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 2; mdRghtPnl.add(telLblRght, c);			// tel label
		c.gridx = 1; c.gridy = 2; mdRghtPnl.add(telTFRght, c);			// tel text field
		c.gridx = 2; c.gridy = 2; mdRghtPnl.add(faxLblRght, c);			// fax label
		c.gridx = 3; c.gridy = 2; mdRghtPnl.add(faxTFRght, c);			// fax text field
		c.gridx = 4; c.gridy = 2; mdRghtPnl.add(cellLblR1, c);			// contact cell label
		c.gridx = 5; c.gridy = 2; mdRghtPnl.add(cntctCellTFRght, c);	// contact cell text field
		c.gridx = 0; c.gridy = 3; mdRghtPnl.add(drvrBtnRght, c);		// driver button
		c.gridwidth = 2;
		c.gridx = 1; c.gridy = 3; mdRghtPnl.add(drvrTFRght, c);			// driver text field
		c.gridwidth = 1;
		c.gridx = 3; c.gridy = 3; mdRghtPnl.add(cellLblR2, c);			// driver cell label
		c.gridwidth = 2;
		c.gridx = 4; c.gridy = 3; mdRghtPnl.add(drvrCellTFRght, c);		// driver cell text field
		c.gridwidth = 2;
		c.gridx = 6; c.gridy = 3; mdRghtPnl.add(withholdPayment, c);	// withold payment check box
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 4; mdRghtPnl.add(trkLblRght, c);			// truck# label
		c.gridx = 1; c.gridy = 4; mdRghtPnl.add(trkTFRght, c);			// truck# text field
		c.gridx = 2; c.gridy = 4; mdRghtPnl.add(trlLblRght, c);			// trailer# label
		c.gridx = 3; c.gridy = 4; mdRghtPnl.add(trlTFRght, c);			// trailer# text field
		c.gridx = 4; c.gridy = 4; mdRghtPnl.add(dscntLblRght, c);		// discount label
		c.gridx = 5; c.gridy = 4; mdRghtPnl.add(dscntTFRght, c);		// discount text field
		c.gridx = 6; c.gridy = 4; mdRghtPnl.add(trmLblRght, c);			// term panel label
		c.gridx = 7; c.gridy = 4; mdRghtPnl.add(trmTFRght, c);			// term text field

		// add in the LoadTable object
		c.gridwidth = 8; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; c.gridy = 5; mdRghtPnl.add(rightTable, c);			// LoadTable object
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		
		c.gridwidth = 3;
		c.gridx = 0; c.gridy = 6; mdRghtPnl.add(expnseLblRght, c);		// expense label + answer
		c.gridx = 3; c.gridy = 6; mdRghtPnl.add(invceLblRght, c);		// invoice label + answer
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 7; mdRghtPnl.add(noteLblRght, c);		// note label
		c.gridwidth = 7; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 7; mdRghtPnl.add(noteTFRght, c);			// note text field
		
		
		// temporary: add a border around mdRghtPnl
		mdRghtPnl.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// combine mdLftPnl & mdRghtPnl to mdPnl using FlowLayout
		mdPnl = new JPanel();
		mdPnl.add(mdLftPnl);
		mdPnl.add(mdRghtPnl);
		
		//temporary: add a colored border around mdPnl
		mdPnl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		// temporary: add borders around mdLftPnl and mdRghtPnl
		mdLftPnl.setBorder(BorderFactory.createLineBorder(Color.RED));
		mdRghtPnl.setBorder(BorderFactory.createLineBorder(Color.RED));

		// ==================================================================================================================================
		// the bottom panel. consists of two smaller panels, which have been created as a separate class from 'packages' and 'badStructures'.
		// serves as the UI for pick up and drop off locations.
		// ==================================================================================================================================
		// initialize the two NLBotPanel objects
		leftBot = new NLBotPanel(1);	// pick up panel
		rightBot = new NLBotPanel(2);	// drop off panel
		
		// add leftBot and rightBot to botPanel using GridBagLayout
		botPanel = new JPanel(); botPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.gridx = 0; c.gridy = 0; botPanel.add(leftBot, c);
		c.gridx = 1; c.gridy = 0; botPanel.add(rightBot, c);
		
		// combine all panels onto mainPanel using GridBagLayout
		mainPanel = new JPanel(); mainPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; c.gridy = 0; mainPanel.add(tpMstPnl, c);
		c.gridx = 0; c.gridy = 1; mainPanel.add(scndTpPnl, c);
		c.gridx = 0; c.gridy = 2; mainPanel.add(thrdTpPnl, c);
		c.gridx = 0; c.gridy = 3; mainPanel.add(mdPnl, c);
		c.gridx = 0; c.gridy = 4; mainPanel.add(botPanel, c);

		// temporary: add borders around the larger panels
		thrdTpLftPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		thrdTpRghtPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		tpMstPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		scndTpPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		thrdTpPnl.setBorder(BorderFactory.createLineBorder(Color.red));
		botPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		this.setTitle("New Load");
		this.add(mainPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		// ============================================================================================================= //
		// FUNCTIONALITY SECTION OF THE GUI: CONTAINS ACTIONLISTENER + OTHER LISTENERS FOR ALL OF THE BUTTONS/COMPONENTS //
		// ============================================================================================================= //
		
		// ActionListener for the top left corner 'Save' button: calls the saveAndStore() method.
		saveBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				saveAndStore();
			}
		});
		
		// ActionListener for the top left corner 'Print' button: calls the printLoad() method
		printBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				printLoad();
			}
		});
		
		// ActionListener for 'Create New' button: calls in a new instance of CompanyMaker().
		crtNwBtnLft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new CompanyMaker(); } });
			}
		});
		
		// ActionListener for the 'Contact' button (left side panel): opens up a window where the user can select a contact for the selected company
		cntctBtnLft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// the data that will be loaded is dependent on the selection in 'billToCB' if no selection is made, this button won't do anything.
				if (((String)billToCB.getSelectedItem()).equals("TBD--"))
					return;
				
				String[] columnNames = new String[] {"Name", "Email", "Tel", "Fax", "Cell"};
				String[][] columnValues;
				
				// get the current item in 'billToCB'.
				String selectedCompany = (String)billToCB.getSelectedItem();
				
				try
				{
					// get the data from SQL server, if it exists. if the table does not exists, a message dialog will be displayed, and the method will end.
					Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
					Statement statement = connection.createStatement();
					ResultSet result_set = statement.executeQuery("SELECT * FROM " + selectedCompany + "BILLTOCONTACTS;");
					
					// copy the data from the ResultSet to a temporary ArrayList.
					ArrayList<String> templist = new ArrayList<String>();
					while (result_set.next())
					{
						templist.add(result_set.getString(1));
						templist.add(result_set.getString(2));
						templist.add(result_set.getString(3));
						templist.add(result_set.getString(4));
						templist.add(result_set.getString(5));
					}
					
					// copy the data from 'templist' to 'columnValues'.
					int counter = 0;
					columnValues = new String[templist.size() / 5][5];
					for (int i = 0; i < columnValues.length; i++)
						for (int j = 0; j < columnValues[i].length; j++)
							columnValues[i][j] = templist.get(counter++);
					
					result_set.close();
					statement.close();
					connection.close();
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(null, "The selected company does not have any saved contacts.");
					return;
				}
				
				// organize the data into a JTable object, set cells non-editable.
				JTable contactsTable = new JTable(columnValues, columnNames) { public boolean isCellEditable(int row, int column) { return false; } };
				contactsTable.setFillsViewportHeight(true);		// fills the entire height of the container
				
				// create a new window to display the data.
				JScrollPane contactsScrollPane = new JScrollPane(contactsTable);
				JFrame contactsFrame = new JFrame("Contacts");
				contactsFrame.add(contactsScrollPane);
				contactsFrame.pack();
				contactsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				contactsFrame.setLocationRelativeTo(null);
				contactsFrame.setVisible(true);
				
				// MouserListener for 'contactsTable' : double-clicking on the table will place the selected row's values to the respective text fields.
				contactsTable.addMouseListener(new MouseListener()
				{
					public void mouseEntered(MouseEvent event)  { }
					public void mouseExited(MouseEvent event)   { }
					public void mousePressed(MouseEvent event)  { }
					public void mouseReleased(MouseEvent event) { }
					
					public void mouseClicked(MouseEvent event)
					{
						if (event.getClickCount() == 2)
						{
							// get the values of the selected table row
							String doubleClickName = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 0);
							String doubleClickEmail = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 1);
							String doubleClickTel = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 2);
							String doubleClickFax = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 3);
							String doubleClickCell = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 4);
							
							// set each of the values to its respective text field.
							cntctTFLft.setText(doubleClickName);
							emlTFLft.setText(doubleClickEmail);
							telTFLft.setText(doubleClickTel);
							faxTFLft.setText(doubleClickFax);
							cellTFLft.setText(doubleClickCell);
							
							// dispose the temporary window, 'contactsFrame'
							contactsFrame.dispose();
						}
					}
				});
			}
		});
		
		// ActionListener for the 'Contact' button (right side panel): opens up a window where the user can select a contact for the selected company.
		cntctBtnRght.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// the data that will be loaded is dependent on the selection in 'pyToCBRght'. if no selection is made, this button won't do anything.
				if (((String)pyToCBRght.getSelectedItem()).equals("Select One"))
					return;
				
				String[] columnNames = new String[] {"Name", "Email", "Tel", "Fax", "Cell"};
				String[][] columnValues;
				
				// get the current item in 'pyToCBRght'.
				String selectedCompany = (String)pyToCBRght.getSelectedItem();
				
				try
				{
					// get the data from SQL server, if it exists. if the table does not exists, a message dialog will be displayed, and the method will end.
					Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
					Statement statement = connection.createStatement();
					ResultSet result_set = statement.executeQuery("SELECT * FROM " + selectedCompany + "PAYTOCONTACTS;");
					
					// copy the data from the ResultSet to a temporary ArrayList.
					ArrayList<String> templist = new ArrayList<String>();
					while (result_set.next())
					{
						templist.add(result_set.getString(1));
						templist.add(result_set.getString(2));
						templist.add(result_set.getString(3));
						templist.add(result_set.getString(4));
						templist.add(result_set.getString(5));
					}
					
					// copy the data from 'templist' to 'columnValues'.
					int counter = 0;
					columnValues = new String[templist.size() / 5][5];
					for (int i = 0; i < columnValues.length; i++)
						for (int j = 0; j < columnValues[i].length; j++)
							columnValues[i][j] = templist.get(counter++);
					
					result_set.close();
					statement.close();
					connection.close();
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(null, "The selected company does not have any saved contacts.");
					return;
				}
				
				// organize the data into a JTable object, set cells non-editable.
				JTable contactsTable = new JTable(columnValues, columnNames) { public boolean isCellEditable(int row, int column) { return false; } };
				contactsTable.setFillsViewportHeight(true);		// fills the entire height of the container
				
				// create a new window to display the data.
				JScrollPane contactsScrollPane = new JScrollPane(contactsTable);
				JFrame contactsFrame = new JFrame("Contacts");
				contactsFrame.add(contactsScrollPane);
				contactsFrame.pack();
				contactsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				contactsFrame.setLocationRelativeTo(null);
				contactsFrame.setVisible(true);
				
				// MouserListener for 'contactsTable' : double-clicking on the table will place the selected row's values to the respective text fields.
				contactsTable.addMouseListener(new MouseListener()
				{
					public void mouseEntered(MouseEvent event)  { }
					public void mouseExited(MouseEvent event)   { }
					public void mousePressed(MouseEvent event)  { }
					public void mouseReleased(MouseEvent event) { }
					
					public void mouseClicked(MouseEvent event)
					{
						if (event.getClickCount() == 2)
						{
							// get the values of the selected table row
							String doubleClickName = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 0);
							String doubleClickEmail = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 1);
							String doubleClickTel = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 2);
							String doubleClickFax = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 3);
							String doubleClickCell = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 4);
							
							// set each of the values to its respective text field.
							cntctTFRght.setText(doubleClickName);
							emlTFRght.setText(doubleClickEmail);
							telTFRght.setText(doubleClickTel);
							faxTFRght.setText(doubleClickFax);
							cntctCellTFRght.setText(doubleClickCell);
							
							// dispose the temporary window, 'contactsFrame'
							contactsFrame.dispose();
						}
					}
				});
			}
		});
		
		// ActionListener for 'Driver' button: opens up a window where the user can select a driver contact for the selected company.
		drvrBtnRght.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// the data that will be loaded is dependent on the selection in 'pyToCBRght'. if no selection is made, this button won't do anything.
				if (((String)pyToCBRght.getSelectedItem()).equals("Select One"))
					return;
				
				String[] columnNames = new String[] {"Name", "Cell", "Truck#", "Trailer#"};
				String[][] columnValues;
				
				// get the current item in 'pyToCBRght'.
				String selectedCompany = (String)pyToCBRght.getSelectedItem();
				
				try
				{
					// get the data from the SQL server, if it exists. if the table does not exist, a message dialog will be displayed, and the method will end.
					Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
					Statement statement = connection.createStatement();
					ResultSet result_set = statement.executeQuery("SELECT * FROM " + selectedCompany + "DRIVERCONTACTS;");
					
					// copy the data from the ResultSet to a temporary ArrayList.
					ArrayList<String> templist = new ArrayList<String>();
					while (result_set.next())
					{
						templist.add(result_set.getString(1));
						templist.add(result_set.getString(2));
						templist.add(result_set.getString(3));
						templist.add(result_set.getString(4));
					}
					
					// copy the data from 'templist' to 'columnValues'.
					int counter = 0;
					columnValues = new String[templist.size() / 4][4];
					for (int i = 0; i < columnValues.length; i++)
						for (int j = 0; j < columnValues[i].length; j++)
							columnValues[i][j] = templist.get(counter++);
					
					result_set.close();
					statement.close();
					connection.close();
				}
				catch (SQLException e)
				{
					JOptionPane.showMessageDialog(null, "The selected company does not have any saved contacts.");
					return;
				}
				
				// organize the data into a JTable object, set cells non-editable.
				JTable contactsTable = new JTable(columnValues, columnNames) { public boolean isCellEditable(int row, int column) { return false; } };
				contactsTable.setFillsViewportHeight(true);		// fills the entire height of the container
				
				// create a new window to display the data.
				JScrollPane contactsScrollPane = new JScrollPane(contactsTable);
				JFrame contactsFrame = new JFrame("Contacts");
				contactsFrame.add(contactsScrollPane);
				contactsFrame.pack();
				contactsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				contactsFrame.setLocationRelativeTo(null);
				contactsFrame.setVisible(true);
				
				// MouseListener for 'contactsTable' : double-clicking on the table will place the selected row's values to the respective text fields.
				contactsTable.addMouseListener(new MouseListener()
				{
					public void mouseEntered(MouseEvent event)  { }
					public void mouseExited(MouseEvent event)   { }
					public void mousePressed(MouseEvent event)  { }
					public void mouseReleased(MouseEvent event) { }
					
					public void mouseClicked(MouseEvent event)
					{
						if (event.getClickCount() == 2)
						{
							// get the values of the selected table row
							String doubleClickName = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 0);
							String doubleClickCell = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 1);
							String doubleClickTruck = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 2);
							String doubleClickTrailer = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 3);
							
							// set each of the values to its respective text field
							drvrTFRght.setText(doubleClickName);
							drvrCellTFRght.setText(doubleClickCell);
							trkTFRght.setText(doubleClickTruck);
							trlTFRght.setText(doubleClickTrailer);
							
							// dispose the temporary window, 'contactsFrame'
							contactsFrame.dispose();
						}
					}
				});
			}
		});
		
		// MouseListener for 'billToCB' ('Bill To' combo box).
		// updates the entries inside the combo box, in case a new entry was made while this window was still open.
		billToCB.addMouseListener(new MouseListener()
		{
			public void mouseEntered(MouseEvent event)  { }
			public void mouseExited(MouseEvent event)   { }
			public void mousePressed(MouseEvent event)  { }
			public void mouseReleased(MouseEvent event) { }
			
			public void mouseClicked(MouseEvent event)
			{
				// call the updateCB() method, update type 1
				updateCB(1);
				
				// clear the items from 'billToCB' (combo box), and add the entries of 'billToCBOptions' (String[])
				billToCB.removeAllItems();
				for (int i = 0; i < billToCBOptions.length; i++)
					billToCB.addItem(billToCBOptions[i]);
			}
		});
		
		// MouseListener for 'pyToCBRght' ('Pay To' combo box).
		// updates the entries inside the combo box, in case a new entry was made while this window was still open.
		pyToCBRght.addMouseListener(new MouseListener()
		{
			public void mouseEntered(MouseEvent event)  { }
			public void mouseExited(MouseEvent event)   { }
			public void mousePressed(MouseEvent event)  { }
			public void mouseReleased(MouseEvent event) { }
			
			public void mouseClicked(MouseEvent event)
			{
				// call the updateCB() method, update type 2
				updateCB(2);
				
				// clear the items from 'pyToCBRght' (combo box), and add the entries of 'pyToCBRghtOptions' (String[])
				pyToCBRght.removeAllItems();
				for (int i = 0; i < pyToCBRghtOptions.length; i++)
					pyToCBRght.addItem(pyToCBRghtOptions[i]);
			}
		});
	}
	
	// method: finds and returns the current date and time
	// source code found at: https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
	private String findDateAndTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		return(dateFormat.format(date));
	}
	
	// method: updates the data of either 'billToCBOptions' or 'pyToCBRghtOptions', depending on integer argument
	private void updateCB(int updateType)
	{
		try
		{
			// make a connection to the SQL server using the login info (global variables)
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			
			// retrieve the information from the SQL server and store it in a ResultSet object
			Statement statement = connection.createStatement();
			ResultSet result_set = statement.executeQuery("SELECT NAME FROM COMPANIES;");
			
			// move the data from the ResultSet to an ArrayList
			ArrayList<String> arraylist = new ArrayList<String>();
			while (result_set.next())
				arraylist.add(result_set.getString(1));
			
			// if updateType == 1, update 'billToCBOptions'. if updateType == 2, update 'pyToCBRghtOptions'
			if (updateType == 1)
			{
				billToCBOptions = new String[arraylist.size() + 1]; billToCBOptions[0] = "TBD--";
				for (int i = 0; i < arraylist.size(); i++)
					billToCBOptions[i + 1] = arraylist.get(i);
			}
			else
			{
				pyToCBRghtOptions = new String[arraylist.size() + 1]; pyToCBRghtOptions[0] = "Select One";
				for (int i = 0; i < arraylist.size(); i++)
					pyToCBRghtOptions[i + 1] = arraylist.get(i);
			}
			
			result_set.close();
			statement.close();
			connection.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// TODO: finish this method.
	// method: saves all of the information on the panel and saves it to the SQL server.
	private void saveAndStore()
	{
		// save all general information to 'generalInfoList'
		generalInfoList = new ArrayList<String>();
		generalInfoList.add(entrdLbl2.getText());
		generalInfoList.add((String)eqpCmboBx.getSelectedItem());
		generalInfoList.add(lngthTF.getText());
		generalInfoList.add(airRideCB.getState()? "true" : "false");
		generalInfoList.add(logisticCB.getState()? "true" : "false");
		generalInfoList.add(teamCB.getState()? "true" : "false");
		generalInfoList.add(instructionTF.getText());
		
		generalInfoList.add(cmmdtyTF.getText());
		generalInfoList.add(qnttyTF.getText());
		generalInfoList.add((String)unitCB.getSelectedItem());
		generalInfoList.add(wghtTF.getText());
		
		generalInfoList.add((String)billToCB.getSelectedItem());
		generalInfoList.add(addrssLblLft2.getText());
		generalInfoList.add(cntctTFLft.getText());
		generalInfoList.add(emlTFLft.getText());
		generalInfoList.add(telTFLft.getText());
		generalInfoList.add(faxTFLft.getText());
		generalInfoList.add(cellTFLft.getText());
		generalInfoList.add(refTFLft.getText());
		generalInfoList.add(dscntTFLft.getText());
		generalInfoList.add(trmTFLft.getText());
		generalInfoList.add(noteTFLft.getText());
		
		generalInfoList.add((String)pyToCBRght.getSelectedItem());
		generalInfoList.add(mcTFRght.getText());
		generalInfoList.add(cntctTFRght.getText());
		generalInfoList.add(emlTFRght.getText());
		generalInfoList.add(telTFRght.getText());
		generalInfoList.add(faxTFRght.getText());
		generalInfoList.add(cntctCellTFRght.getText());
		generalInfoList.add(drvrTFRght.getText());
		generalInfoList.add(drvrCellTFRght.getText());
		generalInfoList.add(withholdPayment.getState()? "true" : "false");
		generalInfoList.add(trkTFRght.getText());
		generalInfoList.add(trlTFRght.getText());
		generalInfoList.add(dscntTFRght.getText());
		generalInfoList.add(trmTFRght.getText());
		generalInfoList.add(noteTFRght.getText());
		
		// call the finalSaveUpdate() method for 'leftBot' and 'rightBot' (NLBotPanel class)
		leftBot.finalSaveUpdate();
		rightBot.finalSaveUpdate();
		
		// call the callFinalSave() method for 'leftTable' and 'rightTable'
		leftTable.callFinalSave();
		rightTable.callFinalSave();
		
		// conditional: if 'isSavedBefore' is false, get the load counter number from the 'vitals' folder, giving this load a unique case number.
		if (isSavedBefore == false)
		{
			try
			{
				// get access to the file 'load_counter.txt', located in the 'vitals' folder.
				File loadCounterFile = new File("./vitals/load_counter.txt");
				FileReader counter_in = new FileReader(loadCounterFile);
				BufferedReader counter_reader = new BufferedReader(counter_in);
				
				// get the counter number and add one to it.
				loadID = Integer.parseInt(counter_reader.readLine()) + 1;
				
				counter_reader.close();
				counter_in.close();
				
				// open a file writing stream to the same file.
				FileWriter counter_out = new FileWriter(loadCounterFile);
				BufferedWriter counter_writer = new BufferedWriter(counter_out);
				
				// write 'loadID' to the file.
				counter_writer.write(Integer.toString(loadID)); counter_writer.newLine();
				
				counter_writer.close();
				counter_out.close();
			}
			catch (FileNotFoundException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }
		}
		
		try
		{
			// make a connection to the SQL server and set up a StringBuilder for making commands.
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			StringBuilder builder = new StringBuilder();
			
			// if-else block: if 'isSavedBefore' is false, create a new entry in LOADS and create associated tables. else, update the LOADS entry and recreate the associated tables.
			if (isSavedBefore == false)
			{
				// create a new entry in the LOADS table using the SQL 'INSERT' command.
				builder.append("INSERT INTO LOADS VALUES (" + "'" + loadID + "', ");
				for (int i = 0; i < generalInfoList.size(); i++)
				{
					if (i == generalInfoList.size() - 1)
						builder.append("'" + generalInfoList.get(i) + "');");
					else
						builder.append("'" + generalInfoList.get(i) + "', ");
				}
				statement.executeUpdate(builder.toString());
				
				// create a table for the left side panel table using the SQL 'CREATE TABLE' command.
				builder = new StringBuilder();
				String tableName = "LOADTABLELEFT" + loadID;
				String[] columnNames = new String[] {"ITEM",  "DESCRIPTION", "QUANTITY", "RATE", "AMOUNT"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnNames.length; i++)
				{
					if (i == columnNames.length - 1)
						builder.append(columnNames[i] + " varchar(500));");
					else
						builder.append(columnNames[i] + " varchar(500), ");
				}
				statement.executeUpdate(builder.toString());
				
				// fill the left side panel table with values from the table using the SQL 'INSERT' command.
				ArrayList<PartialRow> partialRowList = leftTable.getPartialRowList();
				for (PartialRow currObject : partialRowList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + currObject.getItem() + "', ");
					builder.append("'" + currObject.getDescription() + "', ");
					builder.append("'" + currObject.getQuantity() + "', ");
					builder.append("'" + currObject.getRate() + "', ");
					builder.append("'" + currObject.getAmount() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// create a table for the right side panel table using the SQL 'CREATE TABLE' command.
				builder = new StringBuilder();
				tableName = "LOADTABLERIGHT" + loadID;
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnNames.length; i++)
				{
					if (i == columnNames.length - 1)
						builder.append(columnNames[i] + " varchar(500));");
					else
						builder.append(columnNames[i] + " varchar(500), ");
				}
				statement.executeUpdate(builder.toString());
				
				// fill the right side panel table with values from the table using the SQL 'INSERT' command.
				partialRowList = rightTable.getPartialRowList();
				for (PartialRow currObject : partialRowList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + currObject.getItem() + "', ");
					builder.append("'" + currObject.getDescription() + "', ");
					builder.append("'" + currObject.getQuantity() + "', ");
					builder.append("'" + currObject.getRate() + "', ");
					builder.append("'" + currObject.getAmount() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// create a table for the left side NLBotPanel object using the SQL 'CREATE TABLE' command.
				builder = new StringBuilder();
				tableName = "NLBOTPANELLEFT" + loadID;
				columnNames = new String[] {"ROW", "DATE", "TIMEFROM", "TIMETO", "APPT#", "COMPANY", "ADDRESS1", "ADDRESS2", "CITY", "STATE", 
											"ZIP", "CONTACT", "TEL", "REF", "NOTE"};
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnNames.length; i++)
				{
					if (i == columnNames.length - 1)
						builder.append(columnNames[i] + " varchar(500));");
					else
						builder.append(columnNames[i] + " varchar(500), ");
				}
				statement.executeUpdate(builder.toString());
				
				// fill the left side NLBotPanel object using the SQL 'INSERT' command.
				ArrayList<NLBotPanelDataHolder> panelDataList  = leftBot.getDataList();
				int counter = 0;
				for (NLBotPanelDataHolder currObj : panelDataList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append(++counter);
					builder.append("'" + currObj.getDate() + "', ");
					builder.append("'" + currObj.getTime_from() + "', ");
					builder.append("'" + currObj.getTime_to() + "', ");
					builder.append("'" + currObj.getAppt() + "', ");
					builder.append("'" + currObj.getCompany() + "', ");
					builder.append("'" + currObj.getAddress1() + "', ");
					builder.append("'" + currObj.getAddress2() + "', ");
					builder.append("'" + currObj.getCity() + "', ");
					builder.append("'" + currObj.getState() + "', ");
					builder.append("'" + currObj.getZip() + "', ");
					builder.append("'" + currObj.getContact() + "', ");
					builder.append("'" + currObj.getTel() + "', ");
					builder.append("'" + currObj.getRef() + "', ");
					builder.append("'" + currObj.getNote() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// create a table for the right side NLBotPanel object using the SQL 'CREATE TABLE' command.
				builder = new StringBuilder();
				tableName = "NLBOTPANELRIGHT" + loadID;
				builder.append("CREATE TABLE " + tableName + " (");
				for (int i = 0; i < columnNames.length; i++)
				{
					if (i == columnNames.length - 1)
						builder.append(columnNames[i] + " varchar(500));");
					else
						builder.append(columnNames[i] + " varchar(500), ");
				}
				statement.executeUpdate(builder.toString());
				
				// fill the right side NLBotpanel object using the SQL 'INSERT' command.
				panelDataList = rightBot.getDataList();
				counter = 0;
				for (NLBotPanelDataHolder currObj : panelDataList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append(++counter);
					builder.append("'" + currObj.getDate() + "', ");
					builder.append("'" + currObj.getTime_from() + "', ");
					builder.append("'" + currObj.getTime_to() + "', ");
					builder.append("'" + currObj.getAppt() + "', ");
					builder.append("'" + currObj.getCompany() + "', ");
					builder.append("'" + currObj.getAddress1() + "', ");
					builder.append("'" + currObj.getAddress2() + "', ");
					builder.append("'" + currObj.getCity() + "', ");
					builder.append("'" + currObj.getState() + "', ");
					builder.append("'" + currObj.getZip() + "', ");
					builder.append("'" + currObj.getContact() + "', ");
					builder.append("'" + currObj.getTel() + "', ");
					builder.append("'" + currObj.getRef() + "', ");
					builder.append("'" + currObj.getNote() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// save this load to the LOADLIST table, using saveInListTable() method, save mode.
				saveInListTable(true);
				
				// set 'isSavedBefore to true.
				isSavedBefore = true;
				
				// display a message dialog notifying the user that the load has been saved.
				JOptionPane.showMessageDialog(null, "Load saved.");
			}
			else
			{
				// update the entry in the LOADS table using the SQL 'UPDATE' command.
				String[] columnNames = new String[] {"DATEENTERED", "EQUIP", "LENGTH", "AIRRIDE", "LOGISTIC", "TEAM", "INSTRUCTION", "COMMODITY", "QUANTITY", 
													 "UNIT", "WEIGHT", "BILLTO", "ADDRESS", "BTCONTACT", "BTEMAIL", "BTTEL", "BTFAX", "BTCELL", "BTREF#", 
													 "BTDISCOUNT", "BTTERM", "BTNOTE", "PAYTO", "MC#", "PTCONTACT", "PTEMAIL", "PTTEL", "PTFAX", "PTCELL", 
													 "PTDRIVER", "PTDCELL", "WITHHOLDPAYMENT", "PTDTRUCK#", "PTDTRAILER#", "PTDDISCOUNT", "PTDTERM", "PTNOTE"};
				builder.append("UPDATE LOADS SET ");
				for (int i = 0; i < generalInfoList.size(); i++)
				{
					if (i == generalInfoList.size() - 1)
						builder.append(columnNames[i] + " = '" + generalInfoList.get(i) + "' ");
					else
						builder.append(columnNames[i] + " = '" + generalInfoList.get(i) + "', ");
				}
				builder.append("WHERE LOADID = '" + loadID + "';");
				statement.executeUpdate(builder.toString());
				
				// delete all entries from LOADTABLELEFT and add the new entries to it.
				builder = new StringBuilder();
				String tableName = "LOADTABLELEFT" + loadID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				ArrayList<PartialRow> partialRowList = leftTable.getPartialRowList();
				for (PartialRow currObject : partialRowList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + currObject.getItem() + "', ");
					builder.append("'" + currObject.getDescription() + "', ");
					builder.append("'" + currObject.getQuantity() + "', ");
					builder.append("'" + currObject.getRate() + "', ");
					builder.append("'" + currObject.getAmount() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// delete all entries from LOADTABLERIGHT and add the new entries to it.
				builder = new StringBuilder();
				tableName = "LOADTABLERIGHT" + loadID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				partialRowList = rightTable.getPartialRowList();
				for (PartialRow currObject : partialRowList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append("'" + currObject.getItem() + "', ");
					builder.append("'" + currObject.getDescription() + "', ");
					builder.append("'" + currObject.getQuantity() + "', ");
					builder.append("'" + currObject.getRate() + "', ");
					builder.append("'" + currObject.getAmount() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// delete all entries from NLBOTPANELLEFT and add the entries to it.
				builder = new StringBuilder();
				tableName = "NLBOTPANELLEFT" + loadID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				ArrayList<NLBotPanelDataHolder> panelDataList  = leftBot.getDataList();
				int counter = 0;
				for (NLBotPanelDataHolder currObj : panelDataList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append(++counter);
					builder.append("'" + currObj.getDate() + "', ");
					builder.append("'" + currObj.getTime_from() + "', ");
					builder.append("'" + currObj.getTime_to() + "', ");
					builder.append("'" + currObj.getAppt() + "', ");
					builder.append("'" + currObj.getCompany() + "', ");
					builder.append("'" + currObj.getAddress1() + "', ");
					builder.append("'" + currObj.getAddress2() + "', ");
					builder.append("'" + currObj.getCity() + "', ");
					builder.append("'" + currObj.getState() + "', ");
					builder.append("'" + currObj.getZip() + "', ");
					builder.append("'" + currObj.getContact() + "', ");
					builder.append("'" + currObj.getTel() + "', ");
					builder.append("'" + currObj.getRef() + "', ");
					builder.append("'" + currObj.getNote() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// delete all entries from NLBOTPANELRIGHT and add the entries to it.
				builder = new StringBuilder();
				tableName = "NLBOTPANELRIGHT" + loadID;
				statement.executeUpdate("DELETE FROM " + tableName + ";");
				panelDataList = rightBot.getDataList();
				counter = 0;
				for (NLBotPanelDataHolder currObj : panelDataList)
				{
					builder = new StringBuilder();
					
					builder.append("INSERT INTO " + tableName + " VALUES (");
					builder.append(++counter);
					builder.append("'" + currObj.getDate() + "', ");
					builder.append("'" + currObj.getTime_from() + "', ");
					builder.append("'" + currObj.getTime_to() + "', ");
					builder.append("'" + currObj.getAppt() + "', ");
					builder.append("'" + currObj.getCompany() + "', ");
					builder.append("'" + currObj.getAddress1() + "', ");
					builder.append("'" + currObj.getAddress2() + "', ");
					builder.append("'" + currObj.getCity() + "', ");
					builder.append("'" + currObj.getState() + "', ");
					builder.append("'" + currObj.getZip() + "', ");
					builder.append("'" + currObj.getContact() + "', ");
					builder.append("'" + currObj.getTel() + "', ");
					builder.append("'" + currObj.getRef() + "', ");
					builder.append("'" + currObj.getNote() + "');");
					
					statement.executeUpdate(builder.toString());
				}
				
				// add this load to the LOADLIST table using saveInListTable() method, update mode.
				saveInListTable(false);
				
				// display a message dialog notifying the user that the changes have been saved.
				JOptionPane.showMessageDialog(null, "Changes saved.");
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// method: adds this load to the LOADLIST table. accepts boolean 'true' for save, 'false' for update.
	private void saveInListTable(boolean saveCase)
	{
		// the LOADLIST table has 12 columns
		String[] values = new String[12];
		
		values[0] = Integer.toString(loadID);
		values[1] = entrdLbl2.getText();
		values[2] = (String)billToCB.getSelectedItem();
		values[3] = telTFLft.getText();
		values[4] = refTFLft.getText();
		values[5] = (String)pyToCBRght.getSelectedItem();
		values[6] = telTFRght.getText();
		
		if (leftBot.getDataList().size() != 0)
			values[7] = leftBot.getDataList().get(0).getCity() + ", " + leftBot.getDataList().get(0).getState();
		else
			values[7] = null;
		
		if (rightBot.getDataList().size() != 0)
			values[8] = rightBot.getDataList().get(rightBot.getDataList().size() - 1).getCity()
			  + "', " + rightBot.getDataList().get(rightBot.getDataList().size() - 1).getState();
		else
			values[8] = null;
		
		values[9] = trkTFRght.getText();
		values[10] = drvrTFRght.getText();
		values[11] = drvrCellTFRght.getText();

		try
		{
			// set up SQL connection and related components.
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			StringBuilder builder = new StringBuilder();
			
			if (saveCase)
			{
				// set up the SQL 'INSERT' statement.
				builder.append("INSERT INTO LOADLIST VALUES (");
				for (int i = 0; i < values.length; i++)
				{
					if (i == values.length - 1)
						builder.append("'" + values[i] + "');");
					else
						builder.append("'" + values[i] + "', ");
				}
				
				// execute the SQL command.
				statement.executeUpdate(builder.toString());
			}
			else
			{
				// array of the column names of the LOADLIST table.
				String[] names = new String[] {"LOADID", "ENTERED", "CUSTOMER", "CUSTOMERPHONE", "REF#", "CARRIER_DRIVER", 
											   "CARRIERPHONE", "ORIGIN", "DESTINATION", "TRUCK#", "DRIVER", "DRIVERCELL"};
				
				// set up the SQL 'UPDATE' statement.
				builder.append("UPDATE LOADLIST SET ");
				for (int i = 1; i < names.length; i++)
				{
					if (i == names.length - 1)
						builder.append(names[i] + " = '" + values[i] + "' ");
					else
						builder.append(names[i] + " = '" + values[i] + "', ");
				}
				builder.append("WHERE LOADID = '" + loadID + "';");
				
				// execute the SQL command.
				statement.executeUpdate(builder.toString());
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// TODO: finish this method
	// method: prints out an invoice of this load.
	private void printLoad()
	{
		
	}
	
	// main method: temporary run method to display the panel
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new LoadMaker(); } });
	}
}
