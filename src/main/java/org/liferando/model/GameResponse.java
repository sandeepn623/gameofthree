package org.liferando.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@JsonDeserialize(builder = GameResponse.GameResponseBuilder.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class GameResponse {
    @NonNull
    @ApiModelProperty(value = "Http Status", example = "200 OK", position = 1)
    private HttpStatus httpStatus;
    @NonNull
    @ApiModelProperty(value = "Human readable message", example = "Please wait for the other player to join!", position = 2)
    private String message;
    @NonNull
    @ApiModelProperty(value = "Unique Id for players to communicate", example = "797b831d-91d1-4923-9618-ce8f6b97ccde", position = 3)
    private String gameId;
    @ApiModelProperty(value = "The value computed at each stage in a players turn", example = "452", position = 4)
    private Integer move;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GameResponseBuilder {
    }
}
