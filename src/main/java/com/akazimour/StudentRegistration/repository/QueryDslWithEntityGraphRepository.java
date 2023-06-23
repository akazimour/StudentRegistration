package com.akazimour.StudentRegistration.repository;

import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityGraph;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QueryDslWithEntityGraphRepository<T,ID> {

    List<T> findAll(Predicate predicate, String entityGraphName, Sort sort);
}
