package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-----insert-----");
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.print("Enter the name of the field you want to insert in the table: ");
		String nameFieldInsert = sc.nextLine();
		
		Department dp = new Department(null, nameFieldInsert);
		departmentDao.insert(dp);
		
		System.out.println("Insert completed! New id = " + dp.getId());
		
		System.out.println("\n-----update-----");
		
		System.out.print("Enter id number: ");
		int id = sc.nextInt();
		
		System.out.print("Enter name of the field: ");
		String nameFieldUpdate = sc.nextLine();
		
		departmentDao.update(new Department(id, nameFieldUpdate));
		
		System.out.println("Update completed!");
		
		System.out.println("\n-----deleteById-----");
		
		System.out.print("Enter id number you wnat to delete on the table: ");
		int idDelete = sc.nextInt();
		
		departmentDao.deleteById(idDelete);
		
		System.out.println("Delete completed!");
		
		System.out.println("\n-----findById-----");
		
		System.out.print("Enter id number you want to find: ");
		int idFind = sc.nextInt();
		
		Department dep = departmentDao.findById(idFind);
		
		System.out.println(dep);
		
		System.out.println("\n-----findAll-----");
		
		List<Department> list = departmentDao.findAll();
		
		list.forEach(System.out::println);
		
		sc.close();
	}

}
