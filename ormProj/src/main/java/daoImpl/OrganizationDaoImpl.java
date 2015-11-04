package daoImpl;

import org.springframework.stereotype.Repository;

import dao.OrganizationDao;
import model.Organization;
import model.Person;

@Repository("orgDao")
public class OrganizationDaoImpl implements OrganizationDao {

	public void createOrganization(Organization org) {
	}

	public void updateOrganizationInfo(Organization org) {
	}

	public Person getOrganizationInfo(int orgId) {
		return null;
	}

	public void deleteOrganization(int orgId) {
	}

}
