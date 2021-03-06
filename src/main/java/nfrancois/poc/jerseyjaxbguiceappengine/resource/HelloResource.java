package nfrancois.poc.jerseyjaxbguiceappengine.resource;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;
import nfrancois.poc.jerseyjaxbguiceappengine.service.HelloService;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;

@Path("hello")
@RequestScoped
@Produces(MediaType.APPLICATION_XML)
public class HelloResource {
	
	@Context 
	UriInfo uriInfo;	
	
	@Inject
	private HelloService helloService;
	
	@GET
	@Path("/{name}")
	public Hello reply(@PathParam("name") String name){
		return helloService.saysHelloToSomeone(name);
	}

	@POST
	public Response send(String name){
		Hello hello = helloService.sendHello(name);
		URI uri = uriInfo.getAbsolutePathBuilder().build();
		return Response.created(uri).entity(hello).build();
	}		
	
	
	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
	}
	
}
