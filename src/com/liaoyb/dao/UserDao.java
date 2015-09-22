package com.liaoyb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.User;
import com.liaoyb.util.JdbcUtil;
import com.liaoyb.util.MD5;

public class UserDao {
	private JdbcUtil util = new JdbcUtil();

	// 注册
	public boolean addUser(User user) {
		util.executeUpdate("insert into users(userid,username,password) values (?,?,?)", user.getUserId(),user.getUsername(),user.getPassword());
		//新建一个diskinfo列,1个G
		OnlineDisk disk=new OnlineDisk(1024*1024*1024, 0, user.getUserId());
		
		util.executeUpdate("insert into diskinfo (userid,capacity,usespace) values(?,?,?)", disk.getUserId(),disk.getCapacity(),disk.getUserspace());
		
		
		
		return true;

	}

	// 更新用户资料
	public void updateUserInfo(User user) {

	}

	public User loginByUsername(String username, String password) {
		// 通过用户名找到对应的密码

		ResultSet result = util.executeQuery(
				"select userid,username,password from users where username=?",
				username);
		User user = gainUser(result);
		if(user==null)
			return null;
		// 验证密码
		boolean state = MD5.checkpassword(password, user.getPassword());
		if (state)
			return user;
		return null;

	}

	// 得到user

	public User gainUser(ResultSet result) {
		User user = null;
		try {
			if (result.next()) {
				user = new User();
				user.setUserId(result.getString("userid"));
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;

	}
	
	public boolean userExitByUsername(String username){
		
		ResultSet result=util.executeQuery("select userid from users where username=?", username);
		try {
			if(result.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	
	public User gainUserByUserName(String username){
		ResultSet result=util.executeQuery("select * from users where username=?", username);
		return gainUser(result);
	}
	

	public User loginByEmail(String email, String password) {
		// 通过邮箱找到对应的密码

		ResultSet result = util.executeQuery(
				"select userid,username,password from users where email=?",
				email);
		User user = gainUser(result);

		// 验证密码
		boolean state = MD5.checkpassword(password, user.getPassword());
		if (state)
			return user;
		return null;

	}

	// 好友等

}
