package Final.Project.dodo.service.impl;


import Final.Project.dodo.model.dto.AccountDto;
import Final.Project.dodo.service.AccountService;
import Final.Project.dodo.utils.JwtProvider;
import Final.Project.dodo.utils.ResourceBundelLanguage;
import Final.Project.dodo.utils.language;
import Final.Project.dodo.exception.AuthException;
import Final.Project.dodo.exception.AuthNotAcceptableException;
import Final.Project.dodo.model.dto.UserDto;

import Final.Project.dodo.model.enums.Status;
import Final.Project.dodo.model.request.auth.AuthRequest;
import Final.Project.dodo.model.request.auth.ValidateEmailReq;
import Final.Project.dodo.model.request.create.UserCreateRequest;

import Final.Project.dodo.model.response.UserResponse;
import Final.Project.dodo.service.AuthService;
import Final.Project.dodo.service.UserService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {


    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final MailService mailService;
    private final AccountService accountService;


    public AuthServiceImpl(JwtProvider jwtProvider, UserService userService, MailService mailService, AccountService accountService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.mailService = mailService;
        this.accountService = accountService;
    }

    @Override
    public Long validateToken(String token, Integer lang) {
        return jwtProvider.validateToken(token, lang);
    }

    @Override
    public String getClaim(String token, Integer lang) {
        return jwtProvider.getClaim(token, lang);
    }

    public boolean checkTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between( localDateTime, currentTime);
        long minutesDifference = duration.toMinutes();
        return minutesDifference < 10;
    }


    private Boolean checkEmail(String email) {
        return email.contains("@") && !Pattern.matches(".*[а-яА-Я].*", email);
    }

    private String generateTempPass() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        String randomString = String.valueOf(randomNumber);

        return randomString.substring(0, 3) + "-" + randomString.substring(3);
    }

    @Override
    public String auth(AuthRequest request, Integer languageOrdinal) {
        String tempPass;
        if (request.getEmail() == null) {
            throw new AuthNotAcceptableException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(languageOrdinal), "Couldnotparsemail"));
        } else if (checkEmail(request.getEmail())) {

            AccountDto accountDto = accountService.findByEmail(request.getEmail(), languageOrdinal);
            tempPass = generateTempPass();
            Date sendDate =  mailService.send(request.getEmail(),
                    ResourceBundelLanguage.periodMessage(language.getLanguage(languageOrdinal),
                            "Yourtemppass"), tempPass).getSentDate();

            if (accountDto == null) {
                UserDto newUser = new UserDto();
                AccountDto newAccount = new AccountDto();

                ValidateEmailReq req = new ValidateEmailReq();
                req.setEmail(request.getEmail());
                req.setPassword(tempPass);

                newUser.setPhone(request.getPhone());
                newUser.setName(request.getName());
                userService.save(newUser);

                newAccount.setTemp_password(tempPass);
                newAccount.setEmail(request.getEmail());
                newAccount.setTempPasswordTime(sendDate);
                newAccount.setUser(newUser);
                accountService.save(newAccount);

            } else {
                accountDto.setTemp_password(tempPass);
                accountDto.setTempPasswordTime(sendDate);
                accountService.update(accountDto);
                return (ResourceBundelLanguage.periodMessage(language.getLanguage(languageOrdinal), "singInIsSuccessful"));
            }
        } else {
            throw new AuthNotAcceptableException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(languageOrdinal), "Couldnotparsemail"));
        }


        return tempPass;
    }

    @Override
    public String validate(ValidateEmailReq req, Integer languageOrdinal) {
        AccountDto accountDto = accountService.findByEmail(req.getEmail(), languageOrdinal);
        UserDto dto = userService.findById(accountDto.getUser().getId());
        if (accountDto.getTemp_password().equals(req.getPassword())) {

                dto.setStatus(Status.APPROVED);
                userService.update(dto);




        } else {
            throw new AuthException(ResourceBundelLanguage.periodMessage
                    (language.getLanguage(languageOrdinal), "invalidPassword"));
        }

        return jwtProvider.generateAccessToken(dto.getId());

    }


}
