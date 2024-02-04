package it.soriani.tefo.service;

import io.netty.buffer.Unpooled;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.MessagesDTO;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.entity.Messages;
import it.soriani.tefo.error.NotFoundException;
import it.soriani.tefo.mapper.MessagesMapper;
import it.soriani.tefo.repository.MessagesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import telegram4j.tl.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;
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

    public Page<MessagesDTO> getAllMessages(Pageable pageable) {
        Page<Messages> messages = messagesRepository.findAll(pageable);
        return convertMessages(messages);
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
                messagesDTO.setMessagesManiputaled(buildMessagesManipulated(baseMessage));
                if (messagesDTO.getOut() == 1) {
                    messagesDTO.getMessagesManiputaled().setToUser(buildUsersManipulated(baseMessage));
                } else {
                    messagesDTO.getMessagesManiputaled().setFromUser(buildUsersManipulated(baseMessage));
                }
            }
        } catch (IllegalArgumentException e) {
            log.warn("Error while converting messages data to chat manipulated", e);
        }

    }

    private static MessagesDTO.MessagesManiputaled buildMessagesManipulated(ImmutableBaseMessage baseMessage) {

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
            }
        }

        return MessagesDTO.MessagesManiputaled.builder()
                .content(baseMessage.message())
                .isSent(baseMessage.out())
                .pinned(baseMessage.pinned())
                .date(LocalDateTime.ofInstant(Instant.ofEpochSecond(baseMessage.date()), TimeZone.getDefault().toZoneId()).format(DateTimeFormatter.ofPattern(GenericConstants.DATE_TIME_FORMAT_ITALY)))
                .countForward(baseMessage.forwards())
                .hasMedia(hasmedia)
                .mimeType(mimeType)
                .media(media.get())
                .build();
    }

    private UsersDTO.UsersManipulated buildUsersManipulated(ImmutableBaseMessage baseMessage) {
        if (baseMessage.peerId() instanceof ImmutablePeerUser) {
            ImmutablePeerUser peerUser = (ImmutablePeerUser) baseMessage.peerId();
            return usersService.getUserById(peerUser.userId());
        }

        return null;
    }

}
