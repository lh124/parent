package imageserver;

import imageserver.util.JsonResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author 刘琪
 * @class_name: DeleteFileServlet
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-07-09 13:46
 */
@WebServlet({"/deletefileServlet"})
public class DeleteFileServlet extends HttpServlet {
    private static final long serialVersionUID = -6579223296666346580L;
    private static Logger logger = Logger.getLogger(DeleteFileServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.info("***************deletefileServlet******************");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            String realPath = request.getSession().getServletContext().getRealPath("/");
            String filepath = request.getParameter("fUrl");
            String serverRoot = request.getRequestURL().toString();
            int pos = serverRoot.lastIndexOf("/") + 1;
            serverRoot = serverRoot.substring(0, pos);
            filepath = filepath.replace(serverRoot, "");
            String serverPath = realPath + filepath;
            serverPath = serverPath.replaceAll("\\\\", "/");
            File file = new File(serverPath);
            if (!file.exists()) {
                logger.info("对不起，您要删除的文件已经不存在");
                response.getWriter().print(new JsonResult(-1, "对不起，您要删除的文件已经不存在").toString());
                return;
            }
            if (!file.isDirectory()) {
                file.delete();
                logger.info("删除文件成功 ...");
                response.getWriter().print(new JsonResult(1, "删除文件成功").toString());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("删除文件失败 ...");
            response.getWriter().print(new JsonResult(-1, "删除文件失败").toString());
        }
    }

    public void init() throws ServletException {
        super.init();
    }
}
