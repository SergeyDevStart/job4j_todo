package ru.job4j.todo.repository.task;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernateTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    public Task create(Task task) {
        var session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        var session = sf.openSession();
        boolean isUpdated = false;
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery("""
                    UPDATE Task SET title = :title, description = :description, created = :created, done = :done
                    WHERE id = :id
                    """, Task.class)
                    .setParameter("title", task.getTitle())
                    .setParameter("description", task.getDescription())
                    .setParameter("created", task.getCreated())
                    .setParameter("done", task.isDone())
                    .setParameter("id", task.getId());
            isUpdated = query.executeUpdate() > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isUpdated;
    }

    @Override
    public void deleteById(Integer id) {
        var session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Task WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Task> findById(Integer id) {
        var session = sf.openSession();
        Optional<Task> optionalTask = Optional.empty();
        try {
            session.beginTransaction();
            optionalTask = session.createQuery("FROM Task WHERE id = :id", Task.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return optionalTask;
    }

    @Override
    public Collection<Task> findAll() {
        var session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> findNew() {
        var session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        var today = LocalDate.now().atStartOfDay();
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task WHERE created >= :today", Task.class)
                    .setParameter("today", today)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> findCompleted() {
        var session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task WHERE done = true", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }
}
