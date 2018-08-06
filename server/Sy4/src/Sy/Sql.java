package Sy;

import java.sql.*;

public class Sql {
	private String host = "localhost";
	private int port = 1433;//端口
	private String dataBaseName = "sy";//数据库名称 
	private String username = "sa";//用户名
	private String password = "19980814";//密码
	String JDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动，需拷贝到Tomcat/lib目录下
	private String url = null;
	private PreparedStatement ps = null; 
	String sql="select * from dl where 1=1"; 
	private Connection conn;
	public static int errordeal=0;
	String user="";
	String pass="";
	public Sql() {
	try{
		Class.forName(JDriver);
		url = String.format("jdbc:sqlserver://%s:%d;DatabaseName=%s", host, port, dataBaseName);
		conn = DriverManager.getConnection(url, username, password);//与数据库建立连接
	}catch(Exception e){
		System.out.println("连接失败！！！");
		e.printStackTrace();
	}
	}
	public void queryFordlTable() throws SQLException {
		String query = "select * from dl where 1 = 1";
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		System.out.println("表dl");
		ResultSetMetaData rsmd = rs.getMetaData();//ResultSetMetaData是一个有关整个数据库的信息
		int columns = rsmd.getColumnCount();
		for (int i = 1; i <= columns; i++) {//数据库的表中列的索引是从1开始的
			System.out.print(rsmd.getColumnName(i) + "\t");
		} 
		System.out.println();
		while (rs.next()) {
			for (int i = 1; i <= columns; i++) {
				System.out.print(rs.getString(i) + "\t");
			}
			System.out.println(); 
		}
	}
	public String errorhandle() {
		String error1="";
		if(errordeal==1)
			error1="userfailed";
		else if(errordeal==2)
			error1= "passfailed";
		return error1;
	}
	public Boolean denglu(String user,String pass){
		String query = String.format("select * from dl where username = '%s'", user);
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				//System.out.println(rs.getString(1)+rs.getString(2));
//				if(rs.getString(1).equals(user)&&rs.getString(2).equals(pass)){
				if(rs.getString(2).equals(pass)){	
					return true;
				}
				else
					errordeal=2;
			}
			else
				errordeal=1;
			sqlclose();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}
	public Boolean insertdl(String user, String pass){
		String query = String.format("select * from dl where username = '%s'", user);//username为主键，唯一性的标识数据库表中的数据，器数据不可重复
	    PreparedStatement ps;
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery(); 
		    if (!rs.next()) {//如果不存在，那么直接进行插入就行，（对表dl进行操作）
		    	query = String.format("insert into dl(username, password) values ('%s', '%s')", user, pass);
		    	ps = conn.prepareStatement(query);
		    	ps.execute();
		    	sqlclose();
		    	return true;
		    } 
		    else {
		    	System.out.println("已存在该数据"); 
		    	sqlclose();
		    	return false;
		    }
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
	    
	}
	public void sqlclose() throws SQLException {
		    if(ps!= null){
			    ps.close();
			    ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
	}
}