package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.UserProfileRequest;
import com.example.midterm_26500_c.dto.request.UserRequest;
import com.example.midterm_26500_c.dto.response.UserResponse;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.entity.UserProfile;
import com.example.midterm_26500_c.entity.Village;
import com.example.midterm_26500_c.mapper.UserMapper;
import com.example.midterm_26500_c.repository.UserProfileRepository;
import com.example.midterm_26500_c.repository.UserRepository;
import com.example.midterm_26500_c.repository.VillageRepository;
import com.example.midterm_26500_c.util.PageResponse;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;

    public UserResponse create(UserRequest request) throws BadRequestException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }

        Village village = resolveVillage(request, true);
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .village(village)
                .build();

        attachOrUpdateProfile(user, request.getProfile(), true);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public PageResponse<UserResponse> getAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Page<User> users = userRepository.findAll(PageRequest.of(page, size, sort));

        return PageResponse.<UserResponse>builder()
                .content(users.getContent().stream().map(userMapper::toUserResponse).toList())
                .page(users.getNumber())
                .size(users.getSize())
                .totalElements(users.getTotalElements())
                .totalPages(users.getTotalPages())
                .last(users.isLast())
                .build();
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(Long id, UserRequest request) throws BadRequestException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (!user.getPhoneNumber().equals(request.getPhoneNumber()) && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }

        Village village = resolveVillage(request, false);
        if (village != null) {
            user.setVillage(village);
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        attachOrUpdateProfile(user, request.getProfile(), false);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public List<UserResponse> getByProvince(String provinceCode, String provinceName) throws BadRequestException {
        boolean hasCode = StringUtils.hasText(provinceCode);
        boolean hasName = StringUtils.hasText(provinceName);

        if (hasCode == hasName) {
            throw new BadRequestException("Provide either provinceCode or provinceName");
        }

        List<User> users = hasCode
                ? userRepository.findByVillage_Cell_Sector_District_Province_Code(provinceCode)
                : userRepository.findByVillage_Cell_Sector_District_Province_Name(provinceName);

        return users.stream().map(userMapper::toUserResponse).toList();
    }

    private Village resolveVillage(UserRequest request, boolean required) throws BadRequestException {
        if (StringUtils.hasText(request.getVillageCode())) {
            return villageRepository.findByCode(request.getVillageCode())
                    .orElseThrow(() -> new NoSuchElementException("Village not found with code: " + request.getVillageCode()));
        }
        if (required) {
            throw new BadRequestException("villageCode or villageName is required");
        }
        return null;
    }

    private void attachOrUpdateProfile(User user, UserProfileRequest request, boolean creating) throws BadRequestException {
        if (request == null) {
            return;
        }

        UserProfile profile = user.getProfile();
        String nationalId = request.getNationalId();

        if (creating) {
            if (userProfileRepository.existsByNationalId(nationalId)) {
                throw new BadRequestException("National ID already exists");
            }
            profile = new UserProfile();
            profile.setUser(user);
            user.setProfile(profile);
        } else {
            if (profile == null) {
                if (userProfileRepository.existsByNationalId(nationalId)) {
                    throw new BadRequestException("National ID already exists");
                }
                profile = new UserProfile();
                profile.setUser(user);
                user.setProfile(profile);
            } else if (!profile.getNationalId().equals(nationalId)
                    && userProfileRepository.existsByNationalId(nationalId)) {
                throw new BadRequestException("National ID already exists");
            }
        }

        profile.setNationalId(nationalId);
        profile.setBio(request.getBio());
        profile.setDateOfBirth(request.getDateOfBirth());
    }
}
