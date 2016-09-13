package com.softserverinc.edu.repositories;


import com.softserverinc.edu.entities.Issue;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class IssueRep {

//    @Autowired
//    EntityManagerFactory entityManagerFactory;

    @Autowired
    JpaTransactionManager transactionManager;
//    @Autowired
//    private SessionFactory sessionFactory;

//    Using JPA to index data
    public void indexIssues() throws Exception {

        try {
            EntityManager entityManager = transactionManager.getEntityManagerFactory().createEntityManager();

            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (Exception e) {
            throw e;
        }
    }

    //    Using JPA to create and execute a search
    public List<Issue> searchInIssue(String searchText) throws Exception {
        try {

            EntityManager session = transactionManager.getEntityManagerFactory().createEntityManager();

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
