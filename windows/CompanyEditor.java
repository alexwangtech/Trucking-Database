/**
 * this is the window that opens up when the user clicks on the "edit company" button in the company panel
 * this class extends on the CompanyMaker class.
 */

package windows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import storageDevices.CompanyContact;
import storageDevices.InsuranceContact;
import storageDevices.InsurerCompany;
import storageDevices.PayableContact;
import storageDevices.ReceivableContact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

public class CompanyEditor extends CompanyMaker
{	
	// Constructor: accepts an integer input, the ID of the company
	public CompanyEditor(int company_ID)
	{
		// change the name of the window to "Edit Company"
		super.mainFrame.setTitle("Edit Company");
		
		// change the text of saveBtn to "Save Changes"
		super.saveBtn.setText("Save Changes");
		
		// to prevent the super class from running the full saveAndStore() method again, set 'isSavedBefore' to 'true'
		super.isSavedBefore = true;
		
		// call the loadInfo() method
		loadInfo(company_ID);
		
		// call the loadStorageDevicesData() method
		loadStorageDevicesData();
		
		// call the alignIndexes() method
		alignIndexes();
	}
	
	// method: loads in the information from the SQL server and stores it temporary in the members of the class (this)
	// accepts an integer input, the company ID
	private void loadInfo(int company_ID)
	{
		// connect to the SQl server, retrieve the information, and store the information in the storage devices (package storageDevices)
		try
		{
			// get the info needed for SQL connection, located in the 'vitals' folder
			File sql_info_file = new File("./vitals/sql_info.txt");
			FileReader file_in = new FileReader(sql_info_file);
			BufferedReader file_reader = new BufferedReader(file_in);
			
			String sql_link = file_reader.readLine();
			String sql_username = file_reader.readLine();
			String sql_password = file_reader.readLine();
						
			file_reader.close();
			file_in.close();
			
			// make a connection to the SQL server and create a Statement object
			Connection connection = DriverManager.getConnection(sql_link, sql_username, sql_password);
			Statement statement = connection.createStatement();
			
			// retrieve the general information and store it in the 'companyGeneralInfo' ArrayList.
			// store the company ID (first column) in a separate variable. this variable is from the super class, CompanyMaker.
			ResultSet result_set = statement.executeQuery("SELECT * FROM COMPANIES WHERE COMPANYID = " + company_ID + ";");
			super.companyGeneralInfo = new ArrayList<String>();
			result_set.next();
			super.companyID = Integer.parseInt(result_set.getString(1));
			for (int i = 2; i <= 29; i++)
				super.companyGeneralInfo.add(result_set.getString(i));
			
			// get the information for the companyContactList (super class CompanyMaker) from the SQL server.
			// store the information into 'companyContactList' (super class CompanyMaker).
			result_set = statement.executeQuery("SELECT * FROM COMPANYCONTACTS" + super.companyID + ";");
			int counter = 0;
			while (result_set.next())
			{
				super.companyContactList.add(new CompanyContact());
				super.companyContactList.get(counter).storeName(result_set.getString(1));
				super.companyContactList.get(counter).storeCell(result_set.getString(2));
				super.companyContactList.get(counter).storePhone(result_set.getString(3));
				super.companyContactList.get(counter).storeFax(result_set.getString(4));
				super.companyContactList.get(counter).storeEmail(result_set.getString(5));
				
				++counter;
			}
			
			// get the information for the payable contact list (super class CompanyMaker) from the SQL server.
			// store the information in 'payableContactList' (super class CompanyMaker)
			result_set = statement.executeQuery("SELECT * FROM PAYABLECONTACTS" + super.companyID + ";");
			counter = 0;
			while (result_set.next())
			{
				super.payableContactList.add(new PayableContact());
				super.payableContactList.get(counter).storeName(result_set.getString(1));
				super.payableContactList.get(counter).storeCell(result_set.getString(2));
				super.payableContactList.get(counter).storePhone(result_set.getString(3));
				super.payableContactList.get(counter).storeFax(result_set.getString(4));
				super.payableContactList.get(counter).storeEmail(result_set.getString(5));
				
				++counter;
			}
			
			// get the information for the receivable contact list (super class CompanyMaker) from the SQL server
			// store the information in 'receivableContactList' (super class CompanyMaker)
			result_set = statement.executeQuery("SELECT * FROM RECEIVABLECONTACTS" + super.companyID + ";");
			counter = 0;
			while (result_set.next())
			{
				super.receivableContactList.add(new ReceivableContact());
				super.receivableContactList.get(counter).storeName(result_set.getString(1));
				super.receivableContactList.get(counter).storeCell(result_set.getString(2));
				super.receivableContactList.get(counter).storePhone(result_set.getString(3));
				super.receivableContactList.get(counter).storeFax(result_set.getString(4));
				super.receivableContactList.get(counter).storeEmail(result_set.getString(5));
				
				++counter;
			}
			
			// get the information for the insurance contact list (super class CompanyMaker) from the SQL server
			// store the information in 'insuranceContactList' (super class CompanyMaker)
			result_set = statement.executeQuery("SELECT * FROM INSURANCECONTACTS" + super.companyID + ";");
			counter = 0;
			while (result_set.next())
			{
				super.insuranceContactList.add(new InsuranceContact());
				super.insuranceContactList.get(counter).storeName(result_set.getString(1));
				super.insuranceContactList.get(counter).storeCell(result_set.getString(2));
				super.insuranceContactList.get(counter).storePhone(result_set.getString(3));
				super.insuranceContactList.get(counter).storeFax(result_set.getString(4));
				super.insuranceContactList.get(counter).storeEmail(result_set.getString(5));
		
				++counter;
			}
			
			// get the information for the insurer agencies/companies list (super class CompanyMaker) from the SQL server
			// store the information in 'insurerList' (super class CompanyMaker)
			result_set = statement.executeQuery("SELECT * FROM INSURERCOMPANIES" + super.companyID + ";");
			counter = 0;
			while (result_set.next())
			{
				super.insurerList.add(new InsurerCompany());
				super.insurerList.get(counter).setInsurer(result_set.getString(1));
				super.insurerList.get(counter).setGeneral_policy(result_set.getString(2));
				super.insurerList.get(counter).setGeneral_coverage(result_set.getString(3));
				super.insurerList.get(counter).setGeneral_expiration(result_set.getString(4));
				super.insurerList.get(counter).setAuto_policy(result_set.getString(5));
				super.insurerList.get(counter).setAuto_coverage(result_set.getString(6));
				super.insurerList.get(counter).setAuto_expiration(result_set.getString(7));
				super.insurerList.get(counter).setCargo_policy(result_set.getString(8));
				super.insurerList.get(counter).setCargo_coverage(result_set.getString(9));
				super.insurerList.get(counter).setCargo_expiration(result_set.getString(10));
				super.insurerList.get(counter).setTargetGoodsDeductibles(result_set.getString(11));
				
				++counter;
			}
			
			// get the information for the payable panel (super class CompanyMaker) from the SQL server
			// store the information in 'payableContact' (super class CompanyMaker)
			result_set = statement.executeQuery("SELECT * FROM COMPANYPAYABLEPANEL" + super.companyID + ";");
			if (result_set.next())
			{
				super.payableCompany.storeCreditLine(result_set.getString(1));
				super.payableCompany.storePmtTerm(result_set.getString(2));
				super.payableCompany.storeFactor(result_set.getString(3));
				super.payableCompany.storeAddressLine1(result_set.getString(4));
				super.payableCompany.storeAddressLine2(result_set.getString(5));
				super.payableCompany.storeCity(result_set.getString(6));
				super.payableCompany.storeState(result_set.getString(7));
				super.payableCompany.storeZip(result_set.getString(8));
			}
			else
				super.payableCompany.everythingEmpty();
			
			// get the information for the receivable panel (super class CompanyMaker) from the SQL server
			// store the information in 'receivablePanel' (super class CompanyMaker)
			result_set = statement.executeQuery("SELECT * FROM COMPANYRECEIVABLEPANEL" + super.companyID + ";");
			if (result_set.next())
			{
				super.receivableCompany.storeCreditLine(result_set.getString(1));
				super.receivableCompany.storePmtTerm(result_set.getString(2));
				super.receivableCompany.storeFactor(result_set.getString(3));
				super.receivableCompany.storeAddressLine1(result_set.getString(4));
				super.receivableCompany.storeAddressLine2(result_set.getString(5));
				super.receivableCompany.storeCity(result_set.getString(6));
				super.receivableCompany.storeState(result_set.getString(7));
				super.receivableCompany.storeZip(result_set.getString(8));
			}
			else
				super.receivableCompany.everythingEmpty();
		}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "SQL Vital File Not Found. Please contact developer for help.");
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
	
