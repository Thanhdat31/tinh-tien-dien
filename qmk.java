import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.userDAO;
@WebServlet("/qmk")
public class qmk extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public qmk() {
        super();
    }
    userDAO in = new userDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
           res.setContentType("text/html;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Form Nhập Dữ Liệu Người Dùng</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Form Nhập Dữ Liệu Người Dùng</h2>");
            out.println("<form method='get'>");
       
            out.println("Gmail: <input type=\"text\" name=\"gmail\"><br>");
            out.println("Password: <input type=\"password\" name=\"password\"><br>");
            
            out.println("  <input type='submit' value='Submit'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
            in.qmk(req, res, out);
           }

 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}