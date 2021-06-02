package dao;

import java.util.List;
import entity.Organization;

public interface OrganizationDAOI {

    /**
     * 해당 카테고리 조직의 모든 조직 불러오기
     * 
     * @param organizationClass 불러올 조직의 클래스
     * @return
     */
    public List<Organization> getAllOrganization(Class<Organization> organizationClass);

    /**
     * 해당 조직의 하위 조직 불러오기
     * 
     * @param organization 기준이 될 조직
     * @return 기준 조직의 바로 아래 조직
     */
    public List<Organization> getChildrenOf(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv, Organization dept);

}
