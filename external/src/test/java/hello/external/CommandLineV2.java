package hello.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Set;


/**
 * CommandLine 인수는 스페이스로 구분
 * java -jar app.jar dataA dataB -> [dataA, dataB] 2개 * java -jar app.jar url=devdb -> [url=devdb] 1개
 * url=devdb 이라는 단어를 개발자가 직접 파싱해야 함
 **/

@Slf4j
public class CommandLineV2 {

    public static void main(String[] args) {
        for (String arg : args) {
            log.info("arg {}", arg);
        }
        ApplicationArguments appArgs = new DefaultApplicationArguments(args);

        log.info("SourceArgs = {}", List.of(appArgs.getSourceArgs()));
        log.info("NonOptionArgs = {}", appArgs.getNonOptionArgs());
        log.info("OptionNames = {}", appArgs.getOptionNames());

        Set<String> optionNames = appArgs.getOptionNames();
        for (String optionName : optionNames) {
            log.info("option args {}={}", optionName, appArgs.getOptionValues(optionName));
        }

        List<String> url = appArgs.getOptionValues("url");
        List<String> username = appArgs.getOptionValues("username");
        List<String> password = appArgs.getOptionValues("password");
        List<String> mode = appArgs.getOptionValues("mode");

        log.info("url={}", url);
        log.info("username={}", username);
        log.info("password={}", password);
        log.info("mode={}", mode);

    }
}
