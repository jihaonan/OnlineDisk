package com.liaoyb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.liaoyb.po.MyFile;
import com.liaoyb.po.Table;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;

public class DiskDataServlet extends HttpServlet {
	private IDiskService diskServ = new MyDiskService();

	/**
	 * 给浏览器返回网盘文件的json数据
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		/* 设置格式为text/json */
		response.setContentType("text/json");
		/* 设置字符集为'UTF-8' */
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();


		// 这里的path是root_myDir_jj.java这种格式
		String path = request.getParameter("path");
		System.out.println("发送来的路径:" + path);
		Object userobj = request.getSession().getAttribute("user");
		if (userobj == null) {
			// 当前用户未登录
			out.print("message=当前用户未登录");
			return;
		}
		User user = (User) userobj;

		// 先从session中查找是否有文件对象,files,没有的话就新建一个
		Object att = request.getSession().getAttribute("files");
		List<MyFile> allFiles = null;
		if (att != null) {
			allFiles = (List<MyFile>) att;

		} else {
			// 把所有文件找出来，放到session里面，再里面进行过滤路径符合的
			allFiles = diskServ.gainUserFiles(user.getUserId());
			request.getSession().setAttribute("files", allFiles);

		}
		// 可能为null，用户没有文件数据
		if (allFiles == null) {
			out.print("message=没有任何文件信息");
			return;
		}

		// 格式化path
		path = path.replace("_", "/");
		System.out.println("转换后的path:" + path);

		// 拿到过滤后的文件列表
		List<MyFile> finFiles = diskServ.FilesUnderPath(allFiles, path);

		// finFiles可能size为0，表示当前目录中没有内容

		List<Table> tableData = new ArrayList<Table>();
		for (MyFile file : finFiles) {
			
			//已经被删除的不加入里面
			if(file.getIsdelete()==1){
				continue;
			}
			
			int fileicon = 6;
			if ( file.getType() == 1)
				switch (file.getSourceFile().getFileType()) {
				// txt,//0
				// film,//1
				// music,//2
				// picture,//3
				// compress,//4压缩包
				// other//5
				case txt:
					fileicon = 0;
					break;
				case film:
					fileicon = 1;
					break;
				case music:
					fileicon = 2;
					break;
				case picture:
					fileicon = 3;
					break;
				case compress:
					fileicon = 4;
					break;
				case other:
					fileicon = 5;
					break;

				}
			// 格式化date
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String createTime;
			 createTime = format.format(file.getCreateTime());
			
			
			String size = "";
			if (file.getType() == 0) {
				size = "-";
			} else {
				float filesize = file.getSourceFile().getSize();
				if (filesize > 1024)
					size = (int) (filesize / 1024) + "K";
				if (filesize > 1024 * 1024)
					size = (int) (filesize / (1024 * 1024)) + "M";

			}

			Table tab = new Table(fileicon + "", file.getFileName(),
					file.getType() + "", createTime, file.getFileIdInDisk(), size);
			tableData.add(tab);

		}
		// json array
		JSONArray ja1 = JSONArray.fromObject(tableData);
		System.out.println(ja1.toString());

		out.write(ja1.toString());

		// 写json数据到浏览器

		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
