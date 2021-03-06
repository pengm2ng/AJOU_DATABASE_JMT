package entity;

/**
 * 조직 부모 클래스
 */
public abstract class Organization {
    /**
     * 조직 코드
     */
    protected String organizationCode;
    /**
     * 조직 이름
     */
    protected String organizationName;

    protected Organization(String organizationCode, String organizationName) {
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
    }

    /**
     * 조직 코드 가져오기
     * 
     * @return 조직 코드
     */
    public String getOrganizationCode() {
        return this.organizationCode;
    }

    /**
     * 조직 이름 가져오기
     * 
     * @return 조직 이름
     */
    public String getOrganizationName() {
        return this.organizationName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return ((Organization) obj).organizationName.equals(this.organizationName)
                && ((Organization) obj).organizationCode.equals(this.organizationCode);
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(this.organizationCode);
    }

}
