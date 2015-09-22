package com.liaoyb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.liaoyb.po.FileType;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.ShareInfo;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.util.JdbcUtil;

public class DiskDao {
	private JdbcUtil util = new JdbcUtil();

	//拿到所有的文件
	public List<MyFile>gainAllFiles(String userid){
		List<MyFile> userFiles=new ArrayList<MyFile>();
		ResultSet result=util.executeQuery("select fileiddisk from myfiles where userid=?", userid);
		try {
			boolean isExitData=false;
			while(result.next()){
				isExitData=true;
				//通过fileiddisk拿到MyFile
				
				userFiles.add(gainMyFileByFileIdDisk(result.getString("fileiddisk")));
				
			}
			if(!isExitData){
				//没有当前user或者，没有文件及文件夹,返回null
				return null;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userFiles;
		
	}
	
	
	
	
	
	
	
	// 我的网盘容量
	public float gainCapacity(String userId) {
		float capacity=-1;
		ResultSet result=util.executeQuery("select capacity from diskinfo where userid=?", userId);
		try {
			if(result.next()){
				capacity=result.getFloat("capacity");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capacity;

	}

	// 网盘使用空间
	public float gainUseSapce(String userId) {
		// 没有此user，返回-1
		float useSapce = -1;
		ResultSet result = util.executeQuery(
				"select usespace from diskinfo where userid=?", userId);
		try {
			if (result.next()) {
				useSapce = result.getFloat("usespace");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return useSapce;
	}

	public SourceFile gainSourceFileByFileId(String fileId) {
		
		SourceFile sourFile=new SourceFile();
		
		ResultSet result=util.executeQuery("select * from upload where fileid=?", fileId);
		try {
			if(result.next()){
				sourFile.setFileId(fileId);
				sourFile.setFileUrl(result.getString("fileurl"));
				int fitype=result.getInt("filetype");
				sourFile.setFileType(transformToFileType(fitype));
				sourFile.setAddTime(result.getDate("addtime"));
				sourFile.setUserId(result.getString("userid"));
				sourFile.setDownloadtime(result.getInt("downloadtime"));
				sourFile.setSize(result.getFloat("size"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourFile;

	}
	
	//更新下载次数，次数已经加1，直接更新
	public void updateDownloadTime(SourceFile sourFile){
		util.executeUpdate("update upload set downloadtime=? where userid=?", sourFile.getDownloadtime(),sourFile.getUserId());
	}
	
	
	//把int 转换为type类型
	public FileType transformToFileType(int fitype){
//		txt,//0
//		film,//1
//		music,//2
//		picture,//3
//		compress,//4压缩包
//		other//5
		
		FileType fileType=FileType.other;
		switch(fitype){
		case 0:fileType=FileType.txt;break;
		case 1:fileType=FileType.film;break;
		case 2:fileType=FileType.music;break;
		case 3:fileType=FileType.picture;break;
		case 4:fileType=FileType.compress;break;
		case 5:fileType=FileType.other;break;
		}
		
		return fileType;
		
	}
	
	
	
	public ShareInfo gainShareInfoByShareId(String shareid){
		return null;
		
	}
	
	public MyFile gainMyFileByFileIdDisk(String fileiddisk){
		//myFile
		MyFile myFile=new MyFile();
		myFile.setFileIdInDisk(fileiddisk);
		String fileid=null;
		ResultSet result=util.executeQuery("select * from myfiles where fileiddisk=?", fileiddisk);
		try {
			if(result.next()){
				myFile.setPath(result.getString("path"));
				myFile.setFileName(result.getString("filename"));
				myFile.setType(result.getInt("type"));
				myFile.setUserId(result.getString("userid"));
				myFile.setCreateTime(result.getDate("createtime"));
				myFile.setIsdelete(result.getInt("isdelete"));
				fileid=result.getString("fileid");
				
				
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fileid==null||fileid.isEmpty()){
			//没有fileid，是文件夹,SourceFile就不设置，为null
			return myFile;
			
			
		}
		
		
		//通过fileid拿到sourcefile
		SourceFile sourFile=gainSourceFileByFileId(fileid);
		myFile.setSourceFile(sourFile);
		
		
		
		
		return myFile;
		
	}
	
	
	//这里下载都是文件，不包括文件夹
	// 下载,记录次数
		public void download(MyFile file) {
			// 更新源文件下载次数
			
			
			
			
			
		}

	// 上传,写入数据库上传的信息，上传时间，上传文件大小，保存的位置()
	// 在网盘中保存的位置,在MyFile的path中
	// txt,//0
	// film,//1
	// music,//2
	// picture,//3
	// compress,//4压缩包
	// other//5
	public void upload(MyFile myFile) {
		SourceFile sourFile = myFile.getSourceFile();
		int filetype = 4;
		switch (sourFile.getFileType()) {
		case txt:
			filetype = 0;
			break;
		case film:
			filetype = 1;
			break;
		case music:
			filetype = 2;
			break;
		case picture:
			filetype = 3;
			break;
		case compress:
			filetype = 4;
			break;
		case other:
			filetype = 5;
			break;
		}

		// 写入upload表
		util.executeUpdate(
				"insert into upload(fileid,fileurl,filetype,addtime,size,userid) values(?,?,?,?,?,?) ",
				sourFile.getFileId(), sourFile.getFileUrl(), filetype,
				new java.sql.Date(sourFile.getAddTime().getTime()),
				sourFile.getSize(), sourFile.getUserId());

		// 写入myfiles表
		util.executeUpdate(
				"insert into myfiles(fileiddisk,path,filename,type,fileid,userid,createtime) values(?,?,?,?,?,?,?)",
				myFile.getFileIdInDisk(), myFile.getPath(),
				myFile.getFileName(), myFile.getType(), sourFile.getFileId(),myFile.getUserId(),new java.sql.Date(myFile.getCreateTime().getTime()));
		// 更新网盘diskinfo信息,容量
		
		float origuse=gainUseSapce(myFile.getUserId());
		util.executeUpdate("update diskinfo set usespace=? where userid=?", origuse+sourFile.getSize(),myFile.getUserId());

	}

	

	// 验证是否可以获得分享文件
	public boolean valiGetShareFile(String shareUrl, String evidence) {
		return false;

	}

	// 通过验证后拿到分享文件
	public MyFile gainShareFile(String shareUrl) {
		return null;

	}

	// 下载分享文件(有时是下载分享文件的一部分) sharePath是在分享文件中的path
	public MyFile downShareFile(ShareInfo shareInfo, String sharePath) {
		return null;

	}

	// 分享,把ShareInfo写入数据库 表share
	public void share(ShareInfo shareInfo) {

	}

	// 转存,可能转存一部分
	// savePath保存的位置
	public boolean dumped(ShareInfo shareInfo, String sharePath, String savePath) {
		return false;

		// 先要验证空间是否够

	}



	

	public MyFile gainMyFileByName(String fileName) {

		return null;
	}

	public MyFile gainMyFileByDiskPath(String diskPath) {

		return null;
	}

	public ShareInfo gainShafoByshareUrl(String shareInfo) {

		return null;
	}

	// 网盘信息
	public OnlineDisk myDisk(User user) {
		ResultSet result = util.executeQuery(
				"select * from diskinfo where userid=?", user.getUserId());
		OnlineDisk myDisk = new OnlineDisk();
		myDisk.setUserId(user.getUserId());
		try {
			myDisk.setCapacity(result.getFloat("capacity"));
			myDisk.setUserspace(result.getFloat("usespace"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		user.setDisk(myDisk);
		return myDisk;

	}
	
	public void addMyFile(MyFile myFile){
		//是文件夹
		if(myFile.getType()==0){
		// 写入myfiles表
		util.executeUpdate(
				"insert into myfiles(fileiddisk,path,filename,type,userid,createtime) values(?,?,?,?,?,?)",
				myFile.getFileIdInDisk(), myFile.getPath(),
				myFile.getFileName(), myFile.getType(),myFile.getUserId(),new java.sql.Date(myFile.getCreateTime().getTime()));
		}else{
			//是文件
			util.executeUpdate(
					"insert into myfiles(fileiddisk,path,filename,type,fileid,userid,createtime) values(?,?,?,?,?,?,?)",
					myFile.getFileIdInDisk(), myFile.getPath(),
					myFile.getFileName(), myFile.getType(), myFile.getSourceFile().getFileId(),myFile.getUserId(),new java.sql.Date(myFile.getCreateTime().getTime()));
			
		}
		
	}
	
	
	//删除文件，把isdelete置为1
	public void deleteMyFile(String fileiddisk){

		util.executeUpdate("update myfiles set isdelete=? where fileiddisk=?", 1,fileiddisk);
		
		
		
	}
	
	//真的删除文件
	public void deleteMyFileReal(String fileiddisk){
		util.executeUpdate("delete from myfiles where fileiddisk=?", fileiddisk);
	}
	
}
