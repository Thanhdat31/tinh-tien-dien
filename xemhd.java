import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.HoaDonDAO; 
import DAO.menu;

@WebServlet("/xemhd") // Change the servlet name
public class xemhd extends HttpServlet {
    private static final long serialVersionUID = 1L;
    HoaDonDAO hoaDonDAO = new HoaDonDAO(); // Change to HoaDonDAO

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

        out.println("<h2>Tìm Kiếm Hóa Đơn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchSoHoaDon'>Số Hóa Đơn:</label>");
        out.println("  <input type='text' id='searchSoHoaDon' name='searchSoHoaDon' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Nhập Dữ Liệu Hóa Đơn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoHoaDon'>Số Hóa Đơn:</label>");
        out.println("  <input type='text' id='SoHoaDon' name='SoHoaDon' required><br>");

        out.println("  <label for='Thang'>Tháng:</label>");
        out.println("  <input type='text' id='Thang' name='Thang' required><br>");

        out.println("  <label for='MaDienThe'>Mã Điện Kế:</label>");
        out.println("  <input type='text' id='MaDienKe' name='MaDienKe' required><br>");

        out.println("  <label for='ThanhTien'>Thành Tiền:</label>");
        out.println("  <input type='text' id='ThanhTien' name='ThanhTien' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        String searchSoHoaDon = req.getParameter("searchSoHoaDon");
        if (searchSoHoaDon == null) {
            hoaDonDAO.displayData(out);
        } else {
            hoaDonDAO.TK(req, res, out);
        }
        hoaDonDAO.xoa(req, res, out);
        hoaDonDAO.nhap(req, res);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   
               response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");

        out.println("<h2>Nhập Dữ Liệu Hóa Đơn</h2>");
        out.println("<form method='post'>");

        out.println("  <label for='Thang'>Tháng:</label>");
        out.println("  <input type='text' id='Thang' name='Thang' required><br>");

        out.println("  <label for='MaDienKe'>Mã Điện Kế:</label>");
        out.println("  <input type='text' id='MaDienKe' name='MaDienKe' required><br>");

        out.println("  <label for='ThanhTien'>Thành Tiền:</label>");
        out.println("  <input type='text' id='ThanhTien' name='ThanhTien' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        String Thang = request.getParameter("Thang");
        String MaDienKe = request.getParameter("MaDienKe");
        String ThanhTien = request.getParameter("ThanhTien");
        if (Thang != null && MaDienKe != null && ThanhTien != null) {
            hoaDonDAO.sua(request, response, out);
        }
    }
}
