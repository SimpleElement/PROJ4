package org.example.Lab2_BLPS.service.mail.ws.converter;

import org.example.Lab2_BLPS.service.mail.model.MessageEntity;
import org.example.Lab2_BLPS.service.mail.ws.dto.MessageDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConverterMessageToMessageDto implements Converter<MessageEntity, MessageDto> {

    @Override
    public MessageDto convert(MessageEntity source) {
        MessageDto res = new MessageDto();

        res.setId(source.getId());
        res.setRecipient(source.getRecipient());
        res.setSender(source.getSender());
        res.setTopic(source.getTopic());
        res.setContent(source.getContent());
        res.setDateOfDispatch(source.getDateOfDispatch());
        return res;
    }
}
