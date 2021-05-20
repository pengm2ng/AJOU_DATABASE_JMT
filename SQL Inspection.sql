-- 개발 과정 중 필요한 SQL
-- 1. 테이블 생성
CREATE TABLE DeptDiv
(
    dept_div_cd VARCHAR(2)  NOT NULL,
    dept_div_nm VARCHAR(20) NOT NULL,
    PRIMARY KEY (dept_div_cd)
);
CREATE TABLE GovofcDiv
(
    govofc_div_cd VARCHAR(4)  NOT NULL,
    dept_div_cd   VARCHAR(2)  NOT NULL,
    govofc_div_nm VARCHAR(20) NOT NULL,
    PRIMARY KEY (govofc_div_cd),
    FOREIGN KEY (dept_div_cd) REFERENCES DeptDiv (dept_div_cd)
);
CREATE TABLE HgdeptDiv
(
    hgdept_div_cd VARCHAR(4)  NOT NULL,
    govofc_div_cd VARCHAR(4)  NOT NULL,
    hgdept_div_nm VARCHAR(20) NOT NULL,
    PRIMARY KEY (hgdept_div_cd),
    FOREIGN KEY (govofc_div_cd) REFERENCES GovofcDiv (govofc_div_cd)
);
CREATE TABLE Dept
(
    dept_cd_nm    VARCHAR(7)  NOT NULL,
    hgdept_div_cd VARCHAR(4)  NOT NULL,
    dept_nm       VARCHAR(20) NOT NULL,
    PRIMARY KEY (dept_cd_nm),
    FOREIGN KEY (hgdept_div_cd) REFERENCES HgdeptDiv (hgdept_div_cd)
);
CREATE TABLE AccnutDiv
(
    accnut_div_cd VARCHAR(3)  NOT NULL,
    accnut_div_nm VARCHAR(20) NOT NULL,
    PRIMARY KEY (accnut_div_cd)
);
CREATE TABLE BizPlace
(
    biz_reg_no VARCHAR(10) NOT NULL,
    place_nm   VARCHAR(50) NOT NULL,
    PRIMARY KEY (biz_reg_no)
);
CREATE TABLE UserRecommend
(
    biz_reg_no   VARCHAR(10) NOT NULL,
    like_account INT         NOT NULL,
    FOREIGN KEY (biz_reg_no) REFERENCES BizPlace (biz_reg_no)
);
CREATE TABLE ExpendtrExcut
(
    accnut_yy          VARCHAR(4)  NOT NULL,
    accnut_div_cd      VARCHAR(3)  NOT NULL,
    dept_cd_nm         VARCHAR(7)  NOT NULL,
    paymnt_command_de  DATE        NOT NULL,
    expendtr_rsltn_amt INT         NOT NULL,
    biz_reg_no         VARCHAR(10) NOT NULL,
    FOREIGN KEY (accnut_div_cd) REFERENCES AccnutDiv (accnut_div_cd),
    FOREIGN KEY (dept_cd_nm) REFERENCES Dept (dept_cd_nm),
    FOREIGN KEY (biz_reg_no) REFERENCES BizPlace (biz_reg_no)
);
-- 2. 정보 입력 - 관서에 해당하는 GovfcDiv TABLE에 관서구분 코드가 21인 관서코드 1440, 관서명 해양수산지부를 insert 할때
INSERT INTO GovfcDiv
VALUES ('1440', '21', '해양수산지부');

--사용자 이용 과정 중 필요한 SQL
--1. 메인화면 - 공무원 전체 추천 맛집 BEST 10 인 가게이름, 총지출금액
SELECT place_nm,
       total_amt,
       U.like_account
FROM bizplace AS biz
         LEFT JOIN userrecommend u ON biz.biz_reg_no = u.biz_reg_no,
     (
         SELECT biz_reg_no,
                SUM(expendtr_rsltn_amt) AS total_amt
         FROM expendtrexcut
         GROUP BY expendtrexcut.biz_reg_no
         ORDER BY SUM(expendtr_rsltn_amt) DESC
         limit 10
     ) AS TOP
WHERE TOP.biz_reg_no = biz.biz_reg_no
ORDER BY total_amt DESC;






-- 2. 검색설정 - 부서구분의 종류는 어떤 것이 있는가?
SELECT dept_div_nm
FROM deptdiv;





-- 2-2. 검색설정 - 부서구분이 직속기관일때 관서의 종류는?
SELECT govofc_div_nm
FROM deptdiv,
     govofcdiv
WHERE deptdiv.dept_div_cd = govofcdiv.dept_div_cd
  AND deptdiv.dept_div_nm = '직속기관';





-- 2-3. 검색설정 - 부서구분이 직속기관일때 관서는 수원소방서일때 실국의 종류는 어떤것이 있는가?
SELECT hgdept_div_nm
FROM govofcdiv,
     hgdeptdiv
WHERE hgdeptdiv.govofc_div_cd = govofcdiv.govofc_div_cd
  AND govofcdiv.govofc_div_nm = '수원소방서';





-- 2-4. 검색설정 - 부서구분이 직속기관이고 관서는 수원소방서이고 실국은 소방서일때 부서의 종류는 어떤것이 있는가?
SELECT dept_nm
FROM dept,
     hgdeptdiv
WHERE dept.hgdept_div_cd = hgdeptdiv.hgdept_div_cd
  AND hgdeptdiv.hgdept_div_nm = '소방서';
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
             )
               AND 6
                 < EXTRACT(
                           MONTH
                           FROM paymnt_command_de
                       )
               AND EXTRACT(
                           MONTH
                           FROM paymnt_command_de
                       )
                 < 8
         )
         GROUP BY biz_reg_no
         ORDER BY SUM(expendtr_rsltn_amt)
         LIMIT 10) AS TOP
WHERE TOP.biz_reg_no = biz.biz_reg_no
ORDER BY total_amt DESC;


-- 4. 추천하기 - 해당 가게 이름 추천 수 갱신
UPDATE userrecommend
set like_count = (like_count + 1)
WHERE biz_reg_no = (
    SELECT biz_reg_no
    FROM bizplace
    WHERE place_nm = '본수원갈비'
);
-- 5. 본수원갈비 추천수 가져오기
SELECT like_count
FROM userrecommend,
     bizplace
WHERE userrecommend.biz_reg_no = bizplace.biz_reg_no
  and bizplace.place_nm = '본수원갈비';




SELECT place_nm,
       total_amt,
       U.like_account
FROM bizplace AS biz
         LEFT JOIN userrecommend u ON biz.biz_reg_no = u.biz_reg_no,
     (
         SELECT biz_reg_no, SUM(expendtr_rsltn_amt) AS total_amt
         FROM ExpendtrExcut AS expend,
              (
                  SELECT dept_cd_nm
                  FROM Dept AS dept2,
                       (
                           SELECT hgdept_div_cd
                           FROM HgdeptDiv
                           WHERE hgdeptdiv.hgdept_div_nm = '소방서'
                       ) AS hgdept
                  WHERE Dept2.hgdept_div_cd = hgdept.hgdept_div_cd
              ) AS dept_cd
         WHERE Expend.dept_cd_nm = dept_cd.dept_cd_nm
           AND 6 < EXTRACT(MONTH FROM paymnt_command_de)
           AND EXTRACT(MONTH FROM paymnt_command_de) < 8
         GROUP BY biz_reg_no
         ORDER BY SUM(expendtr_rsltn_amt) DESC
         LIMIT 10
     ) AS TOP
WHERE TOP.biz_reg_no = biz.biz_reg_no
ORDER BY total_amt DESC;
