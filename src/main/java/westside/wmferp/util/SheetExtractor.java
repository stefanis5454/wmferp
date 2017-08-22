package westside.wmferp.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import westside.wmferp.models.Bundle;
import westside.wmferp.models.BundleProduct;
import westside.wmferp.models.Product;

public class SheetExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(SheetExtractor.class);

	@SuppressWarnings("deprecation")
	public static String getStringValue(Cell cell) {
		try {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}

	}

	@SuppressWarnings("deprecation")
	public static Double getDoubleValue(Cell cell) {
		try {
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			return cell.getNumericCellValue();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Product[] extractProducts() {
		List<Product> productList = new ArrayList<Product>();

		int rowNum = sheet.getLastRowNum();

		System.out.println("Row Num:" + rowNum);
		// rowIdx is actual row index in excel
		for (int rowIdx = 1; rowIdx < rowNum + 1; rowIdx++) {

			Product product = new Product(getSheetRow(rowIdx));

			if (product.getProduct_id() != null && !product.getProduct_id().trim().equals("")) {
				productList.add(product);
			}

		}

		return (Product[]) productList.toArray(new Product[productList.size()]);
	}

	public Bundle[] extractBundles() {
		List<Bundle> bundleList = new ArrayList<Bundle>();

		int rowNum = sheet.getLastRowNum();

		System.out.println("Row Num:" + rowNum);
		// rowIdx is actual row index in excel
		for (int rowIdx = 1; rowIdx < rowNum + 1; rowIdx++) {

			Bundle bundle = new Bundle(getSheetRow(rowIdx));

			if (bundle.getBundle_id() != null && !bundle.getBundle_id().trim().equals("")) {
				bundleList.add(bundle);
			}

		}

		return (Bundle[]) bundleList.toArray(new Bundle[bundleList.size()]);
	}

	public BundleProduct[] extractBundleProducts() {
		List<BundleProduct> bundleProductList = new ArrayList<BundleProduct>();

		int rowNum = sheet.getLastRowNum();

		System.out.println("Row Num:" + rowNum);
		// rowIdx is actual row index in excel
		for (int rowIdx = 1; rowIdx < rowNum + 1; rowIdx++) {

			BundleProduct bundleProduct = new BundleProduct(getSheetRow(rowIdx));

			if (bundleProduct.getBundle_id() != null && !bundleProduct.getBundle_id().trim().equals("")) {
				bundleProductList.add(bundleProduct);
			}

		}

		return (BundleProduct[]) bundleProductList.toArray(new BundleProduct[bundleProductList.size()]);
	}

	private Sheet sheet;

	private List<String> columnNames = new ArrayList<String>();

	public SheetExtractor(Sheet sheet) {
		this.sheet = sheet;

	}

	private Row getSheetRow(int idx) {
		if (idx < 0 || idx > sheet.getLastRowNum())
			throw new IllegalArgumentException("illegal row index: " + idx);

		return sheet.getRow(idx);
	}

	/**
	 * get first row in excel. generally first row is column names row
	 */
	public void getColumnNamesInExcel() {
		Row headRow = getSheetRow(0);
		if (headRow == null) {
			LOGGER.error("excel has no data in first row");
			return;
		}

		// column num in db
		Iterator<Cell> iterator = headRow.cellIterator();
		while (iterator.hasNext()) {
			Cell cell = iterator.next();
			String columnName = cell.getStringCellValue();
			columnNames.add(columnName);

		}

	}

}
