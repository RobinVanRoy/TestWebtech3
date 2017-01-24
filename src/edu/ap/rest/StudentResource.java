package edu.ap.rest;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import edu.ap.jdbc.JDBConnection;

public class StudentResource extends ServerResource
{
    @Get
	public String allStudents() {
		
		JDBConnection c = JDBConnection.getJDBConnection();
		//JDBConnection c = new JDBConnection();
		c.openConnection("students", "root", "root");
		ArrayList<String> resultArray = c.selectAll();
		c.closeConnection();
		
		JSONObject json = new JSONObject();
		json.put("operation", "selectAll");
		json.put("length", resultArray.size());
		JSONArray jsonArray = new JSONArray();
		
		int i = 0;
		
		for(String s : resultArray) {
			JSONObject obj = new JSONObject();
			obj.put("" + i, s);
			jsonArray.put(obj);
			i++;
		}
		
		json.put("result", jsonArray);
		return json.toString();
	}
	
	@Post("txt")
	public void newStudent(String json) {
		
		JSONObject newStudent = new JSONObject(json);
		String lastName = newStudent.getString("LastName");
		String firstName = newStudent.getString("FirstName");
		int grade = newStudent.getInt("Grade");
				
		JDBConnection c = JDBConnection.getJDBConnection();
		//JDBConnection c = new JDBConnection();
		c.openConnection("students", "root", "root");
		c.executeInsert("student", lastName, firstName, grade);
		c.closeConnection();
	}

}
