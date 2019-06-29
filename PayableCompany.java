/**
 * object to be used as temporary storage during the creation of a new company
 * when switching between options payable and receivable, to avoid data loss,
 * an instance of this class will be called by CompanyMaker and used to temporarily
 * store the information, before it is relayed to an SQL server
 */

package contacts;

public class PayableCompany {
	String creditLine, pmtTerm, factor, addressLine1, addressLine2, city, state, zip;
	
	// constructor: creates an instance of this class
	public PayableCompany() {
		
	}
	
	// method: stores a credit line
	public void storeCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}
	
	// method: stores a pmt term
	public void storePmtTerm(String pmtTerm) {
		this.pmtTerm = pmtTerm;
	}
	
	// method: stores a factor
	public void storeFactor(String factor) {
		this.factor = factor;
	}
	
	// method: stores an address line 1
	public void storeAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	// method: stores an address line 2
	public void storeAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	// method: stores a city
	public void storeCity(String city) {
		this.city = city;
	}
	
	// method: stores a state
	public void storeState(String state) {
		this.state = state;
	}
	
	// method: stores a zipcode
	public void storeZip(String zip) {
		this.zip = zip;
	}
	
	// method: returns a stored credit line
	public String getCreditLine() {
		return this.creditLine;
	}

	// method: returns a stored pmt term
	public String getPmtTerm() {
		return this.pmtTerm;
	}

	// method: returns a stored factor
	public String getFactor() {
		return this.factor;
	}

	// method: returns a stored address line 1
	public String getAddressLine1() {
		return this.addressLine1;
	}
	
	// method: returns a stored address line 2
	public String getAddressLine2() {
		return this.addressLine2;
	}

	// method: returns a stored city
	public String getCity() {
		return this.city;
	}

	// method: returns a stored state
	public String getState() {
		return this.state;
	}

	// method: returns a stored zipcode
	public String getZip() {
		return this.zip;
	}
	
	// method: sets everything to ""
	public void everythingEmpty() {
		this.creditLine = "";
		this.pmtTerm = "";
		this.factor = "";
		this.addressLine1 = "";
		this.addressLine2 = "";
		this.city = "";
		this.state = "";
		this.zip = "";
	}
}