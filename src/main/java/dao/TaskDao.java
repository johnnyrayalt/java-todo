package dao;

import models.Task;
import java.util.List;

public interface TaskDao {

    List<Task> getAll();

    void add(Task task);

    Task findById(int id);

    void update(int id, String content);

    void deleteById(int id);
    void clearAllTasks();
}
