import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.ChiTietDAO;
import DAO.menu;

@WebServlet("/xemcthd")
public class xemcthd extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ChiTietDAO chiTietDAO = new ChiTietDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Chi Tiết Hóa Đơn</title>");
        out.println("</head>");
        out.println("<body>");
        
        menu menu = new menu();
        menu.htmenu(res, out);

        out.println("<h2>Tìm Kiếm Chi Tiết Hóa Đơn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchSoHoaDon'>Số Hóa Đơn:</label>");
        out.println("  <input type='text' id='searchSoHoaDon' name='searchSoHoaDon' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Nhập Dữ Liệu Chi Tiết Hóa Đơn</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoHoaDon'>Số Hóa Đơn:</label>");
        out.println("  <input type='text' id='SoHoaDon' name='SoHoaDon' required><br>");

        out.println("  <label for='MaDonGia'>Mã Đơn Giá:</label>");
        out.println("  <input type='text' id='MaDonGia' name='MaDonGia' required><br>");

        out.println("  <label for='SoLuongKW'>Số Lượng KW:</label>");
        out.println("  <input type='text' id='SoLuongKW' name='SoLuongKW' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

  
        String searchSoHoaDon = req.getParameter("searchSoHoaDon");
        if (searchSoHoaDon == null) {
            chiTietDAO.displayData(out);
        } else {
            // Add logic for searching ChiTiet based on Số Hóa Đơn
            // chiTietDAO.TK(req, res, out);
        }
        
        chiTietDAO.xoa(req, res, out);
        chiTietDAO.nhap(req, res);
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
        out.println("<title>Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String SoHoaDon = request.getParameter("SoHoaDon");
        out.println("<h2>Nhập Dữ Liệu Chi Tiết Hóa Đơn</h2>");
        out.println("<form method='post'>");

        out.println("  <input type='hidden' name='SoHoaDon' value='" + SoHoaDon + "'/>");
        out.println("  <label for='MaDonGia'>Mã Đơn Giá:</label>");
        out.println("  <input type='text' id='MaDonGia' name='MaDonGia' required><br>");

        out.println("  <label for='SoLuongKW'>Số Lượng KW:</label>");
        out.println("  <input type='text' id='SoLuongKW' name='SoLuongKW' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        String MaDonGia = request.getParameter("MaDonGia");
        String strSoLuongKW = request.getParameter("SoLuongKW");
        if (MaDonGia != null && strSoLuongKW != null) {
            int SoLuongKW = Integer.parseInt(strSoLuongKW);
            chiTietDAO.sua(request, response, out);
        }
    }
}
