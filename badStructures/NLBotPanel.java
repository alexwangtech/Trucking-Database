/**
 * lower section of the LoadMaker window
 * as the functions and appearances of both pick and drop are the same, the
 * only thing that needs to be changed is the titles, either "Pick" or "Drop"
 * since the panels will be called as objects, need to create methods for getting and setting
 * data. most likely, as multiple instances may be used, in LoadMaker, ArrayList objects will
 * be used to store multiple instances of these classes
 */

package badStructures;

import windows.CompanyMaker;

import storageDevices.NLBotPanelDataHolder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class NLBotPanel extends JPanel
{
	GridBagConstraints c;
	JPanel topPanel, botPanel;
	JLabel titleLbl, dateLbl, timeFromLbl, toLbl, apptLbl, companyLbl, addressLbl, cityLbl,
		   stateLbl, zipLbl, telLbl, refLbl, noteLbl;
	JTextField dateTF, timeFromTF, toTF, apptTF, addressTF1, addressTF2, cityTF, stateTF,
			   zipTF, contactTF, telTF, refTF, noteTF;
	JButton prevBtn, nextBtn, plusBtn, delBtn, newBtn, contactBtn;
	JComboBox<String> companyCB;
	String[] companyOptions = {"Select One"};
	String sqlUrl, sqlUsername, sqlPassword;
	ArrayList<String> tempList;
	ArrayList<NLBotPanelDataHolder> dataHolderList;
	int dataIndex;
	
	// constructor: accepts an integer argument. 1 for pick up type, 2 for drop type
	public NLBotPanel(int panelType)
	{	
		// NEW EDIT: the list for 'comanyCB' must be a list of companies from the COMPANIES table from the SQL server
		try
		{
			// retrieve the SQL connection info from the 'vitals' folder (one time only) (variables are declared globally)
			File sqlFile = new File("./vitals/sql_info.txt");
			FileReader in = new FileReader(sqlFile);
			BufferedReader sqlReader = new BufferedReader(in);
			
			sqlUrl = sqlReader.readLine();
			sqlUsername = sqlReader.readLine();
			sqlPassword = sqlReader.readLine();
			
			sqlReader.close();
			in.close();
			
			// create a connection to the SQL server and get a ResultSet containing the necessary information
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			ResultSet result_set = statement.executeQuery("SELECT NAME FROM COMPANIES");
			
			// copy the information to 'tempList' (ArrayList)
			tempList = new ArrayList<String>();
			while (result_set.next())
				tempList.add(result_set.getString(1));
			
			// copy the information to 'companyOptions' (the String[] for 'companyCB')
			companyOptions = new String[tempList.size() + 1]; companyOptions[0] = "Select One";
			for (int i = 0; i < tempList.size(); i++)
				companyOptions[i + 1] = tempList.get(i);
			
			result_set.close();
			statement.close();
			connection.close();
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }
		
		// initialize 'dataHolderList' : stores all of the info for NLBotPanelDataHolder objects
		dataHolderList = new ArrayList<NLBotPanelDataHolder>();
		
		// initialize 'dataIndex' to 0: keeps track of which index 'dataHolderList' is at.
		dataIndex = 0;
		
		// initialize 'titleLbl' based on provided integer argument
		if (panelType == 1)
			titleLbl = new JLabel("Pick Up");
		else
			titleLbl = new JLabel("Drop Off");
		
		titleLbl.setBorder(BorderFactory.createLineBorder(Color.BLUE,1, true));
		
		prevBtn = new JButton("<");								// previous button
		nextBtn = new JButton(">");								// next button
		plusBtn = new JButton("+");								// plus button
		delBtn = new JButton("x");								// delete button
		dateLbl = new JLabel("Date: ");							// date label
		dateTF = new JTextField(10);							// date text field
		timeFromLbl = new JLabel("Time From: ");				// time from label
		timeFromTF = new JTextField(5);							// time from text field
		toLbl = new JLabel("To:");								// time to label
		toTF = new JTextField(5);								// time to text field
		apptLbl = new JLabel("Appt#:");							// appointment# label
		apptTF = new JTextField(10);							// appointment# text field
		companyLbl = new JLabel("Company: ");					// company label
		companyCB = new JComboBox<String>(companyOptions);		// company combo box
		newBtn = new JButton("New");							// new button
		addressLbl = new JLabel("Address: ");					// address label
		addressTF1 = new JTextField(10);						// address text field 1
		addressTF2 = new JTextField(10);						// address text field 2
		cityLbl = new JLabel("City: ");							// city label
		cityTF = new JTextField(10);							// city text field
		stateLbl = new JLabel("State: ");						// state label
		stateTF = new JTextField(5);							// state text field
		zipLbl = new JLabel("Zip: ");							// zip label
		zipTF = new JTextField(1);								// zip text field
		contactBtn = new JButton("Contact");					// contact button
		contactTF = new JTextField(10);							// contact text field
		telLbl = new JLabel("Tel: ");							// telephone label
		telTF = new JTextField(10);								// telephone text field

		// initialize 'refLbl' based on provided integer argument
		if (panelType == 1)
			refLbl = new JLabel("Ref B/L#: ");
		else
			refLbl = new JLabel("Ref/PO#: ");
			
		refTF = new JTextField(10);						// ref text field
		noteLbl = new JLabel("Note: ");					// note label
		noteTF = new JTextField(10);					// note text field
		
		// add the top five components to their own panel using GridBagLayout
		topPanel = new JPanel(); topPanel.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.weightx = 0.2;
		c.gridx = 0; c.gridy = 0; topPanel.add(titleLbl, c);	// title label
		c.gridx = 1; c.gridy = 0; topPanel.add(prevBtn, c);		// previous button
		c.gridx = 2; c.gridy = 0; topPanel.add(nextBtn, c);		// next button
		c.gridx = 3; c.gridy = 0; topPanel.add(plusBtn, c);		// plus button
		c.gridx = 4; c.gridy = 0; topPanel.add(delBtn, c);		// delete button
		
		// add the rest of the components to a panel using GridBagLayout
		botPanel = new JPanel(); botPanel.setLayout(new GridBagLayout()); c= new GridBagConstraints();
		c.insets = new Insets(1, 1, 1, 1);
		c.gridx = 0; c.gridy = 0; botPanel.add(dateLbl, c);			// date label
		c.gridx = 1; c.gridy = 0; botPanel.add(dateTF, c);			// date text field
		c.gridx = 2; c.gridy = 0; botPanel.add(timeFromLbl, c);		// time from label
		c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3; c.gridy = 0; botPanel.add(timeFromTF, c);		// time from text field
		c.weightx = 0; c.fill = GridBagConstraints.NONE;
		c.gridx = 4; c.gridy = 0; botPanel.add(toLbl, c);			// time to label
		c.gridx = 5; c.gridy = 0; botPanel.add(toTF, c);			// time to text field
		c.gridx = 0; c.gridy = 1; botPanel.add(apptLbl, c);			// appointment# label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 1; botPanel.add(apptTF, c);			// appointment# text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 2; botPanel.add(companyLbl, c);		// company label
		c.gridwidth = 4; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 2; botPanel.add(companyCB, c);		// company combo box
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 5; c.gridy = 2; botPanel.add(newBtn, c);			// new button
		c.gridx = 0; c.gridy = 3; botPanel.add(addressLbl, c);		// address label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 3; botPanel.add(addressTF1, c);		// address text field 1
		c.gridx = 1; c.gridy = 4; botPanel.add(addressTF2, c);		// address text field 2
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 5; botPanel.add(cityLbl, c);			// city label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 5; botPanel.add(cityTF, c);			// city text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 6; botPanel.add(stateLbl, c);		// state label
		c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 6; botPanel.add(stateTF, c);			// state text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 3; c.gridy = 6; botPanel.add(zipLbl, c);			// zip label
		c.fill = GridBagConstraints.HORIZONTAL; c.gridwidth = 2;
		c.gridx = 4; c.gridy = 6; botPanel.add(zipTF, c);			// zip text field
		c.fill = GridBagConstraints.NONE; c.gridwidth = 1;
		c.gridx = 0; c.gridy = 7; botPanel.add(contactBtn, c);		// contact button
		c.gridwidth = 2; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 7; botPanel.add(contactTF, c);		// contact text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 3; c.gridy = 7; botPanel.add(telLbl, c);			// telephone label
		c.gridwidth = 2;
		c.gridx = 4; c.gridy = 7; botPanel.add(telTF, c);			// telephone text field
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 8; botPanel.add(refLbl, c);			// reference label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 8; botPanel.add(refTF, c);			// reference text field
		c.gridwidth = 1; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 9; botPanel.add(noteLbl, c);			// note label
		c.gridwidth = 5; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1; c.gridy = 9; botPanel.add(noteTF, c);			// note text field
		
		// combine topPanel and botPanel to the main panel (this)
		this.setLayout(new GridBagLayout()); c = new GridBagConstraints();
		c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; c.gridy = 0; this.add(topPanel, c);
		c.weightx = 0; c.fill = GridBagConstraints.NONE;
		c.gridx = 0; c.gridy = 1; this.add(botPanel, c);
		
		// temporary: add a red border
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		// ============================================================================================ //
		// FUNCTIONALITY SECTION OF THE GUI: CONTAINS LISTENER OBJECTS FOR ALL OF THE BUTTON/COMPONENTS //
		// ============================================================================================ //
		
		// ActionListener for the 'New' button: calls an instance of CompanyMaker()
		newBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { new CompanyMaker(); } });
			}
		});
		
		// ActionListener for the '<' button: loads in the info of the previous NLBotPanelDataHolder object
		prevBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if index is one that is not saved in 'dataHolderList', call the saveObject() method. otherwise, call the updateObject() method.
				if (dataIndex == dataHolderList.size())
				{
					// make sure that at least one value is not null
					if (dateTF.getText().length() != 0 ||
						timeFromTF.getText().length() != 0 ||
						toTF.getText().length() != 0 ||
						apptTF.getText().length() != 0 ||
						(!((String)companyCB.getSelectedItem()).equals("Select One")) ||
						addressTF1.getText().length() != 0 ||
						addressTF2.getText().length() != 0 ||
						cityTF.getText().length() != 0 ||
						stateTF.getText().length() != 0 ||
						zipTF.getText().length() != 0 ||
						contactTF.getText().length() != 0 ||
						telTF.getText().length() != 0 ||
						refTF.getText().length() != 0 ||
						noteTF.getText().length() != 0)
					{
						saveObject();
					}
				}
				else
					updateObject();
				
				// simply return 'dataIndex' is already 0, or 'dataHolderList' is empty.
				// otherwise, set index to one before, and display that object's values.
				if (dataIndex == 0 || dataHolderList.size() == 0)
					return;
				else
				{
					--dataIndex;
					dateTF.setText(dataHolderList.get(dataIndex).getDate());
					timeFromTF.setText(dataHolderList.get(dataIndex).getTime_from());
					toTF.setText(dataHolderList.get(dataIndex).getTime_to());
					apptTF.setText(dataHolderList.get(dataIndex).getAppt());
					companyCB.setSelectedItem(dataHolderList.get(dataIndex).getCompany());
					addressTF1.setText(dataHolderList.get(dataIndex).getAddress1());
					addressTF2.setText(dataHolderList.get(dataIndex).getAddress2());
					cityTF.setText(dataHolderList.get(dataIndex).getCity());
					stateTF.setText(dataHolderList.get(dataIndex).getState());
					zipTF.setText(dataHolderList.get(dataIndex).getZip());
					contactTF.setText(dataHolderList.get(dataIndex).getContact());
					telTF.setText(dataHolderList.get(dataIndex).getTel());
					refTF.setText(dataHolderList.get(dataIndex).getRef());
					noteTF.setText(dataHolderList.get(dataIndex).getNote());
				}
			}
		});
		
		// ActionListener for the '>' button: loads in the info of the next NLBotPanelDataHolder object
		nextBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in 'dataHolderList', call the saveObject() method. otherwise, call the updateObject() method
				if (dataIndex == dataHolderList.size())
				{
					// make sure that at least one value is not null
					if (dateTF.getText().length() != 0 ||
						timeFromTF.getText().length() != 0 ||
						toTF.getText().length() != 0 ||
						apptTF.getText().length() != 0 ||
						(!((String)companyCB.getSelectedItem()).equals("Select One")) ||
						addressTF1.getText().length() != 0 ||
						addressTF2.getText().length() != 0 ||
						cityTF.getText().length() != 0 ||
						stateTF.getText().length() != 0 ||
						zipTF.getText().length() != 0 ||
						contactTF.getText().length() != 0 ||
						telTF.getText().length() != 0 ||
						refTF.getText().length() != 0 ||
						noteTF.getText().length() != 0)
					{
						saveObject();
					}
				}
				else
					updateObject();
				
				// if the index is already reached the max, do nothing. otherwise, add one to the index, and display the corresponding object's values
				if (dataIndex >= dataHolderList.size() - 1)
					return;
				else
				{
					++dataIndex;
					dateTF.setText(dataHolderList.get(dataIndex).getDate());
					timeFromTF.setText(dataHolderList.get(dataIndex).getTime_from());
					toTF.setText(dataHolderList.get(dataIndex).getTime_to());
					apptTF.setText(dataHolderList.get(dataIndex).getAppt());
					companyCB.setSelectedItem(dataHolderList.get(dataIndex).getCompany());
					addressTF1.setText(dataHolderList.get(dataIndex).getAddress1());
					addressTF2.setText(dataHolderList.get(dataIndex).getAddress2());
					cityTF.setText(dataHolderList.get(dataIndex).getCity());
					stateTF.setText(dataHolderList.get(dataIndex).getState());
					zipTF.setText(dataHolderList.get(dataIndex).getZip());
					contactTF.setText(dataHolderList.get(dataIndex).getContact());
					telTF.setText(dataHolderList.get(dataIndex).getTel());
					refTF.setText(dataHolderList.get(dataIndex).getRef());
					noteTF.setText(dataHolderList.get(dataIndex).getNote());
				}
			}
		});
		
		// ActionListener for the '+' button: loads in a new blank slate for a new NLBotPanelDataHolder object
		plusBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if the index is one that is not saved in 'dataHolderList', call the saveObject() method. otherwise, call the updateObject() method.
				if (dataIndex == dataHolderList.size())
				{
					// make sure that at least one value is not null
					if (dateTF.getText().length() != 0 ||
						timeFromTF.getText().length() != 0 ||
						toTF.getText().length() != 0 ||
						apptTF.getText().length() != 0 ||
						(!((String)companyCB.getSelectedItem()).equals("Select One")) ||
						addressTF1.getText().length() != 0 ||
						addressTF2.getText().length() != 0 ||
						cityTF.getText().length() != 0 ||
						stateTF.getText().length() != 0 ||
						zipTF.getText().length() != 0 ||
						contactTF.getText().length() != 0 ||
						telTF.getText().length() != 0 ||
						refTF.getText().length() != 0 ||
						noteTF.getText().length() != 0)
					{
						saveObject();
					}
					else
						return;
				}
				else
					updateObject();
				
				// hop the index to 'dataHolderList' max index + 1 (aka list.size())
				dataIndex = dataHolderList.size();
				
				// set all text fields to null
				dateTF.setText(null);
				timeFromTF.setText(null);
				toTF.setText(null);
				apptTF.setText(null);
				companyCB.setSelectedItem("Select One");
				addressTF1.setText(null);
				addressTF2.setText(null);
				cityTF.setText(null);
				stateTF.setText(null);
				zipTF.setText(null);
				contactTF.setText(null);
				telTF.setText(null);
				refTF.setText(null);
				noteTF.setText(null);
			}
		});
		
		// ActionListener for the 'x' button: deletes the current NLBotPanelDataHolder object
		delBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// if object is not saved in 'dataHolderList', simply clear the text fields and return
				if (dataIndex >= dataHolderList.size())
				{
					dateTF.setText(null);
					timeFromTF.setText(null);
					toTF.setText(null);
					apptTF.setText(null);
					companyCB.setSelectedItem("Select One");
					addressTF1.setText(null);
					addressTF2.setText(null);
					cityTF.setText(null);
					stateTF.setText(null);
					zipTF.setText(null);
					contactTF.setText(null);
					telTF.setText(null);
					refTF.setText(null);
					noteTF.setText(null);
					return;
				}
				
				// otherwise, delete the current NLBotPanelDataHolder object
				dataHolderList.remove(dataIndex);
				
				// if 'dataHolderList' is empty, reset everything to null
				if (dataHolderList.size() == 0)
				{
					dateTF.setText(null);
					timeFromTF.setText(null);
					toTF.setText(null);
					apptTF.setText(null);
					companyCB.setSelectedItem("Select One");
					addressTF1.setText(null);
					addressTF2.setText(null);
					cityTF.setText(null);
					stateTF.setText(null);
					zipTF.setText(null);
					contactTF.setText(null);
					telTF.setText(null);
					refTF.setText(null);
					noteTF.setText(null);
				}
				else
				{
					// if 'dataIndex' is still within bounds, set to next object in 'dataHolderList'
					if (dataIndex <= dataHolderList.size() - 1)
					{
						dateTF.setText(dataHolderList.get(dataIndex).getDate());
						timeFromTF.setText(dataHolderList.get(dataIndex).getTime_from());
						toTF.setText(dataHolderList.get(dataIndex).getTime_to());
						apptTF.setText(dataHolderList.get(dataIndex).getAppt());
						companyCB.setSelectedItem(dataHolderList.get(dataIndex).getCompany());
						addressTF1.setText(dataHolderList.get(dataIndex).getAddress1());
						addressTF2.setText(dataHolderList.get(dataIndex).getAddress2());
						cityTF.setText(dataHolderList.get(dataIndex).getCity());
						stateTF.setText(dataHolderList.get(dataIndex).getState());
						zipTF.setText(dataHolderList.get(dataIndex).getZip());
						contactTF.setText(dataHolderList.get(dataIndex).getContact());
						telTF.setText(dataHolderList.get(dataIndex).getTel());
						refTF.setText(dataHolderList.get(dataIndex).getRef());
						noteTF.setText(dataHolderList.get(dataIndex).getNote());
					}
					// if 'dataIndex' is out of bounds, move index one backwards and display values
					else
					{
						--dataIndex;
						dateTF.setText(dataHolderList.get(dataIndex).getDate());
						timeFromTF.setText(dataHolderList.get(dataIndex).getTime_from());
						toTF.setText(dataHolderList.get(dataIndex).getTime_to());
						apptTF.setText(dataHolderList.get(dataIndex).getAppt());
						companyCB.setSelectedItem(dataHolderList.get(dataIndex).getCompany());
						addressTF1.setText(dataHolderList.get(dataIndex).getAddress1());
						addressTF2.setText(dataHolderList.get(dataIndex).getAddress2());
						cityTF.setText(dataHolderList.get(dataIndex).getCity());
						stateTF.setText(dataHolderList.get(dataIndex).getState());
						zipTF.setText(dataHolderList.get(dataIndex).getZip());
						contactTF.setText(dataHolderList.get(dataIndex).getContact());
						telTF.setText(dataHolderList.get(dataIndex).getTel());
						refTF.setText(dataHolderList.get(dataIndex).getRef());
						noteTF.setText(dataHolderList.get(dataIndex).getNote());
					}
				}
			}
		});
		
		// MouseListener for 'companyCB': refreshes the list of company names from the COMPANIES table from the SQL server
		companyCB.addMouseListener(new MouseListener()
		{
			public void mouseEntered(MouseEvent event)  { }
			public void mouseExited(MouseEvent event)   { }
			public void mousePressed(MouseEvent event)  { }
			public void mouseReleased(MouseEvent event) { } 
			
			public void mouseClicked(MouseEvent event)
			{
				try
				{
					// get a ResultSet of the necessary information from the COMPANIES table from the SQL server
					Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
					Statement statement = connection.createStatement();
					ResultSet result_set = statement.executeQuery("SELECT NAME FROM COMPANIES;");
					
					// copy the data to 'tempList'
					tempList = new ArrayList<String>();
					while (result_set.next())
						tempList.add(result_set.getString(1));
					
					// copy the data from 'tempList' to 'companyOptions'
					companyOptions = new String[tempList.size() + 1]; companyOptions[0] = "Select One";
					for (int i = 0; i < tempList.size(); i++)
						companyOptions[i + 1] = tempList.get(i);
					
					// remove all entries from 'companyCB' and add the new entries of 'companyOptions' to it.
					companyCB.removeAllItems();
					for (String item : companyOptions)
						companyCB.addItem(item);
					
					result_set.close();
					statement.close();
					connection.close();
				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		});
		
		// ActionListener for 'contactBtn'. opens up a frame where the user can select a contact
		contactBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				// the data that will be loaded is dependent on the selection in 'companyCB'. if the user has not selected anything, this button won't do anything.
				if (((String)companyCB.getSelectedItem()).equals("Select One"))
					return;
				
				String[] columnNames = new String[] {"Name", "Telephone"};
				String[][] columnValues;
				
				// get the current item in 'companyCB'.
				String selectedCompany = (String)companyCB.getSelectedItem();
				
				try
				{
					// get the data from the SQL server, if it exists. if the table does not exist, a message dialog will be displayed, and the method will end.
					Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
					Statement statement = connection.createStatement();
					ResultSet result_set = statement.executeQuery("SELECT * FROM " + selectedCompany + "PICKDROPCONTACTS;");
					
					// copy the data from the ResultSet to a temporary ArrayList.
					ArrayList<String> templist = new ArrayList<String>();
					while (result_set.next())
					{
						templist.add(result_set.getString(1));
						templist.add(result_set.getString(2));
					}
					
					// copy the data from 'templist' to 'columnValues'.
					int counter = 0;
					columnValues = new String[templist.size() / 2][2];
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
				
				// organize the data into a JTable object. set cells non-editable.
				JTable contactsTable = new JTable(columnValues, columnNames) { public boolean isCellEditable(int row, int column) { return false; } };
				contactsTable.setFillsViewportHeight(true);		// fills the entire height of container
				
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
							String doubleClickTel = (String)contactsTable.getValueAt(contactsTable.getSelectedRow(), 1);
							
							// set 'contactTF' and 'telTF' to the values that were just retrieved
							contactTF.setText(doubleClickName);
							telTF.setText(doubleClickTel);
							
							// dispose the temporary window, 'contactsFrame'
							contactsFrame.dispose();
						}
					}
				});
			}
		});
	}
	
	// method: saves the information on the panel to a new NLBotPanelDataHolder object and saves it to 'dataHolderList'
	private void saveObject()
	{
		NLBotPanelDataHolder newObject = new NLBotPanelDataHolder();
		
		newObject.setDate(dateTF.getText());
		newObject.setTime_from(timeFromTF.getText());
		newObject.setTime_to(toTF.getText());
		newObject.setAppt(apptTF.getText());
		newObject.setCompany((String)companyCB.getSelectedItem());
		newObject.setAddress1(addressTF1.getText());
		newObject.setAddress2(addressTF2.getText());
		newObject.setCity(cityTF.getText());
		newObject.setState(stateTF.getText());
		newObject.setZip(zipTF.getText());
		newObject.setContact(contactTF.getText());
		newObject.setTel(telTF.getText());
		newObject.setRef(refTF.getText());
		newObject.setNote(noteTF.getText());
		
		dataHolderList.add(newObject);
	}
	
	// method: updates the NLBotPanelDataHolder object at the current 'dataIndex' value in 'dataHolderList'
	private void updateObject()
	{
		dataHolderList.get(dataIndex).setDate(dateTF.getText());
		dataHolderList.get(dataIndex).setTime_from(timeFromTF.getText());
		dataHolderList.get(dataIndex).setTime_to(toTF.getText());
		dataHolderList.get(dataIndex).setAppt(apptTF.getText());
		dataHolderList.get(dataIndex).setCompany((String)companyCB.getSelectedItem());
		dataHolderList.get(dataIndex).setAddress1(addressTF1.getText());
		dataHolderList.get(dataIndex).setAddress2(addressTF2.getText());
		dataHolderList.get(dataIndex).setCity(cityTF.getText());
		dataHolderList.get(dataIndex).setState(stateTF.getText());
		dataHolderList.get(dataIndex).setZip(zipTF.getText());
		dataHolderList.get(dataIndex).setContact(contactTF.getText());
		dataHolderList.get(dataIndex).setTel(telTF.getText());
		dataHolderList.get(dataIndex).setRef(refTF.getText());
		dataHolderList.get(dataIndex).setNote(noteTF.getText());
	}
	
	// public method: saves/updates current entry, in preparation for save/update to SQL server
	public void finalSaveUpdate()
	{
		// if current entry is not saved in 'dataHolderList', call the save method. otherwise, call the update method.
		if (dataIndex == dataHolderList.size())
		{
			// make sure that at least one field is not null
			if (dateTF.getText().length() != 0 ||
				timeFromTF.getText().length() != 0 ||
				toTF.getText().length() != 0 ||
				apptTF.getText().length() != 0 ||
				(!((String)companyCB.getSelectedItem()).equals("Select One")) ||
				addressTF1.getText().length() != 0 ||
				addressTF2.getText().length() != 0 ||
				cityTF.getText().length() != 0 ||
				stateTF.getText().length() != 0 ||
				zipTF.getText().length() != 0 ||
				contactTF.getText().length() != 0 ||
				telTF.getText().length() != 0 ||
				refTF.getText().length() != 0 ||
				noteTF.getText().length() != 0)
			{
				saveObject();
			}
		}
		else
			updateObject();
	}
	
	// public method: returns 'dataHolderList'
	public ArrayList<NLBotPanelDataHolder> getDataList() { return dataHolderList; }
	
	// public method: aligns the index for this class (used in LoadEditor)
	public void alignIndex()
	{
		if (dataHolderList.size() != 0) dataIndex = dataHolderList.size() - 1;
		else dataIndex = 0;
	}
	
	// public method: takes in a String[][] and inputs the data to 'dataHolderList'
	public void setData(String[][] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			NLBotPanelDataHolder object = new NLBotPanelDataHolder();
			
			object.setDate(data[i][1]);
			object.setTime_from(data[i][2]);
			object.setTime_to(data[i][3]);
			object.setAppt(data[i][4]);
			object.setCompany(data[i][5]);
			object.setAddress1(data[i][6]);
			object.setAddress2(data[i][7]);
			object.setCity(data[i][8]);
			object.setState(data[i][9]);
			object.setZip(data[i][10]);
			object.setContact(data[i][11]);
			object.setTel(data[i][12]);
			object.setRef(data[i][13]);
			object.setNote(data[i][15]);
			
			dataHolderList.add(object);
		}
	}
	
	// public method: sets fields to last stored NLBotPanelDataHolder entry.
	public void setFields()
	{
		// make sure the list not empty
		if (dataHolderList.size() == 0) return;
		
		int index = dataHolderList.size() - 1;
		
		dateTF.setText(dataHolderList.get(index).getDate());
		timeFromTF.setText(dataHolderList.get(index).getTime_from());
		toTF.setText(dataHolderList.get(index).getTime_to());
		apptTF.setText(dataHolderList.get(index).getAppt());
		companyCB.setSelectedItem(dataHolderList.get(index).getCompany());
		addressTF1.setText(dataHolderList.get(index).getAddress1());
		addressTF2.setText(dataHolderList.get(index).getAddress2());
		cityTF.setText(dataHolderList.get(index).getCity());
		stateTF.setText(dataHolderList.get(index).getState());
		zipTF.setText(dataHolderList.get(index).getZip());
		contactTF.setText(dataHolderList.get(index).getContact());
		telTF.setText(dataHolderList.get(index).getTel());
		refTF.setText(dataHolderList.get(index).getRef());
		noteTF.setText(dataHolderList.get(index).getNote());
	}
	
	// main method: temporary run method
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				NLBotPanel panel = new NLBotPanel(1);	// pickup type panel
				JFrame frame = new JFrame("Temporary");
				frame.add(panel);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
