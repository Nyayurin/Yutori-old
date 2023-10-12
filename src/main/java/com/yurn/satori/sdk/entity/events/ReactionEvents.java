package com.yurn.satori.sdk.entity.events;

/**
 * 表态的事件列表
 *
 * @author Yurn
 */
@SuppressWarnings("unused")
public final class ReactionEvents {
    /**
     * 当表态被添加时触发
     */
    public static final String REACTION_ADDED = "reaction-added";

    /**
     * 当表态被移除时触发
     */
    public static final String REACTION_REMOVED = "reaction-removed";

    private ReactionEvents() {}
}
