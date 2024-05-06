package org.example.HR_ManagementSystem.source.data.impl;

import org.example.HR_ManagementSystem.source.data.PositionServiceDao;
import org.example.HR_ManagementSystem.source.model.Position;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PositionServiceDaoImpl implements PositionServiceDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PositionServiceDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Position findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Position.class, id);
        }
    }

    @Override
    public List<Position> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Position", Position.class).list();
        }
    }

    @Override
    public void create(Position position) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(position);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot save position", e);
        }
    }

    @Override
    public void delete(Position position) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(position);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot delete position", e);
        }
    }

    @Override
    public Position update(Long id, Position positionDetails) {
        Transaction transaction = null;
        Position existingPosition = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            existingPosition = session.get(Position.class, id);
            if (existingPosition != null) {
                existingPosition.setName(positionDetails.getName());
                session.update(existingPosition);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot update position", e);
        }
        return existingPosition;
    }
}

