package service;

import java.util.List;

import dao.FreeBoardDao;
import vo.FreeBoard;

public class FreeBoardService {
	private static FreeBoardService instance = null;

	private FreeBoardService() {
	}

	public static FreeBoardService getInstance() {
		if (instance == null) {
			instance = new FreeBoardService();
		}
		return instance;
	}
	FreeBoardDao dao = FreeBoardDao.getInstance();
	

	public FreeBoard freeDetail(int board_no) {
		return dao.freeDetail(board_no);
	}

	public void updateBoard(List<Object> param, int board_no) {
		dao.updateBoard(param, board_no);
		
	}

	public List<FreeBoard> freeList(List<Object> param) {
		return dao.freeList(param);
	}

	public void freeDelete(int board_no) {
		dao.freeDelete(board_no);
	}
}