	// method: makes the info from all of the storage devices visible and editable to the user
	private void loadStorageDevicesData()
	{
		int index;
		
		// set the data from 'companyGeneralInfo' (super class member) to the text fields of the corresponding components
		super.x0y0Name.setText(super.companyGeneralInfo.get(0));			// name text field
		super.x0y0DBA.setText(super.companyGeneralInfo.get(1));				// DBA text field
		super.x0y0Tel.setText(super.companyGeneralInfo.get(2));				// Tel text field
		super.x0y0TaxID.setText(super.companyGeneralInfo.get(3));			// tax ID text field
		super.x0y0Fax.setText(super.companyGeneralInfo.get(4));				// fax text field
		super.x0y0MC.setText(super.companyGeneralInfo.get(5));				// mc# text field
		super.x0y0Established.setText(super.companyGeneralInfo.get(6));		// established text field
		super.trcdottf.setText(super.companyGeneralInfo.get(7));			// dot# text field
		super.trcscactf.setText(super.companyGeneralInfo.get(8));			// scac text field
		super.x0y1ad1tf.setText(super.companyGeneralInfo.get(9));			// address line 1 text field
		super.x0y1ad2tf.setText(super.companyGeneralInfo.get(10));			// address line 2 text field
		super.x0y1ctf.setText(super.companyGeneralInfo.get(11));			// city text field
		super.x0y1stf.setText(super.companyGeneralInfo.get(12));			// state text field
		super.x0y1ztf.setText(super.companyGeneralInfo.get(13));			// zip text field
		super.trcrcb.setSelectedItem(super.companyGeneralInfo.get(14));		// rating combo box
		super.trccccb.setSelectedItem(super.companyGeneralInfo.get(15));	// contract/common combo box
		super.trciacb.setSelectedItem(super.companyGeneralInfo.get(16));	// ins.agency combo box
		
		super.carrierType.setState(super.companyGeneralInfo.get(17).equals("true")? true : false);			// carrier check box
		super.brokerType.setState(super.companyGeneralInfo.get(18).equals("true")? true : false);			// broker check box
		super.customerType.setState(super.companyGeneralInfo.get(19).equals("true")? true : false);			// customer check box
		super.shipperType.setState(super.companyGeneralInfo.get(20).equals("true")? true : false);			// shipper check box
		super.consigneeType.setState(super.companyGeneralInfo.get(21).equals("true")? true : false);		// consignee check box
		super.insAgencyType.setState(super.companyGeneralInfo.get(22).equals("true")? true : false);		// ins.agency check box
		super.factoringType.setState(super.companyGeneralInfo.get(23).equals("true")? true : false);		// factoring check box
		super.preferredType.setState(super.companyGeneralInfo.get(24).equals("true")? true : false);		// preferred check box
		super.vendorType.setState(super.companyGeneralInfo.get(25).equals("true")? true : false);			// vendor check box
		super.ownerOperatorType.setState(super.companyGeneralInfo.get(26).equals("true")? true : false);	// owner/operator check box
		super.otherType.setState(super.companyGeneralInfo.get(27).equals("true")? true : false);			// other check box
		
		// set the data from the last member of 'companyContactList' (super class member) to the text fields of the corresponding components
		if (super.companyContactList.size() != 0)
		{
			index = super.companyContactList.size() - 1;
			super.x0y2ntf.setText(super.companyContactList.get(index).getName());
			super.x0y2ctf.setText(super.companyContactList.get(index).getCell());
			super.x0y2ptf.setText(super.companyContactList.get(index).getPhone());
			super.x0y2ftf.setText(super.companyContactList.get(index).getFax());
			super.x0y2etf.setText(super.companyContactList.get(index).getEmail());
		}
		
		// ===========================================================================================================================
		// when first initialized, the value of 'midPanelMode' (super class member) is 'false'.
		// as a result, for the middle panel, display the data for payable panel and last member of 'payableContactList' (super class)
		// ===========================================================================================================================
		
		// set the data from 'payableCompany' (super class member) to the text fields of the corresponding components
		super.x1y1cltf.setText(super.payableCompany.getCreditLine());
		super.x1y1ptcb.setSelectedItem(super.payableCompany.getPmtTerm());
		super.x1y1fcb.setSelectedItem(super.payableCompany.getFactor());
		super.x1y2a1tf.setText(super.payableCompany.getAddressLine1());
		super.x1y2a2tf.setText(super.payableCompany.getAddressLine2());
		super.x1y2ctf.setText(super.payableCompany.getCity());
		super.x1y2stf.setText(super.payableCompany.getState());
		super.x1y2ztf.setText(super.payableCompany.getZip());
		
		// set the data from the last member of 'payableContactList' (super class member) to the text fields of the corresponding components
		if (super.payableContactList.size() != 0)
		{
			index = super.payableContactList.size() - 1;
			super.x1y3ntf.setText(super.payableContactList.get(index).getName());
			super.x1y3ctf.setText(super.payableContactList.get(index).getCell());
			super.x1y3ptf.setText(super.payableContactList.get(index).getPhone());
			super.x1y3ftf.setText(super.payableContactList.get(index).getFax());
			super.x1y3etf.setText(super.payableContactList.get(index).getEmail());
		}
		
		// set the data from the last member of 'insurerList' (super class member) to the text fields of the corresponding components
		if (super.insurerList.size() != 0)
		{
			index = super.insurerList.size() - 1;
			super.insurerTextField.setText(super.insurerList.get(index).getInsurer());
			super.trcgptf.setText(super.insurerList.get(index).getGeneral_policy());
			super.trcgctf.setText(super.insurerList.get(index).getGeneral_coverage());
			super.trcgetf.setText(super.insurerList.get(index).getGeneral_expiration());
			super.trcaptf.setText(super.insurerList.get(index).getAuto_policy());
			super.trcactf.setText(super.insurerList.get(index).getAuto_coverage());
			super.trcaetf.setText(super.insurerList.get(index).getAuto_expiration());
			super.trccptf.setText(super.insurerList.get(index).getCargo_policy());
			super.trccctf.setText(super.insurerList.get(index).getCargo_coverage());
			super.trccetf.setText(super.insurerList.get(index).getCargo_expiration());
			super.trctgdtf.setText(super.insurerList.get(index).getTargetGoodsDeductibles());
		}
		
		// set the data from the last member of 'insuranceContactList' (super class member) to the text fields of the corresponding components
		if (super.insuranceContactList.size() != 0)
		{
			index = super.insuranceContactList.size() - 1;
			super.brcntf.setText(super.insuranceContactList.get(index).getName());
			super.brcctf.setText(super.insuranceContactList.get(index).getCell());
			super.brcptf.setText(super.insuranceContactList.get(index).getPhone());
			super.brcftf.setText(super.insuranceContactList.get(index).getFax());
			super.brcetf.setText(super.insuranceContactList.get(index).getEmail());
		}
	}
	
