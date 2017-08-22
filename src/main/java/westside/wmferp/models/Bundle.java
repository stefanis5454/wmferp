package westside.wmferp.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;

public class Bundle extends Model {

	public String getBundle_id() {
		return bundle_id;
	}

	public void setBundle_id(String bundle_id) {
		this.bundle_id = bundle_id;
	}

	public String getBundle_tm_id() {
		return bundle_tm_id;
	}

	public void setBundle_tm_id(String bundle_tm_id) {
		this.bundle_tm_id = bundle_tm_id;
	}

	public String getBundle_jd_id() {
		return bundle_jd_id;
	}

	public void setBundle_jd_id(String bundle_jd_id) {
		this.bundle_jd_id = bundle_jd_id;
	}

	public String getBundle_name() {
		return bundle_name;
	}

	public void setBundle_name(String bundle_name) {
		this.bundle_name = bundle_name;
	}

	public Double getTotal_rrp() {
		return total_rrp;
	}

	public void setTotal_rrp(Double total_rrp) {
		this.total_rrp = total_rrp;
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

	String bundle_id;

	String bundle_tm_id;

	String bundle_jd_id;

	String bundle_name;

	Double total_rrp;

	Double promotion_price;

	Double juhuasuan_price;

	Double daily_price;
	
	String coo;

	String tm_sku;

	String jd_sku;

	public Bundle(Object[] properties) {
		super(properties);
		
	}

	public Bundle(ResultSet rs) {
		super(rs);
		
	}
	
	public Bundle(Row row) {
		super(row);
		
	}
	
	public Bundle()
	{
		super();
	}



}
