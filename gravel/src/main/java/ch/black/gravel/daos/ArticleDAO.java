package ch.black.gravel.daos;

import java.util.List;

import ch.black.gravel.entities.Article;
import ch.black.gravel.entities.Person;

public interface ArticleDAO {
    public Article save(Article article);
    public Article update(Article article);
    public List<Article> findByAuthor(Person author);
    public List<Article> findAll();
}
