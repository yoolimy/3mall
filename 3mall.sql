use yoorim;

-- 어드민 
create table t_admin_list(
	al_idx int auto_increment unique,	-- 관리자번호
	al_id varchar(20) primary key,	-- 관리자ID
	al_pwd varchar(20) not null, 		-- 비밀번호
	al_name varchar(20) not null, 	-- 이름
	al_phone varchar(13) not null, 	-- 전화번호
	al_email varchar(50) not null, 	-- 이메일
	al_date	datetime default now(), 	-- 등록일
	al_memo	varchar(200),		-- 관리자 메모	
	al_team   varchar(20)   not null,    	-- 부서
	al_position   varchar(20)  not null,   	-- 직급
	al_status char(1) default 'a'		-- 상태(a:사용중, b:대기중, c:퇴사)
);
select * from t_admin_list;
insert into t_admin_list (al_idx, al_id, al_pwd, al_name, al_phone, al_email, al_team, al_position)
values (1, 'admin', '1234', '관리자1', '010-3434-2345', 'dkfj@naver.com', '영업부', '사원');

create table t_admin_menu (
	am_idx int auto_increment primary key,	-- 메뉴번호
	am_name	varchar(20) not null, 		-- 메뉴명
	am_link	varchar(100) not null, 		-- 메뉴링크 URL
	am_level int default 1,   			-- 메뉴레벨(0:메뉴분류, 1:실메뉴)
	am_date	datetime default now(),		-- 등록일
	am_status char(1) default 'a'			-- 상태(a:사용중, b:사용불가)
);
create table t_admin_pms (
	ap_idx int auto_increment primary key,		-- 권한번호
	al_idx int not null,				-- 관리자번호
	am_idx int not null,				-- 메뉴번호
	ap_pms char(1) default 'A',			-- 권한
	ap_date	datetime default now(),		-- 등록일
    constraint fk_pms_al_idx foreign key (al_idx)
		references t_admin_list(al_idx), 	-- 관리자idx 외래키 지정
	constraint fk_pms_am_idx foreign key (am_idx)
		references t_admin_menu(am_idx) 	-- 메뉴idx 외래키 지정
);

create table t_admin_etc (
	ae_main1 varchar(20) not null,	-- 메인이미지1
	ae_main2 varchar(20) not null,	-- 메인이미지2
	ae_main3 varchar(20) not null,	-- 메인이미지3
	ae_popup varchar(20), 		-- 팝업이미지
	ae_system varchar(20) not null	-- 대여시스템이미지
);


-- 회원목록 테이블	
create table t_member_list (											-- 21.01.19 변아영 수정
	ml_id varchar(20) primary key,			-- 회원ID
	ml_pwd varchar(20)	not null,			-- 비밀번호
	ml_name	varchar(20) not null,			-- 이름
	ml_gender char(1)	not null,			-- 성별
	ml_birth char(10) not null,				-- 생일
	ml_phone varchar(13) not null,			-- 전화번호
	ml_email varchar(50) not null,			-- 이메일
    ml_agremail char(1) default 'y',		-- 이메일 수신 여부(y:수신 동의 / n:수신 미동의)
	ml_date	datetime default now(),			-- 가입일
	ml_last	datetime default now(),			-- 최종로그인
	ml_status char(1) default 'a',			-- 상태
	ml_memo	varchar(200)					-- 관리자 메모 -- 수정
);
select * from t_member_list;
-- insert into t_member_list(ml_id, ml_pwd, ml_name, ml_gender, ml_birth, ml_phone, ml_email, ml_agremail, ml_status) values ('test1', '1234', '홍길동', 'F', '19930728', '010-1234-5789', 'test1@naver.com', 'y', 'a');
-- insert into t_member_list(ml_id, ml_pwd, ml_name, ml_gender, ml_birth, ml_phone, ml_email, ml_status) values ('test2', '1234', '전우치', 'M', '19931004', '010-1234-5789', 'test2@gmail.com', 'n', 'a');

create table t_member_addr (											-- 21.01.19 변아영 수정
	ma_idx int auto_increment primary key,	-- 주소번호
	ml_id varchar(20) not null,				-- 회원 ID
	ma_zip char(5) not null,				-- 우편번호
	ma_addr1 varchar(50) not null,			-- 주소1
	ma_addr2 varchar(50) not null,			-- 주소2
	ma_basic char(1) default 'y',			-- 기본주소여부
	ma_date	datetime default now(),			-- 등록일
    constraint fk_addr_ml_id foreign key (ml_id)
		references t_member_list(ml_id) 	-- 회원id 외래키 지정
);
select * from t_member_addr;
-- insert into t_member_addr(ml_id, ma_zip, ma_addr1, ma_addr2, ma_basic) values ('test1', 12345, '서울시 은평구', '푸르지오아파드 222동 101호', 'y');
-- insert into t_member_addr(ml_id, ma_zip, ma_addr1, ma_addr2, ma_basic) values ('test2', 54321, '경기도 수원시 ', '벽산아파트 333동 1201호', 'n');

	
-- 상품 테이블
create table t_cata_big (		
	cb_idx int primary key,		-- 대분류번호
	cb_name	varchar(10) not null	-- 대분류명
);
insert into t_cata_big (cb_idx, cb_name) values (1, '여성한복');
insert into t_cata_big (cb_idx, cb_name) values (2, '남성한복');
insert into t_cata_big (cb_idx, cb_name) values (3, '장신구');

