package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.UserProfileRequest;
import com.example.midterm_26500_c.dto.request.UserRequest;
import com.example.midterm_26500_c.dto.response.UserResponse;
import com.example.midterm_26500_c.entity.Location;
import com.example.midterm_26500_c.entity.User;
import com.example.midterm_26500_c.entity.UserProfile;
import com.example.midterm_26500_c.repository.LocationRepository;
import com.example.midterm_26500_c.mapper.UserMapper;
import com.example.midterm_26500_c.repository.UserProfileRepository;
import com.example.midterm_26500_c.repository.UserRepository;
import com.example.midterm_26500_c.util.PageResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
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
    private final LocationRepository locationRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;
    private final LocationService locationService;

    public UserResponse create(UserRequest request) throws BadRequestException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }

        Location location = resolveLocation(request, true);
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .location(location)
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

    public UserResponse getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(UUID id, UserRequest request) throws BadRequestException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (!user.getPhoneNumber().equals(request.getPhoneNumber()) && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }

        Location location = resolveLocation(request, false);
        if (location != null) {
            user.setLocation(location);
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        attachOrUpdateProfile(user, request.getProfile(), false);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        userRepository.delete(user);
    }

    public List<UserResponse> getByLocation(String locationCode, String locationName) throws BadRequestException {
        Location location = locationService.findByCodeOrName(locationCode, locationName);
        List<User> users = userRepository
                .findByLocation_IdOrLocation_Parent_IdOrLocation_Parent_Parent_IdOrLocation_Parent_Parent_Parent_IdOrLocation_Parent_Parent_Parent_Parent_Id(
                        location.getId(),
                        location.getId(),
                        location.getId(),
                        location.getId(),
                        location.getId()
                );

        return users.stream().map(userMapper::toUserResponse).toList();
    }

    private Location resolveLocation(UserRequest request, boolean required) throws BadRequestException {
        if (StringUtils.hasText(request.getLocationCode())) {
            return locationRepository.findByCode(request.getLocationCode())
                    .orElseThrow(() -> new NoSuchElementException("Location not found with code: " + request.getLocationCode()));
        }
        if (StringUtils.hasText(request.getLocationName())) {
            List<Location> locations = locationRepository.findByName(request.getLocationName());
            if (locations.isEmpty()) {
                throw new NoSuchElementException("Location not found with name: " + request.getLocationName());
            }
            if (locations.size() > 1) {
                throw new BadRequestException("Multiple locations found with name: " + request.getLocationName() + ". Use locationCode instead.");
            }
            return locations.getFirst();
        }
        if (required) {
            throw new BadRequestException("locationCode or locationName is required");
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
