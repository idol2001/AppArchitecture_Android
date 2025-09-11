package info.jacoblee.apparchitecture.common.router;


import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum PageEnum {
    MAIN_PAGE(PageEnum.MAIN_PAGE_DEEPLINK),
    CITY_PAGE(PageEnum.CITY_PAGE_DEEPLINK);

    // 将 URL 定义为 public static final String 常量
    public static final String MAIN_PAGE_DEEPLINK = "customscheme://example.com/main_page/{city_id}";
    public static final String CITY_PAGE_DEEPLINK = "customscheme://example.com/city_page/{city_name}";

    private String pageUrl;
    PageEnum(String url){
        pageUrl = url;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public List<String> getParamList() {
        return getPlaceholders(this.getPageUrl());
    }

    private List<String> getPlaceholders(String input) {
        List<String> placeholders = new ArrayList<>();
        // 正则表达式：
        // \\{  匹配开大括号 { (需要转义)
        // (    开始捕获组
        // [^}]+ 匹配一个或多个非闭大括号 } 的字符 (确保捕获括号内的内容)
        // )    结束捕获组
        // \\}  匹配闭大括号 } (需要转义)
        Pattern pattern = Pattern.compile("\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(input);

        // 查找所有匹配项
        while (matcher.find()) {
            // matcher.group(0) 是整个匹配的文本，例如 "{city_name}"
            // matcher.group(1) 是第一个捕获组的内容，即 "city_name"
            placeholders.add(matcher.group(1));
        }
        return placeholders;
    }
}
