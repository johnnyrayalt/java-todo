package dao;

import models.Category;
import models.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class Sql2oCategoryDaoTest {
    private Sql2oTaskDao taskDao;
    private Sql2oCategoryDao categoryDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        categoryDao = new Sql2oCategoryDao(sql2o);
        taskDao = new Sql2oTaskDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCategorySetsId() throws Exception{
        Category category = setUpNewCategory();
        int originalCategoryId = category.getId();
        categoryDao.add(category);
        assertNotEquals(originalCategoryId, category.getId());
    }

    @Test
    public void existingCategoriesCanBeFoundById() throws Exception{
        Category category = setUpNewCategory();
        categoryDao.add(category);
        Category foundCategory = categoryDao.findById(category.getId());
        assertEquals(category, foundCategory);
    }

    @Test
    public void getAllCorrectlyGetsAll() throws Exception {
        Category category = setUpNewCategory();
        categoryDao.add(category);
        Category category1 = setUpNewCategory();
        categoryDao.add(category1);
        assertEquals(2, categoryDao.getAll().size());
    }

    @Test
    public void getAllReturnsNoCategoriesIfNoneAdded() throws Exception {
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void updateUpdatesCorrectly() throws Exception {
        Category category = setUpNewCategory();
        categoryDao.add(category);
        categoryDao.update(category.getId(), "Jest");
        Category updatedCategory = categoryDao.findById(category.getId());
        assertEquals("Jest", updatedCategory.getName());
    }

    @Test
    public void deleteDeletesCorrectly() {
        Category category = setUpNewCategory();
        categoryDao.add(category);
        categoryDao.deleteById(category.getId());
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void clearAllCatagoriesClearsCatagories() throws Exception {
        Category category = setUpNewCategory();
        categoryDao.add(category);
        categoryDao.clearAllCategories();
        assertEquals(0, categoryDao.getAll().size());
    }

    @Test
    public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
        Category category = setUpNewCategory();
        categoryDao.add(category);
        int categoryId = category.getId();
        Task newTask = new Task("mow the lawn", categoryId);
        Task otherTask = new Task("pull weeds", categoryId);
        Task thirdTask = new Task("trim hedge", categoryId);
        taskDao.add(newTask);
        taskDao.add(otherTask);
        assertEquals(2, categoryDao.getAllTasksByCategory(categoryId).size());
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(newTask));
        assertTrue(categoryDao.getAllTasksByCategory(categoryId).contains(otherTask));
        assertFalse(categoryDao.getAllTasksByCategory(categoryId).contains(thirdTask));

    }

    public Category setUpNewCategory() {
        return new Category("Test");
    }
}