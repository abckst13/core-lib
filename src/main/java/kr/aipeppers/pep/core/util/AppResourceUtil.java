package kr.aipeppers.pep.core.util;

import org.springframework.core.io.Resource;

public class AppResourceUtil {

	/**
	 *
	 * @param resourceLocation the resource location
	 * @return the config resource
	 */
	public static Resource getConfigResource() {
		String configPath = System.getProperty("config.path");
		StringBuffer resourceLocation = new StringBuffer();
		if(StringUtil.isEmpty(configPath)) {
			resourceLocation.append("classpath:application.yml");
		} else {
			resourceLocation.append(configPath);
		}
		return ResourceUtil.getResource(resourceLocation.toString());
	}

}


//public class AppResourceUtil implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//	@Override
//	public void initialize(ConfigurableApplicationContext applicationContext) {
//		try {
//			Resource resource = applicationContext.getResource("classpath:application.yml");
//			YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
//			List<PropertySource<?>> yamlProperties = sourceLoader.load("yamlProperties", resource);
//			if (yamlProperties != null && !yamlProperties.isEmpty()) {
//				applicationContext.getEnvironment().getPropertySources().addFirst(yamlProperties.get(0));
//			}
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//}
