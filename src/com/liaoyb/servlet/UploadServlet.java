package com.liaoyb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.liaoyb.po.FileType;
import com.liaoyb.po.MyFile;
import com.liaoyb.po.SourceFile;
import com.liaoyb.po.User;
import com.liaoyb.service.IDiskService;
import com.liaoyb.service.impl.MyDiskService;
import com.liaoyb.util.Json;


public class UploadServlet extends HttpServlet {
	private IDiskService diskServ=new MyDiskService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
        
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//先从session中查找是否有文件对象,如果有，上传文件后，对其进行更新
		Object att = request.getSession().getAttribute("files");
		List<MyFile> allFiles = null;
		if (att != null) {
			allFiles = (List<MyFile>) att;

		}
		
		
		
		
		System.out.println("有上传");
		/* 设置格式为text/json */
		response.setContentType("text/json");
		/* 设置字符集为'UTF-8' */
		response.setCharacterEncoding("UTF-8");
		PrintWriter printout = response.getWriter();
		
		//从session中拿到当前user,从请求中拿到文件保存在网盘中的path(存放目录的路径)
		User user=(User) request.getSession().getAttribute("user");
		
		if(user==null){
			//当前未登录，跳转到登陆页面
			System.out.println("未登陆");
//			response.sendRedirect(this.getServletContext().getContextPath()+"/login.jsp?message=please login first");
			
			printout.print(Json.writeMess("未登陆"));
			return;
		}
		
		String path="";
		String filename="";
		float size;
		
		
		
		//存放路径
		
		String savePath=this.getServletContext().getRealPath("/WEB-INF/upload");
		File file=new File(savePath);
		//判断上传文件的保存目录是否存在
		if(!file.exists()&&!file.isDirectory()){
			System.out.println(savePath+"目录不存在，正在创建");
			file.mkdirs();
		}
		//提示消息
		String message=".......";
		
		
		 //使用Apache文件上传组件处理文件上传步骤：
		//1.创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//2.创建一个文件上传解析器
		ServletFileUpload upload=new ServletFileUpload(factory);
		//解决上传文件名的中文乱码
		upload.setHeaderEncoding("utf-8");

		//upload.setProgressListener(new UploadProgressListener(request.getSession(),new UploadStatus()));//设置上传进度监听,这是所有的进度
		
		//3.判断提交上来的数据是否是上传表单的数据
		if(!ServletFileUpload.isMultipartContent(request)){
			//按照传统方式获取数据
			
			System.out.println("普通表单");
			return;
		}
		
		//4.使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合
		//，每一个FileItem对应一个Form表单的输入项
		try {
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item:list){
				
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name=item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value=item.getString("utf-8");
					
					
					//这里拿到path
					if("path".equals(name)){
						path=value;
						// 格式化path
						path = path.replace("_", "/");
						System.out.println("转换后的path:" + path);
					}
					
					
					//这里应该有文件名
					if("filename".equals(name)){
						filename=value;
					}
					if("size".equals(name)){
						size=Float.parseFloat(value);
						//这里要验证容量是否够
						if(!diskServ.validate(user,size)){
							//空间不足
							message="空间不足";
							break;
						}
					}
					
					System.out.println(name+"="+value);
					
				}else{
					System.out.println("有文件");
					
					
					//如果fileitem中封装的是上传文件
					//得到上传的文件名称
					 String origfilename=item.getName();
					System.out.println(origfilename);//文件名或者文件路径
					if(origfilename==null||origfilename.trim().equals("")){
						continue;
					}
					
					
					long fileSize=item.getSize();//文件大小
					System.out.println("文件大小:"+fileSize+"字节");
					
					
					
					if(filename==null||filename==""){
						filename=origfilename;
					}
					//不能和原来目录中其它文件同名
					
					
					
					
					
					
					
					//这里保存的名字，实际名字存在数据库中，
					
					String saveFileName=UUID.randomUUID().toString();
					String fileUrl=savePath+"\\"+saveFileName;
					
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
				   //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename=filename.substring(filename.lastIndexOf("\\")+1);
					//取后缀
					fileUrl+=filename.substring(filename.lastIndexOf("."));
					
					
					//获取item中的上传文件的输入流
					InputStream in=item.getInputStream();
					//创建一个文件的输入流
					FileOutputStream out=new FileOutputStream(fileUrl);
					byte buffer[]=new byte[1024];
					int len=0;
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
						out.flush();
					}
					out.close();
					in.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
					message="文件上传成功";
					
					
					
					//diskServ中的上传,更新上传信息
					//创建一个MyFile
					
					//文件实际位置
					
					MyFile myfile=createMyFile(user.getUserId(),filename,fileSize,fileUrl,path);
					
					diskServ.upload(myfile);
					//更新session中Myfile集合
					if(allFiles!=null){
						allFiles.add(myfile);
					}
					
					
					
				}
			}
			
			
			//上传成功,
			
			
			
			
			
		} catch (FileUploadException e) {
			message="文件上传失败";
			e.printStackTrace();
		}
//		message=URLEncoder.encode(message,"utf-8");
		System.out.println("json_mess:"+Json.writeMess(message));
		printout.print(Json.writeMess(message));
		//
		System.out.println(message);
		
//		response.sendRedirect(this.getServletContext().getContextPath()+"/upload_message.jsp?message="+message);
//		
	}
	
	
	//新建一个MyFile 
	public MyFile createMyFile(String userId,String filename,float size,String fileUrl,String path){
		//SourceFile
		//从filename中得到FileType
		
		FileType fileType=FileType.other;
		
		if(filename.matches("(.*\\.txt)|(.*\\.doc)|(.*\\.java)")){
			//文本文件
			fileType=FileType.txt;
		}
		if(filename.matches("(.*\\.mp4)|(.*\\.avi)|(.*\\.wmv)")){
			//视频
			fileType=FileType.film;
		}
		if(filename.matches("(.*\\.jpg)|(.*\\.png)|(.*\\.jpeg)")){
			//图片
			fileType=FileType.picture;
		}
		if(filename.matches("(.*\\.zip)|(.*\\.rar)|(.*\\.zip)|(.*\\.7z)|(.*\\.iso)")){
			fileType=FileType.compress;
		}
		if(filename.matches("(.*\\.mp3)|(.*\\.wav)|(.*\\.aac)")){
			//音乐
			fileType=FileType.music;
		}
		
		
		
		
		SourceFile sourFile=new SourceFile(UUID.randomUUID().toString(), fileUrl, new Date(), size, userId, fileType, 0);
		MyFile myFile=new MyFile(UUID.randomUUID().toString(), userId, filename, path+"/"+filename, 1, sourFile);
		myFile.setCreateTime(new Date());
		
		//MyFile
		
		return myFile;
		
	}
	
	
	
	
	

}
