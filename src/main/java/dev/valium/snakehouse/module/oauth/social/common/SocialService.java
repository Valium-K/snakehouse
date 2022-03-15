package dev.valium.snakehouse.module.oauth.social.common;

public interface SocialService {
    Profile getProfile(String accessToken);
    RetAuth getTokenInfo(String code);
}
