package it.soriani.tefo.service;

import io.netty.buffer.Unpooled;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.ChatsDTO;
import it.soriani.tefo.dto.model.MessagesDTO;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.dto.request.MessagesRequestDTO;
import it.soriani.tefo.entity.Messages;
import it.soriani.tefo.error.NotFoundException;
import it.soriani.tefo.mapper.MessagesMapper;
import it.soriani.tefo.repository.MessagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import telegram4j.tl.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static it.soriani.tefo.constants.GenericConstants.CODE_NOT_FOUND_ERROR;
import static it.soriani.tefo.constants.GenericConstants.NOT_FOUND_ERROR;

/**
 * @author christiansoriani on 28/01/24
 * @project TEFO_BE
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final MessagesMapper messagesMapper;
    private final UsersService usersService;
    private final ChatsService chatsService;

    public MessagesDTO.MessagesManiputaled getMessageById(Integer id) {
        Optional<Messages> messages = messagesRepository.findById(id);
        if (messages.isEmpty()) {
            return MessagesDTO.MessagesManiputaled.builder().build();
        } else {
            MessagesDTO messagesDTO = messagesMapper.entityToDto(messages.get());
            convertMessagesManipulated(messagesDTO);
            return messagesDTO.getMessagesManiputaled();
        }
    }

    public Page<MessagesDTO> getAllMessages(Pageable pageable, MessagesRequestDTO messagesRequestDTO) {
        Page<Messages> messages = messagesRepository.findAll(pageable);
        Page<MessagesDTO> messagesDTOS = convertMessages(messages);
        if (Objects.isNull(messagesRequestDTO)) {
            return messagesDTOS;
        } else {
            List<MessagesDTO> messagesDTOList = new ArrayList<>(messagesDTOS.getContent());
            if (Objects.nonNull(messagesRequestDTO.getNamSurnameUser())) {
                filterMessagesByUser(messagesDTOList, messagesRequestDTO.getNamSurnameUser());
            }
            if (Objects.nonNull(messagesRequestDTO.getIsSent())) {
                filterMessagesByIsSent(messagesDTOList, messagesRequestDTO.getIsSent());
            }
            if (Objects.nonNull(messagesRequestDTO.getIsDelivered())) {
                filterMessagesByIsDelivered(messagesDTOList, messagesRequestDTO.getIsDelivered());
            }
            if (Objects.nonNull(messagesRequestDTO.getIsRead())) {
                filterMessagesByIsRead(messagesDTOList, messagesRequestDTO.getIsRead());
            }
            if (Objects.nonNull(messagesRequestDTO.getHasMedia())) {
                filterMessagesByHasMedia(messagesDTOList, messagesRequestDTO.getHasMedia());
            }

            return new PageImpl<>(messagesDTOList, pageable, messagesDTOList.size());
        }

    }

    private void filterMessagesByIsDelivered(List<MessagesDTO> messagesList, Boolean isDelivered) {
        messagesList.removeIf(messages -> (Objects.nonNull(messages.getMessagesManiputaled()) && !Objects.equals(messages.getMessagesManiputaled().getIsDelivered(), isDelivered)) || Objects.isNull(messages.getMessagesManiputaled()));
    }

    private void filterMessagesByIsRead(List<MessagesDTO> messagesList, Boolean isRead) {
        messagesList.removeIf(messages -> (Objects.nonNull(messages.getMessagesManiputaled()) && !Objects.equals(messages.getMessagesManiputaled().getIsRead(), isRead)) || Objects.isNull(messages.getMessagesManiputaled()));
    }

    private void filterMessagesByIsSent(List<MessagesDTO> messagesList, Boolean isSent) {
        messagesList.removeIf(messages -> (Objects.nonNull(messages.getMessagesManiputaled()) && !Objects.equals(messages.getMessagesManiputaled().getIsSent(), isSent)) || Objects.isNull(messages.getMessagesManiputaled()));
    }

    private void filterMessagesByUser(List<MessagesDTO> messagesList, String namSurnameUser) {
        messagesList.removeIf(messages -> (Objects.nonNull(messages.getMessagesManiputaled()) && !Objects.equals(messages.getMessagesManiputaled().getFromUser().getNameAndSurname(), namSurnameUser)) || Objects.isNull(messages.getMessagesManiputaled()));
    }

    private void filterMessagesByHasMedia(List<MessagesDTO> messagesList, Boolean hasMedia) {
        messagesList.removeIf(messages -> (Objects.nonNull(messages.getMessagesManiputaled()) && !Objects.equals(messages.getMessagesManiputaled().getHasMedia(), hasMedia)) || Objects.isNull(messages.getMessagesManiputaled()));
    }

    public Page<MessagesDTO> convertMessages(Page<Messages> messages) {
        if (messages.isEmpty()) {
            throw NotFoundException.of(CODE_NOT_FOUND_ERROR, String.format(NOT_FOUND_ERROR, "messages"));
        }
        final Page<MessagesDTO> messagesDTOS = messages.map(messagesMapper::entityToDto);
        messagesDTOS.forEach(this::convertMessagesManipulated);
        return messagesDTOS;
    }

    public void convertMessagesManipulated(MessagesDTO messagesDTO) {
        try {
            Message message = TlDeserializer.deserialize(Unpooled.copiedBuffer(messagesDTO.getData()));
            if (message instanceof ImmutableBaseMessage) {
                ImmutableBaseMessage baseMessage = (ImmutableBaseMessage) message;
                messagesDTO.setMessagesManiputaled(buildMessagesManipulated(messagesDTO, baseMessage));
                manageUser(messagesDTO, baseMessage);
            }
        } catch (IllegalArgumentException e) {
            log.warn("Error while converting messages data to chat manipulated", e);
        }

    }

    private static MessagesDTO.MessagesManiputaled buildMessagesManipulated(MessagesDTO messagesDTO, ImmutableBaseMessage baseMessage) {
        boolean hasmedia = false;
        String mimeType = null;
        AtomicReference<String> media = new AtomicReference<>();
        if (Objects.nonNull(baseMessage.media())) {
            if (baseMessage.media() instanceof ImmutableMessageMediaDocument) {
                hasmedia = true;
                ImmutableMessageMediaDocument mediaDocument = (ImmutableMessageMediaDocument) baseMessage.media();
                if (Objects.nonNull(mediaDocument.document()) && mediaDocument.document() instanceof ImmutableBaseDocument) {
                    ImmutableBaseDocument document = (ImmutableBaseDocument) mediaDocument.document();
                    mimeType = document.mimeType();
                    document.attributes().forEach(attribute -> {
                        if (attribute instanceof DocumentAttributeFilename) {
                            DocumentAttributeFilename documentAttribute = (DocumentAttributeFilename) attribute;
                            if (documentAttribute instanceof ImmutableDocumentAttributeFilename) {
                                ImmutableDocumentAttributeFilename immutableDocumentAttributeVideo = (ImmutableDocumentAttributeFilename) documentAttribute;
                                media.set(immutableDocumentAttributeVideo.fileName());
                            }
                        }
                    });

                }
            } else if (baseMessage.media() instanceof ImmutableMessageMediaPhoto) {
                hasmedia = true;
            }
        }

        return MessagesDTO.MessagesManiputaled.builder()
                .content(baseMessage.message())
                .isSent(messagesDTO.getReadState() == 2 || messagesDTO.getReadState() == 3)
                .isRead(messagesDTO.getReadState() == 3)
                .isDelivered(messagesDTO.getOut() == 0)
                .pinned(baseMessage.pinned())
                .date(LocalDateTime.ofInstant(Instant.ofEpochSecond(baseMessage.date()), TimeZone.getDefault().toZoneId()).format(DateTimeFormatter.ofPattern(GenericConstants.DATE_TIME_FORMAT_ITALY)))
                .countForward(baseMessage.forwards())
                .hasMedia(hasmedia)
                .mimeType(mimeType)
                .media(media.get())
                .build();
    }

    private void manageUser(MessagesDTO messagesDTO, ImmutableBaseMessage baseMessage) {
        if (baseMessage.peerId() instanceof ImmutablePeerUser) {
            ImmutablePeerUser peerUser = (ImmutablePeerUser) baseMessage.peerId();
            UsersDTO.UsersManipulated user = usersService.getUserById(peerUser.userId());
            if (messagesDTO.getOut() == 1) {
                messagesDTO.getMessagesManiputaled().setToUser(user);
            } else {
                messagesDTO.getMessagesManiputaled().setFromUser(user);
            }
        } else if (baseMessage.peerId() instanceof ImmutablePeerChat) {
            ImmutablePeerChat peerChat = (ImmutablePeerChat) baseMessage.peerId();
            ChatsDTO.ChatsManipulated chat = chatsService.getChatById(peerChat.chatId());
            messagesDTO.getMessagesManiputaled().setFromChat(chat);
        } else if (baseMessage.peerId() instanceof ImmutablePeerChannel) {
            ImmutablePeerChannel peerChat = (ImmutablePeerChannel) baseMessage.peerId();
            ChatsDTO.ChatsManipulated chat = chatsService.getChatById(peerChat.channelId());
            messagesDTO.getMessagesManiputaled().setFromChat(chat);
        }

    }

}
