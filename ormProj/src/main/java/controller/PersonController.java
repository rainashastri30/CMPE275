package controller;

import model.Address;
import model.Organization;
import model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.PersonService;


@Controller
@RequestMapping("/person")
public class PersonController {

	@Autowired
	PersonService personService;

	@RequestMapping(value = "/greeting", produces = { "application/xml",
			"application/json" })
	public @ResponseBody
	Person greeting(
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

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Person createPerson(Person person, Address address, Organization org) {

		person.setAddress(address);
		person.setOrg(org);

		personService.createPerson(person);

		return person;

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			"application/xml", "application/json" })
	public Person getPersonInfo(@PathVariable int personId) {
		personService.getPersonInfo(personId);
		return new Person();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getPersonInfoHtmlView(@PathVariable int personId) {
		getPersonInfo(personId);
		return "personCreateSucces";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public Person updatePersonInfo(
			@PathVariable int personId,
			@RequestParam(value = "firstname", required = true) String firstName,
			@RequestParam(value = "lastName", required = true) String lastName,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) long zip,
			@RequestParam(value = "id", required = false) int orgId) {

		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		Organization org = new Organization();
		org.setId(orgId);
		Person person = new Person();
		person.setFirstname(firstName);
		person.setLastname(lastName);
		person.setEmail(email);
		person.setDescription(description);
		person.setAddress(address);
		person.setOrg(org);

		personService.updatePersonInfo(person);
		return person;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Person deletePerson(@RequestParam(value = "id") int personId) {
		personService.deletePerson(personId);
		return new Person();
	}

}
