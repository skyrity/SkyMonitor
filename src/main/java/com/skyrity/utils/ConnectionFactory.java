package com.skyrity.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionFactory {
	private static Logger log = LoggerFactory
			.getLogger(ConnectionFactory.class);
	/* 定义连接数据库参数 */
	// private static final String DRIVER
	// ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// private static final String URL
	// ="jdbc:sqlserver://thinkpad-pc;instanceName=SQL2008; DatabaseName=Sky2010";
	// 连接服务器和数据库test
	// private static final String USER = "sa"; // 默认用户名
	// private static final String PASSWORD = "12345"; // 密码
	private static final String CONFIGFILE = "/system.properties";

	/**
	 * 获得配置文件中JDBC的Driver
	 * 
	 * @return Driver串
	 */
	private static String getDriver() {
		String driver = null;
		InputStream inputStream = ConnectionFactory.class
				.getResourceAsStream(CONFIGFILE);
		try {
			Properties p = new Properties();
			try {
				p.load(inputStream);
				driver = p.getProperty("DRIVER");
			} catch (IOException e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}
			inputStream.close();
		} catch (IOException e) {
			log.error(e.toString());
		}

		if (driver == null || driver == "")
			driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		// log.debug("DRIVER=" + driver);
		return driver;
	}

	/**
	 * 获得配置文件中JDBC的URL
	 * 
	 * @return URL串
	 */
	private static String getURL() {
		String url = null;
		InputStream inputStream = ConnectionFactory.class
				.getResourceAsStream(CONFIGFILE);
		try {
			Properties p = new Properties();

			try {
				p.load(inputStream);
				url = p.getProperty("URL");
			} catch (IOException e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}
			inputStream.close();
		} catch (IOException e) {
			// e.printStackTrace();
			log.error(e.toString());
		}
		if (url == null || url == "")
			url = "jdbc:sqlserver://Macai-pc;DatabaseName=SkyVistorWeb";

		// log.debug("URL=" + url);
		return url;
	}

	/**
	 * 获得配置文件中数据库登录用户
	 * 
	 * @return 用户名
	 */
	private static String getUser() {
		String user = null;
		InputStream inputStream = ConnectionFactory.class
				.getResourceAsStream(CONFIGFILE);
		try {
			Properties p = new Properties();
			try {
				p.load(inputStream);
				user = p.getProperty("USER");
			} catch (IOException e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}

			if (user == null || user == "")
				user = "sa";

			inputStream.close();
		} catch (IOException e) {
			// e.printStackTrace();
			log.error(e.toString());
		}
		// log.debug("USER=" + user);
		return user;
	}

	/**
	 * 获得配置文件中数据库用户登录密码
	 * 
	 * @return 密码
	 */
	private static String getPassword() {
		String password = null;
		try {
			InputStream inputStream = ConnectionFactory.class
					.getResourceAsStream(CONFIGFILE);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				password = p.getProperty("PASSWORD");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			if (password == null || password == "")
				password = "12345";

			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		// log.debug("PASSWORD=" + password);
		return password;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return 成功 返回 java.sql.Connection 失败返回 null
	 */
	public static Connection getConnection() {
		try {
			Class.forName(getDriver());

			Connection conn = DriverManager.getConnection(getURL(), getUser(),
					getPassword());
			//log.debug("Connection Successful!"); // 如果连接成功 控制台输出
			return conn;
		} catch (SQLException e) {
			log.error("========================获取连接出错===================="
					+ e.toString());
			// e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			log.error("========================获取连接出错===================="
					+ e.toString());
			// e.printStackTrace();
			return null;

		}

	}

	/**
	 * 关闭数据库连接
	 * 
	 * @return 成功 返回 java.sql.Connection 失败返回 null
	 */
	public static void close(Connection conn, Statement stm, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stm != null)
				stm.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			log.error(e.toString());
			// e.printStackTrace();
		}
	}

	/**
	 * 获得配置文件中指定字段数据
	 * 
	 * @return 有就是数据，没有就是“”
	 */
	public static String readIni(String params) {
		String value = null;
		try {
			InputStream inputStream = ConnectionFactory.class
					.getResourceAsStream(CONFIGFILE);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				value = p.getProperty(params);
			} catch (IOException e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}

			if (value == null || value == "")
				value = "";

			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return value;
	}

	/**
	 * 获得配置文件中端口
	 * 
	 * @return 默认4660
	 */
	public static int readLocalPort() {
		int value = 4660;
		try {
			InputStream inputStream = ConnectionFactory.class
					.getResourceAsStream(CONFIGFILE);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				String port = p.getProperty("LOCALPORT");
				value = Integer.parseInt(port);
			} catch (Exception e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return value;
	}
	
	/**
	 * 获得配置文件中端口
	 * 
	 * @return 默认4660
	 */
	public static int readRemotePort() {
		int value = 4661;
		try {
			InputStream inputStream = ConnectionFactory.class
					.getResourceAsStream(CONFIGFILE);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				String port = p.getProperty("REMOTEPORT");
				value = Integer.parseInt(port);
			} catch (Exception e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return value;
	}

	/**
	 * 获得配置文件中端口断开最大超时时间
	 * 
	 * @return 默认60秒
	 */
	public static int readTryTime() {
		int value = 60;
		try {
			InputStream inputStream = ConnectionFactory.class
					.getResourceAsStream(CONFIGFILE);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				String tryTime = p.getProperty("TryTime");
				value = Integer.parseInt(tryTime);
			} catch (IOException e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return value;
	}

	/**
	 * 使用的微信秘钥是哪一个，默认第一个，在ini中配置读取
	 * 
	 * @author 吴林
	 * @date 创建时间：2017-3-20下午5:30:02
	 * @return
	 */
	public static int readWxTockId() {
		int value = 1;
		try {
			InputStream inputStream = ConnectionFactory.class
					.getResourceAsStream(CONFIGFILE);
			Properties p = new Properties();
			try {
				p.load(inputStream);
				String wxTockId = p.getProperty("WxTockId");
				value = Integer.parseInt(wxTockId);
			} catch (IOException e1) {
				e1.printStackTrace();
				log.error(e1.toString());
			}
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return value;
	}

//	/**
//	 * 返回保存使用的网页编码格式，默认ISO8859-1
//	 * 
//	 * @author 吴林
//	 * @date 创建时间：2017-3-21下午2:19:46
//	 * @return
//	 */
//	public static String readEncoding() {
//		String encoding = "ISO8859-1";
//		try {
//			InputStream inputStream = ConnectionFactory.class
//					.getResourceAsStream(CONFIGFILE);
//			Properties p = new Properties();
//			try {
//				p.load(inputStream);
//				encoding = p.getProperty("Encoding");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//	log.error(e1.toString());
//			}
//			inputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//	log.error(e.toString());
//		}
//		return encoding;
//	}

}
