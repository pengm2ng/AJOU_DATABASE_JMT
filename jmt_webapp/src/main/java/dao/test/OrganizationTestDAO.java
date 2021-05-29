package dao.test;

import java.util.ArrayList;
import java.util.List;

import dao.OrganizationDAOI;
import entity.DeptDiv;
import entity.GovofcDiv;
import entity.Organization;

public class OrganizationTestDAO implements OrganizationDAOI{
    private OrganizationTestDAO() { }

    public static OrganizationTestDAO getInstance() {
        return InstHolder.INSTANCE;
    }

    private static class InstHolder {
        public static final OrganizationTestDAO INSTANCE = new OrganizationTestDAO();
    }

    @Override
    public List<Organization> getAllOrganization(Class<Organization> organizationClass) {
            List<Organization> list = new ArrayList<>();
        if (organizationClass.equals(DeptDiv.class)) {
            System.out.println("올 부서");
            System.out.println(organizationClass.getName());
            Organization organization = new DeptDiv("123123", "외청");
            Organization organization2 = new DeptDiv("123122", "본청");
            list.add(organization);
            list.add(organization2);

        } else if (organizationClass.equals(GovofcDiv.class)) {
            System.out.println("올 관서");
            System.out.println(organizationClass.getName());
            Organization organization = new GovofcDiv("123123", "외청관서");
            Organization organization2 = new GovofcDiv("123122", "본청관서");
            list.add(organization);
            list.add(organization2);
        }
        return list;
    }

    @Override
    public List<Organization> getChildrenOf(Organization organization) {
        List<Organization> list = new ArrayList<>();

        if (organization.getClass().equals(DeptDiv.class)) {
            System.out.println("부서구분");
            System.out.println(organization.getOrganizationName());
            Organization organization1 = new DeptDiv("123123", "외청");
            Organization organization2 = new DeptDiv("123122", "본청");
            list.add(organization1);
            list.add(organization2);

        }else if (organization.getClass().equals(GovofcDiv.class)) {
            System.out.println("관서");
            System.out.println(organization.getOrganizationName());
            Organization organization1 = new GovofcDiv("123123", "외청관서");
            Organization organization2 = new GovofcDiv("123122", "본청관서");
            list.add(organization1);
            list.add(organization2);
        }
        return list;
    }
    
}
