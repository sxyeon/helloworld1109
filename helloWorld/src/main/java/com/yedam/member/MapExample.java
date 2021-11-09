package com.yedam.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapExample {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>(); //Map�� �������ִ� HashMap / �߰�����
		map.put("�ڼҿ�", 90);
		map.put("�����", 91);
		map.put("������", 92);
		
		Map<String, Integer> map2 = new HashMap<String, Integer>(); // �⸻����
		map.put("�ڼҿ�", 93);
		map.put("�����", 94);
		map.put("������", 96);
		
		System.out.println(map.get("�ڼҿ�")); // �ش�Ǵ� key�� value���� ��������
		System.out.println(map.get("�����"));
		System.out.println(map.get("������"));
		
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>(); // list�� map, map2 �� ���
		list.add(map);
		list.add(map2);
		
		Gson gson = new GsonBuilder().create();
		System.out.println(gson.toJson(list));
		
	}
}
