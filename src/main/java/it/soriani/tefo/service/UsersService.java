package it.soriani.tefo.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.entity.Users;
import it.soriani.tefo.mapper.UsersMapper;
import it.soriani.tefo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

    public List<UsersDTO> getAllUsers() {
        final List<Users> usersList = usersRepository.findAll().stream().filter(users -> users.getStatus() != 0).collect(Collectors.toList());
        final List<UsersDTO> usersDTOList = usersMapper.entityListToDtoList(usersList);
        usersDTOList.forEach(UsersService::convertUsersManipulatedManipulated);
        return usersDTOList;
    }

    public static void convertUsersManipulatedManipulated(UsersDTO usersDTO) {
        String[] parsedName = usersDTO.getName().split(";;;");
        String nameAndSurname = parsedName[0];
        String username = "Username not found";
        if (parsedName.length > 1) {
            username = parsedName[1];
        }

        LocalDateTime lastStatus = null;
        if (usersDTO.getStatus() != -100 && usersDTO.getStatus() != -101 && usersDTO.getStatus() != -102) {
            lastStatus = LocalDateTime.ofInstant(Instant.ofEpochSecond(usersDTO.getStatus()), TimeZone.getDefault().toZoneId());
        } else {
            lastStatus = LocalDateTime.now();
        }

        byte[] data = usersDTO.getData();
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

        usersDTO.setContactsManipulated(UsersDTO.UsersManipulated.builder()
                .username(username)
                .nameAndSurname(nameAndSurname)
                .lastStatus(lastStatus)
                .phoneNumber(phone)
                .build());
    }

}
