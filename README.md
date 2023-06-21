# SHITTINGME

## 项目构建

在设置→项目结构，选择项目SDK为openjdk-19

<img src="README.assets/1687183835237.png" alt="1687183835237" style="zoom:80%;" />

平台设置SDK选择jdk_17

<img src="README.assets/1687183862861.png" alt="1687183862861" style="zoom:80%;" />

可以选择更高版本来兼容，不过还是选一样的吧，防止哪里又踩坑(*^_^*)

## 代码目录介绍

* src

  * main

    * java
      * com/example/ex3_2_back
        * configuration Cors、Swagger等的配置， Spring容器可以使用这些方法来注入Bean 
        * controller 控制层
        * domain domain层，定义相关接口的数据结构
        * entity 实体类，此处使用JPA可直接在数据库生成对应表
        * exception ExceptionHandler返回相关异常信息
        * interceptor 拦截器，用于token验证
        * repository 与数据库相连的持久层，部分操作需要使用JPQL语句
        * service 一些复用程度高的功能需要在这实现
        * utils 后端运行入口
      * resources .相关配置文件yaml

  * test

    * java/com/example/ex3_2_back
      * repository 持久层相关方法的测试
      * utils  验证是否能够成功加载 Spring 上下文的测试 

    

## JPA & JPQL

### JPA

 Java Persistence API，可以通过注解或者XML描述【对象-关系表】之间的映射关系，并将实体对象持久化到数据库中。 一些注释略解（如下👇）

![1687191186276](README.assets/1687191186276.png)

还有其他许多注解对应不同功能，需根据实际情况灵活选用。

### JPQL

JpaRepository自带的可直接使用的简单查询（如下👇）有时候满足不了我们的需求

![1687191393685](README.assets/1687191393685.png)

我们经常需要 **@Query结合jpql语句进行查询等操作** 

一个更新数据库值的一个简单示例（如下👇）

![1687191665489](README.assets/1687191665489.png)

还有其他许多注解对应不同功能，需根据实际情况灵活选用。

## 测试用例

编写测试用例时的一些注意事项（如下👇）

![1687188959142](README.assets/1687188959142.png)

完成后 **保存为用例**，效果（如下👇）

![1687189244594](README.assets/1687189244594.png)