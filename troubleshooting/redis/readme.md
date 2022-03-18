#REDIS
### raised java.lang.ClassCastException: a.A cannot be cast to a.A
클래스 이름은 같지만 클래스 캐리어가 달라 생기는 문제이다.   

객체 직렬화 (Class loader 1) -> 코드/설정 변경 ->  
Dev tools가 새 클래스 로더 생성(class loader 2) ->   
객체 역직렬화 (class loader 2) -> Exception(클래스 로더가 다름)

dev tools와 프로필을 달리해 해결했다.   

[출처](https://stackoverflow.com/questions/37977166/java-lang-classcastexception-dtoobject-cannot-be-cast-to-dtoobject)