package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

@WebServlet("/mvcboard/logout.do")
public class LogoutController extends HttpServlet
{
	private static final long serialVersionUID = 1L; // 없으면 경고 뜸
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.getRequestDispatcher("/14MVCBoard/Logout.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		try
		{
				
			HttpSession session = req.getSession();
			session.removeAttribute("id");
	     	session.invalidate();
//	     	resp.sendRedirect("../mvcboard/default.do");
	     	
	     	JSFunction.alertLocation(resp, "로그아웃 하였습니다.", "../mvcboard/list.do");
	     	
	     	
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
