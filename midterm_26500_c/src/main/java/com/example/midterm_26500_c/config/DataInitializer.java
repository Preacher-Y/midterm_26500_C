package com.example.midterm_26500_c.config;

import com.example.midterm_26500_c.entity.Location;
import com.example.midterm_26500_c.entity.Tag;
import com.example.midterm_26500_c.entity.Task;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.entity.UserProfile;
import com.example.midterm_26500_c.enums.LocationType;
import com.example.midterm_26500_c.enums.TaskStatus;
import com.example.midterm_26500_c.repository.LocationRepository;
import com.example.midterm_26500_c.repository.TagRepository;
import com.example.midterm_26500_c.repository.TaskRepository;
import com.example.midterm_26500_c.repository.UserRepository;
import java.time.LocalDate;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            if (locationRepository.count() > 0) {
                return;
            }

            Location kigali = locationRepository.save(Location.builder().code("KGL").name("Kigali").type(LocationType.PROVINCE).build());
            Location gasabo = locationRepository.save(Location.builder().code("GAS").name("Gasabo").type(LocationType.DISTRICT).parent(kigali).build());
            Location kimironko = locationRepository.save(Location.builder().code("KIM").name("Kimironko").type(LocationType.SECTOR).parent(gasabo).build());
            Location kibagabaga = locationRepository.save(Location.builder().code("KIB").name("Kibagabaga").type(LocationType.CELL).parent(kimironko).build());
            Location umuduguduOne = locationRepository.save(Location.builder().code("V01").name("Umudugudu wa 1").type(LocationType.VILLAGE).parent(kibagabaga).build());
            locationRepository.save(Location.builder().code("V02").name("Umudugudu wa 2").type(LocationType.VILLAGE).parent(kibagabaga).build());

            Location northern = locationRepository.save(Location.builder().code("NTH").name("Northern").type(LocationType.PROVINCE).build());
            Location musanze = locationRepository.save(Location.builder().code("MSZ").name("Musanze").type(LocationType.DISTRICT).parent(northern).build());
            Location muhoza = locationRepository.save(Location.builder().code("MHZ").name("Muhoza").type(LocationType.SECTOR).parent(musanze).build());
            Location cyivugiza = locationRepository.save(Location.builder().code("CYV").name("Cyivugiza").type(LocationType.CELL).parent(muhoza).build());
            locationRepository.save(Location.builder().code("V03").name("Umudugudu wa 3").type(LocationType.VILLAGE).parent(cyivugiza).build());
            locationRepository.save(Location.builder().code("V04").name("Umudugudu wa 4").type(LocationType.VILLAGE).parent(cyivugiza).build());

            Tag backend = tagRepository.save(Tag.builder().name("Backend").build());
            Tag urgent = tagRepository.save(Tag.builder().name("Urgent").build());

            User user = User.builder()
                    .firstName("Aline")
                    .lastName("Uwimana")
                    .email("aline.uwimana@example.com")
                    .phoneNumber("250788111222")
                    .location(umuduguduOne)
                    .build();

            UserProfile profile = UserProfile.builder()
                    .nationalId("1199980012345678")
                    .bio("Sample seeded user profile")
                    .dateOfBirth(LocalDate.of(1999, 8, 15))
                    .user(user)
                    .build();
            user.setProfile(profile);
            user = userRepository.save(user);

            Task task = Task.builder()
                    .title("Prepare sprint board")
                    .description("Create initial sprint board for team")
                    .status(TaskStatus.TODO)
                    .dueDate(LocalDate.now().plusDays(7))
                    .user(user)
                    .tags(Set.of(backend, urgent))
                    .build();
            taskRepository.save(task);
        };
    }
}
