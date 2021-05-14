INSERT INTO DeptDiv
values('21',
       '사업소');


INSERT INTO GovfcDiv
VALUES ('1440',
        '21',
        '해양수산지부');

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


UPDATE
FROM UserRecommend
SET (like_count = like_count+1)
WHERE biz_reg_no = ?;


DELETE
FROM ?
WHERE PK = ?;

