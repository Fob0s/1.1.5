package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction = null;
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.getSession().createNativeQuery("""
                CREATE TABLE IF NOT EXISTS USERS
                (ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                  NAME VARCHAR(50) NOT NULL,
                  LASTNAME VARCHAR(50) NOT NULL,
                  AGE INT );
                """).executeUpdate();
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.getSession().createNativeQuery("""
                DROP TABLE IF EXISTS USERS;
                """).executeUpdate();
            transaction.commit();
        }catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.getSession().save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        }catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            return session.createQuery("SELECT a FROM users a", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE USERS").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
