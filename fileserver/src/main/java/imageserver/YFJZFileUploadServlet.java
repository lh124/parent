package imageserver;

import imageserver.util.FileUtils;
import imageserver.util.JsonResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @class_name: YFJZFileUploadServlet
 * @describe: 本sevlet只支持上传mp4格式的文件
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/7/11  15:04
 **/
@WebServlet("/yfjzfileuploadServlet")
public class YFJZFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -6579223296666346580L;
	private static Logger logger = Logger.getLogger(YFJZFileUploadServlet.class);
	private static final long FILESIZE = 1000 * 1024 * 1024;// 文件大小1G设置
	private static final String[] fileTypeArr = {"mp4"};

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			logger.info("进入文件上传中...");
			// 因为要通过response.getWriter().print(json字符串);将json字符串写出去，为了防止中文乱码，需要设置返回的编码
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");// 设置响应编码
			int index = request.getRequestURL().lastIndexOf("/") + 1;
			String rootPath = request.getRequestURL().substring(0, index);
			RequestContext req = new ServletRequestContext(request);
			if (FileUpload.isMultipartContent(req)) {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload fileUpload = new ServletFileUpload(factory);
				fileUpload.setFileSizeMax(FILESIZE);
				List<FileItem> items = fileUpload.parseRequest(request);
				 
				for (FileItem fileItem : items) {
					if (!fileItem.isFormField()) {
						if (fileItem.getName() != null && fileItem.getSize() != 0) {
							String realPath = request.getSession().getServletContext().getRealPath("/");
							String realFileName = fileItem.getName();
							int pos = realFileName.lastIndexOf(".");
							String fileType = realFileName.substring(pos + 1).toLowerCase();
							boolean isLegal = FileUtils.checkFileType(fileTypeArr, fileType);
							if (!isLegal) {
								logger.info("该文件格式[" + fileType + "]不允许上传！请联系管理员");
								response.getWriter().print(new JsonResult(-1, "该文件格式[" + fileType + "]不允许上传！请联系管理员").toString());
								return;
							}
							String newFilePath = "files" + File.separator;
							String storeFileName = newFilePath + FileUtils.getRandomfileName() + realFileName.substring(pos);
							File storeFile = new File(realPath + newFilePath);
							if (!storeFile.exists()) {
								storeFile.mkdirs();
							}
							File newFile = new File(realPath + storeFileName);
							fileItem.write(newFile);
							response.getWriter().print(new JsonResult(1, (rootPath + storeFileName).replaceAll("\\\\", "/")).toString());
							logger.info("服务器存放路径{}" + ((rootPath + storeFileName).replaceAll("\\\\", "/")).toString());
						} else {
							logger.info("没有需要上传的文件，请选择需要上传的文件！");
							response.getWriter().print(new JsonResult(-1, "没有需要上传的文件，请选择需要上传的文件！").toString());
							return;
						}
					}
				}
			}
			logger.info("文件上传结束");
		} catch (FileUploadException e) {
			e.printStackTrace();
			logger.info("您所选择的文件大于1G，请选择小于1G的文件上传");
			response.getWriter().print(new JsonResult(-1, "您所选择的文件大于1G，请选择小于1G的文件上传").toString());
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("服务器异常，请联系管理员！");
			response.getWriter().print(new JsonResult(-1, "服务器异常，请联系管理员！").toString());
			return;
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
}