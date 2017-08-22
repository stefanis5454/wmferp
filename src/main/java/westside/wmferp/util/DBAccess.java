package westside.wmferp.util;

import westside.wmferp.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import westside.wmferp.models.Bundle;
import westside.wmferp.models.Model;

public class DBAccess {
	private final static Logger LOGGER = Logger.getLogger(DBAccess.class.getCanonicalName());

	public static List<Product> queryProducts(String sql) {
		Connection conn = DBConnection.getConnection();

		Statement stmt;
		List<Product> productList = new ArrayList<Product>();

		LOGGER.info(sql);

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				productList.add(new Product(rs));
			}
			rs.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return productList;

	}

	public static List<Bundle> queryBundles(String sql) {
		Connection conn = DBConnection.getConnection();

		Statement stmt;

		List<Bundle> bundleList = new ArrayList<Bundle>();

		LOGGER.info(sql);

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				bundleList.add(new Bundle(rs));
			}
			rs.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bundleList;

	}

	public static List<Product> searchProductByKeyword(String keyword) {
		String sql = (new Product()).genSearchSql(keyword);
		return queryProducts(sql);

	}

	public static List<Bundle> searchBundleByKeyword(String keyword) {
		String sql = (new Bundle().genSearchSql(keyword));

		return queryBundles(sql);

	}

	public static Product getProductDetal(String id) {
		String sql = (new Product()).genDetailSql(id);

		List<Product> result = queryProducts(sql);

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}

	}

	public static Bundle getBundleDetal(String id) {
		String sql = (new Bundle()).genDetailSql(id);

		List<Bundle> result = queryBundles(sql);

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}

	}

	public static List<Product> getRelatedProducts(String id) {
		Product instance = new Product();
		String columnList = instance.genColumnListString();

		String sql = "select " + columnList + " from " + instance.getClass().getSimpleName()
				+ " p where p.product_id in (select product_id from BundleProduct b " + "where b.bundle_id = '" + id
				+ "')";

		return queryProducts(sql);

	}

	public static List<Bundle> getRelatedBundles(String id) {
		Bundle instance = new Bundle();
		String columnList = instance.genColumnListString();

		String sql = "select " + columnList + " from " + instance.getClass().getSimpleName()
				+ " b where b.bundle_id in (select bundle_id from BundleProduct b " + "where b.product_id = '" + id
				+ "')";

		return queryBundles(sql);

	}

	public static void insertData(List<Model> data) {
		String[] sqls = {};

		for (int i = 0; i < data.size(); i++) {
			sqls[i] = data.get(i).genInsertSql();
		}
	}

	public static boolean insertModelDataBatch(Model[] data) {
		try {
			Connection conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			String sql;
			if (data.length > 0) {
				sql = data[0].genInsertSql();
			} else {
				return false;
			}

			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < data.length; i++) {

				data[i].setInsertValues(stmt);

				stmt.addBatch();
			}
			stmt.executeBatch();
			conn.commit();

			LOGGER.info("Success inserted " + data.length + " rows into table " + data[0].getClass().getSimpleName());
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean insertModelData(Model[] data) {
		try {
			Connection conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			String sql;
			if (data.length > 0) {
				sql = data[0].genInsertSql();
			} else {
				return false;
			}

			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			for (int i = 0; i < data.length; i++) {

				System.out.println(data[i].toJSON());

				data[i].setInsertValues(stmt);

				stmt.execute();
			}
			// stmt.executeBatch();
			conn.commit();
			conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean emptyTable(String tableName) {
		try {
			Connection conn = DBConnection.getConnection();
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM " + tableName;

			stmt.executeUpdate(sql);
			conn.close();
			LOGGER.info("The table " + tableName + " has been emtpied");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Bundle boud = DBAccess.getBundleDetal("012312321354");

		// System.out.println(boud.toJSON());

		// List<Product> productList =
		// DBAccess.getRelatedProducts("012312321354");
		List<Product> productList = DBAccess.searchProductByKeyword("22 刀");
		LOGGER.debug(productList.size());

		for (Product product : productList) {
			LOGGER.debug(product.toJSON());
		}

		// Product product = DBAccess.getProductDetal("0522135290");

		// System.out.println(product.toJSON());

		// Field[] field = Product.class.getDeclaredFields(); //
		// 获取实体类的所有属性，返回Field数组
		// for (int j = 0; j < field.length; j++) { // 遍历所有属性
		// String name = field[j].getName(); // 获取属性的名字
		// //System.out.println(name+": "+ product.getValueByField(field[j]));
		// }

		// List<Bundle> bundleList = DBAccess.getRelatedBundles("0522135290");
//		List<Bundle> bundleList = DBAccess.searchBundleByKeyword("22");
//
//		System.out.println(bundleList.size());
//
//		for (Bundle bundle : bundleList) {
//			System.out.println(bundle.toJSON());
//		}

	}

}
