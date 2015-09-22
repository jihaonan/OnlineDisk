package com.liaoyb.po;

import java.io.Serializable;
import java.util.Date;

//这里不存文件名
public class SourceFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1592006864688230133L;
	private String fileId;
	//文件存放的链接或路径
	private String fileUrl;
	private Date addTime;
	private float size;
	//上传者
	private String userId;
	private FileType fileType;
	private int downloadtime;
	
	public SourceFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SourceFile(String fileId, String fileUrl, Date addTime, float size,
			String userId, FileType fileType, int downloadtime) {
		super();
		this.fileId = fileId;
		this.fileUrl = fileUrl;
		this.addTime = addTime;
		this.size = size;
		this.userId = userId;
		this.fileType = fileType;
		this.downloadtime = downloadtime;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public int getDownloadtime() {
		return downloadtime;
	}
	public void setDownloadtime(int downloadtime) {
		this.downloadtime = downloadtime;
	}
	
}
