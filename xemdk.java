import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.DienKeDAO;
import DAO.menu;

@WebServlet("/xemdk")
public class xemdk extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DienKeDAO in = new DienKeDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Xem Điện Kế</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif;}");
        out.println("table { border-collapse: collapse; width: 80%; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        menu menu = new menu();
        menu.htmenu(res, out);

        out.println("<h2>Tìm Kiếm Điện Kế</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaDienKe'>Mã Điện Kế:</label>");
        out.println("  <input type='text' id='searchMaDienKe' name='searchMaDienKe' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Nhập Dữ Liệu Điện Kế</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaDienKe'>Mã Điện Kế:</label>");
        out.println("  <input type='text' id='MaDienKe' name='MaDienKe' required><br>");

        out.println("  <label for='HieuDienThe'>Hiệu Điện Thế:</label>");
        out.println("  <input type='text' id='HieuDienThe' name='HieuDienThe' required><br>");

        out.println("  <label for='MaKH'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='MaKH' name='MaKH' required><br>");

        out.println("  <label for='MaKhuVuc'>Mã Khu Vực:</label>");
        out.println("  <input type='text' id='MaKhuVuc' name='MaKhuVuc' required><br>");

        out.println("  <label for='NuocSanXuat'>Nước Sản Xuất:</label>");
        out.println("  <input type='text' id='NuocSanXuat' name='NuocSanXuat' required><br>");

        out.println("  <label for='GhiChu'>Ghi Chú:</label>");
        out.println("  <input type='text' id='GhiChu' name='GhiChu' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        in.displayData(out);
        in.xoa(req, res, out);
        in.nhap(req, res);

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");

        String MaDienKe = request.getParameter("MaDienKe");
        out.println("<h2>Nhập Dữ Liệu Điện Kế</h2>");
        out.println("<form method='post'>");

        out.println("  <input type='hidden' name='MaDienKe' value='" + MaDienKe + "'/>");

        out.println("  <label for='HieuDienThe'>Hiệu Điện Thế:</label>");
        out.println("  <input type='text' id='HieuDienThe' name='HieuDienThe' required><br>");

        out.println("  <label for='MaKH'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='MaKH' name='MaKH' required><br>");

        out.println("  <label for='MaKhuVuc'>Mã Khu Vực:</label>");
        out.println("  <input type='text' id='MaKhuVuc' name='MaKhuVuc' required><br>");

        out.println("  <label for='NuocSanXuat'>Nước Sản Xuất:</label>");
        out.println("  <input type='text' id='NuocSanXuat' name='NuocSanXuat' required><br>");

        out.println("  <label for='GhiChu'>Ghi Chú:</label>");
        out.println("  <input type='text' id='GhiChu' name='GhiChu' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        in.sua(request, response, out);
    }
}

