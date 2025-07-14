package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.DBConnPool;

public class MVCBoardDAO extends DBConnPool
{

	public MVCBoardDAO()
	{
		super();
	}
	
//	게시물 개수 조회
	public int selectCount(Map<String, Object> map) {
		
		int totalCount = 0; 
		String query = "SELECT COUNT(*) FROM mvcboard";
		
//		검색한 단어가 있다면 WHERE 절로 추가
		if(map.get("searchWord") != null) 
		{
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}
		try
		{
			stmt = con.createStatement(); // 쿼리문 생성
			rs = stmt.executeQuery(query); // 쿼리문 실행
			rs.next(); 
			totalCount = rs.getInt(1); // 검색된 게시물 개수 저장
		} catch (Exception e)
		{
			System.out.println("게시물 카운트 중 예외발생");
			e.printStackTrace();
		}
		return totalCount; // 게시물 개수를 서블릿으로 반환
	}
	
	
//	페이징 기능
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map){
		List<MVCBoardDTO> board = new Vector();
		String query = "SELECT * FROM ( "
				+ "  SELECT Tb.*, ROWNUM rNum FROM ( "
				+ "    SELECT * FROM mvcboard ";
//		검색 조건이 있다면 WHERE 절로 추가
		if(map.get("searchWord") != null) 
		{
			query += " WHERE " + map.get("searchField") 
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += "    ORDER BY idx DESC "
				+ "    ) Tb "
				+ "  ) "
				+ " WHERE rNum BETWEEN ? AND ?"; // 게시물 구간은 인파라미터로 설정
		
		try
		{
			psmt = con.prepareStatement(query); // 동적쿼리문생성
			psmt.setString(1, map.get("start").toString()); // 인파라미터 값 설정
			psmt.setString(2, map.get("end").toString()); // 인파라미터 값 설정
			rs = psmt.executeQuery(); // 쿼리문 실행
			
//			결과값 끝날떄까지 dto setter 설정
			while(rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
				
				board.add(dto);
			}
			
		} catch (Exception e)
		{
			System.out.println("게시물 조회(페이징) 중 예외발생");
			e.printStackTrace();
		}
		return board; // 목록 반환
	}
	
//	글쓰기 처리 시 첨부파일까지 함께 입력
	public int insertWrite(MVCBoardDTO dto) {
		int result = 0;
		
		try
		{
			String query = "INSERT INTO mvcboard ( "
					+ " idx, name, title, content, ofile, sfile, pass) "
					+ " VALUES ("
					+ " seq_board_num.NEXTVAL, ?, ?, ?, ?, ?, ?)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass()); // 수정 삭제를 위한 인증 비밀번호
			
			result = psmt.executeUpdate(); //업데이트 갯수를 결과값에 넣기
			
		} catch (Exception e)
		{
			System.out.println("게시물 입력 중 예외발생");
			e.printStackTrace();
		}
		
		return result; // 행의 갯수의 값을 jsp 로 반환
	}
	
	
	public MVCBoardDTO selectView(String idx) {
		MVCBoardDTO dto = new MVCBoardDTO(); // dto 객체 생성
		String query = "SELECT * FROM mvcboard WHERE idx=?"; // 인파라미터?가 있으므로 psmt 준비하기
		
		try
		{
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx); // 인파라미터 설정
			rs = psmt.executeQuery(); // 쿼리문 실행
			
//			 결과를 DTO객체에 저장
			if(rs.next()) {
//				위의 메소드에서 찾아서 복사하기
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}
		} catch (Exception e)
		{
			System.out.println("게시물 상세보기 중 예외발생");
			e.printStackTrace();
		}
		return dto;
	}
	
	public void updateVisitCount(String idx) {
		String query = "UPDATE mvcboard SET "
				+ " visitcount = visitcount+1 "
				+ " WHERE idx=? ";
		
		try
		{
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeUpdate(); // 하나일 떄는 executeUpdate 사용 
//			psmt.executeQuery();
			
		} catch (Exception e)
		{
			System.out.println("게시물 조회수 증가 중 예외발생");
			e.printStackTrace();
		}
	}
	
	public void downCountPlus(String idx) {
//		파일다운로드 횟수 1 증가 
		String sql = "UPDATE mvcboard SET "
				+ " downcount = downcount+1 "
				+ " WHERE idx = ? ";
		
		try
		{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate(); // 하나일 떄는 executeUpdate 사용 
//			psmt.executeQuery();
			
		} catch (Exception e)
		{
			System.out.println("다운로드 카운트 증가 중 예외발생");
			e.printStackTrace();
		}
		
	}
	
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorr = true;
		
		try
		{
			String sql = "SELECT COUNT(*) FROM mvcboard WHERE pass=? AND idx=?";
			
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			
			rs = psmt.executeQuery();
			rs.next();
			
			if(rs.getInt(1) == 0) {
				isCorr = false;
			}
			
		} catch (Exception e)
		{
			isCorr = false;
			e.printStackTrace();
		}
		
		return isCorr;
	}
	
//	지정한 일련번호의 게시물을 삭제
	public int deletePost(String idx) {
		int result = 0;
		try
		{
			String query = "DELETE FROM mvcboard WHERE idx=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			result = psmt.executeUpdate(); // 정상적으로 삭제되면 1을 반환
			
		} catch (Exception e)
		{
			System.out.println("게시물 삭제 중 예외발생");
			e.printStackTrace();
		}
		return result;
	}
	
	public int updatePost(MVCBoardDTO dto) {
		int result = 0;
		try
		{
//			특정 일련번호에 해당하는 게시물 수정 
			String query = "UPDATE mvcboard SET "
					+ " title =?, name =?, content=?,  ofile=? , sfile= ? "
					+ " WHERE idx=? and pass=?"; // 일련번호와 비밀번호 둘 다 일치해야 수정됨
			psmt = con.prepareStatement(query); // 쿼리문 준비 
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getIdx());
			psmt.setString(7, dto.getPass());
			
			result = psmt.executeUpdate(); // 행을 반납해야하므로 executeUpdate() 사용하여 쿼리문 실행
			
			
		} catch (Exception e)
		{
			System.out.println("게시물 수정 중 예외발생");
			e.printStackTrace();
		}
		
		return result;
	}
//	회원가입 처리 
	public int signUpProceed(MVCBoardDTO dto) {
		int result = 0;
		try
		{
			String sql = "INSERT INTO member (id, pass, name, regidate) "
			           + "VALUES (?, ?, ?, sysdate)";

			
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPass());
			psmt.setString(3, dto.getName());
			
			result = psmt.executeUpdate();
			
		} catch (Exception e)
		{
			System.out.println("회원가입 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	
//	로그인 프로세스
	public MVCBoardDTO getMVCBoardDTO(String id, String pass) {
		MVCBoardDTO dto = new MVCBoardDTO();
		String query = "SELECT * FROM member WHERE id=? AND pass=?";
		try
		{
			psmt = con.prepareStatement(query);
			psmt.setString(1, id);
			psmt.setString(2,  pass ) ;
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getDate(4));
				
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return dto;
	}
	
//	아이디 중복 체크
	
}
