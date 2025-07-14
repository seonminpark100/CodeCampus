package model2.mvcboard;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/mvcboard/idcheck.do")
public class IdCheckController extends HttpServlet
{ 
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String id = req.getParameter("id");
		MVCBoardDAO dao = new MVCBoardDAO();
		
		int result = dao.confirmID(id);
		req.setAttribute("id", id);
		req.setAttribute("result", result); // 중복이 있으면 1, 없으면 -1
		
		req.getRequestDispatcher("/14MVCBoard/IdCheck.jsp").forward(req, resp);
	}
}
