package sbiBank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.util.StringUtils;

public class Controller {

	Scanner input = new Scanner(System.in);

	void createUser() throws SQLException {
		System.out.println("Please Enter the Name");
		String name = input.next();
		System.out.println("Please Enter the Password");
		String password = input.next();
		System.out.println("Please Enter the UserName");
		String userName = input.next();
		ResultSet result = JdbcConnection.connector();
		boolean create = true;
		while (result.next()) {
			System.out.println(result.getString(2) + result.getString(3));
			if (userName.equals(result.getString(2))) {
				create = false;
				System.out.println("Please again Re Enter the details UserName already Taken");
				createUser();
			}
		}
		if (create) {
			JdbcConnection.connector().getStatement()
					.execute("INSERT INTO `akash`.`details` (`username`, `password`, `name`, `balance`) VALUES ('"
							+ userName + "', '" + password + "', '" + name + "', '0')");

			System.out.println("Account Created Successfully");
			result.close();
		}
	}

	int loginUser() throws SQLException {
		System.out.println("Please Enter the UserName for Login");
		String userName = input.next();
		ResultSet resultSet = null;
		int accountNum=0;
		ResultSet result = JdbcConnection.connector().getStatement()
				.executeQuery("select * from details where `username`= '" + userName + "'");
		if (result.next()) {
			// if (userName.equals(result.getString(2))) {
			System.out.println("Please Enter the Password");
			String password = input.next();
			resultSet = JdbcConnection.connector().getStatement().executeQuery(
					"select * from details where `password`='" + password + "' and `username`= '" + userName + "'");
			if (resultSet.next()) {
				System.out.println("Login Successfull");
				System.out.println(resultSet.getInt(1) + resultSet.getString(2) + resultSet.getString(3)
						+ resultSet.getString(4) + resultSet.getInt(5));
				accountNum=resultSet.getInt(1);
			} else {
				System.out.println("Wrong Password");
				loginUser();
			}
		} else {
			System.out.println("Wrong UserName ");
			loginUser();
		}
		return accountNum;

	}

	void checkBalance(int accNo) throws SQLException {
		String sql = "Select * from details where account_no =" + accNo;
		ResultSet rs = JdbcConnection.connector().getStatement().executeQuery(sql);
		if (rs.next()) {
			System.out.println("Account Details and Balance");
			System.out.println(rs.getInt(1) +"	"+ rs.getString(2) +"	"+ rs.getString(3) +"	"+ rs.getString(4) +"	"+ rs.getInt(5));
		}
	}
	
	void depositAmount(int accNo) throws SQLException{
		System.out.println("Enter the Amount to Deposit");
		int deposit=input.nextInt();
		String sql = "Select * from details where account_no =" + accNo;
		ResultSet rs = JdbcConnection.connector().getStatement().executeQuery(sql);
		if (rs.next()) {
			int balance= rs.getInt(5);
			deposit=deposit+balance;
		}
	//	String sql = ("UPDATE details SET balance = `"+deposit +"`WHERE account_no =`" + accNo+"`");
		JdbcConnection.connector().getStatement().executeUpdate("update details set `balance` = "+deposit +" where (`account_no` =" + accNo+")");
		
	}
	
	void withDrawlAmount(int accNo) throws SQLException{
		System.out.println("Enter the Amount to Withdrawl");
		int withdraw=input.nextInt();
		int balance =0;
		int current =0;
		String sql = "Select * from details where account_no =" + accNo;
		ResultSet rs = JdbcConnection.connector().getStatement().executeQuery(sql);
		if (rs.next()) {
			 balance= rs.getInt(5);
			current=balance-withdraw;
		}
		if(withdraw<=balance) {
		JdbcConnection.connector().getStatement().executeUpdate("update details set `balance` = "+current +" where (`account_no` =" + accNo+")");
		}
		else {
			System.out.println("You dont have Sufficient balance");
			
			checkBalance(accNo);
			System.out.print("Now");
			withDrawlAmount(accNo);
			
		}
	}

}





















