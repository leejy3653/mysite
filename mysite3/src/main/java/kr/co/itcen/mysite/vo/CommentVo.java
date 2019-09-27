package kr.co.itcen.mysite.vo;

public class CommentVo {

	private Long no;
	private String comment;
	private Long board_no;
	private Long user_no;
	private String username;
	private int o_no;
	private int g_no;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBoard_no() {
		return board_no;
	}

	public void setBoard_no(Long board_no) {
		this.board_no = board_no;
	}

	public Long getUser_no() {
		return user_no;
	}

	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getO_no() {
		return o_no;
	}

	public void setO_no(int o_no) {
		this.o_no = o_no;
	}

	public int getG_no() {
		return g_no;
	}

	public void setG_no(int g_no) {
		this.g_no = g_no;
	}

	@Override
	public String toString() {
		return "CommentVo [no=" + no + ", comment=" + comment + ", board_no=" + board_no + ", user_no=" + user_no
				+ ", username=" + username + ", o_no=" + o_no + ", g_no=" + g_no + "]";
	}
}
