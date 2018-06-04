package dao;


import models.Task;
import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Sql2oTaskDaoTest {
    private Sql2oTaskDao taskDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        taskDao = new Sql2oTaskDao(sql2o);
        conn = sql2o.open();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Task task = new Task("mow the lawn");
        int originalTaskId = task.getId();
        taskDao.add(task);
        assertNotEquals(originalTaskId, task.getId());
    }

    @Test
    public void existingTasksCanBeFoundById() throws Exception {
        Task task = new Task("Mow the lawn");
        taskDao.add(task);
        Task foundTask = taskDao.findById(task.getId());
        assertEquals(task, foundTask);
    }

    @Test
    public void getAllCorrectlyGetsAll() throws Exception {
        Task task = new Task("mow the lawn");
        taskDao.add(task);
        Task task2 = new Task("do the dishes");
        taskDao.add(task2);
        assertEquals(2, taskDao.getAll().size());
    }

    @Test
    public void getAllReturnsNoTasksIfNoneAdded() throws Exception {
        assertEquals(0, taskDao.getAll().size());
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

}
