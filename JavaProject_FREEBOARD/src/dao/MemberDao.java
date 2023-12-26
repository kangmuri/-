package dao;

import java.util.List;

import util.JDBCUtil;
import vo.MemberVo;

public class MemberDao {
	private static MemberDao instance = null;

	private MemberDao() {
	}

	public static MemberDao getInstance() {
		if (instance == null) {
			instance = new MemberDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public MemberVo login(List<Object> params) {
		String sql = " select * "
				+ " from member_board "
				+ " where id=? and pass=? ";
				
		return jdbc.selectOne(sql,params,MemberVo.class);
	}

}
