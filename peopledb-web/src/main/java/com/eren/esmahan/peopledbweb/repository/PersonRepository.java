package com.eren.esmahan.peopledbweb.repository;

import com.eren.esmahan.peopledbweb.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface PersonRepository extends CrudRepository<Person,Long> , PagingAndSortingRepository<Person, Long> {

//    @Query(nativeQuery = true, value = "select photo_filename from person where id in : ids")
//   public Set<String> findFilenameByIds(@Param("ids") Iterable<Long> ids);
}
