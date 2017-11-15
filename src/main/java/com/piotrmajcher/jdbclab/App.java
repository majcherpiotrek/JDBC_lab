package com.piotrmajcher.jdbclab;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.h2.tools.Server;

import com.piotrmajcher.jdbclab.dao.exceptions.CarDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ClientDaoException;
import com.piotrmajcher.jdbclab.dao.exceptions.ReservationDaoException;
import com.piotrmajcher.jdbclab.gui.AppPanel;
import com.piotrmajcher.jdbclab.gui.AppPanelFactory;

public class App 
{
	private static final Logger logger = Logger.getLogger(App.class);
	
	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:tcp://localhost/~/jdbc_lab;INIT=RUNSCRIPT FROM 'classpath:init.sql'";  
	   
	//  Database credentials 
	private static final String USER = "sa"; 
	private static final String PASS = "";

	private static final String APP_NAME = "JdbcLab";
	
	private static Server dbWebServer;
	private static Server tcpServer;
	public static Connection dbConnection;
	
    public static void main( String[] args ) throws InterruptedException {
    	try {
			initDatabase();
			logger.info("Database initialized!");
			createAndShowGUI();
		} catch (ClassNotFoundException e) {
			logger.error("Could not find the database driver: " + e.getMessage());
			e.printStackTrace();
			closeDatabase();
			System.exit(1);
		} catch (ClientDaoException | ReservationDaoException | CarDaoException e) {
			logger.error("Error while accessiong database: " + e.getMessage());
			e.printStackTrace();
			closeDatabase();
			System.exit(2);
		} catch (SQLException e) {
			logger.error("Error occured while initializing the database: " + e.getMessage());
			e.printStackTrace();
			System.exit(3);
		} 
    }
    
    private static void createAndShowGUI() throws ClientDaoException, ReservationDaoException, CarDaoException {
    	JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame mainFrame = new JFrame(APP_NAME);
		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				closeDatabase();
				System.exit(0);
			}
			
		});
		
		AppPanel appPanel = AppPanelFactory.createAppPanel(dbConnection);
		mainFrame.setContentPane(appPanel);
		mainFrame.pack();
		mainFrame.setVisible(true);
    }
    
    private static void initDatabase() throws ClassNotFoundException, SQLException {
    	Class.forName(JDBC_DRIVER);
    	dbWebServer = Server.createWebServer().start();
    	tcpServer = Server.createTcpServer().start();
    	dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
    }
    
    private static void closeDatabase() {
    	logger.info("Closing the database!");
		try {
			if (dbConnection != null) {
				Statement statement = dbConnection.createStatement();
				logger.info("Clearing the database...");
				statement.execute("DROP ALL OBJECTS");
				logger.info("Closing the database connection...");
				dbConnection.close();
			}
		} catch (SQLException e) {
			logger.error("Unable to close the database connection properly: " + e.getMessage());
		}
		
		if (dbWebServer.isRunning(true)) {
			logger.info("Stopping h2 webserver...");
			dbWebServer.stop();
		}
		if (tcpServer.isRunning(true)) {
			logger.info("Stopping h2 tcp server...");
			tcpServer.stop();
		}
		logger.info("Closing the database finished!");
    }
}
