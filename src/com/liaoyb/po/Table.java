package com.liaoyb.po;

public class Table {
	private String fileicon;
	private String filename;
	private String type;//"0"是文件夹   "1"是文件
	private String editdate;
	private String data_id;
	private String filesize;
	public Table() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Table(String fileicon, String filename, String type,
			String editdate, String data_id, String filesize) {
		super();
		this.fileicon = fileicon;
		this.filename = filename;
		this.type = type;
		this.editdate = editdate;
		this.data_id = data_id;
		this.filesize = filesize;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}
	public String getFileicon() {
		return fileicon;
	}
	public void setFileicon(String fileicon) {
		this.fileicon = fileicon;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEditdate() {
		return editdate;
	}
	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}
	public String getData_id() {
		return data_id;
	}
	public void setData_id(String data_id) {
		this.data_id = data_id;
	}
	
}
