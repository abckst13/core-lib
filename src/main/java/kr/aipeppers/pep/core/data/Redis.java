package kr.aipeppers.pep.core.data;

//@Slf4j
//@Component
public class Redis<T> {

    /*
    private RedisTemplate<String, T> redisTemplate;
    private ValueOperations<String, T> valueOperations;

    @Autowired
    public Redis(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void set(String key, T value) {
        valueOperations.set(CmnConst.REDIS_BIZ + key, value, 1, TimeUnit.DAYS);
    }

    public void setBizWithSid(String key, T value) {
        set(SessionUtil.getSid() + "-" + key, value);
    }

    public void setCmn(String key, T value) {
        valueOperations.set(CmnConst.REDIS_CMN + key, value);
    }

    public T get(String key) {
        return valueOperations.get(CmnConst.REDIS_BIZ + key);
    }

    public T getBizWithSid(String key) {
        return get(SessionUtil.getSid() + "-" + key);
    }

    public T getCmn(String key) {
        return valueOperations.get(CmnConst.REDIS_CMN + key);
    }

    public void setString(String key, T value) {
        valueOperations.set(CmnConst.REDIS_BIZ + key, value, 1, TimeUnit.DAYS);
    }

    public void setStringBizWithSid(String key, T value) {
        setString(SessionUtil.getSid() + "-" + key, value);
    }

    public void setCmnString(String key, T value) {
        valueOperations.set(CmnConst.REDIS_CMN + key, value);
    }

    public String getString(String key) {
        return (String) valueOperations.get(CmnConst.REDIS_BIZ + key);
    }

    public String getStringBizWithSid(String key) {
        return getString(SessionUtil.getSid() + "-" + key);
    }

    public String getCmnString(String key) {
        return (String) valueOperations.get(CmnConst.REDIS_CMN + key);
    }

    public void setExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(CmnConst.REDIS_BIZ + key, timeout, unit);
    }

    public void setExpireBizWithSid(String key, long timeout, TimeUnit unit) {
        setExpire(SessionUtil.getSid() + "-" + key, timeout, unit);
    }

    public void setCmnExpire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(CmnConst.REDIS_CMN + key, timeout, unit);
    }


    public void remove(String key) {
    //		valueOperations.set(CmnConst.REDIS_BIZ + key, null, 1, TimeUnit.MILLISECONDS);
        redisTemplate.expire(CmnConst.REDIS_BIZ + key, 1, TimeUnit.MILLISECONDS);
    }

    public void removeBizWithSid(String key) {
        remove(SessionUtil.getSid() + "-" + key);
    }

    public void removeCmn(String key) {
        redisTemplate.expire(CmnConst.REDIS_CMN + key, 1, TimeUnit.MILLISECONDS);
    }
    */
}


