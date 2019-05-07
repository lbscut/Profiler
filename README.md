0. 监控包过滤在Transformer中声明，后续可优化为变量传入
1. 打包后的jar包的MANIFEST.MF文件中必须包含
```
Premain-Class: com.example.demo.javaagent
```
2. 要监控的程序需要加上VM参数
```
-javaagent:C:\Users\lb\Desktop\demo\target\demo-0.0.1-SNAPSHOT.jar
```