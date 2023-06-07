package org.sheep.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpotifyTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
}
