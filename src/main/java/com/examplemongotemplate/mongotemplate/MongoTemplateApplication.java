package com.examplemongotemplate.mongotemplate;

import com.examplemongotemplate.mongotemplate.dal.PersonDal;
import com.examplemongotemplate.mongotemplate.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class MongoTemplateApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger("MongoTemplateApplication");

	public static void main(String[] args) {
		SpringApplication.run(MongoTemplateApplication.class, args);
	}
	private final PersonDal personDal;

	public MongoTemplateApplication(PersonDal personDal) {
		this.personDal = personDal;
	}

	@Override
	public void run(String... args) throws Exception {
		personDal.savePerson(new Person(
				"Shubham", Arrays.asList("Harry potter", "Waking Up"), new Date(769372200000L)));
		personDal.savePerson(new Person(
				"Sergey", Arrays.asList("Startup Guides", "Java"), new Date(664309800000L)));
		personDal.savePerson(new Person(
				"David", Arrays.asList("Harry potter", "Success"), new Date(695845800000L)));
		personDal.savePerson(new Person(
				"Ivan", Arrays.asList("Secrets of Butene", "Meeting Success"), new Date(569615400000L)));
		personDal.savePerson(new Person(
				"Sergey", Arrays.asList("Harry potter", "Startup Guides"), new Date(348777000000L)));



		LOG.info("Getting all data from MongoDB: \n{}",
				personDal.getAllPerson());

		LOG.info("Getting paginated data from MongoDB: \n{}",
				personDal.getAllPersonPaginated(0, 2));
		LOG.info("Getting person By name 'Sergey': {}",
				personDal.findByName("Sergey"));
		LOG.info("Getting all person By name 'Sergey': {}",
				personDal.findOneByName("Sergey"));
		LOG.info("Getting people between age 22 & 26: {}",
				personDal.findByAgeRange(22, 26));
	}
}
