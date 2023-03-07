//ckage com.maps.config;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.stereotype.Component;
//
//import com.maps.common.entity.AppConfig;
//import com.maps.common.repository.AppConfigRepository;
//import com.maps.exception.ResourceNotFoundException;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class ApplicationConfigService {
//
//	private final AppConfigRepository appConfigRepository;
//
//	private Map<String, String> allAppConfig;
//	private Map<String, List<AppConfig>> allAppConfigByCategory;
//
//	@PostConstruct
//	public void loadConfig() {
//		this.allAppConfigByCategory = appConfigRepository.findAll().stream()
//				.collect(Collectors.groupingBy(AppConfig::getCategory));
//		
//	}
//
//	public void refreshAll() {
//		this.loadConfig();
//	}
//
//	public String getValue(String key, String category) {
//		List<AppConfig> list = allAppConfigByCategory.get(category);
//		
//		if(Objects.isNull(list))
//			throw new ResourceNotFoundException(String.format("No category found with name %s ", category));
//		
//		Optional<AppConfig> configOpt = list.stream()
//				.filter(config -> config.getKey().equals(key)).findAny();
//		
//		if (!configOpt.isPresent())
//			throw new ResourceNotFoundException(String.format("Key: %s not found under category: %s ", key, category));
//
//		return configOpt.get().getValue();
//
//	}
//
//	public List<AppConfig> getConfigList(String category) {
//		List<AppConfig> list = allAppConfigByCategory.get(category);
//		if (list == null || list.isEmpty())
//			throw new ResourceNotFoundException(String.format("Category: %s not found ", category));
//
//		return list;
//	}
//
//	public Map<String, List<AppConfig>> getAllConfigByCategory() {
//		return this.allAppConfigByCategory;
//	}
//	
//	public String getValue(String key) {
//		String config = allAppConfig.get(key);
//		if (Objects.isNull(config))
//			throw new ResourceNotFoundException(String.format("Key: %s not found in config", key));
//
//		return config;
//		}
//	}