package vo;

public class MemberPageInfo {
// 상품 목록 페이지에 필요한 데이터들을 저장하는 클래스
	
	// 현재 page번호, 페이지수, 시작페이지, 종료페이지, 회원수, 블록크기, 페이지크기
	private int cpage, pcnt, spage, epage, rcnt, bsize, psize;
	
	
	// 검색조건 : 회원상태, 키워드(상품명, 상품아이디), 성별
	private String schtype, keyword, gender;
	
	// 정렬조건 : 가입일순date(오a내d), 최종로그인순login(오a내d)
	private String ord;

	public int getCpage() {
		return cpage;
	}

	public void setCpage(int cpage) {
		this.cpage = cpage;
	}

	public int getPcnt() {
		return pcnt;
	}

	public void setPcnt(int pcnt) {
		this.pcnt = pcnt;
	}

	public int getSpage() {
		return spage;
	}

	public void setSpage(int spage) {
		this.spage = spage;
	}

	public int getEpage() {
		return epage;
	}

	public void setEpage(int epage) {
		this.epage = epage;
	}

	public int getRcnt() {
		return rcnt;
	}

	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}

	public int getBsize() {
		return bsize;
	}

	public void setBsize(int bsize) {
		this.bsize = bsize;
	}

	public int getPsize() {
		return psize;
	}

	public void setPsize(int psize) {
		this.psize = psize;
	}

	public String getSchtype() {
		return schtype;
	}

	public void setSchtype(String schtype) {
		this.schtype = schtype;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}	
	
	
}
