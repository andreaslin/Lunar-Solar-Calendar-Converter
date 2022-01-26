package cn.z;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class Demo {
	private static String dump(Object o) {
		StringBuffer buffer = new StringBuffer();
		Class<?> oClass = o.getClass();
		if (oClass.isArray()) {
			buffer.append("[");
			for (int i = 0; i < Array.getLength(o); i++) {
				if (i > 0)
					buffer.append(",");
				Object value = Array.get(o, i);
				buffer.append(value.getClass().isArray() ? dump(value) : value);
			}
			buffer.append("]");
		} else {
			buffer.append("{");
			while (oClass != null) {
				Field[] fields = oClass.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					if (buffer.length() > 1)
						buffer.append(",");
					fields[i].setAccessible(true);
					buffer.append(fields[i].getName());
					buffer.append("=");
					try {
						Object value = fields[i].get(o);
						if (value != null) {
							buffer.append(value.getClass().isArray() ? dump(value)
									: value);
						}
					} catch (IllegalAccessException e) {
					}
				}
				oClass = oClass.getSuperclass();
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		Solar solar = new Solar();
		solar.solarYear = 2017;
		solar.solarMonth = 1;
		solar.solarDay = 4;
		System.out.println(dump(solar));
		Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
		System.out.println(dump(lunar));
		solar = LunarSolarConverter.LunarToSolar(lunar);
		System.out.println(dump(solar));
		System.out.println(MiscHelper.lunarYearToGanZhi(2015));
		System.out.println(MiscHelper.lunarYearToGanZhi(1894));
		System.out.println(MiscHelper.lunarYearToAnimal(2022));
		System.out.println(MiscHelper.constellation(1,27));
	}
}
