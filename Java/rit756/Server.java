package rit756;

import java.net.*;
import java.util.Properties;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Server {
	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		 
		
		try {
			prop.load(input);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
 
		
		ServerSocket ss = null;
		int port=Integer.parseInt(prop.getProperty("port"));
		
		try {

			ss = new ServerSocket(port);
			Socket cs = null;
			while (true) {
				cs = ss.accept();
				ThreadedServer ths = new ThreadedServer(cs,prop);
				ths.start();
			}
		} catch (BindException be) {
			System.out
					.println("Server already running on this computer, stopping.");
		} catch (IOException ioe) {
			System.out.println("IO Error");
			ioe.printStackTrace();
		}

	}
}

class ThreadedServer extends Thread {
	Socket cs;
	Properties properties;

	public ThreadedServer(Socket cs,Properties prop) {
		this.cs = cs;
		this.properties=prop;
	}

	@SuppressWarnings("unchecked")
	public void run() {

		try {
			BufferedReader br;
			PrintWriter opw;
			System.out.println("New connection");
			br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			opw = new PrintWriter(new OutputStreamWriter(cs.getOutputStream()));
			boolean status = false;
			Contact contact = new Contact();
			
			
			JDBMethods jdbmethods = new JDBMethods(properties.getProperty("dbhost"), properties.getProperty("dbport"), properties.getProperty("dbuser"),
					properties.getProperty("dbpassword"), properties.getProperty("dbname"));
			
			//JDBMethods jdbmethods = new JDBMethods("localhost", "3306","root", "root", "RIT");
			
			String json = br.readLine();

			Object o = JSONValue.parse(json);
			JSONObject jo = (JSONObject) o;
			String method = (String) jo.get("method");

			if (method.equals("selectOnId")) {

				JSONObject obj = new JSONObject();
				contact.setId(Integer.parseInt((String) jo.get("data")));
				status=jdbmethods.selectOnId(contact);
				obj.put("FirstName", contact.getFirstName());
				obj.put("Id", contact.getId());
				obj.put("LastName", contact.getLastName());
				obj.put("CardNumber", contact.getCardNumber());
				obj.put("City", contact.getCity());
				obj.put("Email", contact.getEmail());
				obj.put("MiddleInitial", contact.getMiddleInitial());
				obj.put("State", contact.getState());
				obj.put("Zip", contact.getZip());
				obj.put("Phone", contact.getPhone());
				obj.put("CreditCardType", contact.getCardtype());
				obj.put("Street", contact.getStreet());
				StringWriter out = new StringWriter();
				
					obj.writeJSONString(out);
					String jsonText = out.toString();
					String response="{\"data\":"+jsonText+",\"status\":\"" + status + "\"}";
					System.out.println(response);
					opw.write(response);

			} else if (method.equals("insert")) {

				JSONObject jsondata = (JSONObject) jo.get("data");
				contact.setCardNumber((String) jsondata.get("CardNumber"));
				contact.setCity((String) jsondata.get("City"));
				contact.setFirstName((String) jsondata.get("FirstName"));
				contact.setLastName((String) jsondata.get("LastName"));
				contact.setId(Integer.parseInt((String) jsondata.get("Id")));
				contact.setEmail((String) jsondata.get("Email"));
				contact.setMiddleInitial((String) jsondata.get("MiddleInitial"));
				contact.setState((String) jsondata.get("State"));
				contact.setZip((String) jsondata.get("Zip"));
				contact.setPhone((String) jsondata.get("Phone"));
				contact.setCardtype((String) jsondata.get("CreditCardType"));
				contact.setStreet((String) jsondata.get("Street"));
				status = jdbmethods.insert(contact);
				opw.write("{'status':'" + status + "'}");
			}

			else if (method.equals("update")) {
				JSONObject jsondata = (JSONObject) jo.get("data");
				contact.setCardNumber((String) jsondata.get("CardNumber"));
				contact.setCity((String) jsondata.get("City"));
				contact.setFirstName((String) jsondata.get("FirstName"));
				contact.setLastName((String) jsondata.get("LastName"));
				contact.setId(Integer.parseInt((String) jsondata.get("Id")));
				contact.setEmail((String) jsondata.get("Email"));
				contact.setMiddleInitial((String) jsondata.get("MiddleInitial"));
				contact.setState((String) jsondata.get("State"));
				contact.setZip((String) jsondata.get("Zip"));
				contact.setPhone((String) jsondata.get("Phone"));
				contact.setCardtype((String) jsondata.get("CreditCardType"));
				contact.setStreet((String) jsondata.get("Street"));
				status = jdbmethods.update(contact);
				opw.write("{'status':'" + status + "'}");

			} else if (method.equals("selectOnLastName")) {


				JSONObject obj = new JSONObject();
				System.out.println((String)jo.get("data"));
				contact.setLastName((String) jo.get("data"));
				status=jdbmethods.selectOnLastName(contact);
				obj.put("FirstName", contact.getFirstName());
				obj.put("Id", contact.getId());
				obj.put("LastName", contact.getLastName());
				obj.put("CardNumber", contact.getCardNumber());
				obj.put("City", contact.getCity());
				obj.put("Email", contact.getEmail());
				obj.put("MiddleInitial", contact.getMiddleInitial());
				obj.put("State", contact.getState());
				obj.put("Zip", contact.getZip());
				obj.put("Phone", contact.getPhone());
				obj.put("CreditCardType", contact.getCardtype());
				obj.put("Street", contact.getStreet());
				StringWriter out = new StringWriter();
				
					obj.writeJSONString(out);
					String jsonText = out.toString();
					//System.out.print(jsonText);
					String response="{\"data\":"+jsonText+",\"status\":\"" + status + "\"}";
					System.out.println(response);
					opw.write(response);

			
				
				
				
			} else if (method.equals("delete")) {
				contact.setId(Integer.parseInt((String) jo.get("data")));
				status=jdbmethods.delete(contact);
				opw.write("{'status':'" + status + "'}");
			}

			opw.flush();
			cs.close();
			System.out.println("connection closed");

		} catch (IOException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
	}

}
