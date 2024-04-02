package Final.Project.dodo.service.impl;


import Final.Project.dodo.authservice.utils.JwtProvider;
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
import java.util.Date;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {


    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final MailService mailService;


    public AuthServiceImpl(JwtProvider jwtProvider, UserService userService, MailService mailService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.mailService = mailService;
    }
    @Override
    public Long validateToken(String token) {
        return jwtProvider.validateToken(token);
    }

    @Override
    public String getClaim(String token) {
        return jwtProvider.getClaim(token);
    }

    @Override
    public Boolean checkTime(Date sendTime) {
        LocalDateTime newTime = LocalDateTime.now();
        Duration duration = Duration.between(sendTime.toInstant(), newTime.atZone(ZoneId.systemDefault()).toInstant());

        if (duration.toMinutes() >= 10) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public String generateTempPass() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        String randomString = String.valueOf(randomNumber);

        return randomString.substring(0, 3) + "-" + randomString.substring(3);


    }


    @Override
    public String auth(AuthRequest request) {
        UserResponse userFromDB = userService.getUserByEmail(request.getEmail());
        String tempPass = generateTempPass();
        Date sendDate =  mailService.send(request.getEmail(), "Dodo",tempPass).getSentDate();

        if(userFromDB == null){

            UserCreateRequest user = new UserCreateRequest();
            user.setPhone(request.getPhone());
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setTempPass(tempPass);
            user.setSendDate(sendDate);
            userService.create(user);

        }else{


            UserDto dto = userService.findById(userFromDB.getId());
            dto.setTempPass(tempPass);
            dto.setSendDate(sendDate);


            userService.update(dto);

        }

        return tempPass;

    }

        //найти в базе данных в таблице accounts запись по email

        //если нет записи то дальше
        //отправляем temp password на почту  temp password(567-123)
        //создаем Account, сетим данные ( email, temp password, status new , add date, update date )сохраняем account entity
        //создаем User entity сетим все данные

        /*если запись есть
        //отправляем temp password на почту  temp password(567-123)
        находим аккаунт обновляем temp password
        * */


    @Override
    public String validate(ValidateEmailReq req) {
        UserResponse user =  userService.getUserByEmail(req.getEmail());
        UserDto dto  = userService.findById(user.getId());
        if(dto.getTempPass().equals(req.getPassword())){
            if(checkTime( dto.getSendDate())){
                dto.setStatus(Status.DELETE);
                userService.update(dto);
            }else {
                return "Time is over";
            }

        }else{
            return "Password incorrect";
        }
       //найти в базе данных в таблице accounts запись по email
        //сравнение пароля из реквеста и из бд
        // если время отправленного пароля истекло (дается 10 мин) то кидаем ошибку
        //если пароль неверный то кидаем ошибку
        //если не прошло 10 минут и пароль верны идем дальше
        //делаем статус у account (status approved)
        //найти юзера по аккаунту
        //формируем токен из userId и роли
        //возвращаем токен

        return jwtProvider.generateAccessToken(dto.getId());

    }


}
