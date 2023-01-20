package kr.gscaltex.gsc.core.config;

//@Configuration
public class RedisCacheConfig {

    /*
    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    RedisConnectionFactory connectionFactory;
    
    
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(defaultConf())
    //              .withInitialCacheConfigurations(confMap())
                .build();
    }
    
    
    private RedisCacheConfiguration defaultConf() {
        return RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())).entryTtl(Duration.ofSeconds(120));
    }
    
    // 사용캐시별로 TTL 설정
    //  private Map<String,RedisCacheConfiguration> confMap(){
    //      Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    //
    //      cacheConfigurations.put("getCommonCode", defaultConf().entryTtl(Duration.ofSeconds(120)));
    //      cacheConfigurations.put("getMessage", defaultConf().entryTtl(Duration.ofSeconds(120)));
    //
    //      return cacheConfigurations;
    //  }
    */
}
