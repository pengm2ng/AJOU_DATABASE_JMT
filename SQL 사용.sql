select DD.dept_div_nm, GD.govofc_div_nm, HD.hgdept_div_nm, D.dept_nm
from "OrganizationChart"
         join "DeptDiv" DD on DD.dept_div_cd = "OrganizationChart".dept_div_cd
         join "GovofcDiv" GD on GD.govofc_div_cd = "OrganizationChart".govofc_div_cd
         join "HgdeptDiv" HD on HD.hgdept_div_cd = "OrganizationChart".hgdept_div_cd
         join "Dept" D on D.dept_cd_nm = "OrganizationChart".dept_cd_nm
where dept_div_nm like '사업소'
  and govofc_div_nm like '수자원본부'
  and hgdept_div_nm like '%'
  and dept_nm like '%'
