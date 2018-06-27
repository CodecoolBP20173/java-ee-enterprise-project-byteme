package com.codecool.codebook.sql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.codecool.codebook.model.Klass;
import com.codecool.codebook.model.Message;
import com.codecool.codebook.model.Student;
import com.codecool.codebook.model.Workplace;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

public class Queries {
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction etr;

    public Queries(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = emf.createEntityManager();
        this.etr = em.getTransaction();
    }

    /**
     * Simple select query
     * @return List
     */
    public List getAllStudentInfo(){

        Query query = em.createQuery("SELECT s FROM Student s");

        return query.getResultList();
    }

    /**
     * Simple entitymanager finder
     * @param: Id : long
     * @return: Student object
     */
    public Student getStudent(Long Id){
        try {
            Student student = em.find(Student.class, Id);
            return student;
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return null;
    }


    public Workplace getWorkplace(Long Id){
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
    public Set getAllStudentInKlass(long klassId){
        try {
            Klass klass = em.find(Klass.class, klassId);
            return klass.getStudents();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public Set getAllStudentInWorkplace(long workplaceId){
        try {
            Workplace workplace = em.find(Workplace.class, workplaceId);
            return workplace.getStudents();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public Set getAllJobsInWorkplace(long workplaceId){
        try {
            Workplace workplace = em.find(Workplace.class, workplaceId);
            return workplace.getJobs();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public Workplace getStudentWorkplace(long Id){
        try {
            Student student = em.find(Student.class, Id);
            return student.getWorkplace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public List getAllMessageBetweenUsers(Long senderId, Long receiverId){

        Query query = em.createQuery("SELECT s FROM Message s " +
                "where sender_id = '" + senderId + "' and receiver_Id = '" + receiverId + "' or sender_id = '" + receiverId + "' and receiver_Id = '" + senderId + "' ");

        return query.getResultList();
    }

    public Klass getStudentKlass(long Id){
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
    public String getWorkplaceForStd(long workplaceId){
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
    public String getKlassForStd(long klassId){
        try {
            Klass klass = em.find(Klass.class, klassId);

            return klass.getName();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        return null;
    }

    public List getAllWorkplace(){
        Query query = em.createQuery("SELECT s FROM Workplace s");

        return query.getResultList();
    }

    public void addNewStudent(Student student) {
        try {
            etr.begin();
            em.persist(student);
            etr.commit();
        }catch (IllegalArgumentException e){
            System.err.println("Error caught: " + e.toString() + "in addNewStudent()");
        }
    }

    public void addNewMessage(Message message) {
        etr.begin();
        em.persist(message);
        etr.commit();

    }

    public String getPassword(String email){
        try {
            Query query = em.createQuery("SELECT password from Student WHERE email = :email")
                    .setParameter("email", email);
            return query.getSingleResult().toString();
        }catch (NoResultException e){
            System.err.println("Error caught: " + e.toString() + "in getPassword()");
        }
        return null;
    }

    /**
     * Simple getID function
     * @param: email: String
     * @return: 0< int or -1 on NoResultException
     */
    public int getID(String email){
        try {
            Query query = em.createQuery("SELECT id from Student WHERE email = :email")
                    .setParameter("email", email);

            return Integer.parseInt(query.getSingleResult().toString());
        }catch (NoResultException e){
            System.err.println("Error caught: " + e.toString() + "in getID()");
        }
        return -1;
    }

    public void deleteStudent(String email){
        try {

            etr.begin();
            Query query = em.createQuery("DELETE from Student where email = :email")
                    .setParameter("email", email);
            query.executeUpdate();
            etr.commit();
        }catch (NoResultException e){
            System.err.println("Error caught: " + e.toString() + "in deleteStudent()");
        }
    }

    public Student getStudent(String email){

        Query query = em.createQuery("select id from Student where email = :email")
                                    .setParameter("email", email);

        Long id = Long.parseLong(query.getSingleResult().toString());

        return em.find(Student.class, id);

    }
}
