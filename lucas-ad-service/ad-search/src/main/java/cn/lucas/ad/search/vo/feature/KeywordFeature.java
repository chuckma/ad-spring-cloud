package cn.lucas.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Administrator
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {

    private List<String> keywords;
}
