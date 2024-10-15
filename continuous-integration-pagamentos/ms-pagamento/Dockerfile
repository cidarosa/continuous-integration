FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# endereço para a pasta target
# .jar é gerado sempre que compilamos o projeto
ARG JAR_FILE=target/*.jar
# copiar o .jar para dentro da nossa imagem
# renomeia para app.jar
COPY ${JAR_FILE} app.jar
# comando que serão executados
# assim que o container inicializar
ENTRYPOINT ["java","-jar","/app.jar"]
