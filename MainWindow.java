package windows;

import windows.CaseMaker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MainWindow {
	
	// DECLARE COMPONENTS HERE
	JFrame mainFrame;
	JScrollPane leftMainPanelScrollPane;
	JSplitPane splitPane;
	JPanel homeAndFile, leftMainPanel, ordersPanel, startingPanel, billingReportsPanel, companyPanel, adminPanel, compliancePanel, expensePanel, homePagePanelR, fileCabinetPanelR, 
			companyPanelR, factoringMapPanelR, newPanelR, listPanelR, truckSchedulePanelR, archivePanelR, searchPanelR, sendSMSPanelR, invoicesPanelR, receivePaymentsPanelR, 
			payBillsPanelR, depositWithdrawPanelR, adjustmentPanelR, aRAgingReportPanelR, aPAgingReportPanelR, aPSearchPanelR, summaryPanelR, snapShotPanelR, aRExperiencePanelR, 
			payoutReportPanelR, uncollectablePayablePanelR, productionPanelR, billingLogPanelR, cashReportPanelR, billingItemLinePanelR, ownerOperatorPanelR, driverReportPanelR, 
			vehicleReportPanelR, inspectionPanelR, alertTaskPanelR, myFleetPanelR, writeCheckPanelR, listCheckPanelR, recordExpensePanelR;
	JLabel ordersLabel, billingReportsLabel, companyLabel, homeLabel, adminLabel, complianceLabel, expenseLabel, startingLabel;
	JButton newButton, listButton, truckScheduleButton, archiveButton, searchButton, sendSMSButton, companyButton, factoringMapButton, productionButton, billingLogButton, cashReportButton,
			billingItemLineButton, invoicesButton, receivePaymentsButton, payBillsButton, depositWithdrawButton, adjustmentButton, aRAgingReportButton, aPAgingReportButton, aPSearchButton, 
			summaryButton, snapShotButton, aRExperienceButton, payoutReportButton, uncollectablePayableButton, homePageButton, fileCabinetButton, ownerOperatorButton, driverReportButton,
			vehicleReportButton, inspectionButton, alertTaskButton, myFleetButton, writeCheckButton, listCheckButton, recordExpenseButton;
			
	// CONSTRUCTOR
	public MainWindow() {
		
		/**** LEFT SIDE OF THE SPLIT PANE ***********************************************/
		
		// HOME PAGE AND FILE CABINET PANEL
		homeLabel = new JLabel("Home");
		homeLabel.setFont(new Font("Default", Font.BOLD, 16));
		homeLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		homePageButton = new JButton("Home Page");
		fileCabinetButton = new JButton("File Cabinet");
		homeAndFile = new JPanel();
		homeAndFile.setBorder(BorderFactory.createLineBorder(Color.red));
		homeAndFile.setLayout(new BoxLayout(homeAndFile, BoxLayout.Y_AXIS));
		homeAndFile.add(homeLabel);
		homeAndFile.add(homePageButton);
		homeAndFile.add(fileCabinetButton);		
		
		// COMPANY PANEL
		companyLabel = new JLabel("Company");
		companyLabel.setFont(new Font("Default", Font.BOLD, 16));
		companyLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		companyButton = new JButton("Company");
		factoringMapButton = new JButton("Factoring Map");
		companyPanel = new JPanel();
		companyPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		companyPanel.setLayout(new BoxLayout(companyPanel, BoxLayout.Y_AXIS));
		companyPanel.add(companyLabel);
		companyPanel.add(companyButton);
		companyPanel.add(factoringMapButton);
		
		// ORDERS PANEL
		ordersLabel = new JLabel("Orders");
		ordersLabel.setFont(new Font("Default", Font.BOLD, 16));
		ordersLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		newButton = new JButton("New");
		listButton = new JButton("List");
		truckScheduleButton = new JButton("Truck Schedule");
		archiveButton = new JButton("Archive");
		searchButton = new JButton("Search");
		sendSMSButton = new JButton("Send SMS");
		ordersPanel = new JPanel();
		ordersPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
		ordersPanel.add(ordersLabel);
		ordersPanel.add(newButton);
		ordersPanel.add(listButton);
		ordersPanel.add(truckScheduleButton);
		ordersPanel.add(archiveButton);
		ordersPanel.add(searchButton);
		ordersPanel.add(sendSMSButton);
		
		// BILLING/REPORTS PANEL
		billingReportsLabel = new JLabel("Billing/Reports");
		billingReportsLabel.setFont(new Font("Default", Font.BOLD, 16));
		billingReportsLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		invoicesButton = new JButton("Invoices");
		receivePaymentsButton = new JButton("Receive Payments");
		payBillsButton = new JButton("Pay Bills");
		depositWithdrawButton = new JButton("Deposit & Withdraw");
		adjustmentButton = new JButton("Adjustment");
		aRAgingReportButton = new JButton("A/R Aging Report");
		aPAgingReportButton = new JButton("A/P Aging Report");
		aPSearchButton = new JButton("A/P Search");
		summaryButton = new JButton("Summary");
		snapShotButton = new JButton("Snap Shot");
		aRExperienceButton = new JButton("A/R Experience");
		payoutReportButton = new JButton("Payout Report");
		uncollectablePayableButton = new JButton("Uncollectable/Payable");
		billingReportsPanel = new JPanel();
		billingReportsPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		billingReportsPanel.setLayout(new BoxLayout(billingReportsPanel, BoxLayout.Y_AXIS));
		billingReportsPanel.add(billingReportsLabel);
		billingReportsPanel.add(invoicesButton);
		billingReportsPanel.add(receivePaymentsButton);
		billingReportsPanel.add(payBillsButton);
		billingReportsPanel.add(depositWithdrawButton);
		billingReportsPanel.add(adjustmentButton);
		billingReportsPanel.add(aRAgingReportButton);
		billingReportsPanel.add(aPAgingReportButton);
		billingReportsPanel.add(aPSearchButton);
		billingReportsPanel.add(summaryButton);
		billingReportsPanel.add(snapShotButton);
		billingReportsPanel.add(aRExperienceButton);
		billingReportsPanel.add(payoutReportButton);
		billingReportsPanel.add(uncollectablePayableButton);
		
		// ADMIN PANEL
		adminLabel = new JLabel("Admin");
		adminLabel.setFont(new Font("Default", Font.BOLD, 16));
		adminLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		productionButton = new JButton("Production");
		billingLogButton = new JButton("Billing Log");
		cashReportButton = new JButton("Cash Report");
		billingItemLineButton = new JButton("Billing Item Line");
		adminPanel = new JPanel();
		adminPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
		adminPanel.add(adminLabel);
		adminPanel.add(productionButton);
		adminPanel.add(billingLogButton);
		adminPanel.add(cashReportButton);
		adminPanel.add(billingItemLineButton);
		
		// COMPLIANCE PANEL
		complianceLabel = new JLabel("Compliance");
		complianceLabel.setFont(new Font("Default", Font.BOLD, 16));
		complianceLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		ownerOperatorButton = new JButton("Owner Operator");
		driverReportButton = new JButton("Driver Report");
		vehicleReportButton = new JButton("Vehicle Report");
		inspectionButton = new JButton("Inspection");
		alertTaskButton = new JButton("Alert & Task");
		myFleetButton = new JButton("My Fleet");
		compliancePanel = new JPanel();
		compliancePanel.setBorder(BorderFactory.createLineBorder(Color.red));
		compliancePanel.setLayout(new BoxLayout(compliancePanel, BoxLayout.Y_AXIS));
		compliancePanel.add(complianceLabel);
		compliancePanel.add(ownerOperatorButton);
		compliancePanel.add(driverReportButton);
		compliancePanel.add(vehicleReportButton);
		compliancePanel.add(inspectionButton);
		compliancePanel.add(alertTaskButton);
		compliancePanel.add(myFleetButton);
		
		
		// EXPENSE PANEL
		expenseLabel = new JLabel("Expense");
		expenseLabel.setFont(new Font("Default", Font.BOLD, 16));
		expenseLabel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		writeCheckButton = new JButton("Write Check");
		listCheckButton = new JButton("List Check");
		recordExpenseButton = new JButton("Record Expense");
		expensePanel = new JPanel();
		expensePanel.setBorder(BorderFactory.createLineBorder(Color.red));
		expensePanel.setLayout(new BoxLayout(expensePanel, BoxLayout.Y_AXIS));
		expensePanel.add(expenseLabel);
		expensePanel.add(writeCheckButton);
		expensePanel.add(listCheckButton);
		expensePanel.add(recordExpenseButton);
		
		
/*******************************************************************************************/		
		
		// COMMENTED OUT //
		/**MAIN LEFT PANEL (WITH JSCROLLPANE)
		leftMainPanel = new JPanel();
		leftMainPanel.setLayout(new BoxLayout(leftMainPanel, BoxLayout.Y_AXIS));
		leftMainPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
		leftMainPanel.add(homeAndFile);
		leftMainPanel.add(companyPanel);
		leftMainPanel.add(ordersPanel);
		leftMainPanel.add(billingReportsPanel);
		leftMainPanel.add(adminPanel);
		leftMainPanel.add(compliancePanel);
		leftMainPanel.add(expensePanel);
		leftMainPanelScrollPane = new JScrollPane(leftMainPanel);
		leftMainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(16); */
		
		
		// MAIN LEFT PANEL (WITH JSCROLLPANE & GRIDBAGLAYOUT)
		leftMainPanel = new JPanel();
		leftMainPanel.setLayout(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 1;
		cons.gridx = 0;
		leftMainPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
		leftMainPanel.add(homeAndFile, cons);
		leftMainPanel.add(companyPanel, cons);
		leftMainPanel.add(ordersPanel, cons);
		leftMainPanel.add(billingReportsPanel, cons);
		leftMainPanel.add(adminPanel, cons);
		leftMainPanel.add(compliancePanel, cons);
		leftMainPanel.add(expensePanel, cons);
		leftMainPanelScrollPane = new JScrollPane(leftMainPanel);
		leftMainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
/*****************************************************************************************/	
		
		
/*********************** ACTION LISTENERS FOR LEFT SIDE **********************/
		// HOME PAGE BUTTON
		homePageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(homePagePanelR);
			}
		});
		
		// FILE CABINET BUTTON
		fileCabinetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(fileCabinetPanelR);
			}
		});
		
		// COMPANY BUTTON
		companyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(companyPanelR);
			}
		});
		
		// FACTORING MAP BUTTON
		factoringMapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(factoringMapPanelR);
			}
		});
		
		// NEW BUTTON
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(newPanelR);
			}
		});
		
		// LIST BUTTON
		listButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(listPanelR);
			}
		});
		
		// TRUCK SCHEDULE BUTTON
		truckScheduleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(truckSchedulePanelR);
			}
		});
		
		// ARCHIVE BUTTON
		archiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(archivePanelR);
			}
		});
		
		// SEARCH BUTTON
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(searchPanelR);
			}
		});
		
		//SEND SMS BUTTON
		sendSMSButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(sendSMSPanelR);
			}
		});
		
		// INVOICES BUTTON
		invoicesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(invoicesPanelR);
			}
		});
		
		// RECEIVE PAYMENTs BUTTON
		receivePaymentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(receivePaymentsPanelR);
			}
		});
		
		// PAY BILLS BUTTON
		payBillsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(payBillsPanelR);
			}
		});
		
		// DEPOSIT WITHDRAW BUTTON
		depositWithdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(depositWithdrawPanelR);
			}
		});
		
		// ADJUSTMENT BUTTON
		adjustmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(adjustmentPanelR);
			}
		});
		
		// A/R AGING REPORT BUTTON
		aRAgingReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(aRAgingReportPanelR);
			}
		});
		
		// A/P AGING REPORT BUTTON
		aPAgingReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(aPAgingReportPanelR);
			}
		});
		
		// A/P SEARCH BUTTON
		aPSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(aPSearchPanelR);
			}
		});
		
		// SUMMARY BUTTON
		summaryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(summaryPanelR);
			}
		});
		
		// SNAP SHOT BUTTON
		snapShotButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(snapShotPanelR);
			}
		});
		
		// A/R EXPERIENCE BUTTON
		aRExperienceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(aRExperiencePanelR);
			}
		});
		
		// PAY OUT REPORT BUTTON
		payoutReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(payoutReportPanelR);
			}
		});
		
		// UNCOLLECTABLE PAYABLE BUTTON
		uncollectablePayableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {	
				splitPane.setRightComponent(uncollectablePayablePanelR);
			}
		});
		
		// PRODUCTION BUTTON
		productionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(productionPanelR);
			}
		});
		
		// BILLING LOG BUTTON
		billingLogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(billingLogPanelR);
			}
		});
		
		// CASH REPORT BUTTON
		cashReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(cashReportPanelR);
			}
		});
		
		//BILLING ITEM LINE BUTTON
		billingItemLineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(billingItemLinePanelR);
			}
		});
		
		// OWNER OPERATOR BUTTON
		ownerOperatorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(ownerOperatorPanelR);
			}
		});
		
		// DRIVER REPORT BUTTON
		driverReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(driverReportPanelR);
			}
		});
		
		// VEHICLE REPORT BUTTON
		vehicleReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(vehicleReportPanelR);
			}
		});
		
		// INSPECTION BUTTON
		inspectionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(inspectionPanelR);
			}
		});
		
		// ALERT TASK BUTTON
		alertTaskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(alertTaskPanelR);
			}
		});
		
		// MY FLEET BUTTON
		myFleetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(myFleetPanelR);
			}
		});
		
		// WRITE CHECK BUTTON
		writeCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(writeCheckPanelR);
			}
		});
		
		// LIST CHECK BUTTON
		listCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(listCheckPanelR);
			}
		});
		
		// RECORD EXPENSE BUTTON
		recordExpenseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				splitPane.setRightComponent(recordExpensePanelR);
			}
		});
		
		
		
		
		
		
		
		
		
		
		
