package dao;

import model.Organization;
import model.Person;

public interface OrganizationDao {

	public void createOrganization(Organization org);

	public void updateOrganizationInfo(Organization org);

	public Person getOrganizationInfo(int orgId);

	public void deleteOrganization(int orgId);

}
