package westside.wmferp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import westside.wmferp.util.DBAccess;

/**
 * Servlet implementation class getProductInfo
 */
public class GetProductInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(GetProductInfo.class.getCanonicalName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetProductInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");

		response.setHeader("content-type", "text/html;charset=UTF-8");

		final PrintWriter writer = response.getWriter();

		String id = request.getParameter("id");

		HashMap productInfo = new HashMap();

		productInfo.put("product", DBAccess.getProductDetal(id));

		productInfo.put("relatedBundles", DBAccess.getRelatedBundles(id));

		String returnJSON = JSON.toJSONString(productInfo);

		LOGGER.info(returnJSON);

		writer.print(returnJSON);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
