package it.soriani.tefo.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.entity.Users;
import it.soriani.tefo.error.NotFoundException;
import it.soriani.tefo.mapper.UsersMapper;
import it.soriani.tefo.repository.UsersRepository;
import it.soriani.tefo.specification.SpecificationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;

import static it.soriani.tefo.constants.GenericConstants.CODE_NOT_FOUND_ERROR;
import static it.soriani.tefo.constants.GenericConstants.NOT_FOUND_ERROR;

/**
 * @author christiansoriani on 24/01/24
 * @project TEFO_BE
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public Page<UsersDTO> getAllUsers(Pageable pageable, UsersDTO.UsersManipulated usersManipulated) {
        if (Objects.isNull(usersManipulated)) {
            return getAllUsers(pageable);
        } else {
            Page<Users> usersList = usersRepository.findAll(SpecificationUser.getSpecification(usersManipulated), pageable);
            return userConversion(usersList);
        }
    }

    private Page<UsersDTO> getAllUsers(Pageable pageable) {
        final Page<Users> usersList = usersRepository.findAllByStatusIsNot(pageable, 0);
        return userConversion(usersList);
    }

    private Page<UsersDTO> userConversion(Page<Users> usersList) {
        if (usersList.isEmpty()) {
            throw NotFoundException.of(CODE_NOT_FOUND_ERROR, String.format(NOT_FOUND_ERROR, "chats"));
        }
        final Page<UsersDTO> usersDTOList = usersList.map(usersMapper::entityToDto);
        usersDTOList.forEach(UsersService::convertUsersManipulatedManipulated);
        return usersDTOList;
    }

    private static void convertUsersManipulatedManipulated(UsersDTO usersDTO) {
        String[] parsedName = usersDTO.getName().split(";;;");
        String nameAndSurname = parsedName[0];
        String username = "Username not found";
        if (parsedName.length > 1) {
            username = parsedName[1];
        }

        LocalDateTime lastStatus;
        if (usersDTO.getStatus() != -100 && usersDTO.getStatus() != -101 && usersDTO.getStatus() != -102) {
            lastStatus = LocalDateTime.ofInstant(Instant.ofEpochSecond(usersDTO.getStatus()), TimeZone.getDefault().toZoneId());
        } else {
            lastStatus = LocalDateTime.now();
        }

        String statusDate = lastStatus.format(DateTimeFormatter.ofPattern(GenericConstants.DATE_TIME_FORMAT_ITALY));

        usersDTO.setUsersManipulated(UsersDTO.UsersManipulated.builder()
                .username(username)
                .nameAndSurname(nameAndSurname)
                .lastStatus(statusDate)
                .phoneNumber(getPhoneNumber(usersDTO.getData()))
                .isInContactsList(Objects.nonNull(usersDTO.getContact()) && usersDTO.getContact().getMutual() != 0)
                .build());
    }

    private static String getPhoneNumber(byte[] data) {
        String phone = null;
        for (int i = 0; i < data.length - 11; i++) {
            boolean isPhoneNumber = true;
            for (int j = i; j < i + 12; j++) {
                if (!Character.isDigit((char) data[j])) {
                    isPhoneNumber = false;
                    break;
                }
            }
            if (isPhoneNumber) {
                byte[] phoneBytes = new byte[12];
                System.arraycopy(data, i, phoneBytes, 0, 12);
                phone = new String(phoneBytes, StandardCharsets.UTF_8);
                break;
            }
        }
        if (StringUtils.isEmpty(phone)) {
            phone = "No Phone Number assigned";
        }

        if (StringUtils.startsWith(phone, "39")) {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, "IT");
                phone = String.valueOf(phoneNumber.getNationalNumber());
            } catch (NumberParseException e) {
                log.warn("Error parsing phone number: {}, {}", phone, ExceptionUtils.getStackTrace(e));
            }
        }

        return phone;
    }

}
