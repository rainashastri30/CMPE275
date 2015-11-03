package dao;

import model.Person;

public interface PersonDao {
	
	public Person createPerson(Person person);
	public Person updatePersonInfo(Person person);
	public Person getPersonInfo(int personId);
	public Person deletePerson(int personId);

}
