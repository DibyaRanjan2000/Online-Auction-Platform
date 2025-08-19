package com.bluepal.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bluepal.dto.ItemDtoReq;
import com.bluepal.dto.ItemRes;

public interface ItemService {
    
    ItemRes create(String username, ItemDtoReq req);

    Page<ItemRes> live(Pageable pageable);
    public Page<ItemRes> getLiveItems(Pageable pageable);
}
