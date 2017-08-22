package westside.wmferp.models;

import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_NULL_AND_BLANK;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;

import com.alibaba.fastjson.JSON;

import westside.wmferp.util.DBConnection;
import westside.wmferp.util.SheetExtractor;

import org.apache.log4j.Logger;

public abstract class Model {
	
	private final static Logger LOGGER = Logger.getLogger(Model.class.getCanonicalName());


	public String toJSON() {
		return JSON.toJSONString(this);
	}

	protected Object getFieldValue(Field field) {
		String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		try {
			Method m = this.getClass().getMethod("get" + name);

			return m.invoke(this);

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void setFieldValue(Field field, Object value) {
		String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		Method m;
		try {
			String type = field.getGenericType().toString(); // 获取属性的类型
			if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
															// "，后面跟类名
				m = this.getClass().getMethod("set" + name, new Class[] { String.class });
				m.invoke(this, new Object[] { (String) value });
			}
			if (type.equals("class java.lang.Double")) {
				m = this.getClass().getMethod("set" + name, new Class[] { Double.class });
				m.invoke(this, new Object[] { (Double) value });

			}

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setFieldByResultSet(Field field, ResultSet rs, int index) {
		String type = field.getGenericType().toString(); // 获取属性的类型
		if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
														// "，后面跟类名
			try {
				setFieldValue(field, rs.getString(index));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (type.equals("class java.lang.Double")) {
			try {
				setFieldValue(field, rs.getDouble(index));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	protected void setFieldByExcelRow(Field field, Row row, int index) {
		String type = field.getGenericType().toString(); // 获取属性的类型
		if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
														// "，后面跟类名
			try {
				setFieldValue(field, SheetExtractor.getStringValue(row.getCell(index, RETURN_NULL_AND_BLANK)));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (type.equals("class java.lang.Double")) {
			try {
				setFieldValue(field, SheetExtractor.getDoubleValue(row.getCell(index, RETURN_NULL_AND_BLANK)));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public String genColumnListString() {
		String str = "";

		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) { // 遍历所有属性
			str += fields[i].getName();
			if (i != fields.length - 1) {
				str += ",";
			}
		}
		return str;
	}

	public String genValueListString() {
		StringBuilder sb = new StringBuilder();

		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) { // 遍历所有属性
			String type = fields[i].getGenericType().toString(); // 获取属性的类型
			if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
				sb.append("'");
				sb.append(this.getFieldValue(fields[i]));
				sb.append("'");

			}
			if (type.equals("class java.lang.Double")) {
				sb.append(this.getFieldValue(fields[i]));
			}

			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String genQListString() {
		String str = "";
		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) {
			str += "?";
			if (i != fields.length - 1) {
				str += ",";
			}
		}

		return str;

	}

	public String genSearchSql(String keyword) {
		String columnList = this.genColumnListString();
		String whereStr = "";
		if (keyword != null && !keyword.trim().equals("")) {
			whereStr += " where";
			String[] keys = {};
			String connective = "";
			if (keyword.split("\\|").length > 1) {
				connective = " OR ";
				keys = keyword.split("\\|");
			} else
			if (keyword.split("\\s+").length > 1) {
				connective = " AND ";
				keys = keyword.split("\\s+");
			} else {
				keys = new String[] { keyword };
			}

			for (int i = 0; i < keys.length; i++) {
				LOGGER.debug(keys[i].trim());
				if (i != 0) {
					whereStr += connective;
				}
				whereStr += " LOCATE('" + keys[i].trim() + "',CONCAT_WS('*@*@'," + columnList + "))";

			}
		}

		String sql = "SELECT " + columnList + " from " + this.getClass().getSimpleName() + " p";
		sql += whereStr;
		return sql;
	}

	public String genDetailSql(String id) {

		String columnList = this.genColumnListString();

		String sql = "SELECT " + columnList + " FROM " + this.getClass().getSimpleName() + " Where "
				+ this.getClass().getDeclaredFields()[0].getName() + " = '" + id + "'";

		return sql;
	}

	public String genInsertSql() {

		String insertSql = "INSERT INTO " + this.getClass().getSimpleName() + "(" + this.genColumnListString()
				+ ") VALUES (" + genQListString() + ")";

		return insertSql;

	}

	public void setInsertValues(PreparedStatement stmt) {
		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) { // 遍历所有属性
			String type = fields[i].getGenericType().toString(); // 获取属性的类型
			try {
				// LOGGER.DEBUG(this.getFieldValue(fields[i]));
				if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class

					stmt.setString(i + 1, (String) this.getFieldValue(fields[i]));

				}
				if (type.equals("class java.lang.Double")) {

					stmt.setDouble(i + 1, (Double) this.getFieldValue(fields[i]));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Model(ResultSet rs) {
		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) { // 遍历所有属性

			this.setFieldByResultSet(fields[i], rs, i + 1);

		}
	}

	public Model(Row row) {
		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) { // 遍历所有属性
			// String name = field[j].getName(); // 获取属性的名字
			// LOGGER.DEBUG(name+": "+ product.getValueByField(field[j]));
			this.setFieldByExcelRow(fields[i], row, i);

		}
	}

	public Model(Object[] properties) {
		Field[] fields = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int i = 0; i < fields.length; i++) { // 遍历所有属性
			// String name = field[j].getName(); // 获取属性的名字
			// LOGGER.DEBUG(name+": "+ product.getValueByField(field[j]));
			this.setFieldValue(fields[i], properties[i]);

		}
	}

	public Model() {

	}

}
