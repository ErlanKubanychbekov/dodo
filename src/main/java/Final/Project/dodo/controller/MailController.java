package Final.Project.dodo.controller;

import Final.Project.dodo.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService service;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {

        return ResponseEntity.ok(service.send(to, subject, text));
    }

}
