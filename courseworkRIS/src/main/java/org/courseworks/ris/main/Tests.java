package org.courseworks.ris.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tests {
	public static void classTest() {
		String a = "dsa";
		Object b = a;
		if (String.class.toString().equals(b.getClass().toString())) {
			System.out.println(b);
		} else {
			System.out.println(String.class.toString());
			System.out.println(b.getClass().toString());
		}
	}

	public static void listTest() {
		ArrayList<Object> array = new ArrayList<Object>();
		array.add(new String("abc"));
		array.add(new String("bcd"));
		List<Object> list = array;
		System.out.println(list.toString());
		String str = (String) list.get(0);
		list.remove(str);
		str = "fgh";
		list.add(str);
		System.out.println(list.toString());
	}

	public static void hashMapTest() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("1", new String("abc"));
		hashMap.put("2", new String("bcd"));
		System.out.println(hashMap.toString());
		hashMap.put("1", new String("fgh"));
		System.out.println(hashMap.toString());
		hashMap.keySet().toArray(new String[0]);
	}
}
