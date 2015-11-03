package controller;

import model.Address;
import model.Organization;
import model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sun.mail.handlers.message_rfc822;
import com.sun.research.ws.wadl.Request;

import service.PersonService;

@Configuration
@EnableWebMvc
@ComponentScan
@Controller
public class PersonController {

	@Autowired
	PersonService personService;

	@RequestMapping(value = "/greeting", produces = { "application/xml",
			"application/json" })
	public @ResponseBody Person greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name,
			Model model) {
		Person person = new Person();
		int age = 25;
		// person.setAge(age);
		// person.setName(name);
		model.addAttribute("person", person);
		return person;
	}

	@RequestMapping(value = "/greeting", method = RequestMethod.POST)
	public String htmlView(@RequestParam(value = "name") String name,
			Model model) {
		greeting(name, model);
		return "person";
	}

	@RequestMapping("/person")
	public Person createPerson(
			@RequestParam(value = "firstname", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "id", required = false) int id) {

		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		Organization org = new Organization();
		org.setId(id);
		Person person = new Person();
		person.setFirstname(firstName);
		person.setLastname(lastName);
		person.setEmail(email);
		person.setDescription(description);
		person.setAddress(address);
		person.setOrg(org);

		personService.createPerson(person);

		return person;

	}

	@RequestMapping(value = "person/{id}", method = RequestMethod.GET)
	public Person getPersonInfo(@RequestParam(value = "id") int personId) {
		personService.getPersonInfo(personId);
		return null;
	}

	@RequestMapping(value = "person/{id}", method = RequestMethod.POST)
	public Person updatePersonInfo(
			@RequestParam(value = "id", required = true) String personId,
			@RequestParam(value = "firstname", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "id", required = false) int id) {

		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		Organization org = new Organization();
		org.setId(id);
		Person person = new Person();
		person.setFirstname(firstName);
		person.setLastname(lastName);
		person.setEmail(email);
		person.setDescription(description);
		person.setAddress(address);
		person.setOrg(org);

		personService.updatePersonInfo(person);
		return null;
	}

	@RequestMapping(value = "person/{id}", method = RequestMethod.DELETE)
	public Person deletePerson(@RequestParam(value = "id") int personId) {
		personService.deletePerson(personId);
		return null;
	}

}
