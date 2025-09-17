package A0602SerializationDeserialization;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//POJO  ----(Serialization)-----> JSON Object-------(De-Serialization)------>POJO 
public class SerializationDeserialization {
	
	
	// POJO(Java Object) ----------------> JSON --(Serilization)
	@Test
	void convertPojo2JSON() throws JsonProcessingException {
		
		//create a JAVA object using POJO class
		Popo data = new Popo();
		data.setName("NameSer");
		data.setAge(21);
		data.setGrade("S++");
		String[] subject = {"sub01", "sub02", "sub03"};
		data.setSubjects(subject);
		
		//Converting java object  ----> json object (serilization)
		
		ObjectMapper objMapper = new ObjectMapper();
		
		String JsonData = objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
		
		System.out.println(JsonData);
	}
	
	
	//JSON ---------> Pojo(Java Object) --(De-Serilization)
	@Test
	void convertJSON2JavaObject() throws JsonMappingException, JsonProcessingException {
		String JsonObj = "{\r\n"
				+ "  \"name\" : \"NameSer\",\r\n"
				+ "  \"age\" : 25,\r\n"
				+ "  \"grade\" : \"S++\",\r\n"
				+ "  \"subjects\" : [ \"sub01\", \"sub02\", \"sub03\" ]\r\n"
				+ "}";
		
		//converting JSON data into POJO Class object
		ObjectMapper objMapper = new ObjectMapper();
		
		Popo sd = objMapper.readValue(JsonObj, Popo.class);
		
		System.out.println(sd.getName());
		System.out.println(sd.getAge());
		System.out.println(sd.getGrade());
		System.out.println(sd.getSubjects()[0]);
		System.out.println(sd.getSubjects()[1]);
		System.out.println(sd.getSubjects()[2]);
		
	}

}
