package com.yedam.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapExample {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<String, Integer>(); //Map을 구현해주는 HashMap / 중간성적
		map.put("박소연", 90);
		map.put("김민지", 91);
		map.put("이진주", 92);
		
		Map<String, Integer> map2 = new HashMap<String, Integer>(); // 기말성적
		map.put("박소연", 93);
		map.put("김민지", 94);
		map.put("이진주", 96);
		
		System.out.println(map.get("박소연")); // 해당되는 key의 value들을 리턴해줌
		System.out.println(map.get("김민지"));
		System.out.println(map.get("이진주"));
		
		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>(); // list에 map, map2 다 담기
		list.add(map);
		list.add(map2);
		
		Gson gson = new GsonBuilder().create();
		System.out.println(gson.toJson(list));
		
	}
}
