package wordpuzzle.ussdmodule.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import wordpuzzle.ussdmodule.handler.UssdHandler;
import wordpuzzle.ussdmodule.models.receiveformat.UssdReceiveFormat;

/**
 * Servlet implementation class UssdReceiver
 */
@WebServlet("/UssdReceiver")
public class UssdReceiver extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UssdReceiver() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = request.getReader();
		Gson gson = new GsonBuilder().create();
		
		UssdReceiveFormat receiveFormat = gson.fromJson(reader, UssdReceiveFormat.class);
		
		UssdHandler ussdHandler = new UssdHandler();
		try {
			ussdHandler.divider(receiveFormat);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
