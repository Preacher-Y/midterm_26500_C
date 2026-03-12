package com.example.midterm_26500_c.service;

import com.example.midterm_26500_c.dto.request.LocationRequest;
import com.example.midterm_26500_c.dto.response.LocationResponse;
import com.example.midterm_26500_c.entity.Location;
import com.example.midterm_26500_c.enums.LocationType;
import com.example.midterm_26500_c.mapper.LocationMapper;
import com.example.midterm_26500_c.repository.LocationRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationResponse create(LocationRequest request) throws BadRequestException {
        validateDuplicate(request, null);
        Location parent = validateAndResolveParent(request.getType(), request.getParentId());

        Location location = Location.builder()
                .code(request.getCode())
                .name(request.getName())
                .type(request.getType())
                .parent(parent)
                .build();

        return locationMapper.toLocationResponse(locationRepository.save(location));
    }

    public List<LocationResponse> getAll() {
        return locationRepository.findAll().stream().map(locationMapper::toLocationResponse).toList();
    }

    public LocationResponse getById(UUID id) {
        return locationMapper.toLocationResponse(findById(id));
    }

    public LocationResponse update(UUID id, LocationRequest request) throws BadRequestException {
        Location location = findById(id);
        validateDuplicate(request, id);
        Location parent = validateAndResolveParent(request.getType(), request.getParentId());

        location.setCode(request.getCode());
        location.setName(request.getName());
        location.setType(request.getType());
        location.setParent(parent);

        return locationMapper.toLocationResponse(locationRepository.save(location));
    }

    public void delete(UUID id) {
        locationRepository.delete(findById(id));
    }

    public Location findById(UUID id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Location not found with id: " + id));
    }

    public Location findByCodeOrName(String code, String name) throws BadRequestException {
        if (code != null && !code.isBlank()) {
            return locationRepository.findByCode(code)
                    .orElseThrow(() -> new NoSuchElementException("Location not found with code: " + code));
        }
        if (name != null && !name.isBlank()) {
            List<Location> locations = locationRepository.findByName(name);
            if (locations.isEmpty()) {
                throw new NoSuchElementException("Location not found with name: " + name);
            }
            if (locations.size() > 1) {
                throw new BadRequestException("Multiple locations found with name: " + name + ". Use locationCode instead.");
            }
            return locations.getFirst();
        }
        throw new BadRequestException("locationCode or locationName is required");
    }

    private void validateDuplicate(LocationRequest request, UUID currentId) throws BadRequestException {
        if (locationRepository.existsByCode(request.getCode())) {
            if (currentId == null || !findByCode(request.getCode()).getId().equals(currentId)) {
                throw new BadRequestException("Location code already exists");
            }
        }

        boolean sameNameExists = request.getParentId() == null
                ? locationRepository.existsByTypeAndParentIsNullAndName(request.getType(), request.getName())
                : locationRepository.existsByTypeAndParent_IdAndName(request.getType(), request.getParentId(), request.getName());

        if (sameNameExists) {
            Location existing = findByTypeParentAndName(request);
            if (currentId == null || !existing.getId().equals(currentId)) {
                throw new BadRequestException("Location name already exists under the same parent");
            }
        }
    }

    private Location validateAndResolveParent(LocationType type, UUID parentId) throws BadRequestException {
        if (type == LocationType.PROVINCE) {
            if (parentId != null) {
                throw new BadRequestException("Province cannot have a parent");
            }
            return null;
        }

        if (parentId == null) {
            throw new BadRequestException(type + " must have a parent");
        }

        Location parent = findById(parentId);
        LocationType expectedParentType = switch (type) {
            case DISTRICT -> LocationType.PROVINCE;
            case SECTOR -> LocationType.DISTRICT;
            case CELL -> LocationType.SECTOR;
            case VILLAGE -> LocationType.CELL;
            default -> null;
        };

        if (parent.getType() != expectedParentType) {
            throw new BadRequestException(type + " must belong to a " + expectedParentType);
        }

        return parent;
    }

    private Location findByCode(String code) {
        return locationRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchElementException("Location not found with code: " + code));
    }

    private Location findByTypeParentAndName(LocationRequest request) {
        return locationRepository.findByName(request.getName()).stream()
                .filter(location -> location.getType() == request.getType())
                .filter(location -> {
                    if (request.getParentId() == null) {
                        return location.getParent() == null;
                    }
                    return location.getParent() != null && request.getParentId().equals(location.getParent().getId());
                })
                .findFirst()
                .orElseThrow();
    }
}
