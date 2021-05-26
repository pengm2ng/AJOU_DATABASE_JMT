package dao;

import java.sql.Date;
import java.util.List;

import entity.ExpendtrExcut;
import entity.Organization;

public interface ExpendtrExcutI {

    public ExpendtrExcutI getInstance();

    /**
     * 해당 부서구분, 관서, 실국, 부서에서 가장 돈을 많이 쓴 음식점 리스트 TOP 10 받아오기
     * 
     * @param deptDiv   부서구분
     * @param govofcDiv 관서
     * @param hgdeptDiv 실국
     * @param dept      부서
     * @param startDate 검색 시작 날짜
     * @param endDate   검색 종료 날짜
     * @return 음식점 리스트 TOP 10
     */
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept, Date startDate, Date endDate);

    /**
     * 해당 부서구분, 관서, 실국, 부서에서 가장 돈을 많이 쓴 음식점 리스트 TOP 10 받아오기
     * 
     * @param deptDiv   부서구분
     * @param govofcDiv 관서
     * @param hgdeptDiv 실국
     * @param dept      부서
     * @return 음식점 리스트 TOP 10
     */
    public List<ExpendtrExcut> getPlaceTopTen(Organization deptDiv, Organization govofcDiv, Organization hgdeptDiv,
            Organization dept);
}
