package rit756;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBMethods implements JDBMethodsInterface {
	private Connection connection = null;

	public void setupDb(String ip, String port, String uname, String pwd,
			String Db) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String constring = "jdbc:mysql://" + ip + ":" + port + "/" + Db;

			connection = DriverManager.getConnection(constring, uname, pwd);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean selectOnId(Contact contact) {
		
		Boolean result=false;
		try {
			
			PreparedStatement ps = connection
					.prepareStatement("SELECT * FROM person WHERE Id=?");

			ps.setInt(1, contact.getId());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				contact.setId(rs.getInt("Id"));
				contact.setCardNumber(rs.getString("CardNumber"));
				contact.setCity(rs.getString("City"));
				contact.setFirstName(rs.getString("FirstName"));
				contact.setLastName(rs.getString("LastName"));	
				contact.setEmail(rs.getString("Email"));
				contact.setMiddleInitial(rs.getString("MiddleInitial"));
				contact.setState(rs.getString("State"));
				contact.setZip(rs.getString("Zip"));
				contact.setPhone(rs.getString("Phone"));
				contact.setCardtype(rs.getString("CreditCardType"));
				contact.setStreet(rs.getString("Street"));
				result=true;
				
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();

		}

		return result;
	}

	public JDBMethods(String Ip, String Port, String uname, String pwd,
			String Db) {

		setupDb(Ip, Port, uname, pwd, Db);

	}

	@Override
	public boolean selectOnLastName(Contact contact) {
		Boolean result=false;

			try {
				
				PreparedStatement ps = connection
						.prepareStatement("SELECT * FROM person WHERE LastName=?");

				ps.setString(1, contact.getLastName());
				ResultSet rs = ps.executeQuery();
				

				while (rs.next()) {
					result=true;
					
					contact.setId(rs.getInt("Id"));
					contact.setCardNumber(rs.getString("CardNumber"));
					contact.setCity(rs.getString("City"));
					contact.setFirstName(rs.getString("FirstName"));
					contact.setLastName(rs.getString("LastName"));	
					contact.setEmail(rs.getString("Email"));
					contact.setMiddleInitial(rs.getString("MiddleInitial"));
					contact.setState(rs.getString("State"));
					contact.setZip(rs.getString("Zip"));
					contact.setPhone(rs.getString("Phone"));
					contact.setCardtype(rs.getString("CreditCardType"));
					contact.setStreet(rs.getString("Street"));	

				}
				
			} catch (SQLException e) {
			
				e.printStackTrace();

			}

			return result;
	}

	@Override
	public boolean update(Contact contact) {
//
		
	String sql="UPDATE person SET Id=?,FirstName=?,LastName=?,MiddleInitial=?,Street=?,City=?,State=?,Zip=?,Phone=?,Email=?,CreditCardType=?,CardNumber=?WHERE Id=?";
	PreparedStatement preparedStatement;
	try {
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, contact.getId());
		preparedStatement.setString(2, contact.getFirstName());
		preparedStatement.setString(3, contact.getLastName());
		preparedStatement.setString(4, contact.getMiddleInitial());
		preparedStatement.setString(5, contact.getStreet());
		preparedStatement.setString(6, contact.getCity());
		preparedStatement.setString(7, contact.getState());
		preparedStatement.setString(8, contact.getZip());
		preparedStatement.setString(9, contact.getPhone());
		preparedStatement.setString(10, contact.getEmail());
		preparedStatement.setString(11, contact.getCardtype());
		preparedStatement.setString(12, contact.getCardNumber());
		preparedStatement.setInt(13, contact.getId());
		preparedStatement.executeUpdate();
		
	} catch (SQLException e) {

		e.printStackTrace();
	}
		
		
		
		return true;
	}

	@Override
	public boolean insert(Contact contact) {
		String sql = "INSERT INTO person"
				+ "(Id, FirstName, LastName,MiddleInitial,Street,City,State,Zip,Phone,Email,CreditCardType,CardNumber) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, contact.getId());
			preparedStatement.setString(2, contact.getFirstName());
			preparedStatement.setString(3, contact.getLastName());
			preparedStatement.setString(4, contact.getMiddleInitial());
			preparedStatement.setString(5, contact.getStreet());
			preparedStatement.setString(6, contact.getCity());
			preparedStatement.setString(7, contact.getState());
			preparedStatement.setString(8, contact.getZip());
			preparedStatement.setString(9, contact.getPhone());
			preparedStatement.setString(10, contact.getEmail());
			preparedStatement.setString(11, contact.getCardtype());
			preparedStatement.setString(12, contact.getCardNumber());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean delete(Contact contact) {

		PreparedStatement preparedStatement = null;
		 
		String deleteSQL = "DELETE FROM person WHERE Id = ?";
 
		try {
			
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1,contact.getId());
			preparedStatement.executeUpdate();
			System.out.println("Record is deleted!");
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		}
		return true;
	}

}
