package black.orange.rutube.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class VideoDto {
   @NonNull
   @NotEmpty (message = "Имя не должно быть пустым!!!")
   private String name;
    @NonNull
    @NotEmpty (message = "Ссылка не должна быть пустой!!!")
    private String link;
}
