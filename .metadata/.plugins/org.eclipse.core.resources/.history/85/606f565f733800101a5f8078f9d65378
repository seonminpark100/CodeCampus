package model2.mvcboard;

import java.io.IOException;
import java.io.PrintWriter;

import fileupload.FileUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.JSFunction;

public class WriteController extends HttpServlet
{
	private static final long serialVersionUID = 1L; // 없으면 경고 뜸
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		HttpSession session = req.getSession();
        String id = (String) session.getAttribute("id");

        if (id == null) {
            // alert 띄우고 login.do로 이동
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<script>");
            out.println("alert('로그인 후 이용 가능합니다.');");
            out.println("location.href='../mvcboard/login.do';");
            out.println("</script>");
            return;
        }
        
     // 로그인 되어 있으면 글쓰기 화면으로 포워드
        req.setAttribute("id", id);
//		단순 포워드 기능
		req.getRequestDispatcher("/14MVCBoard/Write.jsp").forward(req, resp);
	}
	
	//클라이언트가 작성한 내용 DB 저장, 파일 업로드 처리
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		String saveDirectory = req.getServletContext().getRealPath("Uploads");
		
		String originalFileName = "";
		
		try
		{
			originalFileName = FileUtil.uploadFile(req, saveDirectory);
		} catch (Exception e)
		{
			JSFunction.alertLocation(resp, "파일업로드 오류 입니다.", "../mvcboard/write.do");
			return; // return 을 통해 반드시 실행을 중지시켜줘야 함.
		}
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setName(req.getParameter("name"));
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setPass(req.getParameter("pass"));
		
		if(originalFileName != "") {
//			첨부파일이 있을 경우 파일명 변경
			String savedFileName = FileUtil.renameFile(saveDirectory, originalFileName);
			
			dto.setOfile(originalFileName);
			dto.setSfile(savedFileName);
		}
		
//		 새로운 게시물을 테이블에 입력
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.insertWrite(dto);
		dao.close(); // 커넥션풀 자원반납
		
//		서블릿에서 특정 요청명으로 이동할 때는 sendRedirect 사용
		if(result == 1) {
			resp.sendRedirect("../mvcboard/list.do");
		} else {
			JSFunction.alertLocation(resp, "글쓰기에 실패했습니다.", "../mvcboard/write.do");
		}
	}
}
