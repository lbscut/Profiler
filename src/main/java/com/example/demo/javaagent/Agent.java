package com.example.demo.javaagent;

import java.lang.instrument.Instrumentation;

public class Agent {

	public static void premain(String args, Instrumentation inst) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        inst.addTransformer(new Transformer());
    }
}
