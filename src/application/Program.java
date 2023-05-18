package application;

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

		System.out.println("-----\nfindByIDepartment-----");

		Department dp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(dp);

		list.forEach(System.out::println);

		System.out.println("-----\nfindAll-----");

		list = sellerDao.findAll();

		list.forEach(System.out::println);
	}

}
