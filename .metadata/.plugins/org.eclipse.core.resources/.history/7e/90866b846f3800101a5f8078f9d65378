package model2.mvcboard;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JSFunction;

@WebServlet("/mvcboard/signup.do")
public class SignUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 회원가입 페이지 진입
        req.getRequestDispatcher("/14MVCBoard/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            MVCBoardDTO dto = new MVCBoardDTO();
            dto.setId(req.getParameter("user_id"));
            dto.setName(req.getParameter("name"));
            dto.setPass(req.getParameter("pass"));

            MVCBoardDAO dao = new MVCBoardDAO();
            int result = dao.signUpProceed(dto);
            dao.close();

            if (result == 1) {
                resp.sendRedirect("../mvcboard/list.do");
            } else {
                JSFunction.alertLocation(resp, "회원가입 실패했습니다.", "../mvcboard/signup.do");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
