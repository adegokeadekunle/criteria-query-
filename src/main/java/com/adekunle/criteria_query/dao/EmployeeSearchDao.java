package com.adekunle.criteria_query.dao;


import com.adekunle.criteria_query.models.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeSearchDao {

    private final EntityManager entityManager;

    public List<Employee> findAllBySimpleQuery(String firstName, String lastName,String email){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        //select * from employees
        Root<Employee> root = criteriaQuery.from(Employee.class);

        //preparing a where (where firstname like) e.g where firstname like %Adekunle%
        Predicate firstnamePredicate = criteriaBuilder// using predicate from javax.persistence.criteria.Predicate;
                .like(root.get("firstname"),"%" + firstName + "%");
        Predicate lastnamePredicate = criteriaBuilder
                .like(root.get("lastname"),"%" + lastName + "%");
        Predicate emailPredicate = criteriaBuilder
                .like(root.get("email"),"%" + email + "%");

        //creating a logical operation using or. This will check for comparison with the stated entry
        Predicate firstnameOrLastnamePredicate = criteriaBuilder.or(firstnamePredicate,lastnamePredicate);

        //select * from where firstname like "adekunle" or lastname like %adegoke% and email like "adekunle@gmail.com"
        Predicate andEmailPredicate = criteriaBuilder.and(firstnameOrLastnamePredicate,emailPredicate);

        //
        criteriaQuery.where(andEmailPredicate);
        //collecting the result to a list
        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }

    //when you want to work with an object or a request
    public List<Employee> findAllByCriteria(SearchRequest request){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        List<Predicate> predicateList = new ArrayList<>();

        Root<Employee> root = criteriaQuery.from(Employee.class);

        if(request.getFirstName() != null){
            Predicate firstnamePredicate = criteriaBuilder
                    .like(root.get("firstname"),"%" + request.getFirstName() + "%");
            predicateList.add(firstnamePredicate);
        }if(request.getLastName() != null){
            Predicate lastnamePredicate = criteriaBuilder
                    .like(root.get("lastname"),"%" + request.getLastName() + "%");
            predicateList.add(lastnamePredicate);
        }if(request.getEmail() != null){
            Predicate emailPredicate = criteriaBuilder
                    .like(root.get("email"),"%" + request.getEmail() + "%");
            predicateList.add(emailPredicate);
        }

        //this will return  a table of predicates
        criteriaBuilder.or(predicateList.toArray(new Predicate[0]));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }
}
