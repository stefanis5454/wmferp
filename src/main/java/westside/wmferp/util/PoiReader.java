package westside.wmferp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import westside.wmferp.models.Bundle;
import westside.wmferp.models.BundleProduct;
import westside.wmferp.models.Model;
import westside.wmferp.models.Product;

public class PoiReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(PoiReader.class);

	/**
	 * a super class of HSSFWorkbook(xls), XSSFWorkbook(xlsx),
	 * SXSSFWorkbook(xlsx)
	 */
	private Workbook workbook;

	public PoiReader(InputStream inputStream) throws IOException, InvalidFormatException {
		if (inputStream == null) {
			LOGGER.error("input stream is null");
			throw new IllegalArgumentException("illegal inputStream: " + inputStream);
		}

		// works fine with excel 2003/2007/2010+
		workbook = WorkbookFactory.create(inputStream);
	}

	public PoiReader(File excelFile) throws IOException, InvalidFormatException {
		this(new FileInputStream(excelFile));
	}

	/**
	 * sheets number of excel file
	 *
	 * @return sheets number
	 */
	public int sheetsNum() {
		return workbook.getNumberOfSheets();
	}

	/**
	 * get sheet by name
	 *
	 * @param sheetName
	 * @return
	 */
	public Sheet getSheetByName(String sheetName) {
		if (sheetName == null || "".equals(sheetName)) {
			LOGGER.error("sheet name should not be null nor be blank");
			throw new IllegalArgumentException("illegal sheetName: " + sheetName);
		}
		return workbook.getSheet(sheetName);
	}

	/**
	 * get sheet by index
	 *
	 * @param idx
	 * @return
	 */
	public Sheet getSheetById(int idx) {
		if (idx < 0 || idx > sheetsNum()) {
			LOGGER.error("sheet index should >= 0 and smaller than than max scope");
			throw new IllegalArgumentException("illegal idx: " + idx);
		}
		return workbook.getSheetAt(idx);
	}

	/**
	 * Close the underlying input resource (File or Stream), from which the
	 * Workbook was read.
	 * <p>
	 * <p>
	 * Once this has been called, no further operations, updates or reads should
	 * be performed on the Workbook.
	 *
	 * @throws IOException
	 */
	public void closeReader() throws IOException {
		workbook.close();
	}

	public static void main(String[] args) {

		PoiReader poiReader;
		try {
			poiReader = new PoiReader(new File("/Users/syincdl/Downloads/Masterdata.xlsx"));
			SheetExtractor sheetExtractor = new SheetExtractor(poiReader.getSheetById(0));

			// Product[] productList = sheetExtractor.extractProducts();
			//
			// System.out.println(productList.length);
			//
			// for (Product product : productList) {
			// System.out.println(product.toJSON());
			// }
			//
			// DBAccess.emptyTable("Product");
			//
			// DBAccess.insertModelDataBatch(productList);

			 sheetExtractor = new SheetExtractor(poiReader.getSheetById(1));
			
			 Bundle[] bundleList = sheetExtractor.extractBundles();
			
			 System.out.println("Row Num: " + bundleList.length);
			
			 for (Bundle bundle : bundleList) {
			 System.out.println(bundle.toJSON());
			 }
			
			 DBAccess.emptyTable("bundle");
			
			 DBAccess.insertModelDataBatch(bundleList);

			sheetExtractor = new SheetExtractor(poiReader.getSheetById(2));

			BundleProduct[] bundleProductList = sheetExtractor.extractBundleProducts();

			System.out.println("Row Num: " + bundleProductList.length);

			for (BundleProduct bundleProduct : bundleProductList) {
				System.out.println(bundleProduct.toJSON());
			}

			DBAccess.emptyTable("bundleProduct");

			DBAccess.insertModelDataBatch(bundleProductList);

			// System.out.println(productList.size());

		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
