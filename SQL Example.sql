//개발 과정 중 필요한 SQL
//1. 테이블 생성
create table DeptDiv(dept_div_cd varchar(2), dept_div_nm varchar(20), primary key(dept_div_cd));

create table GovofcDiv(govofc_div_cd varchar(4) , dept_div_cd varchar(2), govofc_div_nm varchar(20), primary key(govofc_div_cd), 
					   foreign key(dept_div_cd) references DeptDiv(dept_div_cd));

create table HgdeptDiv(hgdept_div_cd varchar(4) , govofc_div_cd varchar(4), hgdept_div_nm varchar(20), primary key(hgdept_div_cd), 
					   foreign key(govofc_div_cd) references GovofcDiv(govofc_div_cd));
					   
create table Dept(dept_cd_nm varchar(7) , hgdept_div_cd varchar(4), dept_nm varchar(20), primary key(dept_cd_nm), 
					   foreign key(hgdept_div_cd) references HgdeptDiv(hgdept_div_cd));					   
					
					
create table AccnutDiv(accnut_div_cd varchar(3) , accnut_div_nm varchar(20), primary key(accnut_div_cd));
					   
create table BizPlace(biz_reg_no varchar(10), place_nm varchar(20), primary key(biz_reg_no));

create table UserRecommend(biz_reg_no varchar(10), like_account int, foreign key(biz_reg_no) references BizPlace(biz_reg_no));

create table ExperdtrExcut(accnut_yy varchar(4), accnut_div_cd varchar(3), dept_cd_nm varchar(7), summry_info date, expendtr_rsltn_amt int, biz_reg_no varchar(10), 
						   foreign key(accnut_div_cd) references AccnutDiv(accnut_div_cd),
						   foreign key(dept_cd_nm) references Dept(dept_cd_nm),
						   foreign key(biz_reg_no) references BizPlace(biz_reg_no));

//2. 정보 입력 - 관서에 해당하는 GovfcDiv Table에 관서구분 코드가 21인 관서코드 1440, 관서명 해양수산지부를 insert 할때

INSERT INTO GovfcDiv
VALUES ('1440', '21', '해양수산지부');


 //3. 부서 삭제 - 수원소방서 삭제 (수원소방서의 부서 코드 = 1137001)
DELETE 
FROM Dept
WHERE PK = '1137001';



 //사용자 이용 과정 중 필요한 SQL

//1. 메인화면 - 공무원 전체 추천 맛집 BEST 10 인 가게이름, 추천수, 총지출금액

//2. 검색설정 - 부서구분이 직속기관이고 관서가 수원소방서이고 실국이 소방서일 때 부서이름들은?

//3. 검색결과 - 예산 내역 중에서 모든 연도의 6월에서 8월 사이 수원소방서의 BEST 10인 가게 이름, 추천 수와 총지출금액을 알고 싶어.
/*예산 내역중에서 aaa기관(관서)의 BEST TOP n을 알고 싶다.*/
SELECT cust_div_nm
FROM ExpendtrExcut
WHERE ExpendtrExcut.dept_cd_nm =
                (SELECT dept_cd_nm
                 FROM Dept
                 WHERE Dept.hgdept_div_cd =
                                 (SELECT hgdept_div_cd
                                  FROM HgdeptDiv
                                  WHERE hgdept_div_cd.govofc_div_nm =
                                                  (SELECT govofc_div_cd
                                                   FROM GovofcDiv
                                                   WHERE govofc_div_cd.govofc_div_nm = ('aaa기관') ) ) )

GROUP BY biz_reg_no
ORDER BY SUM(expendtr_resltn_amt) DESC
LIMIT n;


//4. 추천하기 - 해당 가게 이름 추천 수 갱신
UPDATE
FROM UserRecommend
SET (like_count = like_count+1)
WHERE biz_reg_no = ?;




