package com.liaoyb.service;

import com.liaoyb.po.User;

public interface IUserService {
	//登陆
	public User login(String strname,String password);
	public User register(String username,String password);
	//更新资料
	public boolean updateInfo(User user);
	//通过用户名查找用户
	public User gainUserByUserName(String username);
	
	//查找用户名是否存在
	public boolean userExitByName(String username);
}
