package com.example.demo.javaagent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profiler {
	
	public static Map<String, MethodInfo> map = new HashMap<>();
	
	public static void addMethod(String className, String methodName){
		map.put(className + ":" + methodName, new MethodInfo(className, methodName));
	}
	
	public static void start(String methodId) {
		map.get(methodId).start();
	}

	public static void end(String methodId) {
		map.get(methodId).end();
	}

}

class MethodInfo{
	public String className;
	public String methodName;
	public long lastStartTime;
	public long useTime;
	public long invokeCount;
	public MethodInfo(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}
	
	public void start(){
		invokeCount++;
		lastStartTime = new Date().getTime();
	}
	@Override
	public String toString() {
		return String.format("%-35.35s%-15.15s%-15d%-15d",className, methodName, useTime, invokeCount);
	}
	
	public void end(){
		useTime = useTime + (new Date().getTime() - lastStartTime);
		if(methodName.equals("main")){
			System.out.println(String.format("%-35.35s%-15.15s%-15.15s%-15.15s","className", "methodName", "useTime", "invokeCount"));
			List<MethodInfo> list = new ArrayList<>();
			list.addAll(Profiler.map.values());
			list.sort(new Comparator<MethodInfo>(){
				@Override
				public int compare(MethodInfo o1, MethodInfo o2) {
					if(o1.className.equals(o2.className)){
						return o1.methodName.hashCode() - o2.methodName.hashCode();
					}else{
						return o1.className.hashCode() - o2.className.hashCode();
					}
				}
			});
			for(MethodInfo method: list){
				System.out.println(method);
			}
		}
	}
	
}