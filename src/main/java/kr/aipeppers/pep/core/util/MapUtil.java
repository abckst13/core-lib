package kr.aipeppers.pep.core.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapUtil {

	/**
	 * Instantiates a new map util.
	 */
	private MapUtil() {
	}

	/**
	 * Gets the string.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the string
	 */
	public static String getString(Map<?, ?> map, Object key) {
		return StringUtil.toString(map.get(key));
	}

	/**
	 * Gets the int.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the int
	 */
	public static int getInt(Map<?, ?> map, Object key) {
		return NumberUtil.toInt(map.get(key));
	}

	/**
	 * Gets the long.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the long
	 */
	public static long getLong(Map<?, ?> map, Object key) {
		return NumberUtil.toLong(map.get(key));
	}

	/**
	 * Gets the double.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the double
	 */
	public static double getDouble(Map<?, ?> map, Object key) {
		return NumberUtil.toDouble(map.get(key));
	}

	/**
	 * Gets the big decimal.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the big decimal
	 */
	public static BigDecimal getBigDecimal(Map<?, ?> map, Object key) {
		return NumberUtil.toBigDecimal(map.get(key));
	}

	/**
	 * Gets the boolean.
	 *
	 * @param map the map
	 * @param key the key
	 * @return the boolean
	 */
	public static boolean getBoolean(Map<?, ?> map, Object key) {
		return Boolean.parseBoolean(StringUtil.toString(map.get(key)));
	}

	/**
	 * To map.
	 *
	 * @param obj the obj
	 * @return the map
	 */
	public static Map<String, Object> toMap(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for(int i=0; i<=fields.length-1;i++){
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}
			return resultMap;
		} catch (IllegalArgumentException e) {
			log.error("error : {}", e);
		} catch (IllegalAccessException e) {
			log.error("error : {}", e);
		}
		return null;
	}

	/**
	 * To bean.
	 *
	 * @param <T> the generic type
	 * @param box the box
	 * @param clazz the clazz
	 * @return the t
	 */
	public static <T> T toBean(Map<?, ?> map, Class<T> clazz) {
		return JsonUtil.convertValue(map, clazz);
	}

}
