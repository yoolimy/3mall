package vo;

public class MemberPageInfo {
// ��ǰ ��� �������� �ʿ��� �����͵��� �����ϴ� Ŭ����
	
	// ���� page��ȣ, ��������, ����������, ����������, ȸ����, ���ũ��, ������ũ��
	private int cpage, pcnt, spage, epage, rcnt, bsize, psize;
	
	
	// �˻����� : ȸ������, Ű����(��ǰ��, ��ǰ���̵�), ����
	private String schtype, keyword, gender;
	
	// �������� : �����ϼ�date(��a��d), �����α��μ�login(��a��d)
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
