package cn.lucas.ad.search;

import cn.lucas.ad.search.vo.SearchRequest;
import cn.lucas.ad.search.vo.SearchResponse;

/**
 * @author Administrator
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
