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
@AllArgsConstructor
@ToString(of = {"rowkey", "clickCount"})
public class ClickCount implements Serializable {

    private String rowkey;

    private int clickCount;
}
