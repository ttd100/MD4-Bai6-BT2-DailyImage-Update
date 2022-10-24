package rikkei.academy.service;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rikkei.academy.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Transactional
public class CommentServiceIMPL implements ICommentService {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> findAll() {
        String queryStr = "select c from Comment as c where c.date = :date";
        TypedQuery<Comment> query = entityManager.createQuery(queryStr, Comment.class);
        query.setParameter("date", new Date(), TemporalType.DATE);
        return query.getResultList();
    }

    @Override
    public void save(Comment comment) {
        if (comment.getId() != 0) {
            entityManager.merge(comment);
        } else {
            entityManager.persist(comment);
        }
    }

    @Override
    public Comment findById(int id) {
        String queryStr = "select c from Comment c where c.id = :id";
        TypedQuery<Comment> query = entityManager.createQuery(queryStr, Comment.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
