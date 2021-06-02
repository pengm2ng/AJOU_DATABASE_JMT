package dao.test;

import java.util.ArrayList;
import java.util.List;

import dao.OrganizationDAOI;
import entity.Dept;
import entity.DeptDiv;
import entity.GovofcDiv;
import entity.HgdeptDiv;
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
        
            System.out.println("올 부서");
            System.out.println(organizationClass.getName());
            Organization organization = new DeptDiv("123123", "외청");
            Organization organization2 = new DeptDiv("123122", "본청");
            list.add(organization);
            list.add(organization2);

        
        return list;
    }

    @Override
    public List<Organization> getChildrenOf(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept) {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public List<Organization> getChildrenOf(Organization organization) {
    //     List<Organization> list = new ArrayList<>();

    //     if (organization.getClass().equals(DeptDiv.class)) {
    //         System.out.println("부서구분 아래 관서 뽑기");
    //         System.out.println(organization.getOrganizationName());
    //         Organization organization1 = new GovofcDiv("123123", "외청관서");
    //         Organization organization2 = new GovofcDiv("123122", "본청관서");
           
    //         if(organization.getOrganizationName().equals("외청")){
                
    //             list.add(organization1);
    //         }else{
    //             list.add(organization2);
                
    //         }
            
    //     }else if (organization.getClass().equals(GovofcDiv.class)) {
            
    //         System.out.println("관서 아래 실국 뽑기");
    //         System.out.println(organization.getOrganizationName());
    //         Organization organization1 = new HgdeptDiv("123123", "외청실국");
    //         Organization organization2 = new HgdeptDiv("123122", "본청실국");
          
            
    //         if(organization.getOrganizationName().equals("외청관서")){
                
    //             list.add(organization1);
    //         }else{
    //             list.add(organization2);
                
    //         }


    //     }else if (organization.getClass().equals(HgdeptDiv.class)) {
    //         System.out.println("실국");
    //         System.out.println(organization.getOrganizationName());
    //         Organization organization1 = new Dept("123123", "외청부서");
    //         Organization organization2 = new Dept("123122", "본청부서");
   

    //         if(organization.getOrganizationName().equals("외청실국")){
                
    //             list.add(organization1);
    //         }else{
    //             list.add(organization2);
                
    //         }

    //     }
    //     return list;
    // }
    
}
