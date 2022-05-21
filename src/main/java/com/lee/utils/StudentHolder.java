package com.lee.utils;

import com.lee.pojo.Student;

/**
 * The type Student holder.
 *
 * @author Lee
 */
public class StudentHolder {

    private static final ThreadLocal<Student> LOCAL = new ThreadLocal<>();

    /**
     * Save student.
     *
     * @param student the student
     */
    public static void saveStudent(Student student){
        LOCAL.set(student);
    }

    /**
     * Get student student.
     *
     * @return the student
     */
    public static Student getStudent(){
        return LOCAL.get();
    }

    /**
     * Remove student.
     */
    public static void removeStudent(){
        LOCAL.remove();
    }

}
