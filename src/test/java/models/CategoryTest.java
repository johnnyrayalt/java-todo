package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void categoryInstantiatesCorrectly() throws Exception{
        Category category = new Category("Work");
        assertEquals(true, category instanceof Category);
    }

    @Test
    public void getName() {
        Category categoryTest = setNewCategory();
        assertEquals("Test Name", categoryTest.getName());
    }


    public static Category setNewCategory() {
        return new Category("Test Name");
    }
}