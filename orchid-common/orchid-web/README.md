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
  
 
6、防止用户重复提交：用户在一次请求返回之前，提交了多次。
    产生原因：网络延时，请求处理缓慢等
    相同用户相同请求时请求返回之前，不允许重复提价，实现redis分布式锁(setnx命令)
    
    RequestThreadContextFilter中实现防止用户重复提交
    

7、防止数据重复插入：唯一索引，select insert，分布式锁
   针对某个字段信息，不允许重复插入,
   redis分布式锁+数据存在校验：高并发下相同数据只能有一个进行插入(获取到锁)，插入时判断数据是否已存在，插入成功则释放锁
   NoRepeatInsertAop+NoRepeatInsert，如：
  
   @NoRepeatInsert(key="'SysConfig:code:'+#sysConfig.code", label = "参数编码")
   //    @NoRepeatInsert(label = "参数编码", name="code")

  

