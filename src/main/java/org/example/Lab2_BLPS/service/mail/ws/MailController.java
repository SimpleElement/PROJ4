package org.example.Lab2_BLPS.service.mail.ws;

import lombok.RequiredArgsConstructor;
import org.example.Lab2_BLPS.common.object.page.PageResponse;
import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.mail.service.MailService;
import org.example.Lab2_BLPS.service.mail.ws.dto.MessageDto;
import org.example.Lab2_BLPS.service.mail.ws.filter.MessageFilter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;

    private final ConversionService conversionService;

    @GetMapping("/getMessages")
    public PageResponse<MessageDto> getMessages(@Valid MessageFilter filter) {
        Page<MessageEntity> page = mailService.getMessages(filter.getPageNumber(), filter.getPageSize());

        PageResponse<MessageDto> response = conversionService.convert(page, PageResponse.class);
        response.setContent(
                page.getContent().stream()
                        .map(messageEntity -> conversionService.convert(messageEntity, MessageDto.class))
                        .collect(Collectors.toList())
        );
        response.setTotalElements(page.getTotalElements());
        return response;
    }

    @PostMapping("/writeMessage")
    public MessageDto writeMessage(@Valid @RequestBody MessageDto messageDto) {
        return conversionService.convert(
                mailService.writeMessage(conversionService.convert(messageDto, MessageEntity.class)),
                MessageDto.class
        );
    }
}
