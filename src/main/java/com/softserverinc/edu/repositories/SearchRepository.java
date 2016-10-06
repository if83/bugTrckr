package com.softserverinc.edu.repositories;

import com.softserverinc.edu.entities.Issue;
import com.softserverinc.edu.entities.Project;
import com.softserverinc.edu.entities.ProjectRelease;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * Serve fot searching in Issue, Project, ProjectRelease entities
 */
@Repository
public class SearchRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private final static Logger LOGGER = LoggerFactory.getLogger(SearchRepository.class);

    /**
     * indexed Issue, Project, ProjectRelease entities
     */
    @Transactional
    public void indexEntity() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (Exception e) {
            LOGGER.error("indexEntity", e);
        }
    }

    /**
     * @param searchText text to search information in Issue, Project, ProjectRelease
     * @return result list of Issue, Project, ProjectRelease entity
     */
    @Transactional
    public List search(String searchText) {
        List resultList = new ArrayList<>();
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(entityManager);

            QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder()
                    .forEntity(Issue.class)
                    .get();

            org.apache.lucene.search.Query query = qb
                    .keyword().onFields("title", "description")
                    .matching(searchText)
                    .createQuery();

            Query jpaQuery =
                    fullTextEntityManager.createFullTextQuery(query,
                            Issue.class,
                            Project.class,
                            ProjectRelease.class);

            resultList = jpaQuery.getResultList();
            return resultList;
        } catch (Exception e) {
            LOGGER.error("search", e);
        }
        return resultList;

    }
}
