package crm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// Spring Web MVC provides support for web app initialization, makes sure code is automatically detected
// and used to initialize the servlet container. We only need to override some methods like below.
public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	// targeting config class
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { DemoAppConfig.class };
	}

	// mapping to the root of our app
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
