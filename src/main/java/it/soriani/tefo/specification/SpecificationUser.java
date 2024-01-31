package it.soriani.tefo.specification;

import it.soriani.tefo.constants.GenericConstants;
import it.soriani.tefo.dto.model.UsersDTO;
import it.soriani.tefo.entity.Users;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author christiansoriani on 31/01/24
 * @project TEFO_BE
 */
public class SpecificationUser {

    private SpecificationUser() {
    }

    public static Specification<Users> getSpecification(UsersDTO.UsersManipulated usersManipulated) {
        Specification<Users> specification = Specification.where(null);
        if (StringUtils.isNotEmpty(usersManipulated.getUsername())) {
            String username = ";;;%" + usersManipulated.getUsername().toLowerCase();
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), MatchMode.ANYWHERE.toMatchString(username)));
        } else if (StringUtils.isNotEmpty(usersManipulated.getNameAndSurname())) {
            String nameSurname = usersManipulated.getNameAndSurname().toLowerCase() + "%;;;";
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), MatchMode.ANYWHERE.toMatchString(nameSurname)));
        } else if (StringUtils.isNotEmpty(usersManipulated.getLastStatus())) {
            LocalDateTime date = LocalDateTime.parse(usersManipulated.getLastStatus(), DateTimeFormatter.ofPattern(GenericConstants.DATE_TIME_FORMAT_ITALY));
            int dateConverted = (int) date.toInstant(ZoneOffset.UTC).toEpochMilli();
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("name"), dateConverted));
        } else if (StringUtils.isNotEmpty(usersManipulated.getPhoneNumber())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.function("HEX", String.class, root.get("data").as(byte[].class)), MatchMode.ANYWHERE.toMatchString(convertPhoneNumberToHex(usersManipulated.getPhoneNumber()))));
        } else if (Objects.nonNull(usersManipulated.getIsInContactsList())) {
            specification = specification.and((root, query, criteriaBuilder) -> {
                root.join("uid");
                return criteriaBuilder.equal(root.get("contact").get("mutual"), Boolean.TRUE.equals(usersManipulated.getIsInContactsList()) ? 1 : 0);
            });
        }

        specification = specification.and((root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("status"), 0)
        );
        return specification;
    }

    private static String convertPhoneNumberToHex(String phoneNumber) {
        byte[] phoneNumberBytes = phoneNumber.getBytes();
        StringBuilder hexPhoneNumber = new StringBuilder();
        for (byte b : phoneNumberBytes) {
            hexPhoneNumber.append(String.format("%02X", b));
        }
        return hexPhoneNumber.toString();
    }

}
