package service;

import java.util.List;

import controller.MainController;
import dao.MemberDao;
import vo.MemberVo;

public class MemberService {
	private static MemberService instance = null;

	private MemberService() {
	}

	public static MemberService getInstance() {
		if (instance == null) {
			instance = new MemberService();
		}
		return instance;
	}
	
	MemberDao dao = MemberDao.getInstance();
	
	public boolean login(List<Object> param) {
		MemberVo mem = dao.login(param);
		if(mem != null) {
			MainController.sessionStorage.put("member", mem);
			return true;
		}
		return false;
	}
	
}
