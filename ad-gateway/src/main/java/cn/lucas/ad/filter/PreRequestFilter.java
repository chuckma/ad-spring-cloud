package cn.lucas.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {

    // 定义 filter 的类型
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    // filter 执行的优先级 越小越先
    @Override
    public int filterOrder() {
        return 0;
    }

    // 是否需要执行这个过滤器 当某些条件成立的时候才去执行过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime",System.currentTimeMillis());
        return null;
    }
}
