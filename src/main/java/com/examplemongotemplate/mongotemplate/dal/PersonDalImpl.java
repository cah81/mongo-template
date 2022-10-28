package com.examplemongotemplate.mongotemplate.dal;

import com.examplemongotemplate.mongotemplate.model.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class PersonDalImpl implements PersonDal{

    private final MongoTemplate mongoTemplate;
    @Autowired
    public PersonDalImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }






    @Override
    public Person savePerson(Person person) {
        mongoTemplate.save(person);
        return person;
    }

    @Override
    public List<Person> getAllPerson() {
        return mongoTemplate.findAll(Person.class);
    }
    //The issue with the above method of getting all persons
    // from the DB is that there can be thousands of objects in the database.
    // We should always implement pagination in our queries so that we can be
    // sure that only limited data will be extracted from the database:
    //This way, only the pageSize number of objects will be fetched from the database at a time.
    @Override
    public List<Person> getAllPersonPaginated(int pageNumber, int pageSize) {
        Query query = new Query();
        query.skip(pageNumber*pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, Person.class);
    }

    @Override
    public Person findOneByName(String name) {
       Query query = new Query();
       query.addCriteria(Criteria.where("name").is(name));
       return mongoTemplate.findOne(query,Person.class);
    }

    @Override
    public List<Person> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Person.class);
    }
    //We showed two ways.
    // The first one obtained a single object from DB,
    // but the second method got all objects from DB with a
    // matching condition.
    @Override
    public List<Person> findByBirthDateAfter(Date date) {
        Query query = new Query();
        query.addCriteria(Criteria.where("dateOfBirth").gt(date));
        return mongoTemplate.find(query,Person.class);

    }

    @Override
    public List<Person> findByAgeRange(int lowerBound, int upperBound) {
        Query query = new Query();
        query.addCriteria(Criteria.where("age").gt(lowerBound).andOperator(Criteria.where("age").lt(upperBound)));
        return mongoTemplate.find(query, Person.class);
    }


    @Override
    public List<Person> findByFavoriteBooks(String favoriteBook) {
       Query query = new Query();
       query.addCriteria(Criteria.where("favoriteBooks").in(favoriteBook));
       return mongoTemplate.find(query, Person.class);
    }
    //We can update data in MongoDB with Update queries.
    // We can find an object and then update the provided fields:
    @Override
    public void updateMultiplePersonAge() {

        Query query = new Query();
        Update update = new Update().inc("age",1);
        mongoTemplate.findAndModify(query,update, Person.class);


    }

    @Override
    public Person updateOnePerson(Person person) {
        mongoTemplate.save(person);
        return  person;
    }
    //Deleting an Object is a matter of a single method call as well:
    @Override
    public void deletePerson(Person person) {
        mongoTemplate.remove(person);

    }
}