/***************************** RIGHT SIDE PANELS **************************************/		
		
		// DEFAULT PANEL WHEN THE APPLICATION FIRST STARTS
		startingLabel = new JLabel("Starting Panel");
		startingPanel = new JPanel();
		startingPanel.setBorder(BorderFactory.createLineBorder(Color.red));
		startingPanel.add(startingLabel);
		
		// HOME PAGE PANEL
		JLabel temp1 = new JLabel("Home Page");
		homePagePanelR = new JPanel();
		homePagePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		homePagePanelR.add(temp1);
		
		
		// FILE CABINET PANEL
		
		fileCabinetPanelR = new JPanel();
		fileCabinetPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp2 = new JLabel("File Cabinet");
		fileCabinetPanelR.add(temp2);
		
		// COMPANY PANEL
		companyPanelR = new JPanel();
		companyPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp3 = new JLabel("Company");
		companyPanelR.add(temp3);
		
		// FACTORING MAP PANEL
		factoringMapPanelR = new JPanel();
		factoringMapPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp4 = new JLabel("Factoring Map");
		factoringMapPanelR.add(temp4);
		
		
		// NEW (ORDERS) PANEL
		newPanelR = new JPanel();
		newPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp5 = new JLabel("New");
		newPanelR.add(temp5);
		
		// LIST (ORDERS) PANEL
		listPanelR = new JPanel();
		listPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp6 = new JLabel("List");
		listPanelR.add(temp6);
		
		// TRUCK SCHEDULE PANEL
		truckSchedulePanelR = new JPanel();
		truckSchedulePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp7 = new JLabel("Truck Schedule");
		truckSchedulePanelR.add(temp7);
		
		// ARCHIVE PANEL
		archivePanelR = new JPanel();
		archivePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp8 = new JLabel("Archive");
		archivePanelR.add(temp8);
		
		// SEARCH PANEL
		searchPanelR = new JPanel();
		searchPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp9 = new JLabel("Search");
		searchPanelR.add(temp9);
		
		// SEND SMS PANEL
		sendSMSPanelR = new JPanel();
		sendSMSPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp10 = new JLabel("Send SMS");
		sendSMSPanelR.add(temp10);
		
		// INVOICES PANEL
		invoicesPanelR = new JPanel();
		invoicesPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp11 = new JLabel("Invoices");
		invoicesPanelR.add(temp11);
		
		// RECEIVE PAYMENTS PANEL
		receivePaymentsPanelR = new JPanel();
		receivePaymentsPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp12 = new JLabel("Receive Payments");
		receivePaymentsPanelR.add(temp12);
		
		// PAY BILLS PANEL
		payBillsPanelR = new JPanel();
		payBillsPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp13 = new JLabel("Pay Bills");
		payBillsPanelR.add(temp13);
		
		// DEPOSIT & WITHDRAW PANEL
		depositWithdrawPanelR = new JPanel();
		depositWithdrawPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp14 = new JLabel("Deposit & Withdraw");
		depositWithdrawPanelR.add(temp14);
		
		// ADJUSTMENT PANEL
		adjustmentPanelR = new JPanel();
		adjustmentPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp15 = new JLabel("Adjustment");
		adjustmentPanelR.add(temp15);
		
		// A/R AGING REPORT PANEL
		aRAgingReportPanelR = new JPanel();
		aRAgingReportPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp16 = new JLabel("A/R Aging Report");
		aRAgingReportPanelR.add(temp16);
		
		// A/P AGING REPORT PANEL
		aPAgingReportPanelR = new JPanel();
		aPAgingReportPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp17 = new JLabel("A/P Aging Report");
		aPAgingReportPanelR.add(temp17);
		
		
		// A/P SEARCH PANEL
		aPSearchPanelR = new JPanel();
		aPSearchPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp18 = new JLabel("A/P Search Panel");
		aPSearchPanelR.add(temp18);
		
		// SUMMARY PANEL
		summaryPanelR = new JPanel();
		summaryPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp19 = new JLabel("Summary");
		summaryPanelR.add(temp19);
		
		// SNAP SHOT PANEL
		snapShotPanelR = new JPanel();
		snapShotPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp20 = new JLabel("Snap Shot");
		snapShotPanelR.add(temp20);
		
		// A/R EXPERIENCE PANEL
		aRExperiencePanelR = new JPanel();
		aRExperiencePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp21 = new JLabel("A/R Experience");
		aRExperiencePanelR.add(temp21);
		
		// PAYOUT REPORT PANEL
		payoutReportPanelR = new JPanel();
		payoutReportPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp22 = new JLabel("Payout Report");
		payoutReportPanelR.add(temp22);
		
		// UNCOLLECTABLE/PAYABLE PANEL
		uncollectablePayablePanelR = new JPanel();
		uncollectablePayablePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp23 = new JLabel("Uncollectable/Payable");
		uncollectablePayablePanelR.add(temp23);
		
		// PRODUCTION PANEL
		productionPanelR = new JPanel();
		productionPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp24 = new JLabel("Production");
		productionPanelR.add(temp24);
		
		// BILLING LOG PANEL
		billingLogPanelR = new JPanel();
		billingLogPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp25 = new JLabel("Log Panel");
		billingLogPanelR.add(temp25);
		
		// CASH REPORT PANEL
		cashReportPanelR = new JPanel();
		cashReportPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp26 = new JLabel("Cash Report");
		cashReportPanelR.add(temp26);
		
		// BILLING ITEM LINE PANEL
		billingItemLinePanelR = new JPanel();
		billingItemLinePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp27 = new JLabel("Billing Item Line");
		billingItemLinePanelR.add(temp27);
		
		// OWNER OPERATOR PANEL
		ownerOperatorPanelR = new JPanel();
		ownerOperatorPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp28 = new JLabel("Owner Operator");
		ownerOperatorPanelR.add(temp28);
		
		// DRIVER REPORT PANEL
		driverReportPanelR = new JPanel();
		driverReportPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp29 = new JLabel("Driver Report");
		driverReportPanelR.add(temp29);
		
		// VEHICLE REPORT PANEL
		vehicleReportPanelR = new JPanel();
		vehicleReportPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp30 = new JLabel("Vehicle Report");
		vehicleReportPanelR.add(temp30);
		
		// INSPECTION PANEL
		inspectionPanelR = new JPanel();
		inspectionPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp31 = new JLabel("Inspection Panel");
		inspectionPanelR.add(temp31);
		
		// ALERT & TASK PANEL
		alertTaskPanelR = new JPanel();
		alertTaskPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp32 = new JLabel("Alert & Task");
		alertTaskPanelR.add(temp32);
		
		// MY FLEET PANEL
		myFleetPanelR = new JPanel();
		myFleetPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp33 = new JLabel("My Fleet");
		myFleetPanelR.add(temp33);
		
		// WRITE CHECK PANEL
		writeCheckPanelR = new JPanel();
		writeCheckPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp34 = new JLabel("Write Check");
		writeCheckPanelR.add(temp34);
		
		// LIST CHECK PANEL
		listCheckPanelR = new JPanel();
		listCheckPanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp35 = new JLabel("List Check");
		listCheckPanelR.add(temp35);
		
		// RECORD EXPENSE PANEL
		recordExpensePanelR = new JPanel();
		recordExpensePanelR.setBorder(BorderFactory.createLineBorder(Color.red));
		JLabel temp36 = new JLabel("Record Expense");
		recordExpensePanelR.add(temp36);
		
		
		
		
		
		
		
		
/*****************************************************************************************/
		
		// SPLIT PANE
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerLocation(185);
		splitPane.setLeftComponent(leftMainPanelScrollPane);
		splitPane.setRightComponent(startingPanel);
		
		// MAIN FRAME
		mainFrame = new JFrame("Dynasty Database");
		mainFrame.add(splitPane);
		mainFrame.pack();
		mainFrame.setSize(700, 800);
		mainFrame.setVisible(true);
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
/***************************** ACTION LISTENERS ********************************/		
		
		//action listener for "newButton"
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//insert actions to take (do this for now \(lol\))
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new CaseMaker();
					}
				});
			}
		});
		
		listButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//insert actions here
			}
		});
		
		
		
	}
	
	
	
	
/************************** MAIN METHOD ********************************/	
	
	
	// EVENT-DISPATCHING THREAD
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindow();
			}
		});
	}
}