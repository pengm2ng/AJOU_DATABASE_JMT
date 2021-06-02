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

    private OrganizationDAO() {
    }

    public static OrganizationDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final OrganizationDAO INSTANCE = new OrganizationDAO();
    }

    @Override
    public List<Organization> getAllOrganization(Class<Organization> organizationClass) {
        /**
         * '부서구분' 카테고리에서 '전체'를 선택했을때, '관서'에 대해서 모든 list를 가져오는 내용
         */
        if (organizationClass.equals(DeptDiv.class)) {
            try (Connection conn = ConnectionProvider.getJDBCConnection()) {
                List<Organization> govofcList = new ArrayList<>();

                PreparedStatement pstmt = conn.prepareStatement("select * from public \"OrganizationChart\"");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String govofcDivCode = rs.getString("Govofc_div_cd");
                    pstmt = conn
                            .prepareStatement("select govofc_div_nm from public.\"GovofcDiv\" where govofc_div_cd='?'");
                    pstmt.setString(1, govofcDivCode);
                    rs = pstmt.executeQuery();
                    String govofcDivName = rs.getString("Govofc_div_nm");
                    govofcList.add(new GovofcDiv(govofcDivCode, govofcDivName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (organizationClass.equals(GovofcDiv.class)) {
            /**
             * '관서' 카테고리에서 '전체'를 선택했을때, '실국'에 대해서 모든 list를 가져오는 내용
             */
            try (Connection conn = ConnectionProvider.getJDBCConnection()) {
                ArrayList<Organization> hgdeptList = new ArrayList<>();

                PreparedStatement pstmt = conn.prepareStatement("select * from public.\"OrganizationChart\"");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String hgdeptCode = rs.getString("hgdept_div_cd");
                    pstmt = conn
                            .prepareStatement("select hgdept_div_nm from public.\"HgdeptDiv\" where hgdept_div_cd='?'");
                    pstmt.setString(1, hgdeptCode);
                    rs = pstmt.executeQuery();
                    String hgdeptName = rs.getString("hgdept_div_nm");
                    hgdeptList.add(new HgdeptDiv(hgdeptCode, hgdeptName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (organizationClass.equals(HgdeptDiv.class)) {
            /**
             * '실국' 카테고리에서 '전체'를 선택했을때, '부서'에 대해서 모든 list를 가져오는 내용
             */
            try (Connection conn = ConnectionProvider.getJDBCConnection()) {
                ArrayList<Organization> deptList = new ArrayList<>();

                PreparedStatement pstmt = conn.prepareStatement("select * from public.\"OrganizationChart\"");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String deptCode = rs.getString("dept_cd_nm");
                    pstmt = conn.prepareStatement("select dept_nm from public.\"Dept\" where dept_cd_nm='?'");
                    pstmt.setString(1, deptCode);
                    rs = pstmt.executeQuery();
                    String deptName = rs.getString("dept_nm");
                    deptList.add(new Dept(deptCode, deptName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Organization> getChildrenOf(Organization organization) {
        if (organization.getClass().equals(DeptDiv.class)) {
            /**
             * '부서구분'카테고리에서 선택할 경우, 해당 '부서구분'에 해당되는 '관서'들의 list를 가져오는 내용
             */
            ArrayList<Organization> govofcList = new ArrayList<Organization>();
            try (Connection conn = ConnectionProvider.getJDBCConnection()) {
                PreparedStatement pstmt = conn.prepareStatement(
                        "select Govofc_div_cd from public.\"OrganizationChart\" where dept_div_cd='?'");
                pstmt.setString(1, organization.getOrganizationCode());
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String govofcDivCode = rs.getString("Govofc_div_cd");
                    pstmt = conn
                            .prepareStatement("select govofc_div_nm from public.\"GovofcDiv\" where govofc_div_cd='?'");
                    pstmt.setString(1, govofcDivCode);
                    rs = pstmt.executeQuery();
                    String govofcDivName = rs.getString("Govofc_div_nm");
                    govofcList.add(new GovofcDiv(govofcDivCode, govofcDivName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (organization.getClass().equals(GovofcDiv.class)) {
            /**
             * '관서'카테고리에서 선택할 경우, 해당 '관서'에 해당되는 '실국'들의 list를 가져오는 내용
             */
            try (Connection conn = ConnectionProvider.getJDBCConnection()) {
                ArrayList<Organization> hgdeptList = new ArrayList<>();

                PreparedStatement pstmt = conn.prepareStatement(
                        "select hgdept_div_cd from public.\"OrganizationChart\" where govofc_div_cd='?'");
                pstmt.setString(1, organization.getOrganizationCode());
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String hgdeptCode = rs.getString("hgdept_div_cd");
                    pstmt = conn
                            .prepareStatement("select hgdept_div_nm from public.\"HgdeptDiv\" where hgdept_div_cd='?'");
                    pstmt.setString(1, hgdeptCode);
                    rs = pstmt.executeQuery();
                    String hgdeptName = rs.getString("hgdept_div_nm");
                    hgdeptList.add(new HgdeptDiv(hgdeptCode, hgdeptName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (organization.getClass().equals(HgdeptDiv.class)) {
            /**
             * '실국'카테고리에서 선택할 경우, 해당 '실국'에 해당되는 '부서'들의 list를 가져오는 내용
             */
            try (Connection conn = ConnectionProvider.getJDBCConnection()) {
                ArrayList<Organization> deptList = new ArrayList<>();

                PreparedStatement pstmt = conn.prepareStatement(
                        "select dept_cd_nm from public.\"OrganizationChart\" where hgdept_div_cd='?'");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String deptCode = rs.getString("dept_cd_nm");
                    pstmt = conn.prepareStatement("select dept_nm from public.\"Dept\" where dept_cd_nm='?'");
                    pstmt.setString(1, deptCode);
                    rs = pstmt.executeQuery();
                    String deptName = rs.getString("dept_nm");
                    deptList.add(new Dept(deptCode, deptName));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

}
