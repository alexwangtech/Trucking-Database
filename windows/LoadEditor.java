// window for editing a load: subclass of LoadMaker()

package windows;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class LoadEditor extends LoadMaker
{
	int loadID;
	
	public LoadEditor(int loadID)
	{
		this.loadID = loadID;
		
		// set the title of the frame to 'Edit Company'
		super.setTitle("Edit Company");
		
		// to prevent the super class from running the full saveAndStore() method, change 'isSavedBefore' to true
		super.isSavedBefore = true;
		
		// load in the information from the SQL server using the loadInfo method
		loadInfo();
		
		// set up the display using the loadStorageDevicesData() method
		loadStorageDevicesData();
		
		// align the tracking indexes using the alignIndexes() method
		alignIndexes();
	}
	
	// method: loads in the information from the SQL server and stores it in the class's storage objects
	private void loadInfo()
	{
		try
		{
			// set up an SQL connection and other necessary components.
			Connection connection = DriverManager.getConnection(sqlUrl, sqlUsername, sqlPassword);
			Statement statement = connection.createStatement();
			
			// get the general information and store it to 'generalInfoList'.
			ResultSet resultset = statement.executeQuery("SELECT * FROM LOADS WHERE LOADID = '" + loadID + "';");
			resultset.next();
			super.generalInfoList = new ArrayList<String>();			
			for (int i = 2; i <= 38; i++)
				super.generalInfoList.add(resultset.getString(i));
			
			// get the information for the left table and set it to the data of 'leftTable'.
			resultset = statement.executeQuery("SELECT * FROM LOADTABLELEFT" + loadID + ";");
			ArrayList<String> templist = new ArrayList<String>();
			while (resultset.next())
				for (int i = 1; i <= 5; i++)
					templist.add(resultset.getString(i));
			String[][] tempArray = new String[templist.size() / 5][5];
			int counter = 0;
			for (int i = 0; i < tempArray.length; i++)
				for (int j = 0; j < tempArray[i].length; j++)
					tempArray[i][j] = templist.get(counter++);			
			super.leftTable.setupTable(tempArray);
			
			// get the information for the right table and set it to the data of 'rightTable'.
			resultset = statement.executeQuery("SELECT * FROM LOADTABLERIGHT" + loadID + ";");
			templist = new ArrayList<String>();
			while (resultset.next())
				for (int i = 1; i <= 5; i++)
					templist.add(resultset.getString(i));
			tempArray = new String[templist.size() / 5][5];
			counter = 0;
			for (int i = 0; i < tempArray.length; i++)
				for (int j = 0; j < tempArray[i].length; j++)
					tempArray[i][j] = templist.get(counter++);
			super.rightTable.setupTable(tempArray);
						
			// get the information for the lower left panel and set it to the data of 'leftBot'.
			resultset = statement.executeQuery("SELECT * FROM NLBOTPANELLEFT" + loadID + ";");
			templist = new ArrayList<String>();
			while (resultset.next())
				for (int i = 1; i <= 15; i++)
					templist.add(resultset.getString(i));
			tempArray = new String[templist.size() / 15][15];
			counter = 0;
			for (int i = 0; i < tempArray.length; i++)
				for (int j = 0; j < tempArray.length; j++)
					tempArray[i][j] = templist.get(counter++);
			super.leftBot.setData(tempArray);
			
			// get the information for the lower right panel and set it to the data of 'rightBot'.
			resultset = statement.executeQuery("SELECT * FROM NLBOTPANELRIGHT" + loadID + ";");
			templist = new ArrayList<String>();
			while (resultset.next())
				for (int i = 1; i <= 15; i++)
					templist.add(resultset.getString(i));
			tempArray = new String[templist.size() / 15][15];
			counter = 0;
			for (int i = 0; i < tempArray.length; i++)
				for (int j = 0; j < tempArray.length; j++)
					tempArray[i][j] = templist.get(counter++);
			super.rightBot.setData(tempArray);
			
			resultset.close();
			statement.close();
			connection.close();
		}
		catch (SQLException e) { e.printStackTrace(); }
	}
	
	// !!! the table objects already have a set in method that displays the data, so the only work left is setting the values display for the NLBotPanel objects.
	// method: loads the information from the storage objects to the GUI components
	private void loadStorageDevicesData()
	{
		// set the information of 'generalInfoList' to the corresponding components of the window
		super.entrdLbl2.setText(super.generalInfoList.get(0));
		super.eqpCmboBx.setSelectedItem(super.generalInfoList.get(1));
		super.lngthTF.setText(super.generalInfoList.get(2));
		super.airRideCB.setState(super.generalInfoList.get(3).equals("true")? true : false);
		super.logisticCB.setState(super.generalInfoList.get(4).equals("true")? true : false);
		super.teamCB.setState(super.generalInfoList.get(5).equals("true")? true : false);
		super.instructionTF.setText(super.generalInfoList.get(6));
		
		super.cmmdtyTF.setText(super.generalInfoList.get(7));
		super.qnttyTF.setText(super.generalInfoList.get(8));
		super.unitCB.setSelectedItem(super.generalInfoList.get(9));
		super.wghtTF.setText(super.generalInfoList.get(10));
		
		super.billToCB.setSelectedItem(super.generalInfoList.get(11));
		super.addrssLblLft2.setText(super.generalInfoList.get(12));
		super.cntctTFLft.setText(super.generalInfoList.get(13));
		super.emlTFLft.setText(super.generalInfoList.get(14));
		super.telTFLft.setText(super.generalInfoList.get(15));
		super.faxTFLft.setText(super.generalInfoList.get(16));
		super.cellTFLft.setText(super.generalInfoList.get(17));
		super.refTFLft.setText(super.generalInfoList.get(18));
		super.dscntTFLft.setText(super.generalInfoList.get(19));
		super.trmTFLft.setText(super.generalInfoList.get(20));
		super.noteTFLft.setText(super.generalInfoList.get(21));
		
		super.pyToCBRght.setSelectedItem(super.generalInfoList.get(22));
		super.mcTFRght.setText(super.generalInfoList.get(23));
		super.cntctTFRght.setText(super.generalInfoList.get(24));
		super.emlTFRght.setText(super.generalInfoList.get(25));
		super.telTFRght.setText(super.generalInfoList.get(26));
		super.faxTFRght.setText(super.generalInfoList.get(27));
		super.cntctCellTFRght.setText(super.generalInfoList.get(28));
		super.drvrTFRght.setText(super.generalInfoList.get(29));
		super.drvrCellTFRght.setText(super.generalInfoList.get(30));
		super.withholdPayment.setState(super.generalInfoList.get(31).equals("true")? true : false);
		super.trkTFRght.setText(super.generalInfoList.get(32));
		super.trlTFRght.setText(super.generalInfoList.get(33));
		super.dscntTFRght.setText(super.generalInfoList.get(34));
		super.trmTFRght.setText(super.generalInfoList.get(35));
		super.noteTFRght.setText(super.generalInfoList.get(36));
		
		// set fields of NLBotPanel(s) to their last stored entry, using method from NLBotPanel class.
		super.leftBot.setFields();
		super.rightBot.setFields();
	}
	
	// method: aligns the indexes of tracking variables, so that the increment buttons function properly.
	private void alignIndexes()
	{
		super.leftBot.alignIndex();
		super.rightBot.alignIndex();
	}
	
	// main method: temporary run method
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new LoadEditor(0);
			}
		});
	}
}
