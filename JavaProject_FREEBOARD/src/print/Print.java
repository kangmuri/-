package print;

import java.util.List;
import vo.FreeBoard;

public class Print {
	
	public void printVar() {
		System.out.println("----------------------------------------");
	}
	public void printVar(String s) {
		System.out.println("-----------------"+s+"-----------------------");
	}

	public void printLn(int n) {
		for(int i=0; i<n; i++) {
			System.out.println();
		}
	}
		public void printList(List<FreeBoard> freeList) {
			System.out.println("번호\t제목\t내용\t조회수\t작성자");
			for(FreeBoard freeBoard : freeList) {
			int no = freeBoard.getBoard_no();
		    String content = freeBoard.getContent();
		    System.out.println(no+"\t"+freeBoard.getTitle()+"\t"+content+"\t"+freeBoard.getCount()+"\t"+
		    freeBoard.getSpare1()+"\t"+freeBoard.getWrite_date());
			
		}
	}
	
	public void printListMenu() {
	System.out.println("1.다음페이지");
	System.out.println("2.이전페이지");
	System.out.println("3.상세페이지");
	System.out.println("4.홈");
		
	
	}
	public void printHome() {
		printVar();
		System.out.println("1. 자유게시판 전체 조회");
		System.out.println("2. 관리자 로그인");
		System.out.println("3. 로그아웃");
		printLn(2);//println 5번 출력
		printVar();
		
	}
}
