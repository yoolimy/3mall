package vo;

import java.util.*;

public class OrderInfo {
	private String ol_id, ol_buyer, ol_rname, ol_rphone1, ol_rphone2, ol_rzip, ol_raddr1, ol_raddr2;
	private String ol_comment, ol_payment, ol_status, ol_rand, ol_return, ol_date;
	private int ol_pay;
	private ArrayList<OrderDetailInfo> detailList;
	public String getOl_id() {
		return ol_id;
	}
	public void setOl_id(String ol_id) {
		this.ol_id = ol_id;
	}
	public String getOl_buyer() {
		return ol_buyer;
	}
	public void setOl_buyer(String ol_buyer) {
		this.ol_buyer = ol_buyer;
	}
	public String getOl_rname() {
		return ol_rname;
	}
	public void setOl_rname(String ol_rname) {
		this.ol_rname = ol_rname;
	}
	public String getOl_rphone1() {
		return ol_rphone1;
	}
	public void setOl_rphone1(String ol_rphone1) {
		this.ol_rphone1 = ol_rphone1;
	}
	public String getOl_rphone2() {
		return ol_rphone2;
	}
	public void setOl_rphone2(String ol_rphone2) {
		this.ol_rphone2 = ol_rphone2;
	}
	public String getOl_rzip() {
		return ol_rzip;
	}
	public void setOl_rzip(String ol_rzip) {
		this.ol_rzip = ol_rzip;
	}
	public String getOl_raddr1() {
		return ol_raddr1;
	}
	public void setOl_raddr1(String ol_raddr1) {
		this.ol_raddr1 = ol_raddr1;
	}
	public String getOl_raddr2() {
		return ol_raddr2;
	}
	public void setOl_raddr2(String ol_raddr2) {
		this.ol_raddr2 = ol_raddr2;
	}
	public String getOl_comment() {
		return ol_comment;
	}
	public void setOl_comment(String ol_comment) {
		this.ol_comment = ol_comment;
	}
	public String getOl_payment() {
		return ol_payment;
	}
	public void setOl_payment(String ol_payment) {
		this.ol_payment = ol_payment;
	}
	public String getOl_status() {
		return ol_status;
	}
	public void setOl_status(String ol_status) {
		this.ol_status = ol_status;
	}
	public String getOl_rand() {
		return ol_rand;
	}
	public void setOl_rand(String ol_rand) {
		this.ol_rand = ol_rand;
	}
	public String getOl_return() {
		return ol_return;
	}
	public void setOl_return(String ol_return) {
		this.ol_return = ol_return;
	}
	public String getOl_date() {
		return ol_date;
	}
	public void setOl_date(String ol_date) {
		this.ol_date = ol_date;
	}
	public int getOl_pay() {
		return ol_pay;
	}
	public void setOl_pay(int ol_pay) {
		this.ol_pay = ol_pay;
	}
	public ArrayList<OrderDetailInfo> getDetailList() {
		return detailList;
	}
	public void setDetailList(ArrayList<OrderDetailInfo> detailList) {
		this.detailList = detailList;
	}
	
}


