package servlet;

import domain.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 15:26 2017/12/13
 * @Modified By:
 */
@WebServlet(name = "UploadServlet", urlPatterns = "/Upload.do")
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到上传文件的保存目录，将上传的文件存放在WEB-INF目录下，不允许外界直接访问
        String savePath = "C:\\Git\\pictures\\";
        //上传文件时生成的临时文件保存目录
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File tempFile = new File(tempPath);
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }
        //消息提示
        String message = "";
        try {
            //1.创建DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置工厂缓冲区大小，当上传的文件大小超过缓冲区的 大小时，就会生成一个临时文件存放到指定的临时文件中
            factory.setSizeThreshold(1024 * 10);
            //设置上传时的临时文件的保存目录
            factory.setRepository(tempFile);

            //2.创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传的进度
            upload.setProgressListener(new ProgressListener() {
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    System.out.println("文件大小为：" + pContentLength + "，当前已经处理：" + pBytesRead);
                }
            });
            //解决上传文件名乱码
            upload.setHeaderEncoding("UTF-8");

            //3.判断提交上来的数据是否是上传表单数据
            if (!ServletFileUpload.isMultipartContent(request)) {
                //按照传统方式获取数据
                return;
            }

            //设置上传单个文件大小的最大值，目前设置为1024*1024字节，也就是1MB
            upload.setFileSizeMax(1024 * 1024);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值和，设置为10M
            upload.setSizeMax(1024 * 1024 * 10);

            //4.使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，其中每一个FileItem对应一个表单输入项
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                //如果fileItem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    System.out.println(name + "=" + value);
                } else {
                    //如果fileItem封装的是上传文件
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    //由于不同浏览器提交的文件名不同，有的会带有路径，有的只是单纯的文件名，所以㤇对filename处理
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    //获得上传文件的扩展名
                    String fileExName = filename.substring(filename.lastIndexOf(".") + 1);
                    //获取Item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //得到唯一文件名：调用makeFileName()方法，生成UUID_源文件名称
                    String saveFileName = makeFileName(filename);

                    //将唯一文件名保存到数据库
                    UserService service = new UserService();
                    User user = new User();
                    user.setUsername("admin");
                    user.setPic(saveFileName);
                    service.updaePic(user);
                    //创建一个保存文件的输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + saveFileName);
                    //创建一个缓冲区
                    byte[] buffer = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区中，如果((len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传成功！";
                }
            }

        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            e.printStackTrace();
            request.setAttribute("message", "单个文件超出最大值！");
            request.getRequestDispatcher("/message.jsp").forward(request, response);
            return;
        } catch (FileUploadBase.SizeLimitExceededException e) {
            e.printStackTrace();
            request.setAttribute("message", "上传文件总的大小超出限制的最大值");
        } catch (Exception e) {
            message = "文件上传失败！";
            e.printStackTrace();
        }
        request.setAttribute("message", message);
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    private String makeFileName(String filename) {
        //为了防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
        return UUID.randomUUID().toString() + "_" + filename;
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
