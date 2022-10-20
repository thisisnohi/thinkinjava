# README.MD
> create by nohi 20220920

## apache httpclient 4 demo
* get post请求  TestApacheHttpClient
* http 连接池   TestApacheHttpClientPool

## 问题
1. log找不到符号
    ```groovy
    implementation 'org.projectlombok:lombok:1.18.24'
    annotationProcessor 'org.projectlombok:lombok:1.18.24'
    ```
    clean 后 再运行
    如果还不行， Preferences | Build, Execution, Deployment | Build Tools | Gradle
    build and run using: 从 gradle 改为 idea
    
2. 请删除该文件或确保该文件位于正确的类路径子目录中。类文件具有错误的版本 55.0, 应为 52.0
   注意：logback-classic 对应版本与jdk版本 
   jdk1.8   logback:1.2.11 slf4j-api:1.7.36