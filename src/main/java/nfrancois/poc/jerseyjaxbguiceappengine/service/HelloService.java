package nfrancois.poc.jerseyjaxbguiceappengine.service;

import nfrancois.poc.jerseyjaxbguiceappengine.model.Hello;

import com.google.inject.Singleton;

@Singleton
public class HelloService {
	
	public Hello saysHelloToSomeone(String who){
		return new Hello("Hello",who);
	}

}
