package kr.gscaltex.gsc.core.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import kr.gscaltex.gsc.core.cont.CmnConst;
import kr.gscaltex.gsc.core.data.Box;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeanUtil {

	public static <T, K> List<T> convertList(List<K> list, Class<T> clazz) {
		List<T> resList = new ArrayList<T>();
		if(list != null) {
			if(list instanceof List) {
				for (K row : list) {
					resList.add(JsonUtil.convertValue(row, clazz));
				}
			}
		}
		return resList;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Box toBox(Object obj) {
		Box box = null;
		if(obj != null) {
			if(obj instanceof Map) {
				box = new Box((Map)obj);
			} else if(obj instanceof String) {
				String objStr = (String)obj;
				if(StringUtil.isNotEmpty(objStr)) {
					box = JsonUtil.toObject(objStr, Box.class);
				}
			} else {
				box = JsonUtil.convertValue(obj, Box.class);
			}
		}
		return box;
	}

	public static final <T> T convert(Object obj, Class<T> clazz) {
		if (obj == null) { return null; }
		if (obj instanceof Map) {
			return JsonUtil.convertValue(obj, clazz);
		} else if (obj instanceof String) {
			return JsonUtil.toObject((String)obj, clazz);
		} else {
			return JsonUtil.convertValue(obj, clazz);
		}
	}

	public static final <T> T convert(Object obj, TypeReference<T> typRef) {
		if (obj == null) { return null; }
		if (obj instanceof Map) {
			return JsonUtil.convertValue(obj, typRef);
		} else if (obj instanceof String) {
			return JsonUtil.toObject((String)obj, typRef);
		} else {
			return JsonUtil.convertValue(obj, typRef);
		}
	}

	@Deprecated
	public static final <T> T convertNullToDefault(T dto) {

//		log.debug(" >>> START convertNullToDefault");
//		log.debug("dto.getClass().getName(): {}", dto.getClass().getName());

		ReflectionUtils.doWithFields(dto.getClass(), field -> {
			try {
				//apache BeanUtils를 사용하면,
				//private 필드의 getter, setter를 찾아서 invoke할 필요없이 바로 가져올 수 있음.
				Class<?> fieldType = field.getType();
				Object fieldVal = PropertyUtils.getProperty(dto, field.getName());

				if(ClassUtils.isPrimitiveOrWrapper(fieldType)) {
					fieldType = ClassUtils.primitiveToWrapper(fieldType);
				}

//				log.debug("fieldType: {}", fieldType);
//				log.debug("fieldVal: {}", fieldVal);

				//값이 null인 경우, 각 type에 맞는 기본 값을 셋팅한다.
				if(fieldVal == null) {
					//1. String
					if(fieldType.isAssignableFrom(String.class)) {
						//      AS-IS는 " " 로 들어가게 했었는데.
						//              그냥 ""로 할 경우에 DBMS에서 null관련 오류가 안난다면 ""로 하도록 하자. 테스트 필요
						PropertyUtils.setProperty(dto, field.getName(), CmnConst.STR_EMPTY);
//						PropertyUtils.setProperty(dto, field.getName(), CmnConst.STR_SPACE);
					}

					//2. BigDecimal
					else if(fieldType.isAssignableFrom(BigDecimal.class)) {
						PropertyUtils.setProperty(dto, field.getName(), BigDecimal.ZERO);
					}

					//3. Boolean
					else if(fieldType.isAssignableFrom(Boolean.class)) {
						PropertyUtils.setProperty(dto, field.getName(), false);
					}

					//4. Number
					else if(fieldType.isAssignableFrom(Number.class)) {
						PropertyUtils.setProperty(dto, field.getName(), 0);
					}

				}
				//값이 null이 아닌 경우, nested type에 대하여 처리
				else {
					//1. List
					if(fieldType.isAssignableFrom(List.class)) {
						PropertyUtils.setProperty(dto, field.getName(), convertNullToDefault(fieldVal));
					}

					//2. Array
					else if(fieldType.isArray()) {
						PropertyUtils.setProperty(dto, field.getName(), convertNullToDefault(fieldVal));
					}

					//3. Object
					else if(fieldType.isAssignableFrom(Object.class)) {
						PropertyUtils.setProperty(dto, field.getName(), convertNullToDefault(fieldVal));
					}
				}

			} catch(NoSuchMethodException nsme) {
				//setter/getter 등이 정의되지 않은 field
				//VO내에 정의하지 않은 필드나 Collection 클래스등 내부에서 사용되는 private 변수 등에 해당
				//일일이 fieldName으로 필터를 걸기 번거로워, NoSuchMethodException을 catch하여 무시한다.
				log.warn(nsme.getMessage());

			} catch (Exception e) {
				log.warn("convertNullToDefault failed!", e);
			}
		});

//		log.debug(" >>> E N D convertNullToDefault");

		return dto;
	}

	@SuppressWarnings("unchecked")
	public static final Object objConvertNullToDefault(Object obj) {
		if (obj instanceof Map) {
			Box box = (Box)obj;
			Box rtnBox = new Box();
			rtnBox.putAll(box);
			for (String key : rtnBox.keySet()) {
				if (rtnBox.get(key) == null) {
	//				String keyStr = key.toString();
	//				log.debug("keyStr : {}, value : ", keyStr, rtnBox.get(keyStr));
					rtnBox.put(key.toString(), CmnConst.STR_EMPTY);
				} else if (rtnBox.get(key) instanceof ArrayList) {
					List<Box> list = (ArrayList<Box>)rtnBox.get(key);
					List<Box> rtnInnerList = new ArrayList<Box>();
					if (null != list && !list.isEmpty()) {
						for (Map<String, Object> innerMap : list) {
							if (null == innerMap || innerMap.isEmpty()) {
								break;
							}
							Box rtnInnerBox = new Box();
							for (String innerKey : innerMap.keySet()) {
								String innerKeyStr = innerKey.toString();
								rtnInnerBox.put(innerKeyStr, innerMap.get(innerKeyStr) == null ? CmnConst.STR_SPACE : innerMap.get(innerKeyStr));
							}
							rtnInnerList.add(rtnInnerBox);
						}
					}
					rtnBox.put(key, rtnInnerList);
				}
			}

//			((Map) obj).clear();
			((Map) obj).putAll(rtnBox);
			return obj;
//			return (Object)rtnBox;
		} else {
			return obj;
		}
	}
}

