package cn.lucas.ad.index.adunit;

/**
 * @author Administrator
 */
public class AdUnitConstants {

    /**
     * 二进制 1 2 4 8 16 可以用 位或运算 位于运算
     */
    public static class POSITION_TYPE {
        public static final int KAIPING = 1;
        public static final int TIEPIAN = 2;
        /**
         * 中贴广告 eg.视频播放中间
         */
        public static final int TIEPIAN_MINDLE = 3;
        /**
         * 暂停广告
         */
        public static final int TIEPIAN_PAUSE = 8;
        /**
         * 后贴广告 eg.视频播放完毕
         */
        public static final int TIEPIAN_POST = 16;

    }
}
