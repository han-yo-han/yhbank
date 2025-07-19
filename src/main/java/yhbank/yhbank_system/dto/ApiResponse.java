package yhbank.yhbank_system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean succeess;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data){
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> fail(String message){
        return new ApiResponse<>(false, message, null);
    }

}
