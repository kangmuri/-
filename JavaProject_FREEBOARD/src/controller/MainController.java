package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import print.Print;
import service.FreeBoardService;
import service.MemberService;
import util.ScanUtil;
import util.View;
import vo.FreeBoard;
import vo.MemberVo;

public class MainController extends Print {
	static public Map<String, Object> sessionStorage = new HashMap<>();
	
	FreeBoardService freeService = FreeBoardService.getInstance();
	MemberService memService = MemberService.getInstance();
	
	public static void main(String[] args) {
		sessionStorage.put("page_no",1);
		new MainController().start();
	}
	
	private void start() {
		View view = View.MAIN;
		while (true) {
			switch (view) {
			case MAIN:
				view = home();
				break;
			case ADMIN:
				view = home();
				break;
			case FREE_LIST:
				view = freeList();
				break;
			case FREE_DETAIL:
				view = freeDetail();
				break;
			case FREE_UPDATE:
				view = freeUpdate();
				break;
			case FREE_DELETE:
				view = freeDelete();
				break;
			case LOGIN:
				view = login();
				break;
			default:
				break;
			}
		}
	}


	private View login() {
		String id = ScanUtil.nextLine("id>>");
		String pass = ScanUtil.nextLine("pass>>");
		
		List<Object> param = new ArrayList();
		param.add(id);
		param.add(pass);
		
		boolean loginChk = memService.login(param);
		if(!loginChk) {
			System.out.println("1.다시로그인");
			System.out.println("2.회원가입");
			System.out.println("3.홈");
			int sel = ScanUtil.nextInt("메뉴선택>>");
			switch(sel) {
			case 1:
				return View.LOGIN;
			case 2:
				return View.ADMIN;
			case 3:
				return View.MAIN;
			default:
				return View.LOGIN;
			}
		}
		View view = (View) sessionStorage.get("View");
		if(view == null) return View.MAIN;
		
		return view;
	}

	private View freeUpdate() {
		
		/*
		 * 1. 로그인 먼저 시키기
		 * 1) 로그인이 되어있는지 확인하기: 되어있지 않다면 로그인 페이지로 이동
		 * 2) 로그인이 되어있다면 update페이지로 돌아와서 2.로 이동
		 * 
		 * 2. 게시글 작성자가 맞는지 확인하기
		 * 1) 작성자가 아니라면 상세페이지로 돌려보내주기
		 * 2) 작성자가 맞다면 마저 수정 진행할 수 있게 해주기
		 */
		
		MemberVo mem = (MemberVo) sessionStorage.get("member");
		if(mem==null) {
			sessionStorage.put("View",View.FREE_UPDATE);
			return View.LOGIN;
		}
		
		
		int board_no = (int) sessionStorage.get("board_no");
		FreeBoard fb = freeService.freeDetail(board_no);	
		
		if(fb.getMem_no() != mem.getMem_no()) {//***
			System.out.println("해당 게시글 작성자가 아닙니다.");
			return View.FREE_DETAIL;
		}		
		
		//이제 로그인도 했고 작성자가 맞는지 확인도 했다. 그러면 게시글 수정을 진행하자.
		List<Object> param = new ArrayList();
		String title = ScanUtil.nextLine("바꿀 제목을 입력해 주세요.");
		String content = ScanUtil.nextLine("바꿀 내용을 입력해 주세요.");
		param.add(title);
		param.add(content);
		freeService.updateBoard(param, board_no);

		return View.FREE_LIST;		
		
//		int no = (int) sessionStorage.get("page_no");
//		System.out.println("1.제목수정");
//		System.out.println("2.내용수정");
//		
//		int select = ScanUtil.nextInt("");
//		String col = null;
//		String colum = "";
//		if(select == 1) {
//			colum = "TITLE";
//			col = ScanUtil.nextLine("수정할 제목>>");
//		}
//		if(select == 2) {
//			colum = "CONTENT";
//			col = ScanUtil.nextLine("수정할 내용>>");
//		}
//		List<Object> param = new ArrayList();
//		param.add(col);
//		param.add(no);
//		
//		freeService.updateBoard(param, colum);
//		
//		return null;
	}

	private View freeDelete() {
		
		MemberVo mem = (MemberVo) sessionStorage.get("member");
		if(mem==null) {
			sessionStorage.put("View", View.FREE_DELETE);
			return View.LOGIN;
		}
	
		int board_no = (int) sessionStorage.get("board_no");
		FreeBoard fb = freeService.freeDetail(board_no);
		if(fb.getMem_no() != mem.getMem_no()) {//***
			System.out.println("해당게시글 작성자가 아닙니다.");
			return View.FREE_DETAIL;
		}
		
		freeService.freeDelete(board_no);
		
		return View.FREE_LIST;
	}
	
	
	private View freeDetail() {
		/*
		 * 상세페이지 출력
		 * 
		 * 메뉴문구
		 * 1.게시판 수정(로그인 진행내 게시판 아니라면 수정불가)
		 * 2.게시판 삭제(로그인 진행 내 게시판 아니라면 수정불가)
		 * 
		 */
		int board_no = (int)sessionStorage.get("board_no");
		FreeBoard fb = freeService.freeDetail(board_no);
		System.out.println(fb);
		
		System.out.println("1.게시판수정");
		System.out.println("2.게시판삭제");
		System.out.println("3.홈으로");
		
		int sel = ScanUtil.nextInt("메뉴 선택: ");
		switch (sel) {
		case 1:
			sessionStorage.put("board_no",board_no);
			return View.FREE_UPDATE;
		case 2:
			sessionStorage.put("board_no",board_no);
			return View.FREE_DELETE;
		case 3:
			return View.MAIN;
		default:
			return View.FREE_DETAIL;
	    }
	
	}

	private View freeList() {
		//전체게시판 조회
		List<Object> param = new ArrayList();
		int page_no = (int)sessionStorage.get("page_no");
		int start_no = 1+5*(page_no-1);
		int end_no = 5+5*(page_no-1);
		param.add(start_no);
		param.add(end_no);
		
		List<FreeBoard> freeList = freeService.freeList(param);
		printList(freeList);
		printListMenu();
		//int no = freeBoard.getBoard_no -->원래는 이런식으로
		
		
		
		int sel = ScanUtil.nextInt("메뉴 선택: ");
		switch (sel) {
		case 1:
			page_no++;
			sessionStorage.put("page_no", page_no);
			return View.FREE_LIST;
		case 2:
			page_no--;
			sessionStorage.put("page_no", page_no);
			return View.FREE_LIST;
		case 3:
			int board_no = ScanUtil.nextInt("게시글 번호를 입력하시오.");
			sessionStorage.put("board_no",board_no);
			return View.FREE_DETAIL;
		
		default:
			return View.MAIN;
		
	     }
	}


	private View home() {
		printHome();
		
		int sel = ScanUtil.nextInt("메뉴 선택 : ");
		switch (sel) {
		case 1:
			return View.FREE_LIST;
		case 2:
			return View.ADMIN;
		case 3:
			sessionStorage.remove("member");
			return View.MAIN;
		
		default:
			return View.MAIN;
		}
	}
}
