package com.muc.rfid.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muc.rfid.dto.ApiResponse;
import com.muc.rfid.dto.TagResponse;
import com.muc.rfid.dto.TagUploadRequest;
import com.muc.rfid.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;
    private final ObjectMapper objectMapper;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<List<TagResponse>>> uploadTags(@RequestBody List<Map<String, Object>> requests)
            throws JsonProcessingException {

        List<TagResponse> responses = new ArrayList<>();

        for (Map<String, Object> tag : requests) {
            log.info("TAG: {}", tag);

            TagUploadRequest request = mapReaderPayload(tag);
            String rawJson = objectMapper.writeValueAsString(tag);

            TagResponse response = tagService.uploadTag(request, rawJson);
            responses.add(response);
        }

        return ResponseEntity.ok(ApiResponse.success(responses, "Tags processed successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TagResponse>>> getAllTags(
            @RequestParam(required = false) String epc,
            @RequestParam(required = false) String readerId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        Page<TagResponse> tags = tagService.getAllTags(epc, readerId, pageable);
        return ResponseEntity.ok(ApiResponse.success(tags, "Tags retrieved successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> getTagById(@PathVariable Long id) {
        TagResponse tag = tagService.getTagById(id);
        return ResponseEntity.ok(ApiResponse.success(tag, "Tag retrieved successfully"));
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<TagResponse>> getLatestTag() {
        TagResponse tag = tagService.getLatestTag();
        return ResponseEntity.ok(ApiResponse.success(tag, "Latest tag retrieved successfully"));
    }

    private TagUploadRequest mapReaderPayload(Map<String, Object> tag) {
        return TagUploadRequest.builder()
                .epc(asString(tag.get("epc")))
                .tid(asString(tag.get("tid")))
                .timestamp(parseTimestamp(tag.get("timestamp")))
                .antennaId(asInteger(tag.get("ant")))
                .rssi(asDouble(tag.get("rssi")))
                .count(asInteger(tag.get("count")))
                .frequencyPoint(asDouble(tag.get("freq")))
                .phase(asDouble(tag.get("phase")))
                .readerId(asString(tag.get("serialno")))
                .userData(asString(tag.get("userdata")))
                .reserved(asString(tag.get("reserved")))
                .build();
    }

    private String asString(Object value) {
        return value != null ? String.valueOf(value) : null;
    }

    private Integer asInteger(Object value) {
        if (value == null) return null;
        try {
            if (value instanceof Number number) {
                return number.intValue();
            }
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    private Double asDouble(Object value) {
        if (value == null) return null;
        try {
            if (value instanceof Number number) {
                return number.doubleValue();
            }
            return Double.parseDouble(String.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseTimestamp(Object value) {
        if (value == null) return null;

        String text = String.valueOf(value).trim();

        List<DateTimeFormatter> formats = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
        );

        for (DateTimeFormatter formatter : formats) {
            try {
                return LocalDateTime.parse(text, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        return null;
    }
}