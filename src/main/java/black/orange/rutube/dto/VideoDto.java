package black.orange.rutube.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class VideoDto {

    @NotEmpty (message = "Имя не должно быть пустым!!!")
    private String name;

    @NotEmpty (message = "Ссылка не должна быть пустой!!!")
    private String link;
}
