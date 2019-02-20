package cn.lucas.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * <p>
 * 地理位置信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 经度
     */
    private Float longitude;

    private String city;

    private String province;
}
