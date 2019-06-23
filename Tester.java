import templates.RateConfirmation;

public class Tester {
	public static void main(String[] args) {
		RateConfirmation test = new RateConfirmation();
		
		test.setLoadNumber("2222");
		test.setDate("today");
		test.setCarrier("ur mom");
		test.setDispatch("andrew kim");
		test.setPhone("30120820202");
		test.setFax("234234234222");
		test.createTemplate();
		
	}
}