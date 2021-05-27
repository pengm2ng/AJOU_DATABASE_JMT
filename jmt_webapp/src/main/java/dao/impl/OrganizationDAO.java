package dao.impl;

import java.util.Arrays;
import java.util.List;

import dao.OrganizationDAOI;
import entity.DeptDiv;
import entity.GovofcDiv;
import entity.Organization;

public class OrganizationDAO implements OrganizationDAOI {

    private OrganizationDAO() { }

    public static OrganizationDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final OrganizationDAO INSTANCE = new OrganizationDAO();
    }

    @Override
    public List<Organization> getAllOrganization(Class<Organization> organizationClass) {
        if (organizationClass.equals(DeptDiv.class)) {

        } else if (organizationClass.equals(GovofcDiv.class)) {

        }
        return null;
    }

    @Override
    public List<Organization> getChildrenOf(Organization organization) {
        if (organization.getClass().equals(DeptDiv.class)) {
            
        }
        return null;
    }

}
