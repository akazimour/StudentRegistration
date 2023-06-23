package com.akazimour.StudentRegistration.repository;

import com.akazimour.StudentRegistration.entity.Course;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import java.util.List;

public class QueryDslWithEntityGraphRepositoryImpl extends SimpleJpaRepository<Course,Integer>implements QueryDslWithEntityGraphRepository<Course,Integer> {

    private final EntityManager entityManager;
    private final EntityPath<Course> path;
    private final PathBuilder<Course> builder;
    private final Querydsl querydsl;
    public QueryDslWithEntityGraphRepositoryImpl(EntityManager em){
        super(Course.class,em);
        this.entityManager = em;
        this.path = SimpleEntityPathResolver.INSTANCE.createPath(Course.class);
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(em, builder);
    }

    @Override
    public List<Course> findAll(Predicate predicate, String entityGraphName, Sort sort) {
        JPAQuery query = (JPAQuery) querydsl.applySorting(sort, createQuery(predicate).select(path));
        query.setHint(EntityGraph.EntityGraphType.LOAD.getKey(), entityManager.getEntityGraph(entityGraphName));
        return query.fetch();
    }
    private JPAQuery createQuery(Predicate predicate) {
        return querydsl.createQuery(path).where(predicate);
    }
}
