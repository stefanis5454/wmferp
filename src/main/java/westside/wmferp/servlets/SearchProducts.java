package westside.wmferp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.alibaba.fastjson.JSON;

import westside.wmferp.models.Product;
import westside.wmferp.util.DBAccess;
import westside.wmferp.models.Bundle;

/**
 * Servlet implementation class SearchProducts
 */
public class SearchProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(SearchProducts.class.getCanonicalName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchProducts() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");

		response.setHeader("content-type", "text/html;charset=UTF-8");

		final PrintWriter writer = response.getWriter();

		String keyword = request.getParameter("keyword");

		LOGGER.info("Request to search by " + keyword);

		HashMap searchResult = new HashMap();

		searchResult.put("products", DBAccess.searchProductByKeyword(keyword));

		searchResult.put("bundles", DBAccess.searchBundleByKeyword(keyword));

		String returnJSON = JSON.toJSONString(searchResult);

		writer.print(returnJSON);
	}

}
