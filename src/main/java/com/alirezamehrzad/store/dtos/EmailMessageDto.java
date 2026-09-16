package com.alirezamehrzad.store.dtos;

public record EmailMessageDto(
        String to,
        String subject,
        String body
) {}
