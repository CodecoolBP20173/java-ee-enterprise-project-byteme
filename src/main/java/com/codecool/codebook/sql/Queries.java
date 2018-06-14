package com.codecool.codebook.sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.codecool.codebook.model.Klass;
import com.codecool.codebook.model.Student;
import com.codecool.codebook.model.Workplace;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class Queries {
    private static String dbName = "codebookPU";
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(dbName);
    private static EntityManager em = emf.createEntityManager();
    private static EntityTransaction etr = em.getTransaction();

    public static void setDbName(String dbName) {
        Queries.dbName = dbName;
    }

    /**
     * Simple select query
     * @return List
     */
    public static List getAllStudentInfo(){

        Query query = em.createQuery("SELECT s FROM Student s");

        return query.getResultList();
    }

    /**
     * Simple entitymanager finder
     * @param: Id : long
     * @return: Student object
     */
    public static Student getStudent(Long Id){
        try {
            Student student = em.find(Student.class, Id);
            return student;
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null;
    }


    public static Workplace getWorkplace(Long Id){
        try {
            Workplace workplace = em.find(Workplace.class, Id);
            return workplace;
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null;
    }



    /**
     *  Returns all the students from a specific klass
     *  @params: klass_id : long
     *  @return: Set : Student obj
     */
    public static Set getAllStudentInKlass(long klassId){
        try {
            Klass klass = em.find(Klass.class, klassId);
            return klass.getStudents();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Set getAllStudentInWorkplace(long workplaceId){
        try {
            Workplace workplace = em.find(Workplace.class, workplaceId);
            return workplace.getStudents();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Set getAllJobsInWorkplace(long workplaceId){
        try {
            Workplace workplace = em.find(Workplace.class, workplaceId);
            return workplace.getJobs();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Workplace getStudentWorkplace(long Id){
        try {
            Student student = em.find(Student.class, Id);
            return student.getWorkplace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Klass getStudentKlass(long Id){
        try {
            Student student = em.find(Student.class, Id);
            return student.getKlass();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     *   Returns a specific Workplace name for a student
     *   @param: workplace_id : long
     *   @returtn: String or null on exception
     */
    public static String getWorkplaceForStd(long workplaceId){
        try {
            Workplace workplace = em.find(Workplace.class, workplaceId);
            return workplace.getName();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     *   Returns a specific Klass name for a student
     *   @param: klassId
     *   @return: String or null on exception
     */
    public static String getKlassForStd(long klassId){
        try {
            Klass klass = em.find(Klass.class, klassId);

            return klass.getName();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        return null;
    }

    public static List getAllWorkplace(){
        Query query = em.createQuery("SELECT s FROM Workplace s");

        return query.getResultList();
    }

    public static void addNewStudent(Student student) {
        etr.begin();
        em.persist(student);
        etr.commit();

    }

    public static String getPassword(String email){
        try {
            Query query = em.createQuery("SELECT password from Student WHERE email = :email")
                    .setParameter("email", email);
            return query.getSingleResult().toString();
        }catch (NoResultException e){
            System.err.println("Error caught: " + e.toString());
        }
        return null;
    }

    public static int getID(String email){
        Query query = em.createQuery("SELECT id from Student WHERE email = :email")
                                    .setParameter("email", email);

        return Integer.parseInt(query.getSingleResult().toString());

    }

    public static void deleteStudent(String email){
        etr.begin();
        Query query = em.createQuery("DELETE from Student where email = '" + email + "'");
        query.executeUpdate();
        etr.commit();
    }

    public static Student getStudent(String email){
        Query query = em.createQuery("select id from Student where email = '" + email + "'");
        Long id = Long.parseLong(query.getSingleResult().toString());

        return em.find(Student.class, id);

    }

    public static void setEnv(String dbName){
        emf = Persistence.createEntityManagerFactory(dbName);
        em = emf.createEntityManager();
        etr = em.getTransaction();
    }
}
