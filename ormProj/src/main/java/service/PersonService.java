package service;

import model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import dao.PersonDao;

@Configuration
@EnableWebMvc
@ComponentScan
@Service
public class PersonService {
	@Autowired
	PersonDao personDao;
	
	public Person createPerson(Person person) {
		
		personDao.createPerson(person);
		return null;
	}

	public Person updatePersonInfo(Person person) {
		
		personDao.updatePersonInfo(person);
		return null;
	}

	public Person getPersonInfo(int personId) {
		return null;
	}

	public Person deletePerson(int personId) {
		personDao.deletePerson(personId);
		return null;
	}

}
