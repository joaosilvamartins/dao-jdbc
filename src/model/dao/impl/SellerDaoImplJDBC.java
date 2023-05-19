package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoImplJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoImplJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller seller) {
		
		PreparedStatement st = null;
		try {
			st= conn.prepareStatement(
					"insert into seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "values "
					+ "(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthdate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Seller seller) {
		
		PreparedStatement st = null;
		try {
			st= conn.prepareStatement(
					"update seller "
					+ "set name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "where Id = ?");
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthdate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			st.setInt(6, seller.getId());
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("delete from seller where Id = ?");
			
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected == 0) {
				throw new DbException("Not found id!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*, department.Nome "
					+ "from seller inner join department "
					+ "on seller.DepartmentId = department.Id "
					+ "where seller.Id = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Department dp = instanciateDepartment(rs);
				
				Seller seller = instanciateSeller(rs, dp);
				
				return seller;
			}
			return null;
		
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instanciateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller seller = new Seller();
		
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Nome"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthdate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dp);
		return seller;
	}

	private Department instanciateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department();
		
		dp.setId(rs.getInt("DepartmentId"));
		dp.setName(rs.getString("Nome"));
		return dp;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*, department.Nome "
					+ "from seller inner join department "
					+ "on seller.DepartmentId = department.Id");
		
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instanciateSeller(rs, dep);
				list.add(seller);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department dp) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select seller.*, department.Nome "
					+ "from seller inner join department "
					+ "on seller.DepartmentId = department.Id "
					+ "where DepartmentId = ? ");
			
			st.setInt(1, dp.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instanciateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instanciateSeller(rs, dep);
				list.add(seller);
			}
			return list;
		
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
