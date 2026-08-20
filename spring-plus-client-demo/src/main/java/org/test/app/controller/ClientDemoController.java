package org.test.app.controller;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.spring.plus.context.annotation.Debounce;
import org.spring.plus.core.RespStatusConstants;
import org.spring.plus.web.http.HttpResp;
import org.spring.plus.web.servlet.mvc.method.annotation.HttpStandardResponse;
import org.spring.plus.web.servlet.mvc.method.annotation.OperationLog;
import org.spring.plus.web.socket.WebsocketSessionManager;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.test.app.dto.TestDto;

import java.util.Locale;

/**
 * @author zhanghaowei
 * @since 1.0
 */
@Tag(name = "测试")
@HttpStandardResponse
@RestController
public class ClientDemoController {

    private final MessageSource messageSource;


    public ClientDemoController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Debounce(duration = 3000)
    @Operation(description = "测试校验post")
    @OperationLog
    @PostMapping("/test/post")
    public Object testPostValidation(@RequestBody @Validated TestDto testDto) {
        Locale locale = LocaleContextHolder.getLocale();
        testDto.setMessage(messageSource.getMessage(RespStatusConstants.NOT_FOUND.getCode(), null, locale));
        return testDto;
    }


    @Debounce(duration = 3000)
    @Operation(description = "测试校验get")
    @OperationLog
    @GetMapping("/test/get")
    public HttpResp<TestDto> testGetValidation(@Validated TestDto testDto) {
        Locale locale = LocaleContextHolder.getLocale();
        testDto.setMessage(messageSource.getMessage(RespStatusConstants.NOT_FOUND.getCode(), null, locale));
        return new HttpResp<>(testDto, RespStatusConstants.OK);
    }


    @Debounce(duration = 3000)
    @Operation(description = "测试发送ws消息")
    @OperationLog
    @PostMapping("/test/ws/msg/{userId}")
    public void sendStandardAllClientMsg(@PathVariable String userId) {
        WebsocketSessionManager.sendMsg(userId, RandomUtil.randomString(64));
    }

}
