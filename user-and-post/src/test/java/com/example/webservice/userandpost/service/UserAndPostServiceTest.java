package com.example.webservice.userandpost.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.example.webservice.userandpost.helper.RestClientHelper;
import com.example.webservice.userandpost.service.impl.UserAndPostServiceImpl;
import com.example.webservice.userandpost.valueobject.Address;
import com.example.webservice.userandpost.valueobject.Company;
import com.example.webservice.userandpost.valueobject.Geography;
import com.example.webservice.userandpost.valueobject.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {UserAndPostServiceImpl.class, RestClientHelper.class, RestTemplateAutoConfiguration.class})
public class UserAndPostServiceTest {

	@Autowired
	UserAndPostService service;
	
	@Autowired
	RestClientHelper helper;
	
	@Autowired
	RestTemplate restTemplate;
	
	private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
 
    @Test
	public void testGetAllUsers_postive() {
		service.getAllUsers();
		assertFalse(service.getAllUsers().isEmpty());
	}
	
	@Test
	public void testGetOneUser_id1_positive() {
		assertNotNull(service.getUser(1));
	}

	@Test()
	public void testGetUserNotFound_negative_404() {
		assertNull(service.getUser(50));
	}
	
	@Test()
	public void testGetPostsForUser_positve() {
		assertNotNull(service.getPostsForUser(2));
	}
	
	@Test()
	public void testGetUsersPostsMapping_positve() {
		assertFalse(service.getUsersPostsMapping().isEmpty());
	}
	
	private User populateUser() {
		Company company = new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");
		Geography geo = new Geography(-37.3159, 81.1496);
		Address address = new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo);
		User user = new User(1, "Leanne Graham", "Bret", "Sincere@april.biz", address, "1-770-736-8031 x56442", "hildegard.org", company);
		return user;
	}
	
	
	//TODO: WIP, sanity is working. Need more exploring.
	@Test                                                                                         
    public void givenMockingIsDoneByMockRestServiceServer_whenGetIsCalled_thenReturnsMockedObject() throws JsonProcessingException, URISyntaxException{
		User user = populateUser();
		mockServer = MockRestServiceServer.createServer(restTemplate); //@Before is not working. May be junit version issue.
        mockServer.expect(ExpectedCount.once(), 
          requestTo(new URI("http://jsonplaceholder.typicode.com/users")))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(new User[] {user}))
        );                                   
                        
        User user1 = service.getUser(1);
        mockServer.verify();
        assertEquals(user.toString(), user1.toString());                                                        
    }
}
