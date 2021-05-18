# web引用通用配置

1、统一异常处理，统一返回格式输出

2、Json序列化设置

3、系统配置上下文管理：
 a、初始化系统配置参数：
    实现SysConfigDetails接口，返回Map<String,Object>参数信息，并注册到Spring容器中
    
 b、获取系统配置：
    SysConfigContext.getConfig(code);
    
 c、动态添加,修改，删除系统配置：
    SysConfigContext.putConfig(code, value);//添加或修改参数
    SysConfigContext.deleteConfig(code);//删除系统配置参数
    
  注意：动态参数配置只适用于单节点部署，配置参数存储在本地内存中。
  
  
4、线程上下文管理
   存储当前请求处理线程的数据
   
   
5、缓存上下文管理
  


  

