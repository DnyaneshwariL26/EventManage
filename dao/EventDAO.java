package dao;

import model.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EventDAO {
    private SessionFactory factory;

    public EventDAO() {
        factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Event.class)
                .buildSessionFactory();
    }

    public void addEvent(Event event) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
        }
    }

    public List<Event> getAllEvents() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Event> events = session.createQuery("FROM Event", Event.class).getResultList();
            session.getTransaction().commit();
            return events;
        }
    }

    public void updateEvent(Event event) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.update(event);
            session.getTransaction().commit();
        }
    }

    public void deleteEvent(int eventId) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Event event = session.get(Event.class, eventId);
            if (event != null) session.delete(event);
            session.getTransaction().commit();
        }
    }
}
