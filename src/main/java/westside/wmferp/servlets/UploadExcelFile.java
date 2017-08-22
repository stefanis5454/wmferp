package westside.wmferp.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import westside.wmferp.models.Bundle;
import westside.wmferp.models.BundleProduct;
import westside.wmferp.models.Product;
import westside.wmferp.util.DBAccess;
import westside.wmferp.util.PoiReader;
import westside.wmferp.util.SheetExtractor;

/**
 * Servlet implementation class UploadExcelFile
 */
@MultipartConfig
public class UploadExcelFile extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(UploadExcelFile.class.getCanonicalName());

	public UploadExcelFile() {
		super();
	}

	/**
	 * accept only post request
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// Create path components to save the file
		// final String path = request.getParameter("destination");
		String realPath = getServletContext().getRealPath("/");
		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		final String path = realPath + "/uploaded";

		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {

			file.mkdir();
		}

		String filePath = path + File.separator + fileName;
		final long size = filePart.getSize();
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		try {

			// Upload file to server
			out = new FileOutputStream(new File(filePath));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			// writer.println("New file " + fileName + " created at " + path);
			LOGGER.log(Level.INFO, "File" + fileName + "being uploaded to " + path);
			PoiReader poiReader = new PoiReader(new File(filePath));
			SheetExtractor sheetExtractor = new SheetExtractor(poiReader.getSheetById(0));

			Product[] productList = sheetExtractor.extractProducts();

			if (!DBAccess.emptyTable("Product")) {
				writer.println("Failed to empty the product table");
				return;
			}

			if (!DBAccess.insertModelDataBatch(productList)) {
				writer.println("Failed to load the product table");
				return;
			}

			sheetExtractor = new SheetExtractor(poiReader.getSheetById(1));

			Bundle[] bundleList = sheetExtractor.extractBundles();

			if (!DBAccess.emptyTable("Bundle")) {
				writer.println("Failed to empty the bundle table");
				return;
			}

			if (!DBAccess.insertModelDataBatch(bundleList)) {
				writer.println("Failed to load the bundle table");
				return;
			}

			sheetExtractor = new SheetExtractor(poiReader.getSheetById(2));

			BundleProduct[] bundleProductList = sheetExtractor.extractBundleProducts();

			if (!DBAccess.emptyTable("BundleProduct")) {
				writer.println("Failed to empty the bundleProduct table");
				return;
			}

			if (!DBAccess.insertModelDataBatch(bundleProductList)) {
				writer.println("Failed to load the bundleProduct table");
				return;
			}

			// Read the excel file by POI and create the table data
			writer.println("Success");

		} catch (FileNotFoundException fne) {

			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());

			LOGGER.log(Level.ERROR, "Problems during file upload. Error: " + fne.getMessage());
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}

	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOGGER.log(Level.INFO, "Part Header = " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}
