package westside.wmferp.models;

import java.sql.ResultSet;

import org.apache.poi.ss.usermodel.Row;

public class Product extends Model {
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProduct_line() {
		return product_line;
	}

	public void setProduct_line(String product_line) {
		this.product_line = product_line;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getProduct_rrp() {
		return product_rrp;
	}

	public void setProduct_rrp(Double product_rrp) {
		this.product_rrp = product_rrp;
	}

	public Double getPromotion_price() {
		return promotion_price;
	}

	public void setPromotion_price(Double promotion_price) {
		this.promotion_price = promotion_price;
	}

	public Double getJuhuasuan_price() {
		return juhuasuan_price;
	}

	public void setJuhuasuan_price(Double juhuasuan_price) {
		this.juhuasuan_price = juhuasuan_price;
	}

	public Double getDaily_price() {
		return daily_price;
	}

	public void setDaily_price(Double daily_price) {
		this.daily_price = daily_price;
	}

	public String getCoo() {
		return coo;
	}

	public void setCoo(String coo) {
		this.coo = coo;
	}

	public String getTm_sku() {
		return tm_sku;
	}

	public void setTm_sku(String tm_sku) {
		this.tm_sku = tm_sku;
	}

	public String getJd_sku() {
		return jd_sku;
	}

	public void setJd_sku(String jd_sku) {
		this.jd_sku = jd_sku;
	}

	String product_id;

	String product_name;

	String category;

	String product_line;

	String status;

	Double product_rrp;

	Double promotion_price;

	Double juhuasuan_price;

	Double daily_price;

	String coo;

	String tm_sku;

	String jd_sku;

	public Product(Object[] properties) {
		super(properties);

	}

	public Product(ResultSet rs) {
		super(rs);
	}

	public Product(Row row) {
		super(row);

	}

	public Product() {
		super();
	}



	public static void main(String[] args) {
		Product p = new Product(new Object[] { "0522135290", "奈彩米快易锅6.5L RDS（红色）", "快易锅", "奈彩米", "电商", 4288.0, 1772.0,
				1968.0, 2187.0, "德国", "45388774785", "10451162597" });

		System.out.println(p.genInsertSql());
	}

}
