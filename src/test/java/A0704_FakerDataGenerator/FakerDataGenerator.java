package A0704_FakerDataGenerator;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class FakerDataGenerator {
	
	@Test
	void testGenerateDummyData() {
		Faker faker = new Faker();
		
		String fullname = faker.name().fullName();
		String firstname = faker.name().firstName();
		String lastname = faker.name().lastName();
		
		String username = faker.name().username();
		String password = faker.internet().password();
		
		String cellphone = faker.phoneNumber().cellPhone();
		
		String email = faker.internet().safeEmailAddress();
		
		System.out.println("Full Name :" + fullname);
		System.out.println("First Name :" +firstname);
		System.out.println("Last Name :" +lastname);
		System.out.println("Username :" +username);
		System.out.println("Password is :" +password);
		System.out.println("Cell Phone Number :" +cellphone);
		System.out.println("Email :" +email);
		
		
	}

}
