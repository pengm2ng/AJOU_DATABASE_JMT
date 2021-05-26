package dao.impl;

import java.util.List;

import dao.OrganizationDAOI;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Organization> getChildrenOf(Organization organization) {
        // TODO Auto-generated method stub
        return null;
    }

}
