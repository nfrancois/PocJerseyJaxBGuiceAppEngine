package nfrancois.poc.jerseyjaxbguiceappengine;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.MediaType;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;
import nfrancois.poc.jerseyjaxbguiceappengine.resource.HelloResource;
import nfrancois.poc.jerseyjaxbguiceappengine.service.HelloService;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class HelloResourceTest extends JerseyTest {
	
	private static Injector injector;	
	private HelloService helloServiceMock;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		HelloResource helloResource =  injector.getInstance(HelloResource.class);
		helloServiceMock = mock(HelloService.class);
		helloResource.setHelloService(helloServiceMock);
	}	
	
	@Override
	protected AppDescriptor configure() {
		injector = Guice.createInjector(new ServletModule() {
			@Override
			protected void configureServlets() {
				bind(HelloResource.class);
				serve("/*").with(GuiceContainer.class);
			}
		});		
		return new WebAppDescriptor.Builder()
							        .contextListenerClass(GuiceTestConfig.class)
							        .filterClass(GuiceFilter.class)
							        .servletPath("/")
							        .build();
	}
	
	
	@Test
	public void shoulReplyHello(){
		String message = "Hello";
		String name ="Nicolas";
		when(helloServiceMock.saysHelloToSomeone("Nicolas")).thenReturn(new Hello(message, name));
		
		WebResource path = resource().path("hello").path(name);
		ClientResponse response = path.get(ClientResponse.class);
		assertThat(response.getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(response.getType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
		Hello hello = response.getEntity(Hello.class);
		assertThat(hello).isNotNull();
		assertThat(hello.getMessage()).isEqualTo("Hello");
		assertThat(hello.getName()).isEqualTo("Nicolas");
	}
	
	private class GuiceTestConfig extends GuiceServletContextListener {
		@Override
		public Injector getInjector() {
			return injector;
		}
	}	

}
