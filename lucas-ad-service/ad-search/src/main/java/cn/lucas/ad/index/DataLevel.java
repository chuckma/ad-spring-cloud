package cn.lucas.ad.index;

import lombok.Getter;

/**
 * @author Administrator
 */
@Getter
public enum DataLevel {


    LEVEL2("2", "level 2"),
    LEVEL3("3", "level 3"),
    LEVEL4("4", "level 4");
    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }


}
