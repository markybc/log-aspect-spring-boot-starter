# 导入 starter
````
<dependency>
    <groupId>com.common.log</groupId>
    <artifactId>log-aspect-controller-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
````
通过
``org.apache.logging.log4j.ThreadContext.put("logId",UUID)``把请求编号``logId``放到线程里面
日志打印添加 ``%X{logId}``输出

日志内容：
````
2022-08-21 11:06:52.702 [http-nio-8092-exec-2] [INFO ] com.common.log.controller.starter.ControllerLogAspect 74 - c8c63e6e687c4155a430d801ceabdc15 ====>>>> controller 调用 <<<==== {URL:[/wt-api/address/BSC/isAddress],RequestMethod:[POST],Args:[{"address":"1"},"BSC"],ReturnValue:[{"code":200,"data":false,"msg":"成功"}],Time:[10ms],MethodName:[CommonResult com.xxx.blockchain.controller.WtAddressController.isAddress(AddressReq,String)]}

````

配合 log-aspect-service-spring-boot-starter
````

2022-08-21 11:06:52.700 [http-nio-8092-exec-2] [INFO ] com.common.log.service.starter.ServiceLogAspect 67 - c8c63e6e687c4155a430d801ceabdc15 ====>>>> service 调用 <<<==== {URL:[Args:[{}],ReturnValue:[true],Time:[2ms],MethodName:[Boolean com.xxx.blockchain.service.identity.impl.WtTransactionServiceImpl.saveEntity(WtTransaction)]}
2022-08-21 11:06:52.700 [http-nio-8092-exec-2] [INFO ] com.common.log.service.starter.ServiceLogAspect 67 - c8c63e6e687c4155a430d801ceabdc15 ====>>>> service 调用 <<<==== {URL:[Args:["1"],ReturnValue:[1],Time:[6ms],MethodName:[int com.xxx.blockchain.service.identity.impl.WtAddressServiceImpl.countUnUseByChain(String)]}
2022-08-21 11:06:52.702 [http-nio-8092-exec-2] [INFO ] com.common.log.controller.starter.ControllerLogAspect 74 - c8c63e6e687c4155a430d801ceabdc15 ====>>>> controller 调用 <<<==== {URL:[/wt-api/address/BSC/isAddress],RequestMethod:[POST],Args:[{"address":"1"},"BSC"],ReturnValue:[{"code":200,"data":false,"msg":"成功"}],Time:[10ms],MethodName:[CommonResult com.xxx.blockchain.controller.WtAddressController.isAddress(AddressReq,String)]}

````

配合 log-aspect-feign-spring-boot-starter
````
2022-08-21 11:12:09.063 [http-nio-8092-exec-2] [INFO ] com.netflix.config.ChainedDynamicProperty 115 - 1cee0cf18bb3449cab1dda03ee5c2e87 Flipping property: xxx-portal.ribbon.ActiveConnectionsLimit to use NEXT property: niws.loadbalancer.availabilityFilteringRule.activeConnectionsLimit = 2147483647
2022-08-21 11:12:09.072 [http-nio-8092-exec-2] [INFO ] com.netflix.loadbalancer.BaseLoadBalancer 197 - 1cee0cf18bb3449cab1dda03ee5c2e87 Client: xxx-portal instantiated a LoadBalancer: DynamicServerListLoadBalancer:{NFLoadBalancer:name=xxx-portal,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:null
2022-08-21 11:12:09.074 [http-nio-8092-exec-2] [INFO ] com.netflix.loadbalancer.DynamicServerListLoadBalancer 222 - 1cee0cf18bb3449cab1dda03ee5c2e87 Using serverListUpdater PollingServerListUpdater
2022-08-21 11:12:14.099 [http-nio-8092-exec-2] [INFO ] com.alibaba.nacos.client.naming 164 - 1cee0cf18bb3449cab1dda03ee5c2e87 new ips(1) service: DEFAULT_GROUP@@xxx-portal -> [{"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"instanceId":"172.19.xx.85#8090#DEFAULT#DEFAULT_GROUP@@xxx-portal","ip":"172.19.x.85","ipDeleteTimeout":30000,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8090,"serviceName":"DEFAULT_GROUP@@xxx-portal","weight":1.0}]
2022-08-21 11:12:14.103 [http-nio-8092-exec-2] [INFO ] com.alibaba.nacos.client.naming 200 - 1cee0cf18bb3449cab1dda03ee5c2e87 current ips:(1) service: DEFAULT_GROUP@@xxx-portal -> [{"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"instanceId":"172.19.xx.85#8090#DEFAULT#DEFAULT_GROUP@@xxx-portal","ip":"172.19.x.85","ipDeleteTimeout":30000,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8090,"serviceName":"DEFAULT_GROUP@@xxx-portal","weight":1.0}]
2022-08-21 11:12:14.122 [http-nio-8092-exec-2] [INFO ] com.netflix.loadbalancer.DynamicServerListLoadBalancer 150 - 1cee0cf18bb3449cab1dda03ee5c2e87 DynamicServerListLoadBalancer for client xxx-portal initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=xxx-portal,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:com.alibaba.cloud.nacos.ribbon.NacosServerList@2b924508
2022-08-21 11:12:14.128 [PollingServerListUpdater-0] [INFO ] com.netflix.config.ChainedDynamicProperty 115 -  Flipping property: xxx-portal.ribbon.ActiveConnectionsLimit to use NEXT property: niws.loadbalancer.availabilityFilteringRule.activeConnectionsLimit = 2147483647
2022-08-21 11:12:15.177 [http-nio-8092-exec-2] [INFO ] com.common.log.feign.starter.FeignLogger 72 - 1cee0cf18bb3449cab1dda03ee5c2e87 ====>>>> feign 调用 <<<==== {URL:[http://xxx-portal/upool/wallet/notify/transfer],RequestMethod:[POST],Args:["{\"msgType\":null,\"sign\":null,\"reqTime\":null,\"data\":null}"],Time:[6171ms]}
2022-08-21 11:12:15.xx [http-nio-8092-exec-2] [INFO ] com.common.log.controller.starter.ControllerLogAspect 74 - 1cee0cf18bb3449cab1dda03ee5c2e87 ====>>>> controller 调用 <<<==== {URL:[/wt-api/address/BSC/isAddress],RequestMethod:[POST],Args:[{"address":"1"},"BSC"],ReturnValue:[{"code":200,"data":false,"msg":"成功"}],Time:[6198ms],MethodName:[CommonResult com.xxx.blockchain.controller.WtAddressController.isAddress(AddressReq,String)]}

````

配合 log-aspect-feign-spring-boot-starter log-aspect-service-spring-boot-starter
````

2022-08-21 11:12:08.991 [http-nio-8092-exec-2] [INFO ] com.common.log.service.starter.ServiceLogAspect 67 - 1cee0cf18bb3449cab1dda03ee5c2e87 ====>>>> service 调用 <<<==== {URL:[Args:[{}],ReturnValue:[true],Time:[2ms],MethodName:[Boolean com.xxx.blockchain.service.identity.impl.WtTransactionServiceImpl.saveEntity(WtTransaction)]}
2022-08-21 11:12:08.991 [http-nio-8092-exec-2] [INFO ] com.common.log.service.starter.ServiceLogAspect 67 - 1cee0cf18bb3449cab1dda03ee5c2e87 ====>>>> service 调用 <<<==== {URL:[Args:["1"],ReturnValue:[1],Time:[6ms],MethodName:[int com.xxx.blockchain.service.identity.impl.WtAddressServiceImpl.countUnUseByChain(String)]}
2022-08-21 11:12:09.063 [http-nio-8092-exec-2] [INFO ] com.netflix.config.ChainedDynamicProperty 115 - 1cee0cf18bb3449cab1dda03ee5c2e87 Flipping property: xxx-portal.ribbon.ActiveConnectionsLimit to use NEXT property: niws.loadbalancer.availabilityFilteringRule.activeConnectionsLimit = 2147483647
2022-08-21 11:12:09.072 [http-nio-8092-exec-2] [INFO ] com.netflix.loadbalancer.BaseLoadBalancer 197 - 1cee0cf18bb3449cab1dda03ee5c2e87 Client: xxx-portal instantiated a LoadBalancer: DynamicServerListLoadBalancer:{NFLoadBalancer:name=xxx-portal,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:null
2022-08-21 11:12:09.074 [http-nio-8092-exec-2] [INFO ] com.netflix.loadbalancer.DynamicServerListLoadBalancer 222 - 1cee0cf18bb3449cab1dda03ee5c2e87 Using serverListUpdater PollingServerListUpdater
2022-08-21 11:12:14.099 [http-nio-8092-exec-2] [INFO ] com.alibaba.nacos.client.naming 164 - 1cee0cf18bb3449cab1dda03ee5c2e87 new ips(1) service: DEFAULT_GROUP@@xxx-portal -> [{"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"instanceId":"172.19.xx.85#8090#DEFAULT#DEFAULT_GROUP@@xxx-portal","ip":"172.19.x.85","ipDeleteTimeout":30000,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8090,"serviceName":"DEFAULT_GROUP@@xxx-portal","weight":1.0}]
2022-08-21 11:12:14.103 [http-nio-8092-exec-2] [INFO ] com.alibaba.nacos.client.naming 200 - 1cee0cf18bb3449cab1dda03ee5c2e87 current ips:(1) service: DEFAULT_GROUP@@xxx-portal -> [{"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000,"instanceId":"172.19.xx.85#8090#DEFAULT#DEFAULT_GROUP@@xxx-portal","ip":"172.19.x.85","ipDeleteTimeout":30000,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8090,"serviceName":"DEFAULT_GROUP@@xxx-portal","weight":1.0}]
2022-08-21 11:12:14.122 [http-nio-8092-exec-2] [INFO ] com.netflix.loadbalancer.DynamicServerListLoadBalancer 150 - 1cee0cf18bb3449cab1dda03ee5c2e87 DynamicServerListLoadBalancer for client xxx-portal initialized: DynamicServerListLoadBalancer:{NFLoadBalancer:name=xxx-portal,current list of Servers=[],Load balancer stats=Zone stats: {},Server stats: []}ServerList:com.alibaba.cloud.nacos.ribbon.NacosServerList@2b924508
2022-08-21 11:12:14.128 [PollingServerListUpdater-0] [INFO ] com.netflix.config.ChainedDynamicProperty 115 -  Flipping property: xxx-portal.ribbon.ActiveConnectionsLimit to use NEXT property: niws.loadbalancer.availabilityFilteringRule.activeConnectionsLimit = 2147483647
2022-08-21 11:12:15.177 [http-nio-8092-exec-2] [INFO ] com.common.log.feign.starter.FeignLogger 72 - 1cee0cf18bb3449cab1dda03ee5c2e87 ====>>>> feign 调用 <<<==== {URL:[http://xxx-portal/upool/wallet/notify/transfer],RequestMethod:[POST],Args:["{\"msgType\":null,\"sign\":null,\"reqTime\":null,\"data\":null}"],Time:[6171ms]}
2022-08-21 11:12:15.xx [http-nio-8092-exec-2] [INFO ] com.common.log.controller.starter.ControllerLogAspect 74 - 1cee0cf18bb3449cab1dda03ee5c2e87 ====>>>> controller 调用 <<<==== {URL:[/wt-api/address/BSC/isAddress],RequestMethod:[POST],Args:[{"address":"1"},"BSC"],ReturnValue:[{"code":200,"data":false,"msg":"成功"}],Time:[6198ms],MethodName:[CommonResult com.xxx.blockchain.controller.WtAddressController.isAddress(AddressReq,String)]}


````