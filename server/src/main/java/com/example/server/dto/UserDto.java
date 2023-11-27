package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private String displayName;
    private String creationDate;

    public ClientDto toClientDto() {
        return ClientDto.builder()
                .name(displayName)
                .creationDate(creationDate)
                .build();
    }
}

