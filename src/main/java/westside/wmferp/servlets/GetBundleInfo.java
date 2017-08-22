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
 * Servlet implementation class getBundleInfo
 */
public class GetBundleInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(GetBundleInfo.class.getCanonicalName());
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBundleInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");

		response.setHeader("content-type", "text/html;charset=UTF-8");

		final PrintWriter writer = response.getWriter();

		String id = request.getParameter("id");

		HashMap bundleInfo = new HashMap();

		bundleInfo.put("bundle", DBAccess.getBundleDetal(id));

		bundleInfo.put("relatedProducts", DBAccess.getRelatedProducts(id));

		String returnJSON = JSON.toJSONString(bundleInfo);

		LOGGER.info(returnJSON);

		writer.print(returnJSON);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
