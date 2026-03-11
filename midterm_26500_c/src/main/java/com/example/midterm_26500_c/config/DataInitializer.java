package com.example.midterm_26500_c.config;

import com.example.midterm_26500_c.entity.Cell;
import com.example.midterm_26500_c.entity.District;
import com.example.midterm_26500_c.entity.Province;
import com.example.midterm_26500_c.entity.Sector;
import com.example.midterm_26500_c.entity.Tag;
import com.example.midterm_26500_c.entity.Task;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.entity.UserProfile;
import com.example.midterm_26500_c.entity.Village;
import com.example.midterm_26500_c.enums.TaskStatus;
import com.example.midterm_26500_c.repository.CellRepository;
import com.example.midterm_26500_c.repository.DistrictRepository;
import com.example.midterm_26500_c.repository.ProvinceRepository;
import com.example.midterm_26500_c.repository.SectorRepository;
import com.example.midterm_26500_c.repository.TagRepository;
import com.example.midterm_26500_c.repository.TaskRepository;
import com.example.midterm_26500_c.repository.UserRepository;
import com.example.midterm_26500_c.repository.VillageRepository;
import java.time.LocalDate;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SectorRepository sectorRepository;
    private final CellRepository cellRepository;
    private final VillageRepository villageRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            if (provinceRepository.count() > 0) {
                return;
            }

            Province kigali = provinceRepository.save(Province.builder().code("01").name("Kigali City").build());
            District gasabo = districtRepository.save(District.builder().code("0101").name("Gasabo").province(kigali).build());
            Sector remera = sectorRepository.save(Sector.builder().code("010101").name("Remera").district(gasabo).build());
            Cell rukiriOne = cellRepository.save(Cell.builder().code("01010101").name("Rukiri I").sector(remera).build());
            Village nyabisindu = villageRepository.save(Village.builder().code("0101010101").name("Nyabisindu").cell(rukiriOne).build());
            villageRepository.save(Village.builder().code("0101010102").name("Rukiri Village").cell(rukiriOne).build());

            Province eastern = provinceRepository.save(Province.builder().code("02").name("Eastern Province").build());
            District rwamagana = districtRepository.save(District.builder().code("0201").name("Rwamagana").province(eastern).build());
            Sector kigabiro = sectorRepository.save(Sector.builder().code("020101").name("Kigabiro").district(rwamagana).build());
            Cell nyagasenyi = cellRepository.save(Cell.builder().code("02010101").name("Nyagasenyi").sector(kigabiro).build());
            villageRepository.save(Village.builder().code("0201010101").name("Munyinya").cell(nyagasenyi).build());
            villageRepository.save(Village.builder().code("0201010102").name("Kabeza").cell(nyagasenyi).build());

            Tag backend = tagRepository.save(Tag.builder().name("Backend").build());
            Tag urgent = tagRepository.save(Tag.builder().name("Urgent").build());

            User user = User.builder()
                    .firstName("Aline")
                    .lastName("Uwimana")
                    .email("aline.uwimana@example.com")
                    .phoneNumber("250788111222")
                    .village(nyabisindu)
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
