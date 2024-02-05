package it.soriani.tefo.service;

import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.DialogsDTO;
import it.soriani.tefo.entity.Dialogs;
import it.soriani.tefo.error.NotFoundException;
import it.soriani.tefo.mapper.DialogsMapper;
import it.soriani.tefo.repository.DialogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static it.soriani.tefo.constants.GenericConstants.CODE_NOT_FOUND_ERROR;
import static it.soriani.tefo.constants.GenericConstants.NOT_FOUND_ERROR;

/**
 * @author christiansoriani on 27/01/24
 * @project TEFO_BE
 */

@Service
@RequiredArgsConstructor
public class DialogsService {

    private final DialogsRepository dialogsRepository;
    private final DialogsMapper dialogsMapper;
    private final MessagesService messagesService;

    public Page<DialogsDTO> getAllDialogs(Pageable pageable) {
        Page<Dialogs> dialogs = dialogsRepository.findAll(pageable);
        return convertDialogs(dialogs);
    }

    private Page<DialogsDTO> convertDialogs(Page<Dialogs> dialogs) {
        if (dialogs.isEmpty()) {
            throw NotFoundException.of(CODE_NOT_FOUND_ERROR, String.format(NOT_FOUND_ERROR, "dialogs"));
        }
        final Page<DialogsDTO> dialogsDTOS = dialogs.map(dialogsMapper::entityToDto);
        dialogsDTOS.forEach(this::convertDialogsManipulated);
        return dialogsDTOS;
    }

    public void convertDialogsManipulated(DialogsDTO dialogsDTO) {
        dialogsDTO.setDialogsManipulated(
                DialogsDTO.DialogsManipulated.builder()
                        .lastDateOperation(LocalDateTime.ofInstant(Instant.ofEpochSecond(dialogsDTO.getDate()), TimeZone.getDefault().toZoneId()).format(DateTimeFormatter.ofPattern(GenericConstants.DATE_TIME_FORMAT_ITALY)))
                        .numeberOfMessagesNoread(dialogsDTO.getUnreadCount())
                        .lastMessage(dialogsDTO.getLastMessageId() != 0 ? messagesService.getMessageById(dialogsDTO.getLastMessageId()) : null)
                        .isPinned(dialogsDTO.getPinned())
                        .build()
        );
    }

}
