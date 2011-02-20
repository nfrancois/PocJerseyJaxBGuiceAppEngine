package nfrancois.poc.jerseyjaxbguiceappengine;

import javax.ws.rs.core.MediaType;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;

import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class HelloResourceTest extends JerseyTest {
	
	@Override
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder()
							        .contextListenerClass(GuiceServletConfig.class)
							        .filterClass(GuiceFilter.class)
							        .servletPath("/")
							        .build();
	}
	
	
	@Test
	public void shoulReplyHello(){
		String relativeUrl = "hello";
		String name ="Nicolas";
		WebResource path = resource().path(relativeUrl).path(name);
		ClientResponse response = path.get(ClientResponse.class);
		assertThat(response.getType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		Hello hello = response.getEntity(Hello.class);
		assertThat(hello).isNotNull();
		assertThat(hello.getMessage()).isEqualTo("Hello");
		assertThat(hello.getName()).isEqualTo("Nicolas");
	}

}
