package com.wesley.growth.streaming.sql;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/27
 */
@Data
@AllArgsConstructor
public class Word implements Serializable {
    private String word;
}
