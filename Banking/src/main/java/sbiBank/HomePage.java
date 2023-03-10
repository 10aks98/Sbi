package sbiBank;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePage {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub


		Controller controller = new Controller();
		//controller.createUser();
		int accNum = controller.loginUser();
	   // controller.checkBalance(accNum);
		//controller.depositAmount(accNum);
		controller.withDrawlAmount(accNum);
	}
}
