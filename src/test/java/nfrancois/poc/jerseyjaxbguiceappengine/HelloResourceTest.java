package nfrancois.poc.jerseyjaxbguiceappengine;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;

import static org.fest.assertions.Assertions.*;
import org.junit.Test;

import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class HelloResourceTest extends JerseyTest {
	
    public HelloResourceTest() {
        super(new WebAppDescriptor.Builder()
                .contextListenerClass(GuiceServletConfig.class)
                .filterClass(GuiceFilter.class)
                .servletPath("/")
                .build());
    }
	
	
	@Test
	public void shoulReplyHello(){
		String relativeUrl = "hello";
		String name ="Nicolas";
		WebResource path = resource().path(relativeUrl).path(name);
		assertThat(path.getURI().toString()).isEqualTo(getFullUrl(relativeUrl, name));
		int status = path.getRequestBuilder().head().getStatus();
		assertThat(status).isEqualTo(Status.OK.getStatusCode());
		Hello response = path.get(Hello.class);
		assertThat(response).isNotNull();
		assertThat(response.getMessage()).isEqualTo("Hello");
		assertThat(response.getName()).isEqualTo("Nicolas");
	}

	private String getFullUrl(String relativeUrl, String name){
		return getBaseURI().toString()+relativeUrl+"/"+name;
	}	
	
}
