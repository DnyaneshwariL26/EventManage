package dao;

import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserDAO {
    private SessionFactory factory;

    public UserDAO() {
        factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public void saveUser(User user) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public User getUserByUsername(String username) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.createQuery("FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResult();
            session.getTransaction().commit();
            return user;
        }
    }

    public void updateUser(User user) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public void deleteUser(int userId) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) session.delete(user);
            session.getTransaction().commit();
        }
    }
}
