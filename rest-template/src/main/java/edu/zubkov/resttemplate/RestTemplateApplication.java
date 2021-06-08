package edu.zubkov.resttemplate;

import edu.zubkov.resttemplate.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestTemplateApplication {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String URL_GET_POST_PUT = "http://91.241.64.178:7081/api/users";
    private static String URL_DELETE = "http://91.241.64.178:7081/api/users/{id}";

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateApplication.class, args);

        findOutTheEncryptedKey();
    }

    private static void findOutTheEncryptedKey() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);

        //GET METHOD
        ResponseEntity<String> responseEntityGetMethod = restTemplate.exchange(URL_GET_POST_PUT, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntityGetMethod.getStatusCode();
        String userBody = responseEntityGetMethod.getBody();
        HttpHeaders headers = responseEntityGetMethod.getHeaders();
        String cookies = headers.getFirst("Set-Cookie");

        restTemplate.getInterceptors().add((httpRequest, bytes, clientHttpRequestExecution) -> {
            httpRequest.getHeaders().set("Cookie", cookies);
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        });

        //POST METHOD
        User postUser = new User(3L, "James", "Brown", (byte) 73);
        HttpEntity<User> httpEntityPost = new HttpEntity<>(postUser, httpHeaders);
        ResponseEntity<String> responseEntityPostMethod = restTemplate.exchange(URL_GET_POST_PUT, HttpMethod.POST, httpEntityPost, String.class);

        //PUT METHOD
        User putUser = new User(3L, "Thomas", "Shelby", (byte) 73);
        HttpEntity<User> httpEntityPut = new HttpEntity<>(putUser, httpHeaders);
        ResponseEntity<String> responseEntityPutMethod = restTemplate.exchange(URL_GET_POST_PUT, HttpMethod.PUT, httpEntityPut, String.class);

        //DELETE METHOD
        Long id = 3L;
        ResponseEntity<String> responseEntityDeleteMethod = restTemplate.exchange(URL_DELETE, HttpMethod.DELETE, null, String.class, id);

        System.out.println("Encrypted key: " + responseEntityPostMethod.getBody() + responseEntityPutMethod.getBody() + responseEntityDeleteMethod.getBody());
    }
}