package session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author:L1ANN
 * @Description:
 * @Date:Created in 10:35 2017/11/10
 * @Modified By:
 */
@WebServlet(name = "IndexServlet")
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;UTF-8");
        PrintWriter out = response.getWriter();
        //创建Session
        request.getSession();
        out.println("The Book List:<br/>");
        Set<Map.Entry<String, Book>> set = DB.getAll().entrySet();

        for (Map.Entry<String, Book> me : set) {
            Book book = me.getValue();
            String url = "Buy.do?id=" + book.getId();
            url = response.encodeURL(url);
            out.println(book.getName() + "<a href='" + url + "'>buy</a><br>");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

class DB {
    private static Map<String, Book> map = new LinkedHashMap<>();

    static {
        map.put("1", new Book("1", "javaweb开发"));
        map.put("2", new Book("2", "spring开发"));
        map.put("3", new Book("3", "hibernate开发"));
        map.put("4", new Book("4", "structs开发"));
        map.put("5", new Book("5", "ajax开发"));

    }

    public static Map<String, Book> getAll() {
        return map;
    }
}

class Book {
    private String id;
    private String name;

    public Book() {
        super();
    }

    public Book(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}