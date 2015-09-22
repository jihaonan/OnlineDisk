package com.liaoyb.service;

import java.util.List;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.ShareInfo;
import com.liaoyb.po.ShareType;
import com.liaoyb.po.User;

public interface IDiskService {

	
	
	public MyFile gainMyFileByName(String fileName);
	public MyFile gainMyFileByDiskPath(String diskPath);
	public ShareInfo gainShafoByshareUrl(String shareInfo);
	
	//新建文件夹
	public void newDir(MyFile dirFile);
	//拿到所有的文件
	public List<MyFile>gainUserFiles(String userid);
	
	//过滤文件，返回符合路径的文件列表
	//path   root/..
	public List<MyFile>FilesUnderPath(List<MyFile> files,String path);
	
	
	
	//上传前验证,大小是否超了，我的网盘容量是否够
	public boolean validate(User user,float FileSize);
	
	//网盘信息
	public OnlineDisk myDisk();
	
	
	//上传,写入数据库上传的信息，上传时间，上传文件大小，保存的位置()
	//在网盘中保存的位置,在MyFile的path中
	public void upload(MyFile file);
	
	
	//下载,记录次数,返回文件的url
	//下载自己的文件
	MyFile downloadMySelf(User currentUser, String fileiddisk);
	
	public MyFile downloadShare(String shareid);
	
	//验证是否可以获得分享文件
	public boolean valiGetShareFile(ShareInfo shareInfo,String evidence);
	
	//下载分享文件(有时是下载分享文件的一部分)  sharePath是在分享文件中的path
	public void downShareFile(ShareInfo shareInfo,String sharePath);
	
	//分享,可能是文件，也可能是文件夹,生成一个ShareInfo,并写入数据库
	public ShareInfo share(MyFile myFile,ShareType sharetype);
	
	
	
	//转存,可能转存一部分
	public boolean dumped(ShareInfo shareInfo,String sharePath);
	
	//删除,把删除的标志置为1
	public void delete(String fileiddisk);
	
	//真的删除
	public void deleteReal(String fileiddisk);
	
	
	
	
}