create table t_cata_small (		
	cs_idx int primary key,			-- 소분류번호
	cb_idx int not null,			-- 대분류번호
	cs_name	varchar(10) not null,	-- 소분류명
	constraint fk_cata_cb_idx foreign key (cb_idx)
		references t_cata_big(cb_idx) 	-- 대분류id 외래키 지정
);
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (1044 , 1, '44size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (1055 , 1, '55size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (1066 , 1, '66size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (1077 , 1, '77size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (2090 , 2, '90size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (2095 , 2, '95size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (2100 , 2, '100size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (2105 , 2, '105size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (2110 , 2, '110size');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (3011 , 3, '여성신발');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (3012 , 3, '남성신발');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (3021 , 3, '여성장신구');
insert into t_cata_small (cs_idx, cb_idx, cs_name) values (3022 , 3, '남성장신구');


create table t_product_list (	
	pl_id char(10) primary key,			-- 상품코드
	cs_idx int not null,				-- 소분류번호
	pl_name	varchar(50) not null,		-- 상품명
	pl_price int default 0,				-- 가격
	pl_mainimg varchar(50) not null,	-- 메인사진
	pl_img1	varchar(50),				-- 상세사진1
	pl_img2	varchar(50),				-- 상세사진2
	pl_rent int default 0,				-- 대여중
    pl_srent varchar(10),				-- 대여 시작일
    pl_erent varchar(10),				-- 대여 종료일
	pl_salecnt int default 0,			-- 판매량
	pl_review int default 0,			-- 리뷰개수
	pl_view	char(1) default 'n',		-- 게시여부
	pl_date	datetime default now(),		-- 상품등록일
	pl_detail varchar(200) ,			-- 상품상세정보
	pl_deInfo varchar(500) ,  			-- 상품상세설명
	al_idx int not null,				-- 관리자번호
    constraint fk_pl_cs_idx foreign key (cs_idx)
		references t_cata_small(cs_idx), 	-- 소분류idx 외래키 지정
	constraint fk_pl_al_idx foreign key (al_idx)
		references t_admin_list(al_idx) 	-- 관리자idx 외래키 지정
);

select * from t_product_list;
select pl_name, c.cs_idx, b.cb_idx  
from t_product_list a, t_cata_big b, t_cata_small c 
where a.cs_idx = c.cs_idx and b.cb_idx = c.cb_idx and a.pl_id = '1044pdt001' ;
update t_product_list set cs_idx = '1066', pl_name = '여성한복 2', pl_price = '33000', pl_opt1 = 'Size,44,55,66', pl_opt2 = '', pl_opt3 = '', pl_mainimg = 'null', pl_img1 = 'w3-1.JPG', pl_img2 = 'w3-2.JPG', pl_stock = '3', pl_detail = 'null', pl_deInfo = 'null', pl_view = 'y' where pl_id = '1066pdt001';
insert into t_product_list (pl_id, cs_idx, pl_name, pl_price, pl_mainimg, al_idx, pl_srent, pl_erent, pl_view) 
values ('1044pdt001' , 1044, '예쁜 한복', '20000', '한복.jpg', '1', '2021-01-18', '2021-01-21', 'y');
-- 상품 주문
create table t_cart_list(
	cl_idx int auto_increment primary key,		-- 일련번호
	cl_buyer varchar(20) not null,		-- 회원ID 
	pl_id char(10) not null,			-- 상품ID
	cl_opt varchar(100),				-- 선택한 옵션
	cl_cnt int default 1,				-- 선택한 수량
	cl_sdate	varchar(10) not null,		-- 대여시작일			
	cl_edate varchar(10) not null,			-- 대여끝나는일
	cl_date datetime default now(),		-- 등록일
	constraint fk_cl_pl_id foreign key (pl_id)
			references t_product_list(pl_id)	-- 상품 id 외래키 지정
);
create table t_order_list (
	ol_id char(15) primary key,		-- 주문ID
	ol_buyer varchar(20) not null, 	-- 회원ID 
	ol_rname  varchar(20) not null,	-- 수령인명
	ol_rphone1 varchar(13) not null,	-- 수령인 전화번호1
	ol_rphone2 varchar(13),		-- 수령인 전화번호2
	ol_rzip char(5) not null,		-- 수령인 우편번호
	ol_raddr1 varchar(50) not null,	-- 수령인 주소1
	ol_raddr2 varchar(50) not null,	-- 수령인 주소2
	ol_comment varchar(200) default '',	-- 요청사항
	ol_payment char(1) default 'a',	-- 결제방법
	ol_pay int default 0,		-- 결제액
	ol_status char(1) default 'b',		-- 주문상태
	ol_rand char(1) default 'd',		-- 수령방법('s' : 직접 / 'd' : 배송)
	ol_return char(1) default 'd',		-- 회수방법('s' : 직접 / 'd' : 배송)
	ol_date datetime default now()	-- 주문일
);
create table t_order_detail (
	od_idx int auto_increment unique,	-- 일련번호
	ol_id char(15) not null,		-- 주문ID
	pl_id char(10) not null, 		-- 상품ID
	od_cnt int default 1,		-- 구매수량
	od_opt varchar(100),		-- 선택한 옵션
	od_price int default 0,		-- 구매단가
	od_sdate	varchar(10) not null,	-- 대여시작일			
	od_edate varchar(10) not null,	-- 대여끝나는일
    constraint fk_od_ol_id foreign key (ol_id)
			references t_order_list(ol_id),		-- 주문ID 외래키 지정
	constraint fk_od_pl_id foreign key (pl_id)
			references t_product_list(pl_id)	-- 상품ID 외래키 지정
);