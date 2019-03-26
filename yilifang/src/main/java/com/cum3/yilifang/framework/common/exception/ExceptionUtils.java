package com.cum3.yilifang.framework.common.exception;

/**
 * <p>
 * 异常辅助工具类
 * </p>
 * 
 * @since 2018-07-24
 */
public final class ExceptionUtils {

    private ExceptionUtils() {
    }

    /**
     * 返回一个新的异常，统一构建，方便统一处理
     *
     * @param msg
     *            消息
     * @param t
     *            异常信息
     * @return 返回异常
     */
    public static SysException exp(String msg, Throwable t) {
        return new SysException(msg, t);
    }

    /**
     * 重载的方法
     *
     * @param msg
     *            消息
     * @return 返回异常
     */
    public static SysException exp(String msg) {
        return new SysException(msg);
    }

    /**
     * 重载的方法
     *
     * @param t
     *            异常
     * @return 返回异常
     */
    public static SysException exp(Throwable t) {
        return new SysException(t);
    }

}
