import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.KhachDAO;
import DAO.menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@WebServlet("/xemds")
public class xemds extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public xemds() {
        super();
    }
    KhachDAO in = new KhachDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        
        menu menu = new menu();
        menu.htmenu(res, out);
        
        out.println("<h2>Tìm Kiếm Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaKH'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='searchMaKH' name='searchMaKH' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");
        
        out.println("<h2>Nhập Dữ Liệu Khách Hàng</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaKH'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='MaKH' name='MaKH' required><br>");
        
        out.println("  <label for='DiaChi'>Địa Chỉ:</label>");
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");
        
        out.println("  <label for='SoDienThoai'>Số Điện Thoại:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        out.println("</body>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        out.println("</body></html>");
        
        
        String searchMaKH = req.getParameter("searchMaKH");
        if (searchMaKH == null) {
            in.displayData(out);
        }
        else{
            in.TK(req, res, out);
        }
        in.xoa(req, res, out);
        in.nhap(req, res); 
               
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String MaKH = request.getParameter("MaKH");
        out.println("<h2>Nhập Dữ Liệu Thay Đổi Của Khách Hàng</h2>");
        out.println("<form method='post'>");
       
        out.println("  <input type='hidden' name='MaKH' value='" + MaKH + "'/>");
        out.println("  <label for='DiaChi'>Địa Chỉ:</label>");
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");

        out.println("  <label for='SoDienThoai'>Số Điện Thoại:</label>");
        out.println("  <input type='text' id='SoDienThoai' name='SoDienThoai' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
        String DiaChi = request.getParameter("DiaChi");
        String std = request.getParameter("SoDienThoai");
        if(DiaChi!=null && std !=null)
        {
        in.sua(request, response, out);
        }
        
    }
    
}
