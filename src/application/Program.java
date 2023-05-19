package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		System.out.println("-----findById-----");

		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = sellerDao.findById(3);

		System.out.println(seller);

		System.out.println("\n-----findByIDepartment-----");

		Department dp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dp);

		list.forEach(System.out::println);
		
		System.out.println("\n-----findAll-----");

		list = sellerDao.findAll();

		list.forEach(System.out::println);
		
		System.out.println("\n-----insert-----");
		
		Seller insertSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dp);

		sellerDao.insert(insertSeller);
		
		System.out.println("Inserted! New id = " + insertSeller.getId());
		
		System.out.println("\n-----update-----");
		
		seller = sellerDao.findById(1);
		seller.setName("Maria Green");
		sellerDao.update(seller);
		
		System.out.println("Update completed!");
	}

}
