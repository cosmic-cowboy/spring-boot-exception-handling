# spring-boot-exception-handling
Spring Bootでの例外処理（主にWeb APIで返却するステータスコードと説明の管理）

#### Exceptinクラスにおいて、例外発生時の振る舞いを定義する

OrderNotFoundExceptionが発生すると、ステータスコード404が返却される。

https://github.com/cosmic-cowboy/spring-boot-exception-handling/blob/master/spring-boot-exception-handling/src/main/java/com/slgerkamp/introductory/spring/boot/exceptions/exception/OrderNotFoundException.java

#### Controllerクラスにおいて、例外発生時の振る舞いを定義する

ExceptionHandlingControllerにおいて、ExceptionHandlerアノテーションで定義された例外が発生すると、Controller独自の振る舞いをする

https://github.com/cosmic-cowboy/spring-boot-exception-handling/blob/master/spring-boot-exception-handling/src/main/java/com/slgerkamp/introductory/spring/boot/exceptions/api/ExceptionHandlingController.java

#### アプリケーション共通で、例外発生時の振る舞いを定義する

ControllerAdviceアノテーションで定義されたクラスで、ExceptionHandlerアノテーションを定義し、アプリケーションで発生した例外の振る舞いを制御する。

https://github.com/cosmic-cowboy/spring-boot-exception-handling/blob/master/spring-boot-exception-handling/src/main/java/com/slgerkamp/introductory/spring/boot/exceptions/aop/ExceptionHandlingControllerAdvice.java


### テスト

#### Controller独自のエラーハンドリング

https://github.com/cosmic-cowboy/spring-boot-exception-handling/tree/master/spring-boot-exception-handling/src/test/java/com/slgerkamp/introductory/spring/boot/exceptions/api/ExceptionHandlingControllerTest.java

#### アプリケーション全体で共通のエラーハンドリング

https://github.com/cosmic-cowboy/spring-boot-exception-handling/tree/master/spring-boot-exception-handling/src/test/java/com/slgerkamp/introductory/spring/boot/exceptions/api/WithoutExceptionHandlingControllerTest.java
