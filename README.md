# Movie Catalog



# Executar
 - Utiliza mongodb embebido, então não se preoculpe com o banco.
 - O mongo será povoado durante a inicialização com alguns dados.
 - O swagger estará disponível em <http://localhost:8080/swagger-ui.html>
 
 ```sh
$ mvn spring-boot:run
```

# Testar
 - Executando a suite de testes: TestSuite.java.
 
 ```sh
$ mvn test
```

# Sonar
 - O endereço do sonarqube pode ser modificado atraves do arquivo pom.xml.
 
 ```sh
$ mvn clean install sonar:sonar
```
# Enviando a cobertura de código para o Sonar
  
 ```sh
$ mvn test 
$ sonar:sonar
```

# TODO
 - Finalizar os testes unitários dos controllers
 - Configurar o maven para criar a imagem do docker
 - Configurar a CI/CD 