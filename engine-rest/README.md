# Spring Boot приложение с Camunda REST API

По мотивам [демо от Camunda](https://github.com/camunda/camunda-bpm-examples/tree/master/spring-boot-starter/example-web) с использованием использование БД Oracle.

Пример содержит
* Встроенный Camunda BPM движок, доступный через REST API
* Конфигурацию подключения к БД Oracle для BPM движка
* Простенький BPMN процесс

## Как это работает
0. Настройка Spring Boot и Camunda с REST API описана в [исходном демо](https://github.com/camunda/camunda-bpm-examples/tree/master/spring-boot-starter/example-web)
0. Использование БД Oracle конфигурируется в `application.yaml` через свойства станадартного datasource: `spring.datasource.*`
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

## Запуск приложения
Приложение собирается стандартно: `mvn clean install`. 
В результате будет исполняемый jar-файл.

После запуска приложение доступно по адресу `http://localhost:18080/rest/engine`.

Посмотреть список зарегистрированных процессов можно по адресу `http://localhost:18080/rest/engine/default/process-definition`.


## Ссылки
0. [Приложение с примером конфигурации REST API от Camunda](https://github.com/camunda/camunda-bpm-examples/tree/master/spring-boot-starter/example-web)
0. [Документация Camunda REST API](https://docs.camunda.org/manual/7.10/reference/rest/)
0. [Документация с параметрами движка](https://github.com/camunda/camunda-docs-manual/blob/master/content/user-guide/spring-boot-integration/configuration.md)