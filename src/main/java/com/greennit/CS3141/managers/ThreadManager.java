package com.greennit.CS3141.managers;

import com.greennit.CS3141.entities.Thread;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;

/*
 * This class manages the input, reading, deletion, and updating of entities
 * into the thread database.
 */
public class ThreadManager {

    private SessionFactory sessionFactory;
    private Session session;

    public ThreadManager() {
        setup();
    }

    /**
     * Initializes the necessary components needed for
     * the sessionFactory to operate.
     */
    private void setup() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("META-INF/hibernate.cfg.xml")
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * Closes the sessionFactory and, as a result, any ThreadManager executions.
     */
    public void exit() {
        sessionFactory.close();
    }

    /**
     * Creates a new thread in a subgreennit and pushes it to the database.
     * @param host_subgreennit  The subgreennit where the thread lies.
     * @param author            The user who created the thread.
     * @param content           The content of the thread.
     * @param creation_date     The time the thread was created.
     */
    public void createThread(int host_subgreennit, String title, String author, String content, Timestamp creation_date) {
        Thread thread = new Thread();
        thread.setHost_subgreennit(host_subgreennit);
        thread.setTitle(title);
        thread.setAuthor(author);
        thread.setContent(content);
        thread.setCreation_date(creation_date);

        session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(thread);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * Gets a thread existing in the database and returns it.
     * @param id         The ID of the thread.
     * @return                  A thread from the database. May have no content.
     */
    public Thread getThread(int id) throws IllegalArgumentException {
        session = sessionFactory.openSession();

        Thread thread = session.get(Thread.class, id);

        if (thread.getContent() == null || thread.getContent().equals("")) {
            throw new IllegalArgumentException("Host Subgreennit or Thread ID provided not valid.");
        }

        session.close();
        return thread;
    }

    /**
     * Updates a thread's content in the database.
     * @param id         The ID of the thread.
     * @param content           The new content for the thread.
     */
    public void updateThreadContent(int id, String content) {
        Thread thread = getThread(id);

        thread.setContent(content);

        session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(thread);

        session.getTransaction().commit();
        session.close();

    }

    /**
     * Deletes a thread from the database.
     * @param id    The ID of the thread.
     */
    public void deleteThread(int id) {
        Thread thread = getThread(id);

        session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(thread);

        session.getTransaction().commit();
        session.close();
    }

}
