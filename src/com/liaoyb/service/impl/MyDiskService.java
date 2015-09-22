package com.liaoyb.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.liaoyb.dao.DiskDao;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.OnlineDisk;
import com.liaoyb.po.ShareInfo;
import com.liaoyb.po.ShareType;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;

public class MyDiskService implements IDiskService {
	private DiskDao diskDao=new DiskDao();



	@Override
	public boolean validate(User user,float fileSize) {
		float num=diskDao.gainCapacity(user.getUserId())-(diskDao.gainUseSapce(user.getUserId())+fileSize);
		System.out.println("num:"+num);
		if(num<0)
			return false;
		
		//先返回true
		return true;
//		return false;
	}

	

	


	@Override
	public void downShareFile(ShareInfo shareInfo, String sharePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public ShareInfo share(MyFile myFile, ShareType sharetype) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean dumped(ShareInfo shareInfo, String sharePath) {
		// TODO Auto-generated method stub
		return false;
	}

	

	

	@Override
	public boolean valiGetShareFile(ShareInfo shareInfo, String evidence) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MyFile gainMyFileByName(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyFile gainMyFileByDiskPath(String diskPath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShareInfo gainShafoByshareUrl(String shareInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	//上传
	@Override
	public void upload(MyFile file) {
		
		
		diskDao.upload(file);
		
		
		
	}

	
	


	
	//网盘信息
	@Override
	public OnlineDisk myDisk() {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	//下载,返回文件url
	@Override
	public MyFile downloadMySelf(User currentUser,String fileiddisk) {
		//如果当前user不是文件的拥有者,或未登陆,返回null
		MyFile myFile=diskDao.gainMyFileByFileIdDisk(fileiddisk);
		if(currentUser==null){
			return null;
		}
		if(myFile==null){
			return null;
		}
		if(!myFile.getUserId().equals(currentUser.getUserId())){
			return null;
		}
		//更新下载次数
		SourceFile sourFile=myFile.getSourceFile();
		sourFile.setDownloadtime(sourFile.getDownloadtime()+1);;
		diskDao.updateDownloadTime(sourFile);
		return myFile;
	}

	@Override
	public MyFile downloadShare(String shareid) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	//拿到所有的文件
		@Override
		public List<MyFile>gainUserFiles(String userid){
		return diskDao.gainAllFiles(userid);
		
			
		}
		
		//过滤文件，返回符合路径的文件列表
		//path   root/..
	@Override
		public List<MyFile>FilesUnderPath(List<MyFile> files,String path){
		List<MyFile> finFiles=new ArrayList<MyFile>();
		//遍历
		for(MyFile file:files){
			if(file.getPath().matches(path+"/[^/]*")){
				finFiles.add(file);
			}
				
	
		}
		
			return finFiles;
		
	}

	
	
	//新建文件夹
	@Override
	public void newDir(MyFile dirFile) {
		
		diskDao.addMyFile(dirFile);
		// TODO Auto-generated method stub
		
	}
	
	//删除,把删除的标志置为1
		public void delete(String fileiddisk){
			//先得到对应的MyFile
			MyFile delFile=diskDao.gainMyFileByFileIdDisk(fileiddisk);
			//是文件夹
			if(delFile.getType()==0){
				//是文件夹
				//文件夹下的文件
				List<MyFile> delFiles=FilesUnderPath(gainUserFiles(delFile.getUserId()),delFile.getPath());
				//遍历删除
				for(MyFile file:delFiles){
					diskDao.deleteMyFile(file.getFileIdInDisk());
				}
				
				
			}
			//再删除自己
			diskDao.deleteMyFile(fileiddisk);
		}
		
		//真的删除
		public void deleteReal(String fileiddisk){
			diskDao.deleteMyFileReal(fileiddisk);
		}






	

	

}
