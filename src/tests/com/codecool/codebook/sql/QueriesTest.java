package com.codecool.codebook.sql;

import com.codecool.codebook.model.Klass;
import com.codecool.codebook.model.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import com.codecool.codebook.CodeBookTestEnv;

import static org.junit.Assert.*;

public class QueriesTest {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("codebooktestPU");
    EntityManager em = emf.createEntityManager();
    Queries queries = new Queries(emf);

    @Before
    public void populateTDB() {
        CodeBookTestEnv.createDB(em);
    }




    /**
     *  Simple test for getAllStudentInfo function
     *  @return: void
     */
    @Test
    public void getAllStudentInfo() {

        List list = queries.getAllStudentInfo();

        //Cast objects to Student objects
        Student student1 = (Student) list.get(0);
        Student student2 = (Student) list.get(1);
        Student student3 = (Student) list.get(2);
        Student student4 = (Student) list.get(3);

        //test each student with the test data
        assertEquals("as44d@gmil.com", student1.getEmail());
        assertEquals("as23d@gmil.com", student2.getEmail());
        assertEquals("as3d@gmil.com", student3.getEmail());
        assertEquals("as321d@gmil.com", student4.getEmail());
    }

    /**
     *  Simple test for getAllStudentInKlass function
     *  @return: void
     */
    @Test
    public void getAllStudentInKlass() {
        Klass newklass = new Klass("klass");
        Klass newklass2 = new Klass("klass2");
        Student std1 =  new Student("john", "asd3@gmil.com", "p4wd");
        Student std2 =  new Student("john1", "as2d@gmil.com", "p3wd");
        Student std3 =  new Student("john2", "asdw@gmil.com", "p2wd");
        Student std4 =  new Student("john3", "asd4@gmil.com", "p1wd");
        newklass.addStudent(std1);
        newklass.addStudent(std2);
        newklass2.addStudent(std3);
        newklass2.addStudent(std4);

        //Getting students from classes
        Set firstClass = queries.getAllStudentInKlass(1);
        Set secondClass = queries.getAllStudentInKlass(2);

        //Cast objects to Student objects
        Student student1 = (Student) firstClass.toArray()[0];
        Student student2 = (Student) firstClass.toArray()[1];
        Student student3 = (Student) secondClass.toArray()[0];
        Student student4 = (Student) secondClass.toArray()[1];

        //test each student with the test data
        assertEquals(std1.getKlass().getName(), student1.getKlass().getName());
        assertEquals(std2.getKlass().getName(), student2.getKlass().getName());
        assertEquals(std3.getKlass().getName(), student3.getKlass().getName());
        assertEquals(std4.getKlass().getName(), student4.getKlass().getName());
    }

    @Test
    public void getWorkplaceForStd() {
        String workplaceName = queries.getWorkplaceForStd(1);
        assertEquals("Pedigre", workplaceName);
    }

    @Test
    public void getKlassForStd() {
        String klassName = queries.getKlassForStd(1);
        assertEquals("klass", klassName);
    }

    @Test
    public void getStudent(){

    }

    @After
    public void closeConnection(){
        em.close();
        emf.close();
    }
}