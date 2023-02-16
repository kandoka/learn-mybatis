package com.kandoka.mybatis.scripting.xmltags;

/**
 * @Description related to
 * <pre>{@code
 * <if test="null != activityId">
 *      activity_id = #{activityId}
 * </if>
 * }</pre>
 *
 *
 * @Author kandoka
 * @Date 2023/2/14 17:24
 */
public interface SqlNode {
    boolean apply(DynamicContext context);
}
