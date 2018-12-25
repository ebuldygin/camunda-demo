# Spring Boot приложение с Camunda Web App

По мотивам [демо от Camunda](https://github.com/camunda/camunda-bpm-examples/tree/master/spring-boot-starter/example-webapp) с использованием использование БД Oracle.

Пример содержит
* Встроенный Camunda BPM движок, доступный через Web-интерфейс
* Конфигурацию подключения к БД Oracle для BPM движка

## Как это работает
1. Настройка Spring Boot и camunda-web описана в [исходном демо](https://github.com/camunda/camunda-bpm-examples/tree/master/spring-boot-starter/example-webapp)
1. Использование БД Oracle конфигурируется в `application.yaml` через свойства станадартного datasource: `spring.datasource.*`
    ```yaml
    ...
    spring:
      datasource:
        url: jdbc:oracle:thin:@//localhost:1521/xe
        username: CAMUNDA
        password: CAMUNDA
        driver-class-name: oracle.jdbc.OracleDriver
        initializationMode: never
    ...
    ```

    Для функционирования JDBC добавлены зависимости (`com.oracle:ojdbc6:11.2.0.3` придётся зарегистрировать в локальном репозитории вручную):
    ```xml
    ...
     <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-jdbc</artifactId>
     </dependency>
 
     <dependency>
       <groupId>com.oracle</groupId>
       <artifactId>ojdbc6</artifactId>
       <version>11.2.0.3</version>
     </dependency>
    ...
    ```
    
    В ходе эксплуатации я столкнулся с ошибкой OptimisticLockException, которая связана с тем, что Oracle 11g не поддерживает jdbc batch processing.
    (подробности о поддержке этой фичи [здесь](https://docs.camunda.org/manual/7.10/user-guide/process-engine/database/#jdbc-batch-processing)).
    Проблема решается добавлением в `application.yaml` параметра `camunda.bpm.database.jdbc-batch-processing = false`

1. В проекте есть процесс `camunda-demo-pool.bpmn` (можно открывать 
в [Camunda Modeler](https://docs.camunda.org/manual/latest/installation/camunda-modeler)).
Схема включает в себя три human tasks и один service task.
В процессе описано создание заявки _клиентом_ (группа пользователей _customer_) с последующим согласованием у _начальника отдела_ (группа пользователей _departmentHead_). 
В случае отклонения заявки вышестоящим руководством, у инициатора 
есть возможность отредактировать заявку и отправить на повторное согласование или отменить.
Условия переходов завязаны на переменных, эти переходы обозначены оранжевым
![Схема процесса](https://github.com/ebuldygin/camunda-demo/blob/master/engine-web/process.PNG) 

## Запуск приложения
Приложение собирается стандартно: `mvn clean install`. 
В результате будет исполняемый jar-файл.

После запуска приложение доступно по адресу `http://localhost:18081/`

## Ссылки
1. [Camunda Get Started (JSF/Spring boot etc)](https://docs.camunda.org/get-started/apache-maven/)
2. [Camunda Reference]()https://docs.camunda.org/manual/7.10/reference/)
3. [Camunda BPMN 2.0 Reference]()https://camunda.com/bpmn/reference/)
