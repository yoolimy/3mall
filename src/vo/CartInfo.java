package vo;

public class CartInfo {
	private int cl_idx, cl_cnt, price;
	private String cl_buyer, pl_id, cl_opt, cl_sdate, cl_edate, cl_date, pl_name, pl_mainimg;
	
	public int getCl_idx() {
		return cl_idx;
	}
	public void setCl_idx(int cl_idx) {
		this.cl_idx = cl_idx;
	}
	public int getCl_cnt() {
		return cl_cnt;
	}
	public void setCl_cnt(int cl_cnt) {
		this.cl_cnt = cl_cnt;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCl_buyer() {
		return cl_buyer;
	}
	public void setCl_buyer(String cl_buyer) {
		this.cl_buyer = cl_buyer;
	}
	public String getPl_id() {
		return pl_id;
	}
	public void setPl_id(String pl_id) {
		this.pl_id = pl_id;
	}
	public String getCl_opt() {
		return cl_opt;
	}
	public void setCl_opt(String cl_opt) {
		this.cl_opt = cl_opt;
	}
	public String getCl_sdate() {
		return cl_sdate;
	}
	public void setCl_sdate(String cl_sdate) {
		this.cl_sdate = cl_sdate;
	}
	public String getCl_edate() {
		return cl_edate;
	}
	public void setCl_edate(String cl_edate) {
		this.cl_edate = cl_edate;
	}
	public String getCl_date() {
		return cl_date;
	}
	public void setCl_date(String cl_date) {
		this.cl_date = cl_date;
	}
	public String getPl_name() {
		return pl_name;
	}
	public void setPl_name(String pl_name) {
		this.pl_name = pl_name;
	}
	public String getPl_mainimg() {
		return pl_mainimg;
	}
	public void setPl_mainimg(String pl_mainimg) {
		this.pl_mainimg = pl_mainimg;
	}
}
