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
select place_nm,
        total_amt,
         U.like_account
from bizplace as biz 
        left join userrecommend u on biz.biz_reg_no = u.biz_reg_no,
        (
                select biz_reg_no,
                        sum(expendtr_rsltn_amt) as total_amt
                from expendtrexcut
                Group by expendtrexcut.biz_reg_no
                order by sum(expendtr_rsltn_amt) DESC
                limit 10
        ) as top
where top.biz_reg_no = biz.biz_reg_no
order by total_amt desc;






-- 2. 검색설정 - 부서구분의 종류는 어떤 것이 있는가?
select dept_div_nm
from deptdiv;





-- 2-2. 검색설정 - 부서구분이 직속기관일때 관서의 종류는?
select govofc_div_nm
from deptdiv,
        govofcdiv
where deptdiv.dept_div_cd = govofcdiv.dept_div_cd
        and deptdiv.dept_div_nm = '직속기관';





-- 2-3. 검색설정 - 부서구분이 직속기관일때 관서는 수원소방서일때 실국의 종류는 어떤것이 있는가?
select hgdept_div_nm
from govofcdiv,
        hgdeptdiv
where hgdeptdiv.govofc_div_cd = govofcdiv.govofc_div_cd
        and govofcdiv.govofc_div_nm = '수원소방서';





-- 2-4. 검색설정 - 부서구분이 직속기관이고 관서는 수원소방서이고 실국은 소방서일때 부서의 종류는 어떤것이 있는가?
select dept_nm
from dept,
        hgdeptdiv
where dept.hgdept_div_cd = hgdeptdiv.hgdept_div_cd
        and hgdeptdiv.hgdept_div_nm = '소방서';
-- 3. 검색결과 - 예산 내역 중에서 모든 연도의 6월에서 8월 사이 수원소방서의 BEST 10인 가게 이름, 총지출금액을 알고 싶어.
/*예산 내역중에서 aaa기관(관서)의 BEST TOP n을 알고 싶다.*/
SELECT place_nm,
        total_amt
FROM bizplace AS biz,
        (
                SELECT biz_reg_no,
                        SUM(expendtr_rsltn_amt) AS total_amt
                FROM ExpendtrExcut
                WHERE ExpendtrExcut.dept_cd_nm = (
                                SELECT dept_cd_nm
                                FROM Dept
                                WHERE Dept.hgdept_div_cd = (
                                                SELECT hgdept_div_cd
                                                FROM HgdeptDiv
                                                WHERE hgdeptdiv.hgdept_div_nm = '경기도인재개발원'
                                        ) 6 < EXTRACT(
                                                MONTH
                                                FROM paymnt_command_de
                                        )
                                        AND EXTRACT(
                                                MONTH
                                                FROM paymnt_command_de
                                        ) < 8
                        )
                GROUP BY biz_reg_no
                ORDER BY SUM(expendtr_rsltn_amt)
                LIMIT 10
        ) AS top
WHERE top.biz_reg_no = biz.biz_reg_no
ORDER BY total_amt DESC;


-- 4. 추천하기 - 해당 가게 이름 추천 수 갱신
update userrecommend
set like_count = (like_count + 1)
where biz_reg_no = (
                SELECT biz_reg_no
                from bizplace
                where place_nm = '본수원갈비'
        );
-- 5. 본수원갈비 추천수 가져오기
select like_count
from userrecommend,
        bizplace
where userrecommend.biz_reg_no = bizplace.biz_reg_no
        and bizplace.place_nm = '본수원갈비';




SELECT place_nm,
        total_amt,
       U.like_account
FROM bizplace AS biz left join userrecommend u on biz.biz_reg_no = u.biz_reg_no, (
                        SELECT biz_reg_no, SUM(expendtr_rsltn_amt) AS total_amt
                        FROM ExpendtrExcut as expend, (
                                                        SELECT dept_cd_nm
                                                        FROM Dept as dept2,(
                                                                        SELECT hgdept_div_cd
                                                                        FROM HgdeptDiv
                                                                        WHERE hgdeptdiv.hgdept_div_nm = '소방서'
                                                                        ) as hgdept
                                                        WHERE Dept2.hgdept_div_cd = hgdept.hgdept_div_cd
                                                        ) as dept_cd
                        WHERE Expend.dept_cd_nm = dept_cd.dept_cd_nm
                                AND 6 < EXTRACT( MONTH FROM paymnt_command_de )
                                AND EXTRACT( MONTH FROM paymnt_command_de ) < 8
                        GROUP BY biz_reg_no
                        ORDER BY SUM(expendtr_rsltn_amt) DESC
                        LIMIT 10
                ) AS top
WHERE top.biz_reg_no = biz.biz_reg_no
ORDER BY total_amt DESC;






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