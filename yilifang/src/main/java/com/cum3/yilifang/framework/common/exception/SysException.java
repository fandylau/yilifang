package com.cum3.yilifang.framework.common.exception;

import org.springframework.util.StringUtils;

import com.cum3.yilifang.framework.common.utils.MessageUtils;

/**
 * 基础异常
 * @author Fandy Lau
 */
public class SysException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误码对应的参数
     */
    private Object[] args;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public SysException(Throwable throwable) {
        super(throwable);
    }

    public SysException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SysException(String module, String code, Object[] args, String defaultMessage) {
        this.module = module;
        this.code = code;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }

    public SysException(String module, String code, Object[] args) {
        this(module, code, args, null);
    }

    public SysException(String module, String defaultMessage) {
        this(module, null, null, defaultMessage);
    }

    public SysException(String code, Object[] args) {
        this(null, code, args, null);
    }

    public SysException(String defaultMessage) {
        this(null, null, null, defaultMessage);
    }

    @Override
    public String getMessage() {
        String message = null;
        if (!StringUtils.isEmpty(code)) {
            message = MessageUtils.message(code, args);
        }
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }

    public String getModule() {
        return module;
    }

    public String getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    @Override
    public String toString() {
        return this.getClass() + "{" + "module='" + module + '\'' + ", message='" + getMessage() + '\'' + '}';
    }
}
