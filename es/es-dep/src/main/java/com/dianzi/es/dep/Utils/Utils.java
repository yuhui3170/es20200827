package com.dianzi.es.dep.Utils;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class Utils {

	public static Map<String, Object> MapAppend(Map<String, Object> argMap,
			String... args) {
		if (args != null || argMap != null) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			for (int i = 0; i < args.length; i++) {
				Map<String, Object> m = (Map<String, Object>) argMap
						.get(args[i]);
				resMap.putAll(m);
			}

			return resMap;
		}
		return null;
	}

	/**
	 * @author AOWII
	 * @describe 根据字段返回Map
	 * @date 2017-2-21
	 * @param argMap
	 * @param args
	 * @return
	 */
	public static Map<String, Object> MapAppoint(Map<String, Object> argMap,
			String... args) {
		if (args != null || argMap != null) {
			Map<String, Object> resMap = new HashMap<String, Object>();
			for (int i = 0; i < args.length; i++) {
				String val = (String) argMap.get(args[i]);
				resMap.put(args[i], val);
			}

			return resMap;
		}
		return null;
	}


	/**
	 * 生成一个Map,并将参数按照键值对，压入Map中
	 * 
	 * @param args
	 *            成对出现的键值对
	 * @return
	 * @author wot_chenyonggang
	 */
	public static Map<String, Object> makeQueryMap(Object... args) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (args == null || args.length == 0) {
			return map;
		}

		if ((args.length & 1) == 1) {
			throw new RuntimeException();
		}
		for (int i = 0; i < args.length - 1; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 * 生成一个Map,并将参数按照键值对，压入Map中
	 * 
	 * @param args
	 *            成对出现的键值对
	 * @return
	 * @author wot_chenyonggang
	 */
	public static Map<String, Object> makeQueryMap(Map map, Object... args) {
		if (map == null)
			map = new HashMap<String, Object>();
		if (args == null || args.length == 0) {
			return map;
		}

		if ((args.length & 1) == 1) {
			throw new RuntimeException();
		}
		for (int i = 0; i < args.length - 1; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	/**
	 * 生成一个List,并每两个生成一个Map，压入List中
	 * 
	 * @param args
	 *            成对出现的键值对
	 * @return
	 * @author wot_chenyonggang
	 */
	public static List<Map<String, Object>> makeQueryList(Object... args) {
		List list = new ArrayList<Map<String, Object>>();
		if (args == null || args.length == 0) {
			return list;
		}

		if ((args.length & 1) == 1) {
			throw new RuntimeException();
		}
		for (int i = 0; i < args.length - 1; i += 2) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", args[i]);
			map.put("val", args[i + 1]);
			list.add(map);
		}
		return list;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id;
	}

	public static Map<String, Object> goldVal(Object... args) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (args == null || args.length == 0) {
			return map;
		}

		if ((args.length & 1) == 1) {
			throw new RuntimeException("参数不够");
		}
		for (int i = 0; i < args.length - 1; i += 2) {
			map.put((String) args[i], args[i + 1]);
		}
		return map;
	}

	public static String getSysArgs(String key) throws IOException {
		InputStream in = Utils.class
				.getResourceAsStream("/parameter.properties");
		Properties p = new Properties();
		p.load(in);
		return p.getProperty(key);
	}

	/**
	 * 列表转为纵向的JSON对像(列表项为Map)
	 * 
	 * @param fieldsName
	 *            转换的成员域名，null代表全部
	 * @param list
	 * @param clazz
	 *            列表中对像的类型
	 * @param convertMap
	 *            需要自定义成员域的转换器集合，可为null
	 * @param dateFmt10
	 *            true 将所有时间转换为 "yyy-MM-dd"格式 *
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject listMapToJSONObject(String[] fieldsName,
			List list, Class clazz, Map<String, Convert> convertMap,
			boolean dateFmt10) {
		Object[] keys;
		HashSet keySet = new HashSet();
		if (fieldsName != null) {
			keys = fieldsName;
		} else {
			for (int i = 0; i < list.size(); i++) {
				Map<Object, Object> map = (Map<Object, Object>) list.get(i);
				keySet.addAll(map.keySet());
			}
			keys = keySet.toArray();
		}
		JSONArray[] arys = new JSONArray[keys.length];
		for (int i = 0; i < arys.length; i++) {
			arys[i] = new JSONArray();
		}
		Object value;
		Convert[] convs = new Convert[keys.length];
		if (convertMap != null) {
			for (int i = 0; i < keys.length; i++) {
				convs[i] = convertMap.get(keys[i]);
			}
		}

		for (Map<Object, Object> map : (List<Map<Object, Object>>) list) {
			for (int i = 0; i < arys.length; i++) {
				value = map.get(keys[i]);
				if (value != null) {
					if (convs[i] != null) {
						value = convs[i].convert(value);
					} else if (value instanceof Date) {
						if (dateFmt10) {
							value = new SimpleDateFormat("yyyy-MM-dd")
									.format(value);
						} else {
							value = new SimpleDateFormat("yyyy-MM")
									.format(value);
						}
					}
				}
				arys[i].add(value);
			}
		}
		JSONObject result = new JSONObject();
		try {
			for (int i = 0; i < arys.length; i++) {
				result.put(keys[i].toString().toLowerCase(), arys[i]);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 将指定类型的一组成员域数组放入缓冲Map中，并返回该数组。（使用缓冲）
	 * 
	 * @param clazz
	 *            类型
	 * @param keyName
	 *            索引名
	 * @param fieldsName
	 *            成员域的名称
	 * @return 指定成员域数组
	 */
	private synchronized static Field[] addFiled(Class clazz,
			String[] fieldsName, String keyName) {
		// 重新从缓冲表内获取，如果已经存在，则返回。（其他线程正好放入了）
		Field[] fields = (Field[]) fieldTable.get(keyName);
		if (fields != null) {
			return fields;
		}
		if (fieldsName == null) {
			fields = clazz.getDeclaredFields();
			boolean[] staticFlags = null;
			for (int i = 0; i < fields.length; i++) {
				if ((fields[i].getModifiers() & Modifier.STATIC) != 0) {
					if (staticFlags == null) {
						staticFlags = new boolean[fields.length];
					}
					staticFlags[i] = true;
				} else {
					fields[i].setAccessible(true);
				}
			}
			if (staticFlags != null) {
				int staticCount = 0;
				for (int j = 0; j < staticFlags.length; j++) {
					if (staticFlags[j]) {
						staticCount++;
					}
				}
				Field[] newFields = new Field[fields.length - staticCount];
				for (int j = 0, k = 0; j < fields.length; j++) {
					if (!staticFlags[j]) {
						newFields[k++] = fields[j];
					}
				}
				fields = newFields;
			}
			fieldTable.put(keyName, fields);
		} else {
			fields = new Field[fieldsName.length];
			for (int i = 0; i < fieldsName.length; i++) {
				try {
					fields[i] = clazz.getDeclaredField(fieldsName[i]);
					fields[i].setAccessible(true);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			fieldTable.put(keyName, fields);
		}
		return fields;
	}

	public static Map<String, Field[]> fieldTable = new HashMap<String, Field[]>();

	public static interface Convert {
		public Object convert(Object src);
	}

	public static Convert CONVERT_DATE_10 = new Convert() {
		public Object convert(Object src) {
			if (src == null) {
				return null;
			}
			return new SimpleDateFormat("yyyy-MM-dd").format((Date) src);
		}
	};

	public static boolean isEmpty(Object src) {
		if (src == null)
			return true;
		String s = src.toString();
		if (src == null || s.length() == 0) {
			return true;
		}
		for (int i = 0, len = s.length(); i < len; i++) {
			if (s.charAt(i) != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取异常信息的出错信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionContextMsg(Throwable e) {
		String result = null;
		if (e instanceof RuntimeException) {
			result = e.getMessage();
			if (result != null) {
				result = result.trim();
			}
			if (result != null && result.length() > 0) {
				return result;
			}

			result = e.getMessage();
			if (result != null) {
				result = result.trim();
			}
			if (result != null && result.length() > 0) {
				return result;
			}
		}
		String msg;
		Throwable causeException = e;
		Throwable currException;
		do {
			currException = causeException;
			if (currException instanceof RuntimeException) {
				result = e.getMessage();
				if (result != null) {
					result = result.trim();
				}
				if (result != null && result.length() > 0) {
					return result;
				}

				result = currException.getMessage();
				if (result != null) {
					result = result.trim();
				}
				if (result != null && result.length() > 0) {
					return result;
				}
			}
			msg = currException.getMessage();
			if (msg != null) {
				return msg;
			}
			causeException = currException.getCause();
		} while (causeException != null);
		return currException.getClass().getName();
	}

	public static Map listToMap(List<Map<String, Object>> list,
			String[] mapFields) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map<String, Object> m : list) {
			for (String key : mapFields) {
				List l = new ArrayList();
				Object object = m.get(key);
				Object object2 = map.get(key);
				if (object2 instanceof List) {
					((List) object2).add(object);
				} else {
					l.add(object);
					map.put(key, l);
				}
			}
		}
		return map;
	}

	public static boolean isAlpha(String src) {
		if (src == null) {
			return true;
		}
		int len = src.length();
		char c;
		for (int i = 0; i < len; i++) {
			c = src.charAt(i);
			if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

	/**
	 * 字符串转二进制
	 * 
	 * @param str
	 *            字符串
	 * @return byte数组
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseDate(String v) {
		SimpleDateFormat sdf = null;
		if (v.indexOf("-") > 0) {
			if (v.length() == 10) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			} else if (v.length() == 19) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
		} else if (v.indexOf("/") > 0) {
			if (v.length() == 10) {
				sdf = new SimpleDateFormat("yyyy/MM/dd");
			} else if (v.length() == 19) {
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			}
		} else {
			if (v.length() == 8) {
				sdf = new SimpleDateFormat("yyyyMMdd");
			} else if (v.length() == 14) {
				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			}
		}

		if (sdf == null) {
			throw new RuntimeException("Bad Date Foramt!");
		}

		try {
			return sdf.parse(v);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Object ifnull(Object expr1, Object expr2) {
		if (expr1 == null)
			return expr2;
		return expr1;
	}

	// Map转List
	public static List<Map<String, Object>> mapToList(
			Map<String, Object>... maps) {
		List<Map<String, Object>> list = new ArrayList();
		for (Map<String, Object> map : maps) {
			list.add(map);
		}
		return list;
	}

	public static String formatVal(Object obj) {
		if (obj == null || "".equals(obj)) {
			return "";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(obj);
	}

	public static String getMapFieldWithoutNULL(Map m, String field) {
		String res = "";
		if (m == null) {
			return "";
		}
		Object o = m.get(field);
		if (o == null) {
			return "";
		}
		if (o instanceof String) {
			res = (String) (((String) o).toLowerCase().equals("null") ? "" : o);
		}
		if (o instanceof Number) {
			res = String.valueOf(o);
		}
		return res;
	}

	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		return now;
	}

	// 为List中的每一条记录，对所有的URL_COLS字段，如果地址不是以http开头，则将基地址补充进去。
	public static void complteUrl(List<Map<String, Object>> list,
			String[] uRL_COLS) {
		if (list == null || list.size() == 0)
			return;
		if (uRL_COLS == null || uRL_COLS.length == 0)
			return;
		Object val;
		int i, len = uRL_COLS.length;
		for (Map<String, Object> item : list) {
			for (i = 0; i < len; i++) {
				val = item.get(uRL_COLS[i]);
				String type = (String) item.get("type");
				if (!"9".equals(type)) {
					if (!Utils.isEmpty(val) && val instanceof String
							&& !((String) val).startsWith("http")) {
						item.put(uRL_COLS[i], "http://saas.finpro.com/Finpro/"
								+ val);
					}
				}
			}
		}
	}

	/**
	 * 按全路径获取JSON对像中的属性值，如果不存在或JSON值为JSONObject.NULL,则返回null
	 * 
	 * @param jobj
	 *            全路径属性名 "user.data.12.name" user 属性的 data属性的 下标为12(JSONArray)
	 *            的name属性值
	 * @return JSON对像中的对应属性值，如果不存在或传入的是null,则返回null
	 * @author wot_chenyonggang
	 */
	public static Object getValueByFullKey(
			JSONObject jobj, String fullKey) {
		if (fullKey == null || fullKey.length() == 0) {
			return null;
		}

		String[] keys = fullKey.split("\\.");
		Object curObj = jobj;
		try {
			for (int i = 0; i < keys.length; i++) {
				if (curObj == null) {
					return null;
				}
				if (isInteger(keys[i])) {
					curObj = ((JSONArray) curObj)
							.get(Integer.parseInt(keys[i]));
				} else {
					curObj = ((JSONObject) curObj)
							.get(keys[i]);
				}
			}
		} catch (Exception e) {
			return null;
		}

		return curObj;
	}

	/**
	 * 字符串是否为合法非负整数(纯数字序列)
	 * 
	 * @param str
	 * @return
	 * @author wot_chenyonggang
	 */
	private static boolean isInteger(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		char[] chrAry = str.toCharArray();
		for (int i = 0; i < chrAry.length; i++) {
			if (chrAry[i] > '9' || chrAry[i] < '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * JSON to Xml
	 * 
	 * @param src
	 * @return
	 * @throws JSONException
	 */
	public static String JSON2Xml(Object src) throws JSONException {
		StringBuilder sb = new StringBuilder();
		JSON2Xml(sb, src, null);
		return sb.toString();
	}

	private static void JSON2Xml(StringBuilder sb, Object src, String tag)
			throws JSONException {
		boolean isAry = src instanceof JSONArray;
		if (tag != null && !isAry) {
			sb.append("<" + tag + ">");
		}
		if (src instanceof JSONObject) {
			JSONObject jo = (JSONObject) src;
			Iterator keys = jo.keySet().iterator();
			String key;
			Object value = null;
			boolean first = true;
			while (keys.hasNext()) {
				key = (String) keys.next();
				value = jo.get(key);
				JSON2Xml(sb, value, key);
			}

		} else if (isAry) {
			int i, len;
			Object o;
			JSONArray ja = (JSONArray) src;
			for (i = 0, len = ja.size(); i < len; i++) {
				o = ja.get(i);
				JSON2Xml(sb, o, tag);
			}
		} else {
			sb.append(src);
		}
		if (tag != null && !isAry) {
			sb.append("</" + tag + ">");
		}
	}

	public static Integer caclAge(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException(
					"The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age;
	}

}
