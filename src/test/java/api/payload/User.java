package api.payload;

import api.utilities.DataProviders;
import com.github.javafaker.Faker;
import lombok.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Write Lombok Annotation
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode


public class User {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;


    List<User> userPayloads = new ArrayList<>();


    public static User userPayloadGenerator(){
        Faker faker = new Faker();
        return User.builder()
                .id(faker.idNumber().hashCode())
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password(5, 10))
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(0)
                .build();
    }

    public static User userPayloadGenerator(int id, String username, String firstname, String lastname, String email, String password, String phone){
        Faker faker = new Faker();
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .password(email)
                .phone(phone)
                .userStatus(0)
                .build();
    }

    public static List<User> xlUserPayloadDataProvider() throws IOException {
        List<User> userPayloads = new ArrayList<>();
        Object[][] data = DataProviders.getAllData("UserModule");

        for (Object[] userData : data) {
            int id = (int) userData[0];
            String username = (String) userData[1];
            String firstname = (String) userData[2];
            String lastname = (String) userData[3];
            String email = (String) userData[4];
            String password = (String) userData[5];
            String phone = (String) userData[6];

            User userPayload = User.userPayloadGenerator(id, username, firstname, lastname, email, password, phone);

            userPayloads.add(userPayload);

        }

        return userPayloads;
    }
}
