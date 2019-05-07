package com.example.demo.javaagent;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class TimeClassVisitor extends ClassAdapter {

	String className;
	public TimeClassVisitor(ClassVisitor var1, String className) {
		super(var1);
		this.className = className;
	}
	
	public TimeClassVisitor(ClassVisitor var1) {
		super(var1);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("<init>") || mv == null) {
            return mv;
        }

        String key = className + ":" + name;	
        mv = new AdviceAdapter( mv, access, name, descriptor) { 
            @Override
            public void onMethodEnter() {
        		Profiler.addMethod(className, name);
                mv.visitLdcInsn(key);
        		this.visitMethodInsn(INVOKESTATIC, "com/example/demo/javaagent/Profiler", "start", "(Ljava/lang/String;)V");
        	}
            @Override
            public void onMethodExit(int opcode) {
                mv.visitLdcInsn(key);
        		this.visitMethodInsn(INVOKESTATIC, "com/example/demo/javaagent/Profiler", "end", "(Ljava/lang/String;)V");
        	}
        };
        return mv;
	}


}
