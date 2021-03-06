package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.OrganizationDAOI;
import entity.Dept;
import entity.DeptDiv;
import entity.GovofcDiv;
import entity.HgdeptDiv;
import entity.Organization;
import jdbc.ConnectionProvider;

public class OrganizationDAO implements OrganizationDAOI {
    private static final String ORGANIZATION_QUERY = "select dept_div_nm,\n" + "DD.dept_div_cd,\n" + "govofc_div_nm,\n"
            + "GD.govofc_div_cd,\n" + "hgdept_div_nm,\n" + "HD.hgdept_div_cd,\n" + "dept_nm,\n" + "D.dept_cd_nm\n"
            + "from \"OrganizationChart\"\n"
            + "join \"DeptDiv\" DD on DD.dept_div_cd = \"OrganizationChart\".dept_div_cd\n"
            + "join \"GovofcDiv\" GD on GD.govofc_div_cd = \"OrganizationChart\".govofc_div_cd\n"
            + "join \"HgdeptDiv\" HD on HD.hgdept_div_cd = \"OrganizationChart\".hgdept_div_cd\n"
            + "join \"Dept\" D on D.dept_cd_nm = \"OrganizationChart\".dept_cd_nm\n" + "where dept_div_nm like ?\n"
            + "  and govofc_div_nm like ?\n" + "  and hgdept_div_nm like ?\n" + "  and dept_nm like ?";

    private OrganizationDAO() {
    }

    public static OrganizationDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final OrganizationDAO INSTANCE = new OrganizationDAO();
    }

    @Override
    public List<Organization> getAllOrganization(Class<? extends Organization> organizationClass) {
        if (organizationClass.equals(DeptDiv.class)) {
            // DeptDiv 부서구분 전체 가져오기
            List<Organization> deptDivList = new ArrayList<>();
            try (Connection conn = ConnectionProvider.getJDBCConnection();
                    PreparedStatement pstmt = conn.prepareStatement("select * from \"DeptDiv\"");
                    ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    deptDivList.add(new DeptDiv(rs.getString("dept_div_cd"), rs.getString("dept_div_nm")));
                }
                
                return deptDivList;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (organizationClass.equals(GovofcDiv.class)) {
            // GovofcDiv 관서 전체 가져오기
            List<Organization> govofcDivList = new ArrayList<>();
            try (Connection conn = ConnectionProvider.getJDBCConnection();
                    PreparedStatement pstmt = conn.prepareStatement("select * from \"GovofcDiv\"");
                    ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    govofcDivList.add(new GovofcDiv(rs.getString("govofc_div_cd"), rs.getString("govofc_div_nm")));
                }
                
                return govofcDivList;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (organizationClass.equals(HgdeptDiv.class)) {
            // HgdeptDiv 실국 전체 가져오기
            List<Organization> hgdeptDivList = new ArrayList<>();
            try (Connection conn = ConnectionProvider.getJDBCConnection();
                    PreparedStatement pstmt = conn.prepareStatement("select * from \"HgdeptDiv\"");
                    ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    hgdeptDivList.add(new HgdeptDiv(rs.getString("hgdept_div_cd"), rs.getString("hgdept_div_nm")));
                }
           
                return hgdeptDivList;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (organizationClass.equals(Dept.class)) {
            // Dept 부서 전체 가져오기
            List<Organization> deptList = new ArrayList<>();
            try (Connection conn = ConnectionProvider.getJDBCConnection();
                    PreparedStatement pstmt = conn.prepareStatement("select * from \"Dept\"");
                    ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    deptList.add(new Dept(rs.getString("dept_cd_nm"), rs.getString("dept_nm")));
                }
         
                return deptList;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Organization> getChildrenOf(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept) {
        String deptDivName = deptDiv == null ? "%" : deptDiv.getOrganizationName();
        String govofcDivName = govofcDiv == null ? "%" : govofcDiv.getOrganizationName();
        String hgdeptDivName = hgdeptDiv == null ? "%" : hgdeptDiv.getOrganizationName();
        String deptName = dept == null ? "%" : dept.getOrganizationName();

        List<Organization> children = new ArrayList<>();
        try (Connection conn = ConnectionProvider.getJDBCConnection();
                PreparedStatement organizationPstmt = conn.prepareStatement(ORGANIZATION_QUERY);) {

            organizationPstmt.setString(1, deptDivName);
            organizationPstmt.setString(2, govofcDivName);
            organizationPstmt.setString(3, hgdeptDivName);
            organizationPstmt.setString(4, deptName);

            ResultSet rs = organizationPstmt.executeQuery();

            while (rs.next()) {
                Organization element = null;
                if (deptDiv == null) {
                    throw new RuntimeException("Invalid Parameter");
                } else if (govofcDiv == null) {
                    element = new GovofcDiv(rs.getString("govofc_div_cd"), rs.getString("govofc_div_nm"));
                    if (children.contains(element)) {
                        continue;
                    }
                } else if (hgdeptDiv == null) {
                    element = new HgdeptDiv(rs.getString("hgdept_div_cd"), rs.getString("hgdept_div_nm"));
                    if (children.contains(element)) {
                        continue;
                    }
                } else if (dept == null) {
                    element = new Dept(rs.getString("dept_cd_nm"), rs.getString("dept_nm"));
                    if (children.contains(element)) {
                        continue;
                    }
                } else {
                    throw new RuntimeException("Invalid Parameter");
                }
                children.add(element);
            }
           
            rs.close();
            
            return children;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

}
