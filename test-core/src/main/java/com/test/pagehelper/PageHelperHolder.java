package com.test.pagehelper;

import com.github.pagehelper.Dialect;

import java.io.Serializable;

/**
 * 数据源方言
 */
public class PageHelperHolder implements Serializable {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    private static final long serialVersionUID = -3621560893204636042L;

    public static void setPagerType(Dialect dialect) {
        CONTEXT_HOLDER.set(dialect.name());
    }

    public static String getPagerType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearPaerType() {
        CONTEXT_HOLDER.remove();
    }
}
