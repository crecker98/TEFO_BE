package it.soriani.tefo.service;

import io.netty.buffer.Unpooled;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.ChatsDTO;
import it.soriani.tefo.dto.request.ChatsRequestDTO;
import it.soriani.tefo.entity.Chats;
import it.soriani.tefo.error.NotFoundException;
import it.soriani.tefo.mapper.ChatsMapper;
import it.soriani.tefo.repository.ChatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import telegram4j.tl.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import static it.soriani.tefo.constants.GenericConstants.CODE_NOT_FOUND_ERROR;
import static it.soriani.tefo.constants.GenericConstants.NOT_FOUND_ERROR;

/**
 * @author christiansoriani on 02/02/24
 * @project TEFO_BE
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatsService {

    private final ChatsRepository chatsRepository;
    private final ChatsMapper chatsMapper;

    public Page<ChatsDTO> getAllChats(Pageable pageable, ChatsRequestDTO request) {
        Page<Chats> chatsList = chatsRepository.findAll(pageable);
        Page<ChatsDTO> chatsDTOS = convertChats(chatsList);
        if (Objects.nonNull(request)) {
            List<ChatsDTO> newListChats = new ArrayList<>(chatsDTOS.getContent());
            if (Objects.nonNull(request.getTitle())) {
                filterChatsByTitle(newListChats, request.getTitle());
            }
            if (Objects.nonNull(request.getNumberOfParticipants())) {
                filterChatsByNumberOfParticipants(newListChats, request.getNumberOfParticipants());
            }
            if (Objects.nonNull(request.getLeft())) {
                filterChatsByBannedRights(newListChats, request.getLeft());
            }

            return new PageImpl<>(newListChats, pageable, newListChats.size());
        } else {
            return chatsDTOS;
        }
    }

    private void filterChatsByBannedRights(List<ChatsDTO> chatsList, Boolean left) {
        chatsList.removeIf(chats -> (Objects.nonNull(chats.getChatsManipulated()) && !Objects.equals(chats.getChatsManipulated().getLeft(), left)) || Objects.isNull(chats.getChatsManipulated()));
    }

    private void filterChatsByNumberOfParticipants(List<ChatsDTO> chatsList, Integer numberOfParticipants) {
        chatsList.removeIf(chats -> (Objects.nonNull(chats.getChatsManipulated()) && !Objects.equals(chats.getChatsManipulated().getNumberOfParticipants(), numberOfParticipants)) || Objects.isNull(chats.getChatsManipulated()));
    }

    private void filterChatsByTitle(List<ChatsDTO> chatsList, String title) {
        chatsList.removeIf(chats -> (Objects.nonNull(chats.getChatsManipulated()) && !StringUtils.containsAnyIgnoreCase(chats.getChatsManipulated().getTitle(), title)) || Objects.isNull(chats.getChatsManipulated()));
    }

    private Page<ChatsDTO> convertChats(Page<Chats> chatsList) {
        if (chatsList.isEmpty()) {
            throw NotFoundException.of(CODE_NOT_FOUND_ERROR, String.format(NOT_FOUND_ERROR, "chats"));
        }
        final Page<ChatsDTO> chatsDTOS = chatsList.map(chatsMapper::entityToDto);
        chatsDTOS.forEach(ChatsService::convertChatsManipulated);
        return chatsDTOS;
    }

    public static void convertChatsManipulated(ChatsDTO chats) {
        try {
            Chat chat = TlDeserializer.deserialize(Unpooled.copiedBuffer(chats.getData()));
            if (chat instanceof ImmutableBaseChat) {
                ImmutableBaseChat baseChat = (ImmutableBaseChat) chat;
                chats.setChatsManipulated(buildChatsManipulated(baseChat));
            }
        } catch (IllegalArgumentException e) {
            log.warn("Error while converting chat data to chat manipulated", e);
        }
    }

    private static ChatsDTO.ChatsManipulated buildChatsManipulated(ImmutableBaseChat baseChat) {
        return ChatsDTO.ChatsManipulated.builder()
                .title(baseChat.title())
                .numberOfParticipants(baseChat.participantsCount())
                .creationDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(baseChat.date()), TimeZone.getDefault().toZoneId()).format(DateTimeFormatter.ofPattern(GenericConstants.DATE_TIME_FORMAT_ITALY)))
                .left(baseChat.left())
                .bannedRights(buildBannedRights(baseChat.defaultBannedRights()))
                .build();
    }

    private static ChatsDTO.BannedRightManipulated buildBannedRights(ChatBannedRights bannedRights) {
        if (bannedRights instanceof ImmutableChatBannedRights) {
            ImmutableChatBannedRights chatBannedRights = (ImmutableChatBannedRights) bannedRights;
            return ChatsDTO.BannedRightManipulated.builder()
                    .canViewMessages(chatBannedRights.viewMessages())
                    .canSendMessages(chatBannedRights.sendMessages())
                    .canSendMedia(chatBannedRights.sendMedia())
                    .canChangeInfo(chatBannedRights.changeInfo())
                    .canInviteUsers(chatBannedRights.inviteUsers())
                    .build();
        }

        return null;

    }

}