	// method: correctly sets all the ArrayList counter index (super class components) to ensure that the '<' and '>' buttons work correctly for all sections.
	// NOTE: unlike in CompanyMaker, the indexes must now be set as list.size() - 1. this is because the GUI will load in the last object, which is at last index of the list.
	// however, if the corresponding is set to size() (the super class way), this will result in an unintentional call to the save() method (super class method), because of the way
	// the indexes are designed to work with the buttons. but if the list empty, set the index to 0 (equal to list.size()).
	private void alignIndexes()
	{	
		// company contacts index
		if (super.companyContactList.size() != 0)
			super.companyIndex = super.companyContactList.size() - 1;
		else
			super.companyIndex = 0;
		
		// payable contacts index
		if (super.payableContactList.size() != 0)
			super.payableIndex = super.payableContactList.size() - 1;
		else
			super.payableIndex = 0;
		
		// receivable contacts index
		if (super.receivableContactList.size() != 0)
			super.receivableIndex = super.receivableContactList.size() - 1;
		else
			super.receivableIndex = 0;
		
		// insurer agencies index
		if (super.insurerList.size() != 0)
			super.insurerIndex = super.insurerList.size() - 1;
		else
			super.insurerIndex = 0;
		
		// insurance contacts index
		if (super.insuranceContactList.size() != 0)
			super.insuranceIndex = super.insuranceContactList.size() - 1;
		else
			super.insuranceIndex = 0;
	}
}
