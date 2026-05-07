package com.muc.rfid.service;

import com.muc.rfid.dto.TagUploadRequest;
import com.muc.rfid.dto.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    TagResponse uploadTag(TagUploadRequest request, String rawJson);
    Page<TagResponse> getAllTags(String epc, String readerId, Pageable pageable);
    TagResponse getTagById(Long id);
    TagResponse getLatestTag();
}
