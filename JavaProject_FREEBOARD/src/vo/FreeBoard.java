package vo;

public class FreeBoard {
	 private int board_no;
	 private String title;
	 private String content;
	 private int mem_no;
	 private String write_date;
	 private int count;
	 private String spare1;
	 
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMem_no() {
		return mem_no;
	}
	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getSpare1() {
		return spare1;
	}
	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}
	@Override
	public String toString() {
		return "FreeBoard [board_no=" + board_no + ", title=" + title + ", content=" + content + ", mem_no=" + mem_no
				+ ", write_date=" + write_date + ", count=" + count + ", spare1=" + spare1 + "]";
	}
}
