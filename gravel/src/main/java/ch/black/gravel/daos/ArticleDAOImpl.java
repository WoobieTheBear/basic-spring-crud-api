package ch.black.gravel.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ch.black.gravel.entities.Article;
import ch.black.gravel.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ArticleDAOImpl implements ArticleDAO {
    private EntityManager manager;

    @Autowired
    public ArticleDAOImpl(EntityManager injectedManager) {
        manager = injectedManager;
    }

    @Override
    public List<Article> findAll() {
        TypedQuery<Article> query = manager.createQuery("FROM Article", Article.class);
        return query.getResultList();
    }

    @Override
    public List<Article> findByAuthor(Person author) {
        
         // does not work 
        TypedQuery<Article> query = manager.createQuery(
            "SELECT a FROM Article a "
            + "LEFT JOIN FETCH a.authors "
            + "WHERE :data MEMBER OF a.authors",
            Article.class
        );
        query.setParameter("data", author);
        

        return query.getResultList();
    }

    @Override
    @Transactional
    public Article save(Article article) {
        manager.persist(article);
        manager.flush();
        return article;
    }

    @Override
    @Transactional
    public Article update(Article article) {
        return manager.merge(article);
    }
    
}
