package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoImplJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoImplJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dp) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("insert into department " + "(Nome) " + "values " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dp.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();

				if (rs.next()) {
					int id = rs.getInt(1);
					dp.setId(id);
				}

				DB.closeResultSet(rs);
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department dp) {

		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("update department " + "set nome = ? " + "where Id =?");

			st.setString(1, dp.getName());
			st.setInt(2, dp.getId());

			st.executeUpdate();
		} 
		catch (SQLException e) {
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
			st = conn.prepareStatement("delete from department where Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from department where Id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {

				Department dp = new Department();

				dp.setId(rs.getInt("Id"));
				dp.setName(rs.getString("Nome"));

				return dp;
			}
			return null;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("select * from department");

			rs = st.executeQuery();

			List<Department> list = new ArrayList<>();

			while (rs.next()) {
				Department dp = new Department();

				dp.setId(rs.getInt("Id"));
				dp.setName(rs.getString("Nome"));

				list.add(dp);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
