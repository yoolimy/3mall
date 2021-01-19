package vo;

public class MemberAddrInfo {
	private String ml_id, ma_zip, ma_addr1, ma_addr2, ma_basic, ma_date;

	private int ma_idx;

	public String getMl_id() {
		return ml_id;
	}

	public void setMl_id(String ml_id) {
		this.ml_id = ml_id;
	}

	public String getMa_zip() {
		return ma_zip;
	}

	public void setMa_zip(String ma_zip) {
		this.ma_zip = ma_zip;
	}

	public String getMa_addr1() {
		return ma_addr1;
	}

	public void setMa_addr1(String ma_addr1) {
		this.ma_addr1 = ma_addr1;
	}

	public String getMa_addr2() {
		return ma_addr2;
	}

	public void setMa_addr2(String ma_addr2) {
		this.ma_addr2 = ma_addr2;
	}

	public String getMa_basic() {
		return ma_basic;
	}

	public void setMa_basic(String ma_basic) {
		this.ma_basic = ma_basic;
	}

	public String getMa_date() {
		return ma_date;
	}

	public void setMa_date(String ma_date) {
		this.ma_date = ma_date;
	}

	public int getMa_idx() {
		return ma_idx;
	}

	public void setMa_idx(int ma_idx) {
		this.ma_idx = ma_idx;
	}

	@Override
	public String toString() {
		return "MemberAddrInfo [ml_id=" + ml_id + ", ma_zip=" + ma_zip + ", ma_addr1=" + ma_addr1 + ", ma_addr2="
				+ ma_addr2 + ", ma_basic=" + ma_basic + ", ma_date=" + ma_date + ", ma_idx=" + ma_idx + "]";
	}

	
	
}
