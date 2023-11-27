# EarBee


## 목차
   1. [프로젝트 목적](#프로젝트-목적)
   2. [사용 기술](#사용-기술)
   3. [수행 도구](#수행-도구)
   4. [설계](#설계)
   5. [기능](#기능)
   6. [테스트](#테스트)
   7. [서버](#서버)
   8. [Properties](#Properties)
   9. [학습](#학습) 

# 프로젝트 목적

<br>

# 사용 기술


### BackEnd
    
### FrontEnd
    
    
### API
    
### Server

<br>

# 수행 도구

<br>

# 설계

<br>    

# 기능

<br>

# 테스트

<br>

# 서버

<br>

# Properties

```
# dbms
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.datasource.username="earbee"
spring.datasource.password="root"

# jsp
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp


# Server port 고정
server.port= 8080

# jsp 변경되면 자동으로 업데이트 지정
server.servlet.jsp.init-parameters.development = true 

```



# 학습
<details>
    <summary>자세히</summary>
1. JSP    
spring boot에서는 jsp를 사용하기 위해서는 추가적으로 의존성 주입이 필요하다.

#### build.gradle
```
// 내장 톰켓에서 jsp인식할 수 있도록 jsp 의존성 삽입
implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
// jstl 의존성 삽입
implementation 'javax.servlet:jstl:1.2'
```

<br>

#### application.properties

```
// view 접두사 ( 경로 설정 )
spring.mvc.view.prefix=/WEB-INF/views/

// view 접미사 ( 확장자 설정)
spring.mvc.view.suffix=.jsp
```
    

</details>



