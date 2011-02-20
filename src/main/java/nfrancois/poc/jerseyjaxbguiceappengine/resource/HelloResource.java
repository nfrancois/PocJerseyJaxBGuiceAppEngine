package nfrancois.poc.jerseyjaxbguiceappengine.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;
import nfrancois.poc.jerseyjaxbguiceappengine.service.HelloService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Path("hello")
@Singleton
@Produces(MediaType.APPLICATION_XML)
public class HelloResource {
	
	@Inject
	private HelloService helloService;
	
	@GET
	@Path("/{name}")
	public Hello reply(@PathParam("name") String name){
		return helloService.saysHelloToSomeone(name);
	}
	
	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
	}
	
}
