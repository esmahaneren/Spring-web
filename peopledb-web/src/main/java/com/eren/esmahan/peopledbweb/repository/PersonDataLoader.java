package com.eren.esmahan.peopledbweb.repository;

import com.eren.esmahan.peopledbweb.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

//@Component
public class PersonDataLoader implements ApplicationRunner {

    private PersonRepository personRepository;

    public PersonDataLoader(PersonRepository personRepository)
    {
        this.personRepository=personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (personRepository.count()==0) {
            List<Person> people = List.of(
//                    new Person(null, "Jake", "Smith", LocalDate.of(1995, 1, 14), "dummy@gsomething.com",new BigDecimal(50000)),
//                    new Person(null, "Julia", "Garner", LocalDate.of(1997, 11, 2),"dummy@gsomething.com", new BigDecimal(100000)),
//                    new Person(null, "Kim", "Possible", LocalDate.of(1996, 8, 17), "dummy@gsomething.com",new BigDecimal(3000))
            );
            personRepository.saveAll(people);
        }
    }
}
