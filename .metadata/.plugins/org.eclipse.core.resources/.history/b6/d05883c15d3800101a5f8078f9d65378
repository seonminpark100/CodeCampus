package model2.mvcboard;

import java.io.Console;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

@WebServlet("/mvcboard/login.do")
public class LoginController extends HttpServlet
{
	private static final long serialVersionUID = 1L; // 없으면 경고 뜸
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/14MVCBoard/Login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
	        // 입력 값 추출
	        String id = req.getParameter("id");
	        String pass = req.getParameter("pass");

	        // DAO 호출
	        MVCBoardDAO dao = new MVCBoardDAO();
	        MVCBoardDTO result = dao.getMVCBoardDTO(id, pass); // 메서드명 가정
	        dao.close();
	        
	        if (result.getId() != null) {
	            // 로그인 성공 → 목록 페이지로 리다이렉트
	        	HttpSession session = req.getSession();
	        	session.setAttribute("id", id);
	        	JSFunction.alertLocation(resp, id + "님 로그인 하셨습니다.", "../mvcboard/list.do");
	        } else {
	            // 실패 → 경고 후 다시 로그인 페이지로 이동
	            JSFunction.alertLocation(resp, "로그인에 실패했습니다.", "../mvcboard/login.do");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
