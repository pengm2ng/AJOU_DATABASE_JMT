package entity;

public class Organization {
    private String organizationCode;
    private String organizationName;


    public Organization(String organizationCode, String organizationName) {
        this.organizationCode = organizationCode;
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return this.organizationCode;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

}
