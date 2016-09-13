package com.softserverinc.edu.repositories;


import com.softserverinc.edu.entities.Issue;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class IssueRep {

//    @Autowired
//    EntityManagerFactory entityManagerFactory;

    @Autowired
    EntityManagerFactory entityManagerFactory;
//    @Autowired
//    private SessionFactory sessionFactory;

//    Using JPA to index data
    @Transactional
    public void indexIssues() throws Exception {

        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (Exception e) {
            throw e;
        }
    }

    //    Using JPA to create and execute a search
    @Transactional
    public List<Issue> searchInIssue(String searchText) throws Exception {
        try {

            EntityManager session = entityManagerFactory.createEntityManager();

            FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(session);

            QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(Issue.class)
                    .get();
            org.apache.lucene.search.Query query = qb
                    .keyword().onFields("title", "description")
                    .matching(searchText)
                    .createQuery();

            Query hibQuery =
                    fullTextEntityManager.createFullTextQuery(query, Issue.class);

            List<Issue> results = hibQuery.getResultList();
            return results;
        } catch (Exception e) {
            throw e;
        }
    }


}
