package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.BoardPage;

public class ListController extends HttpServlet
{
	private static final long serialVersionUID = 1L; // 없으면 경고 뜸
	
//	수정 페이지로 진입해서 기존 내용을 수정폼에 설정함.
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		MVCBoardDAO dao = new MVCBoardDAO(); // DAO 객체 생성을 통해 커넥션풀로 DB에 연결
		
		Map<String, Object> map = new HashMap<String, Object>();
		
//		request 내장 객체를 통해 파라미터를 가져옴
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");

		/* 사용자의 입력한 검색어가 있다면 */
		if(searchWord != null)
		{
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		int totalCount = dao.selectCount(map);

		
		/* 페이징 추가 */
		ServletContext application = getServletContext();
		
//		web.xml에 설정한 파라미터 가져오기
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		
//		현재 페이지 확인
		int pageNum = 1;
		String pageTemp = req.getParameter("pageNum"); // 파라미터로 넘어오는 pageNum 이 있다면 값을 얻어옴.
		if(pageTemp != null && !pageTemp.equals("")){
			pageNum = Integer.parseInt(pageTemp); /* 요청 받은 페이지로 수정 */
		} 
//		목록에 출력할 게시물 범위 계산
		int start = (pageNum -1 ) * pageSize +1; // 첫번째 게시물 번호
		int end = pageNum * pageSize; // 마지막 게시물 번호
		map.put("start", start);
		map.put("end", end);
		/* 페이징 끝 */

		List<MVCBoardDTO> boardLists = dao.selectListPage(map);

		dao.close();
		
		// 뷰에 전달할 매개변수 추가 
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "..//mvcboard/list.do");
//		View 로 전달할 데이터를 map에 put(저장)한다.
		map.put("pagingImg", pagingImg); // 목록 하단에 출력할 페이지 번호
		map.put("totalCount", totalCount);  // 전체 게시물의 갯수
		map.put("pageSize", pageSize); // 한 페이지당 출력할 게시물의 갯수(설정값)
		map.put("pageNum", pageNum); // 현재 페이지 번호
		
//		View 로 전달할 객체들을 request 영역에 생성 
		req.setAttribute("boardLists", boardLists); 
		req.setAttribute("map", map);
//		View 로 포워드 해준다.
		req.getRequestDispatcher("/14MVCBoard/List.jsp").forward(req, resp);
	}
}
