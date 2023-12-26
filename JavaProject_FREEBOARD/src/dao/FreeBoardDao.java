package dao;

import java.util.List;

import util.JDBCUtil;
import vo.FreeBoard;

public class FreeBoardDao {
	private static FreeBoardDao instance = null;

	private FreeBoardDao() {
	}

	public static FreeBoardDao getInstance() {
		if (instance == null) {
			instance = new FreeBoardDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public List<FreeBoard> freeList(List<Object> param) {
		String sql = "select *\r\n" + 
				"from (SELECT ROWNUM RN, A.*\r\n" + 
				"    FROM(select board_no, title, content, f.mem_no,\r\n" + 
				"     to_char(WRITE_DATE, 'yyyy-mm-dd') WRITE_DATE,\r\n" + 
				"     COUNT,m.NAME SPARE1\r\n" + 
				"     from free_board2 f, member_board m\r\n" + 
				"     where f.mem_no = m.mem_no AND DEL_YN = 'N' "
				+ "		order by 1) A)\r\n" + 
				"WHERE RN BETWEEN ? AND ? ";

		return jdbc.selectList(sql, param,FreeBoard.class);
	}

	public FreeBoard freeDetail(int board_no) {
		String sql = "select board_no, title, content, f.mem_no,\r\n" + 
				"       to_char(WRITE_DATE, 'yyyy-mm-dd') WRITE_DATE,\r\n" + 
				"       COUNT,m.NAME SPARE1\r\n" + 
				"from free_board2 f, member_board m\r\n" + 
				"where F.MEM_NO = M.MEM_NO\r\n" + 
				"AND BOARD_NO ="+board_no;
		
		return jdbc.selectOne(sql, FreeBoard.class);
	}

	public void updateBoard(List<Object> param, int board_no) {
		String sql = "UPDATE FREE_BOARD2\r\n" + 
				" SET TITLE = ? ,\r\n" + 
				"    CONTENT = ?\r\n" + 
				" WHERE BOARD_NO =" + board_no;
		
		jdbc.update(sql, param);
	}

	public void freeDelete(int board_no) {
		String sql = " update free_board2 "
				+ " set del_yn = 'Y' "
				+ " where board_no = "+board_no;
		
				
		jdbc.update(sql);
	}
	
	}
