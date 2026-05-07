package com.muc.rfid.service.impl;

import com.muc.rfid.dto.TagResponse;
import com.muc.rfid.dto.TagUploadRequest;
import com.muc.rfid.entity.TagRead;
import com.muc.rfid.exception.ResourceNotFoundException;
import com.muc.rfid.repository.TagReadRepository;
import com.muc.rfid.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagReadRepository tagReadRepository;

    @Override
    @Transactional
    public TagResponse uploadTag(TagUploadRequest request, String rawJson) {
        LocalDateTime threeSecondsAgo = LocalDateTime.now().minusSeconds(3);

        boolean exists = tagReadRepository.existsByEpcAndReaderIdAndCreatedAtAfter(
                request.getEpc(),
                request.getReaderId(),
                threeSecondsAgo
        );

        if (exists) {
            log.info("Duplicate tag upload detected for EPC: {} from Reader: {} within 3s window. Skipping persistence.",
                    request.getEpc(), request.getReaderId());

            TagRead latest = tagReadRepository
                    .findFirstByEpcAndReaderIdOrderByCreatedAtDesc(request.getEpc(), request.getReaderId())
                    .orElse(TagRead.builder()
                            .epc(request.getEpc())
                            .readerId(request.getReaderId())
                            .companyName(request.getCompanyName())
                            .timestamp(request.getTimestamp())
                            .build());

            return mapToResponse(latest);
        }

        TagRead tagRead = TagRead.builder()
                .epc(request.getEpc())
                .tid(request.getTid())
                .timestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now())
                .antennaId(request.getAntennaId())
                .rssi(request.getRssi())
                .count(request.getCount())
                .frequencyPoint(request.getFrequencyPoint())
                .phase(request.getPhase())
                .readerId(request.getReaderId())
                .companyName(request.getCompanyName())
                .userData(request.getUserData())
                .reserved(request.getReserved())
                .rawPayload(rawJson)
                .build();

        TagRead saved = tagReadRepository.save(tagRead);
        log.info("Saved new tag read: {} from reader: {}", saved.getEpc(), saved.getReaderId());

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagResponse> getAllTags(String epc, String readerId, Pageable pageable) {
        return tagReadRepository.findByFilters(epc, readerId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getTagById(Long id) {
        return tagReadRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Tag read not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getLatestTag() {
        return tagReadRepository.findFirstByOrderByCreatedAtDesc()
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("No tag reads found"));
    }

    private TagResponse mapToResponse(TagRead entity) {
        return TagResponse.builder()
                .id(entity.getId())
                .epc(entity.getEpc())
                .tid(entity.getTid())
                .timestamp(entity.getTimestamp())
                .antennaId(entity.getAntennaId())
                .rssi(entity.getRssi())
                .count(entity.getCount())
                .frequencyPoint(entity.getFrequencyPoint())
                .phase(entity.getPhase())
                .readerId(entity.getReaderId())
                .companyName(entity.getCompanyName())
                .userData(entity.getUserData())
                .reserved(entity.getReserved())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}