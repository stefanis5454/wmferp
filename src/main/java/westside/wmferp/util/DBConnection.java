package westside.wmferp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class DBConnection {

	private final static Logger LOGGER = Logger.getLogger(DBConnection.class.getCanonicalName());

	static DBConnection instance;

	Connection connection;

	private DBConnection() {

		try {
			// 调用Class.forName()方法加载驱动程序
			Class.forName("com.mysql.jdbc.Driver");

			LOGGER.info("成功加载MySQL驱动！");

			// System.out.println("成功加载MySQL驱动！");
		} catch (ClassNotFoundException e1) {

			LOGGER.warn("找不到MySQL驱动!");
			// System.out.println("找不到MySQL驱动!");
			e1.printStackTrace();
		}

		String url = "jdbc:mysql://localhost:3306/westside?useUnicode=true&characterEncoding=UTF-8"; // JDBC的URL
		//String url = "jdbc:mysql://101.200.38.174:3306/westside?useUnicode=true&characterEncoding=UTF-8"; // JDBC的URL
		
		// 调用DriverManager对象的getConnection()方法，获得一个Connection对象

		try {
			connection = DriverManager.getConnection(url, "root", "sy1984530");
			//connection = DriverManager.getConnection(url, "remote", "Sy@1984530");
			// 创建一个Statement对象

		} catch (SQLException e) {
			e.printStackTrace();
		}
	};

	public static Connection getConnection() {
		if (instance == null) {
			instance = new DBConnection();
		}
		try {
			if(instance.connection.isClosed())
			{
				instance = new DBConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return instance.connection;
	}

	public static ResultSet executeQuery(String sql) {
		Connection conn = DBConnection.getConnection();

		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs =  stmt.executeQuery(sql);
			conn.close();
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean insertSQLs(String[] sqls) {
		Connection conn = DBConnection.getConnection();
		try {
			// conn.setAutoCommit(false);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i < sqls.length; i++) {
				System.out.println(sqls[i]);
				stmt.execute(sqls[i]);
				// stmt.addBatch(sqls[i]);
			}
			// stmt.executeBatch();
			// conn.commit();
			conn.close();
			LOGGER.info("Success inserted " + sqls.length + " rows.");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection conn = getConnection();

			Statement stmt = conn.createStatement(); // 创建Statement对象
			System.out.print("成功连接到数据库！");

			String sql = "select * from Product where status = '电商'"; // 要执行的SQL
			ResultSet rs = stmt.executeQuery(sql);// 创建数据对象

			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t");
				System.out.println();
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
