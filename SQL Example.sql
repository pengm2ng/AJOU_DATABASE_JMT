-- 개발 과정 중 필요한 SQL
-- 1. 테이블 생성
create table DeptDiv(
        dept_div_cd varchar(2) Not NULL,
        dept_div_nm varchar(20) Not NULL,
        primary key(dept_div_cd)
);
create table GovofcDiv(
        govofc_div_cd varchar(4) Not NULL,
        dept_div_cd varchar(2) Not NULL,
        govofc_div_nm varchar(20) Not NULL,
        primary key(govofc_div_cd),
        foreign key(dept_div_cd) references DeptDiv(dept_div_cd)
);
create table HgdeptDiv(
        hgdept_div_cd varchar(4) Not NULL,
        govofc_div_cd varchar(4) Not NULL,
        hgdept_div_nm varchar(20) Not NULL,
        primary key(hgdept_div_cd),
        foreign key(govofc_div_cd) references GovofcDiv(govofc_div_cd)
);
create table Dept(
        dept_cd_nm varchar(7) Not NULL,
        hgdept_div_cd varchar(4) Not NULL,
        dept_nm varchar(20) Not NULL,
        primary key(dept_cd_nm) Not NULL,
        foreign key(hgdept_div_cd) references HgdeptDiv(hgdept_div_cd)
);
create table AccnutDiv(
        accnut_div_cd varchar(3) Not NULL,
        accnut_div_nm varchar(20) Not NULL,
        primary key(accnut_div_cd)
);
create table BizPlace(
        biz_reg_no varchar(10) Not NULL,
        place_nm varchar(50) Not NULL,
        primary key(biz_reg_no)
);
create table UserRecommend(
        biz_reg_no varchar(10) Not NULL,
        like_account int Not NULL,
        foreign key(biz_reg_no) references BizPlace(biz_reg_no)
);
create table ExpendtrExcut(
        accnut_yy varchar(4) Not NULL,
        accnut_div_cd varchar(3) Not NULL,
        dept_cd_nm varchar(7) Not NULL,
        summry_info date Not NULL,
        expendtr_rsltn_amt int Not NULL,
        biz_reg_no varchar(10) Not NULL,
        foreign key(accnut_div_cd) references AccnutDiv(accnut_div_cd),
        foreign key(dept_cd_nm) references Dept(dept_cd_nm),
        foreign key(biz_reg_no) references BizPlace(biz_reg_no)
);
-- 2. 정보 입력 - 관서에 해당하는 GovfcDiv Table에 관서구분 코드가 21인 관서코드 1440, 관서명 해양수산지부를 insert 할때
INSERT INTO GovfcDiv
VALUES ('1440', '21', '해양수산지부');





--사용자 이용 과정 중 필요한 SQL
--1. 메인화면 - 공무원 전체 추천 맛집 BEST 10 인 가게이름, 총지출금액







-- 2. 검색설정 - 부서구분의 종류는 어떤 것이 있는가?
select dept_div_nm
from deptdiv;





-- 2-2. 검색설정 - 부서구분이 직속기관일때 관서의 종류는?
select govofc_div_nm
from deptdiv
         join govofcdiv g on deptdiv.dept_div_cd = g.dept_div_cd
where dept_div_nm = '직속기관';




-- 2-3. 검색설정 - 부서구분이 직속기관일때 관서는 수원소방서일때 실국의 종류는 어떤것이 있는가?


select hgdept_div_nm
from govofcdiv
         join hgdeptdiv h on govofcdiv.govofc_div_cd = h.govofc_div_cd
where govofcdiv.govofc_div_nm = '부천소방서';


-- 2-4. 검색설정 - 부서구분이 직속기관이고 관서는 수원소방서이고 실국은 소방서일때 부서의 종류는 어떤것이 있는가?
select dept_nm
from dept
         join hgdeptdiv h on h.hgdept_div_cd = dept.hgdept_div_cd
where hgdept_div_nm = '소방서';


-- 3. 검색결과 - 예산 내역 중에서 모든 연도의 6월에서 8월 사이 수원소방서의 BEST 10인 가게 이름, 총지출금액을 알고 싶어.
/*예산 내역중에서 aaa기관(관서)의 BEST TOP n을 알고 싶다.*/

                       ;

-- 4. 추천하기 - 해당 가게 이름 추천 수 갱신
update userrecommend
set like_count = (like_count + 1)
where biz_reg_no = (
                SELECT biz_reg_no
                from bizplace
                where place_nm = '본수원갈비'
        );





select sum(expendtr_rsltn_amt) total_amt, place_nm, like_account
from expendtrexcut
         join dept d on expendtrexcut.dept_cd_nm = d.dept_cd_nm
         join hgdeptdiv h on h.hgdept_div_cd = d.hgdept_div_cd
         join govofcdiv g on g.govofc_div_cd = h.govofc_div_cd
         join deptdiv d2 on d2.dept_div_cd = g.dept_div_cd
         join bizplace b on b.biz_reg_no = expendtrexcut.biz_reg_no
         left join userrecommend u on b.biz_reg_no = u.biz_reg_no
where d2.dept_div_cd like '11' and
      g.govofc_div_cd like '1080' and
      h.hgdept_div_cd like '%' and
      d.dept_cd_nm like '%'
group by place_nm, like_account
order by total_amt desc
limit 10;




select sum(expendtr_rsltn_amt) total_amt, place_nm, like_account
from expendtrexcut e
         join dept d on e.dept_cd_nm = d.dept_cd_nm
         join hgdeptdiv h on h.hgdept_div_cd = d.hgdept_div_cd
         join govofcdiv g on g.govofc_div_cd = h.govofc_div_cd
         join deptdiv d2 on d2.dept_div_cd = g.dept_div_cd
         join bizplace b on b.biz_reg_no = e.biz_reg_no
         left join userrecommend u on b.biz_reg_no = u.biz_reg_no
where d2.dept_div_cd like '11'
  and g.govofc_div_cd like '1080'
  and h.hgdept_div_cd like '%'
  and d.dept_cd_nm like '%'
  and extract(month from e.paymnt_command_de) > 6
  and extract(day from e.paymnt_command_de) < 8
group by place_nm, like_account
order by total_amt desc
limit 10;


select sum(expendtr_rsltn_amt) total_amt, place_nm, like_account
from expendtrexcut e
         join dept d on e.dept_cd_nm = d.dept_cd_nm
         join hgdeptdiv h on h.hgdept_div_cd = d.hgdept_div_cd
         join govofcdiv g on g.govofc_div_cd = h.govofc_div_cd
         join deptdiv d2 on d2.dept_div_cd = g.dept_div_cd
         join bizplace b on b.biz_reg_no = e.biz_reg_no
         left join userrecommend u on b.biz_reg_no = u.biz_reg_no
where d2.dept_div_cd like '11'
  and g.govofc_div_cd like '1080'
  and h.hgdept_div_cd like '%'
  and d.dept_cd_nm like '%'
  and extract(month from e.paymnt_command_de) > 6
  and extract(day from e.paymnt_command_de) < 8
  and extract(year from e.paymnt_command_de) > 2017
  and extract(year from e.paymnt_command_de) < 2020
group by place_nm, like_account
order by total_amt desc
limit 10;
