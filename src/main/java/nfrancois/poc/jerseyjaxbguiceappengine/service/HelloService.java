package nfrancois.poc.jerseyjaxbguiceappengine.service;

import javax.inject.Singleton;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;

@Singleton
public class HelloService {
	
	public Hello saysHelloToSomeone(String who){
		return new Hello("Hello",who);
	}
	
	public Hello sendHello(String name) {
		return new Hello("Hello",name);
	}
	
}
