package kr.gscaltex.gsc.core.config;

//@Slf4j
//@RequiredArgsConstructor
//@Configuration
public class RedisTemplateConfig {

    /*
    @Value("${spring.redis.port}")
    public int port;

    @Value("${spring.redis.host}")
    public String host;

    //  @Value("${spring.redis.password}")
    //  public String password;

    //  private final RedisProperties redisProperties;

    @Bean(name="redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
    //      redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);

    //      RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
    //              .master(redisProperties.getSentinel().getMaster());
    //      redisSentinelConfiguration.setPassword(password);
    //      redisProperties.getSentinel().getNodes().forEach(s -> redisSentinelConfiguration.sentinel(s.split(":")[0], Integer.valueOf(s.split(":")[1])));
    //      LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder().build();
    //      return new LettuceConnectionFactory(redisSentinelConfiguration, lettucePoolingClientConfiguration);

    //      RedisClusterConfiguration redisCluserConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
    //      redisCluserConfiguration.setPassword(password);
    //      LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder().build();
    //      return new LettuceConnectionFactory(redisCluserConfiguration, lettucePoolingClientConfiguration);
    }
    */
}
