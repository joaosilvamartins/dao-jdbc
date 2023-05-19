package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		try {
			System.out.println("-----findById-----");

			System.out.print("Enter id number you want to find: ");
			int idFind = sc.nextInt();
			
			SellerDao sellerDao = DaoFactory.createSellerDao();
			Seller seller = sellerDao.findById(idFind);

			System.out.println(seller);

			System.out.println("\n-----findByIDepartment-----");

			System.out.print("Enter id number of department you want to find: ");
			int idDepartment = sc.nextInt();
			
			Department dp = new Department(idDepartment, null);
			List<Seller> list = sellerDao.findByDepartment(dp);

			list.forEach(System.out::println);
			
			System.out.println("\n-----findAll-----");

			list = sellerDao.findAll();

			list.forEach(System.out::println);
			
			System.out.println("\n-----insert-----");
			
			System.out.print("Enter name seller: ");
			String name = sc.nextLine();
			
			System.out.print("Enter e-mail seller: ");
			String email = sc.nextLine();
			
			System.out.print("Enter date of birth (DD/MM/YYYY): ");
			Date d = sdf.parse(sc.next());
			
			System.out.print("Enter base salary: ");
			double baseSalary = sc.nextDouble();
			
			System.out.println("\nNow the department data:");
			System.out.print("Enter id number: ");
			int idDep = sc.nextInt();
			
			System.out.print("Enter name of the field: ");
			String nameDep = sc.nextLine();
			
			Seller insertSeller = new Seller(null, name, email, d, baseSalary, new Department(idDep, nameDep));
			
			sellerDao.insert(insertSeller);
			
			System.out.println("Inserted! New id = " + insertSeller.getId());
			
			System.out.println("\n-----update-----");
			
			System.out.print("Enter id number: ");
			int idUpdate = sc.nextInt();
			
			seller = sellerDao.findById(idUpdate);
			
			System.out.print("Enter name of the field: ");
			String nameUpdate = sc.nextLine();
			
			seller.setName(nameUpdate);
			
			sellerDao.update(seller);
			
			System.out.println("Update completed!");
			
			System.out.println("\n-----delete-----");
			
			System.out.print("Enter id for delete item: ");
			int id = sc.nextInt();
			
			sellerDao.deleteById(id);
			
			System.out.println("Delete completed!");
			
			sc.close();
		}
		catch(ParseException e) {
			System.out.println(e.getMessage());
		}
	}

}
