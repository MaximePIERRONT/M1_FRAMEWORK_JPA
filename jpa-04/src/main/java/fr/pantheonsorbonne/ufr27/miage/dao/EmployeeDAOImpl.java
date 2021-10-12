package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.domain.Department;
import fr.pantheonsorbonne.ufr27.miage.domain.Department_;
import fr.pantheonsorbonne.ufr27.miage.domain.Employee;
import fr.pantheonsorbonne.ufr27.miage.domain.Employee_;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
public class EmployeeDAOImpl implements EmployeeDAO {

    @Inject
    EntityManager manager;

    @Override
    @Transactional
    public void createBaseEmployees() {
        // get the main criteria building block
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // create the query with the expected return type
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        // table from which to return data
        Root<Employee> root = query.from(Employee.class);

        // joint with DepartmentTable
        Join<Employee, Department> join = root.join(Employee_.department);

        // different predicates for different requests
        Predicate predicateJava = builder.equal(join.get(Department_.NAME), "java");
        Predicate predicatePhp = builder.equal(join.get(Department_.NAME), "php");

        TypedQuery<Employee> getEmployeesByDepNameJava = manager.createQuery(query.where(predicateJava));
        List<Employee> javaEmployees = getEmployeesByDepNameJava.getResultList();
        int numOfEmployeesJava = javaEmployees.size();
        if (numOfEmployeesJava == 0) {
            Department department = new Department("java");
            manager.persist(department);

            manager.persist(new Employee("Jakab Gipsz", department, 100));
            manager.persist(new Employee("Captain Nemo", department, 120));
        }

        TypedQuery<Employee> getEmployeesByDepNamePhp = manager.createQuery(query.where(predicatePhp));

        List<Employee> phpEmployees = getEmployeesByDepNamePhp.getResultList();
        int numOfEmployeesPhp = phpEmployees.size();
        if (numOfEmployeesPhp == 0) {

            Department departmentPhp = new Department("php");
            manager.persist(departmentPhp);

            manager.persist(new Employee("Goofie", departmentPhp, 80));
            manager.persist(new Employee("Dummy", departmentPhp, 70));

        }
    }

    @Override
    @Transactional
    public List<Employee> listEmployees() {
        // get the main criteria building block
        CriteriaBuilder builder = manager.getCriteriaBuilder();


        // create the query with the expected return type
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        return manager.createQuery(query).getResultList();

    }

    @Override
    @Transactional
    public void fireAllPhpGuys() {
        {
            Query query = manager.createQuery("SELECT e from Employee  e where e.department.name=:p");
            query.setParameter("p", "php");
            for (Employee e : (List<Employee>) query.getResultList()) {
                manager.remove(e);
            }
        }
    }

    @Override
    public double getTotalSalary() {
        Query query = manager.createQuery("SELECT e.salary from Employee e");
        double result = 0;
        for( double salary : (List<Double>) query.getResultList()){
            result += salary;
        }
        return result;
    }

    @Override
    public double getPhpGuysCumulatedSalary() {
        Query query = manager.createQuery("SELECT e.salary from Employee  e where e.department.name=:p");
        query.setParameter("p", "php");
        double result = 0;
        for( double salary : (List<Double>) query.getResultList()){
            result += salary;
        }
        return result;
    }

    @Override
    public int countJavaGuys() {
        Query query = manager.createQuery("select count(e) from Employee e where e.department.name=:j");
        query.setParameter("j", "java");
        long res = (long) query.getSingleResult();
        return Math.toIntExact(res);
    }

    @Override
    public void giveRaiseToJavaGuys(double v) {
        manager.getTransaction().begin();
        Query query = manager.createQuery("update Employee e set e.salary = e.salary + :bonus where e.department.name=:j");
        query.setParameter("j", "java");
        query.setParameter("bonus", v);
        query.executeUpdate();
        manager.getTransaction().commit();
    }


}
