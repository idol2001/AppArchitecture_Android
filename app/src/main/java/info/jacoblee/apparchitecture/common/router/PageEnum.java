package info.jacoblee.apparchitecture.common.router;


import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;


@Getter
public enum PageEnum {
    MAIN_PAGE(PageEnum.MAIN_PAGE_DEEPLINK),
    CITY_PAGE(PageEnum.CITY_PAGE_DEEPLINK);

    // 将 URL 定义为 public static final String 常量
    public static final String MAIN_PAGE_DEEPLINK = "customscheme://example.com/main_page/{city_id}";
    public static final String CITY_PAGE_DEEPLINK = "customscheme://example.com/city_page/{city_name}";

    // 1. 预编译正则表达式，避免重复创建性能损耗
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^}]+)\\}");

    private String pageUrl;
    private final List<String> paramList; // 缓存解析后的参数列表
    PageEnum(String url){
        pageUrl = url;
        // 2. 在构造时就完成解析，后续调用 getParamList 性能极高
        this.paramList = Collections.unmodifiableList(extractPlaceholders(url));
    }

    /**
     * 核心逻辑：提取大括号中的内容
     */
    private static List<String> extractPlaceholders(String input) {
        List<String> placeholders = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            return placeholders;
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(input);
        while (matcher.find()) {
            // group(1) 获取第一个括号内匹配的内容
            placeholders.add(matcher.group(1));
        }
        return placeholders;
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
