package dev.valium.snakehouse.module.oauth.social.kakao;

import com.google.gson.Gson;
import dev.valium.snakehouse.module.oauth.exception.CommunicationException;
import dev.valium.snakehouse.module.oauth.social.common.Profile;
import dev.valium.snakehouse.module.oauth.social.common.RetAuth;
import dev.valium.snakehouse.module.oauth.social.common.SocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KakaoService implements SocialService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;
    @Value("${spring.social.kakao.client_id}")
    private String kakaoClientId;
    @Value("${spring.social.kakao.redirect}")
    private String kakaoRedirect;
    @Value("${spring.social.kakao.url.profile}")
    private String kakaoProfile;
    @Value("${spring.social.kakao.url.token}")
    private String kakaoToken;

    @Override
    public Profile getProfile(String accessToken) {
        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        try {
            // Request profile
            ResponseEntity<String> response = restTemplate.postForEntity(kakaoProfile, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK)
                return gson.fromJson(response.getBody(), Profile.class);

        } catch (Exception e) {
            throw new CommunicationException();
        }

        // unreachable
        throw new CommunicationException();
    }

    @Override
    public RetAuth getTokenInfo(String code) {

        // Set header : Content-type: application/x-www-form-urlencoded
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirect);
        params.add("code", code);

        // Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(kakaoToken, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), RetAuth.class);
        }

        return null;
    }
}