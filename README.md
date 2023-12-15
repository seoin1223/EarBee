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
- 국세청_사업자등록정보 진위확인 및 상태조회 서비스
    
### Server

<br>

# 수행 도구

<br>

# 설계

<br>    

# 기능

<br>

# 테스트

<details>
    <summary>자세히</summary>

### 1. postman
   - restAPI test (확인 완료)   

        <img src="img/postman/restApiTest.png" width="700" height=300 alt="경로 오류">

   - 사업자 번호 조회 api test(postman- 확인 완료)   

     <img src="img/postman/businessSearchTest.png" width="700" height=300 alt="경로 오류">

<br>

### JUnit
   - BusinessApiControllerTest
        - checkValue() : properties 내용을 정상적으로 가져오는지 테스트 진행   
     
          <img src="img/junit/checkValue.png" width="500" height=150 alt="경로 오류">




</details>



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
    <summary>Seoin</summary>

#### JSP    
spring boot에서는 jsp를 사용하기 위해서는 추가적으로 의존성 주입이 필요하다.

- build.gradle
    ```
    // 내장 톰켓에서 jsp인식할 수 있도록 jsp 의존성 삽입
    implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
    // jstl 의존성 삽입
    implementation 'javax.servlet:jstl:1.2'
    ```
- application.properties
        
    ```
    // view 접두사 ( 경로 설정 )
    spring.mvc.view.prefix=/WEB-INF/views/
        
    // view 접미사 ( 확장자 설정)
    spring.mvc.view.suffix=.jsp
    ```

  <br>


#### properties 
1. properties 변수 
   - 새롭게 만든 properties를 사용하기 위해서는 위치를 알려야함
   - @PropertySource("classpath:business.properties") 사용
    
    &rarr; 정상적으로 변수를 불러옴을 확인



</details>



