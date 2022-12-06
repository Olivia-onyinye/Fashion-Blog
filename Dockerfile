FROM openjdk:17
EXPOSE 8087
LABEL MAINTAINER="OLIVIA NWACHUKWU "n.oliviaonyinye@gmail.com""
ENV spring.datasource.url=jdbc:mysql://mysqldb:3306/fashionBlog2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
ENV spring.datasource.username=root
ENV spring.datasource.password=Kamsiyochukwu1*
COPY ./target/Fashion-Blog-0.0.1-SNAPSHOT.jar /opt/Fashion-Blog-0.0.1.jar
COPY . /opt/
ENTRYPOINT ["java", "-jar", "/opt/Fashion-Blog-0.0.1.jar", "--server.port=8087", "--spring.config.location=file:/opt/src/main/resources/"]

