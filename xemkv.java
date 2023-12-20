import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.KhuVucDAO;
import DAO.menu;

@WebServlet("/xemkv")
public class xemkv extends HttpServlet {
    private static final long serialVersionUID = 1L;
    KhuVucDAO khuVucDAO = new KhuVucDAO();

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

        out.println("<h2>Tìm Kiếm Khu Vực</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='searchMaKhuVuc'>Mã Khu Vực:</label>");
        out.println("  <input type='text' id='searchMaKhuVuc' name='searchMaKhuVuc' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Nhập Dữ Liệu Khu Vực</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaKhuVuc'>Mã Khu Vực:</label>");
        out.println("  <input type='text' id='MaKhuVuc' name='MaKhuVuc' required><br>");

        out.println("  <label for='TenKhuVuc'>Tên Khu Vực:</label>");
        out.println("  <input type='text' id='TenKhuVuc' name='TenKhuVuc' required><br>");

        out.println("  <label for='QuanHuyen'>Quận Huyện:</label>");
        out.println("  <input type='text' id='QuanHuyen' name='QuanHuyen' required><br>");
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

        String searchMaKhuVuc = req.getParameter("searchMaKhuVuc");
        if (searchMaKhuVuc == null) {
            khuVucDAO.displayData(out);
        } else {
            khuVucDAO.TK(req, res, out);
        }
        khuVucDAO.xoa(req, res, out);
        khuVucDAO.nhap(req, res);
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
        String MaKhuVuc = request.getParameter("MaKhuVuc");
        out.println("<h2>Form Nhập Dữ Liệu Khu Vực</h2>");
        out.println("<form method='post'>");

        out.println("  <input type='hidden' name='MaKhuVuc' value='" + MaKhuVuc + "'/>");
        out.println("  <label for='TenKhuVuc'>Tên Khu Vực:</label>");
        out.println("  <input type='text' id='TenKhuVuc' name='TenKhuVuc' required><br>");

        out.println("  <label for='QuanHuyen'>Quận Huyện:</label>");
        out.println("  <input type='text' id='QuanHuyen' name='QuanHuyen' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");

        String TenKhuVuc = request.getParameter("TenKhuVuc");
        String QuanHuyen = request.getParameter("QuanHuyen");
        if (TenKhuVuc != null && QuanHuyen != null) {
            khuVucDAO.sua(request, response, out);
        }
    }
}
