/* 기본 View */
select DD.dept_div_nm,
       GD.govofc_div_nm,
       HD.hgdept_div_nm,
       D.dept_nm,
       EE.accnut_yy,
       sum(EE.expendtr_rsltn_amt) total_amt,
       BP.place_nm,
       UR.like_count
from "ExpendtrExcut" EE
         join "OrganizationChart" OC on OC.organization_id = EE.organization_id
         join "Dept" D on D.dept_cd_nm = OC.dept_cd_nm
         join "HgdeptDiv" HD on HD.hgdept_div_cd = OC.hgdept_div_cd
         join "GovofcDiv" GD on GD.govofc_div_cd = OC.govofc_div_cd
         join "DeptDiv" DD on DD.dept_div_cd = OC.dept_div_cd
         join "BizPlace" BP on BP.biz_reg_no = EE.biz_reg_no
         join "AccnutDiv" AD on EE.accnut_div_cd = AD.accnut_div_cd
         left join "UserRecommend" UR on BP.biz_reg_no = UR.biz_reg_no
group by DD.dept_div_nm, GD.govofc_div_nm, HD.hgdept_div_nm, D.dept_nm, EE.accnut_yy, BP.place_nm,
         UR.like_count;
