

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, String> data = new HashMap<>();
	private String key, value;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter()
		.append("<form action=\"/Servlet/Servlet\" method=\"post\">\r\n" + 
				"  Key:<br>\r\n" + 
				"  <input type=\"text\" name=\"key\">\r\n" + 
				"  <br>\r\n" + 
				"  Value:<br>\r\n" + 
				"  <input type=\"text\" name=\"value\">\r\n" + 
				"  <br><br>\r\n" + 
				"  <input type=\"submit\" value=\"Submit\">\r\n" + 
				"</form> ");
		
		String query = request.getQueryString();
		
		if (query != null) {
			response.getWriter()
			.append(System.lineSeparator())
			.append(String.format("Value: %s", data.get(query)));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<String> tokens = Arrays.asList(
				request
				.getReader()
				.readLine()
				.split("&"))
				.stream()
				.map(p -> p.split("=")[1])
				.collect(Collectors.toList());
		
		key = tokens.get(0);
		value = tokens.get(1);
		
		data.put(key, value);
		
		welcome(request, response);
	}

	protected void welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter()
		.append(String.format("Key: %s%nValue: %s", key, value));
	}
}
