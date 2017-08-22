package westside.wmferp.models;

import java.sql.ResultSet;

import org.apache.poi.ss.usermodel.Row;

public class BundleProduct extends Model {
	
	String bundle_id;

	public String getBundle_id() {
		return bundle_id;
	}

	public void setBundle_id(String bundle_id) {
		this.bundle_id = bundle_id;
	}
	
	String product_id;

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	public BundleProduct(Object[] properties) {
		super(properties);
		
	}

	public BundleProduct(ResultSet rs) {
		super(rs);
		
	}
	
	public BundleProduct(Row row) {
		super(row);
		
	}
	
	public BundleProduct()
	{
		super();
	}

}
