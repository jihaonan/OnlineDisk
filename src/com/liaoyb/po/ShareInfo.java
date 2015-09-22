package com.liaoyb.po;

import java.util.Date;




//分享信息
public class ShareInfo {
	
	private String shareId;
	private String userId;
	//分享日期
	private Date shareDate;
	//分享文件的链接
	private String shareUrl;
	
	private ShareType shareType;
	//分享的文件
	private MyFile myFile;
	
	//
//	分享的凭证,私密分享，好友分享,发送给好友
	
	//凭证,可能为空，不用凭证
	private String evidence;

	public ShareInfo(String shareUrl) {
		super();
		this.shareUrl = shareUrl;
	}

	public ShareInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShareInfo(String shareUrl, String evidence) {
		super();
		this.shareUrl = shareUrl;
		this.evidence = evidence;
	}
	
	
	
}
