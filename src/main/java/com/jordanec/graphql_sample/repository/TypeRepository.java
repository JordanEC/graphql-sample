package com.jordanec.graphql_sample.repository;

import com.jordanec.graphql_sample.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    Type findByName(String name);
}
