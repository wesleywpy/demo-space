package com.wesley.growth.streaming.log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/20
 */
@Data
@ToString(of = {"ip", "time", "area", "statusCode", "referer"})
@AllArgsConstructor
public class ClickLog implements Serializable {
    private String ip;
    private String time;
    private String area;
    private int statusCode;
    private String referer;

}
