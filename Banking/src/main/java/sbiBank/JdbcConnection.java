package sbiBank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnection {

	public static ResultSet connector() throws SQLException{
	Connection connection = null;
	Statement statement;
	ResultSet resultSet = null;

	String mysql = "jdbc:mysql://localhost:3306/akash";
	String User = "root";
	String Pass = "root";

	try
	{
		connection = DriverManager.getConnection(mysql, User, Pass);
		statement = connection.createStatement();
		resultSet = statement.executeQuery("Select * from details ");
	}catch(
	Exception e)
	{
		System.out.println(e);
	}
	return resultSet;}

}
