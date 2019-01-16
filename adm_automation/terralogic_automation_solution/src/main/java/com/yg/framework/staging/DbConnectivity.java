package com.yg.framework.staging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.web_automation.solution.Web_automation_common_utils;

public class DbConnectivity extends Web_automation_common_utils {
	// bus price info: inventory master into yg_inventory
	// user info: in yg_inventory.USERS
	Statement statement = null;
	Connection connection = null;

	public DbConnectivity() {

	}

	public String reteriveBusprice(String databaseName, String tableName, String boardingCity, String destinationCity,
			int inventoryId) {
		String priceData = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			String query = "SELECT PRICE FROM " + tableName + " where BOARDING_CITY_NAME=" + "'" + boardingCity + "'"
					+ " and DESTINATION_CITY_NAME= '" + destinationCity + "'" + " and DAILY_INVENTORY_ID= '" + inventoryId
					+ "'";
			System.out.println("Successfully Connected to the database!");
			statement = connection.createStatement();
			System.out.println("start executing staement");
			ResultSet resultSet = statement.executeQuery(query);
			// int totalColumn = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				priceData = resultSet.getString(1);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return priceData;
	}

	public String stausOfTicket(String databaseName, String bookingTicket, String tableName) {
		String status = null;
		String query = "select BOOKING_STATUS from " + tableName + " where TICKET_ID = " + "'" + bookingTicket + "'";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				status = resultSet.getString(1);
				System.out.println("status for this booking in db: " + status);
			}

		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	public int reteriveTotalNoOfBuses(String databaseName, String tableName, String boardingCity,
			String destinationCity,LinkedList<String> idList) {
		String busesId = String.join(",", idList);
		 
		System.out.println("busesId:"+busesId);
		int count = 0;
		String query = "SELECT count(*) FROM " + tableName + " where BOARDING_CITY_NAME=" + "'" + boardingCity + "'"
				+ " and DESTINATION_CITY_NAME=" + "'" + destinationCity + "'" + " and INVENTORY_ID in("+busesId+")";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				count = resultSet.getInt(1);
				System.out.println("total no of buses on this route from databse: " + count);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public String reteriveUserName(String databaseName, String tableName, String createdBy) {
		String query = "SELECT USER_NAME FROM " + tableName + " where CREATED_BY=" + "'" + createdBy + "'";
		String userName = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			statement = connection.createStatement();
			ResultSet resuleSet = statement.executeQuery(query);
			while (resuleSet.next()) {
				userName = resuleSet.getString(1);
				System.out.println("actual user name stored in db:" + userName);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userName;
	}

	public String reteriveOTP(String databaseName, String bookingTicket, String tableName) {
		String otp = null;
		String query = "select VERIFICATION_ID from " + tableName + " where TICKET_ID = " + "'" + bookingTicket + "'";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				otp = resultSet.getString(1);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return otp;
	}

	// public static void main(String[] args) {
	// String databaseName = "yg_inventory";
	// String tableName = "INVENTORY_MASTER";
	// String inventoryId="16";
	// reteriveBusCreationTime(databaseName,tableName, inventoryId);
	// }
	public int reteriveBusCreationTime(String databaseName, String tableName, String inventoryId) {
		int activeFlag = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			String query = "SELECT ACTIVE_FLAG FROM " + tableName + " where INVENTORY_ID = " + "'" + inventoryId + "'";
			System.out.println(query);
			statement = connection.createStatement();
			// System.out.println("start executing staement in db");
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				activeFlag = resultSet.getInt(1);
				System.out.println("activeFlag in db:" + activeFlag);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return activeFlag;
	}

	public int deleteCreatedBus(String databaseName, String tableName, String inventoryId) {
		int expectedActiveFlag = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			String query = "DELETE FROM " + tableName + " where INVENTORY_ID= " + "'" + inventoryId + "'";
			statement = connection.createStatement();
			System.out.println("start executing staement in db");
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				expectedActiveFlag = resultSet.getInt(1);
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return expectedActiveFlag;
	}
	
	public String reteriveWalletAmountFromDb(String databaseName, String tableName, String userName) {
		String walletAmount=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// System.out.println("driver is loaded successfully");
			String connectionUrl = "jdbc:mysql://65.49.80.210/" + databaseName + "?useSSL=false";
			connection = DriverManager.getConnection(connectionUrl, "ygroot", "Terralogic@123");
			String query = "SELECT wallet_amount FROM " + tableName + " where CREATED_BY="+"'"+userName+"'";
			statement = connection.createStatement();
			System.out.println("start executing staement in db");
			ResultSet resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				walletAmount=resultSet.getString(1);
			}
		}catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return walletAmount;
	}

}
